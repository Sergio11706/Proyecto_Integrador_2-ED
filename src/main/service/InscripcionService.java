package main.service;

import main.model.Jugador;
import main.util.Generator;
import main.util.ILinkedList;
import main.util.SimpleLinkedList;

public class InscripcionService {
  private int ID;
  private ILinkedList<Jugador> listPlayers;

  // Inicializa la lista de jugadores y el contador de identificadores.
  public InscripcionService() {
    this.ID = 0;
    this.listPlayers = new SimpleLinkedList<>();
  }

  // Registra un estudiante si su DNI aún no está inscripto.
  public Jugador registrarEstudiante(String dni, String firstName, String lastName, int age) {

    if (existeDni(dni)) {
        return null;
    }

    Jugador newPlayer = new Jugador(dni, firstName, lastName, age, ++ID);
    listPlayers.addLast(newPlayer);
    return newPlayer;
  }

  // Selecciona cuatro jugadores distintos de forma aleatoria.
  public Jugador[] seleccionarCuatroJugadores() {
    if (listPlayers.size() < 4) {
        throw new IllegalStateException("No hay suficientes jugadores");
    }

    Jugador[] seleccionados = new Jugador[4];
    boolean[] usados = new boolean[listPlayers.size()];

    for (int i = 0; i < 4; i++) {
        int indice;

        do {
            indice = Generator.randomInt(0, listPlayers.size() - 1);
        } while (usados[indice]);

        usados[indice] = true;
        seleccionados[i] = listPlayers.getPos(indice);
    }

    return seleccionados;
  }

  // Comprueba si ya existe un jugador con el DNI indicado.
  private boolean existeDni(String dni) {
    for (int i = 0; i < listPlayers.size(); i++) {
        if (listPlayers.getPos(i).getDni().equals(dni)) {
            return true;
        }
    }
    return false;
  }

  // Devuelve la cantidad de jugadores inscriptos.
  public int obtenerCantidadRegistrados() {
    return listPlayers.size();
  }

  // Devuelve la lista de jugadores inscriptos.
  public ILinkedList<Jugador> obtenerInscriptos() {
    return listPlayers;
  }
}
