package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the MyArrayList class.
 *
 * @author Evgeny
 */

class MyArrayListTest {

  private MyArrayList<String> myArrayList;
  private MyArrayList<String> myArrayListExpected;

  @BeforeEach
  void setUp() {

    myArrayListExpected = new MyArrayList<>();

    myArrayList = new MyArrayList<>();

    myArrayList.add("one");
    myArrayList.add("two");
    myArrayList.add("three");
    myArrayList.add("four");
    myArrayList.add("five");
    myArrayList.add("six");
    myArrayList.add("seven");
    myArrayList.add("eight");
    myArrayList.add("nine");
  }

  @Test
  void addTen() {
    myArrayListExpected = new MyArrayList<>();
    myArrayListExpected.add("one");
    myArrayListExpected.add("two");
    myArrayListExpected.add("three");
    myArrayListExpected.add("four");
    myArrayListExpected.add("five");
    myArrayListExpected.add("six");
    myArrayListExpected.add("seven");
    myArrayListExpected.add("eight");
    myArrayListExpected.add("nine");
    myArrayListExpected.add("ten");

    myArrayList.add("ten");

    assertEquals(myArrayListExpected, myArrayList);
  }

  @Test
  void addInEmptyList() {
    MyArrayList<Integer> myArrayListActual = new MyArrayList<>();
    myArrayListActual.add(1);
    assertEquals("[1]", myArrayListActual.toString());
  }

  @Test
  void removeSeven() {
    myArrayListExpected = new MyArrayList<>();
    myArrayListExpected.add("one");
    myArrayListExpected.add("two");
    myArrayListExpected.add("three");
    myArrayListExpected.add("four");
    myArrayListExpected.add("five");
    myArrayListExpected.add("six");
    myArrayListExpected.add("eight");
    myArrayListExpected.add("nine");

    myArrayList.remove(6);

    assertEquals(myArrayListExpected, myArrayList);
  }

  @Test
  void removeEmptyThrownIndexOutOfBoundsException() {
    assertThrows(IndexOutOfBoundsException.class, () -> myArrayListExpected.remove(6));
  }

  @Test
  void containsSix() {
    assertTrue(myArrayList.contains("six"));
  }

  @Test
  void containsFalseEleven() {
    assertFalse(myArrayList.contains("eleven"));
  }

  @Test
  void isEmptyMyArrayListFalse() {
    assertFalse(myArrayList.isEmpty());
  }

  @Test
  void isEmptyNewMyArrayList() {
    assertTrue(new MyArrayList<>().isEmpty());
  }

  @Test
  void getMyArrayListSeven() {
    assertEquals("seven", myArrayList.get(6));
  }

  @Test
  void getWrongIndexThrownIndexOutOfBoundsException() {
    assertThrows(IndexOutOfBoundsException.class, () -> myArrayList.get(25));
  }

  @Test
  void addElevenInSevenPosition() {
    myArrayList.add(7, "eleven");
    assertEquals("eleven", myArrayList.get(7));
  }

  @Test
  void addWrongPositionThrownIndexOutOfBoundsException() {
    assertThrows(IndexOutOfBoundsException.class, () -> myArrayList.add(-2, "eleven"));
  }

  @Test
  void myArrayListWithCapacityGetRightSize() {
    assertEquals(0, new MyArrayList<>(20).size());
  }

  @Test
  void setMyArrayListSeven() {
    myArrayList.set(7, "eleven");
    assertEquals("eleven", myArrayList.get(7));
  }

  @Test
  void setWrongPositionThrownIndexOutOfBoundsException() {
    assertThrows(IndexOutOfBoundsException.class, () -> myArrayList.set(25, "eleven"));
  }

  @Test
  void getIterator() {
    String[] values = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    MyArrayIterator<String> iterator = new MyArrayIterator<>(values, values.length);
    assertEquals(iterator, myArrayList.iterator());
  }

  @Test
  void removeThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.remove("string"));
  }

  @Test
  void indexOfFive() {
    assertEquals(4, myArrayList.indexOf("five"));
  }

  @Test
  void lastIndexOfThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.lastIndexOf("string"));
  }

  @Test
  void listIteratorWithoutParametersThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.listIterator());
  }

  @Test
  void listIteratorWithIndexThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.listIterator(4));
  }

  @Test
  void subListThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.subList(4, 8));
  }

  @Test
  void toArrayWithoutParametersThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.toArray());
  }

  @Test
  void toArrayWithArrayThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.toArray(new String[0]));
  }

  @Test
  void containsAllThrownUnsupportedOperationException() {
    List<String> list = myArrayList;
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.containsAll(list));
  }

  @Test
  void addAllWithCollectionParameterThrownUnsupportedOperationException() {
    List<String> list = myArrayList;
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.addAll(list));
  }

  @Test
  void addAllWithIndexAndCollectionParametersThrownUnsupportedOperationException() {
    List<String> list = myArrayList;
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.addAll(4, list));
  }

  @Test
  void removeAllThrownUnsupportedOperationException() {
    List<String> list = myArrayList;
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.removeAll(list));
  }

  @Test
  void retainAllThrownUnsupportedOperationException() {
    List<String> list = myArrayList;
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.retainAll(list));
  }

  @Test
  void clearThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myArrayList.clear());
  }


}
