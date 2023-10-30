package ru.spbu.apcyb.svp.tasks.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Directory tree scan test class.
 *
 * @author Evgeny
 */
class FileTreeScanTest {

  private FileTreeScan fileTreeScan;

  @BeforeEach
  void setUp() {
    fileTreeScan = new FileTreeScan(Path.of("src/test/resources"),
        Path.of("src/test/resources/file_scan.txt"));
  }


  @Test
  void scanThrownFileNotFoundException() {
    fileTreeScan.setPathOfDirectory(Path.of("/fd"));
    assertThrows(FileNotFoundException.class, () -> fileTreeScan.scan());
  }

  @Test
  void scanThrownIOException() {
    fileTreeScan.setPathOfDirectory(Path.of("/fd"));
    assertThrows(IOException.class, () -> fileTreeScan.scan());
  }

  @Test
  void mainThrownIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> FileTreeScan.main("src/test/resources"));
  }

  @Test
  void scanResourcesDirectory() {
    Set<String> actualSet = new HashSet<>();

    try {
      BufferedReader bufferedReader = new BufferedReader(
          new FileReader(fileTreeScan.getPathOfTargetFile().toFile()));
      fileTreeScan.scan();
      while (bufferedReader.ready()) {
        actualSet.add(bufferedReader.readLine());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Set<String> expectedSet = new HashSet<>();
    expectedSet.add(
        "Директория: C:\\Users\\jdash\\IdeaProjects\\special-computing-workshop-template_new\\src\\test\\resources");
    expectedSet.add(
        "Файл: C:\\Users\\jdash\\IdeaProjects\\special-computing-workshop-template_new\\src\\test\\resources\\file_scan.txt");

    assertEquals(expectedSet, actualSet);
  }

  @Test
  void scanCountTasksDirectory() {
    fileTreeScan.setPathOfDirectory(Path.of("src/main/java/ru/spbu/apcyb/svp/tasks"));
    int count = 0;
    try {
      BufferedReader bufferedReader = new BufferedReader(
          new FileReader(fileTreeScan.getPathOfTargetFile().toFile()));
      fileTreeScan.scan();
      while (bufferedReader.ready()) {
        count++;
        bufferedReader.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(13, count);
  }


}
