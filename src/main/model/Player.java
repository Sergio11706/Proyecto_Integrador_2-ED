package main.model;

public class Player extends Student {
    private int numeroJugador;
    private int puntaje;
    public Player(String dni, String nombre, String apellido, int edad, int numeroJugador) {
        super(dni, nombre, apellido, edad); 
        this.numeroJugador = numeroJugador;
        this.puntaje = 0;
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

    @Override
    public String toString() {
        return "Player{" +
                "estudiante=" + super.toString() + 
                ", numeroJugador=" + numeroJugador +
                ", puntaje=" + puntaje +
                '}';
    }
}