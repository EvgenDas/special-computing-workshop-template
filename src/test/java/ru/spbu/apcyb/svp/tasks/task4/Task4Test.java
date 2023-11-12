package ru.spbu.apcyb.svp.tasks.task4;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Task4 class.
 *
 * @author Evgeny
 */
class Task4Test {

  private Task4 task4;

  @BeforeEach
  void setUp() {
    task4 = new Task4();
  }

  @Test
  void mainThrownIOException() {
    assertThrows(IOException.class,
        () -> Task4.main("src/test/resource/InputDouble.txt",
            "src/test/resource/OutputDouble.txt"));
  }

  @Test
  void calculateTangentsFileToFileThrownIOException() {
    File inputFile = new File("src/test/resource/InputDouble.txt");
    File outputFile = new File("src/test/resource/OutputDouble.txt");
    assertThrows(IOException.class,
        () -> task4.calculateTangentsFileToFile(inputFile, outputFile));
  }

  @Test
  void calculateTangentsFileToFileTestOneHundredElement()
      throws IOException, ExecutionException, InterruptedException {
    File inputFile = new File("src/test/resources/InputDouble.txt");
    File outputFile = new File("src/test/resources/OutputDouble.txt");

    task4.calculateTangentsFileToFile(inputFile, outputFile);

    List<Double> actualOutputList = task4.getListOfOutputDataMultithreading();
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
      throws ExecutionException, InterruptedException {

    task4.setListOfInputData(Arrays.asList(1.0, 1.1, 1.2, 1.3, 1.4));
    task4.calculateTangentByForkJoinPool(5);
    List<Double> expectedList = Arrays.asList(1.5574077246549023, 1.9647596572486523,
        2.5721516221263188, 3.6021024479679786, 5.797883715482887);

    assertArrayEquals(expectedList.toArray(), task4.getListOfOutputDataMultithreading().toArray());
  }

  @Test
  void calculateTangentSingleThreadFiveElements() {

    task4.setListOfInputData(Arrays.asList(1.0, 1.1, 1.2, 1.3, 1.4));
    List<Double> actualList = task4.calculateTangentSingleThread();
    List<Double> expectedList = Arrays.asList(1.5574077246549023, 1.9647596572486523,
        2.5721516221263188, 3.6021024479679786, 5.797883715482887);

    assertArrayEquals(expectedList.toArray(), actualList.toArray());
  }
}
