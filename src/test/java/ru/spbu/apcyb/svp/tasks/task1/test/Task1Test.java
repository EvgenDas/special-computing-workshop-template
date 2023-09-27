package ru.spbu.apcyb.svp.tasks.task1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.spbu.apcyb.svp.tasks.task1.SumExchange;
import ru.spbu.apcyb.svp.tasks.task1.Task1;


/**
 * Тесты для задания 1.
 */
class Task1Test {

  private Task1 task1;

  @BeforeEach
  void setUp() {
    task1 = new Task1();
  }

  @Test
  void inputDataTest_10_1_2_3() {
    String expected = "10 1 2 3";
    System.setIn(new ByteArrayInputStream("10 1 2 3".getBytes()));
    assertEquals(expected, task1.inputData());
  }

  @Test
  void transformInputLineToLongList_10_1_2_3() {
    System.setIn(new ByteArrayInputStream("10 1 2 3".getBytes()));
    List<Long> listExpected = Arrays.asList(10L, 1L, 2L, 3L);
    assertEquals(listExpected, task1.transformInputLineToLongList());
  }

  @Test
  void transformInputLineToLongListThrownNullPointerExceptionEmptyInput() {
    System.setIn(new ByteArrayInputStream("".getBytes()));
    assertThrows(NullPointerException.class, task1::transformInputLineToLongList);
  }

  @Test
  void transformInputLineToLongListThrownIllegalArgumentExceptionZeroSum() {
    System.setIn(new ByteArrayInputStream("0".getBytes()));
    assertThrows(IllegalArgumentException.class, task1::transformInputLineToLongList);
  }

  @Test
  void transformInputLineToLongListThrownIllegalArgumentExceptionEmptySum() {
    System.setIn(new ByteArrayInputStream(" 2 1".getBytes()));
    assertThrows(IllegalArgumentException.class, task1::transformInputLineToLongList);
  }

  @Test
  void transformInputLineToLongListThrownNumberFormatExceptionEmptySum() {
    System.setIn(new ByteArrayInputStream("5  1".getBytes()));
    assertThrows(NumberFormatException.class, task1::transformInputLineToLongList);
  }

  @Test
  void transformInputLineToLongListThrownNumberFormatExceptionLetterInput() {
    System.setIn(new ByteArrayInputStream("asd 2 1".getBytes()));
    assertThrows(NumberFormatException.class, task1::transformInputLineToLongList);
  }

  @Test
  void splitAndSortData_10_1_2_3() {
    System.setIn(new ByteArrayInputStream("10 1 2 3".getBytes()));
    SumExchange sumExchangeExpected = new SumExchange(10, Arrays.asList(1L, 2L, 3L));
    SumExchange sumExchangeActual = task1.splitAndSortData();
    assertEquals(sumExchangeExpected, sumExchangeActual);
  }

  @Test
  void splitAndSortDataThrownArithmeticException_1_0_1_2_3() {
    System.setIn(new ByteArrayInputStream("1 0 1 2 3".getBytes()));
    assertThrows(ArithmeticException.class, task1::splitAndSortData);
  }

  @Test
  void splitAndSortDataNormalThrownArithmeticException_1_1_2_3() {
    System.setIn(new ByteArrayInputStream("-1 1 2 3".getBytes()));
    assertThrows(ArithmeticException.class, task1::splitAndSortData);
  }

}
