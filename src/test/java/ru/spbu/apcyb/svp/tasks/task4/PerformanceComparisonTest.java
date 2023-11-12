package ru.spbu.apcyb.svp.tasks.task4;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerformanceComparisonTest {

  private PerformanceComparison performanceComparison;

  @BeforeEach
  void setUp() {
    performanceComparison = new PerformanceComparison();
  }

  @Test
  void comparePerformanceMultithreadedAndSingleThreadedThrownIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class,
        () -> performanceComparison.comparePerformanceMultithreadedAndSingleThreaded(0));
  }

  @Test
  void mainDoesNotThrowAnyException() {
    assertDoesNotThrow(() -> PerformanceComparison.main());
  }

  @Test
  void timeMultithreadedLessThanSingleThreadedOnTenElementTest() {
    long timeOfOfMultithreadedMethod = performanceComparison.calculatePerformanceOfMultithreadedMethod(
        10000000);
    long timeOfSingleThreadedMethod = performanceComparison.calculatePerformanceSingleThreadedMethod(
        10000000);
    assertTrue(timeOfOfMultithreadedMethod < timeOfSingleThreadedMethod);
  }
}
