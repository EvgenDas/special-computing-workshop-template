package ru.spbu.apcyb.svp.tasks.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * This is the main class. It has methods for initial processing of input data.
 *
 * @author Evgeny
 */

public class Task1 {

  public static final Logger logger = Logger.getLogger(Task1.class.getName());


  /**
   * Here start point of the program.
   *
   * @param args command line values
   */
  public static void main(String[] args) {
    Task1 task1 = new Task1();
    SumExchange sumExchange = task1.splitAndSortData();
    sumExchange.getAllCombinations();
  }

  /**
   * All data is entered in one line.
   *
   * @return the entered data in the form of a string
   */

  public String inputData() {
    String lineInput = "";
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

      lineInput = bufferedReader.readLine();

    } catch (IOException e) {
      logger.warning(e.toString());
    }
    return lineInput;
  }

  /**
   * The method receives a string as input and converts it to a list Integer.
   *
   * @return list Integer
   */

  public List<Long> transformInputLineToLongList() {
    String lineInput = inputData();

    if (lineInput == null) {
      throw new NullPointerException("Ошибка ввода");
    }
    if (lineInput.charAt(0) == ' ') {
      throw new IllegalArgumentException("Пропущена сумма размена");
    }
    String[] arrayOfStringNumber = lineInput.trim().split(" ");

    if (arrayOfStringNumber.length < 2) {
      throw new IllegalArgumentException("Введено недостаточно цифр");
    }
    if (!checkFormatInputNumbers(arrayOfStringNumber)) {
      throw new NumberFormatException("Неправильный ввод цифр");
    }
    return Arrays.stream(arrayOfStringNumber)
        .map(Long::parseLong)
        .toList();
  }

  private boolean checkFormatInputNumbers(String[] denominations) {
    for (String string : denominations) {
      for (char ch : string.toCharArray()) {
        if (!Character.isDigit(ch) && ch != '-') {
          return false;
        }
      }
    }
    return true;
  }


  /**
   * The method divides the data into an amount and a set of nominal coins.
   * <br>
   * And sorts the nominal coins.
   *
   * @return an object containing the amount and denomination
   * @see SumExchange
   */

  public SumExchange splitAndSortData() {
    List<Long> arrayOfNumber = transformInputLineToLongList();
    List<Long> denominations = new ArrayList<>(arrayOfNumber);

    long sum = arrayOfNumber.get(0);
    denominations.remove(0);

    if (sum <= 0) {
      throw new ArithmeticException("Сумма меньше нуля, либо равна нулю");
    }
    if (!checkingSignOfNumbers(denominations)) {
      throw new ArithmeticException("Номинал меньше либо равен нулю");
    }

    Collections.sort(denominations);
    denominations = denominations.stream().distinct().toList();
    return new SumExchange(sum, denominations);
  }

  private boolean checkingSignOfNumbers(List<Long> denominations) {
    for (Long el : denominations) {
      if (el <= 0) {
        return false;
      }
    }
    return true;
  }
}

