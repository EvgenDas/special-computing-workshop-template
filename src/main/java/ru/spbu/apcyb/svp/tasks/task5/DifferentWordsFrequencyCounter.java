package ru.spbu.apcyb.svp.tasks.task5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that implements methods for finding different words in a file and writing them to separate
 * files.
 *
 * @author Evgeny
 */
public class DifferentWordsFrequencyCounter {

  public static final Logger logger = Logger.getLogger(
      DifferentWordsFrequencyCounter.class.getName());

  /**
   * The starting point for the start of the program.
   *
   * @param args args[0] Path to the file from which the words are read. args[1] Path in which the
   *             results of counting different words are write
   * @throws IOException file reading/writing error
   */
  public static void main(String... args) throws IOException {
    Path inputPath = Path.of(args[0]);
    Path outputPath = Path.of(args[1]);

    Map<String, Long> words = countWords(inputPath);
    writeWordsToFile(words, outputPath);
    writeWordsToIndividualFiles(words, outputPath);
  }

  /**
   * Method for counting the number of different words.
   *
   * @param inputPath Path to the file from which the words are read
   * @return Map containing words and their number of repetitions
   * @throws IOException file reading error
   */
  public static Map<String, Long> countWords(Path inputPath) throws IOException {
    try (Stream<String> stringStream = Files.lines(inputPath)) {
      return stringStream.flatMap(line -> Arrays.stream(line.split(" ")))
          .map(word -> word.replaceAll("[^\\p{L}\\p{N}]+", ""))
          .filter(word -> !word.isEmpty())
          .map(String::toLowerCase)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    } catch (IOException e) {
      throw new IOException(e + "\nError while reading from input file");
    }
  }

  /**
   * Method that writes different words and the number of their repetitions.
   *
   * @param words      Map containing words and their number of repetitions
   * @param outputPath Path in which the results of counting different words are write
   * @throws IOException file writing error
   */
  public static void writeWordsToFile(Map<String, Long> words, Path outputPath) throws IOException {
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outputPath)) {
      for (Map.Entry<String, Long> entry : words.entrySet()) {
        bufferedWriter.write(entry.getKey() + " : " + entry.getValue() + "\n");
      }
    } catch (IOException e) {
      throw new IOException(e + "\nError while writing to output file");
    }
  }

  /**
   * Method that writes different words to separate files as many times as the number specified in
   * the map value.
   *
   * @param words      Map containing words and their number of repetitions
   * @param outputFile Path to directory the results of writes different words to separate files
   * @return Path of directory
   * @throws IOException file writing error
   */

  public static Path writeWordsToIndividualFiles(Map<String, Long> words, Path outputFile)
      throws IOException {
    Path directory = Path.of(outputFile.toString()
        .substring(0, outputFile.toString().lastIndexOf(".")));

    try {
      if (!Files.exists(directory)) {
        Files.createDirectory(directory);
      }
      words.forEach((word, iteration) -> CompletableFuture.runAsync(
          () -> writeWordToIndividualFile(word, iteration, directory)).join());
      logger.log(Level.INFO, "output directory {0}", directory);
      return directory;
    } catch (IOException ex) {
      throw new IOException(ex + "\nError while writing words to separate file");
    }
  }

  private static void writeWordToIndividualFile(String word, Long iteration, Path directoryPath) {
    Path filePath = Path.of(String.valueOf(directoryPath), word + ".txt");
    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
      for (long i = 0; i < iteration; i++) {
        bufferedWriter.write(word + " ");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException(e + "\nError while writing word to file");
    }
  }
}
