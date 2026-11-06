package main.model;

import main.util.LinkedQueue;
import main.util.Queue;
import main.util.Stack;

public class Match {
    private static final int CANTIDAD_JUGADORES = 4;
    private static final int CANTIDAD_RONDAS = 3;
    private static final int CARTAS_POR_JUGADOR = 3;

    private final Deck mazo;
    private final Queue<Player> jugadores;
    private int rondaActual;
    private boolean iniciada;

    public Match(Deck mazo, Player[] arrayJugadores) {
        if (mazo == null) {
            throw new IllegalArgumentException("El mazo no puede ser nulo.");
        }
        if (arrayJugadores == null || arrayJugadores.length != CANTIDAD_JUGADORES) {
            throw new IllegalArgumentException("La partida requiere exactamente cuatro jugadores.");
        }

        this.mazo = mazo;
        this.jugadores = new LinkedQueue<>();
        this.rondaActual = 1;

        for (Player jugador : arrayJugadores) {
            if (jugador == null) {
                throw new IllegalArgumentException("Los jugadores no pueden ser nulos.");
            }
            this.jugadores.add(jugador);
        }
    }

    /** Ejecuta la partida completa de tres rondas. */
    public void iniciarPartida() {
        if (iniciada) {
            throw new IllegalStateException("Esta partida ya fue iniciada.");
        }
        iniciada = true;
        reiniciarPuntajes();

        System.out.println("=== INICIO DE LA PARTIDA ===");

        for (rondaActual = 1; rondaActual <= CANTIDAD_RONDAS; rondaActual++) {
            System.out.println("\n--- RONDA " + rondaActual + " ---");
            jugarRonda();
        }

        determinaGanadorFinal();
    }

    private void reiniciarPuntajes() {
        for (int i = 0; i < CANTIDAD_JUGADORES; i++) {
            Player jugador = jugadores.remove();
            jugador.setPuntaje(0);
            jugador.setCartasGanadas(new Stack<>());
            jugadores.add(jugador);
        }
    }

    private void jugarRonda() {
        Player[] jugadoresRonda = new Player[CANTIDAD_JUGADORES];
        Card[][] cartasPorJugador = new Card[CANTIDAD_JUGADORES][CARTAS_POR_JUGADOR];
        int[] puntajesRonda = new int[CANTIDAD_JUGADORES];
        boolean[] tieneComodin = new boolean[CANTIDAD_JUGADORES];

        for (int i = 0; i < CANTIDAD_JUGADORES; i++) {
            Player jugadorActual = jugadores.remove();
            jugadoresRonda[i] = jugadorActual;

            int puntajeTotal = 0;
            boolean comodinEncontrado = false;
            boolean asEncontrado = false;

            for (int c = 0; c < CARTAS_POR_JUGADOR; c++) {
                Card carta = mazo.repartir();
                if (carta == null) {
                    throw new IllegalStateException("No hay suficientes cartas para completar la ronda.");
                }
                cartasPorJugador[i][c] = carta;

                if (carta.getPalo() == Suit.COMODIN || carta.getValor() == 100) {
                    comodinEncontrado = true;
                } else {
                    puntajeTotal += carta.getValor();
                    if (carta.getValor() == 1) {
                        asEncontrado = true;
                    }
                }
            }

            tieneComodin[i] = comodinEncontrado;
            puntajesRonda[i] = comodinEncontrado
                    ? 100
                    : puntajeTotal * (asEncontrado ? 2 : 1);
            jugadores.add(jugadorActual);
        }

        determinarGanadorRonda(jugadoresRonda, cartasPorJugador, puntajesRonda, tieneComodin);
    }

    private void determinarGanadorRonda(
            Player[] jugadoresRonda,
            Card[][] cartasPorJugador,
            int[] puntajes,
            boolean[] comodines) {
        boolean hayComodinEnRonda = false;
        for (boolean comodin : comodines) {
            hayComodinEnRonda |= comodin;
        }

        int maxPuntaje = Integer.MIN_VALUE;
        int ganadorIndex = -1;
        int cantidadEmpatados = 0;

        for (int i = 0; i < jugadoresRonda.length; i++) {
            if (hayComodinEnRonda && !comodines[i]) {
                continue;
            }

            if (puntajes[i] > maxPuntaje) {
                maxPuntaje = puntajes[i];
                ganadorIndex = i;
                cantidadEmpatados = 1;
            } else if (puntajes[i] == maxPuntaje) {
                cantidadEmpatados++;
            }
        }

        if (cantidadEmpatados > 1) {
            System.out.println("¡Empate en la ronda! Cada jugador conserva sus cartas.");
            for (int i = 0; i < jugadoresRonda.length; i++) {
                guardarCartas(jugadoresRonda[i], cartasPorJugador[i]);
            }
            return;
        }

        Player ganador = jugadoresRonda[ganadorIndex];
        System.out.println("¡El ganador de la ronda es " + ganador.getNombre()
                + " con " + maxPuntaje + " puntos!");

        for (Card[] cartas : cartasPorJugador) {
            guardarCartas(ganador, cartas);
        }
    }

    private void guardarCartas(Player jugador, Card[] cartas) {
        Stack<Card> cartasGanadas = jugador.getCartasGanadas();
        for (Card carta : cartas) {
            cartasGanadas.push(carta);
        }
    }

    /** Calcula los puntajes acumulados e informa quién ganó la partida. */
    private void determinaGanadorFinal() {
        System.out.println("\n=== FIN DE LA PARTIDA - RESULTADOS FINALES ===");

        Queue<Player> colaAuxiliar = new LinkedQueue<>();
        int mayorPuntajeFinal = Integer.MIN_VALUE;
        StringBuilder ganadores = new StringBuilder();

        while (!jugadores.isEmpty()) {
            Player jugador = jugadores.remove();
            int puntajeFinal = 0;
            Object[] cartasGanadas = jugador.getCartasGanadas().toArray();
            for (Object carta : cartasGanadas) {
                puntajeFinal += ((Card) carta).getValor();
            }

            jugador.setPuntaje(puntajeFinal);
            System.out.println("Jugador: " + jugador.getNombre()
                    + " - Puntaje Final: " + puntajeFinal);

            if (puntajeFinal > mayorPuntajeFinal) {
                mayorPuntajeFinal = puntajeFinal;
                ganadores.setLength(0);
                ganadores.append(jugador.getNombre());
            } else if (puntajeFinal == mayorPuntajeFinal) {
                ganadores.append(", ").append(jugador.getNombre());
            }

            colaAuxiliar.add(jugador);
        }

        while (!colaAuxiliar.isEmpty()) {
            jugadores.add(colaAuxiliar.remove());
        }

        System.out.println("Ganador(es) de la partida: " + ganadores
                + " con " + mayorPuntajeFinal + " puntos.");
        System.out.println("Partida finalizada con éxito.");
    }
}
