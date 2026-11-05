package main.model;

import main.util.Stack; 

public class Player extends Student {
    private int numeroJugador;
    private int puntaje;
    private Stack<Card> cartasGanadas;
    public Player(String dni, String nombre, String apellido, int edad, int numeroJugador) {
        super(dni, nombre, apellido, edad);
        this.numeroJugador = numeroJugador;
        this.puntaje = 0; 
        this.cartasGanadas = new Stack<Card>(); 
    }

    public int getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Stack<Card> getCartasGanadas() {
        return cartasGanadas;
    }

    public void setCartasGanadas(Stack<Card> cartasGanadas) {
        this.cartasGanadas = cartasGanadas;
    }

    @Override
    public String toString() {
        return "Player{" +
                "estudiante=" + super.toString() +
                ", numeroJugador=" + numeroJugador +
                ", puntaje=" + puntaje +
                ", cartasGanadas=" + cartasGanadas.size() + " cartas" +
                '}';
    }
}