package qpcr_project;

import static spark.Spark.get;
import static spark.Spark.*;

import java.util.ArrayList;

import org.eclipse.jetty.server.session.JDBCSessionDataStoreFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphingDataAll {
	/**
	 * A representation of all the gene-ctr pair groups. Contains the finalized rq
	 * results for graphing any gene-ctr pair combinations. This is the top-level
	 * class.
	 */

	private AnalyzedReplicates analyzedReplicates;
	//private String geneNames[];
	// private String ctrNames[];
	private ArrayList<String> geneNames = new ArrayList<String>();
	private ArrayList<String> ctrNames = new ArrayList<String>();
	private String jsonData;
	private ArrayList<GraphingDataGeneCtrPair> data = new ArrayList<GraphingDataGeneCtrPair>(); // internal representation

	public GraphingDataAll(String jsonData) throws JSONException {
		this.jsonData = jsonData;
		// need to separate jsonData from ctr's before creating object, and create Names-arrays
		separateCtrs();
		separateNonCtrs();
		analyzedReplicates = new AnalyzedReplicates(this.jsonData);
		createObject();
	}

	private void createObject() {
		// create a geneCtrPair object for every combination of genes and CTR's
		for (int i = 0; i < ctrNames.size(); i++) {
			for (int j = 0; j < geneNames.size(); j++) {
				GraphingDataGeneCtrPair geneCtrPair = new GraphingDataGeneCtrPair(geneNames.get(j), ctrNames.get(i), analyzedReplicates);
				data.add(geneCtrPair);
			}
		}
	}

	/**
	 * Separates ctr from the rest of the json data and sets the
	 * instance variable ctrNames[] to include the values with the key
	 * "ctr" from the given json string. 
	 * Note: This function mutates the instance variable jsonData.
	 * 
	 */
	private void separateCtrs() {
		JSONArray jsonDataArray = new JSONArray(jsonData);
		int i = jsonDataArray.length() - 1;
		while (jsonDataArray.getJSONObject(i).has("ctr")) {
			ctrNames.add(jsonDataArray.getJSONObject(i).getString("ctr"));
			jsonDataArray.remove(i);
			i--;
		}
		jsonData = jsonDataArray.toString();
	}

	/**
	 * Separates (creates) a new arrayList containing the non-ctr genes.
	 */
	private void separateNonCtrs() {
		Datasheet datasheet = new Datasheet(jsonData);
		JSONArray allGenes = datasheet.getAllGenes();
		for (int i = 0; i < allGenes.length(); i++) {
			geneNames.add(allGenes.getJSONObject(i).getString("name"));
		}
		// now that geneList has all genes begin removing the ctrs
		for (int i = 0; i < ctrNames.size(); i++) {
			for (int j = 0; j < geneNames.size(); j++) {
				if (ctrNames.get(i).equals(allGenes.getJSONObject(j).getString("name"))) {
					geneNames.remove(j);
					break; // unique names so once found just move to next ctrName
				}
			}
		}
		System.out.println(ctrNames);
		System.out.println(geneNames);
	}

	/**
	 * Create a function that returns json (stringified) of packaged data
	 */
	public String stringifyAllData() {
		JSONArray jarr = new JSONArray();
		for (int i = 0; i < data.size(); i++) {
			JSONArray sampleNames = new JSONArray();
			// add geneName and ctrName
			JSONObject geneName = new JSONObject();
			JSONObject ctrName = new JSONObject();
			geneName.put("ctr", data.get(i).getCtrName());
			ctrName.put("gene", data.get(i).getGeneName());
			sampleNames.put(geneName);
			sampleNames.put(ctrName);
			for (GraphingDataLine sampleLine : data.get(i).getGraphingData()) { // retrieved a single "packet" gene-ctr pair
				JSONObject line = new JSONObject();
				line.put("sampleName", sampleLine.getSampleName());
				line.put("goiAvgCt", sampleLine.getColValue("goiAvgCt"));
				line.put("goiStDev", sampleLine.getColValue("goiStDev"));
				line.put("refAvgCt", sampleLine.getColValue("refAvgCt"));
				line.put("refStDev", sampleLine.getColValue("refStDev"));
				line.put("value", sampleLine.getColValue("value"));
				line.put("stErr", sampleLine.getColValue("stErr"));
				line.put("ct", sampleLine.getColValue("ct"));
				line.put("ctErrDown", sampleLine.getColValue("ctErrDown"));
				line.put("ctErrUp", sampleLine.getColValue("ctErrUp"));
				line.put("rq", sampleLine.getColValue("rq"));
				line.put("rqErrDown", sampleLine.getColValue("rqErrDown"));
				line.put("rqErrUp", sampleLine.getColValue("rqErrUp"));

				sampleNames.put(line);
			}
			jarr.put(sampleNames);
		}
		return jarr.toString();
	}
	
	
	public static void main(String args[]) throws JSONException {
		// read the file and construct these arrays from json input! Do not make the user enter it themselves
		// String ctrNames[] = {"B-actin", "GAPDH"};
		// String geneNames[] = {"BRN2", "CGA", "NSE", "SYP", "cMYC", "RB1"};
		GraphingDataAll tester = new GraphingDataAll("testdata.json");
	}

}
