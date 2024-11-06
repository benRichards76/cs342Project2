import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to JavaFX");
		
//		 Rectangle rect = new Rectangle (100, 40, 100, 100);
//	     rect.setArcHeight(50);
//	     rect.setArcWidth(50);
//	     rect.setFill(Color.VIOLET);
//
//	     RotateTransition rt = new RotateTransition(Duration.millis(5000), rect);
//	     rt.setByAngle(270);
//	     rt.setCycleCount(4);
//	     rt.setAutoReverse(true);
//	     SequentialTransition seqTransition = new SequentialTransition (
//	         new PauseTransition(Duration.millis(500)),
//	         rt
//	     );
//	     seqTransition.play();
//
//	     FadeTransition ft = new FadeTransition(Duration.millis(5000), rect);
//	     ft.setFromValue(1.0);
//	     ft.setToValue(0.3);
//	     ft.setCycleCount(4);
//	     ft.setAutoReverse(true);
//
//	     ft.play();
//	     BorderPane root = new BorderPane();
//	     root.setCenter(rect);
		 
	     Text welcomeText = new Text("  WELCOME\n3-Card Poker");
		 welcomeText.setFill(Color.WHITE);
		 welcomeText.setFont(Font.font ("Arial", 20));

		 Button playButton = new Button("PLAY");
		 playButton.setFont(Font.font ("Arial", 20));
		 playButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");

		 Button exitButton = new Button("EXIT");
		 exitButton.setFont(Font.font ("Arial", 20));
		 exitButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");

		 VBox displays = new VBox(20, welcomeText, playButton, exitButton);
		 displays.setAlignment(Pos.CENTER);

		 Rectangle border = new Rectangle(700, 700); // Width and height of the scene
		 border.setFill(null); // Make it transparent
		 border.setStroke(Color.CYAN); // Border color
		 border.setStrokeWidth(5); // Border thickness

//		 DropShadow glow = new DropShadow();
//		 glow.setColor(Color.CYAN);
//		 glow.setRadius(20);
//		 border.setEffect(glow);

		 Timeline colorAnimation = new Timeline(
				new KeyFrame(Duration.seconds(0), new KeyValue(border.strokeProperty(), Color.CYAN)),
				new KeyFrame(Duration.seconds(1), new KeyValue(border.strokeProperty(), Color.MAGENTA)),
				new KeyFrame(Duration.seconds(2), new KeyValue(border.strokeProperty(), Color.YELLOW)),
				new KeyFrame(Duration.seconds(3), new KeyValue(border.strokeProperty(), Color.LIME)),
				new KeyFrame(Duration.seconds(4), new KeyValue(border.strokeProperty(), Color.CYAN))
		 );
		 colorAnimation.setCycleCount(Timeline.INDEFINITE); // Repeat the animation indefinitely
		 colorAnimation.play();

		 StackPane newStack = new StackPane(border, displays);
		 newStack.setAlignment(Pos.CENTER);

		 VBox temp = new VBox(20, newStack);
		 temp.setAlignment(Pos.CENTER);
		 temp.setStyle("-fx-background-color: black;");




		Scene scene = new Scene(temp, 700,700);
		primaryStage.setScene(scene);
		primaryStage.show();

		playButton.setOnAction(event -> {
			// Create the new page (game page) and switch to it
			Scene gameScene = createGameScene(primaryStage);
			primaryStage.setScene(gameScene);
		});
	}

	private Scene createGameScene(Stage primaryStage) {
		Text testing = new Text("Testing");
		testing.setFont(Font.font ("Arial", 20));
		testing.setFill(Color.PALEVIOLETRED);
		HBox forThis = new HBox(20, testing);
		return new Scene(forThis, 700,700);
	}

}
