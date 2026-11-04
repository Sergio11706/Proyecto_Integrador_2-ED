package main.util;

import java.util.EmptyStackException;

/**
 * Estructura de datos genérica que representa una Pila (Stack) con comportamiento LIFO (Last In,
 * First Out), implementada con una lista enlazada simple ({@link SimpleLinkedList}).
 *
 * <p>La cima de la pila es el primer nodo de la lista: {@code push} usa {@code addFirst} y {@code
 * pop} usa {@code removeFirst}, ambas en tiempo constante <b>O(1)</b>. Si la cima estuviera al
 * final de la lista, {@code pop} (que usa {@code removeLast}) debería recorrer todos los nodos.
 *
 * <p>No tiene capacidad máxima y no admite elementos {@code null}.
 *
 * @param <E> El tipo de elementos que almacena la pila.
 * @author Acuña Sergio
 * @version 1.0
 */
public class LinkedStack<E> {

  /** Lista enlazada simple que almacena los elementos; la cima es su primer nodo. */
  private final SimpleLinkedList<E> list;

  /** Crea una pila vacía. */
  public LinkedStack() {
    this.list = new SimpleLinkedList<E>();
  }

  /**
   * Inserta un elemento en la cima de la pila.
   *
   * @param item El elemento a insertar en la pila.
   * @return El elemento que fue insertado.
   * @throws NullPointerException Si el elemento es {@code null}.
   */
  public E push(E item) {
    if (item == null) throw new NullPointerException("La pila no admite elementos nulos");

    list.addFirst(item);
    return item;
  }

  /**
   * Remueve y devuelve el elemento ubicado en la cima de la pila.
   *
   * @return El elemento removido de la cima.
   * @throws EmptyStackException Si la pila se encuentra vacía.
   */
  public E pop() {
    if (isEmpty()) throw new EmptyStackException();
    return list.removeFirst();
  }

  /**
   * Devuelve el elemento ubicado en la cima de la pila sin removerlo.
   *
   * @return El elemento actual de la cima.
   * @throws EmptyStackException Si la pila se encuentra vacía.
   */
  public E peek() {
    if (isEmpty()) throw new EmptyStackException();
    return list.getFirst();
  }

  /**
   * Busca un objeto en la pila y devuelve la distancia relativa desde la cima.
   *
   * <p>La cima de la pila tiene una distancia de {@code 1}. Si el objeto aparece múltiples veces,
   * devuelve la distancia del elemento más cercano a la cima.
   *
   * @param o El objeto que se desea buscar.
   * @return La distancia desde la cima (base 1), o {@code -1} si el objeto no se encuentra.
   * @throws EmptyStackException Si la pila se encuentra vacía.
   */
  public int search(Object o) {
    if (isEmpty()) throw new EmptyStackException();

    int distance = 1;
    for (E item : list) {
      if (item.equals(o)) return distance;
      distance++;
    }
    return -1;
  }

  /**
   * Devuelve la cantidad de elementos almacenados actualmente en la pila.
   *
   * @return El número de elementos en la pila.
   */
  public int size() {
    return list.size();
  }

  /**
   * Comprueba si la pila no contiene elementos.
   *
   * @return {@code true} si la pila está vacía; {@code false} en caso contrario.
   */
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * Devuelve un arreglo con todos los elementos contenidos en la pila, ordenados desde la base
   * hasta la cima.
   *
   * @return Un arreglo de tipo {@code Object[]} con los elementos de la pila.
   */
  public Object[] toArray() {
    Object[] copy = new Object[list.size()];
    int i = copy.length - 1; // la lista va de cima a base, el arreglo de base a cima

    for (E item : list) {
      copy[i--] = item;
    }
    return copy;
  }

  /**
   * Devuelve una representación en formato de texto con los elementos de la pila, ordenados desde
   * la cima hasta la base y encerrados entre corchetes.
   *
   * @return Una representación en {@link String} del contenido de la pila.
   */
  @Override
  public String toString() {
    return list.toString();
  }
}