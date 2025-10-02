package jmg.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HighLightsController {

    @FXML private ScrollPane scrollPane;

    private VBox verticalContainer;
    private HBox horizontalContainer;
    private boolean isVertical = true; // default orientation
    private boolean manualOverride = false; // if user sets orientation in settings

    private Stage primaryStage; // for window size detection

    public static class Highlight {
        private String videoPath;
        private String caption;

        public Highlight(String videoPath, String caption) {
            this.videoPath = videoPath;
            this.caption = caption;
        }

        public String getVideoPath() { return videoPath; }
        public String getCaption() { return caption; }
    }

    @FXML
    public void initialize() {
        verticalContainer = new VBox(20);
        horizontalContainer = new HBox(20);

        scrollPane.setContent(verticalContainer);

        // Sample highlights; replace with dynamic data from API or database
        List<Highlight> highlights = new ArrayList<>();
        highlights.add(new Highlight("file:///C:/Users/HP_500GB_SSD/Downloads/Video/All Hail King Julien - Cake O'Clock - Lyrics.mp4", "Amazing goal!"));
        highlights.add(new Highlight("file:///C:/Videos/video2.mp4", "Crazy dribble"));
        highlights.add(new Highlight("file:///C:/Videos/video3.mp4", "Incredible save"));

        highlights.forEach(this::addHighlightToContainers);
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
        if (!manualOverride) {
            primaryStage.widthProperty().addListener(this::windowSizeChanged);
            primaryStage.heightProperty().addListener(this::windowSizeChanged);
        }
    }

    private void windowSizeChanged(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        if (manualOverride) return; // don't auto-switch if user manually set orientation
        boolean shouldBeVertical = primaryStage.getHeight() >= primaryStage.getWidth();
        setOrientationVertical(shouldBeVertical);
    }

    private void addHighlightToContainers(Highlight highlight) {
        try {
            Media media = new Media(highlight.getVideoPath());
            MediaPlayer player = new MediaPlayer(media);
            MediaView mediaView = new MediaView(player);

            mediaView.setFitWidth(300);
            mediaView.setFitHeight(400);
            mediaView.setPreserveRatio(true);

            player.setAutoPlay(true);
            player.setCycleCount(MediaPlayer.INDEFINITE);

            VBox videoBox = new VBox(5);
            videoBox.getChildren().add(mediaView);

            if (highlight.getCaption() != null && !highlight.getCaption().isEmpty()) {
                Label captionLabel = new Label(highlight.getCaption());
                videoBox.getChildren().add(captionLabel);
            }

            verticalContainer.getChildren().add(videoBox);
            horizontalContainer.getChildren().add(videoBox);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch orientation manually (can be called from settings button)
     * @param vertical true for vertical, false for horizontal
     */
    public void setOrientationVertical(boolean vertical) {
        isVertical = vertical;
        manualOverride = true;
        scrollPane.setContent(isVertical ? verticalContainer : horizontalContainer);
        scrollPane.setHbarPolicy(isVertical ?
                ScrollPane.ScrollBarPolicy.NEVER : ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(isVertical ?
                ScrollPane.ScrollBarPolicy.AS_NEEDED : ScrollPane.ScrollBarPolicy.NEVER);
    }
}
