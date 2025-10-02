package jmg.scoutingapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateScoutReportController {

    @FXML
    private TextField playerNameField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField ageField;

    @FXML
    private TextArea strengthsArea;

    @FXML
    private TextArea weaknessesArea;

    @FXML
    private TextArea styleArea;
    
    @FXML
    private TextArea wagesArea;
    
    @FXML
    private TextArea nationallityArea;
    
    @FXML
    private TextArea clubArea;
    
    @FXML
    private TextArea matchesplayedArea;
    
    @FXML
    private TextArea minutesplayedArea;
    
    @FXML
    private TextArea goalsscoredArea;
    
    @FXML
    private TextArea assistsmadeArea;
    
    @FXML
    private TextArea yellowcardsArea;
    
    @FXML
    private TextArea redcardsArea;
    
    @FXML
    private TextArea xGArea;
    
    @FXML
    private TextArea npxGArea;
    
    @FXML
    private TextArea xAGArea;
    
    @FXML
    private TextArea scaArea;
    
    @FXML
    private TextArea gcaArea;


    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;
    
    @FXML
    private Button nextpage;

    // Save button action
    @FXML
    private void handleSave() {
    String agentName = "Joseph Mbewe";
    String name = playerNameField.getText();
    String position = positionField.getText();

    if (name.isEmpty() || position.isEmpty()) {
        showAlert("Validation Error", "Player Name and Position are required!");
        return;
    }

    // New variables for numerical data
    int age = 0;
    int matchesPlayed = 0;
    int minutesPlayed = 0;
    int goalsScored = 0;
    int assistsMade = 0;
    int yellowCards = 0;
    int redCards = 0;
    double xG = 0.0;
    double npxG = 0.0;
    double xAG = 0.0;
    int sca = 0;
    int gca = 0;

    // Use try-catch blocks to safely parse data
    try {
        if (!ageField.getText().isEmpty()) {
            age = Integer.parseInt(ageField.getText());
        }
        if (!matchesplayedArea.getText().isEmpty()) {
            matchesPlayed = Integer.parseInt(matchesplayedArea.getText());
        }
        if (!minutesplayedArea.getText().isEmpty()) {
            minutesPlayed = Integer.parseInt(minutesplayedArea.getText());
        }
        if (!goalsscoredArea.getText().isEmpty()) {
            goalsScored = Integer.parseInt(goalsscoredArea.getText());
        }
        if (!assistsmadeArea.getText().isEmpty()) {
            assistsMade = Integer.parseInt(assistsmadeArea.getText());
        }
        if (!yellowcardsArea.getText().isEmpty()) {
            yellowCards = Integer.parseInt(yellowcardsArea.getText());
        }
        if (!redcardsArea.getText().isEmpty()) {
            redCards = Integer.parseInt(redcardsArea.getText());
        }
        if (!xGArea.getText().isEmpty()) {
            xG = Double.parseDouble(xGArea.getText());
        }
        if (!npxGArea.getText().isEmpty()) {
            npxG = Double.parseDouble(npxGArea.getText());
        }
        if (!xAGArea.getText().isEmpty()) {
            xAG = Double.parseDouble(xAGArea.getText());
        }
        if (!scaArea.getText().isEmpty()) {
            sca = Integer.parseInt(scaArea.getText());
        }
        if (!gcaArea.getText().isEmpty()) {
            gca = Integer.parseInt(gcaArea.getText());
        }
    } catch (NumberFormatException e) {
        showAlert("Input Error", "Please ensure numerical fields contain only numbers.");
        return;
    }

    String strengths = strengthsArea.getText();
    String weaknesses = weaknessesArea.getText();
    String style = styleArea.getText();
    String wages = wagesArea.getText();
    String nationality = nationallityArea.getText();
    String club = clubArea.getText();

    String folderPath = "Scouting Reports";
    String fileName = agentName.replaceAll("\\s+", "_") + "report_on_" + name.replaceAll("\\s+", "_") + ".txt";

    File directory = new File(folderPath);
    if (!directory.exists()) {
        directory.mkdirs();
    }

    try (FileWriter writer = new FileWriter(folderPath + File.separator + fileName)) {
        writer.write("=== Scout Report ===\n");
        writer.write("Scout Agent: " + agentName + "\n");
        writer.write("Player: " + name + "\n");
        writer.write("Position: " + position + "\n");
        writer.write("Age: " + age + "\n");
        writer.write("Strengths: " + strengths + "\n");
        writer.write("Weaknesses: " + weaknesses + "\n");
        writer.write("Style of Play: " + style + "\n");
        writer.write("Wages: " + wages + "\n");
        writer.write("Nationality: " + nationality + "\n");
        writer.write("Club: " + club + "\n");
        writer.write("Matches Played: " + matchesPlayed + "\n");
        writer.write("Minutes Played: " + minutesPlayed + "\n");
        writer.write("Goals Scored: " + goalsScored + "\n");
        writer.write("Assists made: " + assistsMade + "\n");
        writer.write("Yellow Cards: " + yellowCards + "\n");
        writer.write("Red Cards: " + redCards + "\n");
        writer.write("Expected Goals(xG): " + xG + "\n");
        writer.write("Non-penalty Expected Goals(npxG): " + npxG + "\n");
        writer.write("Expected Assisted Goals(xAG): " + xAG + "\n");
        writer.write("Shot-Creating Actions(SCA): " + sca + "\n");
        writer.write("Goal-Creating Actions(GCA): " + gca + "\n");

        System.out.println("Scout Report saved to: " + folderPath + File.separator + fileName);
        showAlert("Success", "Scout Report saved successfully!");

    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Error", "An error occurred while saving the report.");
    }
}
    // Clear form
    @FXML
    private void handleClear() {
        playerNameField.clear();
        positionField.clear();
        ageField.clear();
        strengthsArea.clear();
        weaknessesArea.clear();
        styleArea.clear();
    }
    
    @FXML
    private void handleNextPage()
    {
        loadScene("CreateActualMatchStatsReport.fxml");
    }
    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Scouting App");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
