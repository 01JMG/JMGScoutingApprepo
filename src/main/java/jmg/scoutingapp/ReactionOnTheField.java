package jmg.scoutingapp;

public class ReactionOnTheField {
    private String id;
    private String postId;
    private String userId;
    private String reactionType;
    private String createdAt;
    
    public ReactionOnTheField()
    {
        
    }
    
    public ReactionOnTheField(String postId, String userId, String reactionType) {
        this.postId = postId;
        this.userId = userId;
        this.reactionType = reactionType;
    }


    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getReactionType() { return reactionType; }
    public void setReactionType(String reactionType) { this.reactionType = reactionType; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ReactionOnTheField{" +
                "id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", reactionType='" + reactionType + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
