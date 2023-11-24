package ru.spbu.apcyb.svp.tasks.task4;

import java.io.File;
import java.io.IOException;
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
  public static void main(String... args) throws IOException {
    File fileContainingOneDouble = new File(args[0]);
    File fileContainingOneHundredDouble = new File(args[1]);
    File fileContainingOneMillionDouble = new File(args[2]);

    PerformanceComparison performanceComparison = new PerformanceComparison();
    performanceComparison.comparePerformanceOnTheseInputs(fileContainingOneDouble,
        fileContainingOneHundredDouble, fileContainingOneMillionDouble);
  }

  /**
   * Method of launching benchmarks.
   *
   * @param files Input files
   */
  public void comparePerformanceOnTheseInputs(File... files) throws IOException {
    comparePerformanceMultithreadedAndSingleThreaded(files[0]);
    comparePerformanceMultithreadedAndSingleThreaded(files[1]);
    comparePerformanceMultithreadedAndSingleThreaded(files[2]);
  }

  /**
   * Method for displaying comparative tangent calculation records in multithreaded and
   * single-threaded modes.
   *
   * @param inputFile Input File containing Double
   */

  public void comparePerformanceMultithreadedAndSingleThreaded(File inputFile)
      throws IOException {
    logger.log(Level.INFO, "name of input file: {0}", inputFile.getName());

    long multiThreadedResult = calculatePerformanceOfMultithreadedMethod(inputFile);
    logger.log(Level.INFO, "multithreaded result: {0} millisecond", multiThreadedResult);

    long singleThreadedResult = calculatePerformanceSingleThreadedMethod(inputFile);
    logger.log(Level.INFO, "singleThreaded result: {0} millisecond", singleThreadedResult);
  }

  /**
   * Calculating the running time of a method that calculates the tangent value in single-threaded
   * mode.
   *
   * @param inputFile Input File containing Double
   * @return method operation time in milliseconds
   */
  public long calculatePerformanceSingleThreadedMethod(File inputFile) throws IOException {
    Task4 task4 = new Task4(inputFile);
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
   * @param inputFile Input File containing Double
   * @return method operation time in milliseconds
   */

  public long calculatePerformanceOfMultithreadedMethod(File inputFile) throws IOException {
    Task4 task4 = new Task4(inputFile);
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
}
