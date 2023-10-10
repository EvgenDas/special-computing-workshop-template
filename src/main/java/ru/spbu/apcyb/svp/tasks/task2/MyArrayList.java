package ru.spbu.apcyb.svp.tasks.task2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Own ArrayList class.
 *
 * @param <E> generic
 * @author Evgeny
 */

public class MyArrayList<E> implements List<E>, Iterable<E> {

  private static final int DEFAULT_CAPACITY = 8;
  private int size = 0;
  private int capacity;
  private E[] values;

  public static final Logger logger = Logger.getLogger(MyArrayList.class.getName());

  /**
   * Creating a class object MyArrayList.
   */

  public MyArrayList() {
    capacity = DEFAULT_CAPACITY;
    values = (E[]) new Object[capacity];
  }

  /**
   * Creating a class object MyArrayList.
   *
   * @param capacity initial capacity
   */

  public MyArrayList(int capacity) {
    this.capacity = capacity;
    values = (E[]) new Object[capacity];
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return list size
   */

  @Override
  public int size() {
    return size;
  }

  /**
   * Returns {@code true} if this list contains no elements.
   *
   * @return {@code true} if this list contains no elements
   */

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns {@code true} if this list contains the specified element. More formally, returns
   * {@code true} if and only if this list contains at least one element {@code e} such that
   * {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this list is to be tested
   * @return {@code true} if this list contains the specified element
   */

  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  /**
   * Returns an iterator of this type.
   *
   * @return Generic iterator
   */
  @Override
  public Iterator<E> iterator() {
    return new MyArrayIterator<>(values, size);
  }

  /**
   * Increases the capacity of the internal array by 2 times.
   */

  private void grow() {
    capacity *= 2;
    E[] newArray = (E[]) new Object[capacity];
    System.arraycopy(values, 0, newArray, 0, size);
    values = newArray;
  }

  /**
   * Appends the specified element to the end of this list.
   *
   * @param e element to be appended to this list
   * @return {@code true} (as specified by {@link Collection#add})
   * @throws ClassCastException if the class of the specified element prevents it from being added
   *                            to this list
   */
  @Override
  public boolean add(E e) {
    try {
      if (size >= capacity) {
        grow();
      }
      values[size++] = e;
      return true;
    } catch (ClassCastException exception) {
      logger.info("Ошибка приведения типа при добавлении элемента в список");
    }
    return false;
  }

  /**
   * Inserts the specified element at the specified position in this list. Shifts the element
   * currently at that position (if any) and any subsequent elements to the right (adds one to their
   * indices).
   *
   * @param index   index at which the specified element is to be inserted
   * @param element element to be inserted
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   ({@code index < 0 || index > size()})
   */

  @Override
  public void add(int index, E element) {
    Objects.checkIndex(index, size);

    if (size + 1 >= capacity) {
      grow();
    }

    for (int i = size; i >= index; i--) {
      values[i + 1] = values[i];
    }
    size++;
    values[index] = element;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   ({@code index < 0 || index >= size()})
   */

  @Override
  public E get(int index) {
    Objects.checkIndex(index, size);
    return values[index];
  }

  /**
   * Replaces the element at the specified position in this list with the specified element.
   *
   * @param index   index of the element to replace
   * @param element element to be stored at the specified position
   * @return the element previously at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   ({@code index < 0 || index >= size()})
   */

  @Override
  public E set(int index, E element) {
    Objects.checkIndex(index, size);
    E o = values[index];
    values[index] = element;
    return o;
  }

  /**
   * Removes the element at the specified position in this list (optional operation).  Shifts any
   * subsequent elements to the left (subtracts one from their indices).  Returns the element that
   * was removed from the list.
   *
   * @param index the index of the element to be removed
   * @return the element previously at the specified position
   * @throws UnsupportedOperationException if the {@code remove} operation is not supported by this
   *                                       list
   * @throws IndexOutOfBoundsException     if the index is out of range
   *                                       ({@code index < 0 || index >= size()})
   */

  @Override
  public E remove(int index) {
    Objects.checkIndex(index, size);
    E[] temp = values;
    values = (E[]) new Object[temp.length];
    System.arraycopy(temp, 0, values, 0, index);
    System.arraycopy(temp, index + 1, values, index, temp.length - index - 1);
    size--;

    return temp[index];
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException("Метод remove(Object o) не переопределён");
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index {@code i} such that
   * {@code Objects.equals(o, get(i))}, or -1 if there is no such index.
   *
   * @param o element to search for
   * @return the index of the first occurrence of the specified element in this list. or -1 if this
   * list does not contain the element
   */
  @Override
  public int indexOf(Object o) {
    if (o == null) {
      for (int i = 0; i < size; i++) {
        if (values[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (o.equals(values[i])) {
          return i;
        }
      }
    }
    return -1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyArrayList<?> that = (MyArrayList<?>) o;
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
    return iterator().hashCode();
  }

  @Override
  public String toString() {
    E[] array = (E[]) new Object[size];
    System.arraycopy(values, 0, array, 0, size);
    return Arrays.toString(array);
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException("Метод lastIndexOf не переопределён");
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException("Метод listIterator не переопределён");
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    throw new UnsupportedOperationException("Метод listIterator не переопределён");
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException("Метод subList не переопределён");
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException("Метод toArray не переопределён");
  }


  @Override
  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException("Метод toArray не переопределён");
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException("Метод containsAll не переопределён");
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException("Метод addAll не переопределён");

  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException("Метод addAll не переопределён");

  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException("Метод removeAll не переопределён");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException("Метод retainAll не переопределён");
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Метод clear не переопределён");

  }
}
