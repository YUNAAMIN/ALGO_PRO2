package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {// main calculation in main class this screen make greeting to user and start
										// the program.
	@Override
	public void start(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		StackPane pane = new StackPane();
		Button start = new Button("LET'S START!!");
		start.setFont(new Font("Comic Sans MS", 20));
		start.setStyle("-fx-background-color: #89CFF0; -fx-text-fill: white;");
		start.setMaxWidth(300);
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #89CFF0");
		Image startImage = new Image("C:\\Users\\Yuna\\eclipse-workspace\\ALGO_PRO2\\src\\application\\76YS.gif");
		ImageView view = new ImageView(startImage);
		view.setFitWidth(900);
		view.setFitHeight(700);
		view.setPreserveRatio(true);
		vbox.getChildren().addAll(view);
		pane.getChildren().addAll(vbox, start);
		VBox gif = new VBox();
		gif.setAlignment(Pos.CENTER);
		gif.setStyle("-fx-background-color: #89CFF0");
		Image gifim = new Image(
				"C:\\Users\\Yuna\\eclipse-workspace\\ALGO_PRO2\\src\\application\\7e881bf46c1270f03fd580e8ce221639.gif");
		ImageView imageView = new ImageView(gifim);
		imageView.setFitWidth(900);
		imageView.setFitHeight(900);
		imageView.setPreserveRatio(true);
		gif.getChildren().addAll(imageView);
		bp.setCenter(pane);
		Scene scene = new Scene(bp, 900, 500); // Set the size of the scene
		primaryStage.setScene(scene);
		primaryStage.setTitle("File Management System :)");
		primaryStage.show();
//**************** set on actions***************//
		start.setOnAction(e -> {// go to next screen "calcscreen" and play a one second.
			bp.setCenter(gif);
			// make time that the image will be shown for one second.
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
				// after the time is over and the image shown it will go to next screen
				// "calcscreen".
				try {
					bp.setCenter(new calcscreen());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // go to next screen "calcscreen" .
			}));
			timeline.setCycleCount(1); // make the image shown only once by one cycle.
			timeline.play(); // play the image that is shown
		});
	}

	public static void main(String[] args) {
		Application.launch(args);
		System.out.println(" ");
	}
}
