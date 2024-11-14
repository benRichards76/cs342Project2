package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class ExitConfirmationController {

    @FXML
    private Button continueButton;

    @FXML
    private Button quitButton;

    @FXML
    private void handleQuit() {
        System.exit(0);
    }

    @FXML
    private void handleContinue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameScreen.fxml"));
            Parent root = loader.load();

            // Get the current stage and replace the scene with the game screen
            Stage stage = (Stage) continueButton.getScene().getWindow();
            Scene gameScene = new Scene(root, stage.getWidth(), stage.getHeight());

            // Apply the same stylesheet as the welcome screen if needed
            gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            stage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


