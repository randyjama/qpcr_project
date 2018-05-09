package qpcr_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.json.*;

public class Datasheet {

	private ArrayList<Line> excelData = new ArrayList<Line>();

	// constructor fills out exceData with information from the filename
	public Datasheet(String filename) throws JSONException {
		JSONArray jarr = createJsonArray(filename);
		for (int i = 0; i < jarr.length(); i++) {
			createLine(jarr.getJSONObject(i));
		}
	}

	// credit to https://www.thepolyglotdeveloper.com/2015/03/parse-json-file-java/
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
	private static JSONArray createJsonArray(String filename) throws JSONException {
		String jsonData = readFile(filename);
		JSONArray jarr = new JSONArray(jsonData);
		for (int i = 0; i < jarr.length(); i++) {
			System.out.println(jarr.get(i));
		}
		return jarr;
	}

	/**
	 * creates line objects from json objects and adds it to the arraylist of lines
	 * to represent the exceldata
	 * 
	 * @param jobj
	 * @throws JSONException
	 */
	private void createLine(JSONObject jobj) throws JSONException {
		Line line = new Line(jobj.getString("sample_name"), jobj.getString("target_name"), jobj.getDouble("ct"));
		excelData.add(line);
	}
	
	/**
	 * Returns an ArrayList<Double> containing all ct values for a given sampleName and targetName
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
	
	// add the following functionalities using excelData:
	// bool see if SD good enough

	// create class that uses this object and creates final result including
	// replicate results
	// 2 classes? replicate_line (all data for one replicate)
	// functions
	// analyze triplicate
	// and Arraylist<replicate_line>

	public static void main(String args[]) throws JSONException {
		readFile("testdata.json");
		createJsonArray("testdata.json");
		Datasheet testObj = new Datasheet("testdata.json");
//		System.out.println(testObj.getMean("1 shCTR", "B-actin"));
//		System.out.println(testObj.getSD("1 shCTR", "B-actin"));
//		System.out.println(testObj.getSD("INCORRECT INPUT", "B-actin"));
//		System.out.println(testObj.getSD("1 shCTR", "INCORRECT INPUT"));
	}
}
