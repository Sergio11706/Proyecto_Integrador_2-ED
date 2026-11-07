package main.model;

import main.util.*;

public class Jugador extends Estudiante implements Comparable<Jugador> {
    private int numeroJugador;
    private int puntaje;
    private int puntajeMaximo;
    private ILinkedList<Carta> cartasGanadas;

    // Crea un jugador con sus datos personales y número asignado.
    public Jugador(String dni, String nombre, String apellido, int edad, int numeroJugador) {
        super(dni, nombre, apellido, edad);
        this.numeroJugador = numeroJugador;
        this.cartasGanadas = new SimpleLinkedList<>();
    }

    // Devuelve el número asignado al jugador.
    public int getNumeroJugador() {
        return numeroJugador;
    }

    // Devuelve el puntaje obtenido en la partida actual.
    public int getPuntaje() {
        return puntaje;
    }

    // Devuelve el mejor puntaje registrado para el jugador.
    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    // Reinicia el puntaje y las cartas acumuladas.
    public void reiniciarPuntaje() {
        puntaje = 0;
        cartasGanadas = new SimpleLinkedList<>();
    }

    // Agrega una carta a las cartas ganadas por el jugador.
    public void agregarCarta(Carta carta) {
        cartasGanadas.addLast(carta);
    }

    // Calcula el puntaje total a partir de las cartas ganadas.
    public void calcularPuntajePartida() {
        puntaje = 0;
        for(Carta carta: cartasGanadas){
            puntaje += carta.getValor();
        }
    }

    // Actualiza el mejor puntaje si el actual es superior.
    public void registrarPuntajePartida() {
        if (puntaje > puntajeMaximo) {
            puntajeMaximo = puntaje;
        }
    }

    // Ordena los jugadores por puntaje máximo y luego por DNI.
    @Override
    public int compareTo(Jugador otro) {
        int resultado = Integer.compare(
                otro.getPuntajeMaximo(),
                this.getPuntajeMaximo()
        );

        if (resultado != 0) {
            return resultado;
        }

        return this.getDni().compareTo(otro.getDni());
    }

    // Devuelve una descripción textual del jugador y su estado.
    @Override
    public String toString() {
        return "Jugador{" +
                "estudiante=" + super.toString() +
                ", numeroJugador=" + numeroJugador +
                ", puntaje=" + puntaje +
                ", puntajeMaximo=" + puntajeMaximo +
                ", cartasGanadas=" + cartasGanadas.size() + " cartas" +
                '}';
    }
}