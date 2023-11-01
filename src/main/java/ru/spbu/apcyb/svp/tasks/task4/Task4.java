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

public class Task4 {

  private List<Double> listOfInputData;
  private List<Double> listOfOutputDataMultithreading;

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {

    Task4 task4 = new Task4();
    File inputDataFile = new File(args[0]);
    task4.readData(inputDataFile);
    task4.tangentCalculatorByForkJoinPool(10);
    File outputDataFile = new File(args[1]);
    task4.writeData(outputDataFile);
  }


  public void readData(File file) throws IOException {
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

  public void writeData(File file) throws IOException {
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

  public void tangentCalculatorByForkJoinPool(int numberOfThread)
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

  public List<Double> tangentCalculatorSingleThread() {
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
}



