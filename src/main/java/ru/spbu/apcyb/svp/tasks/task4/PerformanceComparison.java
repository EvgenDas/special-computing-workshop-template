package ru.spbu.apcyb.svp.tasks.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerformanceComparison {

  public static final Logger logger = Logger.getLogger(PerformanceComparison.class.getName());

  public static void main(String[] args) {
    PerformanceComparison performanceComparison = new PerformanceComparison();
    performanceComparison.performanceComparisonMultithreadedAndSingleThreaded(1);
    performanceComparison.performanceComparisonMultithreadedAndSingleThreaded(100);
    performanceComparison.performanceComparisonMultithreadedAndSingleThreaded(1000000);
    performanceComparison.performanceComparisonMultithreadedAndSingleThreaded(100000000);
  }

  public void performanceComparisonMultithreadedAndSingleThreaded(int sizeOfInputList) {
    logger.log(Level.INFO, "size of input list: {0}", sizeOfInputList);

    long multiThreadedResult = performanceOfMultithreadedMethod(sizeOfInputList);
    logger.log(Level.INFO, "multithreaded result: {0} millisecond", multiThreadedResult);

    long singleThreadedResult = performanceOfSingleThreadedMethod(sizeOfInputList);
    logger.log(Level.INFO, "singleThreaded result: {0} millisecond", singleThreadedResult);
  }

  private long performanceOfSingleThreadedMethod(int sizeOfInputList) {
    List<Double> list = getDoubleListOfGivenSize(sizeOfInputList);
    Task4 task4 = new Task4();
    task4.setListOfInputData(list);
    long start;
    long end;
    start = System.currentTimeMillis();
    task4.tangentCalculatorSingleThread();
    end = System.currentTimeMillis();
    return end - start;
  }

  private long performanceOfMultithreadedMethod(int sizeOfInputList) {
    List<Double> list = getDoubleListOfGivenSize(sizeOfInputList);
    Task4 task4 = new Task4();
    task4.setListOfInputData(list);
    long start;
    long end;
    try {
      start = System.currentTimeMillis();
      task4.tangentCalculatorByForkJoinPool(10);
      end = System.currentTimeMillis();
    } catch (ExecutionException e) {
      throw new IllegalArgumentException("Ошибка в запуске ForkJoinPool - ExecutionException");
    } catch (InterruptedException e) {
      throw new IllegalArgumentException("Ошибка в запуске ForkJoinPool - InterruptedException");
    }
    return end - start;
  }

  private List<Double> getDoubleListOfGivenSize(int sizeOfInputList) {
    if (sizeOfInputList < 1) {
      throw new IllegalArgumentException("Неверно задан размер списка: "
          + sizeOfInputList + " должен быть >0");
    }
    List<Double> list = new ArrayList<>();
    for (int i = 0; i < sizeOfInputList; i++) {
      list.add(Math.random());
    }
    return list;
  }
}
