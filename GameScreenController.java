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
            player1Folded = false;
            player2Folded = false;
            player1Played = false;
            player2Played = false;
            // Player 1 Wager Input Dialogs
            if (!p1NextHand) {
                if (nextAnte1) {
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
                }


                TextInputDialog player1PairPlusDialog = new TextInputDialog();
                player1PairPlusDialog.setTitle("Player 1 Pair Plus Wager");
                player1PairPlusDialog.setHeaderText(null);
                player1PairPlusDialog.setContentText("Enter Player 1 Pair Plus (optional, between $5 and $25):");

                Optional<String> player1PairPlusResult = player1PairPlusDialog.showAndWait();
                if (player1PairPlusResult.isPresent()) {
                    String result = player1PairPlusResult.get();
                    if (isValidWager(result, 5, 25)) {
                        setPairPlusWager(result);
                    } else {
                        showAlert("Invalid pair plus wager for Player 1. Please enter a value between $5 and $25.");
                        return; // Stop if invalid
                    }
                } else {
                    // If the dialog is cancelled, set the wager to 0
                    setPairPlusWager("0");
                }
            }

            // Player 2 Wager Input Dialogs
            if (!p2NextHand) {
                if (nextAnte2) {
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
                }


                TextInputDialog player2PairPlusDialog = new TextInputDialog();
                player2PairPlusDialog.setTitle("Player 2 Pair Plus Wager");
                player2PairPlusDialog.setHeaderText(null);
                player2PairPlusDialog.setContentText("Enter Player 2 Pair Plus (optional, between $5 and $25):");

                Optional<String> player2PairPlusResult = player2PairPlusDialog.showAndWait();
                if (player2PairPlusResult.isPresent()) {
                    String result = player2PairPlusResult.get();
                    if (isValidWager(result, 5, 25)) {
                        setPlayer2PairPlusWager(result);
                    } else {
                        showAlert("Invalid pair plus wager for Player 2. Please enter a value between $5 and $25.");
                        return; // Stop if invalid
                    }
                } else {
                    // If the dialog is cancelled, set the wager to 0
                    setPlayer2PairPlusWager("0");
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
        p1NextHand = false;
        nextAnte1 = true;
        resetWagers1();
        player1FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        showInfo("Player 1 has folded."); // here
        checkAllActionsCompleted();
    }

    @FXML
    private void handlePlayer2Fold() {
        player2Folded = true;
        p2NextHand = false;
        nextAnte2 = true;
        resetWagers2();
        player2FoldButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        showInfo("Player 2 has folded."); // here
        checkAllActionsCompleted();
    }

    @FXML
    private void handlePlayer1PlayBet() {
        if (player1Folded) {
            showInfo("Player 1 has folded and cannot place a play bet."); // here
            return;
        }

        // Set Player 1's play wager to be equal to their ante wager
        String anteWager = anteInfo.getText().replace("ANTE: ", "");
        setPlayWager(anteWager);
        player1FoldButton.setDisable(true);
        player1PlayBetButton.setDisable(true);
        player1Played = true;
        showInfo("Player 1's play wager has been set to the ante wager: " + anteWager); // here
        checkAllActionsCompleted();
    }

    @FXML
    private void handlePlayer2PlayBet() {
        if (player2Folded) {
            showInfo("Player 2 has folded and cannot place a play bet."); // here
            return;
        }

        // Set Player 2's play wager to be equal to their ante wager
        String anteWager = anteInfo2.getText().replace("ANTE: ", "");
        setPlayer2PlayWager(anteWager);
        player2FoldButton.setDisable(true);
        player2PlayBetButton.setDisable(true);
        player2Played = true;
        showInfo("Player 2's play wager has been set to the ante wager: " + anteWager); // here
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

        ThreeCardLogic threeCardLogic = new ThreeCardLogic();
        String wager = "0";

        // Reveal dealer cards
        animateCardDeal(deckCardImage, dealerCard1Image, "/deckOfCards/" + dealer.getHand().get(0) + ".png", 0);
        animateCardDeal(deckCardImage, dealerCard2Image, "/deckOfCards/" + dealer.getHand().get(1) + ".png", 0);
        animateCardDeal(deckCardImage, dealerCard3Image, "/deckOfCards/" + dealer.getHand().get(2) + ".png", 0);
        threeCardLogic.callSort(dealer.getHand());

        // Check if dealer qualifies
        boolean dealerQualifies = dealer.getHand().get(0).getValue() >= 12; // Queen high or better

        // Handle Player 1 evaluation
        if (!player1Folded) {
            // Evaluate Pair Plus
            int pairPlusWinnings = threeCardLogic.evalPPWinnings(player1.getHand(), player1.getPairPlusBet());
            if (pairPlusWinnings > 0) {
                showInfo("Player 1 wins Pair Plus");
            } else if (player1.getPairPlusBet() > 0) {
                showInfo("Player 1 loses Pair Plus");
            }
            setTotalWinnings(player1Winnings, String.valueOf(pairPlusWinnings), player1);

            if (!dealerQualifies) {
                // Dealer does not qualify: return play wager and push ante bet
                nextAnte1 = false;
                setPairPlusWager(wager);
                setPlayWager(wager); // we chose to not add it to winnings because it is said the play wager is returned, so you don't "win" more money
                showInfo("Dealer does not qualify. Player 1's play wager is returned, and the ante is pushed.");
            } else {
                nextAnte1 = true;
                // Dealer qualifies: compare hands
                int p1Result = threeCardLogic.compareHands(dealer.getHand(), player1.getHand());
                int playAndAnteWinnings = player1.getAnteBet() + player1.getPlayBet(); // Play and Ante payout

                if (p1Result == 1) {
                    // Dealer wins
                    player1EvalString = "Player 1 loses to dealer";
                    showInfo(player1EvalString);
                    resetWagers1();
                } else if (p1Result == 2) {
                    // Player wins
                    player1EvalString = "Player 1 beats dealer";
                    showInfo(player1EvalString);
                    resetWagers1();
                    setTotalWinnings(player1Winnings, String.valueOf(playAndAnteWinnings), player1);
                } else if (p1Result == 0) {
                    // Tie
                    player1EvalString = "Player 1 ties dealer";
                    showInfo(player1EvalString);
                    // Ante and Play bets are pushed, no changes needed
                }
            }
        }

        // Handle Player 2 evaluation
        if (!player2Folded) {
            // Evaluate Pair Plus
            int pairPlusWinnings = threeCardLogic.evalPPWinnings(player2.getHand(), player2.getPairPlusBet());
            if (pairPlusWinnings > 0) {
                showInfo("Player 2 wins Pair Plus");
            } else if (player2.getPairPlusBet() > 0) {
                showInfo("Player 2 loses Pair Plus");
            }
            setTotalWinnings(player2Winnings, String.valueOf(pairPlusWinnings), player2);

            if (!dealerQualifies) {
                // Dealer does not qualify: return play wager and push ante bet
                nextAnte2 = false;
                setPlayer2PairPlusWager(wager);
                setPlayer2PlayWager(wager); // we chose to not add it to winnings because it is said the play wager is returned, so you don't "win" more money
                showInfo("Dealer does not qualify. Player 2's play wager is returned, and the ante is pushed.");
            } else {
                // Dealer qualifies: compare hands
                nextAnte2 = true;
                int p2Result = threeCardLogic.compareHands(dealer.getHand(), player2.getHand());
                int playAndAnteWinnings = player2.getAnteBet() + player2.getPlayBet(); // Play and Ante payout

                if (p2Result == 1) {
                    // Dealer wins
                    player2EvalString = "Player 2 loses to dealer";
                    showInfo(player2EvalString);
                    resetWagers2();
                } else if (p2Result == 2) {
                    // Player wins
                    player2EvalString = "Player 2 beats dealer";
                    showInfo(player2EvalString);
                    resetWagers2();
                    setTotalWinnings(player2Winnings, String.valueOf(playAndAnteWinnings), player2);
                } else if (p2Result == 0) {
                    // Tie
                    player2EvalString = "Player 2 ties dealer";
                    showInfo(player2EvalString);
                    // Ante and Play bets are pushed, no changes needed
                }
            }
        }

        showInfo("Press Place Bets button in top right to start new hand");
    }

//    private void evaluateDealerHand() { //wrong PP totalSum
//        String player1EvalString;
//        String player2EvalString;
//        String player1PPString;
//        String player2PPString;
//
//        ThreeCardLogic threeCardLogic = new ThreeCardLogic();
//        String wager = "0";
//
//        animateCardDeal(deckCardImage, dealerCard1Image, "/deckOfCards/" + dealer.getHand().get(0) + ".png", 0);
//        animateCardDeal(deckCardImage, dealerCard2Image, "/deckOfCards/" + dealer.getHand().get(1) + ".png", 0);
//        animateCardDeal(deckCardImage, dealerCard3Image, "/deckOfCards/" + dealer.getHand().get(2) + ".png", 0);
//        threeCardLogic.callSort(dealer.getHand());
////        for (Card card : dealer.getHand()) {
////            System.out.println(card);
////        }
////        System.out.println(dealer.getTheDeck().size());
//        if (dealer.getHand().get(0).getValue() < 12) {
////            if (!player1Folded) {
////                p1NextHand = true;
////            }
////            if (!player2Folded) {
////                p2NextHand = true;
////            }
//
////            System.out.println("dealer less than queen");
//            // Dealer does not qualify, return play wagers and push ante bets
//            if (!player1Folded) {
//
//                if (threeCardLogic.evalPPWinnings(player1.getHand(), player1.getPairPlusBet()) != 0) {
//                    showInfo("Player 1 wins Pair Plus"); // here
//                }
//                else if (player1.getPairPlusBet() != 0){
//                    showInfo("Player 1 loses Pair Plus"); // here
//                }
//                setTotalWinnings(player2Winnings, String.valueOf(threeCardLogic.evalPPWinnings(player1.getHand(), player1.getPairPlusBet())), player1);
//                String playWager = playWagerInfo.getText().replace("PLAY WAGER: ", "");
//                setTotalWinnings(player1Winnings, playWager, player1);
//                setPlayWager(wager);
//                showInfo("Dealer does not qualify. Player 1's play wager is returned, and the ante is pushed."); // here
//            }
//            if (!player2Folded) {
//                if (threeCardLogic.evalPPWinnings(player2.getHand(), player2.getPairPlusBet()) != 0) {
//                    showInfo("Player 2 wins Pair Plus"); // here
//                }
//                else if (player2.getPairPlusBet() != 0){
//                    showInfo("Player 2 loses Pair Plus"); // here
//                }
//                setTotalWinnings(player2Winnings, String.valueOf(threeCardLogic.evalPPWinnings(player2.getHand(), player2.getPairPlusBet())), player2);
//                String playWager2 = playWagerInfo2.getText().replace("PLAY WAGER: ", "");
//                setTotalWinnings(player2Winnings, playWager2, player2);
//                setPlayer2PlayWager(wager);
//                showInfo("Dealer does not qualify. Player 2's play wager is returned, and the ante is pushed."); // here
//            }
////            dealCards();
//        }
//        else {
//            p1NextHand = false;
//            p2NextHand = false;
//            int p1Result;
//            int p2Result;
//            int combo = 0;
//
//            if (!player1Folded) {
//                if (threeCardLogic.evalPPWinnings(player1.getHand(), player1.getPairPlusBet()) != 0) {
//                    showInfo("Player 1 wins Pair Plus"); // here
//                }
//                else if (player1.getPairPlusBet() != 0){
//                    showInfo("Player 1 loses Pair Plus"); // here
//                }
//
//
//                p1Result = threeCardLogic.compareHands(dealer.getHand(), player1.getHand());
//                System.out.println(combo + "before adding ante and play bet x2");
//                combo = (player1.getAnteBet() + player1.getPlayBet()) * 2;
//                System.out.println(combo + "after adding ante and play bet x2");
//                combo += threeCardLogic.evalPPWinnings(player1.getHand(), player1.getPairPlusBet());
//                System.out.println(combo + "after adding PP");
//                if (p1Result == 1) {
////                    if (player1.getPairPlusBet() != 0) {
////                        player1PPString = "Player 1 loses Pair Plus";
////                        showInfo(player1PPString); // here
////                    }
//                    player1EvalString = "Player 1 loses to dealer";
//                    showInfo(player1EvalString); // here
//                    setAnteWager(wager);
//                    setPairPlusWager(wager);
//                    setPlayWager(wager);
//                }
//                else if (p1Result == 2) {
////                    if (player1.getPairPlusBet() != 0) {
////                        player1PPString = "Player 1 wins Pair Plus";
////                        showInfo(player1PPString); // here
////                    }
//                    player1EvalString = "Player 1 beats dealer";
//                    showInfo(player1EvalString); // here
//                    setAnteWager(wager);
//                    setPairPlusWager(wager);
//                    setPlayWager(wager);
//                    setTotalWinnings(player1Winnings, String.valueOf(combo), player1);
//                }
//                else if (p1Result == 0) {
////                    p1NextHand = true;
//                    player1EvalString = "Player 1 ties dealer";
//                    showInfo(player1EvalString); // here
//                }
//            }
//            if (!player2Folded) {
//                if (threeCardLogic.evalPPWinnings(player2.getHand(), player2.getPairPlusBet()) != 0) {
//                    showInfo("Player 2 wins Pair Plus"); // here
//                }
//                else if (player2.getPairPlusBet() != 0){
//                    showInfo("Player 2 loses Pair Plus"); // here
//                }
//
//
//                p2Result = threeCardLogic.compareHands(dealer.getHand(), player2.getHand());
//                System.out.println(combo + "before adding ante and play bet x2");
//                combo = (player2.getAnteBet() + player2.getPlayBet()) * 2;
//                System.out.println(combo + "after adding ante and play bet x2");
//                combo += threeCardLogic.evalPPWinnings(player2.getHand(), player2.getPairPlusBet());
//                System.out.println(combo + "after adding PP");
//
//                if (p2Result == 1) {
////                    if (player2.getPairPlusBet() != 0) {
////                        player2PPString = "Player 2 loses Pair Plus";
////                        showInfo(player2PPString); // here
////                    }
//                    player2EvalString = "Player 2 loses to dealer";
//                    showInfo(player2EvalString); // here
//                    setPlayer2AnteWager(wager);
//                    setPlayer2PairPlusWager(wager);
//                    setPlayer2PlayWager(wager);
//                }
//                else if (p2Result == 2) {
////                    if (player2.getPairPlusBet() != 0) {
////                        player2PPString = "Player 2 wins Pair Plus";
////                        showInfo(player2PPString); // here
////                    }
//                    player2EvalString = "Player 2 beats dealer";
//                    showInfo(player2EvalString); // here
//                    setPlayer2AnteWager(wager);
//                    setPlayer2PairPlusWager(wager);
//                    setPlayer2PlayWager(wager);
//                    setTotalWinnings(player2Winnings, String.valueOf(combo), player2);
//                }
//                else if (p2Result == 0) {
////                    p2NextHand = true;
//                    player2EvalString = "Player 2 ties dealer";
//                    showInfo(player2EvalString); // here
//                }
//
//            }
//
//            // Dealer qualifies, continue with game logic
//            // TODO: Implement further game logic to compare hands and determine winners/losers
//
//        }
//        showInfo("Press Place Bets button in top right to start new hand"); // here
//
//    }

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
        player1 = new Player();
        player2 = new Player();
        dealer = new Dealer();
        player1Folded = false;
        player2Folded = false;
        player1Played = false;
        player2Played = false;
        betsPlaced = false;
        nextAnte1 = true;
        nextAnte2 = true;
        currentWinnings = 0;
        playWagerAmount = 0;
        updatedWinnings = 0;
        p1NextHand = false;
        p2NextHand = false;
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
        player1Card1Image.setImage(null);
        player1Card2Image.setImage(null);
        player1Card3Image.setImage(null);
        player2Card1Image.setImage(null);
        player2Card2Image.setImage(null);
        player2Card3Image.setImage(null);
        dealerCard1Image.setImage(null);
        dealerCard2Image.setImage(null);
        dealerCard3Image.setImage(null);
        showInfo("Game reset, press Place Bets button in top right to start new hand");
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
        currentWinnings = Integer.parseInt(playerWinningsText.getText().replace("Total Winnings: $", ""));
        playWagerAmount = Integer.parseInt(wager);
        updatedWinnings = currentWinnings + playWagerAmount;
        player.addWinnings(playWagerAmount);
        playerWinningsText.setText("Total Winnings: $" + updatedWinnings);
    }

    private void showInfoAfterDelay(String message, int delayInSeconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(delayInSeconds));
        pause.setOnFinished(event -> showInfo(message));
        pause.play();
    }

    private void resetWagers1() {
        setAnteWager("0");
        setPairPlusWager("0");
        setPlayWager("0");
    }

    private void resetWagers2() {
        setPlayer2AnteWager("0");
        setPlayer2PairPlusWager("0");
        setPlayer2PlayWager("0");
    }
}
