package application;

//Ameer Qadadha - 1221147
public class MajorTableView {// A class to add the attributes and data in the statistics and reporting
								// option to report each major with the accepted and rejected students
	private String majorName;// store major name
	private int accepted;// store accepted students in the major
	private int rejected;// store rejected students in the major

	public MajorTableView() {// default constructor

	}

	// constructor with the attributes
	public MajorTableView(String majorName, int accepted, int rejected) {
		this.majorName = majorName;
		this.accepted = accepted;
		this.rejected = rejected;

	}

	// getter & setter methods
	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
}
