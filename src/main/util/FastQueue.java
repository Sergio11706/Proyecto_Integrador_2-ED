package main.util;

import java.util.NoSuchElementException;

/**
 * Implementación de una cola ({@link Queue}) de capacidad fija basada en un arreglo circular.
 *
 * <p>Esta clase ofrece un rendimiento optimizado al realizar todas las operaciones de inserción,
 * extracción y consulta en tiempo constante <b>O(1)</b> sin la sobrecarga de redimensionamiento
 * dinámico de arreglos ni asignación de nodos en memoria.
 *
 * <p><b>Nota sobre la capacidad:</b> La cola utiliza un espacio adicional interno para diferenciar
 * los estados de "cola vacía" y "cola llena", por lo que reserva un arreglo de tamaño {@code
 * capacidad + 1}.
 *
 * @param <E> El tipo de elementos almacenados en esta cola.
 * @author Lucas Montaño
 * @version 1.0
 */
public class FastQueue<E> implements Queue<E> {

  /** Arreglo interno para almacenar los elementos de la cola. */
  private Object[] queue;

  /** Índice del frente de la cola (primer elemento disponible para extraer). */
  private int head;

  /** Índice de la siguiente posición disponible para insertar un elemento. */
  private int tail;

  /** Crea una instancia de {@code FastQueue} con una capacidad por defecto de 10 elementos. */
  public FastQueue() {
    this(10);
  }

  /**
   * Crea una instancia de {@code FastQueue} con la capacidad inicial especificada.
   *
   * @param initialCapacity La capacidad máxima de elementos que podrá almacenar la cola.
   * @throws IllegalArgumentException Si {@code initialCapacity} es menor o igual a cero.
   */
  public FastQueue(int initialCapacity) {
    if (initialCapacity <= 0)
      throw new IllegalArgumentException("La capacidad inicial debe ser mayor a 0");

    this.queue = new Object[initialCapacity + 1];
    this.head = 0;
    this.tail = 0;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalStateException Si la cola está llena.
   */
  @Override
  public boolean add(E element) {
    if (isFull()) throw new IllegalStateException("La cola está llena");

    queue[tail] = element;
    tail = next(tail);
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
  public boolean isEmpty() {
    return head == tail;
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return (tail - head + queue.length) % queue.length;
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
    int currentSize = size();
    int copyHead = this.head;

    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < currentSize; i++) {
      if (i > 0) sb.append(", ");
      sb.append(queue[copyHead]);
      copyHead = next(copyHead);
    }
    sb.append("]");
    return sb.toString();
  }

  /** {@inheritDoc} */
  @Override
  public Object[] toArray() {
    if (isEmpty()) return new Object[0];

    Object[] array = new Object[size()];
    int copyHead = this.head;
    int currentSize = size();
    for (int i = 0; i < currentSize; i++) {
      array[i] = queue[copyHead];
      copyHead = next(copyHead);
    }
    return array;
  }

  /**
   * Comprueba si la cola ha alcanzado su capacidad máxima.
   *
   * @return {@code true} si la cola está llena; {@code false} en caso contrario.
   */
  public boolean isFull() {
    return head == next(tail);
  }

  /**
   * Calcula la siguiente posición dentro del arreglo circular aplicando la operación de módulo
   * implícita.
   *
   * @param pos Índice actual.
   * @return El siguiente índice dentro del rango del arreglo.
   */
  private int next(int pos) {
    pos++;
    if (pos >= queue.length) return 0;
    return pos;
  }
}
