package controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * Controller for the options screen, handling actions for exiting,
 * starting a fresh game, and applying a new look.
 */
public class OptionScreenController {

    // Button to exit the application
    @FXML
    private Button exitButton;

    // Button to start a fresh game
    @FXML
    private Button freshStartButton;

    // Button to apply a new look/style
    @FXML
    private Button newLookButton;

    /**
     * Initializes the option screen by setting up button actions.
     */
    @FXML
    public void initialize() {
        exitButton.setOnAction(event -> handleExit());
        freshStartButton.setOnAction(event -> handleFreshStart());
        newLookButton.setOnAction(event -> handleNewLook());
    }

    /**
     * Closes the current window when the exit button is pressed.
     */
    public void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Resets the game state when the fresh start button is pressed.
     * Additional logic to reset game scores or state can be added here.
     */
    public void handleFreshStart() {
        // Logic to reset game state; e.g., resetting scores
    }

    /**
     * Applies a new look/style to the scene by loading a new stylesheet.
     */
    public void handleNewLook() {
        Scene scene = exitButton.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add("/newlook.css");
    }
}
