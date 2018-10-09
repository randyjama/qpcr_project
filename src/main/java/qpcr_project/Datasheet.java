package qpcr_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

import org.json.*;

public class Datasheet {

	private ArrayList<Line> excelData = new ArrayList<Line>();

	// constructor fills out exceData with information from the filename
	public Datasheet(String json) throws JSONException {
		JSONArray jarr = createJsonArray(json);
		for (int i = 0; i < jarr.length(); i++) {
			try {
				excelData.add(createLine(jarr.getJSONObject(i)));
			} catch (JSONException e) { // catch normal exception and give Randy special exception
				//TODO: handle exception
				// add to the exception the failure line
				throw e; // make own exception type and make all catches catch this exception type
						// have the exception show the line number and what the issue is, maybe print the specific line
			}
			System.out.println(excelData.get(i).getSampleName() + ", " + excelData.get(i).getTargetName()
					+ ": " + excelData.get(i).getCt());
		}
	}
	
	// getters and setters for sampleName, targetName, and ct
    public String getSampleName(int index) {
        return excelData.get(index).getSampleName();
    }

    public String getTargetName(int index) {
    		return excelData.get(index).getTargetName();
    }
    
    public double getCt(int index) {
		return excelData.get(index).getCt();
		}
    public void setSampleName(String sampleName, int index) {
        if (sampleName == null || sampleName == "") {
          throw new RuntimeException("Name must not be empty.");
        }
        excelData.get(index).setSampleName(sampleName);
    }
    public void setTargetName(String targetName, int index) {
        if (targetName == null || targetName == "") {
          throw new RuntimeException("Name must not be empty.");
        }
        excelData.get(index).setTargetName(targetName);
    }
    public void setCt(double ct, int index) {
        excelData.get(index).setCt(ct);
    }

	// partial credit to
	// https://www.thepolyglotdeveloper.com/2015/03/parse-json-file-java/
	/**
	 * Reads the given file into a string
	 * 
	 * @param filename
	 * @return string of file text
	 */
	private static String readFile(String filename) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				// System.out.println(line);
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Creates JSONArray of given json-format file. To be used in another function
	 * to create an arraylist of result objects.
	 * 
	 * @param filename
	 * @return JSONArray where each index is in a JSONobject format
	 * @throws JSONException
	 */
	private static JSONArray createJsonArray(String jsonData) throws JSONException {
		// String jsonData = readFile(filename);
		JSONArray jarr = new JSONArray(jsonData);
//		for (int i = 0; i < jarr.length(); i++) {
//			System.out.println(jarr.get(i));
//		}
		return jarr;
	}

	/**
	 * Creates line objects from json objects
	 * 
	 * @param jobj
	 * @return
	 * @throws JSONException
	 */
	private Line createLine(JSONObject jobj) throws JSONException {
		try {
			Line line = new Line(jobj.getString("SampleName"), jobj.getString("TargetName"), jobj.optDouble("CT"));
			// put each category in it's own try catch block and throw the error that it is the specific one that's missing.
			// or that the value is bad, etc. Also be sure they dont include the column headers
			return line;
		} catch (Exception e) {
			//TODO: handle exception
			System.out.println(jobj.toString());
			throw e;
		}
		// Line line = new Line(jobj.getString("SampleName"), jobj.getString("TargetName"), jobj.optDouble("CT"));
		// return line;
	}

	/**
	 * Returns an ArrayList<Double> containing all ct values for a given sampleName
	 * and targetName
	 * 
	 * @param sampleName
	 * @param targetName
	 * @return
	 */
	public ArrayList<Double> getCtAll(String sampleName, String targetName) {
		ArrayList<Double> ctAll = new ArrayList<Double>();
		for (Line line : excelData) {
			if (line.getSampleName().equals(sampleName) && line.getTargetName().equals(targetName)) {
				// if names match, add line's ct to ctAll
				ctAll.add(line.getCt());
			}
		}
		return ctAll;
	}

	/**
	 * Returns a json list of all the genes (Target Names) in the datasheet
	 * 
	 * @param None
	 * @return result A JSONArray of all the genes in the datasheet
	 */
	public JSONArray getAllGenes() {
		// create unique LinkedHashSet to later iterate through to add to json array
		// want to maintain order that user expects from qPCR plate layout
		HashSet<String> uniqueSet = new LinkedHashSet<String>();
		for (Line line : excelData) {
			uniqueSet.add(line.getTargetName());
		}
		JSONArray result = new JSONArray();
		for (String targetName : uniqueSet){
			JSONObject obj = new JSONObject();
			obj.put("name", targetName);
			result.put(obj);
		}
		return result;
	}
	
	public int size() {
		return excelData.size();
	}

	public static void main(String args[]) throws JSONException {
		// readFile("testdata.json");
		// createJsonArray("testdata.json");
		Datasheet testObj = new Datasheet("testdata.json");
		// System.out.println(testObj.getMean("1 shCTR", "B-actin"));
		// System.out.println(testObj.getSD("1 shCTR", "B-actin"));
		// System.out.println(testObj.getSD("INCORRECT INPUT", "B-actin"));
		// System.out.println(testObj.getSD("1 shCTR", "INCORRECT INPUT"));
		// System.out.println(testObj.getAllGenes());
	}
}
