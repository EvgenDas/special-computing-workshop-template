package ru.spbu.apcyb.svp.tasks.task4;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Task4 class.
 *
 * @author Evgeny
 */
class Task4Test {

  private Task4 task4;

  @Test
  void mainThrownIOException() {
    assertThrows(IOException.class,
        () -> Task4.main("src/test/resource/InputDouble.txt",
            "src/test/resource/OutputDouble.txt"));
  }

  @Test
  void calculateTangentsFileToFileThrownIOException() {
    task4 = new Task4(new File("src/test/resource/InputDouble.txt"),
        new File("src/test/resource/OutputDouble.txt"));

    assertThrows(IOException.class,
        () -> task4.calculateTangentsFileToFile());
  }

  @Test
  void calculateTangentsFileToFileDoesNotThrowAnyException() {
    task4 = new Task4(new File("src/test/resources/InputDouble.txt"),
        new File("src/test/resources/OutputDouble.txt"));

    assertDoesNotThrow(() -> task4.calculateTangentsFileToFile());
  }

  @Test
  void calculateTangentsFileToFileTestOneHundredElement()
      throws IOException, ExecutionException, InterruptedException {

    task4 = new Task4(new File("src/test/resources/InputDouble.txt"),
        new File("src/test/resources/OutputDouble.txt"));
    List<Double> actualOutputList = task4.calculateTangentByForkJoinPool(10);
    List<Double> expectedOutputList = task4.calculateTangentSingleThread();

    assertArrayEquals(expectedOutputList.toArray(), actualOutputList.toArray());
  }

  @Test
  void calculateTangentByForkJoinPoolThrownNullPointerException() {
    assertThrows(NullPointerException.class, () -> task4.calculateTangentByForkJoinPool(10));
  }

  @Test
  void calculateTangentSingleThreadThrownNullPointerException() {
    assertThrows(NullPointerException.class, () -> task4.calculateTangentSingleThread());
  }

  @Test
  void calculateTangentByForkJoinPoolFiveElements()
      throws ExecutionException, InterruptedException, IOException {

    task4 = new Task4(new File("src/test/resources/InputDoubleOne.txt"));
    List<Double> actualList = task4.calculateTangentByForkJoinPool(5);

    assertEquals(1.41755748490966, actualList.get(0));
  }

  @Test
  void calculateTangentSingleThreadFiveElements() throws IOException {
    task4 = new Task4(new File("src/test/resources/InputDoubleOne.txt"));
    List<Double> actualList = task4.calculateTangentSingleThread();

    assertEquals(1.41755748490966, actualList.get(0));
  }
}
