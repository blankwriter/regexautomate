package main.java.org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MainController {
    @FXML private TabPane mainTabPane;

    // Initialize method called after FXML loading
    public void initialize() {
        // Set the first tab as selected by default
        mainTabPane.getSelectionModel().selectFirst();
    }

    // Add methods for menu actions if needed
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}