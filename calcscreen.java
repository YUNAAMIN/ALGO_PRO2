package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class calcscreen extends BorderPane {// the screen that will show the result of the program
	private TextArea a; // TextArea to display the result of the program in it.
	private HuffmanTable[] huffmanTable;// create array of the huffman table
	private Decoding decoding;// invoke the decoding class
	private File selectedFile;// initialize the file that i will compressed

	public File getSelectedFile() {// getter method that will use it in other method to get the file that i worked
		// on to choose
		return selectedFile;// return the file
	}

	// private HuffmanCoding huffmanCoding;
	public calcscreen() throws IOException {// no argument constructor .
		huffmanTable = new HuffmanTable[256];// initialize huffman taple that 2^8=256 size of array
		decoding = new Decoding();// new decoding
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #000000");
		Label l1 = new Label("-Welcome to file compressed and decompressed system!");
		l1.setFont(new Font("Impact", 20));
		l1.setTextFill(Color.web("#FFFFFF"));
		Label l2 = new Label("-choose file that u want to do operation on it!");
		l2.setFont(new Font("Impact", 20));
		l2.setTextFill(Color.web("#FFFFFF"));
		HBox b = new HBox(10);
		b.setAlignment(Pos.CENTER);
		a = new TextArea();
		a.setEditable(false);
		a.setWrapText(true);
		a.setMaxWidth(600);
		Button dh = new Button("Decompressed");
		dh.setFont(new Font("Comic Sans MS", 15));
		dh.setStyle("-fx-background-color: #89CFF0");
		dh.setTextFill(Color.web("#FFFFFF"));
		Button calculate = new Button("Compress");
		calculate.setFont(new Font("Comic Sans MS", 15));
		calculate.setStyle("-fx-background-color: #89CFF0");
		calculate.setTextFill(Color.web("#FFFFFF"));
		Button Db = new Button("Display Table");
		Db.setFont(new Font("Comic Sans MS", 15));
		Db.setStyle("-fx-background-color: #89CFF0");
		Db.setTextFill(Color.web("#FFFFFF"));
		Button sl = new Button("Frequency for each char");
		sl.setFont(new Font("Comic Sans MS", 15));
		sl.setStyle("-fx-background-color: #89CFF0");
		sl.setTextFill(Color.web("#FFFFFF"));
		Button cp = new Button("Header");
		cp.setFont(new Font("Comic Sans MS", 15));
		cp.setStyle("-fx-background-color: #89CFF0");
		cp.setTextFill(Color.web("#FFFFFF"));
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(sl, calculate, dh, Db, cp);
		hb.setSpacing(10);
		hb.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
		Button file = new Button("File to Compress");
		file.setFont(new Font("Comic Sans MS", 15));
		file.setStyle("-fx-background-color: #89CFF0");
		file.setTextFill(Color.web("#FFFFFF"));
		HBox hb2 = new HBox(10);
		hb2.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(file);
		vbox.getChildren().addAll(l1, b, l2, hb2, a, hb);
		vbox.setSpacing(10);
		setCenter(vbox);
		//////////////// ***set on action *****////////////////////////
		file.setOnAction(e -> {// set on action on the file button that when i press it it invoke the file
								// button method and choose the filr for me
			filebtn();// methos will be invoked
		});
		calculate.setOnAction(e -> {
			a.setText(" "); // clear the text at the first
			try {
				if (this.getSelectedFile() != null) {
					Hufman.encoding(this.getSelectedFile());
					Db.setOnAction(l -> {
						// Display the Huffman table information
						try {
							a.appendText("\nFile path: " + this.getSelectedFile().getPath() + "\nSize:"
									+ selectedFile.length() + "\nCompressed file path: " + Hufman.fname + "\n"
									+ Hufman.getHeaderInfo(this.selectedFile) + "\n" +"\n"
									+ "\n\nASCII(REP)\tCharacter\t\tFrequency\tHuffman Code Length\t\tHuffman Code\n");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						try {
							for (int i = 0; i < Hufman.array.length; i++) {
								if ((int) Hufman.array[i].getCharacter() == '\t'
										|| (int) Hufman.array[i].getCharacter() == '\n')
									continue;
								a.appendText(String.valueOf((int) Hufman.array[i].getCharacter()) + "\t\t\t"
										+ Hufman.array[i].getCharacter() + "\t\t\t\t"
										+ String.valueOf(Hufman.array[i].getFrequency()) + "\t\t\t"
										+ Hufman.array[i].getCodeLength() + "\t\t\t\t\t" + Hufman.array[i].getHuffCode()
										+ "\n");
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
					a.setText("File compressed successfully!");
				} else {
					error("An error occurred, check out your files!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		dh.setOnAction(e -> {
			a.setText(" ");
			FileChooser fc = new FileChooser();
			Stage s = new Stage();
			File selectedFile = fc.showOpenDialog(s); // Use selectedFile instead of filee
			Hufman.deCompress(selectedFile);

			a.setText("File decompressed successfully!!!!!");

			Db.setOnAction(l -> {
				try {
					a.appendText("\nDecompressed File: " + selectedFile.getPath() + "\nSize:" + selectedFile.length()
							+ "\nCompressed file path: " + Hufman.fname + "\n" + Hufman.getHeaderInfo(selectedFile)
							+ "\n\nASCII(REP)\tCharacter\t\tFrequency\tHuffman Code Length\t\tHuffman Code\n");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					for (int i = 0; i < Hufman.array.length; i++) {
						if ((int) Hufman.array[i].getCharacter() == '\t'
								|| (int) Hufman.array[i].getCharacter() == '\n')
							continue;
						a.appendText(String.valueOf((int) Hufman.array[i].getCharacter()) + "\t\t\t"
								+ Hufman.array[i].getCharacter() + "\t\t\t\t" + "-"
								+ String.valueOf(Hufman.array[i].getFrequency()) + "\t\t\t"
								+ Hufman.array[i].getCodeLength() + "\t\t\t\t\t" + Hufman.array[i].getHuffCode()
								+ "\n");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		});
		sl.setOnAction(y -> {// set on action on the frequency button that show each character and how it
								// frequency
			a.setText(" ");// clear the text area
			try {// to detect from file error occuring
				if (this.getSelectedFile() != null) {// if the selacted file from the file chooser is not null
					a.appendText("the file path that choosen: " + this.getSelectedFile().getPath()
							+ "\nThe compressed file path in (.huff): " + Hufman.fname
							+ "\n\nCharacter\t\tFrequency\n");
					for (int i = 0; i < Hufman.array.length; i++) {// for loop to walk line by line and fill it with
																	// the huffman table array contents
						if ((int) Hufman.array[i].getCharacter() == '\t'
								|| (int) Hufman.array[i].getCharacter() == '\n')// the
																				// 9
																				// represents
																				// the
																				// tab
																				// ascii
																				// representation
																				// \t
																				// and
																				// 10
																				// represents
																				// the
																				// ascii
																				// line\n
							continue;
						a.appendText(Hufman.array[i].getCharacter() + "\t\t\t"
								+ String.valueOf(Hufman.array[i].getFrequency()) + "\n");// appand on the text area the
																							// character and its
																							// frequency
					}
				} else {
					error("an error occurred, check out your files!!!");// error while choosing file
				}
			} catch (Exception e) {
				error("error while choosing file check out!!!!!");// error in file
			}
		});

		cp.setOnAction(m -> {
			try {
				showHeaderInfo();// method to show the header information
			} catch (Exception e1) {
				error("choose file at first!!!");// error in the file
			}
		});
	}

	///////////////////////// *********methods*********///////////////////////////////////
	private void filebtn() {// method that choose the file
		FileChooser fileChooser = new FileChooser();
		Stage stage = new Stage();// new stage fore file chooser
		selectedFile = fileChooser.showOpenDialog(stage);// open the file chooser

		if (selectedFile != null) {
			suc("the file selected successfully!!!!");// success massege while choosing the file
		} else {
			error("error while selecting file be carefull!!!!");// error massege while choosign the file
		}
	}

	private void showHeaderInfo() throws Exception {
		a.setText(" "); // Clear the text area
		try {
			if (this.getSelectedFile() != null) {
				// Invoke the header method and append the header information to the text area
				String headerInfo = Hufman.header(this.getSelectedFile().getPath(),
						(int) this.getSelectedFile().length(), Hufman.harrSize);
				a.appendText(headerInfo);
			} else {
				error("An error occurred, check out your files!!!");
			}
		} catch (Exception e) {
			error("Error while choosing file, check out your files!!");
		}
	}

	private void error(String msg) {// Display an error message if the number is not between 1 and 100.
		Alert alert = new Alert(Alert.AlertType.ERROR);// Create an alert to display the error message.
		alert.setTitle("Error!!!");
		alert.setHeaderText(null);
		alert.setContentText(msg);// Set the content of the alert to the error message.
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.showAndWait();// Display the alert.
	}

	private void suc(String msg) {// display an sucess message
		Alert alert = new Alert(Alert.AlertType.INFORMATION);// create an alert to display the sucsses massege
		alert.setTitle("File selected successfully");
		alert.setHeaderText(null);
		alert.setContentText("Selected file: " + selectedFile.getPath());// add the selected path file that show the
																			// succse on choosing the file
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.showAndWait();// display the alert
	}
}
