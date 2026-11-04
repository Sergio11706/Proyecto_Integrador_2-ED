package main.util;

import java.util.Random;

/**
 * Clase utilitaria para la generación de valores aleatorios.
 *
 * <p>Proporciona métodos estáticos para generar números enteros y decimales (double) dentro de
 * rangos determinados.
 *
 * @author Lucas Montaño
 * @version 1.0
 */
public class Generator {

  private static final Random random = new Random();

  /** Constructor privado para evitar la instanciación de la clase utilitaria. */
  private Generator() {}

  /**
   * Genera un número entero aleatorio dentro de un rango determinado (inclusivo). Los límites
   * pueden enviarse en cualquier orden.
   *
   * @param rango1 Primer límite del rango.
   * @param rango2 Segundo límite del rango.
   * @return Un número entero aleatorio entre los rangos especificados (ambos inclusive).
   */
  public static int randomInt(int rango1, int rango2) {
    if (rango1 == rango2) return rango1;

    int minimo = Math.min(rango1, rango2);
    int maximo = Math.max(rango1, rango2);

    return minimo + random.nextInt((maximo - minimo) + 1);
  }

  /**
   * Genera un número decimal (double) aleatorio dentro de un rango determinado. Los límites pueden
   * enviarse en cualquier orden.
   *
   * @param rango1 Primer límite del rango.
   * @param rango2 Segundo límite del rango.
   * @return Un número decimal (double) aleatorio entre el rango especificado.
   */
  public static double randomDouble(double rango1, double rango2) {
    if (rango1 == rango2) return rango1;

    double minimo = Math.min(rango1, rango2);
    double maximo = Math.max(rango1, rango2);

    return minimo + (maximo - minimo) * random.nextDouble();
  }
}
