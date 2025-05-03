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
                "Top N Words",
                "Summarize Text",
                "Clean Text"

        );
        analysisTypeChoice.setValue("Word Frequency");

        // Hide topNField by default
        // Show/hide numeric input for options that need a number (Top N, Summarize)
        analysisTypeChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            topNField.setVisible("Top N Words".equals(newVal) || "Summarize Text".equals(newVal));
        });

        topNField.setPromptText("Enter number");
        topNField.setVisible(false); // hidden by default
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
                    if (topNField.getText().isEmpty()) {
                        analysisResults.setText("Error: Please enter a value for N");
                        return;
                    }
                    int n = Integer.parseInt(topNField.getText());
                    Map<String, Long> topWords = DataAnalyzer.topNWords(List.of(text.split("\\n")), n);
                    analysisResults.setText(formatMap(topWords));
                    break;

                case "Summarize Text":
                    if (topNField.getText().isEmpty()) {
                        analysisResults.setText("Error: Please enter number of sentences");
                        return;
                    }
                    int numSentences = Integer.parseInt(topNField.getText());
                    String summary = TextProcessor.summarizeText(text, numSentences);
                    analysisResults.setText(summary);
                    break;

                case "Clean Text":
                    String cleaned = TextProcessor.cleanText(text);
                    analysisResults.setText(cleaned);
                    break;
            }
        } catch (NumberFormatException e) {
            analysisResults.setText("Error: Please enter a valid number for N");
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