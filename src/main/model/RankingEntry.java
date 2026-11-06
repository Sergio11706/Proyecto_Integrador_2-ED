package main.model;

public class RankingEntry implements Comparable<RankingEntry> {
    private Player jugador;
    private int puntaje;

    public RankingEntry(Player jugador, int puntaje) {
        this.jugador = jugador;
        this.puntaje = puntaje;
    }

    public Player getJugador() {
        return jugador;
    }

    public void setJugador(Player jugador) {
        this.jugador = jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public int compareTo(RankingEntry otro) {
        int resultado = Integer.compare(otro.puntaje, this.puntaje);

        if (resultado != 0) {
            return resultado;
        }

        return this.jugador.getDni().compareTo(otro.jugador.getDni());
    }
    
}
