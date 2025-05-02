package main.java.org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.org.example.model.RegexEngine;

import java.util.regex.*;

public class RegexController {
    @FXML private TextField regexField;
    @FXML private TextArea inputTextArea;
    @FXML private TextField replacementField;
    @FXML private TextArea resultTextArea;

    @FXML
    public void initialize() {
        // Show/hide replacement field based on button clicks
        replacementField.visibleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) replacementField.requestFocus();
        });
    }

    @FXML
    private void handleSearch() {
        try {
            String regex = regexField.getText();
            String text = inputTextArea.getText();

            if (regex.isEmpty() || text.isEmpty()) {
                resultTextArea.setText("Error: Both pattern and text are required");
                return;
            }

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            StringBuilder results = new StringBuilder();
            while (matcher.find()) {
                results.append("Found: '").append(matcher.group())
                        .append("' at position ").append(matcher.start())
                        .append("-").append(matcher.end()).append("\n");
            }

            resultTextArea.setText(results.length() > 0 ? results.toString() : "No matches found");
            replacementField.setVisible(false);

        } catch (PatternSyntaxException e) {
            resultTextArea.setText("Invalid regex pattern: " + e.getMessage());
        }
    }

    @FXML
    private void handleReplaceAll() {
        try {
            replacementField.setVisible(true);
            if (replacementField.getText().isEmpty()) return;

            String regex = regexField.getText();
            String text = inputTextArea.getText();
            String replacement = replacementField.getText();

            if (regex.isEmpty() || text.isEmpty()) {
                resultTextArea.setText("Error: Both pattern and text are required");
                return;
            }

            String result = text.replaceAll(regex, replacement);
            resultTextArea.setText(result);

        } catch (PatternSyntaxException e) {
            resultTextArea.setText("Invalid regex pattern: " + e.getMessage());
        } catch (Exception e) {
            resultTextArea.setText("Error during replacement: " + e.getMessage());
        }
    }
}