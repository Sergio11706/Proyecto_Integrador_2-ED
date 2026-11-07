package main.service;

import main.model.Jugador;
import main.util.BinarySearchTree;
import main.util.SimpleLinkedList;

public class RankingService {

    private BinarySearchTree<Jugador> arbolRanking;

    // Inicializa el árbol que almacena el ranking.
    public RankingService() {
        this.arbolRanking = new BinarySearchTree<>();
    }

    // Incorpora al jugador o actualiza su mejor puntaje en el ranking.
    public void actualizar(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }

        if (estaEnRanking(jugador)) {
            if (jugador.getPuntaje() <= jugador.getPuntajeMaximo()) {
                return;
            }
            quitarPuntajeAnterior(jugador);
        }

        jugador.registrarPuntajePartida();
        arbolRanking.add(jugador);
    }

    // Elimina del ranking el registro previo del jugador.
    private void quitarPuntajeAnterior(Jugador jugador) {
        arbolRanking.remove(jugador);
    }

    // Comprueba si el jugador ya aparece en el ranking.
    private boolean estaEnRanking(Jugador jugador) {
        for (Jugador p : arbolRanking.obtenerElementosEnOrden()) {
            if (p.getDni().equals(jugador.getDni())) {
                return true;
            }
        }
        return false;
    }

    // Devuelve los jugadores ordenados según el ranking.
    public SimpleLinkedList<Jugador> obtenerRanking() {
        return arbolRanking.obtenerElementosEnOrden();
    }
}