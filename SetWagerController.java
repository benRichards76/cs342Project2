package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

/**
 * Controller for setting wagers for Player 1 and Player 2.
 * Validates ante wagers and sets Pair Plus wagers if provided.
 */
public class SetWagerController {

    // Text fields for Player 1 and Player 2 ante and pair plus wagers
    @FXML
    private TextField player1Ante, player1PairPlus, player2Ante, player2PairPlus;

    // Button to start the game after setting wagers
    @FXML
    private Button startGameButton;

    // Callback to trigger when the game starts
    private Runnable onStartGame;

    /**
     * Initializes the controller by setting the start game button action.
     */
    @FXML
    public void initialize() {
        startGameButton.setOnAction(event -> handleStartGame());
    }

    /**
     * Sets a callback to be executed when the game starts.
     * @param onStartGame Runnable callback function
     */
    public void setOnStartGame(Runnable onStartGame) {
        this.onStartGame = onStartGame;
    }

    // Getter methods for retrieving player wager values from text fields
    public String getPlayer1Ante() {
        return player1Ante.getText();
    }

    public String getPlayer1PairPlus() {
        return player1PairPlus.getText();
    }

    public String getPlayer2Ante() {
        return player2Ante.getText();
    }

    public String getPlayer2PairPlus() {
        return player2PairPlus.getText();
    }

    /**
     * Handles the action to start the game. Validates ante wagers for both players,
     * sets pair plus wagers if provided, and loads the game screen.
     */
    private void handleStartGame() {
        try {
            // Parse ante wager values for validation
            int player1AnteValue = Integer.parseInt(getPlayer1Ante());
            int player2AnteValue = Integer.parseInt(getPlayer2Ante());

            // Validate ante wagers for both players
            if (validateAnte(player1AnteValue) && validateAnte(player2AnteValue)) {
                // Load the GameScreen.fxml file and obtain its controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SetWager.fxml"));
                Parent root = loader.load();
                GameScreenController controller = loader.getController();

                // Set Player 1 and Player 2 ante wagers in GameScreenController
                controller.setAnteWager(getPlayer1Ante());
                controller.setPlayer2AnteWager(getPlayer2Ante());

                // Set Player 1 and Player 2 pair plus wagers if present
                if (!getPlayer1PairPlus().isEmpty()) {
                    controller.setPairPlusWager(getPlayer1PairPlus());
                }
                if (!getPlayer2PairPlus().isEmpty()) {
                    controller.setPlayer2PairPlusWager(getPlayer2PairPlus());
                }

                // Replace the current stage with the game screen
                Stage stage = (Stage) startGameButton.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 700));
                stage.show();

            } else {
                // Show error if ante wagers are outside the valid range
                showAlert("Ante wagers must be between $5 and $25.");
            }
        } catch (NumberFormatException e) {
            // Show error if the input for wagers is invalid
            showAlert("Please enter valid wager amounts.");
        } catch (IOException e) {
            // Handle any errors in loading the game screen
            e.printStackTrace();
            showAlert("Error loading game screen.");
        }
    }

    /**
     * Validates that an ante wager is within the allowed range ($5 to $25).
     * @param wager The ante wager to validate
     * @return True if wager is within range, false otherwise
     */
    private boolean validateAnte(int wager) {
        return wager >= 5 && wager <= 25;
    }

    /**
     * Displays an error alert with the given message.
     * @param message The error message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
