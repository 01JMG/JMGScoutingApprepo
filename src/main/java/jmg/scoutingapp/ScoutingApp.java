package jmg.scoutingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScoutingApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML layout
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("OAuth.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAddMenu.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Football Scouting App - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launches JavaFX application
    }
}
