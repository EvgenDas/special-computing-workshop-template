package ru.spbu.apcyb.svp.tasks.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Comparison of tangent calculation time in multithreaded and single-threaded mode on different
 * amounts of data.
 */

public class PerformanceComparison {

  public static final Logger logger = Logger.getLogger(PerformanceComparison.class.getName());

  /**
   * The starting point for the start of the program.
   *
   * @param args command line values
   */
  public static void main(String... args) {

    PerformanceComparison performanceComparison = new PerformanceComparison();

    performanceComparison.comparePerformanceMultithreadedAndSingleThreaded(1);
    performanceComparison.comparePerformanceMultithreadedAndSingleThreaded(100);
    performanceComparison.comparePerformanceMultithreadedAndSingleThreaded(1000000);
    performanceComparison.comparePerformanceMultithreadedAndSingleThreaded(10000000);
  }

  /**
   * Method for displaying comparative tangent calculation records in multithreaded and
   * single-threaded modes.
   *
   * @param sizeOfInputList size of the input list
   */

  public void comparePerformanceMultithreadedAndSingleThreaded(int sizeOfInputList) {
    logger.log(Level.INFO, "size of input list: {0}", sizeOfInputList);

    long multiThreadedResult = calculatePerformanceOfMultithreadedMethod(sizeOfInputList);
    logger.log(Level.INFO, "multithreaded result: {0} millisecond", multiThreadedResult);

    long singleThreadedResult = calculatePerformanceSingleThreadedMethod(sizeOfInputList);
    logger.log(Level.INFO, "singleThreaded result: {0} millisecond", singleThreadedResult);
  }

  /**
   * Calculating the running time of a method that calculates the tangent value in single-threaded
   * mode.
   *
   * @param sizeOfInputList size of the input list
   * @return method operation time in milliseconds
   */
  public long calculatePerformanceSingleThreadedMethod(int sizeOfInputList) {
    List<Double> list = getDoubleListOfGivenSize(sizeOfInputList);
    Task4 task4 = new Task4();
    task4.setListOfInputData(list);
    long start;
    long end;
    start = System.currentTimeMillis();
    task4.calculateTangentSingleThread();
    end = System.currentTimeMillis();
    return end - start;
  }

  /**
   * Calculating the running time of a method that calculates the tangent value in multithreaded
   * mode.
   *
   * @param sizeOfInputList size of the input list
   * @return method operation time in milliseconds
   */

  public long calculatePerformanceOfMultithreadedMethod(int sizeOfInputList) {
    List<Double> list = getDoubleListOfGivenSize(sizeOfInputList);
    Task4 task4 = new Task4();
    task4.setListOfInputData(list);
    long start = 0;
    long end = 0;
    try {
      start = System.currentTimeMillis();
      task4.calculateTangentByForkJoinPool(10);
      end = System.currentTimeMillis();
    } catch (ExecutionException e) {
      throw new IllegalArgumentException("Ошибка в запуске ForkJoinPool - ExecutionException");
    } catch (InterruptedException e) {
      logger.log(Level.INFO, "Ошибка в запуске ForkJoinPool - InterruptedException, {0}",
          e.getMessage());
      Thread.currentThread().interrupt();
    }
    return end - start;
  }

  private List<Double> getDoubleListOfGivenSize(int sizeOfInputList) {
    if (sizeOfInputList < 1) {
      throw new IllegalArgumentException("Неверно задан размер списка: "
          + sizeOfInputList + " должен быть > 0");
    }
    List<Double> list = new ArrayList<>();
    for (int i = 0; i < sizeOfInputList; i++) {
      list.add(Math.random());
    }
    return list;
  }
}
