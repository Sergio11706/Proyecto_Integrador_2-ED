package main.service;

import main.model.Carta;
import main.model.Mazo;
import main.model.Partida;
import main.model.Jugador;
import main.util.Queue;

public class JuegoService {
    private InscripcionService registro;
    private RankingService ranking;

    // Crea el servicio con los componentes de inscripción y ranking.
    public JuegoService(InscripcionService registro, RankingService ranking) {
        this.registro = registro;
        this.ranking = ranking;
    }

    // Indica si hay suficientes jugadores para iniciar una partida.
    public boolean hayJugadoresSuficientes() {
        return registro.obtenerCantidadRegistrados() >= Partida.JUGADORES;
    }

    // Crea una partida con cuatro jugadores inscriptos.
    public Partida crearPartida() {
        return new Partida(new Mazo(), registro.seleccionarCuatroJugadores());
    }

    // Avanza la cola de turnos y devuelve el jugador que sigue.
    public Jugador siguienteJugador(Partida partida) {
        Jugador jugador = partida.getTurnos().remove();
        partida.getTurnos().add(jugador);
        return jugador;
    }

    // Reparte cartas al jugador del turno y actualiza el máximo de la ronda.
    public Jugador jugarTurno(Partida partida, int turno) {
        Queue<Jugador> turnos = partida.getTurnos();
        Jugador jugador = turnos.remove();

        Carta[] cartas = partida.getCartasRonda()[turno];
        for (int c = 0; c < Partida.CARTAS_POR_RONDA; c++) {
            cartas[c] = partida.getMazo().repartir();
        }

        int puntaje = calcularPuntaje(cartas);
        if (puntaje > partida.getPuntajeMaximo()) {
            partida.setPuntajeMaximo(puntaje);
        }

        turnos.add(jugador);
        return jugador;
    }

    // Resuelve la ronda, asigna las cartas y avanza a la siguiente.
    public Jugador cerrarRonda(Partida partida) {
        Queue<Jugador> turnos = partida.getTurnos();
        Carta[][] cartasRonda = partida.getCartasRonda();
        int maximo = partida.getPuntajeMaximo();

        int contMaximo = 0;
        for (Carta[] cartas : cartasRonda) {
            if (calcularPuntaje(cartas) == maximo) contMaximo++;
        }
        boolean empate = contMaximo > 1;

        Jugador ganador = null;
        for (int i = 0; i < Partida.JUGADORES; i++) {
            Jugador jugador = turnos.remove();

            if (empate) {
                for (Carta carta : cartasRonda[i]) {
                    jugador.agregarCarta(carta);
                }
            } else if (calcularPuntaje(cartasRonda[i]) == maximo) {
                ganador = jugador;
                for (Carta[] cartas : cartasRonda) {
                    for (Carta carta : cartas) {
                        jugador.agregarCarta(carta);
                    }
                }
            }

            turnos.add(jugador);
        }

        partida.siguienteRonda();
        return ganador;
    }

    // Calcula los puntajes finales y actualiza el ranking.
    public int finalizarPartida(Partida partida) {
        Queue<Jugador> turnos = partida.getTurnos();
        int mayor = 0;

        for (int i = 0; i < Partida.JUGADORES; i++) {
            Jugador jugador = turnos.remove();
            jugador.calcularPuntajePartida();
            ranking.actualizar(jugador);
            if (jugador.getPuntaje() > mayor) mayor = jugador.getPuntaje();
            turnos.add(jugador);
        }
        return mayor;
    }

    // Suma los valores de las cartas y aplica la regla de puntaje especial.
    public int calcularPuntaje(Carta[] cartas) {
        int total = 0;
        boolean tieneAs = false;

        for (Carta carta : cartas) {
            total += carta.getValor();
            if (carta.getValor() == 1) {
                tieneAs = true;
            }
        }

        if (tieneAs) {
            total *= 2;
        }

        return total;
    }
}