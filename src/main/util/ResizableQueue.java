package main.util;

import java.util.NoSuchElementException;

/**
 * Implementación de una cola ({@link Queue}) dinámicamente redimensionable basada en un arreglo
 * circular.
 *
 * <p>A diferencia de las versiones de capacidad fija, esta clase ajusta su tamaño de forma
 * automática cuando se alcanza el límite del arreglo interno, duplicando su capacidad para
 * garantizar que la inserción no falle por restricciones de espacio.
 *
 * <p>Las operaciones de inserción, extracción y consulta ofrecen un rendimiento amortizado de
 * <b>O(1)</b>.
 *
 * @param <E> El tipo de elementos almacenados en esta cola.
 * @author Lucas Montaño
 * @version 1.0
 */
public class ResizableQueue<E> implements Queue<E> {

  /** Arreglo interno para almacenar los elementos de la cola. */
  private Object[] queue;

  /** Índice del frente de la cola (primer elemento disponible para extraer). */
  private int head;

  /** Índice de la siguiente posición disponible para insertar un elemento. */
  private int tail;

  /** Número actual de elementos almacenados en la cola. */
  private int count;

  /**
   * Crea una instancia de {@code ResizableQueue} con una capacidad inicial por defecto de 10
   * elementos.
   */
  public ResizableQueue() {
    this(10);
  }

  /**
   * Crea una instancia de {@code ResizableQueue} con la capacidad inicial especificada.
   *
   * @param initialCapacity La capacidad inicial del arreglo interno.
   * @throws IllegalArgumentException Si {@code initialCapacity} es menor o igual a cero.
   */
  public ResizableQueue(int initialCapacity) {
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
   * <p><b>Nota de implementación:</b> Si la cola está llena, redimensiona automáticamente el
   * arreglo interno al doble de su capacidad actual.
   *
   * @return Siempre {@code true}, ya que la cola se redimensiona cuando alcanza su límite.
   */
  @Override
  public boolean add(E element) {
    if (isFull()) resize(queue.length * 2);

    queue[tail] = element;
    tail = next(tail);
    count++;
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * <p>En esta implementación, invoca internamente a {@link #add(Object)} y siempre retorna {@code
   * true}.
   */
  @Override
  public boolean offer(E element) {
    return add(element);
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
  public E remove() {
    if (isEmpty()) throw new NoSuchElementException("La cola está vacía.");
    return poll();
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
    if (isEmpty()) throw new NoSuchElementException("La cola está vacía.");
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
   * hasta el final, separados por comas y encerrados entre corchetes.
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
   * Comprueba si el arreglo interno ha alcanzado su capacidad máxima actual antes de
   * redimensionarse.
   *
   * @return {@code true} si la cantidad de elementos es igual a la longitud del arreglo.
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

  /**
   * Redimensiona el arreglo interno a una nueva capacidad reordenando los elementos de forma
   * contigua a partir del índice inicial ({@code head = 0}).
   *
   * @param newCapacity La nueva capacidad máxima del arreglo.
   */
  private void resize(int newCapacity) {
    Object[] newQueue = new Object[newCapacity];
    int copyHead = this.head;

    for (int i = 0; i < count; i++) {
      newQueue[i] = queue[copyHead];
      copyHead = next(copyHead);
    }

    this.queue = newQueue;
    this.head = 0;
    this.tail = count;
  }
}
