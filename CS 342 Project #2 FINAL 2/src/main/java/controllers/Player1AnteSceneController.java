package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
                addButton.setDisable(false); // Ensure add button is enabled
            }
            if (anteWagerValue <= 5) {
                subtractButton.setDisable(true); // Disable if it reaches minimum value
            }
        });

        // Configure add button action
        addButton.setOnAction(event -> {
            if (anteWagerValue < 25) {
                anteWagerValue++;
                anteWagerText.setText("$" + anteWagerValue);
                subtractButton.setDisable(false); // Ensure subtract button is enabled
            }
            if (anteWagerValue >= 25) {
                addButton.setDisable(true); // Disable if it reaches maximum value
            }
        });

        // Configure next button action
        nextButton.setOnAction(event -> {
            // Here you would navigate to the next scene (e.g., player 2 ante wager scene)
            // You can pass data to the next scene or handle scene switching logic

            // Example placeholder logic to close the current stage:
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.close(); // This would need to be replaced with actual scene-switching logic
        });
    }
}
