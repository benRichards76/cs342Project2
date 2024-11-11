package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

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
        try {
            // Load the next scene's FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Player1AnteScreen.fxml"));
            Parent root = loader.load();

            // Get the current stage from the playButton
            Stage stage = (Stage) playButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Log any exception for troubleshooting
        }
    }

    private void handleExitButtonAction(ActionEvent event) {
        // Close the application
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
