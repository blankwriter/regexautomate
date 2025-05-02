package main.java.org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MainController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab regexTab;
    @FXML private Tab dataAnalysisTab;
    @FXML private Tab fileProcessingTab;

    @FXML
    public void initialize() {
        mainTabPane.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            System.out.println("Opened file: " + file.getAbsolutePath());
            // Add your file-loading logic here
        }
    }

    @FXML
    private void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            System.out.println("Saving to: " + file.getAbsolutePath());
            // Add your file-saving logic here
        }
    }

    @FXML
    private void switchToRegexTab() {
        mainTabPane.getSelectionModel().select(regexTab);
    }

    @FXML
    private void switchToFileProcessingTab() {
        mainTabPane.getSelectionModel().select(fileProcessingTab);
    }

    @FXML
    private void switchToDataAnalysisTab() {
        mainTabPane.getSelectionModel().select(dataAnalysisTab);
    }
}
