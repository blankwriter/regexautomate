package main.java.org.example.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.org.example.model.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import javafx.stage.FileChooser;

public class FileProcessingController {
    @FXML private TextArea fileInputTextArea;
    @FXML private TextField inputFilePathField;
    @FXML private TextField outputFilePathField;
    @FXML private TextField searchPatternField;
    @FXML private TextField replacePatternField;
    @FXML private TextArea fileOutputTextArea;

    @FXML
    private void handleLoadFile() {
        Task<List<String>> loadTask = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return FileManager.readFile(inputFilePathField.getText());
            }
        };

        loadTask.setOnSucceeded(e -> {
            fileInputTextArea.setText(String.join("\n", loadTask.getValue()));
        });

        loadTask.setOnFailed(e -> {
            showAlert("Error", "Failed to load file");
        });

        new Thread(loadTask).start();
    }

    @FXML
    private void handleProcessFile() {
        if (inputFilePathField.getText().isEmpty() || outputFilePathField.getText().isEmpty()) {
            showAlert("Error", "Please select input and output files");
            return;
        }
        Function<String, String> processor = line ->
                line.replaceAll(searchPatternField.getText(), replacePatternField.getText());

        try {
            FileManager.processFile(
                    inputFilePathField.getText(),
                    outputFilePathField.getText(),
                    processor
            );
            fileOutputTextArea.setText("File processed successfully!");
        } catch (IOException e) {
            showAlert("Processing Error", "Failed to process file: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleBrowseInput() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Input File");
        File file = fileChooser.showOpenDialog(inputFilePathField.getScene().getWindow());
        if (file != null) {
            inputFilePathField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleBrowseOutput() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Output File");
        File file = fileChooser.showSaveDialog(outputFilePathField.getScene().getWindow());
        if (file != null) {
            outputFilePathField.setText(file.getAbsolutePath());
        }
    }
}