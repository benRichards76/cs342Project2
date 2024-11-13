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
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/GameScene.fxml")));
		Scene scene = new Scene(root, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}


//	// Page for setting the Ante Wager for Player 1
//	private Scene setAnteWagerScene(Stage primaryStage) {
//		final int[] anteWagerValue = {5};
//		Rectangle border = makeBorder();
//
//		Text setAnteWagerText = new Text("PLAYER 1\nSET ANTE WAGER");
//		setAnteWagerText.setFill(Color.WHITE);
//		setAnteWagerText.setFont(Font.font("Arial", 50));
//		// setAnteWagerText.setFont(Font.font("Arial", 50));
//
//		Button subtractButton = new Button("  -  ");
//		subtractButton.setFont(Font.font("Arial", 20));
//		subtractButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		subtractButton.setAlignment(Pos.CENTER);
//
//		Button addButton = new Button("  +  ");
//		addButton.setFont(Font.font("Arial", 20));
//		addButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		addButton.setAlignment(Pos.CENTER);
//
//		TextField anteWagerText = new TextField();
//		anteWagerText.setText(String.valueOf("$" + anteWagerValue[0]));
//		anteWagerText.setFont(Font.font("Arial", 20));
//		anteWagerText.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;"); // Set the font size inside the text box
//		anteWagerText.setEditable(false);
//
//		Button nextButton = new Button("Place Ante Wager");
//		nextButton.setFont(Font.font("Arial", 20));
//		nextButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//
//		HBox wagerBox = new HBox(10, subtractButton, anteWagerText, addButton); // 10px spacing
//		wagerBox.setAlignment(Pos.CENTER);
//
//		BorderPane borderPane = new BorderPane();
//		borderPane.setTop(setAnteWagerText);
//		BorderPane.setAlignment(setAnteWagerText, Pos.CENTER);
//		borderPane.setCenter(wagerBox);
//		borderPane.setBottom(nextButton);
//		borderPane.setAlignment(nextButton, Pos.CENTER);
//
//		StackPane newStack = new StackPane(border, borderPane);
//		newStack.setAlignment(Pos.CENTER);
//
//		VBox displayUI = new VBox(20, newStack);
//		displayUI.setAlignment(Pos.CENTER);
//		displayUI.setStyle("-fx-background-color: black;");
//
//		Scene scene = new Scene(displayUI, 700, 700);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//
//		subtractButton.setOnAction(event -> {
//			if (anteWagerValue[0] <= 5) {
//				subtractButton.setDisable(true);
//			} else if (anteWagerValue[0] > 5 && anteWagerValue[0] <= 25) {
//				subtractButton.setDisable(false);
//				addButton.setDisable(false);
//				anteWagerValue[0]--;
//				anteWagerText.setText("$" + anteWagerValue[0]);
//			}
//		});
//
//		addButton.setOnAction(event -> {
//			if (anteWagerValue[0] >= 25) {
//				addButton.setDisable(true);
//			} else if (anteWagerValue[0] >= 5 && anteWagerValue[0] < 25) {
//				addButton.setDisable(false);
//				subtractButton.setDisable(false);
//				anteWagerValue[0]++;
//				anteWagerText.setText("$" + anteWagerValue[0]);
//			}
//		});
//
//		// Modify the nextButton.setOnAction to update the class-level player1AnteWager
//		nextButton.setOnAction(event -> {
//			// Update the class-level ante wager with the value set by Player 1
//			player1AnteWager = anteWagerValue[0];
//			// Navigate to the ante wager scene for Player 2
//			Scene player2AnteWagerScene = setAnteWagerSceneForPlayer2(primaryStage);
//			primaryStage.setScene(player2AnteWagerScene);
//		});
//
//		return scene;
//	}
//
//	// Set Ante wager for player 2
//	private Scene setAnteWagerSceneForPlayer2(Stage primaryStage) {
//		final int[] anteWagerValue = {5};
//		Rectangle border = makeBorder();
//
//		Text setAnteWagerText = new Text("PLAYER 2\nSET ANTE WAGER");
//		setAnteWagerText.setFill(Color.WHITE);
//		setAnteWagerText.setFont(Font.font("Arial", 50));
//
//		// Wrap in a VBox to center-align
//		VBox textContainer = new VBox(setAnteWagerText);
//		textContainer.setAlignment(Pos.CENTER);
//		textContainer.setMaxWidth(Double.MAX_VALUE);
//
//		Button subtractButton = new Button("  -  ");
//		subtractButton.setFont(Font.font("Arial", 20));
//		subtractButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		subtractButton.setAlignment(Pos.CENTER);
//
//		Button addButton = new Button("  +  ");
//		addButton.setFont(Font.font("Arial", 20));
//		addButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		addButton.setAlignment(Pos.CENTER);
//
//		TextField anteWagerText = new TextField();
//		anteWagerText.setText(String.valueOf("$" + anteWagerValue[0]));
//		anteWagerText.setFont(Font.font("Arial", 20));
//		anteWagerText.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		anteWagerText.setEditable(false);
//
//		Button nextButton = new Button("Place Ante Wager");
//		nextButton.setFont(Font.font("Arial", 20));
//		nextButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//
//		HBox wagerBox = new HBox(10, subtractButton, anteWagerText, addButton);
//		wagerBox.setAlignment(Pos.CENTER);
//
//		// Declare the BorderPane
//		BorderPane borderPane = new BorderPane();
//		borderPane.setTop(textContainer);
//		BorderPane.setAlignment(textContainer, Pos.CENTER);
//		borderPane.setCenter(wagerBox);
//		borderPane.setBottom(nextButton);
//		BorderPane.setAlignment(nextButton, Pos.CENTER);
//
//		StackPane newStack = new StackPane(border, borderPane);
//		newStack.setAlignment(Pos.CENTER);
//
//		VBox displayUI = new VBox(20, newStack);
//		displayUI.setAlignment(Pos.CENTER);
//		displayUI.setStyle("-fx-background-color: black;");
//
//		Scene scene = new Scene(displayUI, 700, 700);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//
//		subtractButton.setOnAction(event -> {
//			if (anteWagerValue[0] <= 5) {
//				subtractButton.setDisable(true);
//			} else if (anteWagerValue[0] > 5 && anteWagerValue[0] <= 25) {
//				subtractButton.setDisable(false);
//				addButton.setDisable(false);
//				anteWagerValue[0]--;
//				anteWagerText.setText("$" + anteWagerValue[0]);
//			}
//		});
//
//		addButton.setOnAction(event -> {
//			if (anteWagerValue[0] >= 25) {
//				addButton.setDisable(true);
//			} else if (anteWagerValue[0] >= 5 && anteWagerValue[0] < 25) {
//				addButton.setDisable(false);
//				subtractButton.setDisable(false);
//				anteWagerValue[0]++;
//				anteWagerText.setText("$" + anteWagerValue[0]);
//			}
//		});
//
//		// Modify the nextButton.setOnAction to update the class-level player2AnteWager
//		nextButton.setOnAction(event -> {
//			// Update the class-level ante wager with the value set by Player 2
//			player2AnteWager = anteWagerValue[0];
//			// Navigate to the game board scene
//			Scene gameScene = setGameScene(primaryStage);
//			primaryStage.setScene(gameScene);
//		});
//
//		return scene;
//	}
//
//	private Scene setPairPlusWagerScene(Stage primaryStage) {
//		final int[] pairPlusWagerValue = {5};
//		Rectangle border = makeBorder();
//
//		Text setAnteWagerText = new Text(" SET ANTE WAGER");
//		setAnteWagerText.setFill(Color.WHITE);
//		setAnteWagerText.setFont(Font.font("Arial", 20));
//		setAnteWagerText.setFont(Font.font("Arial", 20));
//
//		Button subtractButton = new Button("  -  ");
//		subtractButton.setFont(Font.font("Arial", 20));
//		subtractButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		subtractButton.setAlignment(Pos.CENTER);
//
//		Button addButton = new Button("  +  ");
//		addButton.setFont(Font.font("Arial", 20));
//		addButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		addButton.setAlignment(Pos.CENTER);
//
//		TextField anteWagerText = new TextField();
//		anteWagerText.setText(String.valueOf("$" + pairPlusWagerValue[0]));
//		anteWagerText.setFont(Font.font("Arial", 20));
//		anteWagerText.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;"); // Set the font size inside the text box
//		anteWagerText.setEditable(false);
//
//		Button placeBetButton = new Button("Place Pair Plus Wager");
//		placeBetButton.setFont(Font.font("Arial", 20));
//		placeBetButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//
//		Button noPPButton = new Button("Don't Place Pair Plus Wager");
//		noPPButton.setFont(Font.font("Arial", 20));
//		noPPButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//
//		HBox wagerBox = new HBox(10, subtractButton, anteWagerText, addButton); // 10px spacing
//		wagerBox.setAlignment(Pos.CENTER);
//
//		HBox bottomHBox = new HBox(10, placeBetButton, noPPButton);
//		BorderPane borderPane = new BorderPane();
//		borderPane.setTop(setAnteWagerText);
//		BorderPane.setAlignment(setAnteWagerText, Pos.CENTER);
//		borderPane.setCenter(wagerBox);
//		borderPane.setBottom(bottomHBox);
//		borderPane.setAlignment(bottomHBox, Pos.CENTER);
//
//		StackPane newStack = new StackPane(border, borderPane);
//		newStack.setAlignment(Pos.CENTER);
//
//		VBox displayUI = new VBox(20, newStack);
//		displayUI.setAlignment(Pos.CENTER);
//		displayUI.setStyle("-fx-background-color: black;");
//
//		Scene scene = new Scene(displayUI, 700, 700);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//
//		subtractButton.setOnAction(event -> {
//			if (pairPlusWagerValue[0] <= 5) {
//				subtractButton.setDisable(true);
//			} else if (pairPlusWagerValue[0] > 5 && pairPlusWagerValue[0] <= 25) {
//				subtractButton.setDisable(false);
//				addButton.setDisable(false);
//				pairPlusWagerValue[0]--;
//				anteWagerText.setText("$" + pairPlusWagerValue[0]);
//			}
//		});
//
//		addButton.setOnAction(event -> {
//			if (pairPlusWagerValue[0] >= 25) {
//				addButton.setDisable(true);
//			} else if (pairPlusWagerValue[0] >= 5 && pairPlusWagerValue[0] < 25) {
//				addButton.setDisable(false);
//				subtractButton.setDisable(false);
//				pairPlusWagerValue[0]++;
//				anteWagerText.setText("$" + pairPlusWagerValue[0]);
//			}
//		});
//
//		noPPButton.setOnAction(event -> {
//			pairPlusWagerValue[0] = 0;
//			Scene setAnteWager = setGameScene(primaryStage);
//			primaryStage.setScene(setAnteWager);
//		});
//
//		placeBetButton.setOnAction(event -> {
//			Scene setAnteWager = setGameScene(primaryStage);
//			primaryStage.setScene(setAnteWager);
//		});
//
//		return scene;
//	}
//
//	private Scene setGameScene(Stage primaryStage) {
//		HBox topBar = new HBox();
//		topBar.setStyle("-fx-background-color: black;");
//		topBar.setPrefHeight(40);
//
//		Button exitButton = new Button("EXIT");
//		exitButton.setFont(Font.font("Arial", 15));
//		exitButton.setTextFill(Color.BLACK);
//		exitButton.setPrefWidth(100);
//
//		Button optionsButton = new Button("OPTIONS");
//		optionsButton.setFont(Font.font("Arial", 15));
//		optionsButton.setTextFill(Color.BLACK);
//		optionsButton.setPrefWidth(100);
//
//		Region leftSpacer = new Region();
//		Region rightSpacer = new Region();
//		HBox.setHgrow(leftSpacer, Priority.ALWAYS);
//		HBox.setHgrow(rightSpacer, Priority.ALWAYS);
//
//		topBar.getChildren().addAll(exitButton, leftSpacer, rightSpacer, optionsButton);
//		topBar.setPadding(new Insets(5, 20, 5, 5));
//
//		// Dealer panel setup
//		VBox dealerPanel = new VBox();
//		Text dealerLabel = new Text("DEALER");
//		dealerLabel.setRotate(-90);
//		dealerLabel.setFont(Font.font("Arial", 30));
//		dealerLabel.setFill(Color.WHITE);
//
//		// Adjust panel size to match the previous layout
//		dealerPanel.setPrefWidth(90);
//		dealerPanel.setPrefHeight(200);
//		dealerPanel.setMinWidth(90);
//		dealerPanel.setMinHeight(200);
//		dealerPanel.setMaxWidth(90);
//		dealerPanel.setMaxHeight(200);
//		dealerPanel.getChildren().add(dealerLabel);
//		dealerPanel.setStyle("-fx-background-color: #d84c73;");
//		dealerPanel.setAlignment(Pos.CENTER);
//
//		dealerPanel.setTranslateY(215); // Adjust the vertical position as needed
//
//		// WAGER INFO box for Player 1
//		VBox wagerInfoBoxTopRight = new VBox(10); // 10px spacing between elements
//		wagerInfoBoxTopRight.setStyle("-fx-background-color: black;");
//		wagerInfoBoxTopRight.setPadding(new Insets(10)); // Add padding inside the box
//
//		// Adjust the size as needed for a shorter box
//		wagerInfoBoxTopRight.setPrefHeight(150); // Change this value to make it taller
//		wagerInfoBoxTopRight.setMinHeight(150);  // Ensure the minimum height matches
//		wagerInfoBoxTopRight.setMaxHeight(150);
//
//		Text wagerInfoLabelTitle = new Text("WAGER INFO");
//		wagerInfoLabelTitle.setFont(Font.font("Arial", 20));
//		wagerInfoLabelTitle.setFill(Color.WHITE);
//
//		Text anteInfo = new Text("ANTE: $" + player1AnteWager);
//		anteInfo.setFont(Font.font("Arial", 16));
//		anteInfo.setFill(Color.WHITE);
//
//		Text pairPlusInfo = new Text("PAIR PLUS: $");
//		pairPlusInfo.setFont(Font.font("Arial", 16));
//		pairPlusInfo.setFill(Color.WHITE);
//
//		Text playWagerInfo = new Text("PLAY WAGER: $");
//		playWagerInfo.setFont(Font.font("Arial", 16));
//		playWagerInfo.setFill(Color.WHITE);
//
//		// Add labels to the WAGER INFO box
//		wagerInfoBoxTopRight.getChildren().addAll(wagerInfoLabelTitle, anteInfo, pairPlusInfo, playWagerInfo);
//
//		// Player 1 panel setup
//		HBox player1Panel = new HBox();
//		Text player1Label = new Text("PLAYER 1");
//		player1Label.setFont(Font.font("Arial", 30));
//		player1Label.setFill(Color.WHITE);
//		player1Panel.setPrefSize(200, 50); // Adjust size as needed
//		player1Panel.getChildren().add(player1Label);
//		player1Panel.setStyle("-fx-background-color: #d84c73;");
//		player1Panel.setAlignment(Pos.CENTER);
//
//		// Combine player1Panel and WAGER INFO into player1Container
//		HBox player1Container = new HBox(20, player1Panel);
//		player1Container.setAlignment(Pos.CENTER);
//
//		// New WAGER INFO box setup for the bottom right
//		VBox wagerInfoBoxBottomRight = new VBox(10); // 10px spacing between elements
//		wagerInfoBoxBottomRight.setStyle("-fx-background-color: black;");
//		wagerInfoBoxBottomRight.setPadding(new Insets(10)); // Add padding inside the box
//
//		// Adjust the size as needed for the bottom right box
//		wagerInfoBoxBottomRight.setPrefHeight(150);
//		wagerInfoBoxBottomRight.setMinHeight(150);
//		wagerInfoBoxBottomRight.setMaxHeight(150);
//
//		Text wagerInfoLabelTitle2 = new Text("WAGER INFO");
//		wagerInfoLabelTitle2.setFont(Font.font("Arial", 20));
//		wagerInfoLabelTitle2.setFill(Color.WHITE);
//
//		Text anteInfo2 = new Text("ANTE: $" + player2AnteWager);
//		anteInfo2.setFont(Font.font("Arial", 16));
//		anteInfo2.setFill(Color.WHITE);
//
//		Text pairPlusInfo2 = new Text("PAIR PLUS: $");
//		pairPlusInfo2.setFont(Font.font("Arial", 16));
//		pairPlusInfo2.setFill(Color.WHITE);
//
//		Text playWagerInfo2 = new Text("PLAY WAGER: $");
//		playWagerInfo2.setFont(Font.font("Arial", 16));
//		playWagerInfo2.setFill(Color.WHITE);
//
//		// Add labels to the new WAGER INFO box
//		wagerInfoBoxBottomRight.getChildren().addAll(wagerInfoLabelTitle2, anteInfo2, pairPlusInfo2, playWagerInfo2);
//
//		// Player 2 panel setup
//		HBox player2Panel = new HBox();
//		Text player2Label = new Text("PLAYER 2");
//		player2Label.setFont(Font.font("Arial", 30));
//		player2Label.setFill(Color.WHITE);
//		player2Panel.setPrefSize(200, 50);
//		player2Panel.getChildren().add(player2Label);
//		player2Panel.setStyle("-fx-background-color: #d84c73;");
//		player2Panel.setAlignment(Pos.CENTER);
//
//		// Combine player2Panel into player2Container
//		HBox player2Container = new HBox(player2Panel);
//		player2Container.setAlignment(Pos.CENTER);
//
//		// Main layout adjustments
//		BorderPane mainLayout = new BorderPane();
//		mainLayout.setTop(new VBox(topBar, player1Container));
//		mainLayout.setLeft(dealerPanel);
//		mainLayout.setBottom(player2Container);
//
//		// Create a StackPane for aligning the top right and bottom right boxes
//		StackPane rightOverlay = new StackPane();
//		rightOverlay.getChildren().addAll(wagerInfoBoxTopRight, wagerInfoBoxBottomRight);
//
//		// Align both boxes in the StackPane
//		StackPane.setAlignment(wagerInfoBoxTopRight, Pos.TOP_RIGHT);
//		StackPane.setAlignment(wagerInfoBoxBottomRight, Pos.BOTTOM_RIGHT);
//
//		// Add the right overlay to the main layout's right section
//		mainLayout.setRight(rightOverlay);
//
//		// Set background color for the scene
//		mainLayout.setStyle("-fx-background-color: #2b7a78;");
//
//		Scene gameScene = new Scene(mainLayout, 1000, 700);
//		return gameScene;
//	}

