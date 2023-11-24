package ru.spbu.apcyb.svp.tasks.task4;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerformanceComparisonTest {

  private PerformanceComparison performanceComparison;

  @BeforeEach
  void setUp() {
    performanceComparison = new PerformanceComparison();
  }

  @Test
  void comparePerformanceMultithreadedAndSingleThreadedThrownIOException() {
    File inputFile = new File("src/test/resources/InputDoubleZero");
    assertThrows(IOException.class,
        () -> performanceComparison
            .comparePerformanceMultithreadedAndSingleThreaded(inputFile));
  }

  @Test
  void mainDoesNotThrowAnyException() {
    assertDoesNotThrow(() -> PerformanceComparison.main("src/test/resources/InputDoubleOne.txt",
        "src/test/resources/InputDoubleOne.txt", "src/test/resources/InputDoubleOne.txt"));
  }
}
