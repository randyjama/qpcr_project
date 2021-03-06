package qpcr_project;

import java.util.ArrayList;

import org.json.JSONException;

public class GraphingDataGeneCtrPair {
	/**
	 * Holds an ArrayList<GraphingDataLines> where each line is a sample for a given
	 * gene-ctr pair.
	 */

	private ArrayList<GraphingDataLine> data = new ArrayList<GraphingDataLine>();
	private String geneName;
	private String ctrName;
	private AnalyzedReplicates analyzedReplicates;

	public GraphingDataGeneCtrPair(String geneName, String ctrName, AnalyzedReplicates analyzedReplicates) {
		this.geneName = geneName;
		this.ctrName = ctrName;
		this.analyzedReplicates = analyzedReplicates;
		createObject();
	}

	/**
	 * Creates the object that represents all finalized data for a given gene-ctr
	 * pair. Internal representation is an ArrayList<GraphingDataLine>
	 */
	private void createObject() {
		// have arrays that save every entry for ctr CT
		// have arrays that save every entry for ctr SD
		// have array that saves every entry for gene CT
		// have arrays that save every entry for gene SD
		// have array for SAME ORDER to save given sample name
		ArrayList<String> sampleNameArr = new ArrayList<String>();
		ArrayList<Double> geneCtArr = new ArrayList<Double>();
		ArrayList<Double> geneSDArr = new ArrayList<Double>();
		ArrayList<Double> ctrCtArr = new ArrayList<Double>();
		ArrayList<Double> ctrSDArr = new ArrayList<Double>();

		// fill up above 3 arrays then loop through each row to make a graphing line obj
		for (int i = 0; i < analyzedReplicates.size(); i++) {
			// if new sampleName found add sampleName
			if (!sampleNameArr.contains(analyzedReplicates.getSampleName(i))
					&& (geneName.equals(analyzedReplicates.getTargetName(i))
							|| ctrName.equals(analyzedReplicates.getTargetName(i)))) {
				sampleNameArr.add(analyzedReplicates.getSampleName(i));
			}
			if (geneName.equals(analyzedReplicates.getTargetName(i))) { // add ct of gene name
				double goiAvgCt = analyzedReplicates.getAvgCt(analyzedReplicates.getSampleName(i),
						analyzedReplicates.getTargetName(i));
				double goiStDev = analyzedReplicates.getSD(analyzedReplicates.getSampleName(i),
						analyzedReplicates.getTargetName(i));
				geneCtArr.add(goiAvgCt);
				geneSDArr.add(goiStDev);
			} else if (ctrName.equals(analyzedReplicates.getTargetName(i))) { // add ct of ctr name
				double refAvgCt = analyzedReplicates.getAvgCt(analyzedReplicates.getSampleName(i),
						analyzedReplicates.getTargetName(i));
				double refStDev = analyzedReplicates.getSD(analyzedReplicates.getSampleName(i),
						analyzedReplicates.getTargetName(i));
				ctrCtArr.add(refAvgCt);
				ctrSDArr.add(refStDev);
			}
		}

		double initValue = geneCtArr.get(0) - ctrCtArr.get(0); // constant for value column calculation below

		// all arrays filled, loop through and begin making arrayList<GraphingDataLines>
		// to add to obj
		for (int i = 0; i < sampleNameArr.size(); i++) {
			// calculate column values based on 4 above now-filled arrays
			double value = geneCtArr.get(i) - ctrCtArr.get(i);
			double stErr = Math.sqrt(Math.pow(geneSDArr.get(i), 2) + Math.pow(ctrSDArr.get(i), 2)); // sqrt(sumsq(a,b))
			double ct = value - initValue;
			double ctErrDown = ct + stErr;
			double ctErrUp = ct - stErr;
			double rq = Math.pow(2, -1 * ct);
			double rqErrDown = rq - Math.pow(2, -1 * ctErrDown);
			double rqErrUp = Math.pow(2, -1 * ctErrUp) - rq;

			// System.out.println(sampleNameArr.get(i) + ", " + geneCtArr.get(i) + ", " + geneSDArr.get(i) + ", " + ctrCtArr.get(i) + ", " + ctrSDArr.get(i) + ", "
			// + ", " +  value + ", " + stErr + ", " + ct + ", " +  ctErrDown + ", " + ctErrUp + ", " + rq + ", " + rqErrDown
			// + ", " + rqErrUp);

			GraphingDataLine line = new GraphingDataLine(sampleNameArr.get(i), geneCtArr.get(i), geneSDArr.get(i),
					ctrCtArr.get(i), ctrSDArr.get(i), value, stErr, ct, ctErrDown, ctErrUp, rq, rqErrDown, rqErrUp);

			data.add(line);
		}
	}

	// getters and setters below
	public String getGeneName() {
		return geneName;
	}
	
	public String getCtrName() {
		return ctrName;
	}
	
	public GraphingDataLine getGraphingDataLine (int index) {
		return data.get(index);
	}
	
	public ArrayList<GraphingDataLine> getGraphingData() {
		return data;
	}

	public static void main(String args[]) throws JSONException {
		AnalyzedReplicates analyzedReplicates = new AnalyzedReplicates("testdata.json");
		GraphingDataGeneCtrPair tester1 = new GraphingDataGeneCtrPair("BRN2", "B-actin", analyzedReplicates);
		GraphingDataGeneCtrPair tester2 = new GraphingDataGeneCtrPair("RB1", "GAPDH", analyzedReplicates);
	}

}
