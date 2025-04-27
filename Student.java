package application;

//Ameer Qadadha - 1221147
public class Student {
	// Student attributes
	private int studentID;
	private String studentName;
	private int tawjihiGrade;
	private int placementTestGrade;
	private double admissionMark;
	private String status;
	private String chosenMajor;

	public Student() {// Constructor to initialize empty StudentLinkedList

	}

	// Constructor with student's attributes
	public Student(int studentID, String studentName, int tawjihiGrade, int placementTestGrade, double admissionMark,
			String chosenMajor) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.tawjihiGrade = tawjihiGrade;
		this.placementTestGrade = placementTestGrade;
		this.admissionMark = admissionMark;
		this.chosenMajor = chosenMajor;
	}

	public String status(int acceptanceGrade) {
		if (acceptanceGrade > this.admissionMark) {
			this.status = "-Rejected";
		} else {
			this.status = "-Accepted";
		}
		return this.status;
	}

	// getter & setter methods
	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getTawjihiGrade() {
		return tawjihiGrade;
	}

	public void setTawjihiGrade(int tawjihiGrade) {
		this.tawjihiGrade = tawjihiGrade;
	}

	public int getPlacementTestGrade() {
		return placementTestGrade;
	}

	public void setPlacementTestGrade(int placementTestGrade) {
		this.placementTestGrade = placementTestGrade;
	}

	public double getAdmissionMark() {
		return admissionMark;
	}

	public void setAdmissionMark(double admissionMark) {
		this.admissionMark = admissionMark;
	}

	public String getChosenMajor() {
		return chosenMajor;
	}

	public void setChosenMajor(String chosenMajor) {
		this.chosenMajor = chosenMajor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
