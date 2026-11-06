package main.model;

import main.util.LinkedQueue;
import main.util.Queue;

import java.util.Arrays;

public class Match {
    private static final int JUGADORES = 4;
    private static final int RONDAS = 3;
    private static final int CARTAS_POR_RONDA = 3;

    private final Deck mazo;
    private final Queue<Player> turnos = new LinkedQueue<>();

    public Match(Deck mazo, Player[] jugadores) {
        if (jugadores.length != JUGADORES) {
            throw new IllegalArgumentException("La partida requiere exactamente cuatro jugadores.");
        }
        this.mazo = mazo;
        for (Player jugador : jugadores) {
            jugador.reiniciarPuntaje();
            turnos.add(jugador);
        }
    }

    /** Juega las tres rondas y deja el puntaje final de cada jugador calculado. */
    public void iniciarPartida() {
        for (int ronda = 1; ronda <= RONDAS; ronda++) {
            System.out.println("\n--- RONDA " + ronda + " ---");
            jugarRonda();
            turnos.add(turnos.remove()); // rota quién empieza la próxima ronda
        }

        for (int i = 0; i < JUGADORES; i++) {
            Player jugador = turnos.remove();
            jugador.calcularPuntajePartida();
            turnos.add(jugador);
        }
    }

    private void jugarRonda() {
        Card[][] cartasRonda = new Card[JUGADORES][CARTAS_POR_RONDA];
        int puntajeMaximo = 0;
        Player ganadorRonda = null;
        boolean empate = false;

        // Cada jugador sale de la cola, recibe sus cartas y vuelve al final
        for (int i = 0; i < JUGADORES; i++) {
            Player jugadorActual = turnos.remove();
            for (int c = 0; c < CARTAS_POR_RONDA; c++) {
                cartasRonda[i][c] = mazo.repartir();
            }
            int puntajeActual = puntajeMano(cartasRonda[i]);

            if (puntajeActual > puntajeMaximo) {
                empate = false;
                puntajeMaximo = puntajeActual;
                ganadorRonda = jugadorActual;
            }

            if(puntajeActual == puntajeMaximo) {
                empate = true;
            }

            System.out.println(jugadorActual.getNombre() + " saca " + Arrays.toString(cartasRonda[i])
                    + " -> " + puntajeActual + " puntos");

            turnos.add(jugadorActual);
        }

        if (empate) {
            System.out.println("Empate en la ronda: cada jugador conserva sus cartas.");

            for(int i=0; i<JUGADORES; i++){
                Player jugadorActual = turnos.remove();

                for(Card carta: cartasRonda[i]){
                    jugadorActual.agregarCarta(carta);
                }
            }
        } else {
            System.out.println("Gana la ronda " + ganadorRonda.getNombre()
                    + " con " + puntajeMaximo + " puntos.");
        }
    }

    /**
     * Suma las tres cartas; si hay un as, duplica el total una sola vez.
     * El comodín (100) no necesita trato especial: ya supera cualquier mano sin comodín.
     */
    private int puntajeMano(Card[] mano) {
        int total = 0;
        boolean tieneAs = false;
        for (Card carta : mano) {
            total += carta.getValor();
            if (carta.getValor() == 1) tieneAs = true;
        }
        return tieneAs ? total * 2 : total;
    }
}