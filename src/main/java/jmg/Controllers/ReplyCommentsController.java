package jmg.scoutingapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class ReplyCommentsController {

    @FXML private ListView<CommentOnTheField> commentsList;
    @FXML private TextField replyField;

    private PostOnTheFieldClient client = new PostOnTheFieldClient();
    private String postId;

    public void setPostId(String postId) {
        this.postId = postId;
        loadComments();
    }

    private void loadComments() {
        try {
            List<CommentOnTheField> comments = client.getComments(postId);
            commentsList.getItems().clear();
            commentsList.getItems().addAll(comments);

            commentsList.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(CommentOnTheField comment, boolean empty) {
                    super.updateItem(comment, empty);
                    if (empty || comment == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(comment.getUserId() + ": " + comment.getCommentText());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReply() {
        CommentOnTheField selected = commentsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a comment to reply to.");
            return;
        }

        String replyText = replyField.getText().trim();
        if (replyText.isEmpty()) {
            showAlert("Reply cannot be empty.");
            return;
        }

        try {
            client.addReply(postId, selected.getId(), "user123", replyText);
            replyField.clear();
            loadComments(); // reload comments with replies
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to send reply.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}