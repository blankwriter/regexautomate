package main.java.org.example.model;



import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class TextProcessor {

    public static Map<String, Long> wordFrequency(String text) {
        return Arrays.stream(text.split("\\W+"))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                        String::toLowerCase,
                        Collectors.counting()
                ));
    }

    public static String summarizeText(String text, int maxSentences) {
        String[] sentences = text.split("[.!?]+");
        return Arrays.stream(sentences)
                .limit(maxSentences)
                .collect(Collectors.joining(". ")) + ".";
    }

    public static List<String> extractEmails(String text) {
        String emailRegex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
        int flags = Pattern.CASE_INSENSITIVE;
        return RegexEngine.findAllMatches(text, emailRegex, flags ).stream()
                .map(RegexEngine.MatchResult::getMatchedText)
                .collect(Collectors.toList());
    }

    public static String cleanText(String text) {
        // Remove extra whitespace
        text = text.replaceAll("\\s+", " ").trim();
        // Standardize line endings
        text = text.replaceAll("\\r\\n?", "\n");
        return text;
    }
}