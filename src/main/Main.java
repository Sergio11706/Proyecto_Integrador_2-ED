package main;

/**
 * Clase principal para el Proyecto Integrador II - Estructura de Datos.
 *
 * @author Acuña, Sergio Facundo
 * @author Andreu, Luca Maximiliano
 * @author Silva, Jael Abril Alejandra
 * @author Cruz, Kevin Brian Joel
 * @author Montaño, Lucas Antonio
 * @author Ramos, Daniel Maximiliano Efraín
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

    public static void main(String[] args) {

        System.out.println("Hello World");

    }
}
