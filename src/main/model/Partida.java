package main.model;

import main.util.CountedQueue;
import main.util.Queue;

public class Partida {
    public static int JUGADORES = 4;
    public static int RONDAS = 3;
    public static int CARTAS_POR_RONDA = 3;

    private Mazo mazo;
    private Queue<Jugador> turnos = new CountedQueue<>();
    private Carta[][] cartasRonda = new Carta[JUGADORES][CARTAS_POR_RONDA];
    private int rondaActual = 1;
    private int puntajeMaximoRonda = 0;

    // Inicializa la partida y prepara los turnos de los jugadores.
    public Partida(Mazo mazo, Jugador[] jugadores) {
        if (jugadores.length != JUGADORES) {
            throw new IllegalArgumentException("La partida requiere exactamente cuatro jugadores.");
        }
        this.mazo = mazo;
        for (Jugador jugador : jugadores) {
            jugador.reiniciarPuntaje();
            turnos.add(jugador);
        }
    }

    // Devuelve el mazo utilizado en la partida.
    public Mazo getMazo() { 
        return mazo; 
    }

    // Devuelve la cola de turnos de los jugadores.
    public Queue<Jugador> getTurnos() { 
        return turnos; 
    }
    
    // Devuelve las cartas repartidas durante la ronda actual.
    public Carta[][] getCartasRonda() { 
        return cartasRonda; 
    }
    
    // Devuelve el número de la ronda actual.
    public int getRondaActual() { 
        return rondaActual; 
    }
    
    // Devuelve el puntaje más alto de la ronda actual.
    public int getPuntajeMaximo() { 
        return puntajeMaximoRonda; 
    }
    
    // Actualiza el puntaje más alto de la ronda actual.
    public void setPuntajeMaximo(int puntajeMaximo) { 
        this.puntajeMaximoRonda = puntajeMaximo; 
    }

    // Avanza a la siguiente ronda y reinicia el puntaje máximo.
    public void siguienteRonda() {
        rondaActual++;
        puntajeMaximoRonda = 0;
    }
}