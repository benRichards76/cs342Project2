package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

/**
 * Controller for the welcome screen, handling actions for starting the game and exiting.
 */
public class WelcomeScreenController {

    // Button to start the game
    @FXML
    private Button playButton;

    // Button to exit the application
    @FXML
    private Button exitButton;

    /**
     * Initializes the welcome screen by setting up actions for the play and exit buttons.
     */
    @FXML
    public void initialize() {
        playButton.setOnAction(event -> handlePlayButtonAction());
        exitButton.setOnAction(event -> handleExitButtonAction());
    }

    /**
     * Loads the game screen when the play button is pressed.
     * Sets the screen to full screen or maximized.
     */
    private void handlePlayButtonAction() {
        try {
            // Load the GameScreen.fxml layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameScreen.fxml"));
            Parent root = loader.load();

            // Get the current stage and set it to show the game screen
            Stage stage = (Stage) playButton.getScene().getWindow();

            // Create a new scene with large dimensions and set it to the stage
            Scene scene = new Scene(root, 2000, 2000);
            stage.setScene(scene);

            // Maximize the window or enter full screen mode
            stage.setMaximized(true);
            stage.setFullScreen(true); // Optional: full screen mode without window borders
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log exception if loading fails
        }
    }

    /**
     * Closes the application when the exit button is pressed.
     */
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
