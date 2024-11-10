package src.GameLogic;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class JavaFXTemplate extends Application {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    private Rectangle makeBorder() {
        Rectangle border = new Rectangle(700, 700); // Width and height of the scene
        border.setFill(null); // Make it transparent
        border.setStroke(Color.CYAN); // Border color
        border.setStrokeWidth(5); // Border thickness


        Timeline colorAnimation = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(border.strokeProperty(), Color.CYAN)), new KeyFrame(Duration.seconds(1), new KeyValue(border.strokeProperty(), Color.MAGENTA)), new KeyFrame(Duration.seconds(2), new KeyValue(border.strokeProperty(), Color.YELLOW)), new KeyFrame(Duration.seconds(3), new KeyValue(border.strokeProperty(), Color.LIME)), new KeyFrame(Duration.seconds(4), new KeyValue(border.strokeProperty(), Color.CYAN)));
        colorAnimation.setCycleCount(Timeline.INDEFINITE); // Repeat the animation indefinitely
        colorAnimation.play();
        return border;
    }

    //feel free to remove the starter code from this method
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Welcome to JavaFX");

        Text welcomeText = new Text("  WELCOME\n3-Card Poker");
        welcomeText.setFill(Color.WHITE);
        welcomeText.setFont(Font.font("Arial", 20));

        Button playButton = new Button("PLAY");
        playButton.setFont(Font.font("Arial", 20));
        playButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");

        Button exitButton = new Button("EXIT");
        exitButton.setFont(Font.font("Arial", 20));
        exitButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");

        VBox displays = new VBox(20, welcomeText, playButton, exitButton);
        displays.setAlignment(Pos.CENTER);

        Rectangle border = makeBorder();

        StackPane newStack = new StackPane(border, displays);
        newStack.setAlignment(Pos.CENTER);

        VBox temp = new VBox(20, newStack);
        temp.setAlignment(Pos.CENTER);
        temp.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(temp, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        playButton.setOnAction(event -> {
            // Create the new page (game page) and switch to it
            Scene gameScene = createWagerScene(primaryStage);
            primaryStage.setScene(gameScene);
            //blahblahblah
        });

        exitButton.setOnAction(event -> {
            System.exit(0);
        });
    }

    private Scene createWagerScene(Stage primaryStage) {
        Text setWagerText = new Text(" SET WAGER");
        setWagerText.setFill(Color.WHITE);
        setWagerText.setFont(Font.font("Arial", 20));

        Button anteButton = new Button("  ANTE   ");
        anteButton.setFont(Font.font("Arial", 20));
        anteButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
        anteButton.setPrefWidth(140);  // Set preferred width
        anteButton.setPrefHeight(40);  // Set preferred height
        anteButton.setMinWidth(140);   // Set minimum width
        anteButton.setMinHeight(40);   // Set minimum height

        Button pairPlusButton = new Button("PAIR PLUS");
        pairPlusButton.setFont(Font.font("Arial", 20));
        pairPlusButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
        pairPlusButton.setPrefWidth(140);  // Set preferred width
        pairPlusButton.setPrefHeight(40);  // Set preferred height
        pairPlusButton.setMinWidth(140);   // Set minimum width
        pairPlusButton.setMinHeight(40);   // Set minimum height

        VBox setWagerVBox = new VBox(20, setWagerText, anteButton, pairPlusButton);
        setWagerVBox.setAlignment(Pos.CENTER);

        Rectangle border = makeBorder();

        StackPane newStack = new StackPane(border, setWagerVBox);
        newStack.setAlignment(Pos.CENTER);

        VBox displayUI = new VBox(20, newStack);
        displayUI.setAlignment(Pos.CENTER);
        displayUI.setStyle("-fx-background-color: black;");

        anteButton.setOnAction(event -> {
            // Create the new page (game page) and switch to it
            Scene setAnteWager = setAnteWagerScene(primaryStage);
            primaryStage.setScene(setAnteWager);
        });

        pairPlusButton.setOnAction(event -> {
            // Create the new page (game page) and switch to it
            Scene setAnteWager = setPairPlusWagerScene(primaryStage);
            primaryStage.setScene(setAnteWager);
        });

        Scene scene = new Scene(displayUI, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        return scene;
    }

    private Scene setAnteWagerScene(Stage primaryStage) {
        final int[] anteWagerValue = {5};
        Rectangle border = makeBorder();

        Text setAnteWagerText = new Text(" SET ANTE WAGER");
        setAnteWagerText.setFill(Color.WHITE);
        setAnteWagerText.setFont(Font.font("Arial", 20));
        setAnteWagerText.setFont(Font.font("Arial", 20));

        Button subtractButton = new Button("  -  ");
        subtractButton.setFont(Font.font("Arial", 20));
        subtractButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
        subtractButton.setAlignment(Pos.CENTER);

        Button addButton = new Button("  +  ");
        addButton.setFont(Font.font("Arial", 20));
        addButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
        addButton.setAlignment(Pos.CENTER);

        TextField anteWagerText = new TextField();
        anteWagerText.setText(String.valueOf("$" + anteWagerValue[0]));
        anteWagerText.setFont(Font.font("Arial", 20));
        anteWagerText.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;"); // Set the font size inside the text box
//		anteWagerText.setAlignment(Pos.CENTER);
        anteWagerText.setEditable(false);

        Button nextButton = new Button("Place Ante Wager");
        nextButton.setFont(Font.font("Arial", 20));
        nextButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
//		nextButton.setAlignment(Pos.CENTER);

        HBox wagerBox = new HBox(10, subtractButton, anteWagerText, addButton); // 10px spacing
        wagerBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(setAnteWagerText);
        BorderPane.setAlignment(setAnteWagerText, Pos.CENTER);
        borderPane.setCenter(wagerBox);
        borderPane.setBottom(nextButton);
        borderPane.setAlignment(nextButton, Pos.CENTER);

        StackPane newStack = new StackPane(border, borderPane);
        newStack.setAlignment(Pos.CENTER);

        VBox displayUI = new VBox(20, newStack);
        displayUI.setAlignment(Pos.CENTER);
        displayUI.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(displayUI, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        subtractButton.setOnAction(event -> {
            if (anteWagerValue[0] <= 5) {
                subtractButton.setDisable(true);
            } else if (anteWagerValue[0] > 5 && anteWagerValue[0] <= 25) {
                subtractButton.setDisable(false);
                addButton.setDisable(false);
                anteWagerValue[0]--;
                anteWagerText.setText("$" + anteWagerValue[0]);
            }
        });

        addButton.setOnAction(event -> {
            if (anteWagerValue[0] >= 25) {
                addButton.setDisable(true);
            } else if (anteWagerValue[0] >= 5 && anteWagerValue[0] < 25) {
                addButton.setDisable(false);
                subtractButton.setDisable(false);
                anteWagerValue[0]++;
                anteWagerText.setText("$" + anteWagerValue[0]);
            }
        });

        nextButton.setOnAction(event -> {
            Scene setAnteWager = setPairPlusWagerScene(primaryStage);
            primaryStage.setScene(setAnteWager);
        });

        return scene;
    }

    private Scene setPairPlusWagerScene(Stage primaryStage) {
        final int[] pairPlusWagerValue = {5};
        Rectangle border = makeBorder();

        Text setAnteWagerText = new Text(" SET ANTE WAGER");
        setAnteWagerText.setFill(Color.WHITE);
        setAnteWagerText.setFont(Font.font("Arial", 20));
        setAnteWagerText.setFont(Font.font("Arial", 20));

        Button subtractButton = new Button("  -  ");
        subtractButton.setFont(Font.font("Arial", 20));
        subtractButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
        subtractButton.setAlignment(Pos.CENTER);

        Button addButton = new Button("  +  ");
        addButton.setFont(Font.font("Arial", 20));
        addButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");
        addButton.setAlignment(Pos.CENTER);

        TextField anteWagerText = new TextField();
        anteWagerText.setText(String.valueOf("$" + pairPlusWagerValue[0]));
        anteWagerText.setFont(Font.font("Arial", 20));
        anteWagerText.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;"); // Set the font size inside the text box
        anteWagerText.setEditable(false);

        Button placeBetButton = new Button("Place Pair Plus Wager");
        placeBetButton.setFont(Font.font("Arial", 20));
        placeBetButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");

        Button noPPButton = new Button("Don't Place Pair Plus Wager");
        noPPButton.setFont(Font.font("Arial", 20));
        noPPButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white;");

        HBox wagerBox = new HBox(10, subtractButton, anteWagerText, addButton); // 10px spacing
        wagerBox.setAlignment(Pos.CENTER);

        HBox bottomHBox = new HBox(10, placeBetButton, noPPButton);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(setAnteWagerText);
        BorderPane.setAlignment(setAnteWagerText, Pos.CENTER);
        borderPane.setCenter(wagerBox);
        borderPane.setBottom(bottomHBox);
        borderPane.setAlignment(bottomHBox, Pos.CENTER);

        StackPane newStack = new StackPane(border, borderPane);
        newStack.setAlignment(Pos.CENTER);

        VBox displayUI = new VBox(20, newStack);
        displayUI.setAlignment(Pos.CENTER);
        displayUI.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(displayUI, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        subtractButton.setOnAction(event -> {
            if (pairPlusWagerValue[0] <= 5) {
                subtractButton.setDisable(true);
            } else if (pairPlusWagerValue[0] > 5 && pairPlusWagerValue[0] <= 25) {
                subtractButton.setDisable(false);
                addButton.setDisable(false);
                pairPlusWagerValue[0]--;
                anteWagerText.setText("$" + pairPlusWagerValue[0]);
            }
        });

        addButton.setOnAction(event -> {
            if (pairPlusWagerValue[0] >= 25) {
                addButton.setDisable(true);
            } else if (pairPlusWagerValue[0] >= 5 && pairPlusWagerValue[0] < 25) {
                addButton.setDisable(false);
                subtractButton.setDisable(false);
                pairPlusWagerValue[0]++;
                anteWagerText.setText("$" + pairPlusWagerValue[0]);
            }
        });

        noPPButton.setOnAction(event -> {
            pairPlusWagerValue[0] = 0;
            Scene setAnteWager = setGameScene(primaryStage);
            primaryStage.setScene(setAnteWager);
        });

        placeBetButton.setOnAction(event -> {
            Scene setAnteWager = setGameScene(primaryStage);
            primaryStage.setScene(setAnteWager);
        });

        return scene;
    }

    private Scene setGameScene(Stage primaryStage) {
            // Top bar with options and exit
            HBox topBar = new HBox();
            topBar.setStyle("-fx-background-color: black;");
            topBar.setPrefHeight(40);

            Button exitButton = new Button("EXIT");
            exitButton.setFont(Font.font("Arial", 15));
            exitButton.setTextFill(Color.BLACK);
            exitButton.setPrefWidth(100);

            Button optionsButton = new Button("OPTIONS");
            optionsButton.setFont(Font.font("Arial", 15));
            optionsButton.setTextFill(Color.BLACK);
            optionsButton.setPrefWidth(100);

            Region leftSpacer = new Region();
            Region rightSpacer = new Region();
            HBox.setHgrow(leftSpacer, Priority.ALWAYS);
            HBox.setHgrow(rightSpacer, Priority.ALWAYS);

            topBar.getChildren().addAll(exitButton, leftSpacer, rightSpacer, optionsButton);
            topBar.setPadding(new Insets(5, 20, 5, 5));

        // Dealer panel setup
        VBox dealerPanel = new VBox();
        Text dealerLabel = new Text("DEALER");
        dealerLabel.setRotate(-90); // Rotate text to be vertical
        dealerLabel.setFont(Font.font("Arial", 30)); // Set font size
        dealerLabel.setFill(Color.WHITE); // Set text color to white

        // Adjust panel size to match the previous layout
        dealerPanel.setPrefWidth(90); // Adjust width to desired size
        dealerPanel.setPrefHeight(200); // Adjust height to desired size
        dealerPanel.setMinWidth(90); // Ensure minimum width is the same
        dealerPanel.setMinHeight(200); // Ensure minimum height is the same
        dealerPanel.setMaxWidth(90); // Ensure maximum width matches
        dealerPanel.setMaxHeight(200); // Ensure maximum height matches
        dealerPanel.getChildren().add(dealerLabel);
        dealerPanel.setStyle("-fx-background-color: #d84c73;"); // Add color for visual effect
        dealerPanel.setAlignment(Pos.CENTER); // Center the content

        // Center the dealer panel vertically by adding padding or alignment
//        VBox.setVgrow(dealerPanel, Priority.ALWAYS); // Allow the panel to grow vertically
        dealerPanel.setTranslateY(300); // Adjust the vertical position as needed

        // Player 1 panel setup
            HBox player1Panel = new HBox();
            Text player1Label = new Text("PLAYER 1");
            player1Label.setFont(Font.font("Arial", 30));
            player1Label.setFill(Color.WHITE);
            player1Panel.setPrefSize(200, 50); // Adjust size as needed
            player1Panel.getChildren().add(player1Label);
            player1Panel.setStyle("-fx-background-color: #d84c73;");
            player1Panel.setAlignment(Pos.CENTER);

            // WAGER INFO box next to Player 1
            HBox wagerInfoBox1 = new HBox();
            Text wagerInfoLabel1 = new Text("WAGER INFO");
            wagerInfoLabel1.setFont(Font.font("Arial", 20));
            wagerInfoLabel1.setFill(Color.WHITE);
            wagerInfoBox1.setPrefSize(150, 50);
            wagerInfoBox1.getChildren().add(wagerInfoLabel1);
            wagerInfoBox1.setStyle("-fx-background-color: black;");
            wagerInfoBox1.setAlignment(Pos.CENTER);

            // Combine player1Panel and WAGER INFO into player1Container
            HBox player1Container = new HBox(20, player1Panel, wagerInfoBox1); // Add spacing between them
            player1Container.setAlignment(Pos.CENTER);

            // Player 2 panel setup
            HBox player2Panel = new HBox();
            Text player2Label = new Text("PLAYER 2");
            player2Label.setFont(Font.font("Arial", 30));
            player2Label.setFill(Color.WHITE);
            player2Panel.setPrefSize(200, 50); // Adjust size as needed
            player2Panel.getChildren().add(player2Label);
            player2Panel.setStyle("-fx-background-color: #d84c73;");
            player2Panel.setAlignment(Pos.CENTER);

            // WAGER INFO box next to Player 2
            HBox wagerInfoBox2 = new HBox();
            Text wagerInfoLabel2 = new Text("WAGER INFO");
            wagerInfoLabel2.setFont(Font.font("Arial", 20));
            wagerInfoLabel2.setFill(Color.WHITE);
            wagerInfoBox2.setPrefSize(150, 50);
            wagerInfoBox2.getChildren().add(wagerInfoLabel2);
            wagerInfoBox2.setStyle("-fx-background-color: black;");
            wagerInfoBox2.setAlignment(Pos.CENTER);

            // Combine player2Panel and WAGER INFO into player2Container
            HBox player2Container = new HBox(20, player2Panel, wagerInfoBox2); // Add spacing between them
            player2Container.setAlignment(Pos.CENTER);

            // Main layout adjustments
            BorderPane mainLayout = new BorderPane();
            mainLayout.setTop(new VBox(topBar, player1Container)); // Top container with player1Container
            mainLayout.setLeft(dealerPanel); // Dealer panel on the left
            mainLayout.setBottom(player2Container); // Player 2 container at the bottom

            // Set background color for the scene
            mainLayout.setStyle("-fx-background-color: #2b7a78;");

            Scene gameScene = new Scene(mainLayout, 1000, 700);
            return gameScene;
        }
}
