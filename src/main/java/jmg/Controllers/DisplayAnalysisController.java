package jmg.scoutingapp;

import java.util.LinkedHashMap;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextArea;
import java.util.Map;
import javafx.scene.control.Label;
import java.util.Map;
import java.util.LinkedHashMap;

public class DisplayAnalysisController {

    @FXML
    private StackPane radarChartPane;

    @FXML
    private TextArea styleOfPlayArea;

    private Map<String, Double> metrics;

    @FXML
    public void initialize() {
        // Example metrics
        metrics = new LinkedHashMap<>();
        metrics.put("Creativity", 80.0);
        metrics.put("OutOfPossession", 65.0);
        metrics.put("DefensiveTerritory", 70.0);
        metrics.put("HighRegains", 60.0);
        metrics.put("CreativityInFinalThird", 75.0);
        metrics.put("BallProgression", 85.0);

        styleOfPlayArea.setText("Player prefers fast transitions, maintains width, and creates overloads in the final third.");

        // Display radar chart (choose one)
        displayRadarChartShapes();
        // OR
        // displayRadarChartCanvas();
    }

    private void displayRadarChartShapes() {
        radarChartPane.getChildren().clear();
        RadarChartShapes radar = new RadarChartShapes(metrics);
        radarChartPane.getChildren().add(radar);
    }

    private void displayRadarChartCanvas() {
        radarChartPane.getChildren().clear();
        RadarChartCanvas radar=new RadarChartCanvas();
        radarChartPane.getChildren().add(RadarChartCanvas.createRadarChart(metrics, 300, 300));
    }
}
