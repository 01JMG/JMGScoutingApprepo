package jmg.scoutingapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ViewtheOnTheFieldController {

    @FXML private TextField agentIdField;
    @FXML private TextArea contentArea;
    @FXML private TextField tagsField;
    @FXML private ListView<PostOnTheField> postsList;
    @FXML private ComboBox<String> reactionBox;
    @FXML private TextField passCaptionField;
    @FXML private Button attachMediaButton;

    private File mediaFile = null;
    private final PostOnTheFieldClient client = new PostOnTheFieldClient();

    @FXML
    public void initialize() {
        // Populate reactions
        reactionBox.getItems().addAll("like", "love", "wow", "sad", "angry");

        // Load posts
        loadPosts();

        // Display posts with media (images & videos)
        postsList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PostOnTheField post, boolean empty) {
                super.updateItem(post, empty);
                if (empty || post == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    String text = post.getAgentId() + ": " + post.getContent() +
                            "\nTags: " + post.getTags() +
                            "\nReactions: " + getReactions(post) +
                            " | Comments: " + getCommentsCount(post) +
                            " | Passes: " + getPassCount(post);
                    setText(text);

                    // Media display
                    if (post.getMediaPath() != null && !post.getMediaPath().isEmpty()) {
                        File file = new File(post.getMediaPath());
                        if (file.exists() && file.isFile()) {
                            try {
                                VBox mediaBox = new VBox(5);
                                mediaBox.setStyle("-fx-padding: 5;");
                                setGraphic(mediaBox);

                                if (file.getName().matches(".*\\.(png|jpg|jpeg)")) {
                                    ImageView imageView = new ImageView(new Image(file.toURI().toString(), 200, 200, true, true));
                                    mediaBox.getChildren().add(imageView);
                                } else if (file.getName().matches(".*\\.(mp4|mov)")) {
                                    Media media = new Media(file.toURI().toString());
                                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                                    MediaView mediaView = new MediaView(mediaPlayer);
                                    mediaView.setFitWidth(300);
                                    mediaView.setPreserveRatio(true);
                                    mediaBox.getChildren().add(mediaView);
                                    mediaPlayer.setAutoPlay(true);
                                }
                            } catch (Exception e) {
                                setGraphic(null);
                                e.printStackTrace();
                            }
                        } else {
                            setGraphic(null);
                        }
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        // Open comment scene on double-click
        postsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                PostOnTheField selected = postsList.getSelectionModel().getSelectedItem();
                if (selected != null) openCommentsScene(selected.getId());
            }
        });
    }

    @FXML
    private void handleAttachMedia() {
        var fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Select Media File");
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Images & Videos", "*.png", "*.jpg", "*.jpeg", "*.mp4", "*.mov")
        );
        File selected = fileChooser.showOpenDialog(attachMediaButton.getScene().getWindow());
        if (selected != null) mediaFile = selected;
    }

    @FXML
    private void handleCreatePost() {
        try {
            String agentId = agentIdField.getText().trim();
            String content = contentArea.getText().trim();
            List<String> tags = Arrays.asList(tagsField.getText().split(","));

            if (agentId.isEmpty() || content.isEmpty()) {
                showAlert("Error", "Agent ID and content cannot be empty.");
                return;
            }

            PostOnTheField newPost = client.createPost(agentId, content, tags, mediaFile);
            postsList.getItems().add(newPost);

            agentIdField.clear();
            contentArea.clear();
            tagsField.clear();
            mediaFile = null;
        } catch (Exception e) {
            showAlert("Error", "Failed to create post: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddReaction() {
        PostOnTheField selected = postsList.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Error", "Select a post first."); return; }

        try {
            String reactionType = reactionBox.getValue();
            if (reactionType == null) return;
            client.addReaction(selected.getId(), "user123", reactionType);
            postsList.refresh();
        } catch (Exception e) {
            showAlert("Error", "Failed to add reaction: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddPass() {
        PostOnTheField selected = postsList.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Error", "Select a post first."); return; }

        try {
            String caption = passCaptionField.getText().trim();
            client.addPass(selected.getId(), "user123", caption);
            passCaptionField.clear();
            postsList.refresh();
        } catch (Exception e) {
            showAlert("Error", "Failed to pass/share post: " + e.getMessage());
        }
    }

   
    private void loadPosts() {
        try {
            List<PostOnTheField> posts = client.getPosts();
            postsList.getItems().clear();
            postsList.getItems().addAll(posts);
        } catch (Exception e) {
            showAlert("Error", "Failed to load posts: " + e.getMessage());
        }
    }

    private int getCommentsCount(PostOnTheField post) {
        try { return client.getComments(post.getId()).size(); }
        catch (Exception e) { return 0; }
    }

    private int getPassCount(PostOnTheField post) {
        try { return client.getPasses(post.getId()).size(); }
        catch (Exception e) { return 0; }
    }

    private String getReactions(PostOnTheField post) {
        try {
            var reactions = client.getReactions(post.getId());
            if (reactions.isEmpty()) return "0";
            var summary = reactions.stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                            ReactionOnTheField::getReactionType,
                            java.util.stream.Collectors.counting()
                    ));
            return summary.toString();
        } catch (Exception e) { return "{}"; }
    }

    private void showAlert(String title, String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    private void openCommentsScene(String postId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadComments.fxml"));
            Parent root = loader.load();
            ReadCommentsController controller = loader.getController();
            controller.setPostId(postId);

            Stage stage = new Stage();
            stage.setTitle("Comments");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openReplyWindow(String postId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReplyComments.fxml"));
            Parent root = loader.load();

            ReplyCommentsController controller = loader.getController();
            controller.setPostId(postId);

            Stage stage = new Stage();
            stage.setTitle("Reply to Comments");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
