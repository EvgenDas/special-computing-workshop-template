package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the MyStack class.
 *
 * @author Evgeny
 */


class MyStackTest {

  private MyStack<Integer> myStack;
  private MyStack<Integer> myStackExpected;

  @BeforeEach
  void setUp() {
    myStackExpected = new MyStack<>();

    myStack = new MyStack<>();

    myStack.push(1);
    myStack.push(2);
    myStack.push(3);
    myStack.push(4);
    myStack.push(5);
    myStack.push(6);
    myStack.push(7);
    myStack.push(8);
    myStack.push(9);
  }

  @Test
  void pushTen() {
    myStackExpected.push(1);
    myStackExpected.push(2);
    myStackExpected.push(3);
    myStackExpected.push(4);
    myStackExpected.push(5);
    myStackExpected.push(6);
    myStackExpected.push(7);
    myStackExpected.push(8);
    myStackExpected.push(9);
    myStackExpected.push(10);

    myStack.push(10);

    assertEquals(myStackExpected, myStack);
  }

  @Test
  void popNine() {
    assertEquals(9, myStack.pop());
  }

  @Test
  void popThrownEmptyStackException() {
    assertThrows(EmptyStackException.class, () -> myStackExpected.pop());
  }

  @Test
  void peekEight() {
    myStack.pop();
    assertEquals(8, myStack.peek());
  }

  @Test
  void peekThrownEmptyStackException() {
    assertThrows(EmptyStackException.class, () -> myStackExpected.peek());
  }

  @Test
  void emptyMyStack() {
    assertFalse(myStack.empty());
  }

  @Test
  void emptyMyNewStack() {
    MyStack<String> stack = new MyStack<>();
    assertTrue(stack.empty());
  }

  @Test
  void searchThrownUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> myStack.search(4));
  }


}
