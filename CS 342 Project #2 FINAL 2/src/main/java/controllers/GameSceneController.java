package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GameSceneController {

    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Text anteInfo;

    @FXML
    private Text pairPlusInfo;

    @FXML
    private Text playWagerInfo;

    @FXML
    private Text anteInfo2;

    @FXML
    private Text pairPlusInfo2;

    @FXML
    private Text playWagerInfo2;

    @FXML
    public void initialize() {
        // Example: Set initial values or event handlers
        anteInfo.setText("ANTE: $5");
        pairPlusInfo.setText("PAIR PLUS: $0");
        playWagerInfo.setText("PLAY WAGER: $0");

        anteInfo2.setText("ANTE: $5");
        pairPlusInfo2.setText("PAIR PLUS: $0");
        playWagerInfo2.setText("PLAY WAGER: $0");

        // Event handler for the exit button
        exitButton.setOnAction(event -> {
            System.exit(0); // Close the application
        });

        // Event handler for the options button
        optionsButton.setOnAction(event -> {
            // Add code to display options or navigate to an options scene
            System.out.println("Options button clicked");
        });
    }
}
