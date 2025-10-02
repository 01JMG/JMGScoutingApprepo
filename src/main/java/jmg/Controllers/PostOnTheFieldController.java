package jmg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jmg.scoutingapp.PostOnTheField;
import jmg.scoutingapp.PostOnTheFieldClient;

public class PostOnTheFieldController {

    @FXML private TextField agentIdField;
    @FXML private TextArea contentArea;
    @FXML private TextField tagsField;
    @FXML private ListView<String> postsList;
    @FXML private Label mediaLabel;

    private File selectedMediaFile;  // Holds chosen image/video
    private final PostOnTheFieldClient client = new PostOnTheFieldClient();

    @FXML
    public void initialize() {
        loadPosts();
    }

    @FXML
    private void handleChooseMedia() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image or Video");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Media Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.mp4", "*.mov", "*.avi")
        );

        Stage stage = (Stage) mediaLabel.getScene().getWindow();
        selectedMediaFile = fileChooser.showOpenDialog(stage);

        if (selectedMediaFile != null) {
            mediaLabel.setText(selectedMediaFile.getName());
        } else {
            mediaLabel.setText("No file selected");
        }
    }

    @FXML
    private void handleCreatePost() {
        try {
            String agentId = agentIdField.getText().trim();
            String content = contentArea.getText().trim();
            List<String> tags = Arrays.stream(tagsField.getText().split(","))
                                      .map(String::trim)
                                      .filter(s -> !s.isEmpty())
                                      .collect(Collectors.toList());

            if (agentId.isEmpty() || content.isEmpty()) {
                showAlert("Error", "Agent ID and Content cannot be empty.");
                return;
            }

            PostOnTheField newPost = client.createPost(agentId, content, tags, selectedMediaFile);

            postsList.getItems().add(formatPost(newPost));

            // Clear form after posting
            agentIdField.clear();
            contentArea.clear();
            tagsField.clear();
            mediaLabel.setText("No file selected");
            selectedMediaFile = null;

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create post: " + e.getMessage());
        }
    }

    private void loadPosts() {
        try {
            List<PostOnTheField> posts = client.getPosts();
            postsList.getItems().clear();
            for (PostOnTheField post : posts) {
                postsList.getItems().add(formatPost(post));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load posts: " + e.getMessage());
        }
    }

    private String formatPost(PostOnTheField post) {
        String mediaInfo = (post.getMediaPath() != null && !post.getMediaPath().isEmpty())
                ? " [Media: " + post.getMediaPath() + "]" : "";
        String tagsInfo = (post.getTags() != null && !post.getTags().isEmpty())
                ? " (tags: " + post.getTags() + ")" : "";
        return post.getAgentId() + ": " + post.getContent() + mediaInfo + tagsInfo;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
}
