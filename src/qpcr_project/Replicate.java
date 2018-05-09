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
		// once all ct's retrieved, calculate ctMean and sd
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
    public ArrayList<Double> getCtAll(){
    		return ctAll;
    }
    
    /**
     * Calculate the mean of all entries for a given list of ct values
     * @param ctAll
     * @return
     */
	public double calcMean(ArrayList<Double> ctAll){
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
	 * @param ctAll
	 * @return
	 */
	public double calcSd(ArrayList<Double> ctAll){
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
		sd = Math.sqrt(sd/(count-1));
		return sd;
	}
    
    /*
     * add following functionality:
     * 
     * - see if sd good enough
     * - remove outliers from ctAll[]
     * - each replicate object creates a Datasheet class containing the entire data of excel. See how I should improve this
     */
	
	public static void main (String args[]) throws JSONException {
		Replicate replicateTest = new Replicate("5 shRB1 D", "RB1");
		System.out.println(replicateTest.getSampleName());
		System.out.println(replicateTest.getTargetName());
		System.out.println(replicateTest.getCtMean());
		System.out.println(replicateTest.getSd());
	}
}
