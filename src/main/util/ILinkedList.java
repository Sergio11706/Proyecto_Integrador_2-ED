//
// Created by Julio Tentor <jtentor@fi.unju.edu.ar>
//
package main.util;

// from https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/LinkedList.html
public interface ILinkedList<ELEMENT> extends Iterable<ELEMENT> {

    // Returns the number of elements in this list.
    public int size();

    // Devuelve true si la lista no tiene elementos
    public default boolean isEmpty() {
        return this.size() == 0;
    }

    // Inserts the specified element at the beginning of this list.
    public void addFirst(ELEMENT item);
    // Appends the specified element to the end of this list.
    public void addLast(ELEMENT item);
    // Inserta el elemento en la posición indicada (0 a size())
    public void addPos(int position, ELEMENT item);

    // Removes and returns the first element from this list.
    public ELEMENT removeFirst();
    // Removes and returns the last element from this list.
    public ELEMENT removeLast();
    // Elimina y devuelve el elemento de la posición indicada (0 a size()-1)
    public ELEMENT removePos(int position);

    // Devuelve el primer elemento sin quitarlo (falla si la lista está vacía)
    public ELEMENT getFirst();
    // Devuelve el último elemento sin quitarlo (falla si la lista está vacía)
    public ELEMENT getLast();
    // Devuelve el elemento de la posición indicada (0 a size()-1) sin quitarlo
    public ELEMENT getPos(int position);

}