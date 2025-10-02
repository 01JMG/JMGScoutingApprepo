package jmg.scoutingapp;

public class PassOnTheField {
    private String id;
    private String postId;
    private String userId;
    private String caption;    // custom caption added by user
    private String createdAt;
    
    
    public PassOnTheField(String postId, String userId, String caption) {
    this.postId = postId;
    this.userId = userId;
    this.caption = caption;
}


    // Original post included if backend sends it along
    private PostOnTheField originalPost;

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public PostOnTheField getOriginalPost() { return originalPost; }
    public void setOriginalPost(PostOnTheField originalPost) { this.originalPost = originalPost; }

    @Override
    public String toString() {
        return "PassOnTheField{" +
                "id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", caption='" + caption + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", originalPost=" + originalPost +
                '}';
    }
}
