package controllers;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.Optional;

import main.java.Dealer;
import main.java.Player;
import main.java.Card;

public class GameScreenController {

    @FXML private Button exitButton, optionsButton, freshStartButton, newLookButton, placeBetsButton;
    @FXML private VBox optionsPane;
    @FXML private Text anteInfo, pairPlusInfo, playWagerInfo;       // Text fields for Player 1's wager info
    @FXML private Text anteInfo2, pairPlusInfo2, playWagerInfo2;   // Text fields for Player 2's wager info
    @FXML private Text player1Winnings, player2Winnings;
    @FXML private ImageView player1Card1Image, player1Card2Image, player1Card3Image;
    @FXML private ImageView player2Card1Image, player2Card2Image, player2Card3Image;
    @FXML private ImageView dealerCard1Image, dealerCard2Image, dealerCard3Image;
    @FXML private ImageView deckCardImage;
    private boolean isNewLookEnabled = false;
    private boolean betsPlaced = false;

    // Make the constructor public
    public GameScreenController() {
    }

    @FXML
    public void initialize() {
        try {
            optionsButton.setOnAction(event -> toggleOptionsPane());
            newLookButton.setOnAction(event -> handleNewLook());
            exitButton.setOnAction(event -> handleExit());
            freshStartButton.setOnAction(event -> handleFreshStart());
            placeBetsButton.setOnAction(event -> handlePlaceBets());

            Dealer dealer = new Dealer();
            Player player1 = new Player();
            Player player2 = new Player();

            dealer.setHand(dealer.dealHand());
            player1.setHand(dealer.dealHand());
            player2.setHand(dealer.dealHand());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions during setup to prevent crashes
        }
    }

//    @FXML
//    private void handlePlaceBets() {
//        try {
//            // Load the SetWager.fxml which allows players to set their wagers
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("SetWager.fxml"));
//            Parent root = loader.load();
//            SetWagerController controller = loader.getController();
//
//            // Set up a new stage for placing bets
//            Stage wagerStage = new Stage();
//            wagerStage.setTitle("Set Wager");
//            wagerStage.setScene(new Scene(root, 400, 300));
//            wagerStage.show();
//
//            // Set up a callback to handle the 'Start Game' button in SetWagerController
//            controller.setOnStartGame(() -> {
//                try {
//                    // Get the wager values set by the players
//                    String player1AnteWager = controller.getPlayer1Ante();
//                    String player2AnteWager = controller.getPlayer2Ante();
//                    String player1PairPlusWager = controller.getPlayer1PairPlus();
//                    String player2PairPlusWager = controller.getPlayer2PairPlus();
//
//                    // Set the wager values in the GameScreen UI
//                    setAnteWager(player1AnteWager);
//                    setPlayer2AnteWager(player2AnteWager);
//                    if (!player1PairPlusWager.isEmpty()) {
//                        setPairPlusWager(player1PairPlusWager);
//                    }
//                    if (!player2PairPlusWager.isEmpty()) {
//                        setPlayer2PairPlusWager(player2PairPlusWager);
//                    }
//
//                    // Mark bets as placed and start dealing cards
//                    betsPlaced = true;
//                    dealCards();
//
//                    // Close the wager stage
//                    wagerStage.close();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    showAlert("Error applying wagers.");
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert("Error loading Set Wager screen.");
//        }
//    }

    @FXML
    private void handlePlaceBets() {
        try {
            // Player 1 Wager Input Dialogs
            TextInputDialog player1AnteDialog = new TextInputDialog();
            player1AnteDialog.setTitle("Player 1 Ante Wager");
            player1AnteDialog.setHeaderText(null);
            player1AnteDialog.setContentText("Enter Player 1 Ante (between $5 and $25):");

            Optional<String> player1AnteResult = player1AnteDialog.showAndWait();
            if (player1AnteResult.isPresent() && isValidWager(player1AnteResult.get(), 5, 25)) {
                setAnteWager(player1AnteResult.get());
            } else {
                showAlert("Invalid ante wager for Player 1. Please enter a value between $5 and $25.");
                return; // Stop if invalid
            }

            TextInputDialog player1PairPlusDialog = new TextInputDialog();
            player1PairPlusDialog.setTitle("Player 1 Pair Plus Wager");
            player1PairPlusDialog.setHeaderText(null);
            player1PairPlusDialog.setContentText("Enter Player 1 Pair Plus (optional, between $0 and $25):");

            Optional<String> player1PairPlusResult = player1PairPlusDialog.showAndWait();
            if (player1PairPlusResult.isPresent() && isValidWager(player1PairPlusResult.get(), 0, 25)) {
                setPairPlusWager(player1PairPlusResult.get());
            } else if (player1PairPlusResult.isPresent()) {
                showAlert("Invalid pair plus wager for Player 1. Please enter a value between $0 and $25.");
                return; // Stop if invalid
            }

            // Player 2 Wager Input Dialogs
            TextInputDialog player2AnteDialog = new TextInputDialog();
            player2AnteDialog.setTitle("Player 2 Ante Wager");
            player2AnteDialog.setHeaderText(null);
            player2AnteDialog.setContentText("Enter Player 2 Ante (between $5 and $25):");

            Optional<String> player2AnteResult = player2AnteDialog.showAndWait();
            if (player2AnteResult.isPresent() && isValidWager(player2AnteResult.get(), 5, 25)) {
                setPlayer2AnteWager(player2AnteResult.get());
            } else {
                showAlert("Invalid ante wager for Player 2. Please enter a value between $5 and $25.");
                return; // Stop if invalid
            }

            TextInputDialog player2PairPlusDialog = new TextInputDialog();
            player2PairPlusDialog.setTitle("Player 2 Pair Plus Wager");
            player2PairPlusDialog.setHeaderText(null);
            player2PairPlusDialog.setContentText("Enter Player 2 Pair Plus (optional, between $0 and $25):");

            Optional<String> player2PairPlusResult = player2PairPlusDialog.showAndWait();
            if (player2PairPlusResult.isPresent() && isValidWager(player2PairPlusResult.get(), 0, 25)) {
                setPlayer2PairPlusWager(player2PairPlusResult.get());
            } else if (player2PairPlusResult.isPresent()) {
                showAlert("Invalid pair plus wager for Player 2. Please enter a value between $0 and $25.");
                return; // Stop if invalid
            }

            // Mark bets as placed and start dealing cards
            betsPlaced = true;
            dealCards();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("An error occurred while placing bets.");
        }
    }

    private boolean isValidWager(String wager, int min, int max) {
        try {
            int value = Integer.parseInt(wager);
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void dealCards() {
        if (!betsPlaced) {
            System.out.println("Please place bets before dealing cards.");
            return;
        }

        Dealer dealer = new Dealer();
        Player player1 = new Player();
        Player player2 = new Player();

        dealer.setHand(dealer.dealHand());
        player1.setHand(dealer.dealHand());
        player2.setHand(dealer.dealHand());

        try {
            setupCardImage(deckCardImage, "/deckOfCards/BackOfCard.png");
            animateCardDeal(deckCardImage, player1Card1Image, "/deckOfCards/" + player1.getHand().get(0) + ".png", 0);
            animateCardDeal(deckCardImage, player2Card1Image, "/deckOfCards/" + player2.getHand().get(0) + ".png", 500);
            animateCardDeal(deckCardImage, dealerCard1Image, "/deckOfCards/BackOfCard.png", 1000);
            animateCardDeal(deckCardImage, player1Card2Image, "/deckOfCards/" + player1.getHand().get(1) + ".png", 1500);
            animateCardDeal(deckCardImage, player2Card2Image, "/deckOfCards/" + player2.getHand().get(1) + ".png", 2000);
            animateCardDeal(deckCardImage, dealerCard2Image, "/deckOfCards/BackOfCard.png", 2500);
            animateCardDeal(deckCardImage, player1Card3Image, "/deckOfCards/" + player1.getHand().get(2) + ".png", 3000);
            animateCardDeal(deckCardImage, player2Card3Image, "/deckOfCards/" + player2.getHand().get(2) + ".png", 3500);
            animateCardDeal(deckCardImage, dealerCard3Image, "/deckOfCards/BackOfCard.png", 4000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setupCardImage(ImageView imageView, String imagePath) {
        Image cardImage = null;
        try {
            cardImage = new Image(getClass().getResourceAsStream(imagePath));
            if (cardImage.isError()) {
                throw new IOException("Unable to load image from path: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cardImage != null) {
            imageView.setImage(cardImage);
            imageView.setFitWidth(80);
            imageView.setFitHeight(120);
        }
    }

    @FXML
    private void animateCardDeal(ImageView deckCard, ImageView targetCard, String targetImagePath, int delay) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(deckCard);
        transition.setDuration(Duration.millis(500)); // Animation duration in milliseconds
        transition.setDelay(Duration.millis(delay)); // Delay before the animation starts

        // Calculate the target position for the animation
        double targetX = targetCard.getLayoutX() - deckCard.getLayoutX();
        double targetY = targetCard.getLayoutY() - deckCard.getLayoutY();

        transition.setByX(targetX);
        transition.setByY(targetY);

        transition.setOnFinished(event -> {
            // Set the final card image on the target after the animation ends
            setupCardImage(targetCard, targetImagePath);
            deckCard.setTranslateX(0);
            deckCard.setTranslateY(0);
        });

        transition.play();
    }

    @FXML
    private void toggleOptionsPane() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), optionsPane);
        optionsPane.setVisible(!optionsPane.isVisible());
        transition.setToX(optionsPane.isVisible() ? 0 : -200);
        transition.play();
    }

    @FXML
    public void handleNewLook() {
        Scene scene = optionsButton.getScene();
        if (isNewLookEnabled) {
            scene.getStylesheets().remove(getClass().getResource("/newlook.css").toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource("/newlook.css").toExternalForm());
        }
        isNewLookEnabled = !isNewLookEnabled;
    }

    @FXML
    private void handleExit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExitConfirmationScreen.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) exitButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 1000, 700));
            currentStage.setTitle("Exit Confirmation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFreshStart() {
        // Reset game state if necessary
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Set Player 1's Ante wager value
    public void setAnteWager(String wager) {
        anteInfo.setText("ANTE: " + wager);
    }

    // Set Player 1's Pair Plus wager value
    public void setPairPlusWager(String wager) {
        pairPlusInfo.setText("PAIR PLUS: " + wager);
    }

    // Set Player 1's Play wager value
    public void setPlayWager(String wager) {
        playWagerInfo.setText("PLAY WAGER: " + wager);
    }

    // Set Player 2's Ante wager value
    public void setPlayer2AnteWager(String wager) {
        anteInfo2.setText("ANTE: " + wager);
    }

    // Set Player 2's Pair Plus wager value
    public void setPlayer2PairPlusWager(String wager) {
        pairPlusInfo2.setText("PAIR PLUS: " + wager);
    }

    // Set Player 2's Play wager value
    public void setPlayer2PlayWager(String wager) {
        playWagerInfo2.setText("PLAY WAGER: " + wager);
    }
}