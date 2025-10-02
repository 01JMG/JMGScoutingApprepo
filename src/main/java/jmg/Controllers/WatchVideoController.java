package jmg.scoutingapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class WatchVideoController {

    @FXML
    private ListView<String> videoListView;

    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    private final String uploadsFolder = "uploads"; // folder where videos are stored

    @FXML
    public void initialize() {
        mediaView.setFitWidth(600); // set width
        mediaView.setFitHeight(400); // set height
        mediaView.setPreserveRatio(true);
        loadVideos();
    }


    // Load all videos from the uploads folder into the ListView
    private void loadVideos() {
        File folder = new File(uploadsFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".mp4") || name.endsWith(".mov") || name.endsWith(".avi"));
        ObservableList<String> videoNames = FXCollections.observableArrayList();

        if (files != null) {
            for (File file : files) {
                videoNames.add(file.getName());
            }
        }

        videoListView.setItems(videoNames);

        // Auto-select first video if available
        if (!videoNames.isEmpty()) {
            videoListView.getSelectionModel().selectFirst();
            prepareMedia(videoNames.get(0));
        }

        // Change video when a different one is selected
        videoListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                prepareMedia(newVal);
            }
        });
    }

    // Prepare the selected media
    private void prepareMedia(String fileName) {
    // Stop and dispose previous mediaPlayer if exists
    if (mediaPlayer != null) {
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }

    File file = new File(uploadsFolder + "/" + fileName);
    if (!file.exists()) {
        System.out.println("File does not exist: " + file.getAbsolutePath());
        return;
    }

    Media media = new Media(file.toURI().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaView.setMediaPlayer(mediaPlayer);

    mediaPlayer.setOnReady(() -> {
        System.out.println("Playing: " + fileName);
        mediaPlayer.play();
    });
}


    @FXML
private void handlePlay(ActionEvent event) {
    if (mediaPlayer != null) {
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }

    String selected = videoListView.getSelectionModel().getSelectedItem();
    if (selected == null) return;

    File file = new File(uploadsFolder, selected);

    // Check if file is already an MP4
    File playableFile;
    if (!selected.toLowerCase().endsWith(".mp4")) {
        playableFile = new File(uploadsFolder, selected + "_converted.mp4");
        if (!playableFile.exists()) {
            convertToMp4(file, playableFile); // run ffmpeg
        }
    } else {
        playableFile = file;
    }

    Media media = new Media(playableFile.toURI().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaView.setMediaPlayer(mediaPlayer);
    mediaPlayer.play();
}


    @FXML
    private void handlePause(ActionEvent event) {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    @FXML
    private void handleStop(ActionEvent event) {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAddMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void convertToMp4(File input, File output) {
    try {
        // ffmpeg command: convert input to H.264 + AAC MP4
        ProcessBuilder pb = new ProcessBuilder(
            "ffmpeg", "-i", input.getAbsolutePath(),
            "-vcodec", "h264", "-acodec", "aac",
            output.getAbsolutePath()
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Optional: print ffmpeg output for debugging
        new Thread(() -> {
            try (java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        process.waitFor();
        System.out.println("Converted to: " + output.getAbsolutePath());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
