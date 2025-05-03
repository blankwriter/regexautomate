package test.unittest;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.java.org.example.model.RegexEngine;

import java.util.List;
import java.util.regex.Pattern;

public class RegexEngineTest {

    @Test
    public void testFindAllMatches_WithoutFlags() {
        String text = "Hello world! Hello Java!";
        List<RegexEngine.MatchResult> matches = RegexEngine.findAllMatches(text, "Hello");
        assertEquals(2, matches.size());
        assertEquals("Hello", matches.get(0).getMatchedText());
    }

    @Test
    public void testFindAllMatches_WithCaseInsensitiveFlag() {
        String text = "hello HELLO HeLLo";
        List<RegexEngine.MatchResult> matches = RegexEngine.findAllMatches(text, "hello", Pattern.CASE_INSENSITIVE);
        assertEquals(3, matches.size());
        assertEquals("hello", matches.get(0).getMatchedText().toLowerCase());
    }

    @Test
    public void testFindAllMatches_NoMatches() {
        String text = "No match here";
        List<RegexEngine.MatchResult> matches = RegexEngine.findAllMatches(text, "apple");
        assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindAllMatches_InvalidPattern() {
        assertThrows(java.util.regex.PatternSyntaxException.class, () -> {
            RegexEngine.findAllMatches("test", "*invalid");
        });
    }

    @Test
    public void testFindAllMatches_MultilineMode() {
        String text = "start\nmiddle\nend";
        List<RegexEngine.MatchResult> matches = RegexEngine.findAllMatches(text, "^middle$", Pattern.MULTILINE);
        assertEquals(1, matches.size());
        assertEquals("middle", matches.get(0).getMatchedText());
    }

    @Test
    public void testReplaceAll_WithoutFlags() {
        String input = "apple orange apple";
        String result = RegexEngine.replaceAll(input, "apple", "fruit");
        assertEquals("fruit orange fruit", result);
    }

    @Test
    public void testReplaceAll_WithFlags() {
        String input = "Apple ORANGE apple";
        String result = RegexEngine.replaceAll(input, "apple", "fruit", Pattern.CASE_INSENSITIVE);
        assertEquals("fruit ORANGE fruit", result);
    }

    @Test
    public void testReplaceAll_NoMatch() {
        String input = "banana grape";
        String result = RegexEngine.replaceAll(input, "apple", "fruit");
        assertEquals("banana grape", result);  // No change
    }

    @Test
    public void testReplaceAll_EmptyText() {
        String result = RegexEngine.replaceAll("", "apple", "fruit");
        assertEquals("", result);
    }

    @Test
    public void testReplaceAll_EmptyPattern_ReplacesBetweenCharacters() {
        String result = RegexEngine.replaceAll("abc", "", "-");
        assertEquals("-a-b-c-", result);
    }

}