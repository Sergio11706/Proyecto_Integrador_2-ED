package main.util;

import java.util.EmptyStackException;

/**
 * Estructura de datos genérica que representa una Pila (Stack) con comportamiento LIFO (Last In,
 * First Out).
 *
 * <p>Permite almacenar elementos de cualquier tipo de dato de manera dinámica, redimensionando
 * internamente su capacidad a medida que se agregan más elementos.
 *
 * @param <E> El tipo de elementos que almacena la pila.
 * @author Lucas Montaño
 * @version 1.0
 */
public class Stack<E> {

  private Object[] stack;
  private int count;

  /** Crea una pila vacía con una capacidad inicial por defecto de 5 elementos. */
  public Stack() {
    this(5);
  }

  /**
   * Crea una pila vacía con la capacidad inicial especificada.
   *
   * @param initialCapacity Capacidad inicial del arreglo interno.
   * @throws IllegalArgumentException Si {@code initialCapacity} es menor o igual a cero.
   */
  public Stack(int initialCapacity) {
    if (initialCapacity <= 0)
      throw new IllegalArgumentException("La capacidad inicial debe ser mayor a 0");

    this.stack = new Object[initialCapacity];
    this.count = 0;
  }

  /**
   * Inserta un elemento en la cima de la pila.
   *
   * <p>Si la capacidad máxima actual del arreglo interno se alcanza, la pila incrementa
   * automáticamente su capacidad para alojar el nuevo elemento.
   *
   * @param item El elemento a insertar en la pila.
   * @return El elemento que fue insertado.
   */
  public E push(E item) {
    if (count == stack.length) resize();
    stack[count] = item;
    count++;
    return item;
  }

  /**
   * Remueve y devuelve el elemento ubicado en la cima de la pila.
   *
   * @return El elemento removido de la cima.
   * @throws EmptyStackException Si la pila se encuentra vacía.
   */
  @SuppressWarnings("unchecked")
  public E pop() {
    if (isEmpty()) throw new EmptyStackException();
    count--;
    E item = (E) stack[count];
    stack[count] = null;
    return item;
  }

  /**
   * Devuelve el elemento ubicado en la cima de la pila sin removerlo.
   *
   * @return El elemento actual de la cima.
   * @throws EmptyStackException Si la pila se encuentra vacía.
   */
  @SuppressWarnings("unchecked")
  public E peek() {
    if (isEmpty()) throw new EmptyStackException();
    return (E) stack[count - 1];
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

    int index = lastIndexOf(o);

    if (index >= 0) return count - index;
    return -1;
  }

  /**
   * Busca un objeto desde la cima hacia la base y devuelve el índice interno (basado en 0).
   *
   * @param o El objeto a buscar.
   * @return El índice base 0 de la última aparición del objeto, o {@code -1} si no existe.
   */
  private int lastIndexOf(Object o) {
    return lastIndexOf(o, count - 1);
  }

  /**
   * Recorre el arreglo interno de manera descendente a partir del índice especificado buscando el
   * objeto.
   *
   * @param o El objeto a buscar.
   * @param index El índice inicial desde el cual comenzar la búsqueda descendente.
   * @return El índice del elemento encontrado, o {@code -1} si no está en el rango indicado.
   * @throws IndexOutOfBoundsException Si el índice es mayor o igual a la cantidad de elementos.
   */
  private int lastIndexOf(Object o, int index) {
    if (index >= count) throw new IndexOutOfBoundsException();

    if (o == null) {
      for (int i = index; i >= 0; i--) if (stack[i] == null) return i;
    } else {
      for (int i = index; i >= 0; i--) if (o.equals(stack[i])) return i;
    }

    return -1;
  }

  /**
   * Devuelve la cantidad de elementos almacenados actualmente en la pila.
   *
   * @return El número de elementos en la pila.
   */
  public int size() {
    return count;
  }

  /**
   * Comprueba si la pila no contiene elementos.
   *
   * @return {@code true} si la pila está vacía; {@code false} en caso contrario.
   */
  public boolean isEmpty() {
    return count == 0;
  }

  /**
   * Incrementa la capacidad del arreglo interno creando uno nuevo con espacio adicional y copiando
   * los elementos existentes.
   */
  private void resize() {
    Object[] newStack = new Object[count + 1];

    for (int i = 0; i < stack.length; i++) newStack[i] = stack[i];
    stack = newStack;
  }

  /**
   * Devuelve un arreglo con todos los elementos contenidos en la pila, ordenados desde la base
   * hasta la cima.
   *
   * @return Un arreglo de tipo {@code Object[]} con los elementos de la pila.
   */
  public Object[] toArray() {
    Object[] copia = new Object[count];

    for (int i = 0; i < count; i++) {
      copia[i] = stack[i];
    }

    return copia;
  }

  /**
   * Devuelve una representación en formato de texto con los elementos de la pila, ordenados desde
   * la cima hasta la base y encerrados entre corchetes.
   *
   * @return Una representación en {@link String} del contenido de la pila.
   */
  @Override
  public String toString() {
    if (isEmpty()) return "[]";

    StringBuilder sb = new StringBuilder("[");
    for (int i = count - 1; i >= 0; i--) {
      sb.append(stack[i]);
      if (i > 0) sb.append(", ");
    }
    sb.append("]");
    return sb.toString();
  }
}
