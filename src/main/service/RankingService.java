package main.service;

import main.model.Player;
import main.util.BinarySearchTree;
import main.util.SimpleLinkedList;

public class RankingService {

    private BinarySearchTree<Player> arbolRanking;

    public RankingService() {
        this.arbolRanking = new BinarySearchTree<>();
    }

    public void actualizar(Player jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }

        Player anterior = buscarPorDni(jugador.getDni());

        if (anterior != null
                && anterior != jugador
                && jugador.getPuntajeMaximo() <= anterior.getPuntajeMaximo()) {
            return;
        }

        BinarySearchTree<Player> nuevoArbol = new BinarySearchTree<>();

        for (Player elemento : arbolRanking.obtenerElementosEnOrden()) {
            if (!elemento.getDni().equals(jugador.getDni())) {
                nuevoArbol.add(elemento);
            }
        }

        nuevoArbol.add(jugador);
        arbolRanking = nuevoArbol;
    }

    private Player buscarPorDni(String dni) {
        for (Player p : arbolRanking.obtenerElementosEnOrden()) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }
    public SimpleLinkedList<Player> obtenerRanking() {
        return arbolRanking.obtenerElementosEnOrden();
    }
}