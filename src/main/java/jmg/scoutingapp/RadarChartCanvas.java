package jmg.scoutingapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;

import java.util.Map;

public class RadarChartCanvas {

    public static Canvas createRadarChart(Map<String, Double> metrics, double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double centerX = width / 2;
        double centerY = height / 2;
        double radius = Math.min(width, height) / 3;

        String[] keys = metrics.keySet().toArray(new String[0]);
        int n = keys.length;

        // Draw axes
        gc.setStroke(Color.GRAY);
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI / n * i - Math.PI / 2;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            gc.strokeLine(centerX, centerY, x, y);
            gc.fillText(keys[i], x - 15, y - 5);
        }

        // Draw polygon
        gc.setStroke(Color.web("#C0392B"));
        gc.setFill(Color.web("#C0392B", 0.4));
        gc.beginPath();
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI / n * i - Math.PI / 2;
            double value = metrics.get(keys[i]) / 100.0;
            double x = centerX + radius * value * Math.cos(angle);
            double y = centerY + radius * value * Math.sin(angle);
            if (i == 0) gc.moveTo(x, y);
            else gc.lineTo(x, y);
        }
        gc.closePath();
        gc.fill();
        gc.stroke();

        return canvas;
    }
}
