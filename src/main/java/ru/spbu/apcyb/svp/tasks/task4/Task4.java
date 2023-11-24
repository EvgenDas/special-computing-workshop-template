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

  private final File inputFile;
  private File outputFile;

  public Task4(File inputFile, File outputFile) {
    this.inputFile = inputFile;
    this.outputFile = outputFile;
  }

  public Task4(File inputFile) {
    this.inputFile = inputFile;
  }

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

    Task4 task4 = new Task4(new File(args[0]), new File(args[1]));
    task4.calculateTangentsFileToFile();
  }

  /**
   * A method that reads data from an input file calculates the tangent of numbers and writes it to
   * an output file.
   *
   * @throws IOException file reading error
   */
  private List<Double> readData() throws IOException {
    List<Double> listOfInputData = new ArrayList<>();
    try (DataInputStream dataInputStream =
        new DataInputStream(new FileInputStream(inputFile))) {
      while (dataInputStream.available() > 0) {
        listOfInputData.add(dataInputStream.readDouble());
      }
      return listOfInputData;
    } catch (IOException exception) {
      throw new IOException("Произошла ошибка при чтении данных из файла");
    }
  }

  /**
   * Method of writing the calculated tangents to the output file.
   *
   * @throws IOException          file reading error
   * @throws ExecutionException   error starting threads
   * @throws InterruptedException interrupting the thread
   */
  public void calculateTangentsFileToFile()
      throws IOException, ExecutionException, InterruptedException {
    List<Double> listOfOutputDataMultithreading = calculateTangentByForkJoinPool(10);
    try (DataOutputStream dataOutputStream =
        new DataOutputStream(new FileOutputStream(outputFile))) {
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
  public List<Double> calculateTangentByForkJoinPool(int numberOfThread)
      throws ExecutionException, InterruptedException, IOException {
    ForkJoinTask<List<Double>> listOfCalculatedTangent;
    List<Double> listOfInputData = readData();
    try (ForkJoinPool myPool = new ForkJoinPool(numberOfThread)) {
      listOfCalculatedTangent = myPool.submit(() ->
          listOfInputData.parallelStream()
              .map(Math::tan)
              .toList());
      return listOfCalculatedTangent.get();
    }
  }

  /**
   * A method for calculating tangents in single-threaded mode.
   *
   * @return list of calculated tangents
   */
  public List<Double> calculateTangentSingleThread() throws IOException {
    List<Double> listOfCalculatedTangent;
    List<Double> listOfInputData = readData();
    listOfCalculatedTangent = listOfInputData.stream()
        .map(Math::tan)
        .toList();
    return listOfCalculatedTangent;
  }
}



