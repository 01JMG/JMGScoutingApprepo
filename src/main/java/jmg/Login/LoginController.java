package jmg.Login;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private final String USERS_DIR = "users"; // folder where user files are stored

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter username and password");
            return;
        }

        
        File userFile = new File(USERS_DIR + "/" + username + ".txt");

        if (!userFile.exists()) {
            statusLabel.setText("User not found");
            return;
        }

        try {
          
            String storedPassword = Files.readAllLines(userFile.toPath()).get(0).trim();

            if (password.equals(storedPassword)) {
                statusLabel.setText("Login successful!");

              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAddMenu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Scouting App");
            } else {
                statusLabel.setText("Incorrect password");
            }
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error reading user file");
        }
    }
}
