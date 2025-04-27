package application;
//Ameer Qadadha - 1221147

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Main extends Application {
	private static MajorLinkedList majorList;// initializing a major linked list to store majors data
	private static StudentLinkedList studentsList;// initializing a student linked list to store student data
	private MNode mnode = new MNode();
	private int accepted = 0;// counter for accepted students
	private int rejected = 0;// counter for rejected students

	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {// the start stage the runs and has all of the scenes
		try {
			if (majorList == null) {
				majorList = new MajorLinkedList();// checking if the major list is null
			}
			if (studentsList == null) {
				studentsList = new StudentLinkedList();// checking if the student list is null
			}
			accepted = 0;// stopping both counters from incrementing
			rejected = 0;
			BorderPane root = new BorderPane();// border pane for the scene

			Label wlc = new Label("Student Admission Management System");// title label
			wlc.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
			BorderPane.setAlignment(wlc, Pos.CENTER);
			root.setTop(wlc);

			Button stats = new Button("Statistics and Reporting");// button to move to statistics scene
			stats.setPrefHeight(50);
			stats.setPrefWidth(180);
			stats.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");
			stats.setOnAction(e -> {
				stats(primaryStage);// statistics scene
			});

			Button navi = new Button("Navigation System");// button to move to navigation scene
			navi.setPrefHeight(50);
			navi.setPrefWidth(170);
			navi.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");
			navi.setOnAction(e -> {
				NavigationStage(primaryStage);// navigation scene
			});

			Button major = new Button("Majors System");// button to move to major scene
			major.setPrefHeight(50);
			major.setPrefWidth(130);
			major.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");
			major.setOnAction(e -> {
				Major(primaryStage);// major management scene
			});

			Button student = new Button("Students System ");// button to move to students management stage
			student.setPrefHeight(50);
			student.setPrefWidth(130);
			student.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");
			student.setOnAction(e -> {
				Student(primaryStage);// students management scene
			});

			Button exit = new Button("Exit Program!");
			exit.setPrefHeight(50);
			exit.setPrefWidth(130);
			exit.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			exit.setOnAction(e -> {// a button to close the program
				primaryStage.close();
			});

			Button save = new Button("Save students to file");// a button to save new updated lists back to its files
			save.setPrefHeight(50);
			save.setPrefWidth(190);
			save.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			save.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();// creating a file chooser
				fileChooser.setTitle("Save Major and Student Data");
				File file = fileChooser.showOpenDialog(null);
				if (file != null) {// checking if file is not new
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {// writing back to the files
						MNode mnode = majorList.getFront();// pointing at the front of the major list
						while (mnode != null) {
							SNode snode = mnode.getStudentsList().getFront();// front of the student linked list
							while (snode != null) {
								Student student2 = snode.getElement();// creating an object of student to return the
																		// attributes and write them back to the file
								writer.write("    Student ID: " + student2.getStudentID() + ", Name: "
										+ student2.getStudentName() + ", Tawjihi Grade: " + student2.getTawjihiGrade()
										+ ", Placement Test Grade: " + student2.getPlacementTestGrade()
										+ ", Admission Mark: " + student2.getAdmissionMark());
								writer.newLine();// writing a new line
								snode = snode.getNext();// moving to the next student
							}
							mnode = mnode.getNext();// moving to the next major
						}
						Alert alert = new Alert(Alert.AlertType.INFORMATION);// giving an alert for success writing
						alert.setHeaderText("Success");
						alert.setContentText("Data saved successfully");
						alert.showAndWait();
					} catch (IOException ioException) {
						Alert alert = new Alert(Alert.AlertType.ERROR);// alert to catch the exception
						alert.setHeaderText("Error");
						alert.setContentText("Data was not saved");
						alert.showAndWait();
					}
				}
			});
			Button save2 = new Button("Save majors to file");// saving edited majors back to its file
			save2.setPrefHeight(50);
			save2.setPrefWidth(190);
			save2.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");
			save2.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();// declaring a file chooser
				fileChooser.setTitle("Save Major and Student Data");// title of fc
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {// using buffered writer and
																							// file writer
						MNode mnode = majorList.getFront();// traversing to get all of the major nodes then writing the
															// data back to the selected file
						while (mnode != null) {
							Major major2 = mnode.getElement();
							writer.write("Major: " + major2.getMajorName() + ", Acceptance Grade: "
									+ major2.getAcceptanceGrade() + ", Tawjihi Weight: " + major2.getTawjihiWeight()
									+ ", Placement Test Weight: " + major2.getPlacementTestWeight());
							writer.newLine();
							mnode = mnode.getNext();// moving to the next major
						}
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText("Success");
						alert.setContentText("Data saved successfully");// data saved alert
						alert.showAndWait();
					} catch (IOException ioException) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Error");
						alert.setContentText("Data was not saved");
						alert.showAndWait();
					}
				}
			});

			HBox hbox = new HBox(10);// horizontal box to store and add all buttons
			hbox.getChildren().addAll(major, student, stats, navi, save, save2);

			hbox.setAlignment(Pos.CENTER);// aligning the hbox
			root.setCenter(hbox);
			root.setBottom(exit);

			Scene scene = new Scene(root, 1000, 500);// choosing scenec co-ordinates to run
			Image backgroundImage = new Image(getClass().getResource("bg.jpg").toExternalForm());// setting a background
																									// image
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			root.setBackground(new Background(bgImage));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("Student Admission Management System");// scene title
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "exports" })
	public void Major(Stage majorStage) {
		try {
			if (majorList == null) {
				majorList = new MajorLinkedList();
			}
			if (studentsList == null) {
				studentsList = new StudentLinkedList();
			}

			BorderPane root = new BorderPane();
			TableView<Major> tableView = new TableView<>();// initializing a new table view from Major type

			TableColumn<Major, String> majorNameColumn = new TableColumn<>("Major Name");
			majorNameColumn.setCellValueFactory(new PropertyValueFactory<>("majorName"));// storing all major attributes
			majorNameColumn.setPrefWidth(100);// editting major column width

			TableColumn<Major, Integer> acceptanceGradeColumn = new TableColumn<>("Acceptance Grade");
			acceptanceGradeColumn.setCellValueFactory(new PropertyValueFactory<>("acceptanceGrade"));// storing all
																										// major
																										// attributes
			acceptanceGradeColumn.setPrefWidth(130);// editting major column width

			TableColumn<Major, Double> tawjihiWeightColumn = new TableColumn<>("Tawjihi Weight");
			tawjihiWeightColumn.setCellValueFactory(new PropertyValueFactory<>("tawjihiWeight"));// storing all major
																									// attributes
			tawjihiWeightColumn.setPrefWidth(100);// editting major column width

			TableColumn<Major, Double> placementTestColumn = new TableColumn<>("Placement Test Weight");
			placementTestColumn.setCellValueFactory(new PropertyValueFactory<>("placementTestWeight"));// storing all
																										// major
																										// attributes
			placementTestColumn.setPrefWidth(150);// editting major column width

			tableView.getColumns().addAll(majorNameColumn, acceptanceGradeColumn, tawjihiWeightColumn,
					placementTestColumn);// adding column to the table view

			root.setCenter(tableView);

			Button back = new Button("Back");
			back.setPrefHeight(50);// button back to return to previous scene/stage
			back.setPrefWidth(100);
			back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			back.setOnAction(e -> {
				start(majorStage);// go back to start stage
			});

			Button read = new Button("Read from file");
			read.setPrefHeight(50);// read from file button
			read.setPrefWidth(140);
			read.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			read.setOnAction(e -> {// reading the data from the file and saving them in a double linked list
				FileChooser fileChooser = new FileChooser();// making a file chooser
				File file = fileChooser.showOpenDialog(majorStage);
				if (file != null) {
					try (Scanner scanner = new Scanner(file)) {
						if (scanner.hasNextLine()) {
							scanner.nextLine();// skipping the next line
						}
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine().trim();
							String[] parts = line.split(":");// creating a parts array to store file attributes - rows
																// and column
							if (parts.length == 4) {// making sure parts are only 4
								try {
									String majorName = parts[0].trim();
									int acceptanceGrade = Integer.parseInt(parts[1].trim());
									double tawjihiWeight = Double.parseDouble(parts[2].trim());
									double placementTestWeight = Double.parseDouble(parts[3].trim());
									majorList.InsertMajor(majorName, acceptanceGrade, tawjihiWeight,
											placementTestWeight);
									// after storing them, we insert the major with its attributes to the double
									// linked list
								} catch (NumberFormatException ex) {
									ex.printStackTrace();
								}
							} else {
							}
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					MNode current = majorList.getFront();// traversing and adding all major data to the table view
					while (current != null) {
						Major major = current.getElement();
						tableView.getItems().add(major);
						current = current.getNext();
					}
					Alert readData = new Alert(Alert.AlertType.INFORMATION);// alert to inform saving and reading
																			// success
					readData.setHeaderText("Success!");
					readData.setContentText("Data was read and saved successfully");
					readData.showAndWait();
				}
			});

			Button reload = new Button("Re-Load");// button reload that displays the data after editting
			reload.setPrefHeight(50);
			reload.setPrefWidth(100);
			reload.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			reload.setOnAction(e -> {
				tableView.getItems().clear();// adding new OR editted linked list to the table view
				MNode current = majorList.getFront();
				while (current != null) {
					Major major = current.getElement();
					tableView.getItems().add(major);
					current = current.getNext();// moving to the next major
				}
			});

			Button insert = new Button("Insert");// insertion major stage
			insert.setPrefHeight(50);
			insert.setPrefWidth(100);
			insert.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			insert.setOnAction(e -> {
				InsertStage(majorStage);
			});

			Button delete = new Button("Delete");// deleting major stage
			delete.setPrefHeight(50);
			delete.setPrefWidth(100);
			delete.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			delete.setOnAction(e -> {
				DeleteStage(majorStage);
			});

			Button update = new Button("Update");// updating major stage
			update.setPrefHeight(50);
			update.setPrefWidth(100);
			update.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			update.setOnAction(e -> {
				UpdateStage(majorStage);
			});

			Button search = new Button("Search");// searching for a major stage
			search.setPrefHeight(50);
			search.setPrefWidth(100);
			search.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			search.setOnAction(e -> {
				SearchMajor(majorStage);
			});

			HBox buttonBox = new HBox(10);// hbox to store buttons
			buttonBox.getChildren().addAll(back, read, reload, insert, delete, update, search);
			buttonBox.setAlignment(Pos.CENTER);// alignment
			root.setBottom(buttonBox);

			Scene scene = new Scene(root, 850, 500);// scene width and height
			Image backgroundImage = new Image(getClass().getResource("bgmajor.jpg").toExternalForm());// setting a
																										// background
																										// image
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			root.setBackground(new Background(bgImage));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			majorStage.setScene(scene);
			majorStage.setTitle("Major System");// title for major system
			majorStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("exports")
	public void InsertStage(Stage insertStage) {// first button which is inserting a major scene/stage!
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(8);

		TextField txtfield1 = new TextField();// making 4 textfields for different needs
		TextField txtfield2 = new TextField();
		TextField txtfield3 = new TextField();
		TextField txtfield4 = new TextField();

		Label title = new Label("Insertion Stage:");// title label
		Label label1 = new Label("Major Name:");// other 4 labels for the textfields
		Label label2 = new Label("Acceptance Grade:");
		Label label3 = new Label("Tawjihi Weightings:");
		Label label4 = new Label("Placement Test Weight:");

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");// style
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		label2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		label3.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		label4.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

		Button insert = new Button("Insert");// insert major button!
		insert.setPrefHeight(50);
		insert.setPrefWidth(75);
		insert.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		insert.setOnAction(e -> {
			String majorName = txtfield1.getText().trim();// returning the majorName from the textfield
			if (majorName.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");// an error type alert to make sure major name is not empty!
				alert.setContentText("Major Name cannot be empty");
				alert.showAndWait();
				return;
			}

			try {// checking validity and converting string to double / int.
				int acceptanceGrade = Integer.parseInt(txtfield2.getText().trim());
				double tawjihiWeight = Double.parseDouble(txtfield3.getText().trim());
				double placementTestWeight = Double.parseDouble(txtfield4.getText().trim());
				if (inValidAG(acceptanceGrade)) {// checking validity of acceptance grade
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Number");// giving an error alert if invalid
					alert.setContentText("Acceptance Grade should be smaller than 100 and greater than 0");
					alert.showAndWait();
					return;
				}
				if (inValidTW(tawjihiWeight) || inValidPT(placementTestWeight)) {// checking validity of other
																					// weightings
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Number");// giving an error alert if invalid
					alert.setContentText(
							"Tawjihi Weight and Placement TestWeight Grade should be smaller than 1 and greater than 0");
					alert.showAndWait();
					return;
				}
				if (inValidMN(majorName)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Duplicate");// giving an error alert if major is a duplicate or already exists
					alert.setContentText("Major already exists");
					alert.showAndWait();
					return;
				}
				if (!majorName.matches("[a-zA-Z\\s]+")) {// giving an error alert if major name contains numbers!
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Name");
					alert.setContentText("Major Name cannot have numbers");
					alert.showAndWait();
					return;
				}
				// inserting the major with its attributes to the double major linked list
				majorList.InsertMajor(majorName, acceptanceGrade, tawjihiWeight, placementTestWeight);

				Alert insertedAlert = new Alert(Alert.AlertType.INFORMATION);
				insertedAlert.setHeaderText("Inserted");// Alert for success
				insertedAlert.setContentText("The major was inserted sucessfully");
				insertedAlert.showAndWait();
			} catch (NumberFormatException e2) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Input");// alert for empty text fields
				alert.setContentText("Empty textfield(s)");
				alert.showAndWait();
				return;
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// Back button for previous stage
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Major(insertStage);// major stage
		});
		Scene scene = new Scene(root, 800, 500);// width and height

		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());// background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));// setting the bg image
		root.add(title, 2, 0);// adding differnet implementations to the fx scene
		root.addRow(6, label1, txtfield1);
		root.addRow(7, label2, txtfield2);
		root.addRow(8, label3, txtfield3);
		root.addRow(9, label4, txtfield4);
		root.addRow(10, insert);
		root.addRow(35, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		insertStage.setScene(scene);
		insertStage.setTitle("Insert Major");// scene title
		insertStage.show();
	}

	@SuppressWarnings("exports")
	public void DeleteStage(Stage deleteStage) {// deleting major stage
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		ComboBox<String> comboBox = new ComboBox<>();// combo box that gives major names

		Label title = new Label("Delete Stage:");// scene title
		Label label1 = new Label("Major Name:");// label to specify what the combo box is for

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");// style
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

		MNode current = majorList.getFront();// traversing to add all majors to the combo box for the user to choose
												// from
		while (current != null) {
			comboBox.getItems().add(current.getElement().getMajorName());
			current = current.getNext();
		}

		Button delete = new Button("Delete");// deleting a major button
		delete.setPrefHeight(50);
		delete.setPrefWidth(75);
		delete.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
		delete.setOnAction(e -> {
			String majorName = comboBox.getValue();// returning the comboBox value which is major name
			if (majorName == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty field");// alert for if the user doesnt pick a major
				alert.setContentText("Choose a major!");
				alert.showAndWait();
				return;
			}
			Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
			deleteAlert.setHeaderText("Deletion");// alert for confirmation to make sure it was not an accidental
													// deletion.
			deleteAlert.setContentText("Are you sure you want to delete " + majorName);

			Optional<ButtonType> option = deleteAlert.showAndWait();
			if (option.get() == ButtonType.OK) {// checking the result of the button
				majorList.getNode(majorName).getStudentsList().DeleteStudent(majorName);
				majorList.DeleteMajor(majorName);
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setHeaderText("Success");// deleting the major if the user clicks on OK which means it was
														// not an accidental deletion
				successAlert.setContentText("Major was deleted successfully");
				successAlert.showAndWait();
				return;
			} else {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");// cancelled delete (accidental deletion).
				cancelAlert.setContentText("Major was not deleted (cancelled)");
				cancelAlert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);// back button for previous stage
		back.setPrefWidth(75);
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
		back.setOnAction(e -> {
			Major(deleteStage);
		});

		Scene scene = new Scene(root, 800, 500);// width and height of scene
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());// back ground image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		root.setBackground(new Background(bgImage));
		root.add(title, 10, 0);// setting the implementation in the fx scene
		root.addRow(6, label1, comboBox);
		root.addRow(7, delete);
		root.addRow(70, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		deleteStage.setScene(scene);
		deleteStage.setTitle("Delete Major");// title of scene
		deleteStage.show();
	}

	@SuppressWarnings("exports")
	public void UpdateStage(Stage updateStage) {// updating a major stage
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		ComboBox<String> comboBox = new ComboBox<>();// storing major names to let the user pick from

		TextField txtfield2 = new TextField();
		TextField txtfield3 = new TextField();// textfields for new major name and other attributes
		TextField txtfield4 = new TextField();
		TextField txtfield5 = new TextField();

		Label title = new Label("Update Stage:");
		Label head = new Label("Change Major Details");
		Label currentName = new Label("Change Current Major Name From:");
		Label newName = new Label("New Major Name:");// labels different attributes
		Label Agrade = new Label("New Acceptance Grade:");
		Label Tweight = new Label("New Tawjihi Weighting:");
		Label Pweight = new Label("New Placement Test Weightings:");

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
		head.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
		currentName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles
		newName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		Agrade.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		Tweight.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		Pweight.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

		MNode current = majorList.getFront();
		while (current != null) {// looping to add all major names to the comboBox
			comboBox.getItems().add(current.getElement().getMajorName());
			current = current.getNext();
		}

		Button update = new Button("Update");// updating button
		update.setPrefHeight(50);
		update.setPrefWidth(75);
		update.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		update.setOnAction(e -> {
			String oldMajorName = comboBox.getValue();// storing old + new major names
			String newMajorName = txtfield2.getText().trim();
			if (newMajorName.isEmpty()) {
				newMajorName = oldMajorName;
			}
			try {
				int acceptanceGrade = 0;// initializing all attributes as null / zero for if the user decides to update
										// only one or more attributes
				double tawjihiWeight = 0;
				double placementTestWeight = 0;
				if (txtfield3.getText().isEmpty()) {// checking validity
					acceptanceGrade = majorList.getNode(oldMajorName).getElement().getAcceptanceGrade();
				} else {
					acceptanceGrade = Integer.parseInt(txtfield3.getText().trim());
				}
				if (txtfield4.getText().isEmpty()) {// checking validity
					tawjihiWeight = majorList.getNode(oldMajorName).getElement().getAcceptanceGrade();
				} else {
					tawjihiWeight = Double.parseDouble(txtfield4.getText().trim());
				}
				if (txtfield5.getText().isEmpty()) {// checking validity
					placementTestWeight = majorList.getNode(oldMajorName).getElement().getPlacementTestWeight();
				} else {
					placementTestWeight = Double.parseDouble(txtfield5.getText().trim());
				}
				if ((!newMajorName.matches("[a-zA-Z\\s]+"))) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Name");//// checking validity beacause new major name cannot contain
														//// numbers!
					alert.setContentText("Major Name cannot have numbers");
					alert.showAndWait();
					return;
				}
				Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);
				updateAlert.setHeaderText("Update");// alert to prevent accidental operations
				updateAlert.setContentText("Are you sure you want to update information?");

				Optional<ButtonType> result = updateAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					majorList.UpdateMajor(oldMajorName, newMajorName, acceptanceGrade, tawjihiWeight, // updating new
																										// major
																										// attributes
							placementTestWeight);
					Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
					successAlert.setHeaderText("Success");
					successAlert.setContentText("Major information Was Updated successfully");
					successAlert.showAndWait();// success alert
					return;
				} else {
					Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
					cancelAlert.setHeaderText("Cancelled");// cancelled update alert
					cancelAlert.setContentText("Major information was not updated!");
					cancelAlert.showAndWait();
				}
			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);// back button for previous stage
		back.setPrefWidth(75);
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Major(updateStage);
		});

		Scene scene = new Scene(root, 850, 500);// width + height
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);// bg image
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.add(head, 2, 1);// adding all implementation to the fx scene
		root.addRow(5, currentName, comboBox);
		root.addRow(6, newName, txtfield2);
		root.addRow(7, Agrade, txtfield3);
		root.addRow(8, Tweight, txtfield4);
		root.addRow(9, Pweight, txtfield5);
		root.addRow(10, update);
		root.addRow(45, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updateStage.setScene(scene);
		updateStage.setTitle("Update Major");
		updateStage.show();// title of the scene
	}

	@SuppressWarnings({ "unchecked", "exports" })
	public void SearchMajor(Stage searchStage) {
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);
		// Create a new TableView for displaying Major objects
		TableView<Major> tableView = new TableView<>();

		// Create a column for Major Name
		TableColumn<Major, String> majorNameColumn = new TableColumn<>("Major Name");
		majorNameColumn.setCellValueFactory(new PropertyValueFactory<>("majorName"));
		majorNameColumn.setPrefWidth(100);

		// Create a column for Acceptance Grade
		TableColumn<Major, Integer> acceptanceGradeColumn = new TableColumn<>("Acceptance Grade");
		acceptanceGradeColumn.setCellValueFactory(new PropertyValueFactory<>("acceptanceGrade"));

		acceptanceGradeColumn.setPrefWidth(130);

		// Create a column for Tawjihi Weight
		TableColumn<Major, Double> tawjihiWeightColumn = new TableColumn<>("Tawjihi Weight");
		tawjihiWeightColumn.setCellValueFactory(new PropertyValueFactory<>("tawjihiWeight"));

		tawjihiWeightColumn.setPrefWidth(100);

		// Create a column for Placement Test Weight
		TableColumn<Major, Double> placementTestColumn = new TableColumn<>("Placement Test Weight");
		placementTestColumn.setCellValueFactory(new PropertyValueFactory<>("placementTestWeight"));

		placementTestColumn.setPrefWidth(150);

		// Add all columns to the TableView
		tableView.getColumns().addAll(majorNameColumn, acceptanceGradeColumn, tawjihiWeightColumn, placementTestColumn);

		TextField txtfield = new TextField();

		Label title = new Label("Search Stage");// title of the scene
		Label label1 = new Label("Major Name To Search:");

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");// style
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// style

		Button search = new Button("Search");// search button
		search.setPrefHeight(50);
		search.setPrefWidth(75);
		search.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		search.setOnAction(e -> {
			String majorName = txtfield.getText().trim();// returning major name value from the text field
			if (majorName.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);// making sure textfield isnt empty
				alert.setHeaderText("Empty field");
				alert.setContentText("Major Name cannot be empty");
				alert.showAndWait();
				return;
			}
			if (!majorName.matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Name");// checking that major name doesnt have numbers
				alert.setContentText("Major Name cannot have numbers");
				alert.showAndWait();
				return;
			}
			tableView.getItems().clear();// clearing the tableview
			majorList.newSearch(majorName, tableView);
			if (tableView.getItems().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// alert error type to display that the letter is not in any major
				alert.setContentText("Letter is not in any major");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Displayed successfully");// success alert
				alert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// back to previous stage
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Major(searchStage);
		});

		Scene scene = new Scene(root, 850, 500);
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);
		root.addRow(3, label1, txtfield);// adding everything to the fx scene
		root.addRow(4, search);
		root.add(tableView, 2, 8);
		root.addRow(5, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		searchStage.setScene(scene);
		searchStage.setTitle("Search Major");// search major title scene
		searchStage.show();
	}

	@SuppressWarnings({ "unchecked", "exports" })
	public void Student(Stage studentStage) {// student management scene
		try {
			if (majorList == null) {
				majorList = new MajorLinkedList();
			}
			if (studentsList == null) {
				studentsList = new StudentLinkedList();
			}

			BorderPane root = new BorderPane();
			TableView<Student> tableView = new TableView<>(); // creating a table view for student

			// student ID column
			TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
			idColumn.setPrefWidth(50);
			// student name column
			TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
			nameColumn.setPrefWidth(100);
			// tawjihi grade column
			TableColumn<Student, Integer> tawjihiColumn = new TableColumn<>("Tawjihi Grade");
			tawjihiColumn.setCellValueFactory(new PropertyValueFactory<>("tawjihiGrade"));
			tawjihiColumn.setPrefWidth(140);
			// placement test grade column
			TableColumn<Student, Integer> placementTestColumn = new TableColumn<>("Placement Test Grade");
			placementTestColumn.setCellValueFactory(new PropertyValueFactory<>("placementTestGrade"));
			placementTestColumn.setPrefWidth(140);
			// admission mark column
			TableColumn<Student, Double> admissionColumn = new TableColumn<>("Admission Mark");
			admissionColumn.setCellValueFactory(new PropertyValueFactory<>("admissionMark"));
			admissionColumn.setPrefWidth(100);
			// chosen major column
			TableColumn<Student, String> majorColumn = new TableColumn<>("Chosen Major");
			majorColumn.setCellValueFactory(new PropertyValueFactory<>("chosenMajor"));
			majorColumn.setPrefWidth(150);
			// adding all columns to the table view
			tableView.getColumns().addAll(idColumn, nameColumn, tawjihiColumn, placementTestColumn, admissionColumn,
					majorColumn);

			root.setCenter(tableView);

			Button back = new Button("Back");
			back.setPrefHeight(50);
			back.setPrefWidth(100);// back button to previous stage
			back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			back.setOnAction(e -> {
				start(studentStage);
			});

			Button read = new Button("Read from file");
			read.setPrefHeight(50);// reading from file and saving data to the single linked list
			read.setPrefWidth(140);
			read.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			read.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(studentStage);

				if (file != null) {
					try (Scanner scanner = new Scanner(file)) {
						if (scanner.hasNextLine()) {
							scanner.nextLine();// skipping the first line
						}
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine().trim();
							String[] parts = line.split(":");

							if (parts.length == 5) {// array to store file parts and split them
								try {
									int studentID = Integer.parseInt(parts[0].trim());
									String studentName = parts[1].trim();
									int tawjihiGrade = Integer.parseInt(parts[2].trim());
									int placementTestGrade = Integer.parseInt(parts[3].trim());
									String chosenMajor = parts[4].trim();
									if (majorList.searchMajor(chosenMajor)) {// searching if the major exists then
																				// returning the coordinated node and
																				// then inserting the students in that
																				// major node
										mnode = majorList.getNode(chosenMajor);
										mnode.getStudentsList().InsertStudent(studentID, studentName, tawjihiGrade,
												placementTestGrade, chosenMajor);
										SNode snode = mnode.getStudentsList().getFront();
										while (snode != null) {// countering accepted or rejected based on acceptance
																// grade and admission mark
											if (mnode.getElement().getAcceptanceGrade() > snode.getElement()
													.getAdmissionMark()) {

												snode.getElement().setChosenMajor(
														mnode.getElement().getMajorName() + snode.getElement()
																.status(mnode.getElement().getAcceptanceGrade()));
												rejected++;
											} else {
												snode.getElement().setChosenMajor(
														mnode.getElement().getMajorName() + snode.getElement()
																.status(mnode.getElement().getAcceptanceGrade()));
												accepted++;
											}
											snode = snode.getNext();
										}
									}
								} catch (NumberFormatException ex) {
									ex.printStackTrace();
								}
							} else {
								Alert readData = new Alert(Alert.AlertType.ERROR);
								readData.setHeaderText("Error!");// alert type error if no file was selected
								readData.setContentText("Pick a file");
								readData.showAndWait();
							}
						}

						tableView.getItems().clear();
						MNode mnode = majorList.getFront();
						while (mnode != null) {
							SNode snode = mnode.getStudentsList().getFront();
							while (snode != null) {// adding the data to the table view
								Student student = snode.getElement();
								tableView.getItems().add(student);
								snode = snode.getNext();
							}
							mnode = mnode.getNext();
						}

						Alert readData = new Alert(Alert.AlertType.INFORMATION);// alert type information which is a
																				// success alert
						readData.setHeaderText("Success!");
						readData.setContentText("Data was read and saved successfully.");
						readData.showAndWait();

					} catch (FileNotFoundException e1) {//
						e1.printStackTrace();// alert type if file wasnt found
						Alert fileNotFoundAlert = new Alert(Alert.AlertType.ERROR);
						fileNotFoundAlert.setHeaderText("File Not Found");
						fileNotFoundAlert.setContentText("The specified file could not be found. Please try again.");
						fileNotFoundAlert.showAndWait();
					}
				} else {

				}
			});

			Button reload = new Button("Re-Load");
			reload.setPrefHeight(50);
			reload.setPrefWidth(100);// reload buttong to reload all editted data back to the table view
			reload.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			reload.setOnAction(e -> {
				MNode mnode = majorList.getFront();
				while (mnode != null) {
					StudentLinkedList studentlist = mnode.getStudentsList();
					SNode snode = studentlist.getFront();
					while (snode != null) {
						Student student = snode.getElement();
						tableView.getItems().add(student);// traverse in major nodes and student nodes then appending in
															// table view
						snode = snode.getNext();
					}
					mnode = mnode.getNext();
				}

			});

			Button insert = new Button("Insert");// insert student scene
			insert.setPrefHeight(50);
			insert.setPrefWidth(100);
			insert.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			insert.setOnAction(e -> {
				InsertStudentStage(studentStage);
			});

			Button delete = new Button("Delete");// delete student scene
			delete.setPrefHeight(50);
			delete.setPrefWidth(100);
			delete.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			delete.setOnAction(e -> {
				DeleteStudentStage(studentStage);
			});

			Button update = new Button("Update");// update student scene
			update.setPrefHeight(50);
			update.setPrefWidth(100);
			update.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			update.setOnAction(e -> {
				UpdateStudentStage(studentStage);
			});

			Button search = new Button("Search");// search student scene
			search.setPrefHeight(50);
			search.setPrefWidth(100);
			search.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			search.setOnAction(e -> {
				SearchStudent(studentStage);
			});

			Button alterMajor = new Button("Change Major");// change student's rejected major scene
			alterMajor.setPrefHeight(50);
			alterMajor.setPrefWidth(170);
			alterMajor.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
			alterMajor.setOnAction(e -> {
				ChangeMajor(studentStage);
			});

			HBox buttonBox = new HBox(10);// hbox to store all buttons
			buttonBox.getChildren().addAll(back, read, reload, insert, delete, update, search, alterMajor);
			buttonBox.setAlignment(Pos.CENTER);
			root.setBottom(buttonBox);

			Scene scene = new Scene(root, 950, 500);
			Image backgroundImage = new Image(getClass().getResource("bgmajor.jpg").toExternalForm());
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			root.setBackground(new Background(bgImage));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			studentStage.setScene(scene);
			studentStage.setTitle("Student System");
			studentStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("exports")
	public void ChangeMajor(Stage changeMajor) {// changing the major of a rejected student's chosen major
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(8);

		ComboBox<String> comboBox = new ComboBox<>();// combo box to store students id
		ComboBox<String> comboBox2 = new ComboBox<>();// combo box to store majors that the user can choose from

		Button confirm = new Button("Confirm");
		confirm.setPrefHeight(50);// confirmation major button
		confirm.setPrefWidth(100);
		confirm.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style

		MNode majorNode = majorList.getFront();
		while (majorNode != null) {
			SNode studentNode = majorNode.getStudentsList().getFront();
			while (studentNode != null) {
				if (studentNode.getElement().status(mnode.getElement().getAcceptanceGrade())
						.equalsIgnoreCase("-rejected")) {// displying only rejected students in the combo box
					comboBox.getItems().add(String.valueOf(studentNode.getElement().getStudentID()));// converting from
																										// int to string

				}
				studentNode = studentNode.getNext();// moving to next student
			}
			majorNode = majorNode.getNext();// moving to next major
		}
		VBox vbox = new VBox();
		vbox.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");

		Button show = new Button("Show alternative majors");// displaying alternative majors the the user can pick from
		show.setPrefHeight(50);
		show.setPrefWidth(200);
		show.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
		show.setOnAction(e -> {
			if (comboBox.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");// making sure student picked thier ID
				alert.setContentText("Please pick your studnet ID!");
				alert.showAndWait();
				return;
			}
			int studentID = Integer.parseInt(comboBox.getValue().trim());
			MNode mnode = majorList.getFront();// declarying mnode as front
			double admissionMark = 0.0;
			boolean find = false;

			while (mnode != null) {
				Major major = mnode.getElement();
				SNode snode = mnode.getStudentsList().getFront();
				while (snode != null) {// traversing to find student id that matches student id that was given
					if (snode.getElement().getStudentID() == studentID) {
						int tawjihiGrade = snode.getElement().getTawjihiGrade();
						int placementTestGrade = snode.getElement().getPlacementTestGrade();

						admissionMark = tawjihiGrade * major.getTawjihiWeight()// calculating admission mark
								+ placementTestGrade * major.getPlacementTestWeight();
						find = true;// boolean flag to stop traverse
						break;
					}
					snode = snode.getNext();
				}
				if (find) {// displaying alternative majors in comoxbox2
					if (admissionMark > major.getAcceptanceGrade()) {
						comboBox2.getItems().add(major.getMajorName());
					}
				}
				mnode = mnode.getNext();
			}
			confirm.setOnAction(e2 -> {// confirmation that the user wants to change their major
				String chosenMajor = comboBox2.getValue();
				MNode mnode2 = majorList.getFront();
				while (mnode2 != null) {
					SNode snode2 = mnode2.getStudentsList().getFront();
					while (snode2 != null) {
						if (snode2.getElement().getStudentID() == studentID) {
							String name = snode2.getElement().getStudentName();
							int tg = snode2.getElement().getTawjihiGrade();
							int ptg = snode2.getElement().getPlacementTestGrade();
							MNode mnode3 = majorList.getNode(chosenMajor);
							mnode2.getStudentsList().DeleteStudent(studentID);
							mnode3.getStudentsList().InsertStudent(studentID, name, tg, ptg, chosenMajor);
							accepted++;// incrementing counter

						}
						snode2 = snode2.getNext();
					}
					mnode2 = mnode2.getNext();
				}
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Major changed successfully");
				alert.showAndWait();

			});
			vbox.getChildren().addAll(comboBox2, confirm);
			root.addRow(5, vbox);
		});

		comboBox.setPrefHeight(30);
		comboBox.setPrefWidth(260);

		comboBox2.setPrefHeight(30);
		comboBox2.setPrefWidth(260);

		HBox hbox = new HBox();

		Label title = new Label("Alternative Major Recommendation:");
		Label label1 = new Label("Pick your student ID:");// titles + labels
		hbox.getChildren().addAll(label1, comboBox);
		Label label2 = new Label("Pick your new major:");

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles
		label2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		comboBox.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");
		comboBox2.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// to go to previous stages
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Student(changeMajor);
		});
		Scene scene = new Scene(root, 700, 500);

		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, // background image
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.setBackground(new Background(bgImage));
		root.add(title, 0, 0);// adding all to fx scene
		root.addRow(2, hbox);
		root.addRow(3, show);
		root.addRow(22, back);
		changeMajor.setScene(scene);
		changeMajor.setTitle("Update Student's Major");
		changeMajor.show();// scene title

	}

	@SuppressWarnings({ "exports", "unchecked" })
	public void InsertStudentStage(Stage insertStudent) {
		GridPane root = new GridPane();// inserting student stage
		root.setHgap(8);
		root.setVgap(8);

		TableView<Major> tableView = new TableView<>();
		// table view of major class and its attributes then adding columns to the table
		// view
		TableColumn<Major, String> majorNameColumn = new TableColumn<>("Major Name");
		majorNameColumn.setCellValueFactory(new PropertyValueFactory<>("majorName"));
		majorNameColumn.setPrefWidth(100);

		TableColumn<Major, Double> tawjihiWeightColumn = new TableColumn<>("Tawjihi Weight");
		tawjihiWeightColumn.setCellValueFactory(new PropertyValueFactory<>("tawjihiWeight"));
		tawjihiWeightColumn.setPrefWidth(100);

		TableColumn<Major, Double> placementTestColumn = new TableColumn<>("Placement Test Weight");
		placementTestColumn.setCellValueFactory(new PropertyValueFactory<>("placementTestWeight"));
		placementTestColumn.setPrefWidth(150);

		TableColumn<Major, Integer> acceptanceGradeColumn = new TableColumn<>("Acceptance Grade");
		acceptanceGradeColumn.setCellValueFactory(new PropertyValueFactory<>("acceptanceGrade"));
		acceptanceGradeColumn.setPrefWidth(130);

		tableView.getColumns().addAll(majorNameColumn, tawjihiWeightColumn, placementTestColumn, acceptanceGradeColumn);

		ComboBox<String> comboBox = new ComboBox<>();// two combo boxes for major functionalities
		ComboBox<String> comboBox2 = new ComboBox<>();

		comboBox.getItems().add("Initial Major Suggestion");

		comboBox.setPrefHeight(30);
		comboBox.setPrefWidth(260);

		comboBox2.setPrefHeight(30);
		comboBox2.setPrefWidth(260);

		TextField txtfield1 = new TextField();
		TextField txtfield2 = new TextField();// textfields for insertion
		TextField txtfield3 = new TextField();
		TextField txtfield4 = new TextField();
		HBox hbox6 = new HBox();

		Label title = new Label("Insertion Stage:");
		Label label1 = new Label("Student ID:");// labels to clarify what to insert
		Label label2 = new Label("Student Name:");
		Label label3 = new Label("Tawjihi Grade:");
		Label label4 = new Label("Placement Test Grade:");
		Label label5 = new Label("Major:");

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		label2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		label3.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles
		label4.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		label5.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		comboBox.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");
		comboBox2.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");

		Button calculate = new Button("Calculate Admission Mark!");
		calculate.setPrefHeight(50);// calculate admission mark button
		calculate.setPrefWidth(220);
		calculate.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style

		Label result = new Label();// result label that prints admission mark calculation
		hbox6.getChildren().addAll(calculate, result);
		calculate.setOnAction(e -> {
			String majorFunction = comboBox.getValue();
			if (majorFunction == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Please pick a major functionality!");
				alert.showAndWait();
				return;
			}
			if (comboBox.getValue().equalsIgnoreCase("Initial Major Suggestion")) {
				try {
					int tawjihiGrade = Integer.parseInt(txtfield3.getText().trim());
					int placementTestGrade = Integer.parseInt(txtfield4.getText().trim());
					Double admissionMark = 0.0;
					MNode current = majorList.getFront();// traversing to find and print the majors that the student can
															// choose and pick from
					while (current != null) {
						Major major = current.getElement();
						admissionMark = tawjihiGrade * major.getTawjihiWeight()// calculating admission mark in all
																				// majors
								+ placementTestGrade * major.getPlacementTestWeight();
						if (major.getAcceptanceGrade() < admissionMark) {// adding majors to combo box
							tableView.getItems().add(major);
							comboBox2.getItems().add(major.getMajorName());
							accepted++;
						}
						current = current.getNext();
					}
					root.addRow(18, comboBox2);
					result.setText("Your Admission Mark is:" + admissionMark);
					result.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
					return;// printing admission mark
				} catch (NumberFormatException e3) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Empty Field");
					alert.setContentText("Invalid input");// error for validity
					alert.showAndWait();
				}
			}

		});

		Button insert = new Button("Confirm");// confirmation insertion button!
		insert.setPrefHeight(50);
		insert.setPrefWidth(135);
		insert.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// style
		insert.setOnAction(e -> {
			String studentName = txtfield2.getText().trim();// storing student name
			if (studentName.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty Field");
				alert.setContentText("Student Name cannot be empty");// alert to make sure Student name isnt empty
				alert.showAndWait();
				return;
			}
			try {// saving all data + attributes
				int studentID = Integer.parseInt(txtfield1.getText().trim());
				int tawjihiGrade = Integer.parseInt(txtfield3.getText().trim());
				int placementTestGrade = Integer.parseInt(txtfield4.getText().trim());
				if (inValidAG(tawjihiGrade) || inValidAG(placementTestGrade)) {// checking validity with alerts for each
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Number");
					alert.setContentText(
							"Tawjihi Grade / Placement Test Grade should be less than 100 and greater than 0");
					alert.showAndWait();
					return;
				}
				if (!studentName.matches("[a-zA-Z\\s]+")) {// making sure name doesnt have numbers
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Invalid Name");
					alert.setContentText("Student Name / Chosen Major cannot have numbers");
					alert.showAndWait();
					return;
				} // inserting the student in the major node
				MNode mnode = majorList.getNode(comboBox2.getValue().trim());
				mnode.getStudentsList().InsertStudent(studentID, studentName, tawjihiGrade, placementTestGrade,
						comboBox2.getValue());

				Alert insertedAlert = new Alert(Alert.AlertType.INFORMATION);
				insertedAlert.setHeaderText("Inserted");
				insertedAlert.setContentText("Student was inserted sucessfully");// sucess alert
				insertedAlert.showAndWait();
			} catch (NumberFormatException e2) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Invalid Input");// checking validity
				alert.setContentText("Empty textfield(s)");
				alert.showAndWait();
				return;
			}

		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// to go back to previous stage
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Student(insertStudent);
		});
		Scene scene = new Scene(root, 800, 700);

		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		HBox hbox1 = new HBox();// making different hboxe for all implementations
		hbox1.getChildren().addAll(label1, txtfield1);
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(label2, txtfield2);
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(label3, txtfield3);
		HBox hbox4 = new HBox();
		hbox4.getChildren().addAll(label4, txtfield4);
		HBox hbox5 = new HBox();
		hbox5.getChildren().addAll(label5, comboBox);

		root.setBackground(new Background(bgImage));
		root.add(title, 0, 0);
		root.addRow(6, hbox1);
		root.addRow(7, hbox2);
		root.addRow(8, hbox3);
		root.addRow(9, hbox4);
		root.addRow(10, hbox5);// adding everything to the fx scene
		root.addRow(11, hbox6);
		root.addRow(12, insert);
		root.addRow(18, tableView);
		root.addRow(22, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		insertStudent.setScene(scene);// scene title
		insertStudent.setTitle("Insert Student");
		insertStudent.show();
	}

	@SuppressWarnings("exports")
	public void DeleteStudentStage(Stage deleteStudent) {// deleting a student stage
		GridPane root = new GridPane();
		root.setHgap(8);
		root.setVgap(5);

		Label title = new Label("Delete Stage:");
		Label label1 = new Label("Student ID:");

		ComboBox<String> comboBox = new ComboBox<>();// combo box to give student id

		MNode majorNode = majorList.getFront();
		while (majorNode != null) {// traversing to find the major needed
			SNode studentNode = majorNode.getStudentsList().getFront();
			while (studentNode != null) {
				comboBox.getItems().add(String.valueOf(studentNode.getElement().getStudentID()));
				studentNode = studentNode.getNext();
			}
			majorNode = majorNode.getNext();
		}

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles
		comboBox.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

		Button delete = new Button("Delete");
		delete.setPrefHeight(50);
		delete.setPrefWidth(75);// deleting style
		delete.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		delete.setOnAction(e -> {
			String id = comboBox.getValue();// saving the id
			if (id.isEmpty()) {
				Alert failureAlert = new Alert(Alert.AlertType.ERROR);
				failureAlert.setHeaderText("Error");// alert type error if is combo box is empty or returns null
				failureAlert.setContentText("Pick an ID");
				failureAlert.showAndWait();
				return;
			}
			int studentID = Integer.parseInt(id);
			Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
			deleteAlert.setHeaderText("Deletion");
			deleteAlert.setContentText("Are you sure you want to delete this student?");// conformation for accidental
																						// deletions

			Optional<ButtonType> result = deleteAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				MNode mnode2 = majorList.getFront();// conformation to delete
				while (mnode2 != null) {// traversing to find the major node and student node
					SNode snode2 = mnode2.getStudentsList().getFront();
					while (snode2 != null) {// deleting the student from the major
						if (snode2.getElement().getStudentID() == studentID) {
							mnode2.getStudentsList().DeleteStudent(studentID);
						}
						snode2 = snode2.getNext();
					}
					mnode2 = mnode2.getNext();
				}
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setHeaderText("Success");// alert type success
				successAlert.setContentText("Student was deleted successfully");
				successAlert.showAndWait();
			} else {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");// alert type cancelled
				cancelAlert.setContentText("Student was not deleted (cancelled)");
				cancelAlert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// back to previous stage
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Student(deleteStudent);
		});

		Scene scene = new Scene(root, 800, 500);
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		root.setBackground(new Background(bgImage));
		root.add(title, 10, 0);// back ground image
		root.addRow(6, label1, comboBox);
		root.addRow(8, delete);
		root.addRow(70, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		deleteStudent.setScene(scene);
		deleteStudent.setTitle("Delete Student");
		deleteStudent.show();
	}

	@SuppressWarnings("exports")
	public void UpdateStudentStage(Stage updateStudent) {
		GridPane root = new GridPane(); // Updating student stage
		root.setHgap(8);
		root.setVgap(5);

		TextField txtfield1 = new TextField(); // text fields for all attributes
		TextField txtfield2 = new TextField();
		TextField txtfield3 = new TextField();
		ComboBox<String> comboBox = new ComboBox<>(); // comboboxo for selecting student ID

		Label title = new Label("Update Stage:");
		Label head = new Label("Change Student Details");
		Label sID = new Label("Choose Student based on student's ID");
		Label newName = new Label("New Student Name:");
		Label Tgrade = new Label("New Tawjihi Grade:");
		Label Pgrade = new Label("New Placement Test Grade:");

		MNode majorNode = majorList.getFront();
		while (majorNode != null) {
			SNode studentNode = majorNode.getStudentsList().getFront();
			while (studentNode != null) {
				comboBox.getItems().add(String.valueOf(studentNode.getElement().getStudentID()));
				studentNode = studentNode.getNext();
			}
			majorNode = majorNode.getNext();
		}

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
		head.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
		sID.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles
		newName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		Pgrade.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		Tgrade.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

		Button update = new Button("Update");
		update.setPrefHeight(50);
		update.setPrefWidth(75);
		update.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// styles

		update.setOnAction(e -> {
			if (comboBox.getValue() == null) {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Error");
				cancelAlert.setContentText("Please pick a student ID");
				cancelAlert.showAndWait();// alert type error if no id was selected
				return;
			}

			int studentID = Integer.parseInt(comboBox.getValue());
			String newStudentName = txtfield1.getText().trim();// saving from textfields into variables
			String tawjihiGrade1 = txtfield2.getText().trim();
			String placementTestGrade1 = txtfield3.getText().trim();

			if (!newStudentName.isEmpty() && !newStudentName.matches("[a-zA-Z\\s]+")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);// alert type error to make sure
				alert.setHeaderText("Invalid Name");
				alert.setContentText("Student Name cannot have numbers");
				alert.showAndWait();
				return;
			}

			Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);
			updateAlert.setHeaderText("Update");
			updateAlert.setContentText("Are you sure you want to update this student?");// confimration to prevent
																						// accidental operations
			Optional<ButtonType> result = updateAlert.showAndWait();

			if (result.get() == ButtonType.OK) {
				MNode mnode2 = majorList.getFront();
				while (mnode2 != null) {
					SNode snode2 = mnode2.getStudentsList().getFront();
					while (snode2 != null) {
						if (snode2.getElement().getStudentID() == studentID) {// finding the student id wanted
							if (!newStudentName.isEmpty()) {// checking if new student if new name is empty
								snode2.getElement().setStudentName(newStudentName);
							}
							if (!tawjihiGrade1.isEmpty()) {
								int tawjihiGrade = Integer.parseInt(tawjihiGrade1);
								if (inValidAG(tawjihiGrade)) {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setHeaderText("Invalid Number");
									alert.setContentText("Tawjihi Grade should be less than 100 and greater than 0");
									alert.showAndWait();
									return;
								}
								snode2.getElement().setTawjihiGrade(tawjihiGrade);
							}
							if (!placementTestGrade1.isEmpty()) {
								int placementTestGrade = Integer.parseInt(placementTestGrade1);
								if (inValidAG(placementTestGrade)) {
									Alert alert = new Alert(Alert.AlertType.ERROR);// checking validatin
									alert.setHeaderText("Invalid Number");
									alert.setContentText(
											"Placement Test Grade should be less than 100 and greater than 0");
									alert.showAndWait();
									return;
								}
								snode2.getElement().setPlacementTestGrade(placementTestGrade);
							}
							Alert successAlert = new Alert(Alert.AlertType.INFORMATION);// alert type sucess!
							successAlert.setHeaderText("Success");
							successAlert.setContentText("Student was updated successfully");
							successAlert.showAndWait();
						}
						snode2 = snode2.getNext();// next student
					}
					mnode2 = mnode2.getNext();// next major
				}
			} else {
				Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
				cancelAlert.setHeaderText("Cancelled");
				cancelAlert.setContentText("Student was not updated (cancelled)");// alert type error - (accidental
																					// update)
				cancelAlert.showAndWait();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// previous stage
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Student(updateStudent);
		});

		Scene scene = new Scene(root, 850, 500);
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		root.setBackground(new Background(bgImage));
		root.add(title, 2, 0);// adding all to fx scene
		root.add(head, 2, 1);
		root.addRow(5, sID, comboBox);
		root.addRow(6, newName, txtfield1);
		root.addRow(7, Tgrade, txtfield2);
		root.addRow(8, Pgrade, txtfield3);
		root.addRow(10, update);
		root.addRow(45, back);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updateStudent.setScene(scene);
		updateStudent.setTitle("Update Student");
		updateStudent.show();
	}

	@SuppressWarnings({ "exports", "unchecked" })
	public void SearchStudent(Stage searchStudent) {
		GridPane root = new GridPane();
		root.setHgap(8);// searching tudent stage based on student ID
		root.setVgap(5);

		TableView<Student> tableView = new TableView<>();
		// table view of student and adding columns to the table view
		TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		idColumn.setPrefWidth(50);

		TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
		nameColumn.setPrefWidth(100);

		TableColumn<Student, Integer> tawjihiColumn = new TableColumn<>("Tawjihi Grade");
		tawjihiColumn.setCellValueFactory(new PropertyValueFactory<>("tawjihiGrade"));
		tawjihiColumn.setPrefWidth(140);

		TableColumn<Student, Integer> placementTestColumn = new TableColumn<>("Placement Test Grade");
		placementTestColumn.setCellValueFactory(new PropertyValueFactory<>("placementTestGrade"));
		placementTestColumn.setPrefWidth(140);

		TableColumn<Student, Double> admissionColumn = new TableColumn<>("Admission Mark");
		admissionColumn.setCellValueFactory(new PropertyValueFactory<>("admissionMark"));
		admissionColumn.setPrefWidth(100);

		TableColumn<Student, String> majorColumn = new TableColumn<>("Chosen Major");
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("chosenMajor"));
		majorColumn.setPrefWidth(100);

		tableView.getColumns().addAll(idColumn, nameColumn, tawjihiColumn, placementTestColumn, admissionColumn,
				majorColumn);

		TextField txtfield = new TextField();

		Label title = new Label("Search Stage");
		Label label1 = new Label("Student ID To Search:");

		title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: white;");
		label1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles

		Button search = new Button("Search");
		search.setPrefHeight(50);// search button
		search.setPrefWidth(75);
		search.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		search.setOnAction(e -> {

			String sID = txtfield.getText().trim();
			if (sID.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Empty field");
				alert.setContentText("Student ID cannot be empty");// making sure student ID to be searched isnt
																	// empty
				alert.showAndWait();
				return;
			}
			try {
				int studentID = Integer.parseInt(sID);
				tableView.getItems().clear();
				MNode mnode2 = majorList.getFront();// traversing through majors to find the student
				while (mnode2 != null) {
					SNode snode2 = mnode2.getStudentsList().getFront();// traversing throught students to find
																		// specificed student's ID
					while (snode2 != null) {
						if (snode2.getElement().getStudentID() == studentID) {
							tableView.getItems().add(snode2.getElement());// appending to table view
						}
						snode2 = snode2.getNext();// next student
					}
					mnode2 = mnode2.getNext();// next major
				}
				if (tableView.getItems().isEmpty()) {
					Alert alert2 = new Alert(Alert.AlertType.ERROR);
					alert2.setHeaderText("Error");// alert type error if student doesnt exist
					alert2.setContentText("Student does not exist");
					alert2.showAndWait();
				} else {
					Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
					alert3.setHeaderText("Success");// alert type succuess + appended on table view + displayed
													// successfully
					alert3.setContentText("Student exists - displayed successfully");
					alert3.showAndWait();
				}

			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}
		});

		Button back = new Button("Back");
		back.setPrefHeight(50);
		back.setPrefWidth(75);// back to previous stage
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setOnAction(e -> {
			Student(searchStudent);
		});

		Scene scene = new Scene(root, 850, 500);
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		HBox hbox = new HBox();
		hbox.getChildren().addAll(label1, txtfield);

		root.setBackground(new Background(bgImage));
		root.add(title, 0, 0);
		root.addRow(3, hbox);
		root.addRow(4, search);
		root.addRow(7, tableView);
		root.addRow(10, back);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		searchStudent.setScene(scene);
		searchStudent.setTitle("Search Student");
		searchStudent.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public int acceptedStudents() {
		MNode mnode = majorList.getFront();
		while (mnode != null) {// getting all accepted studennts from all majors!
			StudentLinkedList studentlist = mnode.getStudentsList();
			SNode snode = studentlist.getFront();
			while (snode != null) {
				if (snode.getElement().getAdmissionMark() > mnode.getElement().getAcceptanceGrade()) {
					accepted++;// incrementing accepted students counter
				}
				// if admission mark is greater than the acceptance grade then the student was
				// accepted into their chosen major
				snode = snode.getNext();
			}
			mnode = mnode.getNext();
		}
		return accepted;// returning accepted students counter
	}

	public int rejectedStudents() {
		MNode mnode = majorList.getFront();
		while (mnode != null) {
			StudentLinkedList studentlist = mnode.getStudentsList();
			SNode snode = studentlist.getFront();
			while (snode != null) {
				if (snode.getElement().getAdmissionMark() < mnode.getElement().getAcceptanceGrade()) {
					rejected++;// incrementing rejected students counter
				}
				snode = snode.getNext();
			}
			// if admission mark is smaller than the acceptance grade then the student was
			// rejected into their chosen major
			mnode = mnode.getNext();
		}
		return rejected;// returning counter
	}

	public int evaulatedStudents() {// accepted + rejected combined is the number of all students!
		return accepted + rejected;
	}

	public String acceptanceRate() {// acceptance rate across all majors
		if (evaulatedStudents() == 0) {
			return "0.00";
		} else {
			return String.format("%.2f", ((double) acceptedStudents() / evaulatedStudents()) * 100);
		}
	}

	@SuppressWarnings({ "exports", "unchecked" })
	public void stats(Stage statsStage) {
		BorderPane root = new BorderPane();// statistics stage

		Label track = new Label("Tracking and reportings of various statistics: ");
		Label accAllMajor = new Label("");
		Label rejAllMajor = new Label("");
		Label evaluated = new Label("");
		Label accRate = new Label("");
		Button show = new Button("Show Statistics");
		ComboBox<String> comboBox = new ComboBox<>();

		Button back = new Button("Back");
		comboBox.getItems().addAll("Overall Major Information!");
		comboBox.getItems().addAll("Seperate Major Information!");// adding different statistics items to the combo box
		comboBox.getItems().addAll("Specfic Major Information!");
		comboBox.setPrefHeight(30);
		comboBox.setPrefWidth(260);

		ComboBox<String> comboBox2 = new ComboBox<>();

		Button get = new Button("");
		TextField txtfield = new TextField();

		comboBox.setPrefHeight(30);
		comboBox.setPrefWidth(260);

		VBox vbox = new VBox(8);
		vbox.getChildren().addAll(comboBox, show);

		VBox vbox2 = new VBox(8);

		track.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
		accAllMajor.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");// styles
		rejAllMajor.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		evaluated.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		accRate.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
		show.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		comboBox2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
		back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");

		show.setOnAction(e -> {
			String choice = comboBox.getValue();
			if (choice == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Error!");
				alert.setContentText("Please pick a type of major information!");
				alert.showAndWait();
				return;
			}
			vbox2.getChildren().clear();// displaying all major information across all majors!
			if (choice.equalsIgnoreCase("Overall Major Information!")) {
				vbox2.getChildren().addAll(accAllMajor, rejAllMajor, evaluated, accRate);
				// displying accepted students
				accAllMajor.setText("The total number of students accepted across all majors: " + acceptedStudents());
				// displying rejected students
				rejAllMajor.setText("The total number of students rejected across all majors: " + rejectedStudents());
				// displying evaluated number of students
				evaluated.setText("Total number of students evaluated: " + evaulatedStudents());
				// displying acceptance rate! students
				accRate.setText("Acceptance Rate: " + acceptanceRate());
			} else if (choice.equalsIgnoreCase("Seperate Major Information!")) {
				TableView<MajorTableView> tableView = new TableView<>();
				TableColumn<MajorTableView, String> majorColumn = new TableColumn<>("Major Name:");
				majorColumn.setCellValueFactory(new PropertyValueFactory<>("majorName"));
				majorColumn.setPrefWidth(150);

				TableColumn<MajorTableView, Integer> acceptedColumn = new TableColumn<>("Accepted Students");
				acceptedColumn.setCellValueFactory(new PropertyValueFactory<>("accepted"));
				acceptedColumn.setPrefWidth(150);

				TableColumn<MajorTableView, Integer> rejectedColumn = new TableColumn<>("Rejected Students");
				rejectedColumn.setCellValueFactory(new PropertyValueFactory<>("rejected"));
				rejectedColumn.setPrefWidth(150);

				// traversing and finding
				tableView.getColumns().addAll(majorColumn, acceptedColumn, rejectedColumn);
				MNode mnode2 = majorList.getFront();
				while (mnode2 != null) {
					int acceptedCount = 0;
					int rejectedCount = 0;
					SNode snode2 = mnode2.getStudentsList().getFront();
					while (snode2 != null) {
						if (snode2.getElement().getAdmissionMark() > mnode2.getElement().getAcceptanceGrade()) {
							acceptedCount++;
						} else if (snode2.getElement().getAdmissionMark() < mnode2.getElement().getAcceptanceGrade()) {
							rejectedCount++;
						}
						snode2 = snode2.getNext();
					} // appending all to table view
					tableView.getItems()
							.add(new MajorTableView(mnode2.getElement().getMajorName(), acceptedCount, rejectedCount));
					mnode2 = mnode2.getNext();

				}
				vbox2.getChildren().add(tableView);
			} else {
				vbox2.getChildren().clear();

				get.setText("Get Students!");

				HBox hbox = new HBox();// button to get the top N number of students in a specific major!

				Label number = new Label("Please Enter the number of students to display: ");
				number.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
				get.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");

				hbox.getChildren().addAll(comboBox2, number, txtfield);
				vbox2.getChildren().addAll(hbox, get);
				MNode mnode2 = majorList.getFront();
				while (mnode2 != null) {
					comboBox2.getItems().add(mnode2.getElement().getMajorName());
					mnode2 = mnode2.getNext();
				}
				get.setOnAction(e1 -> {
					String majorName = comboBox2.getValue().trim();
					int number2 = Integer.parseInt(txtfield.getText());
					MNode mnode = majorList.getNode(majorName);
					if (mnode != null) {
						SNode snode = mnode.getStudentsList().getFront();
						VBox vbox3 = new VBox(5);
						int count = 0;
						while (snode != null && count < number2) {// printing the students using a label!
							Label studentLabel = new Label("Student's ID: " + snode.getElement().getStudentID()
									+ " Student's Name: " + snode.getElement().getStudentName() + " Admission Mark: "
									+ snode.getElement().getAdmissionMark());
							vbox3.getChildren().add(studentLabel);
							studentLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: red;");
							snode = snode.getNext();
							count++;
						}
						vbox2.getChildren().clear();
						vbox2.getChildren().addAll(hbox, get, vbox3);
					}
				});
			}

		});

		VBox vbox3 = new VBox(8);
		vbox3.getChildren().addAll(vbox, vbox2);

		Scene scene = new Scene(root, 850, 420);
		Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());// bg image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		back.setPrefHeight(50);
		back.setPrefWidth(75);
		back.setOnAction(e -> start(statsStage));

		HBox hbox2 = new HBox();
		hbox2.getChildren().add(track);
		hbox2.setAlignment(Pos.TOP_CENTER);
		root.setTop(hbox2);
		root.setLeft(vbox3);
		root.setBottom(back);// statistics scene!
		root.setBackground(new Background(bgImage));
		statsStage.setScene(scene);
		statsStage.setTitle("Statistics and Reporting");
		statsStage.show();
	}

	@SuppressWarnings({ "exports", "unchecked" })
	public void NavigationStage(Stage navigate) {
		try {
			BorderPane root = new BorderPane();
			Label title = new Label("Navigation between majors and students!");
			Label majorName = new Label();

			// table view of students with its columns
			TableView<Student> tableView = new TableView<>();

			TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
			idColumn.setPrefWidth(50);

			TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
			nameColumn.setPrefWidth(100);

			TableColumn<Student, Integer> tawjihiColumn = new TableColumn<>("Tawjihi Grade");
			tawjihiColumn.setCellValueFactory(new PropertyValueFactory<>("tawjihiGrade"));
			tawjihiColumn.setPrefWidth(140);

			TableColumn<Student, Integer> placementTestColumn = new TableColumn<>("Placement Test Grade");
			placementTestColumn.setCellValueFactory(new PropertyValueFactory<>("placementTestGrade"));
			placementTestColumn.setPrefWidth(140);

			TableColumn<Student, Double> admissionColumn = new TableColumn<>("Admission Mark");
			admissionColumn.setCellValueFactory(new PropertyValueFactory<>("admissionMark"));
			admissionColumn.setPrefWidth(100);

			TableColumn<Student, String> majorColumn = new TableColumn<>("Chosen Major");
			majorColumn.setCellValueFactory(new PropertyValueFactory<>("chosenMajor"));
			majorColumn.setPrefWidth(100);

			tableView.getColumns().addAll(idColumn, nameColumn, tawjihiColumn, placementTestColumn, admissionColumn,
					majorColumn);

			Button back = new Button("Back");
			back.setPrefHeight(50);
			back.setPrefWidth(75);
			back.setOnAction(e -> start(navigate));

			MNode mnode = majorList.getFront();// to display first node's data as soon as the user enters
			majorName.setText(
					mnode.getElement().getMajorName() + " /Tawjihi Weight: " + mnode.getElement().getTawjihiWeight()
							+ " /Placemenet Test Weight: " + mnode.getElement().getPlacementTestWeight()
							+ " /Acceptance Grade: " + mnode.getElement().getAcceptanceGrade());
			if (mnode != null) {
				SNode snode = mnode.getStudentsList().getFront();
				while (snode != null) {
					tableView.getItems().add(snode.getElement());
					snode = snode.getNext();
				}
				majorList.setCurrent(mnode);
			}

			Button next = new Button("Next Major");
			next.setPrefHeight(50);
			next.setPrefWidth(150);
			next.setOnAction(e -> {
				MNode mnode2 = majorList.NavigateNext();// navigateing next to print major details
				if (mnode2 != null) {
					majorName.setText(mnode2.getElement().getMajorName() + " /Tawjihi Weight: "
							+ mnode2.getElement().getTawjihiWeight() + " /Placemenet Test Weight: "
							+ mnode2.getElement().getPlacementTestWeight() + " /Acceptance Grade: "
							+ mnode2.getElement().getAcceptanceGrade());
					tableView.getItems().clear();
					SNode snode = mnode2.getStudentsList().getFront();
					while (snode != null) {
						tableView.getItems().add(snode.getElement());
						snode = snode.getNext();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("ERROR");
					alert.setContentText("End of majors list");
					alert.showAndWait();
				}
			});

			Button prev = new Button("Previous Major");
			prev.setPrefHeight(50);
			prev.setPrefWidth(150);
			prev.setOnAction(e -> {
				MNode mnode3 = majorList.NavigatePrev();// navigating previous to print major details
				if (mnode3 != null) {
					majorName.setText(mnode3.getElement().getMajorName() + " /Tawjihi Weight: "
							+ mnode3.getElement().getTawjihiWeight() + " /Placemenet Test Weight: "
							+ mnode3.getElement().getPlacementTestWeight() + " /Acceptance Grade: "
							+ mnode3.getElement().getAcceptanceGrade());
					tableView.getItems().clear();
					SNode snode = mnode3.getStudentsList().getFront();
					while (snode != null) {
						tableView.getItems().add(snode.getElement());
						snode = snode.getNext();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);// alerting to show that there are no more majors
					alert.setHeaderText("ERROR");
					alert.setContentText("No more majors");
					alert.showAndWait();
				}
			});

			HBox hbox = new HBox();
			hbox.getChildren().addAll(back, prev, next);// storing all buttons

			VBox vbox = new VBox();
			vbox.setAlignment(Pos.TOP_CENTER);// vbox to store title and major name
			vbox.getChildren().addAll(title, majorName);

			title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
			majorName.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
			next.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");// styles
			back.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
			prev.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");

			root.setCenter(tableView);
			root.setTop(vbox);
			root.setBottom(hbox);
			hbox.setAlignment(Pos.BOTTOM_CENTER);

			Scene scene = new Scene(root, 850, 420);// back ground image
			Image backgroundImage = new Image(getClass().getResource("insertbg.jpg").toExternalForm());
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.setBackground(new Background(bgImage));
			navigate.setScene(scene);// navigation stage
			navigate.setTitle("Navigation between Majors");
			navigate.show();
		} catch (NullPointerException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("No majors loaded into the program!");
			alert.showAndWait();
		}

	}

	private boolean inValidAG(int acceptanceGrade) {// checking if the acceptance grade is valid or in valid
		if (acceptanceGrade < 0 || acceptanceGrade > 100) {
			return true;
		}
		return false;
	}

	private boolean inValidTW(double tawjihiWeight) {// checking validity
		if (tawjihiWeight < 0 || tawjihiWeight > 1) {
			return true;
		}
		return false;
	}

	private boolean inValidPT(double placementTestWeight) {// checking validity
		if (placementTestWeight < 0 || placementTestWeight > 1) {
			return true;
		}
		return false;
	}

	private boolean inValidMN(String majorName) {// checking validity
		MNode current = majorList.getFront();
		while (current != null) {
			if (current.getElement().getMajorName().equalsIgnoreCase(majorName)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	public static MajorLinkedList getMajorList() {
		return majorList;
	}

	@SuppressWarnings("static-access")
	public void setMajorList(MajorLinkedList majorList) {
		this.majorList = majorList;
	}

}
