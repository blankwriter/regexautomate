package main.java.org.example.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import main.java.org.example.model.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import javafx.stage.FileChooser;
import main.java.org.example.model.TextProcessor;

public class FileProcessingController {
    @FXML private TextArea fileInputTextArea;
    @FXML private TextField inputFilePathField;
    @FXML private TextField outputFilePathField;
    @FXML private TextField searchPatternField;
    @FXML private TextField replacePatternField;
    @FXML private TextArea fileOutputTextArea;

    @FXML private TextField directoryPathField;
    @FXML private TextField filePatternField;
    @FXML private TextArea fileSearchResultsArea;
    @FXML private TextField filenamePatternField;
    @FXML private TextArea foundFilesTextArea;

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

    @FXML
    private void handleProcessFileInBatch() {
        if (inputFilePathField.getText().isEmpty() || outputFilePathField.getText().isEmpty()) {
            showAlert("Error", "Please select input and output files");
            return;
        }
        try {
            FileManager.processFileInBatch(inputFilePathField.getText(), outputFilePathField.getText(), TextProcessor::cleanText);
            fileOutputTextArea.setText("Batch processed using cleanText successfully!");
        } catch (IOException e) {
            showAlert("Batch Processing Error", "Failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchFilesWithPattern() {
        if (directoryPathField.getText().isEmpty() || filePatternField.getText().isEmpty()) {
            showAlert("Error", "Please enter both directory and pattern");
            return;
        }
        try {
            List<String> matches = FileManager.findFilesWithPattern(directoryPathField.getText(), filePatternField.getText());
            fileSearchResultsArea.setText(String.join("\n", matches));
        } catch (IOException e) {
            showAlert("Search Error", "Failed to search files: " + e.getMessage());
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
    private void handleBrowseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDirectory = directoryChooser.showDialog(directoryPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleFindFiles() {
        String dir = directoryPathField.getText();
        String pattern = filenamePatternField.getText();

        if (dir.isEmpty() || pattern.isEmpty()) {
            showAlert("Input Error", "Please provide both directory and pattern.");
            return;
        }

        Task<List<String>> searchTask = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return FileManager.findFilesWithPattern(dir, pattern);
            }
        };

        searchTask.setOnSucceeded(e -> {
            List<String> files = searchTask.getValue();
            if (files.isEmpty()) {
                foundFilesTextArea.setText("No matching files found.");
            } else {
                foundFilesTextArea.setText(String.join("\n", files));
            }
        });

        searchTask.setOnFailed(e -> {
            showAlert("Search Error", "Failed to search files: " + searchTask.getException().getMessage());
        });

        new Thread(searchTask).start();
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