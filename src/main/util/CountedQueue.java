package main.util;

import java.util.NoSuchElementException;

/**
 * Implementación de una cola ({@link Queue}) de capacidad fija basada en un arreglo circular con un
 * contador explícito de elementos.
 *
 * <p>A diferencia de otras implementaciones circulares que sacrifican una casilla del arreglo para
 * diferenciar los estados de vacía y llena, esta clase utiliza una variable {@code count} dedicada,
 * lo que permite aprovechar el $100\%$ del espacio reservado en el arreglo.
 *
 * <p>Todas las operaciones de acceso, inserción y extracción se ejecutan en tiempo constante
 * <b>O(1)</b>.
 *
 * @param <E> El tipo de elementos almacenados en esta cola.
 * @author Lucas Montaño
 * @version 1.0
 */
public class CountedQueue<E> implements Queue<E> {

  /** Arreglo interno para almacenar los elementos de la cola. */
  private Object[] queue;

  /** Índice del frente de la cola (primer elemento disponible para extraer). */
  private int head;

  /** Índice de la siguiente posición disponible para insertar un elemento. */
  private int tail;

  /** Número actual de elementos almacenados en la cola. */
  private int count;

  /** Crea una instancia de {@code CountedQueue} con una capacidad por defecto de 10 elementos. */
  public CountedQueue() {
    this(10);
  }

  /**
   * Crea una instancia de {@code CountedQueue} con la capacidad inicial especificada.
   *
   * @param initialCapacity La capacidad máxima de elementos que podrá almacenar la cola.
   * @throws IllegalArgumentException Si {@code initialCapacity} es menor o igual a cero.
   */
  public CountedQueue(int initialCapacity) {
    if (initialCapacity <= 0)
      throw new IllegalArgumentException("La capacidad inicial debe ser mayor a 0");

    this.queue = new Object[initialCapacity];
    this.head = 0;
    this.tail = 0;
    this.count = 0;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalStateException Si la cola ha alcanzado su capacidad máxima.
   */
  @Override
  public boolean add(E element) {
    if (isFull()) throw new IllegalStateException("La cola está llena");

    queue[tail] = element;
    tail = next(tail);
    count++;
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@code true} si el elemento se añadió correctamente, o {@code false} si la cola está
   *     llena.
   */
  @Override
  public boolean offer(E element) {
    if (isFull()) return false;

    queue[tail] = element;
    tail = next(tail);
    count++;
    return true;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public E poll() {
    if (isEmpty()) return null;

    E element = (E) queue[head];
    queue[head] = null;
    head = next(head);
    count--;
    return element;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public E remove() {
    if (isEmpty()) throw new NoSuchElementException("La cola está vacía");

    E element = (E) queue[head];
    queue[head] = null;
    head = next(head);
    count--;
    return element;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public E peek() {
    if (isEmpty()) return null;
    return (E) queue[head];
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public E element() {
    if (isEmpty()) throw new NoSuchElementException("La cola está vacía");
    return (E) queue[head];
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return count;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
    return count == 0;
  }

  /** {@inheritDoc} */
  @Override
  public Object[] toArray() {
    if (isEmpty()) return new Object[0];

    Object[] array = new Object[count];
    int copyHead = this.head;

    for (int i = 0; i < count; i++) {
      array[i] = queue[copyHead];
      copyHead = next(copyHead);
    }
    return array;
  }

  /**
   * Devuelve una representación en formato de texto de los elementos de la cola, desde el frente
   * hasta el final, encerrados entre corchetes.
   *
   * @return Una representación en {@link String} del contenido de la cola.
   */
  @Override
  public String toString() {
    if (isEmpty()) return "[]";
    int copyHead = this.head;

    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < count; i++) {
      if (i > 0) sb.append(", ");
      sb.append(queue[copyHead]);
      copyHead = next(copyHead);
    }
    sb.append("]");
    return sb.toString();
  }

  /**
   * Comprueba si la cola ha alcanzado su capacidad máxima.
   *
   * @return {@code true} si la cola está llena; {@code false} en caso contrario.
   */
  public boolean isFull() {
    return count == queue.length;
  }

  /**
   * Avanza circularmente el índice dentro del arreglo.
   *
   * @param pos Índice actual.
   * @return El siguiente índice válido dentro del rango del arreglo.
   */
  private int next(int pos) {
    pos++;
    if (pos >= queue.length) return 0;
    return pos;
  }
}
