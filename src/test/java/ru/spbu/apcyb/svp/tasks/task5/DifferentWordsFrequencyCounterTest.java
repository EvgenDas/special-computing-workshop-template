package ru.spbu.apcyb.svp.tasks.task5;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests for the UniqueWordFrequencyCounter class.
 *
 * @author Evgeny
 */
class DifferentWordsFrequencyCounterTest {

  private final Path inputPath = Path.of("src/test/resources/inputSmallFile.txt");
  private final Path outputPath = Path.of("src/test/resources/outputSmallFile.txt");

  @Test
  void mainDoesNotThrowAnyException() {
    assertDoesNotThrow(() ->
        DifferentWordsFrequencyCounter.main(inputPath.toString(), outputPath.toString()));
  }

  @Test
  void countWordsTestOnSmallFile() throws IOException {
    Map<String, Long> expectedMap = new HashMap<>();
    expectedMap.put("foo", 6L);
    expectedMap.put("bar", 3L);
    expectedMap.put("baz", 3L);
    Map<String, Long> actualMap = DifferentWordsFrequencyCounter.countWords(inputPath);
    assertEquals(expectedMap, actualMap);
  }

  @Test
  void writeWordsToFileDoesNotThrowAnyException() {
    assertDoesNotThrow(() ->
        DifferentWordsFrequencyCounter
            .writeWordsToFile(DifferentWordsFrequencyCounter.countWords(inputPath), outputPath));
  }

  @Test
  void writeWordsToIndividualFilesTestOnSmallFile() throws IOException {
    Map<String, Long> map = DifferentWordsFrequencyCounter.countWords(inputPath);
    Path directory = DifferentWordsFrequencyCounter.writeWordsToIndividualFiles(map, outputPath);
    Set<String> expectedPaths = map.keySet();
    Set<String> actualPaths = new HashSet<>();

    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
      for (Path path : dirStream) {
        actualPaths.add(String.valueOf(path.getFileName()).replace(".txt", ""));
      }
    }
    assertEquals(expectedPaths, actualPaths);
  }
}
