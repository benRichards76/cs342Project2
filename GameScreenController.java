package controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Optional;

import main.java.Dealer;
import main.java.Player;
import main.java.Card;
import main.java.ThreeCardLogic;

public class GameScreenController {

    // Buttons and UI elements for controlling game actions
    @FXML private Button exitButton, optionsButton, freshStartButton, newLookButton, placeBetsButton;
    @FXML private Button player1FoldButton, player2FoldButton, player1PlayBetButton, player2PlayBetButton;
    @FXML private VBox optionsPane;
    @FXML private Text anteInfo, pairPlusInfo, playWagerInfo;       // Player 1 wager info
    @FXML private Text anteInfo2, pairPlusInfo2, playWagerInfo2;    // Player 2 wager info
    @FXML private Text player1Winnings, player2Winnings;
    @FXML private ImageView player1Card1Image, player1Card2Image, player1Card3Image;
    @FXML private ImageView player2Card1Image, player2Card2Image, player2Card3Image;
    @FXML private ImageView dealerCard1Image, dealerCard2Image, dealerCard3Image;
    @FXML private ImageView deckCardImage;

    // Game state variables
    private boolean isNewLookEnabled = false;
    private boolean betsPlaced = false;
    private boolean player1Folded = false;
    private boolean player2Folded = false;
    private boolean player1Played = false;
    private boolean player2Played = false;
    private int currentWinnings = 0;
    private int playWagerAmount = 0;
    private int updatedWinnings = 0;
    private boolean p1NextHand = false;
    private boolean p2NextHand = false;
    private boolean nextAnte1 = true;
    private boolean nextAnte2 = true;

    // Player and Dealer instances
    private Player player1;
    private Player player2;
    private Dealer dealer;

    // Constructor
    public GameScreenController() {
    }

    @FXML
    public void initialize() {
        try {
            // Set up button actions
            optionsButton.setOnAction(event -> toggleOptionsPane());
            newLookButton.setOnAction(event -> handleNewLook());
            exitButton.setOnAction(event -> handleExit());
            freshStartButton.setOnAction(event -> handleFreshStart());
            placeBetsButton.setOnAction(event -> handlePlaceBets());
            player1FoldButton.setOnAction(event -> handlePlayer1Fold());
            player2FoldButton.setOnAction(event -> handlePlayer2Fold());
            player1PlayBetButton.setOnAction(event -> handlePlayer1PlayBet());
            player2PlayBetButton.setOnAction(event -> handlePlayer2PlayBet());

            // Initialize player and dealer
            dealer = new Dealer();
            player1 = new Player();
            player2 = new Player();

            // Disable fold and play buttons initially
            player1FoldButton.setDisable(true);
            player2FoldButton.setDisable(true);
            player1PlayBetButton.setDisable(true);
            player2PlayBetButton.setDisable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle bet placement for both players
    @FXML
    private void handlePlaceBets() {
        try {
            player1Folded = false;
            player2Folded = false;
            player1Played = false;
            player2Played = false;

            // Prompt Player 1 for wagers
            if (!p1NextHand) {
                if (nextAnte1) {
                    TextInputDialog player1AnteDialog = new TextInputDialog();
                    player1AnteDialog.setTitle("Player 1 Ante Wager");
                    player1AnteDialog.setContentText("Enter Player 1 Ante (between $5 and $25):");
                    Optional<String> player1AnteResult = player1AnteDialog.showAndWait();
                    if (player1AnteResult.isPresent() && isValidWager(player1AnteResult.get(), 5, 25)) {
                        setAnteWager(player1AnteResult.get());
                    } else {
                        showAlert("Invalid ante wager for Player 1. Please enter a value between $5 and $25.");
                        return;
                    }
                }

                // Pair Plus wager (optional)
                TextInputDialog player1PairPlusDialog = new TextInputDialog();
                player1PairPlusDialog.setTitle("Player 1 Pair Plus Wager");
                player1PairPlusDialog.setContentText("Enter Player 1 Pair Plus (optional, between $5 and $25):");
                Optional<String> player1PairPlusResult = player1PairPlusDialog.showAndWait();
                if (player1PairPlusResult.isPresent()) {
                    String result = player1PairPlusResult.get();
                    if (isValidWager(result, 5, 25)) {
                        setPairPlusWager(result);
                    } else {
                        showAlert("Invalid pair plus wager for Player 1. Please enter a value between $5 and $25.");
                        return;
                    }
                } else {
                    setPairPlusWager("0");
                }
            }

            // Prompt Player 2 for wagers
            if (!p2NextHand) {
                if (nextAnte2) {
                    TextInputDialog player2AnteDialog = new TextInputDialog();
                    player2AnteDialog.setTitle("Player 2 Ante Wager");
                    player2AnteDialog.setContentText("Enter Player 2 Ante (between $5 and $25):");
                    Optional<String> player2AnteResult = player2AnteDialog.showAndWait();
                    if (player2AnteResult.isPresent() && isValidWager(player2AnteResult.get(), 5, 25)) {
                        setPlayer2AnteWager(player2AnteResult.get());
                    } else {
                        showAlert("Invalid ante wager for Player 2. Please enter a value between $5 and $25.");
                        return;
                    }
                }

                // Pair Plus wager (optional)
                TextInputDialog player2PairPlusDialog = new TextInputDialog();
                player2PairPlusDialog.setTitle("Player 2 Pair Plus Wager");
                player2PairPlusDialog.setContentText("Enter Player 2 Pair Plus (optional, between $5 and $25):");
                Optional<String> player2PairPlusResult = player2PairPlusDialog.showAndWait();
                if (player2PairPlusResult.isPresent()) {
                    String result = player2PairPlusResult.get();
                    if (isValidWager(result, 5, 25)) {
                        setPlayer2PairPlusWager(result);
                    } else {
                        showAlert("Invalid pair plus wager for Player 2. Please enter a value between $5 and $25.");
                        return;
                    }
                } else {
                    setPlayer2PairPlusWager("0");
                }
            }

            // Enable fold and play buttons
            betsPlaced = true;
            dealCards();
            player1FoldButton.setDisable(false);
            player2FoldButton.setDisable(false);
            player1PlayBetButton.setDisable(false);
            player2PlayBetButton.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("An error occurred while placing bets.");
        }
    }

    // Handle fold action for Player 1
    @FXML
    private void handlePlayer1Fold() {
        player1Folded = true;
        p1NextHand = false;
        resetWagers1();
        player1FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        showInfo("Player 1 has folded.");
        checkAllActionsCompleted();
    }

    // Handle fold action for Player 2
    @FXML
    private void handlePlayer2Fold() {
        player2Folded = true;
        p2NextHand = false;
        resetWagers2();
        player2FoldButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        showInfo("Player 2 has folded.");
        checkAllActionsCompleted();
    }

    // Handle play bet action for Player 1
    @FXML
    private void handlePlayer1PlayBet() {
        if (player1Folded) {
            showInfo("Player 1 has folded and cannot place a play bet.");
            return;
        }
        // Set Player 1's play wager to match ante
        String anteWager = anteInfo.getText().replace("ANTE: ", "");
        setPlayWager(anteWager);
        player1FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        player1Played = true;
        showInfo("Player 1's play wager set to ante: " + anteWager);
        checkAllActionsCompleted();
    }

    // Handle play bet action for Player 2
    @FXML
    private void handlePlayer2PlayBet() {
        if (player2Folded) {
            showInfo("Player 2 has folded and cannot place a play bet.");
            return;
        }
        // Set Player 2's play wager to match ante
        String anteWager = anteInfo2.getText().replace("ANTE: ", "");
        setPlayer2PlayWager(anteWager);
        player2FoldButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        player2Played = true;
        showInfo("Player 2's play wager set to ante: " + anteWager);
        checkAllActionsCompleted();
    }

    // Check if both players have finished their actions
    private void checkAllActionsCompleted() {
        if ((player1Folded || player1Played) && (player2Folded || player2Played)) {
            evaluateDealerHand();
        }
    }

    // Validate wager input
    private boolean isValidWager(String wager, int min, int max) {
        try {
            int value = Integer.parseInt(wager);
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Deal cards to each player and the dealer
    private void dealCards() {
        if (!betsPlaced) {
            System.out.println("Place bets before dealing cards.");
            return;
        }
        // Deal hands to players and dealer
        dealer.setHand(dealer.dealHand());
        if (!player1Folded) player1.setHand(dealer.dealHand());
        if (!player2Folded) player2.setHand(dealer.dealHand());

        try {
            setupCardImage(deckCardImage, "/deckOfCards/BackOfCard.png");
            // Animated card deal for each player and dealer
            if (!player1Folded) {
                animateCardDeal(deckCardImage, player1Card1Image, "/deckOfCards/" + player1.getHand().get(0) + ".png", 0);
                animateCardDeal(deckCardImage, player1Card2Image, "/deckOfCards/" + player1.getHand().get(1) + ".png", 1500);
                animateCardDeal(deckCardImage, player1Card3Image, "/deckOfCards/" + player1.getHand().get(2) + ".png", 3000);
            }
            if (!player2Folded) {
                animateCardDeal(deckCardImage, player2Card1Image, "/deckOfCards/" + player2.getHand().get(0) + ".png", 500);
                animateCardDeal(deckCardImage, player2Card2Image, "/deckOfCards/" + player2.getHand().get(1) + ".png", 2000);
                animateCardDeal(deckCardImage, player2Card3Image, "/deckOfCards/" + player2.getHand().get(2) + ".png", 3500);
            }
            animateCardDeal(deckCardImage, dealerCard1Image, "/deckOfCards/BackOfCard.png", 1000);
            animateCardDeal(deckCardImage, dealerCard2Image, "/deckOfCards/BackOfCard.png", 2500);
            animateCardDeal(deckCardImage, dealerCard3Image, "/deckOfCards/BackOfCard.png", 4000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Evaluate the dealer's hand against each player
    private void evaluateDealerHand() {
        ThreeCardLogic threeCardLogic = new ThreeCardLogic();
        String wager = "0";

        // Reveal dealer cards
        animateCardDeal(deckCardImage, dealerCard1Image, "/deckOfCards/" + dealer.getHand().get(0) + ".png", 0);
        animateCardDeal(deckCardImage, dealerCard2Image, "/deckOfCards/" + dealer.getHand().get(1) + ".png", 0);
        animateCardDeal(deckCardImage, dealerCard3Image, "/deckOfCards/" + dealer.getHand().get(2) + ".png", 0);
        threeCardLogic.callSort(dealer.getHand());

        // Check if dealer qualifies
        boolean dealerQualifies = dealer.getHand().get(0).getValue() >= 19; // Queen high or better

        // Evaluate and display result for Player 1
        if (!player1Folded) {
            int pairPlusWinnings = threeCardLogic.evalPPWinnings(player1.getHand(), player1.getPairPlusBet());
            setTotalWinnings(player1Winnings, String.valueOf(pairPlusWinnings), player1);
            if (!dealerQualifies) {
                String playWager = playWagerInfo.getText().replace("PLAY WAGER: ", "");
                setTotalWinnings(player1Winnings, playWager, player1);
                nextAnte1 = false;
                setPairPlusWager(wager);
                setPlayWager(wager);
                showInfo("Dealer does not qualify. Player 1's play wager is returned, and the ante is pushed.");
            } else {
                // Compare hands
                int p1Result = threeCardLogic.compareHands(dealer.getHand(), player1.getHand());
                int playAndAnteWinnings = (player1.getAnteBet() + player1.getPlayBet()) * 2;
                if (p1Result == 1) {
                    showInfo("Player 1 loses to dealer");
                    resetWagers1();
                } else if (p1Result == 2) {
                    showInfo("Player 1 beats dealer");
                    resetWagers1();
                    setTotalWinnings(player1Winnings, String.valueOf(playAndAnteWinnings), player1);
                } else {
                    showInfo("Player 1 ties dealer");
                }
            }
        }

        // Evaluate and display result for Player 2 (similar to Player 1)
        if (!player2Folded) {
            int pairPlusWinnings = threeCardLogic.evalPPWinnings(player2.getHand(), player2.getPairPlusBet());
            setTotalWinnings(player2Winnings, String.valueOf(pairPlusWinnings), player2);
            if (!dealerQualifies) {
                String playWager2 = playWagerInfo2.getText().replace("PLAY WAGER: ", "");
                setTotalWinnings(player2Winnings, playWager2, player2);
                nextAnte2 = false;
                setPlayer2PairPlusWager(wager);
                setPlayer2PlayWager(wager);
                showInfo("Dealer does not qualify. Player 2's play wager is returned, and the ante is pushed.");
            } else {
                // Compare hands
                int p2Result = threeCardLogic.compareHands(dealer.getHand(), player2.getHand());
                int playAndAnteWinnings = (player2.getAnteBet() + player2.getPlayBet()) * 2;
                if (p2Result == 1) {
                    showInfo("Player 2 loses to dealer");
                    resetWagers2();
                } else if (p2Result == 2) {
                    showInfo("Player 2 beats dealer");
                    resetWagers2();
                    setTotalWinnings(player2Winnings, String.valueOf(playAndAnteWinnings), player2);
                } else {
                    showInfo("Player 2 ties dealer");
                }
            }
        }

        showInfo("Press Place Bets button in top left to start new hand");
    }

    @FXML
    private void setupCardImage(ImageView imageView, String imagePath) {
        Image cardImage = null;
        try {
            cardImage = new Image(getClass().getResourceAsStream(imagePath));
            if (cardImage.isError()) throw new IOException("Unable to load image from path: " + imagePath);
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
        transition.setDuration(Duration.millis(500));
        transition.setDelay(Duration.millis(delay));
        double targetX = targetCard.getLayoutX() - deckCard.getLayoutX();
        double targetY = targetCard.getLayoutY() - deckCard.getLayoutY();
        transition.setByX(targetX);
        transition.setByY(targetY);
        transition.setOnFinished(event -> {
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
        // Reset game state and UI elements
        player1Folded = false;
        player2Folded = false;
        player1Played = false;
        player2Played = false;
        betsPlaced = false;
        player1FoldButton.setDisable(true);
        player2FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        player1Winnings.setText("Total Winnings: $0");
        player2Winnings.setText("Total Winnings: $0");
        anteInfo.setText("ANTE: $");
        pairPlusInfo.setText("PAIR PLUS: $");
        playWagerInfo.setText("PLAY WAGER: $");
        anteInfo2.setText("ANTE: $");
        pairPlusInfo2.setText("PAIR PLUS: $");
        playWagerInfo2.setText("PLAY WAGER: $");

        // Clear card images
        player1Card1Image.setImage(null);
        player1Card2Image.setImage(null);
        player1Card3Image.setImage(null);
        player2Card1Image.setImage(null);
        player2Card2Image.setImage(null);
        player2Card3Image.setImage(null);
        dealerCard1Image.setImage(null);
        dealerCard2Image.setImage(null);
        dealerCard3Image.setImage(null);
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets Player 1's Ante wager and updates the UI.
     * @param wager The ante wager amount for Player 1
     */
    public void setAnteWager(String wager) {
        int p1Ante = Integer.parseInt(wager);
        player1.setAnteBet(p1Ante);
        anteInfo.setText("ANTE: " + wager);
    }

    /**
     * Sets Player 1's Pair Plus wager and updates the UI.
     * @param wager The Pair Plus wager amount for Player 1
     */
    public void setPairPlusWager(String wager) {
        int p1Pair = Integer.parseInt(wager);
        player1.setPairPlusBet(p1Pair);
        pairPlusInfo.setText("PAIR PLUS: " + wager);
    }

    /**
     * Sets Player 1's Play wager and updates the UI.
     * @param wager The play wager amount for Player 1
     */
    public void setPlayWager(String wager) {
        int p1Play = Integer.parseInt(wager);
        player1.setPlayBet(p1Play);
        playWagerInfo.setText("PLAY WAGER: " + wager);
    }

    /**
     * Sets Player 2's Ante wager and updates the UI.
     * @param wager The ante wager amount for Player 2
     */
    public void setPlayer2AnteWager(String wager) {
        int p2Ante = Integer.parseInt(wager);
        player2.setAnteBet(p2Ante);
        anteInfo2.setText("ANTE: " + wager);
    }

    /**
     * Sets Player 2's Pair Plus wager and updates the UI.
     * @param wager The Pair Plus wager amount for Player 2
     */
    public void setPlayer2PairPlusWager(String wager) {
        int p2Pair = Integer.parseInt(wager);
        player2.setPairPlusBet(p2Pair);
        pairPlusInfo2.setText("PAIR PLUS: " + wager);
    }

    /**
     * Sets Player 2's Play wager and updates the UI.
     * @param wager The play wager amount for Player 2
     */
    public void setPlayer2PlayWager(String wager) {
        int p2Play = Integer.parseInt(wager);
        player2.setPlayBet(p2Play);
        playWagerInfo2.setText("PLAY WAGER: " + wager);
    }

    /**
     * Updates the total winnings for a player and updates the UI.
     * @param playerWinningsText Text element showing the total winnings
     * @param wager The amount won in the latest round
     * @param player The player whose winnings are updated
     */
    public void setTotalWinnings(Text playerWinningsText, String wager, Player player) {
        currentWinnings = Integer.parseInt(playerWinningsText.getText().replace("Total Winnings: $", ""));
        playWagerAmount = Integer.parseInt(wager);
        updatedWinnings = currentWinnings + playWagerAmount;
        player.addWinnings(updatedWinnings);
        playerWinningsText.setText("Total Winnings: $" + updatedWinnings);
    }

    /**
     * Displays an information message after a delay.
     * @param message The message to display
     * @param delayInSeconds The delay before displaying the message
     */
    private void showInfoAfterDelay(String message, int delayInSeconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(delayInSeconds));
        pause.setOnFinished(event -> showInfo(message));
        pause.play();
    }

    /**
     * Resets all wager values for Player 1 to 0.
     */
    private void resetWagers1() {
        setAnteWager("0");
        setPairPlusWager("0");
        setPlayWager("0");
    }

    /**
     * Resets all wager values for Player 2 to 0.
     */
    private void resetWagers2() {
        setPlayer2AnteWager("0");
        setPlayer2PairPlusWager("0");
        setPlayWager("0");
    }
}
