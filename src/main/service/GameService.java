package main.service;

import main.model.Deck;
import main.model.Match;
import main.model.Player;
import main.util.Generator;

public class GameService {
    private static final int JUGADORES_POR_PARTIDA = 4;

    private final RegistrationService registro;
    private final RankingService ranking;

    public GameService(RegistrationService registro, RankingService ranking) {
        this.registro = registro;
        this.ranking = ranking;
    }

    /** Organiza y juega UNA partida completa. El menú la llama las veces que haga falta. */
    public void jugarPartida() {
        if (registro.cantidadInscriptos() < JUGADORES_POR_PARTIDA) {
            System.out.println("Se necesitan al menos " + JUGADORES_POR_PARTIDA + " inscriptos.");
            return;
        }

        Player[] jugadoresElegidos = elegirJugadoresAleatorios();
        if (jugadoresElegidos == null) {
            System.out.println("Partida cancelada.");
            return;
        }

        // Mazo nuevo y barajado en cada partida
        new Match(new Deck(), jugadoresElegidos).iniciarPartida();
        mostrarResultados(jugadoresElegidos);

        for (Player jugador : jugadoresElegidos) {
            jugador.registrarPuntajePartida();
            ranking.actualizar(jugador);
        }
    }

    private Player[] elegirJugadoresAleatorios() {
        Player[] todos = registro.obtenerInscriptos(); 
        Player[] elegidos = new Player[JUGADORES_POR_PARTIDA];

        for (int i = 0; i < JUGADORES_POR_PARTIDA; i++) {
            int j = Generator.randomInt(i, todos.length - 1);
            Player aux = todos[i];
            todos[i] = todos[j];
            todos[j] = aux;
            elegidos[i] = todos[i];
        }
        return elegidos;
    }

    private void mostrarResultados(Player[] jugadores) {
        int mayor = jugadores[0].getPuntaje();
        for (Player jugador : jugadores) {
            if (jugador.getPuntaje() > mayor) mayor = jugador.getPuntaje();
        }

        System.out.println("\n=== RESULTADOS FINALES ===");
        for (Player jugador : jugadores) {
            System.out.println(jugador.getNombre() + " " + jugador.getApellido()
                    + ": " + jugador.getPuntaje() + " puntos");
        }

        System.out.println("Ganador(es) con " + mayor + " puntos:");
        for (Player jugador : jugadores) {
            if (jugador.getPuntaje() == mayor) {
                System.out.println(" - " + jugador.getNombre() + " " + jugador.getApellido());
            }
        }
    }
}