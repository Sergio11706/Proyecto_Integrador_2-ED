package main;

import main.model.Carta;
import main.model.Partida;
import main.model.Jugador;
import main.service.JuegoService;
import main.service.RankingService;
import main.service.InscripcionService;
import main.util.Helper;
import main.util.ILinkedList;
import main.util.SimpleLinkedList;

/**
 * Clase principal para el Proyecto Integrador II - Estructura de Datos.
 *
 * @author Acuña, Sergio Facundo
 * @author Andreu, Luca Maximiliano
 * @author Silva, Jael Abril Alejandra
 * @author Cruz, Kevin Brian Joel
 * @author Montaño, Lucas Antonio
 *
 * Caso de estudio: Evento estudiantil - Juego de cartas.
 *
 * El sistema permite gestionar la inscripción y participación de estudiantes
 * en la actividad “Juego de cartas” desarrollada durante una jornada estudiantil.
 * Los participantes se registran de forma dinámica y reciben un número único
 * de jugador. Cuando hay cuatro jugadores disponibles, puede iniciarse una
 * partida compuesta por tres rondas utilizando un mazo de cartas francesas
 * que incluye tréboles, corazones, diamantes, picas y dos comodines.
 *
 * En cada ronda, cada jugador recibe tres cartas y se calcula un puntaje
 * correspondiente a la suma de sus valores. El jugador con el mayor puntaje
 * se queda con las cartas de los demás participantes. Si existe empate,
 * cada jugador conserva sus propias cartas. Los comodines tienen valor 100
 * y otorgan la victoria automática de la ronda, mientras que los ases permiten
 * duplicar el puntaje obtenido, aplicándose la bonificación una sola vez por ronda.
 *
 * Al finalizar las tres rondas se determina el ganador de la partida según
 * el puntaje acumulado con las cartas obtenidas. Además, el sistema mantiene
 * un ranking general de jugadores, conservando el mejor puntaje alcanzado por
 * cada participante a lo largo de la jornada.
 */
public class Main {

    // Ejecuta el menú principal y gestiona las opciones del programa.
    public static void main(String[] args) {
        InscripcionService registro = new InscripcionService();
        RankingService ranking = new RankingService();
        JuegoService juego = new JuegoService(registro, ranking);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Helper.readMenuOption("Opción: ", 0, 4);

            switch (opcion) {
                case 1:
                    inscribirAlumno(registro);
                    break;
                case 2:
                    jugarPartida(juego);
                    break;
                case 3:
                    mostrarRanking(ranking);
                    break;
                case 4:
                    mostrarInscriptos(registro);
                    break;
                case 0:
                    salir = Helper.readYesNo("¿Desea salir?");
                    break;
            }
        }

        System.out.println("Fin del programa.");
    }

    // Muestra las opciones disponibles en pantalla.
    private static void mostrarMenu() {
        System.out.println("\n===== JUEGO DE CARTAS =====");
        System.out.println("1. Inscribir alumno");
        System.out.println("2. Jugar partida");
        System.out.println("3. Ver ranking");
        System.out.println("4. Ver alumnos inscriptos");
        System.out.println("0. Salir");
    }

    // Solicita los datos de un alumno e intenta inscribirlo.
    private static void inscribirAlumno(InscripcionService registro) {
        System.out.println("\n--- INSCRIPCION ---");
        String dni = Helper.readDni("DNI: ");
        String nombre = Helper.readString("Nombre: ");
        String apellido = Helper.readString("Apellido: ");
        int edad = Helper.readIntBetween("Edad: ", 10, 99);

        Jugador jugador = registro.registrarEstudiante(dni, nombre, apellido, edad);
        if (jugador == null) {
            System.out.println("Ya hay un alumno inscripto con ese DNI.");
        } else {
            System.out.println("Inscripción exitosa. N° de jugador: " + jugador.getNumeroJugador());
        }
    }

    // Organiza las rondas de una partida y muestra sus resultados.
    private static void jugarPartida(JuegoService juego) {
        if (!juego.hayJugadoresSuficientes()) {
            System.out.println("\nSe necesitan al menos " + Partida.JUGADORES + " inscriptos para jugar.");
            return;
        }

        Partida partida = juego.crearPartida();

        System.out.println("\nJugadores seleccionados:");
        for (int i = 0; i < Partida.JUGADORES; i++) {
            System.out.println("- Jugador N° "+ (i+1) + ": " + juego.siguienteJugador(partida).apellidoYNombre());
        }

        while (partida.getRondaActual() <= Partida.RONDAS) {
            System.out.println("\n===== RONDA " + partida.getRondaActual() + " =====");

            for (int i = 0; i < Partida.JUGADORES; i++) {
                Jugador jugador = juego.jugarTurno(partida, i);
                mostrarTurno(juego, jugador, partida.getCartasRonda()[i]);
            }

            int maximo = partida.getPuntajeMaximo();
            Jugador ganador = juego.cerrarRonda(partida);
            mostrarResultadoRonda(ganador, maximo);
        }

        mostrarResultadoPartida(juego, partida);
    }

    // Muestra las cartas obtenidas por el jugador y su puntaje.
    private static void mostrarTurno(JuegoService juego, Jugador jugador, Carta[] cartas) {
        System.out.println("\n" + jugador.getNombre() + " sacó:");
        for (Carta carta : cartas) {
            System.out.println("  " + carta.toString());
        }

        int puntaje = juego.calcularPuntaje(cartas);
        System.out.println("  puntaje: " + puntaje);
    }

    // Informa si hubo empate o quién ganó la ronda.
    private static void mostrarResultadoRonda(Jugador ganador, int maximo) {
        System.out.println();
        if (ganador == null) {
            System.out.println("Empate con " + maximo + " puntos: cada jugador conserva sus cartas.");
        } else {
            System.out.println("Gana la ronda " + ganador.getNombre() + " con " + maximo
                    + " puntos y se queda con todas las cartas.");
        }
    }

    // Finaliza la partida y muestra el puntaje de cada jugador.
    private static void mostrarResultadoPartida(JuegoService juego, Partida partida) {
        int mayor = juego.finalizarPartida(partida);

        System.out.println("\n===== RESULTADOS FINALES =====");
        for (int i = 0; i < Partida.JUGADORES; i++) {
            Jugador jugador = juego.siguienteJugador(partida);
            System.out.println("- Jugador N° " + (i+1) + ": " + jugador.apellidoYNombre() + ": " + jugador.getPuntaje() + " puntos"
                    + (jugador.getPuntaje() == mayor ? "  <- GANADOR" : ""));
        }
    }

    // Muestra el ranking ordenado de jugadores.
    private static void mostrarRanking(RankingService ranking) {
        SimpleLinkedList<Jugador> lista = ranking.obtenerRanking();
        if (lista.size() == 0) {
            System.out.println("\nTodavía no hay jugadores en el ranking.");
            return;
        }

        System.out.println("\n===== RANKING =====");
        int posicion = 1;
        for (Jugador jugador : lista) {
            System.out.println(posicion++ + ". " + jugador.apellidoYNombre() + " - " + jugador.getPuntajeMaximo() + " puntos");
        }
    }

    // Muestra los alumnos que están inscriptos.
    private static void mostrarInscriptos(InscripcionService registro) {
        System.out.println("\n===== ALUMNOS INSCRIPTOS =====");

        ILinkedList<Jugador> jugadores = registro.obtenerInscriptos();

        if (jugadores.size() == 0) {
            System.out.println("No hay alumnos inscriptos.");
            return;
        }

        int numero = 1;

        for (Jugador jugador : jugadores) {
            System.out.println(numero++ + ". " + jugador.datosAlumno());
        }
    }
}