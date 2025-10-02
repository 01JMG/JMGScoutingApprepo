package jmg.scoutingapp;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import jmg.scoutingapp.Player;
import jmg.scoutingapp.Player;
import jmg.scoutingapp.PlayerService;
import jmg.scoutingapp.PlayerService;


public class AddPlayerStatsToPlayerController {
    
    String playerName;
    String playerID;

    @FXML public TextField attackField;
    @FXML public TextField accelerationField;
    @FXML public TextField skillField;
    @FXML public TextField kickPowerField;
    @FXML public TextField accuracyField;
    @FXML public TextField defenseField;
    @FXML public TextField passingField;
    @FXML public TextField crossingField;
    @FXML public TextField dribbleField;
    @FXML public TextField aerialAbilityField;
    @FXML private Label statusLabel;

  

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        System.out.println("Adding stats for: " + playerName);
    }
    
    public void setPlayerID(String playerName) {
        this.playerID = playerName;
        System.out.println("Adding stats for: " + playerName);
    }

    
    public double[] getStats() {
        return new double[]{
                Double.parseDouble(attackField.getText()),
                Double.parseDouble(accelerationField.getText()),
                Double.parseDouble(skillField.getText()),
                Double.parseDouble(kickPowerField.getText()),
                Double.parseDouble(accuracyField.getText()),
                Double.parseDouble(defenseField.getText()),
                Double.parseDouble(passingField.getText()),
                Double.parseDouble(crossingField.getText()),
                Double.parseDouble(dribbleField.getText()),
                Double.parseDouble(aerialAbilityField.getText())
        };
    }

    public boolean validateStats() {
        try {
            getStats();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
    
    @FXML
private void handleSaveStats() {
    if (!validateStats()) {
        statusLabel.setText("Please enter valid numbers for all stats!");
        statusLabel.setStyle("-fx-text-fill: red;");
        return;
    }

    if (playerName == null || playerID == null) {
        statusLabel.setText("Player information missing!");
        statusLabel.setStyle("-fx-text-fill: red;");
        return;
    }

    double[] stats = getStats();

    // Load player from JSON
    Player player = PlayerService.findById(playerID);

    if (player != null) {
        player.setStats(stats);
        PlayerService.updatePlayer(player); // save to JSON

        // Sanitize filename
        String safePlayerName = playerName.replaceAll("[\\\\/:*?\"<>|]", "_");
        String safePlayerID = playerID.replaceAll("[\\\\/:*?\"<>|]", "_");
        //String folderPath = "C:\\Users\\HP_500GB_SSD\\Documents\\Stats\\";
        String filePath=safePlayerName + safePlayerID + " PlayerStats.txt";

         try {
            // Write stats to file
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write("Stats for player: " + playerName + " " + playerID + "\n");
                for (double stat : stats) writer.write(stat + "\n");
                writer.write("\n");
            }

            statusLabel.setText("Stats saved for " + playerName + "!");
            statusLabel.setStyle("-fx-text-fill: green;");

        } catch (IOException ex) {
            ex.printStackTrace();
            statusLabel.setText("Error saving stats!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }


    } else {
        statusLabel.setText("Player not found!");
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    // Close window
    Stage stage = (Stage) statusLabel.getScene().getWindow();
    stage.close();
}



}
