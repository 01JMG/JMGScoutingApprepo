package jmg.scoutingapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateActualMatchStatsReportController {

    @FXML
    private TextField playerNameField; // Assuming you'll still need a player's name
    
    // Attacking Stats
    @FXML
    private TextField  nonPenaltyGoalsArea;
    @FXML
    private TextField  npxGPer90Area;
    @FXML
    private TextField  shotsTotalPer90Area;
    @FXML
    private TextField  assistsPer90Area;
    @FXML
    private TextField  xAGPer90Area;
    @FXML
    private TextField  npxGPlusxAGPer90Area;
    @FXML
    private TextField  scaPer90Area;

    // Possession/Passing Stats
    @FXML
    private TextField  passesAttemptedPer90Area;
    @FXML
    private TextField  passCompletionPercentageArea;
    @FXML
    private TextField  progressivePassesPer90Area;
    @FXML
    private TextField  progressiveCarriesPer90Area;
    @FXML
    private TextField  successfulTakeOnsPer90Area;
    @FXML
    private TextField  touchesAttPenPer90Area;
    @FXML
    private TextField progressivePassesRecPer90Area;

    // Defensive Stats
    @FXML
    private TextField  tacklesPer90Area;
    @FXML
   private TextField interceptionsPer90Area;
    @FXML
    private TextField  blocksPer90Area;
    @FXML
    private TextField  clearancesPer90Area;
    @FXML
    private TextField  aerialsWonPer90Area;

    // Action to handle saving the report
    @FXML
    private void handleSave() {
        String playerName = playerNameField.getText();
        
        // You would get the text from each TextArea here
        String nonPenaltyGoals = nonPenaltyGoalsArea.getText();
        // ...and so on for all the other stats
        
        // This is a placeholder for your save logic
        // It's similar to the previous controller but with the new stats
        System.out.println("Saving match stats for player: " + playerName);
        System.out.println("Non-Penalty Goals per 90: " + nonPenaltyGoals);
        // ... and so on
        
        showAlert("Success", "Match Stats Report saved successfully!");
    }
    
    // Action to handle clearing the form
    @FXML
    private void handleClear() {
        playerNameField.clear();
        nonPenaltyGoalsArea.clear();
        npxGPer90Area.clear();
        shotsTotalPer90Area.clear();
        assistsPer90Area.clear();
        xAGPer90Area.clear();
        npxGPlusxAGPer90Area.clear();
        scaPer90Area.clear();
        passesAttemptedPer90Area.clear();
        passCompletionPercentageArea.clear();
        progressivePassesPer90Area.clear();
        progressiveCarriesPer90Area.clear();
        successfulTakeOnsPer90Area.clear();
        touchesAttPenPer90Area.clear();
        progressivePassesRecPer90Area.clear();
        tacklesPer90Area.clear();
        interceptionsPer90Area.clear();
        blocksPer90Area.clear();
        clearancesPer90Area.clear();
        aerialsWonPer90Area.clear();
    }
    
    // Helper method to show alerts
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}