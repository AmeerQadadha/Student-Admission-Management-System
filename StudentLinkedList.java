package application;

//Ameer Qadadha - 1221147

public class StudentLinkedList {
	private SNode front;// head of linked list
	private SNode back;// tail of linked list
	private SNode current;
	private int size;

	public StudentLinkedList() {// Constructor to initialize empty StudentLinkedList

	}

	public StudentLinkedList(SNode front, SNode back, SNode current) {// Constructor with specified nodes
		this.front = front;
		this.back = back;
		this.current = current;
	}

	public boolean isEmpty() {// Returns true if size=0 which means the list is empty
		if (size == 0) {
			return true;
		}
		return false;
	}

//Inserting students in a single linked list based on student's attributes while sorting the student based on admission mark
	public void InsertStudent(int studentID, String studentName, int tawjihiGrade, int placementTestGrade,
			String chosenMajor) {
		// Declaring attributes
		double tawjihiWeight = Main.getMajorList().getNode(chosenMajor).getElement().getTawjihiWeight();
		double placementTestWeight = Main.getMajorList().getNode(chosenMajor).getElement().getPlacementTestWeight();
		double admissionMark = (tawjihiGrade * tawjihiWeight) + (placementTestGrade * placementTestWeight);
		// Initializing new object student
		Student student = new Student(studentID, studentName, tawjihiGrade, placementTestGrade, admissionMark,
				chosenMajor);
		// Initializing a new student node to insert
		SNode newNode = new SNode(student);

		if (isEmpty()) {// checking if list is empty
			front = back = newNode;
		} else if (front.getElement().getAdmissionMark() < admissionMark) {// Comparing between front and the new node
																			// by admission mark
			newNode.setNext(front);
			front = newNode;
		} else {
			SNode current = front;// Declaring current node as front of the student linked list to compare next
									// nodes and insert them in descending order
			while (current.getNext() != null && current.getNext().getElement().getAdmissionMark() >= admissionMark) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
		}
		size++;// incrementing size
	}

	public void DeleteStudent(int studentID) {
		if (isEmpty()) {// checking if list is empty
			return;
		}
		if (size == 1) {// checking if the list has one node then deleting it
			front = back = null;
			size--; // decrementing size

		}
		if (front.getElement().getStudentID() == studentID) {// checking if the node to be deleted is the head and
																// assigning the second node as the new head of the list
			front = front.getNext();
			size--;
		}
		SNode current = front;// looping between nodes from front to tail to find the node that should be
								// deleted based on student's ID.
		while (current != null && current.getNext() != null) {
			if (current.getNext().getElement().getStudentID() == studentID) {
				current.setNext(current.getNext().getNext());
				size--;
			}
			current = current.getNext();// moving to the next node
		}
	}

//updating student information based on what the user wants to edit and update
	public void UpdateStudent(int studentID, String newStudentName, int newTawjihiGrade, int newPlacementTest) {
		if (newTawjihiGrade > 100 || newTawjihiGrade < 0) {// checking if tawjihi grade is valid or invalid
			return;
		}
		if (newPlacementTest > 100 || newPlacementTest < 0) {// checking if placement test grade is valid or invalid
			return;
		}
		SNode current = front;// looping to find the certain node
		while (current != null) {// updating attributes based on user's choice
			if (current.getElement().getStudentID() == (studentID)) {// updating student id
				current.getElement().setStudentName(newStudentName);// updating student name
				current.getElement().setTawjihiGrade(newTawjihiGrade);// updating student tawjihi Grade
				current.getElement().setPlacementTestGrade(newPlacementTest);// updating student placement test grade
				return;
			} else {
				current = current.getNext();// moving to the next major node
			}
		}
		return;
	}

//Searching for a student based on student ID, returning true if found and false if not found
	public boolean searchStudent(int studentID) {
		SNode current = front;// looping to find the certain node
		while (current != null) {
			if (current.getElement().getStudentID() == studentID) {// checking if the node was found or not
				return true;
			}
			current = current.getNext();// moving to the next node
		}
		return false;
	}

//a method to delete a student based on major name
	public void DeleteStudent(String stMajor) {
		if (front == null) {// checking if major exists
			return;
		}
		while (front != null && front.getElement().getChosenMajor().equals(stMajor)) {
			front = front.getNext();// checking if the student is the head of the student linked list in that major
			size--;
		}

		SNode current = front;// looping to find the node
		while (current.getNext() != null) {
			if (current.getNext().getElement().getChosenMajor().equals(stMajor)) {// checking if the node's name matches
																					// the major name
				current.setNext(current.getNext().getNext());// deleting the student by making its previous node point
																// to its next
			} else {
				current = current.getNext();// moving to the next node
			}
		}
	}

	// getter & setter for class attributes
	public SNode getFront() {
		return front;
	}

	public SNode getBack() {
		return back;
	}

	public void setBack(SNode back) {
		this.back = back;
	}

	public void setFront(SNode front) {
		this.front = front;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public SNode getCurrent() {
		return current;
	}

	public void setCurrent(SNode current) {
		this.current = current;
	}

}
