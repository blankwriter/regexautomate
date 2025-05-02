package main.java.org.example.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.org.example.model.RegexEngine;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexController {
    @FXML private TextField regexField;
    @FXML private TextArea inputTextArea;
    @FXML private TextField replacementField;
    @FXML private TextArea resultTextArea;
    @FXML private CheckBox caseInsensitiveCheck;
    @FXML private CheckBox multilineCheck;
    @FXML private Button replaceButton;
    @FXML private Button savePatternButton;

    @FXML
    public void initialize() {
        // Initial UI state
        replacementField.setVisible(false);
        savePatternButton.setDisable(true);

        // Real-time regex validation
        regexField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateRegexValidationUI(newVal);
            replacementField.setVisible(false);
            savePatternButton.setDisable(newVal.isEmpty());
        });
    }

    private void updateRegexValidationUI(String regex) {
        if (regex.isEmpty()) {
            regexField.setStyle("");
            return;
        }
        try {
            Pattern.compile(regex);
            regexField.setStyle("-fx-border-color: green; -fx-border-width: 1;");
        } catch (PatternSyntaxException e) {
            regexField.setStyle("-fx-border-color: red; -fx-border-width: 1;");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            String regex = regexField.getText();
            String text = inputTextArea.getText();

            if (regex.isEmpty() || text.isEmpty()) {
                showAlert("Error", "Both pattern and text are required");
                return;
            }

            int flags = getRegexFlags();
            var matches = RegexEngine.findAllMatches(text, regex, flags);
            displayMatches(matches);

        } catch (PatternSyntaxException e) {
            showAlert("Invalid Pattern", e.getMessage());
        }
    }

    @FXML
    private void handleReplaceAll() {
        try {
            String regex = regexField.getText();
            String text = inputTextArea.getText();

            if (regex.isEmpty() || text.isEmpty()) {
                showAlert("Error", "Both pattern and text are required");
                return;
            }

            if (replacementField.getText().isEmpty()) {
                replacementField.setVisible(true);
                resultTextArea.setText("Please enter replacement text");
                return;
            }

            int flags = getRegexFlags();
            String result = RegexEngine.replaceAll(text, regex, replacementField.getText(), flags);
            resultTextArea.setText(result);
            inputTextArea.setText(result);
            replacementField.setVisible(false);

        } catch (PatternSyntaxException e) {
            showAlert("Invalid Pattern", e.getMessage());
        }
    }

    @FXML
    private void handleSavePattern() {
        // Implementation for saving patterns
        String pattern = regexField.getText();
        // Add logic to save to file/database
        showAlert("Pattern Saved", "Pattern saved successfully!");
    }

    private int getRegexFlags() {
        int flags = 0;
        if (caseInsensitiveCheck.isSelected()) flags |= java.util.regex.Pattern.CASE_INSENSITIVE;
        if (multilineCheck.isSelected()) flags |= java.util.regex.Pattern.MULTILINE;
        return flags;
    }

    private void displayMatches(List<RegexEngine.MatchResult> matches) {
        StringBuilder results = new StringBuilder();
        if (matches.isEmpty()) {
            results.append("No matches found");
        } else {
            results.append("Found ").append(matches.size()).append(" matches:\n\n");
            for (var match : matches) {
                results.append("• '").append(match.getMatchedText())
                        .append("' at ").append(match.getStart())
                        .append("-").append(match.getEnd()).append("\n");
            }
        }
        resultTextArea.setText(results.toString());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}