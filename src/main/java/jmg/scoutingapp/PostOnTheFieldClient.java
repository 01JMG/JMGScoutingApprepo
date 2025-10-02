package jmg.scoutingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Path;
import java.util.List;

public class PostOnTheFieldClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String BASE_URL = "http://localhost:3000/api/v1/on-the-field";

    // ==============================
    // POSTS
    // ==============================
    public PostOnTheField createPost(String agentId, String content, List<String> tags, File mediaFile) throws Exception {

        var builder = new MultipartBodyBuilder()
                .addText("agentId", agentId)
                .addText("content", content);

        if (tags != null && !tags.isEmpty()) {
            builder.addText("tags", String.join(",", tags));
        }

        if (mediaFile != null && mediaFile.exists()) {
            builder.addFile("media", mediaFile.toPath());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts"))
                .header("Content-Type", "multipart/form-data; boundary=" + builder.getBoundary())
                .POST(BodyPublishers.ofByteArray(builder.build()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), PostOnTheField.class);
    }

    public List<PostOnTheField> getPosts() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<PostOnTheField>>() {});
    }

    // ==============================
    // COMMENTS
    // ==============================
    public CommentOnTheField addComment(String postId, String userId, String commentText) throws Exception {
        CommentOnTheField comment = new CommentOnTheField(postId, userId, commentText);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts/" + postId + "/comments"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(comment)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), CommentOnTheField.class);
    }

    public List<CommentOnTheField> getComments(String postId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts/" + postId + "/comments"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<CommentOnTheField>>() {});
    }
    
    public void addReply(String postId, String commentId, String userId, String replyText) throws Exception {
    // Make sure you have a ReplyOnTheField class
    ReplyOnTheField reply = new ReplyOnTheField(postId, commentId, userId, replyText);

    String requestBody = objectMapper.writeValueAsString(reply); // use objectMapper, not mapper

    HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(BASE_URL + "/posts/" + postId + "/comments/" + commentId + "/replies"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // use BodyPublishers directly
            .build();

    client.send(request, HttpResponse.BodyHandlers.ofString()); // use client, not httpClient
}


    // ==============================
    // REACTIONS
    // ==============================
    public ReactionOnTheField addReaction(String postId, String userId, String reactionType) throws Exception {
        ReactionOnTheField reaction = new ReactionOnTheField(postId, userId, reactionType);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts/" + postId + "/reactions"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(reaction)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), ReactionOnTheField.class);
    }

    public List<ReactionOnTheField> getReactions(String postId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts/" + postId + "/reactions"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<ReactionOnTheField>>() {});
    }

    // ==============================
    // PASSES / SHARES
    // ==============================
    public PassOnTheField addPass(String postId, String userId, String caption) throws Exception {
        PassOnTheField pass = new PassOnTheField(postId, userId, caption);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts/" + postId + "/pass"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(pass)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), PassOnTheField.class);
    }

    public List<PassOnTheField> getPasses(String postId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts/" + postId + "/pass"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<PassOnTheField>>() {});
    }
}
