package test.unittest;

import main.java.org.example.model.FileManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    @TempDir
    Path tempDir;

    @Test
    public void testReadAndWriteFile() throws IOException {
        Path testFile = tempDir.resolve("testfile.txt");
        List<String> content = List.of("Apple", "Banana", "Cherry");

        FileManager.writeFile(testFile.toString(), content);

        List<String> readLines = FileManager.readFile(testFile.toString());
        assertEquals(content, readLines);
    }

    @Test
    public void testProcessFile() throws IOException {
        Path input = tempDir.resolve("input.txt");
        Path output = tempDir.resolve("output.txt");
        List<String> inputLines = List.of("hello", "world");

        FileManager.writeFile(input.toString(), inputLines);

        FileManager.processFile(input.toString(), output.toString(), String::toUpperCase);
        List<String> outputLines = FileManager.readFile(output.toString());

        assertEquals(List.of("HELLO", "WORLD"), outputLines);
    }

    @Test
    public void testProcessFileInBatch() throws IOException {
        Path input = tempDir.resolve("batch_input.txt");
        Path output = tempDir.resolve("batch_output.txt");
        List<String> lines = List.of("1", "2", "3");

        FileManager.writeFile(input.toString(), lines);

        FileManager.processFileInBatch(input.toString(), output.toString(), line -> "Line: " + line);
        List<String> result = FileManager.readFile(output.toString());

        assertEquals(List.of("Line: 1", "Line: 2", "Line: 3"), result);
    }

    @Test
    public void testFindFilesWithPattern() throws IOException {
        Path file1 = tempDir.resolve("report1.txt");
        Path file2 = tempDir.resolve("report2.txt");
        Path file3 = tempDir.resolve("ignore.md");

        FileManager.writeFile(file1.toString(), List.of("data"));
        FileManager.writeFile(file2.toString(), List.of("data"));
        FileManager.writeFile(file3.toString(), List.of("data"));

        List<String> matchedFiles = FileManager.findFilesWithPattern(tempDir.toString(), ".*\\.txt");

        List<String> fileNames = matchedFiles.stream()
                .map(path -> Paths.get(path).getFileName().toString())
                .collect(Collectors.toList());

        assertTrue(fileNames.contains("report1.txt"));
        assertTrue(fileNames.contains("report2.txt"));
        assertFalse(fileNames.contains("ignore.md"));
    }

    @Test
    public void testReadFile_FileNotFound() {
        Path fakePath = tempDir.resolve("nonexistent.txt");
        assertThrows(FileNotFoundException.class, () -> FileManager.readFile(fakePath.toString()));
    }

    @Test
    public void testProcessFile_InputIsDirectory() throws IOException {
        Path directoryPath = tempDir.resolve("directory");
        Files.createDirectory(directoryPath);

        Path output = tempDir.resolve("out.txt");
        assertThrows(IOException.class, () ->
                FileManager.processFile(directoryPath.toString(), output.toString(), s -> s));
    }
}
