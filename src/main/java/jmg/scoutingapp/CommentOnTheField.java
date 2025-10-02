package jmg.scoutingapp;

public class CommentOnTheField {
    private String id;
    private String postId;
    private String userId;
    private String commentText;
    private String createdAt;

    // No-argument constructor (required by Jackson)
    public CommentOnTheField() {}

    // Optional: constructor with parameters for easier object creation
    public CommentOnTheField(String postId, String userId, String commentText) {
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "CommentOnTheField{" +
                "id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", commentText='" + commentText + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
