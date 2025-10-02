package jmg.scoutingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class UploadVideoController {

    @FXML private Button chooseFileButton;
    @FXML private Button uploadButton;
    @FXML private Label fileNameLabel;

    private File selectedFile;

    @FXML
    private void initialize() {
        fileNameLabel.setText("No file selected");
    }

     @FXML
    private void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mov", "*.avi")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            fileNameLabel.setText(selectedFile.getName());
        } else {
            fileNameLabel.setText("No file selected");
        }
    }

    @FXML
    private void handleUpload(ActionEvent event) {
        if (selectedFile != null) {
            // Define destination folder
            File destDir = new File("uploads");
            if (!destDir.exists()) {
                destDir.mkdir(); // create folder if it doesn't exist
            }

            File destFile = new File(destDir, selectedFile.getName());

            try {
                // Copy the selected file to the destination folder
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded to: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to upload file.");
            }
        } else {
            System.out.println("No file selected to upload.");
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
