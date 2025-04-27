package application;

//Ameer Qadadha - 1221147

public class MNode {
	private Major element;// major element with attributes to each node
	private MNode next;// next and previous pointers
	private MNode prev;
	private StudentLinkedList studentsList;// a single linked list in the major

	public MNode() {// default constructor
		this.studentsList = new StudentLinkedList();
	}

	// constructor with the major element and to defind a student linked list
	public MNode(Major element) {
		this.element = element;
		this.studentsList = new StudentLinkedList();
	}

	// getter & setter methods
	public MNode getNext() {
		return next;
	}

	public void setNext(MNode next) {
		this.next = next;
	}

	public MNode getPrev() {
		return prev;
	}

	public void setPrev(MNode prev) {
		this.prev = prev;
	}

	public Major getElement() {
		return element;
	}

	public void setElement(Major element) {
		this.element = element;
	}

	public StudentLinkedList getStudentsList() {
		return studentsList;
	}

	public void setStudentsList(StudentLinkedList studentsList) {
		this.studentsList = studentsList;
	}

}
