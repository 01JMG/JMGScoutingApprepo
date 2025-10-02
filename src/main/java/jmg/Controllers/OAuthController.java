package jmg.scoutingapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OAuthController {

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;

    // Google OAuth parameters
    private final String CLIENT_ID = "YOUR_CLIENT_ID_HERE";
    private final String REDIRECT_URI = "http://localhost:8080/callback";
    private final String SCOPE = "openid%20email%20profile";
    private final String RESPONSE_TYPE = "token"; // implicit flow

    @FXML
    private void handleLogin() {
        String oauthUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE
                + "&scope=" + SCOPE;

        try {
            Desktop.getDesktop().browse(new URI(oauthUrl));
            statusLabel.setText("Browser opened. Complete login there.");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to open browser.");
        }
    }

    // You would need a local server or embedded HTTP listener to catch the redirect
    public void handleOAuthCallback(String accessToken) {
        // This method would be called when OAuth completes
        statusLabel.setText("Logged in! Token: " + accessToken);
        System.out.println("Access Token: " + accessToken);
    }
}
