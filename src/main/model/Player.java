package main.model;

import main.util.*;

public class Player extends Student implements Comparable<Player> {
    private int numeroJugador;
    private int puntaje;          // puntaje de la partida en curso / última jugada
    private int puntajeMaximo;    // mejor puntaje histórico (el que usa el ranking)
    private ILinkedList<Card> cartasGanadas;

    public Player(String dni, String nombre, String apellido, int edad, int numeroJugador) {
        super(dni, nombre, apellido, edad);
        this.numeroJugador = numeroJugador;
        this.cartasGanadas = new SimpleLinkedList<>();
    }

    public int getNumeroJugador() {
        return numeroJugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    /** Se llama al comenzar una partida: vacía las cartas y el puntaje de la partida. */
    public void reiniciarPuntaje() {
        puntaje = 0;
        cartasGanadas = new SimpleLinkedList<>();
    }

    public void agregarCarta(Card carta) {
        cartasGanadas.addLast(carta);
    }

    /** Puntaje de la partida = suma del valor de todas las cartas ganadas. */
    public void calcularPuntajePartida() {
        puntaje = 0;
        for(Card carta: cartasGanadas){
            puntaje += carta.getValor();
        }
    }

    /** Se llama al terminar una partida: conserva el mayor puntaje. */
    public void registrarPuntajePartida() {
        if (puntaje > puntajeMaximo) {
            puntajeMaximo = puntaje;
        }
    }

    @Override
    public int compareTo(Player otro) {
        int resultado = Integer.compare(
                otro.getPuntajeMaximo(),
                this.getPuntajeMaximo()
        );

        if (resultado != 0) {
            return resultado;
        }

        return this.getDni().compareTo(otro.getDni());
    }

    @Override
    public String toString() {
        return "Player{" +
                "estudiante=" + super.toString() +
                ", numeroJugador=" + numeroJugador +
                ", puntaje=" + puntaje +
                ", puntajeMaximo=" + puntajeMaximo +
                ", cartasGanadas=" + cartasGanadas.size() + " cartas" +
                '}';
    }
}