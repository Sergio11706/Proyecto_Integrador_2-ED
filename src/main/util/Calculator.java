package main.util;

import java.util.Arrays;

/**
 * Clase utilitaria para operaciones de cálculo y validaciones numéricas.
 *
 * <p>Proporciona métodos estáticos para promediar arreglos, verificar divisibilidad, evaluar
 * paridad y determinar la primalidad de números enteros.
 *
 * @author Lucas Montaño
 * @version 1.0
 */
public class Calculator {

  /** Constructor privado para evitar la instanciación de la clase utilitaria. */
  private Calculator() {}

  /**
   * Calcula el promedio aritmético de un arreglo de enteros.
   *
   * @param valores Arreglo de números enteros.
   * @return El promedio en formato decimal ({@code double}), o {@code 0.0} si el arreglo está vacío
   *     o es nulo.
   */
  public static double average(int[] valores) {
    if (valores == null || valores.length == 0) return 0.0;

    return Arrays.stream(valores).average().orElse(0.0);
  }

  /**
   * Determina si un número dividendo es exactamente divisible por un divisor.
   *
   * @param dividendo El número a ser dividido.
   * @param divisor El número por el cual se divide.
   * @return {@code true} si la división es exacta; {@code false} en caso contrario.
   * @throws ArithmeticException Si el divisor es igual a cero.
   */
  public static boolean isDivisible(int dividendo, int divisor) {
    if (divisor == 0) throw new ArithmeticException("\nERROR: No es posible dividir por 0");
    return dividendo % divisor == 0;
  }

  /**
   * Determina si un número entero es par.
   *
   * @param number El número entero a verificar.
   * @return {@code true} si el número es par; {@code false} en caso contrario.
   */
  public static boolean isEven(int number) {
    return isDivisible(number, 2);
  }

  /**
   * Determina si un número entero ingresado por parámetro es primo.
   *
   * <p>Aplica la prueba de primalidad de $6k \pm 1$ para lograr mayor eficiencia computacional.
   *
   * @param numero El entero que se desea evaluar.
   * @return {@code true} si el número es primo; {@code false} en caso contrario.
   */
  public static boolean isPrime(int numero) {
    if (numero <= 1) return false;
    if (numero <= 3) return true;
    if (numero % 2 == 0 || numero % 3 == 0) return false;

    for (int i = 5; i * i <= numero; i += 6) {
      if (numero % i == 0 || numero % (i + 2) == 0) {
        return false;
      }
    }
    return true;
  }
}