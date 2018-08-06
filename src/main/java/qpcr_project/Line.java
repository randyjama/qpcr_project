package qpcr_project;

class Line {

    private String sampleName;
    private String targetName;
    private double ct;
    
    public Line(String sampleName, String targetName, double ct) {
    		this.sampleName = sampleName;
    		this.targetName = targetName;
    		this.ct = ct;
    }
    
    // setter functions
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
    public void setCt(double ct) {
        this.ct = ct;
    }

    // getter functions
    public String getSampleName() {
        return sampleName;
    }

    public String getTargetName() {
        return targetName;
    }
    
    public double getCt() {
		return ct;
	}
}
