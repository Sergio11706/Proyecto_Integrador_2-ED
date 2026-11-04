package main.util;

import java.util.NoSuchElementException;

/**
 * Implementación de una cola ({@link Queue}) de capacidad ilimitada basada en una lista enlazada
 * simple ({@link SimpleLinkedList}).
 *
 * <p>Los elementos se encolan al final de la lista ({@code addLast}) y se desencolan por el inicio
 * ({@code removeFirst}). Como la lista conserva referencias a su primer y último nodo, ambas
 * operaciones se ejecutan en tiempo constante <b>O(1)</b>.
 *
 * <p>A diferencia de la cola circular con arreglo, no maneja índices {@code head} y {@code tail}
 * propios ni tiene capacidad máxima: nunca se llena, por lo que {@link #add(Object)} y {@link
 * #offer(Object)} siempre tienen éxito. No admite elementos {@code null}.
 *
 * @param <E> El tipo de elementos almacenados en esta cola.
 * @author Acuña Sergio
 * @version 1.0
 */
public class LinkedQueue<E> implements Queue<E> {

  /** Lista enlazada simple que almacena los elementos; el frente de la cola es su primer nodo. */
  private final SimpleLinkedList<E> list;

  /** Crea una instancia de {@code LinkedQueue} vacía. */
  public LinkedQueue() {
    this.list = new SimpleLinkedList<E>();
  }

  /**
   * {@inheritDoc}
   *
   * @throws NullPointerException Si el elemento es {@code null}.
   */
  @Override
  public boolean add(E element) {
    if (element == null) throw new NullPointerException("La cola no admite elementos nulos");

    list.addLast(element);
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@code true} siempre, ya que la cola no tiene capacidad máxima.
   * @throws NullPointerException Si el elemento es {@code null}.
   */
  @Override
  public boolean offer(E element) {
    return add(element);
  }

  /** {@inheritDoc} */
  @Override
  public E poll() {
    if (isEmpty()) return null;
    return list.removeFirst();
  }

  /** {@inheritDoc} */
  @Override
  public E remove() {
    if (isEmpty()) throw new NoSuchElementException("La cola está vacía");
    return list.removeFirst();
  }

  /** {@inheritDoc} */
  @Override
  public E peek() {
    if (isEmpty()) return null;
    return list.getFirst();
  }

  /** {@inheritDoc} */
  @Override
  public E element() {
    if (isEmpty()) throw new NoSuchElementException("La cola está vacía");
    return list.getFirst();
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return list.size();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public Object[] toArray() {
    Object[] array = new Object[list.size()];
    int i = 0;

    for (E element : list) {
      array[i++] = element;
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
    return list.toString();
  }
}