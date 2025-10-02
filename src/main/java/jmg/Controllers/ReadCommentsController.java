package jmg.scoutingapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;

public class ReadCommentsController {

    @FXML private ListView<CommentOnTheField> commentsList;
    @FXML private TextField commentField;

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
                    setText((empty || comment == null) ? null : comment.getUserId() + ": " + comment.getCommentText());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddComment() {
        try {
            String text = commentField.getText().trim();
            if (!text.isEmpty()) {
                client.addComment(postId, "user123", text);
                commentField.clear();
                loadComments();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
