package jmg.Controllers;

import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CompareStatsController implements Initializable {

    @FXML
    private ComboBox<String> playerOneDropdown;

    @FXML
    private ComboBox<String> playerTwoDropdown;

    // Player One Stat Labels
    @FXML private Label playerOneStat0;
    @FXML private Label playerOneStat1;
    @FXML private Label playerOneStat2;
    @FXML private Label playerOneStat3;
    @FXML private Label playerOneStat4;
    @FXML private Label playerOneStat5;
    @FXML private Label playerOneStat6;
    @FXML private Label playerOneStat7;
    @FXML private Label playerOneStat8;
    @FXML private Label playerOneStat9;

    // Player Two Stat Labels
    @FXML private Label playerTwoStat0;
    @FXML private Label playerTwoStat1;
    @FXML private Label playerTwoStat2;
    @FXML private Label playerTwoStat3;
    @FXML private Label playerTwoStat4;
    @FXML private Label playerTwoStat5;
    @FXML private Label playerTwoStat6;
    @FXML private Label playerTwoStat7;
    @FXML private Label playerTwoStat8;
    @FXML private Label playerTwoStat9;

    // Dummy data (replace with real service later)
    private final Map<String, List<String>> playerStats = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Example players
        playerStats.put("Lionel Messi", Arrays.asList(
                "Goals: 25", "Assists: 20", "Shots: 90", "Passes: 1500", "Tackles: 15",
                "Interceptions: 8", "Dribbles: 100", "Key Passes: 70", "Minutes: 2800", "Yellow Cards: 2"
        ));

        playerStats.put("Cristiano Ronaldo", Arrays.asList(
                "Goals: 30", "Assists: 10", "Shots: 110", "Passes: 900", "Tackles: 10",
                "Interceptions: 5", "Dribbles: 50", "Key Passes: 30", "Minutes: 2900", "Yellow Cards: 3"
        ));

        // Populate dropdowns
        playerOneDropdown.getItems().addAll(playerStats.keySet());
        playerTwoDropdown.getItems().addAll(playerStats.keySet());

        // Add listeners
        playerOneDropdown.setOnAction(e -> showPlayerStats(playerOneDropdown.getValue(), true));
        playerTwoDropdown.setOnAction(e -> showPlayerStats(playerTwoDropdown.getValue(), false));
    }

    private void showPlayerStats(String playerName, boolean isPlayerOne) {
        if (playerName == null || !playerStats.containsKey(playerName)) return;

        List<String> stats = playerStats.get(playerName);
        Label[] labels = isPlayerOne
                ? new Label[]{playerOneStat0, playerOneStat1, playerOneStat2, playerOneStat3, playerOneStat4,
                              playerOneStat5, playerOneStat6, playerOneStat7, playerOneStat8, playerOneStat9}
                : new Label[]{playerTwoStat0, playerTwoStat1, playerTwoStat2, playerTwoStat3, playerTwoStat4,
                              playerTwoStat5, playerTwoStat6, playerTwoStat7, playerTwoStat8, playerTwoStat9};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(stats.get(i));
        }
    }
    
    @FXML
    private void handleCompareStats(ActionEvent event) {
        try {
            // Load the CompareStats.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompareStats.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set new scene
            stage.setScene(new Scene(root));
            stage.setTitle("Compare Player Stats");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAddMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
