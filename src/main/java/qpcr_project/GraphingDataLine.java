package qpcr_project;

import java.util.HashMap;

public class GraphingDataLine {
	/**
	 * Simple container class for all data associated with a line
	 */
	// map of maps (map<cell name, map<category names, double values>>)
	// private HashMap<String, HashMap<String, Double>> lineMap = new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, Double> columnMap = new HashMap<String, Double>();
	private String sampleName;
	private double goiAvgCt;
	private double goiStDev;
	private double refAvgCt;
	private double refStDev;
	private double value;
	private double stErr;
	private double ct;
	private double ctErrDown;
	private double ctErrUp;
	private double rq;
	private double rqErrDown;
	private double rqErrUp;

	// GOI avg Ct GOI stdev ref avg Ct ref s tdev #VALUE! st err ^^Ct err down err
	// up RQ err down err up
	public GraphingDataLine(String sampleName, double goiAvgCt, double goiStDev, double refAvgCt, double refStDev,
			double value, double stErr, double ct, double ctErrDown, double ctErrUp, double rq, double rqErrDown,
			double rqErrUp) {
		this.sampleName = sampleName;
		this.goiAvgCt = goiAvgCt;
		this.goiStDev = goiStDev;
		this.refAvgCt = refAvgCt;
		this.refStDev = refStDev;
		this.value = value;
		this.stErr = stErr;
		this.ct = ct;
		this.ctErrDown = ctErrDown;
		this.ctErrUp = ctErrUp;
		this.rq = rq;
		this.rqErrDown = rqErrDown;
		this.rqErrUp = rqErrUp;

		columnMap.put("goiAvgCt", this.goiAvgCt);
		columnMap.put("goiStDev", this.goiStDev);
		columnMap.put("refAvgCt", this.refAvgCt);
		columnMap.put("refStDev", this.refStDev);
		columnMap.put("value", this.value);
		columnMap.put("stErr", this.stErr);
		columnMap.put("ct", this.ct);
		columnMap.put("ctErrDown", this.ctErrDown);
		columnMap.put("ctErrUp", this.ctErrUp);
		columnMap.put("rq", this.rq);
		columnMap.put("rqErrDown", this.rqErrDown);
		columnMap.put("rqErrUp", this.rqErrUp);
		
		// lineMap.put(this.sampleName, columnMap);
	}
	
	// getters
	public Double getColValue(String colName) {
		return columnMap.get(colName);
	}
	public String getSampleName() {
		return sampleName;
	}
	
	// setter
	public void setColValue(String colName, double value) throws NullPointerException {
		columnMap.put(colName, value);
	}	
	
	public static void main (String args[]) {
		// below tests line data
		GraphingDataLine line = new GraphingDataLine("Sample Name 1", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		System.out.println(line.getColValue("goiAvgCt"));
		System.out.println(line.getColValue("goiStDev"));
		System.out.println(line.getColValue("refAvgCt"));
		System.out.println(line.getColValue("refStDev"));
		System.out.println(line.getColValue("valueStErr"));
		System.out.println(line.getColValue("ct"));
		System.out.println(line.getColValue("ctErrDown"));
		System.out.println(line.getColValue("ctErrUp"));
		System.out.println(line.getColValue("rq"));
		System.out.println(line.getColValue("rqErrDown"));
		System.out.println(line.getColValue("rqErrUp"));	
		line.setColValue("goiAvgCt", 5000);
		System.out.println(line.getColValue("goiAvgCt"));
		System.out.println(line.getSampleName());
	}
}
