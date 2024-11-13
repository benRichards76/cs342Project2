package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Player2AnteSceneController {

    @FXML
    private Button subtractButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField anteWagerText;

    @FXML
    private Button nextButton;

    private int anteWagerValue = 5; // Default ante wager value for Player 2

    @FXML
    public void initialize() {
        anteWagerText.setText("$" + anteWagerValue);

        subtractButton.setOnAction(event -> {
            if (anteWagerValue > 5) {
                anteWagerValue--;
                anteWagerText.setText("$" + anteWagerValue);
                addButton.setDisable(false); // Enable the add button
            }
            if (anteWagerValue == 5) {
                subtractButton.setDisable(true); // Disable the subtract button at the minimum value
            }
        });

        addButton.setOnAction(event -> {
            if (anteWagerValue < 25) {
                anteWagerValue++;
                anteWagerText.setText("$" + anteWagerValue);
                subtractButton.setDisable(false); // Enable the subtract button
            }
            if (anteWagerValue == 25) {
                addButton.setDisable(true); // Disable the add button at the maximum value
            }
        });

        nextButton.setOnAction(event -> {
            // Logic to proceed to the next scene, e.g., game scene
            // Stage stage = (Stage) nextButton.getScene().getWindow();
            // stage.setScene(new Scene(...)); // Load the next scene here
            System.out.println("Player 2 Ante Wager placed: $" + anteWagerValue);
        });
    }
}
