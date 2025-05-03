package test.unittest;

import main.java.org.example.model.TextProcessor;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TextProcessorTest {
    @Test
    public void testWordFrequency() {
        String text = "hello world hello java";
        Map<String, Long> frequency = TextProcessor.wordFrequency(text);

        assertEquals(2, frequency.get("hello"));
        assertEquals(1, frequency.get("world"));
        assertNull(frequency.get("nonexistent"));
    }

    @Test
    public void testExtractEmails() {
        String text = "Contact me@example.com or support@company.org";
        List<String> emails = TextProcessor.extractEmails(text);

        assertEquals(2, emails.size());
        assertTrue(emails.contains("me@example.com"));
        assertTrue(emails.contains("support@company.org"));
    }

    @Test
    public void testSummarizeText() {
        String input = "Java is great. It is portable. Runs on many platforms!";
        String summary = TextProcessor.summarizeText(input, 2);
        assertEquals("Java is great. It is portable.", summary);
    }

    @Test
    public void testCleanText() {
        String messy = "  Hello   world \r\nThis is\tJava! ";
        String cleaned = TextProcessor.cleanText(messy);
        assertEquals("Hello world\nThis is Java!", cleaned);
    }

}