package application;

import javafx.scene.control.TableView;

//Ameer Qadadha - 1221147

public class MajorLinkedList {
	private MNode front;// head of the double linked list
	private MNode back;// tail of the linked list
	private MNode current;// current node for to use in methods
	private int size;// size of the linked list

	// default constructor
	public MajorLinkedList() {

	}

	// constructor with attributes
	public MajorLinkedList(MNode front, MNode back, MNode current) {
		this.front = front;
		this.back = back;
		this.current = current;
	}

	// method to check if the list is empty
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	// Inserting major sorted in a double linked list with major attributes
	public void InsertMajor(String majorName, int acceptanceGrade, double tawjihiWeight, double placementTestWeight) {
		Major major = new Major(majorName, acceptanceGrade, tawjihiWeight, placementTestWeight);// creating a new node
																								// of Major type in the
																								// list
		MNode newNode = new MNode(major);
		if (isEmpty()) {// checking if list is empty
			front = back = newNode;
			size++;
			return;
		}
		MNode current = front;// using current node to navigate and inserting the node sorted by alphabetic
		if (majorName.compareToIgnoreCase(current.getElement().getMajorName()) <= 0) {
			newNode.setNext(front);
			front.setPrev(newNode);
			front = newNode;
			size++;
			return;
		}
		// traverse the list to find the correct position for the new major
		while (current.getNext() != null
				&& majorName.compareToIgnoreCase(current.getNext().getElement().getMajorName()) > 0) {
			current = current.getNext();
			if (majorName.equalsIgnoreCase(current.getElement().getMajorName())) {// checking if the name is duplicated
				return;
			}
		}
		// insert the new node at the sorted position
		newNode.setNext(current.getNext());
		newNode.setPrev(current);

		if (current.getNext() != null) {
			current.getNext().setPrev(newNode);
		} else {
			back = newNode;// setting as last node if its the lastly sorted
		}

		current.setNext(newNode);
		size++;
	}

	public boolean DeleteMajor(String majorName) {// deleting a major by taking major name
		if (isEmpty()) {// returning false if the list is empty
			return false;
		}
		if (size == 1) {// checking if the list has only one node then deleting it
			front = back = null;
			return true;
		}
		// traverse to find the node to delete
		MNode current = front;
		while (current != null) {
			if (current.getElement().getMajorName().equalsIgnoreCase(majorName)) {// if the node to be deleted is the
																					// head of the list
				if (current == front) {
					front = front.getNext();// setting the second node as the new head of the list
				} else if (current == back) {
					back = current.getPrev();// the node to be deleted is the tail of the list
					back.setNext(null);
				} else {
					current.getPrev().setNext(current.getNext());// finding the node and fixing pointers
					current.getNext().setPrev(current.getPrev());
				}
				return true;
			}
			current = current.getNext();// traversing to find the node
		}
		return false;
	}

	// updating major based on multiple attributes
	public void UpdateMajor(String majorName, String newMajorName, int NAG, double NTW, double NPT) {
		// checking validity
		if (NAG > 100 || NAG < 0) {
			System.out.println("Acceptance Grade was not changed!");
			return;
		}
		if (NTW > 100 || NTW < 0) {
			System.out.println("Tawjihi Weightings was not changed!");
			return;
		}
		if (NPT > 100 || NPT < 0) {
			System.out.println("Placement Test was not changed!");
			return;
		}
		// traversing to find the node to be deleted
		MNode current = front;
		while (current != null) {
			if (current.getElement().getMajorName().equals(majorName)) {
				current.getElement().setMajorName(newMajorName);// setting the new major name
				current.getElement().setAcceptanceGrade(NAG);// setting the new acceptance grade
				current.getElement().setTawjihiWeight(NTW);// setting the new tawjihi weight
				current.getElement().setPlacementTestWeight(NPT);// setting the new placement test weight
				return;
			} else {
				current = current.getNext();
			}
		}
		return;
	}

	// method that returns true if the major exists or not based on major name
	public boolean searchMajor(String majorName) {
		// traversing to find the major name
		MNode current = front;
		while (current != null) {
			if (current.getElement().getMajorName().equalsIgnoreCase(majorName)) {// if statement to check if the major
																					// matches the major inserted
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	// a method that returns a major node based on a major name for future use like
	// inserting students
	public MNode getNode(String majorName) {
		MNode current = front;
		while (current != null) {
			if (current.getElement().getMajorName().equalsIgnoreCase(majorName)) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "exports", "rawtypes" })
	// a method to display major details in a table view that takes letter as an
	// atribute and displayed majors based on a letter
	public void newSearch(String letter, TableView tableView) {
		MNode current = front;
		while (current != null) {
			if (current.getElement().getMajorName().contains(letter)) {
				tableView.getItems().add(current.getElement());
			}
			current = current.getNext();
		}
	}

	// navigating to the next major node
	public MNode NavigateNext() {
		if (current != null && current.getNext() != null) {
			current = current.getNext();
			return current;
		}
		return null;
	}

	// navigating to the previous major node
	public MNode NavigatePrev() {
		if (current != null && current.getPrev() != null) {
			current = current.getPrev();
			return current;
		}
		return null;
	}

	// getter & setter methods
	public MNode getFront() {
		return front;
	}

	public void setFront(MNode front) {
		this.front = front;
	}

	public MNode getBack() {
		return back;
	}

	public void setBack(MNode back) {
		this.back = back;
	}

	public MNode getCurrent() {
		return current;
	}

	public void setCurrent(MNode current) {
		this.current = current;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
