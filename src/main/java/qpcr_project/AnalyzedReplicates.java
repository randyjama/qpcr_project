package qpcr_project;

import java.util.ArrayList;

import org.json.JSONException;

public class AnalyzedReplicates {
	/**
	 * This class represents a list of the analyzed replicates for every sampleName
	 * and targetName from the excel sheet. It is merely an
	 * ArrayList<ReplicateObjects> that does the manual labour of going through
	 * replicates to eliminate outliers and recalculate the mean and SD.
	 */

	private ArrayList<Replicate> analyzedReplicates = new ArrayList<Replicate>();

	public AnalyzedReplicates(String jsonExcel) throws JSONException {
		Datasheet excelData = new Datasheet(jsonExcel);
		analyzeResults(excelData, analyzedReplicates);
	}

	/**
	 * Loops through the datasheet and creates an ArrayList<Replicates> for every
	 * sampleName and targetName combinations. NOTE: This function modifies the
	 * analysedReplicates that you pass into it.
	 * 
	 * @param datasheet
	 * @param analyzedReplicates
	 * @throws JSONException
	 */
	private void analyzeResults(Datasheet datasheet, ArrayList<Replicate> analyzedReplicates) throws JSONException {
		// loop through datasheet, make replicates, and add them to the
		// arraylist<replicates>
		for (int i = 0; i < datasheet.size(); i++) {
			String sampleName = datasheet.getSampleName(i);
			String targetName = datasheet.getTargetName(i);
			Replicate replicate = new Replicate(sampleName, targetName, datasheet);
			analyzedReplicates.add(replicate);

			// after adding replicate object to analyzedReplicates, increment i until a new
			// sampleName and/or targetName are reached.
			while ((i < datasheet.size()) && sampleName.equals(datasheet.getSampleName(i))
					&& targetName.equals(datasheet.getTargetName(i))) {
				i++;
			}
		}

		// print results of analyzedReplicates
		int count = 0;
		for (int i = 0; i < analyzedReplicates.size(); i++) {
			System.out.println(
					analyzedReplicates.get(i).getSampleName() + ", " + analyzedReplicates.get(i).getTargetName() + ":\n"
							+ analyzedReplicates.get(i).getCtMean() + ", " + analyzedReplicates.get(i).getSd() + "\n");
			count++;
		}
		System.out.println(count);

	}
	
	public int size() {
		int count = 0;
		for (int i = 0; i < analyzedReplicates.size(); i++) {
			count++;
		}
		return count;
	}
	
	public double getAvgCt(String sampleName, String targetName) {
		for (int i = 0; i < analyzedReplicates.size(); i++) {
			if (analyzedReplicates.get(i).getSampleName() == sampleName && analyzedReplicates.get(i).getTargetName() == targetName){
				return analyzedReplicates.get(i).getCtMean();
			}
		}
		return 0;
	}
	
	public double getSD(String sampleName, String targetName) {
		for (int i = 0; i < analyzedReplicates.size(); i++) {
			if (analyzedReplicates.get(i).getSampleName() == sampleName && analyzedReplicates.get(i).getTargetName() == targetName){
				return analyzedReplicates.get(i).getSd();
			}
		}
		return 0;
	}
	
	
	// getter
	public String getSampleName(int index) {
		return analyzedReplicates.get(index).getSampleName();
	}
	public String getTargetName(int index) {
		return analyzedReplicates.get(index).getTargetName();
	}
	
	// setter

	public static void main(String args[]) throws JSONException {
		AnalyzedReplicates tester = new AnalyzedReplicates("testdata.json");
		System.out.println(tester.size());
	}
}
