package ru.spbu.apcyb.svp.tasks.task4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Class that implements methods for reading from a file. Calculating tangent in multithreaded mode
 * and single-threaded mode. And writing to a file.
 *
 * @author Evgeny
 */
public class Task4 {

  private List<Double> listOfInputData;
  private List<Double> listOfOutputDataMultithreading;

  /**
   * The starting point for the start of the program.
   *
   * @param args command line values
   * @throws IOException          file reading error
   * @throws ExecutionException   error starting threads
   * @throws InterruptedException interrupting the thread
   */
  public static void main(String... args)
      throws IOException, ExecutionException, InterruptedException {

    Task4 task4 = new Task4();
    task4.calculateTangentsFileToFile(new File(args[0]), new File(args[1]));
  }

  /**
   * A method that reads data from an input file
   * calculates the tangent of numbers and writes it to
   * an output file.
   *
   * @param inputFile  input file containing double elements
   * @param outputFile output file
   * @throws IOException          file reading error
   * @throws ExecutionException   error starting threads
   * @throws InterruptedException interrupting the thread
   */
  public void calculateTangentsFileToFile(File inputFile, File outputFile)
      throws IOException, ExecutionException, InterruptedException {

    readData(inputFile);
    calculateTangentByForkJoinPool(10);
    writeData(outputFile);
  }

  private void readData(File file) throws IOException {
    listOfInputData = new ArrayList<>();
    try (DataInputStream dataInputStream =
        new DataInputStream(new FileInputStream(file))) {
      while (dataInputStream.available() > 0) {
        listOfInputData.add(dataInputStream.readDouble());
      }
    } catch (IOException exception) {
      throw new IOException("Произошла ошибка при чтении данных из файла");
    }
  }

  private void writeData(File file) throws IOException {
    try (DataOutputStream dataOutputStream =
        new DataOutputStream(new FileOutputStream(file))) {
      for (double arrayOfOutputDatum : listOfOutputDataMultithreading) {
        dataOutputStream.writeDouble(arrayOfOutputDatum);
      }
      dataOutputStream.writeInt(listOfOutputDataMultithreading.size());
    } catch (IOException exception) {
      throw new IOException("Произошла ошибка при записи данных в файл");
    }
  }

  /**
   * Method for calculating tangents in multithreaded mode.
   *
   * @param numberOfThread number of threads
   * @throws ExecutionException   error starting threads
   * @throws InterruptedException interrupting the thread
   */
  public void calculateTangentByForkJoinPool(int numberOfThread)
      throws ExecutionException, InterruptedException {
    ForkJoinTask<List<Double>> listOfCalculatedTangent;
    try (ForkJoinPool myPool = new ForkJoinPool(numberOfThread)) {
      if (listOfInputData != null) {
        listOfCalculatedTangent = myPool.submit(() ->
            listOfInputData.parallelStream()
                .map(Math::tan)
                .toList());
      } else {
        throw new NullPointerException("Ошибка обработки данных: listOfInputData is null");
      }
    }
    listOfOutputDataMultithreading = listOfCalculatedTangent.get();
  }

  /**
   * A method for calculating tangents in single-threaded mode.
   *
   * @return list of calculated tangents
   */

  public List<Double> calculateTangentSingleThread() {
    List<Double> listOfOutputDataSingleThread;
    if (listOfInputData != null) {
      listOfOutputDataSingleThread = listOfInputData.stream()
          .map(Math::tan)
          .toList();
    } else {
      throw new NullPointerException("Ошибка обработки данных: listOfInputData is null");
    }
    return listOfOutputDataSingleThread;
  }

  public void setListOfInputData(List<Double> listOfInputData) {
    this.listOfInputData = listOfInputData;
  }

  public List<Double> getListOfOutputDataMultithreading() {
    return listOfOutputDataMultithreading;
  }
}



