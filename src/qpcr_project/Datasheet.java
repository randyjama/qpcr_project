package qpcr_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
	 * Calculate the mean of all entries for a given sampleName and targetName
	 * @param sampleName
	 * @param targetName
	 * @return
	 */
	public double getMean(String sampleName, String targetName) {
		// be sure to add exceptions for wrong strings
		double sumCt = 0;
		int count = 0;
		for (Line line : excelData) {
			if (line.getSampleName().equals(sampleName) && line.getTargetName().equals(targetName)) { // match found
				sumCt += line.getCt();
				count++;
			}
		}
		return sumCt / count;
	}
	
	/**
	 * Calculate the standard deviation of all entries for a given sampleName and targetName
	 * @param sampleName
	 * @param targetName
	 * @return
	 */
	public double getSD(String sampleName, String targetName) {
		// be sure to add exceptions for wrong strings
		double mean = getMean(sampleName, targetName);
		double sd = 0;
		int count = 0;
		for (Line line : excelData) {
			if (line.getSampleName().equals(sampleName) && line.getTargetName().equals(targetName)) { // match found
				sd += Math.pow((mean - line.getCt()), 2);
				count++;
			}
		}
		sd = Math.sqrt(sd/(count-1));
		return sd;
	}
	
	// add the following functionalities using excelData:
	// bool see if SD good enough
	// analyze triplicate

	// create class that uses this object and creates final result including
	// replicate results
	// 2 classes? replicate_line and storage<replicate_line>

	public static void main(String args[]) throws JSONException {
		readFile("testdata.json");
		createJsonArray("testdata.json");
		Datasheet testObj = new Datasheet("testdata.json");
		System.out.println(testObj.getMean("1 shCTR", "B-actin"));
		System.out.println(testObj.getSD("1 shCTR", "B-actin"));
	}
}
