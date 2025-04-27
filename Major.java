package application;

//Ameer Qadadha - 1221147

public class Major {
	// Major attributes
	private String majorName;
	private int acceptanceGrade;
	private double tawjihiWeight;
	private double placementTestWeight;
	

	// Default constructor
	public Major() {
		
	}

	// constructor with major attributes
	public Major(String majorName, int acceptanceGrade, double tawjihiWeight, double placementTestWeight) {
		this.majorName = majorName;
		this.acceptanceGrade = acceptanceGrade;
		this.tawjihiWeight = tawjihiWeight;
		this.placementTestWeight = placementTestWeight;
	}

	// getters and setters
	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getAcceptanceGrade() {
		return acceptanceGrade;
	}

	public void setAcceptanceGrade(int acceptanceGrade) {
		this.acceptanceGrade = acceptanceGrade;
	}

	public double getTawjihiWeight() {
		return tawjihiWeight;
	}

	public void setTawjihiWeight(double tawjihiWeight) {
		this.tawjihiWeight = tawjihiWeight;
	}

	public double getPlacementTestWeight() {
		return placementTestWeight;
	}

	public void setPlacementTestWeight(double placementTestWeight) {
		this.placementTestWeight = placementTestWeight;
	}

}
