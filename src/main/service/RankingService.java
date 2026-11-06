package main.service;
import main.util.BinarySearchTree;
import main.util.SimpleLinkedList;
import main.model.Player;
import main.model.RankingEntry;

public class RankingService {

    private final BinarySearchTree<RankingEntry> arbolRanking;

    public RankingService() {
        this.arbolRanking = new BinarySearchTree<>();
    }

    public void actualizar(Player jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }

        RankingEntry anterior = buscarPorDni(jugador.getDni());

        if (anterior != null) {
            if (jugador.getPuntaje() <= anterior.getPuntaje()) {
                return;
            }

            arbolRanking.remove(anterior);
        }

        arbolRanking.add(new RankingEntry(jugador, jugador.getPuntaje()));
    }

    private RankingEntry buscarPorDni(String dni) {
        SimpleLinkedList<RankingEntry> elementos = arbolRanking.obtenerElementosEnOrden();

        for (RankingEntry entry : elementos) {
            if (entry.getJugador().getDni().equals(dni)) {
                return entry;
            }
        }

        return null;
    }
    
    public SimpleLinkedList<RankingEntry> obtenerRanking() {
        return arbolRanking.obtenerElementosEnOrden();
    }

}
