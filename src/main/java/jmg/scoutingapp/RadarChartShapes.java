package jmg.scoutingapp;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class RadarChartShapes extends Pane {

    private final Map<String, Double> metrics;
    private final double radius = 100;
    private final double centerX = 150;
    private final double centerY = 150;
    private final int numAxes;

    public RadarChartShapes(Map<String, Double> metrics) {
        this.metrics = metrics;
        this.numAxes = metrics.size();
        drawChart();
    }

    private void drawChart() {
        getChildren().clear();

        // Draw concentric circles
        for (int i = 1; i <= 5; i++) {
            Circle circle = new Circle(centerX, centerY, (radius / 5) * i, Color.TRANSPARENT);
            circle.setStroke(Color.GRAY);
            circle.getStrokeDashArray().addAll(5.0, 5.0);
            getChildren().add(circle);
        }

        List<Point> dataPoints = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            double angle = Math.toRadians(360.0 / numAxes * i - 90);
            double value = entry.getValue();
            double adjustedRadius = (value / 100) * radius;

            double x = centerX + adjustedRadius * Math.cos(angle);
            double y = centerY + adjustedRadius * Math.sin(angle);
            dataPoints.add(new Point(x, y));

            // Draw axis line
            Line axis = new Line(centerX, centerY, centerX + radius * Math.cos(angle), centerY + radius * Math.sin(angle));
            axis.setStroke(Color.DARKGRAY);
            getChildren().add(axis);

            // Draw axis label
            Text label = new Text(entry.getKey());
            // Adjust label position to avoid overlap
            double labelX = centerX + (radius + 15) * Math.cos(angle);
            double labelY = centerY + (radius + 15) * Math.sin(angle);
            if (angle > Math.toRadians(90) && angle < Math.toRadians(270)) {
                labelX -= label.getLayoutBounds().getWidth();
            }
            if (angle > Math.toRadians(180)) {
                labelY += 10;
            } else {
                labelY -= 5;
            }
            label.setX(labelX);
            label.setY(labelY);
            getChildren().add(label);

            i++;
        }

        // Draw data polygon
        Polygon dataPolygon = new Polygon();
        for (Point p : dataPoints) {
            dataPolygon.getPoints().addAll(p.x, p.y);
        }
        dataPolygon.setFill(Color.CORNFLOWERBLUE.deriveColor(1, 1, 1, 0.6));
        dataPolygon.setStroke(Color.ROYALBLUE);
        dataPolygon.setStrokeWidth(2);
        getChildren().add(dataPolygon);
    }
    
    private static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}