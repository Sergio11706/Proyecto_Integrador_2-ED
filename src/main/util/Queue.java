package main.util;

/**
 * Interfaz que representa una estructura de datos de tipo Cola (Queue) basada en el principio FIFO
 * (First In, First Out).
 *
 * <p>Los elementos se insertan por el final (al final de la cola) y se extraen o consultan por el
 * frente.
 *
 * @param <E> El tipo de elementos que almacena esta cola.
 * @author Lucas Montaño
 * @version 1.0
 */
public interface Queue<E> {

  /**
   * Inserta el elemento especificado en la cola si es posible hacerlo inmediatamente sin violar las
   * restricciones de capacidad.
   *
   * @param element El elemento a añadir a la cola.
   * @return {@code true} si el elemento fue añadido con éxito.
   * @throws IllegalStateException Si el elemento no se puede añadir en este momento debido a
   *     restricciones de capacidad.
   * @throws NullPointerException Si el elemento especificado es {@code null} y la cola no permite
   *     elementos nulos.
   */
  public boolean add(E element);

  /**
   * Inserta el elemento especificado en la cola si es posible hacerlo inmediatamente sin violar las
   * restricciones de capacidad.
   *
   * <p>Es preferible usar este método antes que {@link #add(Object)} cuando se trabaja con colas de
   * capacidad restringida.
   *
   * @param element El elemento a añadir a la cola.
   * @return {@code true} si el elemento fue añadido a la cola, o {@code false} si no hay espacio
   *     disponible.
   * @throws NullPointerException Si el elemento especificado es {@code null} y la cola no permite
   *     elementos nulos.
   */
  public boolean offer(E element);

  /**
   * Recupera y elimina el frente de esta cola.
   *
   * @return El elemento que estaba al frente de la cola, o {@code null} si la cola está vacía.
   */
  public E poll();

  /**
   * Recupera y elimina el frente de esta cola.
   *
   * <p>Este método se diferencia de {@link #poll()} únicamente en que lanza una excepción si la
   * cola está vacía.
   *
   * @return El elemento que estaba al frente de la cola.
   * @throws java.util.NoSuchElementException Si la cola está vacía.
   */
  public E remove();

  /**
   * Recupera, pero no elimina, el frente de esta cola.
   *
   * @return El elemento que está al frente de la cola, o {@code null} si la cola está vacía.
   */
  public E peek();

  /**
   * Recupera, pero no elimina, el frente de esta cola.
   *
   * <p>Este método se diferencia de {@link #peek()} únicamente en que lanza una excepción si la
   * cola está vacía.
   *
   * @return El elemento que está al frente de la cola.
   * @throws java.util.NoSuchElementException Si la cola está vacía.
   */
  public E element();

  /**
   * Comprueba si la cola no contiene elementos.
   *
   * @return {@code true} si la cola está vacía; {@code false} en caso contrario.
   */
  public boolean isEmpty();

  /**
   * Devuelve el número de elementos contenidos en esta cola.
   *
   * @return La cantidad de elementos en la cola.
   */
  public int size();

  /**
   * Devuelve un arreglo que contiene todos los elementos de esta cola en la secuencia correcta
   * (desde el frente hasta el final).
   *
   * @return Un arreglo con los elementos de la cola.
   */
  public Object[] toArray();
}
