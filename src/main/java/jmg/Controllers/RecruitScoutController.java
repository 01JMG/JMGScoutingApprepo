package jmg.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import jmg.UserStructurecode.Scout;



import java.util.List;

public class RecruitScoutController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Scout> scoutTable;

    @FXML
    private TableColumn<Scout, String> nameColumn;
    @FXML
    private TableColumn<Scout, String> skillColumn;
    @FXML
    private TableColumn<Scout, String> locationColumn;
    @FXML
    private TableColumn<Scout, Double> successRateColumn;

    @FXML
    private TextArea scoutStatsArea;

    private ObservableList<Scout> scoutList = FXCollections.observableArrayList();

    public void initialize() {
        // Set up table columns
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        skillColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSkill()));
        locationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLocation()));
        successRateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSuccessRate()).asObject());

        scoutTable.setItems(scoutList);
        loadDummyScouts();
    }

    private void loadDummyScouts() {
        scoutList.addAll(
            new Scout(1, "John Doe", "Striker Analysis", "Zambia"),
            new Scout(2, "Jane Smith", "Goalkeeper Analysis", "South Africa")
        );
    }

    @FXML
    private void searchScouts() {
        String query = searchField.getText().toLowerCase();
        scoutTable.setItems(scoutList.filtered(s -> s.getName().toLowerCase().contains(query) 
                                                || s.getSkill().toLowerCase().contains(query)));
    }

    @FXML
    private void showScoutStats() {
        Scout selected = scoutTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            scoutStatsArea.setText("Name: " + selected.getName() + "\n" +
                                   "Skill: " + selected.getSkill() + "\n" +
                                   "Location: " + selected.getLocation() + "\n" +
                                   "Players Scouted: " + selected.getTotalPlayersScouted() + "\n" +
                                   "Success Rate: " + String.format("%.2f", selected.getSuccessRate()) + "%");
        }
    }
}
