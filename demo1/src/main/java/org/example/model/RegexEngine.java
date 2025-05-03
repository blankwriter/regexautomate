package main.java.org.example.model;


import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class RegexEngine {

    public static List<MatchResult> findAllMatches(String text, String regexPattern, int flags) {
        List<MatchResult> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexPattern, flags);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(new MatchResult(matcher.group(), matcher.start(), matcher.end()));
        }

        return matches;
    }

    // Without flags (default)
    public static List<MatchResult> findAllMatches(String text, String regexPattern) {
        return findAllMatches(text, regexPattern, 0);
    }


    public static String replaceAll(String text, String regexPattern, String replacement, int flags) {
        Pattern pattern = Pattern.compile(regexPattern, flags);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(replacement);  // replaces all matches
}

    // Overloaded version without flags (default behavior)
    public static String replaceAll(String text, String regexPattern, String replacement) {
        return replaceAll(text, regexPattern, replacement, 0); // delegates to the flag version
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
