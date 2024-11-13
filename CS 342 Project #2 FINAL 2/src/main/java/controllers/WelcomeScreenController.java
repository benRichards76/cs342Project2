package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class WelcomeScreenController {

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        // Add event handler for playButton
        playButton.setOnAction(this::handlePlayButtonAction);

        // Add event handler for exitButton
        exitButton.setOnAction(this::handleExitButtonAction);
    }

    private void handlePlayButtonAction(ActionEvent event) {
        // Logic to navigate to the next scene (e.g., Ante Wager Scene)
        System.out.println("Play button clicked");
        // You would replace this with logic to load the next FXML scene
    }

    private void handleExitButtonAction(ActionEvent event) {
        // Close the application
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
