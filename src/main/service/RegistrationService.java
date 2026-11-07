package main.service;

import main.model.Player;
import main.util.ILinkedList;
import main.util.SimpleLinkedList;

public class RegistrationService {
  private int ID;
  private final ILinkedList<Player> listPlayers;

  public RegistrationService() {
    this.ID = 0;
    this.listPlayers = new SimpleLinkedList<>();
  }

  public Player registrarEstudiante(String dni, String firstName, String lastName, int age) {
    Player newPlayer = new Player(dni, firstName, lastName, age, ++ID);
    listPlayers.addLast(newPlayer);
    return newPlayer;
  }

  public Player[] seleccionarCuatroJugadores(int[] targetIds) {
    if (targetIds == null || targetIds.length != 4) {
      throw new IllegalArgumentException("Es necesario el ID de cuatro jugadores");
    }

    Player[] selectedPlayers = new Player[4];

    for (int i = 0; i < 4; i++) {
      Player found = buscarPorId(targetIds[i]);
      if (found == null) return null;
      selectedPlayers[i] = found;
    }
    return selectedPlayers;
  }

  public Player buscarPorId(int id) {
    for (int i = 0; i < listPlayers.size(); i++) {
      Player p = listPlayers.getPos(i);
      if (p.getNumeroJugador() == id) {
        return p;
      }
    }
    return null;
  }

  public int obtenerCantidadRegistrados() {
    return listPlayers.size();
  }

  public ILinkedList<Player> obtenerInscriptos() {
    return listPlayers;
  }
}
