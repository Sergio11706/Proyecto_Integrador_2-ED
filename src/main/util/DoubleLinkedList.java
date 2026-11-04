//
// Created by Julio Tentor <jtentor@fi.unju.edu.ar>
//
package main.util;

import java.util.Iterator;

public class DoubleLinkedList<ELEMENT> implements ILinkedList<ELEMENT> {

    //region Node Class

    protected class Node<E> {
        protected ELEMENT item;
        protected Node<ELEMENT> next;
        protected Node<ELEMENT> prev;

        protected Node() {
            this(null, null, null);
        }
        protected Node(ELEMENT item) {
            this(item, null, null);
        }
        protected Node(ELEMENT item, Node<ELEMENT> next) {
            this(item, next, null);
        }
        protected Node(ELEMENT item, Node<ELEMENT> next, Node<ELEMENT> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }
    }
    //endregion

    //region Attributes

    private Node<ELEMENT> head;
    private int count;
    private Node<ELEMENT> tail;
    //endregion

    //region Constructors

    public DoubleLinkedList() {
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
        Node<ELEMENT> temp = new Node<ELEMENT>(item, this.head, null);
        if (this.size() <= 0) {
            this.tail = temp;
        }
        else {
            this.head.prev = temp;
        }
        this.head = temp;
        ++this.count;
    }

    // Appends the specified element to the end of this list.
    public void addLast(ELEMENT item) {
        Node<ELEMENT> temp = new Node<ELEMENT>(item, null, this.tail);
        if (this.size() <= 0) {
            this.head = temp;
        }
        else {
            this.tail.next = temp;
        }
        this.tail = temp;
        ++this.count;
    }

    // Removes and returns the first element from this list.
    public ELEMENT removeFirst() {
        if (this.size() <= 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        ELEMENT item = this.head.item;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        else {
            this.head.prev = null;
        }
        --this.count;
        return item;
    }

    // Removes and returns the last element from this list.
    public ELEMENT removeLast() {
        if (this.size() <= 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        ELEMENT item = this.tail.item;
        if (this.head.next == null) {
            this.head = this.tail = null;
        } else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        --this.count;
        return item;
    }

    // Devuelve el primer elemento sin quitarlo
    public ELEMENT getFirst() {
        if (this.size() <= 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        return this.head.item;
    }

    // Devuelve el último elemento sin quitarlo
    public ELEMENT getLast() {
        if (this.size() <= 0) {
            throw new RuntimeException("La lista está vacía...");
        }
        return this.tail.item;
    }
    //endregion

    //region Positional Methods

    // Devuelve el nodo de la posición indicada, recorriendo desde el extremo más cercano (head o tail)
    private Node<ELEMENT> nodeAt(int position) {
        Node<ELEMENT> current;
        if (position < this.count / 2) {
            current = this.head;
            for (int i = 0; i < position; i++) {
                current = current.next;
            }
        } else {
            current = this.tail;
            for (int i = this.count - 1; i > position; i--) {
                current = current.prev;
            }
        }
        return current;
    }

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
            // after es el nodo que hoy ocupa la posición; el nuevo nodo queda justo antes que él
            Node<ELEMENT> after = this.nodeAt(position);
            Node<ELEMENT> temp = new Node<ELEMENT>(item, after, after.prev);
            after.prev.next = temp;
            after.prev = temp;
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
        if (position == this.count - 1) {
            return this.removeLast();
        }
        // nodo del medio: se actualizan solo 2 punteros (prev.next y next.prev)
        Node<ELEMENT> removed = this.nodeAt(position);
        removed.prev.next = removed.next;
        removed.next.prev = removed.prev;
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
        return this.nodeAt(position).item;
    }
    //endregion

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
        return new DoubleLinkedListIterator(this.head);
    }

    public class DoubleLinkedListIterator implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public DoubleLinkedListIterator(Node<ELEMENT> current) {
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

    // Recorre la lista desde el último hasta el primero (ventaja de la lista doble)
    public Iterator<ELEMENT> iteratorBack() {
        return new DoubleLinkedListIteratorBack(this.tail);
    }

    public class DoubleLinkedListIteratorBack implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public DoubleLinkedListIteratorBack(Node<ELEMENT> current) {
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
            this.current = this.current.prev;
            return item;
        }

    }
    //endregion

}