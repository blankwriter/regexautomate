package main.java.org.example.model;



import java.util.*;
import java.util.stream.*;

public class DataAnalyzer {

    public static Map<String, Long> topNWords(List<String> texts, int n) {
        return texts.stream()
                .flatMap(text -> Arrays.stream(text.split("\\W+")))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                        String::toLowerCase,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static Map<String, Long> patternFrequency(List<String> texts, String regexPattern) {
        return texts.stream()
                .collect(Collectors.groupingBy(
                        text -> RegexEngine.containsMatch(text, regexPattern) ? "Matches" : "No Matches",
                        Collectors.counting()
                ));
    }

    public static List<String> filterByPattern(List<String> texts, String regexPattern) {
        return texts.stream()
                .filter(text -> RegexEngine.containsMatch(text, regexPattern))
                .collect(Collectors.toList());
    }
}