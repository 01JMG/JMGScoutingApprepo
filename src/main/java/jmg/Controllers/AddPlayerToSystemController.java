package jmg.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jmg.scoutingapp.AddPlayerStatsToPlayerController;

public class AddPlayerToSystemController {

    @FXML private TextField nameField;
    @FXML private TextField weightField;
    @FXML private TextField raceField;
    @FXML private TextField birthPlaceField;
    @FXML private TextField appIdField;
    @FXML private Label statusLabel;
    @FXML private ImageView profilePicView;
    @FXML private ImageView backgroundPicView;

    // Declare instance variables to hold the image paths
    private String profilePicPath;
    private String backgroundPicPath;

    @FXML
    private void handleAddPlayer() {
        String name = nameField.getText();
        String weightText = weightField.getText();
        String race = raceField.getText();
        String birthPlace = birthPlaceField.getText();
        String appId = appIdField.getText();

        if (name.isEmpty() || weightText.isEmpty() || race.isEmpty() || birthPlace.isEmpty() || appId.isEmpty()) {
            statusLabel.setText("Please fill in all fields!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        double weight;
        try {
            weight = Double.parseDouble(weightText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Weight must be a number!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        
        // Use a placeholder if a path hasn't been set
        String finalProfilePicPath = (this.profilePicPath != null) ? this.profilePicPath : "N/A";
        String finalBackgroundPicPath = (this.backgroundPicPath != null) ? this.backgroundPicPath : "N/A";
        
        
        try{
            FileWriter writerr=new FileWriter(name+appId+".txt");
            
            writerr.write("Name: "+name+"\nWeight: "+weightText+"\nRace: "+race+"\nBirth Place: "+birthPlace+"\nProfile Picture"+finalProfilePicPath +"\nBackground Picture"+ finalBackgroundPicPath);
            
        }
        catch(IOException e){
            Logger.getLogger(AddPlayerToSystemController.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            FileWriter writer = new FileWriter("Scoutable Players.txt", true);
            // Append all data, including the new image paths, separated by a pipe '|'
            writer.append("\n" + name + "|" + weightText + "|" + race + "|" + birthPlace + "|" + appId + "|" + finalProfilePicPath + "|" + finalBackgroundPicPath);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(AddPlayerToSystemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jmg/scoutingapp/AddPlayerStatsToPlayer.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add Player Stats");

            AddPlayerStatsToPlayerController statsController = loader.getController();
            statsController.setPlayerName(name);
            statsController.setPlayerID(appId);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        statusLabel.setText("Player " + name + " added successfully!");
        statusLabel.setStyle("-fx-text-fill: green;");

        // Clear all fields and image views
        nameField.clear();
        weightField.clear();
        raceField.clear();
        birthPlaceField.clear();
        appIdField.clear();
        profilePicView.setImage(null);
        backgroundPicView.setImage(null);
    }
    
    @FXML
    private void handleAddProfilePic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                profilePicView.setImage(image);
                // Correctly save the absolute path to the profile picture variable
                this.profilePicPath = selectedFile.getAbsolutePath();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AddPlayerToSystemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void handleAddBackgroundPic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                backgroundPicView.setImage(image);
                // Correctly save the absolute path to the background picture variable
                this.backgroundPicPath = selectedFile.getAbsolutePath();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AddPlayerToSystemController.class.getName()).log(Level.SEVERE, null, ex);
            }
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