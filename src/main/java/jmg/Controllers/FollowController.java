package jmg.Controllers;

import jmg.scoutingapp.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class FollowController {

    public FollowController() {
        // Required empty constructor for JavaFX
    }

    private User currentUser; // logged-in user
    private List<User> allUsers; // all users in the system

    @FXML
    private TextField searchField;

    @FXML
    private VBox resultsBox;

    // Initialize controller after FXML loads
    @FXML
    private void initialize() {
        setupSearch();
    }

    public void setData(User currentUser, List<User> allUsers) {
        this.currentUser = currentUser;
        this.allUsers = allUsers;
    }

    private void setupSearch() {
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            updateSearchResults(newText);
        });
    }

    private void updateSearchResults(String query) {
        resultsBox.getChildren().clear();

        if (allUsers == null || currentUser == null) return;

        // Filter users by query, exclude self, private or hidden users
        List<User> filtered = allUsers.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(query.toLowerCase()))
                .filter(u -> !u.equals(currentUser))
                .filter(u -> !u.isPrivate())
                .filter(u -> !u.isHidden())
                .collect(Collectors.toList());

        for (User user : filtered) {
            HBox userRow = new HBox(10);
            Label nameLabel = new Label(user.getUsername());

            Button followBtn = new Button();
            updateFollowButton(followBtn, user);

            followBtn.setOnAction(e -> {
                if (currentUser.isFollowing(user)) {
                    currentUser.getFollowing().remove(user);
                } else {
                    currentUser.follow(user);
                }
                updateFollowButton(followBtn, user);
            });

            userRow.getChildren().addAll(nameLabel, followBtn);
            resultsBox.getChildren().add(userRow);
        }
    }

    private void updateFollowButton(Button button, User user) {
        if (currentUser.isFollowing(user)) {
            button.setText("Following");
        } else {
            button.setText("Follow");
        }
    }
}
