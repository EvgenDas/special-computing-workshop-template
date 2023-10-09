package ru.spbu.apcyb.svp.tasks.task2;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Own Stack class.
 *
 * @param <E> generic
 * @author Evgeny
 */

public class MyStack<E> extends Stack<E> {

  private final transient List<E> list;

  /**
   * Creates an empty Stack.
   */
  public MyStack() {
    list = new MyArrayList<>();
  }

  /**
   * Pushes an item onto the top of this stack. This has exactly the same effect as:
   * <blockquote><pre>
   * addElement(item)</pre></blockquote>
   *
   * @param item the item to be pushed onto this stack.
   * @return the {@code item} argument.
   * @see MyArrayList#add
   */
  @Override
  public E push(E item) {
    list.add(item);
    return item;
  }

  /**
   * Removes the object at the top of this stack and returns that object as the value of this
   * function.
   *
   * @return The object at the top of this stack (the last item of the {@code Vector} object).
   * @throws EmptyStackException if this stack is empty.
   */

  @Override
  public synchronized E pop() {
    try {
      return list.remove(list.size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new EmptyStackException();
    }
  }

  /**
   * Looks at the object at the top of this stack without removing it from the stack.
   *
   * @return the object at the top of this stack (the last item of the {@code Vector} object).
   * @throws EmptyStackException if this stack is empty.
   */

  @Override
  public synchronized E peek() {
    try {
      return list.get(list.size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new EmptyStackException();
    }
  }

  /**
   * Tests if this stack is empty.
   *
   * @return {@code true} if and only if this stack contains no items; {@code false} otherwise.
   */
  @Override
  public boolean empty() {
    return list.isEmpty();
  }

  /**
   * Compares the specified Object with this Vector for equality.  Returns true if and only if the
   * specified Object is also a List, both Lists have the same size, and all corresponding pairs of
   * elements in the two Lists are <em>equal</em>.  (Two elements {@code e1} and {@code e2} are
   * <em>equal</em> if {@code Objects.equals(e1, e2)}.) In other words, two Lists are defined to be
   * equal if they contain the same elements in the same order.
   *
   * @param o the Object to be compared for equality with this Vector
   * @return true if the specified Object is equal to this Vector
   */
  @Override
  public synchronized boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    MyStack<?> myStack = (MyStack<?>) o;
    return Objects.equals(list, myStack.list);
  }

  @Override
  public synchronized int hashCode() {
    return Objects.hash(super.hashCode(), list);
  }

  /**
   * Returns the 1-based position where an object is on this stack. If the object {@code o} occurs
   * as an item in this stack, this method returns the distance from the top of the stack of the
   * occurrence nearest the top of the stack; the topmost item on the stack is considered to be at
   * distance {@code 1}. The {@code equals} method is used to compare {@code o} to the items in this
   * stack.
   *
   * @param o the desired object.
   * @return the 1-based position from the top of the stack where the object is located;
   */

  @Override
  public synchronized int search(Object o) {
    throw new UnsupportedOperationException("Метод search не переопределён");
  }
}
