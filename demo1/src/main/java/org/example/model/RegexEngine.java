package main.java.org.example.model;


import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class RegexEngine {

    public static List<MatchResult> findAllMatches(String text, String regexPattern) {
        List<MatchResult> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(new MatchResult(matcher.group(), matcher.start(), matcher.end()));
        }

        return matches;
    }

    public static String replaceAll(String text, String regexPattern, String replacement) {
        return text.replaceAll(regexPattern, replacement);
    }

    public static boolean containsMatch(String text, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(text).find();
    }

    public static class MatchResult {
        private final String matchedText;
        private final int start;
        private final int end;

        public MatchResult(String matchedText, int start, int end) {
            this.matchedText = matchedText;
            this.start = start;
            this.end = end;
        }

        // Getters
        public String getMatchedText() { return matchedText; }
        public int getStart() { return start; }
        public int getEnd() { return end; }
    }
}
