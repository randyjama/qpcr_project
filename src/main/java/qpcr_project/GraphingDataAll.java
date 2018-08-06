package qpcr_project;

import static spark.Spark.get;
import static spark.Spark.*;

import java.util.ArrayList;

import org.json.JSONException;

public class GraphingDataAll {
	/**
	 * A representation of all the gene-ctr pair groups. Contains the finalized rq
	 * results for graphing any gene-ctr pair combinations. This is the top-level
	 * class.
	 */

	private AnalyzedReplicates analyzedReplicates;
	private String geneNames[];
	private String ctrNames[];
	private ArrayList<GraphingDataGeneCtrPair> data = new ArrayList<GraphingDataGeneCtrPair>(); // internal representation

	public GraphingDataAll(String fileName, String ctrNames[], String geneNames[]) throws JSONException {
		analyzedReplicates = new AnalyzedReplicates(fileName);
		this.geneNames = geneNames;
		this.ctrNames = ctrNames;
		createObject();
	}

	private void createObject() {
		// create a geneCtrPair object for every combination of genes and CTR's
		for (int i = 0; i < ctrNames.length; i++) {
			for (int j = 0; j < geneNames.length; j++) {
				GraphingDataGeneCtrPair geneCtrPair = new GraphingDataGeneCtrPair(geneNames[j], ctrNames[i], analyzedReplicates);
				data.add(geneCtrPair);
			}
		}
	}
	
	
	
	
	public static void main(String args[]) throws JSONException {
		String ctrNames[] = {"B-actin", "GAPDH"};
		String geneNames[] = {"BRN2", "CGA", "NSE", "SYP", "cMYC", "RB1"};
		GraphingDataAll tester = new GraphingDataAll("testdata.json", ctrNames, geneNames);
	}

}
