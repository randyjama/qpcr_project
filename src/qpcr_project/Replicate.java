package qpcr_project;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.json.JSONException;

public class Replicate {
	/**
	 * As opposed to representing a single line of data as in Line class, this class
	 * represents an entire replicate of lines containing the same sample and target
	 * names, along with mean CT, SD,
	 */

	private String sampleName;
	private String targetName;
	private ArrayList<Double> ctAll = new ArrayList<Double>();
	private double ctMean;
	private double sd;

	public Replicate(String sampleName, String targetName) throws JSONException {
		Datasheet excelData = new Datasheet("testdata.json");
		this.sampleName = sampleName;
		this.targetName = targetName;
		this.ctAll = excelData.getCtAll(sampleName, targetName);
		this.ctAll = removeCtOutliers(ctAll);
		// once all ct's retrieved and filtered for smallest difference, calculate ctMean and sd
		this.ctMean = calcMean(ctAll);
		this.sd = calcSd(ctAll);
	}

	// setter functMeanions
	public void setSampleName(String sampleName) {
		if (sampleName == null || sampleName == "") {
			throw new RuntimeException("Name must not be empty.");
		}
		this.sampleName = sampleName;
	}

	public void setTargetName(String targetName) {
		if (targetName == null || targetName == "") {
			throw new RuntimeException("Name must not be empty.");
		}
		this.targetName = targetName;
	}

	public void setCtMean(double ctMean) {
		this.ctMean = ctMean;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	// getter functMeanions
	public String getSampleName() {
		return sampleName;
	}

	public String getTargetName() {
		return targetName;
	}

	public double getCtMean() {
		return ctMean;
	}

	public double getSd() {
		return sd;
	}

	public ArrayList<Double> getCtAll() {
		return ctAll;
	}

	/**
	 * Calculate the mean of all entries for a given list of ct values
	 * 
	 * @param ctAll
	 * @return
	 */
	public double calcMean(ArrayList<Double> ctAll) {
		double sumCt = 0;
		int count = 0;
		for (Double ct : ctAll) {
			sumCt += ct;
			count++;
		}
		if (count == 0) {
			throw new NoSuchElementException("Error: Ensure ctAll is not empty or null");
		}
		return sumCt / count;
	}

	/**
	 * Calculate the standard deviation of all entries for a given list of ct values
	 * 
	 * @param ctAll
	 * @return
	 */
	public double calcSd(ArrayList<Double> ctAll) {
		double mean = calcMean(ctAll);
		double sd = 0;
		int count = 0;
		for (Double ct : ctAll) {
			sd += Math.pow((mean - ct), 2);
			count++;
		}
		if (count == 0) {
			throw new NoSuchElementException("Error: Ensure ctAll is not empty or null.");
		}
		sd = Math.sqrt(sd / (count - 1));
		return sd;
	}

	// partial credit to https://www.geeksforgeeks.org/find-minimum-difference-pair/
	/**
	 * Returns an array containing two values with the smallest difference.
	 * @param ctAll Must not be empty or null
	 * @return
	 */
	private ArrayList<Double> removeCtOutliers (ArrayList<Double> ctAll){
        // Initialize difference as infinite
		if (ctAll.isEmpty() || ctAll == null) {
			throw new NoSuchElementException("ArrayList must not be empty or null.");
		}
        // ct1 and ct2 keep track of whatever values create the smallest difference
		double ct1 = 0;
		double ct2 = 0;
		double diff = Integer.MAX_VALUE;
		ArrayList<Double> result = new ArrayList<Double>();
        
        // for each value loop through remaining values to compare smallest difference. 
        // Save the two indices that make that smallest difference.
        for (int i = 0; i < ctAll.size() - 1; i++) {
        		for (int j = i+1; j < ctAll.size(); j++) {
        			//diff = (diff > Math.abs(ctAll.get(i) - ctAll.get(j))) ? Math.abs(ctAll.get(i) - ctAll.get(j)) : diff;
        			if (diff > Math.abs(ctAll.get(i) - ctAll.get(j))){
        				diff = Math.abs(ctAll.get(i) - ctAll.get(j));
            			ct1 = ctAll.get(i);
            			ct2 = ctAll.get(j);
        			}
        		}
        }
        
        // Now return ArrayList only containing the smallest difference ct's
        result.add(ct1);
        result.add(ct2);
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        return result;
	}

	/*
	 * add following functionality:
	 * 
	 * - see if sd good enough - remove outliers from ctAll[] - each replicate
	 * object creates a Datasheet class containing the entire data of excel. See how
	 * I should improve this
	 */

	public static void main(String args[]) throws JSONException {
		Replicate replicateTest = new Replicate("4 shRB1 C", "SYP");
		System.out.println(replicateTest.getSampleName());
		System.out.println(replicateTest.getTargetName());
		System.out.println(replicateTest.getCtMean());
		System.out.println(replicateTest.getSd());
	}
}
