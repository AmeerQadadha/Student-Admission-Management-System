package application;

//Ameer Qadadha - 1221147

public class SNode {
	private Student element;// student element in each node
	private SNode next; // reference to the next node in the linked list

	public SNode() {// default constructor

	}

	// a node to store student data
	public SNode(Student element) {
		this.element = element;
	}

	// getters and setters
	public SNode getNext() {
		return next;
	}

	public void setNext(SNode next) {
		this.next = next;
	}

	public Student getElement() {
		return element;
	}

	public void setElement(Student element) {
		this.element = element;
	}

}
