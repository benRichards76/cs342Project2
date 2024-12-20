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
import javafx.util.Duration;
import java.io.IOException;
import java.util.Optional;

import main.java.Dealer;
import main.java.Player;
import main.java.Card;
import main.java.ThreeCardLogic;

public class GameScreenController {

    @FXML private Button exitButton, optionsButton, freshStartButton, newLookButton, placeBetsButton;
    @FXML private Button player1FoldButton, player2FoldButton, player1PlayBetButton, player2PlayBetButton;
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
    private boolean player1Folded = false;
    private boolean player2Folded = false;
    private boolean player1Played = false;
    private boolean player2Played = false;
    private int currentWinnings = 0;
    private int playWagerAmount = 0;
    private int updatedWinnings = 0;

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
            optionsButton.setOnAction(event -> toggleOptionsPane());
            newLookButton.setOnAction(event -> handleNewLook());
            exitButton.setOnAction(event -> handleExit());
            freshStartButton.setOnAction(event -> handleFreshStart());
            placeBetsButton.setOnAction(event -> handlePlaceBets());
            player1FoldButton.setOnAction(event -> handlePlayer1Fold());
            player2FoldButton.setOnAction(event -> handlePlayer2Fold());
            player1PlayBetButton.setOnAction(event -> handlePlayer1PlayBet());
            player2PlayBetButton.setOnAction(event -> handlePlayer2PlayBet());

            // Initialize player and dealer instances once
            dealer = new Dealer();
            player1 = new Player();
            player2 = new Player();

            // Disable fold and play buttons until bets are placed and cards are dealt
            player1FoldButton.setDisable(true);
            player2FoldButton.setDisable(true);
            player1PlayBetButton.setDisable(true);
            player2PlayBetButton.setDisable(true);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions during setup to prevent crashes
        }
    }

    @FXML
    private void handlePlaceBets() {
        try {
            if (player1Folded && player2Folded) {
                showAlert("Both players have folded. Please start a new game.");
                return;
            }

            // Player 1 Wager Input Dialogs
            if (!player1Folded) {
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
            }

            // Player 2 Wager Input Dialogs
            if (!player2Folded) {
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
            }

            // Mark bets as placed and start dealing cards
            betsPlaced = true;
            dealCards();

            // Enable fold and play buttons after bets are placed and cards are dealt
            player1FoldButton.setDisable(false);
            player2FoldButton.setDisable(false);
            player1PlayBetButton.setDisable(false);
            player2PlayBetButton.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("An error occurred while placing bets.");
        }
    }

    @FXML
    private void handlePlayer1Fold() {
        player1Folded = true;
        setAnteWager("0");
        setPairPlusWager("0");
        setPlayWager("0");
        player1FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        showInfo("Player 1 has folded.");
        checkAllActionsCompleted();
    }

    @FXML
    private void handlePlayer2Fold() {
        player2Folded = true;
        setPlayer2AnteWager("0");
        setPlayer2PairPlusWager("0");
        setPlayer2PlayWager("0");
        player2FoldButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        showInfo("Player 2 has folded.");
        checkAllActionsCompleted();
    }

    @FXML
    private void handlePlayer1PlayBet() {
        if (player1Folded) {
            showInfo("Player 1 has folded and cannot place a play bet.");
            return;
        }

        // Set Player 1's play wager to be equal to their ante wager
        String anteWager = anteInfo.getText().replace("ANTE: ", "");
        setPlayWager(anteWager);
        player1FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        player1Played = true;
        showInfo("Player 1's play wager has been set to the ante wager: " + anteWager);
        checkAllActionsCompleted();
    }

    @FXML
    private void handlePlayer2PlayBet() {
        if (player2Folded) {
            showInfo("Player 2 has folded and cannot place a play bet.");
            return;
        }

        // Set Player 2's play wager to be equal to their ante wager
        String anteWager = anteInfo2.getText().replace("ANTE: ", "");
        setPlayer2PlayWager(anteWager);
        player2FoldButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        player2Played = true;
        showInfo("Player 2's play wager has been set to the ante wager: " + anteWager);
        checkAllActionsCompleted();
    }

    private void checkAllActionsCompleted() {
        if ((player1Folded || player1Played) && (player2Folded || player2Played)) {
            evaluateDealerHand();
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

        // Re-deal hands for each new round
        dealer.setHand(dealer.dealHand());
        for (Card card : dealer.getHand()) {
            System.out.println(card.getValue());
        }
        if (!player1Folded) {
            player1.setHand(dealer.dealHand());
        }
        if (!player2Folded) {
            player2.setHand(dealer.dealHand());
        }

        try {
            setupCardImage(deckCardImage, "/deckOfCards/BackOfCard.png");
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

    private void evaluateDealerHand() {
        String player1EvalString;
        String player2EvalString;
        String player1PPString;
        String player2PPString;

        ThreeCardLogic threeCardLogic = new ThreeCardLogic();
        String wager = "0";

        animateCardDeal(deckCardImage, dealerCard1Image, "/deckOfCards/" + dealer.getHand().get(0) + ".png", 1000);
        animateCardDeal(deckCardImage, dealerCard2Image, "/deckOfCards/" + dealer.getHand().get(1) + ".png", 2500);
        animateCardDeal(deckCardImage, dealerCard3Image, "/deckOfCards/" + dealer.getHand().get(2) + ".png", 4000);
        threeCardLogic.callSort(dealer.getHand());
        for (Card card : dealer.getHand()) {
            System.out.println(card.getValue());
        }
        System.out.println(dealer.getTheDeck().size());
        if (dealer.getHand().get(0).getValue() < 12) {

            System.out.println("dealer less than queen");
            // Dealer does not qualify, return play wagers and push ante bets
            if (!player1Folded) {
                String playWager = playWagerInfo.getText().replace("PLAY WAGER: ", "");
                setTotalWinnings(player1Winnings, playWager, player1);
                setPlayWager(wager);
                showInfo("Dealer does not qualify. Player 1's play wager is returned, and the ante is pushed.");
            }
            if (!player2Folded) {
                String playWager2 = playWagerInfo2.getText().replace("PLAY WAGER: ", "");
                setTotalWinnings(player2Winnings, playWager2, player2);
                setPlayer2PlayWager(wager);
                showInfo("Dealer does not qualify. Player 2's play wager is returned, and the ante is pushed.");
            }
            dealCards();
        } else {
            int p1Result;
            int p2Result;
            int combo = 0;

            if (!player1Folded) {
                p1Result = threeCardLogic.compareHands(dealer.getHand(), player1.getHand());
                combo = (player1.getAnteBet() + player1.getPlayBet()) * 2;

                if (p1Result == 1) {
                    if (player1.getPairPlusBet() != 0) {
                        player1PPString = "Player 1 loses Pair Plus";
                        showInfo(player1PPString);
                    }
                    player1EvalString = "Player 1 loses to dealer";
                    showInfo(player1EvalString);
                    setAnteWager(wager);
                    setPairPlusWager(wager);
                }
                else if (p1Result == 2) {
                    if (player1.getPairPlusBet() != 0) {
                        player1PPString = "Player 1 wins Pair Plus";
                        showInfo(player1PPString);
                    }
                    player1EvalString = "Player 1 loses to dealer";
                    showInfo(player1EvalString);
                    setAnteWager(wager);
                    setPairPlusWager(wager);
                    setTotalWinnings(player1Winnings, String.valueOf(combo), player1);
                }
            }
            p2Result = threeCardLogic.compareHands(dealer.getHand(), player2.getHand());
            // Dealer qualifies, continue with game logic
            // TODO: Implement further game logic to compare hands and determine winners/losers
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

    // Set Player 1's Ante wager value
    public void setAnteWager(String wager) {
        int p1Ante = Integer.parseInt(wager);
        player1.setAnteBet(p1Ante);
        anteInfo.setText("ANTE: " + wager);
    }

    // Set Player 1's Pair Plus wager value
    public void setPairPlusWager(String wager) {
        int p1Pair = Integer.parseInt(wager);
        player1.setPairPlusBet(p1Pair);
        pairPlusInfo.setText("PAIR PLUS: " + wager);
    }

    // Set Player 1's Play wager value
    public void setPlayWager(String wager) {
        int p1Play = Integer.parseInt(wager);
        player1.setPlayBet(p1Play);
        playWagerInfo.setText("PLAY WAGER: " + wager);
    }

    // Set Player 2's Ante wager value
    public void setPlayer2AnteWager(String wager) {
        int p2Ante = Integer.parseInt(wager);
        player2.setAnteBet(p2Ante);
        anteInfo2.setText("ANTE: " + wager);
    }

    // Set Player 2's Pair Plus wager value
    public void setPlayer2PairPlusWager(String wager) {
        int p2Pair = Integer.parseInt(wager);
        player2.setPairPlusBet(p2Pair);
        pairPlusInfo2.setText("PAIR PLUS: " + wager);
    }

    // Set Player 2's Play wager value
    public void setPlayer2PlayWager(String wager) {
        int p2Play = Integer.parseInt(wager);
        player2.setPlayBet(p2Play);
        playWagerInfo2.setText("PLAY WAGER: " + wager);
    }

    public void setTotalWinnings(Text playerWinningsText, String wager, Player player) {
        currentWinnings += Integer.parseInt(playerWinningsText.getText().replace("Total Winnings: $", ""));
        playWagerAmount += Integer.parseInt(wager);
        updatedWinnings += currentWinnings + playWagerAmount;
        player.addWinnings(updatedWinnings);
        playerWinningsText.setText("Total Winnings: $" + updatedWinnings);
    }
}
