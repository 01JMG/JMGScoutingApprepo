package jmg.scoutingapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostOnTheField {
    private String id;
    private String agentId;
    private String content;
    private List<String> tags;
    private String createdAt;
    private String mediaPath; // <-- NEW field for image/video

    // --- Getters & Setters ---
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMediaPath() {
        return mediaPath;
    }
    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    // --- toString() for debugging ---
    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", agentId='" + agentId + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", createdAt='" + createdAt + '\'' +
                ", mediaPath='" + mediaPath + '\'' +
                '}';
    }
}
