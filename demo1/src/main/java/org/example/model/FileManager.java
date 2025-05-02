package main.java.org.example.model;



import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class FileManager {

    public static List<String> readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.collect(Collectors.toList());
        }
    }

    public static void writeFile(String filePath, List<String> content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void processFileInBatch(String inputPath, String outputPath,
                                          Function<String, String> processor) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(processor.apply(line));
                writer.newLine();
            }
        }
    }

    public static void processFile(String inputPath, String outputPath,
                                   Function<String, String> processor) throws IOException {
        Path inPath = Paths.get(inputPath);
        Path outPath = Paths.get(outputPath);

        if (!Files.exists(inPath)) {
            throw new FileNotFoundException("Input file not found");
        }

        if (Files.isDirectory(inPath)) {
            throw new IOException("Input path is a directory");
        }

        try (BufferedReader reader = Files.newBufferedReader(inPath);
             BufferedWriter writer = Files.newBufferedWriter(outPath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(processor.apply(line));
                writer.newLine();
            }
        }
    }

    public static List<String> findFilesWithPattern(String directory, String pattern) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().matches(pattern))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }
}