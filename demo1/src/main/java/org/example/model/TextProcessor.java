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

    public static String summarizeText(String input, int sentenceCount) {
        String[] sentences = input.split("(?<=[.!?])\\s+");  // split on period/exclamation/question + whitespace
        return Arrays.stream(sentences)
                .limit(sentenceCount)
                .collect(Collectors.joining(" "));
    }


    public static List<String> extractEmails(String text) {
        String emailRegex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
        int flags = Pattern.CASE_INSENSITIVE;
        return RegexEngine.findAllMatches(text, emailRegex, flags ).stream()
                .map(RegexEngine.MatchResult::getMatchedText)
                .collect(Collectors.toList());
    }

    public static String cleanText(String input) {
        return input
                .replaceAll("\r\n", "\n")    // Normalize newlines
                .replaceAll("\t", " ")       // Replace tabs with space
                .replaceAll(" +", " ")       // Collapse multiple spaces
                .replaceAll(" *\n *", "\n")  // Trim around newlines
                .trim();                     // Final trim
    }

}