//
// Created by Julio Tentor <jtentor@fi.unju.edu.ar>
//
package main.util;

import java.util.Iterator;

public class SimpleLinkedList<ELEMENT> implements ILinkedList<ELEMENT> {

    //region Node Class

    private class Node<E> {
        public ELEMENT item;
        public Node<ELEMENT> next;

        public Node(ELEMENT item, Node<ELEMENT> next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }
    }
    //endregion

    //region Attributes

    protected Node<ELEMENT> head;
    protected int count;
    protected Node<ELEMENT> tail;
    //endregion

    //region Constructors

    public SimpleLinkedList() {
        this.head = null;
        this.count = 0;
        this.tail = null;
    }
    //endregion

    //region Linked List Methods

    // Returns the number of elements in this list.
    public int size() {
        return this.count;
    }

    // Inserts the specified element at the beginning of this list.
    public void addFirst(ELEMENT item) {
        Node<ELEMENT> temp = new Node<ELEMENT>(item, this.head);
        if (this.count == 0) {
            this.tail = temp;
        }
        this.head = temp;
        ++this.count;
    }

    // Appends the specified element to the end of this list.
    public void addLast(ELEMENT item) {
        Node<ELEMENT> temp = new Node<ELEMENT>(item, null);
        if (this.count == 0) {
            this.head = temp;
        } else {
            this.tail.next = temp;
        }
        this.tail = temp;
        ++this.count;
    }

    // Removes and returns the first element from this list.
    public ELEMENT removeFirst() {
        if (this.count == 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        ELEMENT item = this.head.item;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        --this.count;
        return item;
    }

    // Removes and returns the last element from this list.
    public ELEMENT removeLast() {
        if (this.count == 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        ELEMENT item = this.tail.item;
        if (this.head.next == null) {
            this.head = this.tail = null;
        } else {
            Node<ELEMENT> skip = this.head;
            while (skip.next.next != null) {
                skip = skip.next;
            }
            this.tail = skip;
            this.tail.next = null;
        }
        --this.count;
        return item;
    }

    // Devuelve el primer elemento sin quitarlo
    public ELEMENT getFirst() {
        if (this.count == 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        return this.head.item;
    }

    // Devuelve el último elemento sin quitarlo
    public ELEMENT getLast() {
        if (this.count == 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        return this.tail.item;
    }
    //endregion

    // Inserta el elemento en la posición indicada (0 a size()); size() equivale a agregar al final
    public void addPos(int position, ELEMENT item) {
        if (position < 0 || position > this.count) {
            throw new IndexOutOfBoundsException(
                    "Posición fuera de rango: " + position + " (válidas: 0 a " + this.count + ")");
        }
        if (position == 0) {
            this.addFirst(item);
        } else if (position == this.count) {
            this.addLast(item);
        } else {
            // se detiene en el nodo anterior a la posición buscada
            Node<ELEMENT> previous = this.head;
            for (int i = 0; i < position - 1; i++) {
                previous = previous.next;
            }
            // el nuevo nodo ya apunta al siguiente antes de modificar previous.next
            previous.next = new Node<ELEMENT>(item, previous.next);
            ++this.count;
        }
    }

    // Elimina y devuelve el elemento de la posición indicada (0 a size()-1)
    public ELEMENT removePos(int position) {
        if (this.count == 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        if (position < 0 || position >= this.count) {
            throw new IndexOutOfBoundsException(
                    "Posición fuera de rango: " + position + " (válidas: 0 a " + (this.count - 1) + ")");
        }
        if (position == 0) {
            return this.removeFirst();
        }
        // se detiene en el nodo anterior al que se elimina
        Node<ELEMENT> previous = this.head;
        for (int i = 0; i < position - 1; i++) {
            previous = previous.next;
        }
        Node<ELEMENT> removed = previous.next;
        previous.next = removed.next;
        if (removed == this.tail) {
            this.tail = previous;
        }
        --this.count;
        return removed.item;
    }

    // Devuelve el elemento de la posición indicada (0 a size()-1) sin quitarlo
    public ELEMENT getPos(int position) {
        if (this.count == 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        if (position < 0 || position >= this.count) {
            throw new IndexOutOfBoundsException(
                    "Posición fuera de rango: " + position + " (válidas: 0 a " + (this.count - 1) + ")");
        }
        if (position == this.count - 1) {
            return this.tail.item;
        }
        Node<ELEMENT> current = this.head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.item;
    }

    //region Object Methods

    @Override
    public String toString() {

        if (this.size() <= 0) {
            return "[]";
        }

        // from https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/StringBuilder.html
        StringBuilder sb = new StringBuilder();

        sb.append("[" + this.head.item.toString());
        for (Node<ELEMENT> skip = this.head.next; skip != null; skip = skip.next) {
            sb.append(", " + skip.item.toString());
        }
        sb.append("]");

        return sb.toString();
    }
    //endregion


    //region Iterable Methods
    @Override
    public Iterator<ELEMENT> iterator() {
        return new SimpleLinkedListIterator(this.head);
    }

    private class SimpleLinkedListIterator implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public SimpleLinkedListIterator(Node<ELEMENT> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public ELEMENT next() {
            if (!this.hasNext()) {
                throw new RuntimeException("La lista está vacía...");
            }
            ELEMENT item = this.current.item;
            this.current = this.current.next;
            return item;
        }

    }
    //endregion

}