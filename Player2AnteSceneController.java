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
    private Button finishButton;

    private int anteWagerValue = 5;

    @FXML
    public void initialize() {
        anteWagerText.setText("$" + anteWagerValue);

        subtractButton.setOnAction(event -> {
            if (anteWagerValue > 5) {
                anteWagerValue--;
                anteWagerText.setText("$" + anteWagerValue);
                addButton.setDisable(false);
            }
            if (anteWagerValue == 5) {
                subtractButton.setDisable(true);
            }
        });

        addButton.setOnAction(event -> {
            if (anteWagerValue < 25) {
                anteWagerValue++;
                anteWagerText.setText("$" + anteWagerValue);
                subtractButton.setDisable(false);
            }
            if (anteWagerValue == 25) {
                addButton.setDisable(true);
            }
        });

        // Configure finish button action
        finishButton.setOnAction(event -> {
            Stage stage = (Stage) finishButton.getScene().getWindow();
            stage.close(); // Replace with navigation logic as needed
        });
    }
}
