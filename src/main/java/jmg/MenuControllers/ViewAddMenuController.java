package jmg.MenuControllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import jmg.Controllers.HighLightsController;

public class ViewAddMenuController {

    @FXML private Button addPlayerButton;
    @FXML private Button viewPlayersButton;
    
    @FXML
    private void handleAddPlayer() {
        loadScene("AddPlayerToSystem.fxml");
    }

    @FXML
    private void handleViewPlayers() {
        loadScene("ViewPlayersInSystem.fxml");
    }
    
    @FXML
    private void handleCompareStats()
    {
        loadScene("CompareStats.fxml");
    }
    
    @FXML
    private void handleUploadVideo()
    {
        loadScene("UploadVideo.fxml");
    }
    
    @FXML
    private void handleWatchVideo()
    {
        loadScene("WatchVideo.fxml");
    }
    
    @FXML
    private void handleCreateUser()
    {
        loadScene("CreateUser.fxml");
    }
    
    @FXML
    private void handleOneToOnetext()
    {
        loadScene("OneToOneText.fxml");
    }
    
    @FXML
    private void handleScoutReport()
    {
        loadScene("CreateScoutReport.fxml");
    }
    @FXML
    private void handleDisplayAnalysis(javafx.event.ActionEvent event)
    {
        loadScene("DisplayAnalysis.fxml");
    }
    
    @FXML
    private void handleViewOnTheField()
    {
        loadScene("ViewtheOnTheField.fxml");
    }
    
    @FXML
    private void handlePostOnTheField()
    {
        loadScene("PostOnTheField.fxml");
    }
    @FXML
    private void handleHighLights() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Highlights.fxml"));
            Parent root = loader.load();

            HighLightsController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Highlights Feed");

            controller.setPrimaryStage(stage); // Pass stage for auto rotation detection

            stage.setScene(new Scene(root, 400, 700));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void handleRecruitScout()
    {
        loadScene("RecruitScout.fxml");
    }
    
    
    @FXML
    private void handleFollowController(javafx.event.ActionEvent event)
    {
        loadScene("Follow.fxml");
    }
    
    
    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jmg/Scoutingapp/"+fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Scouting App");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
