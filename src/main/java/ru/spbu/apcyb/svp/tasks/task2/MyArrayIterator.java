package ru.spbu.apcyb.svp.tasks.task2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Custom iterator class for a class MyArrayList.
 *
 * @param <E> generic
 * @author Evgeny
 */

public class MyArrayIterator<E> implements Iterator<E> {

  private int index = 0;
  E[] values;
  private final int size;

  public MyArrayIterator(E[] values, int size) {
    this.values = values;
    this.size = size;
  }

  /**
   * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true}
   * if {@link #next} would return an element rather than throwing an exception.)
   *
   * @return {@code true} if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    return index < size;
  }

  /**
   * Returns the next element in the iteration.
   *
   * @return the next element in the iteration
   * @throws NoSuchElementException if the iteration has no more elements
   */
  @Override
  public E next() {
    if (!hasNext()) {
      throw new NoSuchElementException("Пустой список");
    }
    return values[index++];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyArrayIterator<?> that = (MyArrayIterator<?>) o;
    if (size == that.size) {
      for (int i = 0; i < size; i++) {
        if (!values[i].equals(that.values[i])) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(size);
    result = 31 * result;
    return result;
  }
}
