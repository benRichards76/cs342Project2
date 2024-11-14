package main.java;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Objects;

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Rectangle makeBorder() {
		Rectangle border = new Rectangle(700, 700);
		border.setFill(null);
		border.setStroke(Color.CYAN);
		border.setStrokeWidth(5);

		Timeline colorAnimation = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(border.strokeProperty(), Color.CYAN)), new KeyFrame(Duration.seconds(1), new KeyValue(border.strokeProperty(), Color.MAGENTA)), new KeyFrame(Duration.seconds(2), new KeyValue(border.strokeProperty(), Color.YELLOW)), new KeyFrame(Duration.seconds(3), new KeyValue(border.strokeProperty(), Color.LIME)), new KeyFrame(Duration.seconds(4), new KeyValue(border.strokeProperty(), Color.CYAN)));
		colorAnimation.setCycleCount(Timeline.INDEFINITE); // Repeat the animation indefinitely
		colorAnimation.play();
		return border;
	}

	// WELCOME SCREEN
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Welcome to 3-Card Poker");
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/WelcomeScreen.fxml")));
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// PLAYER 1 ANTE SCENE
	public void showPlayer1AnteScene(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Player1AnteScreen.fxml")));
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// PLAYER 2 ANTE SCENE
	public void showPlayer2AnteScene(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Player2AnteScreen.fxml")));
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// GAME SCREEN
	public void showGameScene(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/GameScreen.fxml")));
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}



