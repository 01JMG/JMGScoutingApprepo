package jmg.scoutingapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewPlayersInSystemController {

    @FXML
    private TableView<Player> playersTable;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> dobColumn;
    @FXML
    private TableColumn<Player, Number> weightColumn;
    @FXML
    private TableColumn<Player, String> raceColumn;
    @FXML
    private TableColumn<Player, String> birthPlaceColumn;
    @FXML
    private TableColumn<Player, String> appIdColumn;
    @FXML
    private TableColumn<Player, String> profilePicColumn;
    @FXML
    private Label statusLabel;
    
    private final ObservableList<Player> playerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the cell value factories for each column.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        dobColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfBirthProperty());
        weightColumn.setCellValueFactory(cellData -> cellData.getValue().weightTextProperty());
        raceColumn.setCellValueFactory(cellData -> cellData.getValue().raceProperty());
        birthPlaceColumn.setCellValueFactory(cellData -> cellData.getValue().birthPlaceProperty());
        appIdColumn.setCellValueFactory(cellData -> cellData.getValue().appIdProperty());
        profilePicColumn.setCellValueFactory(cellData -> cellData.getValue().profilePicPathProperty());
        
        // This is the custom cell factory for the profile picture column.
        profilePicColumn.setCellFactory(column -> {
            return new TableCell<Player, String>() {
                private final ImageView imageView = new ImageView();
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Check if the image file exists
                        File imageFile = new File("profile-pics" + File.separator + item);
                        if (imageFile.exists()) {
                            Image image = new Image(imageFile.toURI().toString(), 100, 100, true, true);
                            imageView.setImage(image);
                            setGraphic(imageView);
                        } else {
                            // Display a placeholder if the image file is not found
                            setGraphic(new Label("No Image"));
                        }
                    }
                }
            };
        });

        readScoutablePlayers();
        playersTable.setItems(playerList);
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
       
    }

    private void readScoutablePlayers() {
    String fileName = "Scoutable Players.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(", ");
            if (parts.length == 7) {
                try {
                    String name = parts[0];
                    String dateOfBirth = parts[1];
                    double weight = Double.parseDouble(parts[2]);
                    String race = parts[3];
                    String birthPlace = parts[4];
                    String appId = parts[5];
                    String profilePicPath = parts[6];

                    // Corrected line with proper constructor call
                    playerList.add(new Player(name, weight, race, birthPlace, appId, profilePicPath, dateOfBirth));

                } catch (NumberFormatException e) {
                    statusLabel.setText("Error reading player data: Invalid weight format.");
                }
            }
        }
        statusLabel.setText("Loaded " + playerList.size() + " players.");
    } catch (IOException e) {
        statusLabel.setText("Error reading player file: " + e.getMessage());
        Logger.getLogger(ViewPlayersInSystemController.class.getName()).log(Level.SEVERE, null, e);
    }
}
}