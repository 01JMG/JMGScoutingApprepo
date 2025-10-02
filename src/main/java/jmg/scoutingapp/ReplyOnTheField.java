package jmg.scoutingapp;

public class ReplyOnTheField {
    private String postId;
    private String commentId;
    private String userId;
    private String replyText;
    private String createdAt;

    // No-arg constructor needed for Jackson
    public ReplyOnTheField() {}

    public ReplyOnTheField(String postId, String commentId, String userId, String replyText) {
        this.postId = postId;
        this.commentId = commentId;
        this.userId = userId;
        this.replyText = replyText;
    }

    // Getters and setters
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) { this.commentId = commentId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getReplyText() { return replyText; }
    public void setReplyText(String replyText) { this.replyText = replyText; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return userId + ": " + replyText;
    }
}
