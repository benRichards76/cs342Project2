package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class Player1AnteSceneController {

    @FXML
    private Button subtractButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField anteWagerText;

    @FXML
    private Button nextButton;

    private int anteWagerValue = 5; // Initial ante wager value

    @FXML
    public void initialize() {
        // Set initial value in the text field
        anteWagerText.setText("$" + anteWagerValue);

        // Configure subtract button action
        subtractButton.setOnAction(event -> {
            if (anteWagerValue > 5) {
                anteWagerValue--;
                anteWagerText.setText("$" + anteWagerValue);
                addButton.setDisable(false); // Enable add button
            }
            if (anteWagerValue == 5) {
                subtractButton.setDisable(true); // Disable if minimum value reached
            }
        });

        // Configure add button action
        addButton.setOnAction(event -> {
            if (anteWagerValue < 25) {
                anteWagerValue++;
                anteWagerText.setText("$" + anteWagerValue);
                subtractButton.setDisable(false); // Enable subtract button
            }
            if (anteWagerValue == 25) {
                addButton.setDisable(true); // Disable if maximum value reached
            }
        });

        // Configure next button action
        nextButton.setOnAction(event -> {
            try {
                // Load the Player 2 Ante Scene FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Player2AnteScreen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) nextButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
