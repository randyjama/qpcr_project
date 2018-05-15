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

	public AnalyzedReplicates(String fileName) throws JSONException {
		Datasheet excelData = new Datasheet(fileName);
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

		int count = 0;
		// print results of analyzedReplicates
		for (int i = 0; i < analyzedReplicates.size(); i++) {
			System.out.println(
					analyzedReplicates.get(i).getSampleName() + ", " + analyzedReplicates.get(i).getTargetName() + ":\n"
							+ analyzedReplicates.get(i).getCtMean() + ", " + analyzedReplicates.get(i).getSd() + "\n");
			count++;
		}
		System.out.println(count);
	}
	
	
	// make function to check if replicate has <= 1 entries. If so, it cannot be
	// used.

	public static void main(String args[]) throws JSONException {
		AnalyzedReplicates tester = new AnalyzedReplicates("testdata.json");
	}
}
