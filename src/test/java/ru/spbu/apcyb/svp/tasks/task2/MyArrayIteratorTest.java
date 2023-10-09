package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the MyArrayIterator class.
 *
 * @author Evgeny
 */

class MyArrayIteratorTest {

  private MyArrayIterator<String> myArrayIterator;
  private MyArrayIterator<String> myArrayIteratorExpected;


  @BeforeEach
  void setUp() {
    myArrayIteratorExpected = new MyArrayIterator<>(new String[0], 0);
    String[] values = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    myArrayIterator = new MyArrayIterator<>(values, values.length);
  }

  @Test
  void hasNextFirstElement() {
    assertTrue(myArrayIterator.hasNext());
  }

  @Test
  void hasNextEmptyValues() {
    assertFalse(myArrayIteratorExpected.hasNext());
  }

  @Test
  void nextOne() {
    assertEquals("one", myArrayIterator.next());
  }

  @Test
  void nextEmptyValuesThrown() {
    assertThrows(NoSuchElementException.class, () -> myArrayIteratorExpected.next());
  }

  @Test
  void equalsDifferentIterator() {
    Iterator<String> iterator = Collections.emptyIterator();
    assertNotEquals(myArrayIteratorExpected, iterator);
  }


}
