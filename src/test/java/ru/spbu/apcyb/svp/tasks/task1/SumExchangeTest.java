package ru.spbu.apcyb.svp.tasks.task1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for the SumExchange class.
 *
 * @author Evgeny
 * @see SumExchange
 */

class SumExchangeTest {

  @Test
  void allCombinationsOfExchangesTest_5_2_3() {
    long[] expectedArray = new long[]{1, 1};

    List<Long> denomination = Arrays.asList(2L, 3L);
    SumExchange actualSumExchange = new SumExchange(5, denomination);
    List<long[]> list = actualSumExchange.getAllCombinationsWithList();
    System.out.println(Arrays.toString(list.get(0)));
    assertArrayEquals(expectedArray, list.get(0));
  }

  @Test
  void allCombinationsOfExchangesTest_4_1_2() {
    List<Long> denomination = Arrays.asList(1L, 2L);
    SumExchange actualSumExchange = new SumExchange(4, denomination);
    List<long[]> list = actualSumExchange.getAllCombinationsWithList();
    StringBuilder actualString = new StringBuilder();
    list.forEach(el -> actualString.append(Arrays.toString(el)));
    assertEquals("[0, 2]" + "[2, 1]" + "[4, 0]", actualString.toString());
  }

  @Test
  void allCombinationsOfExchangesTest_1000_1() {
    List<Long> denomination = List.of(1L);
    SumExchange actualSumExchange = new SumExchange(1000, denomination);
    List<long[]> actualList = actualSumExchange.getAllCombinationsWithList();
    int actualCountOfCombinations = actualList.size();
    assertEquals(1, actualCountOfCombinations);
  }

  @Test
  void allCombinationsOfExchangesTestEmpty_8_5_6() {
    List<Long> denomination = Arrays.asList(5L, 6L);
    SumExchange actualSumExchange = new SumExchange(8, denomination);
    List<long[]> list = actualSumExchange.getAllCombinationsWithList();
    StringBuilder actualString = new StringBuilder();
    list.forEach(el -> actualString.append(Arrays.toString(el)));
    assertEquals("", actualString.toString());
  }

}
