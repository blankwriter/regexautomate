package main.java.org.example.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import main.java.org.example.model.DataAnalyzer;
import main.java.org.example.model.TextProcessor;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataAnalysisController {
    @FXML private TextArea analysisInputText;
    @FXML private TextField topNField;
    @FXML private TextArea analysisResults;
    @FXML private ChoiceBox<String> analysisTypeChoice;

    @FXML
    public void initialize() {
        analysisTypeChoice.getItems().addAll(
                "Word Frequency",
                "Email Extraction",
                "Top N Words"
        );
        analysisTypeChoice.setValue("Word Frequency");
    }

    @FXML
    private void handleAnalyze() {
        String text = analysisInputText.getText();
        String analysisType = analysisTypeChoice.getValue();

        try {
            switch (analysisType) {
                case "Word Frequency":
                    Map<String, Long> frequencies = TextProcessor.wordFrequency(text);
                    analysisResults.setText(formatMap(frequencies));
                    break;

                case "Email Extraction":
                    List<String> emails = TextProcessor.extractEmails(text);
                    analysisResults.setText(emails.stream().collect(Collectors.joining("\n")));
                    break;

                case "Top N Words":
                    int n = Integer.parseInt(topNField.getText());
                    Map<String, Long> topWords = DataAnalyzer.topNWords(List.of(text), n);
                    analysisResults.setText(formatMap(topWords));
                    break;
            }
        } catch (Exception e) {
            analysisResults.setText("Error: " + e.getMessage());
        }
    }

    private String formatMap(Map<String, Long> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}