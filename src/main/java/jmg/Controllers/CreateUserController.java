package jmg.scoutingapp;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateUserController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField countryField;
    @FXML private TextField religionField;
    @FXML private TextField raceField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label statusLabel;
    @FXML private TextField userNameField;
 


    private final String USERS_DIR = "users"; // base folder

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll(
                "Coach",
                "Scout",
                "Admin",
                "Content Creator",
                "Agent",
                "Casual User"
        );
    }

    @FXML
    private void handleCreateUser() {
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String country = countryField.getText().trim();
        String religion = religionField.getText().trim();
        String race = raceField.getText().trim();
        String password = passwordField.getText();
        String userName=userNameField.getText().trim();
        String role = roleComboBox.getValue();

        // Validation
        if (name.isEmpty() || age.isEmpty() || country.isEmpty() || religion.isEmpty()
                || race.isEmpty() || password.isEmpty() || role == null ||userName.isEmpty()) {
            statusLabel.setText("Please fill in all fields and select a role.");
            return;
        }

        // Create role folder if it doesn't exist
        File roleFolder = new File(USERS_DIR + File.separator + role);
        if (!roleFolder.exists()) roleFolder.mkdirs();

        // Save user as a TXT file named by the user's name (replace spaces with underscores)
        String fileName = userName.replaceAll(" ","_")+name.replaceAll(" ", "_") + ".txt";
        File userFile = new File(roleFolder, fileName);

        try (FileWriter writer = new FileWriter(userFile)) {
            writer.write("Name: " + name + "\n");
            writer.write("Age: " + age + "\n");
            writer.write("Country: " + country + "\n");
            writer.write("Religion: " + religion + "\n");
            writer.write("Race: " + race + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("Username: " + userName + "\n");
            writer.write("Role: " + role + "\n");

            statusLabel.setText("User created successfully!");
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to create user.");
        }
        
        
        // Create role folder if it doesn't exist
        File passwordFolder = new File(USERS_DIR + File.separator + role);
        if (!roleFolder.exists()) roleFolder.mkdirs();

        // Save user as a TXT file named by the user's name (replace spaces with underscores)
        String fileName2 = userName.replaceAll(" ","_")+"systeminfo.txt";
        File userFile2 = new File(roleFolder, fileName2);

        try (FileWriter writer = new FileWriter(userFile)) {
            writer.write("Password: " + password + "\n");
            writer.write("Username: " + userName + "\n");
            writer.write("Role: " + role + "\n");

            statusLabel.setText("User created successfully!");
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to create user.");
        }
    }

    private void clearFields() {
        nameField.clear();
        ageField.clear();
        countryField.clear();
        religionField.clear();
        raceField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
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
