package main.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase utilitaria para facilitar la captura y validación de entradas por consola.
 *
 * <p>Proporciona métodos estáticos para leer cadenas, enteros, números decimales y fechas,
 * gestionando las excepciones de entrada y la limpieza del búfer del {@link Scanner}.
 *
 * @author Lucas Montaño
 * @version 1.0
 */
public class Input {
  private static final Scanner scanner = new Scanner(System.in);
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  /** Constructor privado para evitar la instanciación de la clase utilitaria. */
  private Input() {}

  /**
   * Solicita una cadena de texto al usuario por consola.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return La cadena de texto ingresada por el usuario.
   */
  public static String getString(String mensaje) {
    System.out.printf("%n%s ", mensaje);
    return scanner.nextLine();
  }

  /**
   * Solicita una cadena de texto al usuario garantizando que no esté vacía ni contenga solo
   * espacios en blanco.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return La cadena de texto recortada (sin espacios al inicio o final).
   */
  public static String getTrimString(String mensaje) {
    String string = "";
    while (true) {
      System.out.printf("%n%s ", mensaje);
      string = scanner.nextLine();
      if (!string.trim().isEmpty()) return string.trim();
      System.out.println("\nERROR: el campo no puede estar vacio");
    }
  }

  /**
   * Solicita un número entero al usuario. Muestra un mensaje de error por defecto si la entrada no
   * es válida.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return El número entero ingresado.
   */
  public static int getInt(String mensaje) {
    return getInt(mensaje, "ERROR: El valor ingresado no corresponde a un numero entero.");
  }

  /**
   * Solicita un número entero al usuario permitiendo personalizar el mensaje de error.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @param mensajeError El mensaje de error que se mostrará si la entrada no es un número entero
   *     válido.
   * @return El número entero ingresado.
   */
  public static int getInt(String mensaje, String mensajeError) {
    int number;
    while (true) {
      try {
        System.out.printf("%n%s ", mensaje);
        number = scanner.nextInt();
        scanner.nextLine();
        return number;
      } catch (InputMismatchException e) {
        System.out.printf("%n%s ", mensajeError);
        scanner.nextLine();
      }
    }
  }

  /**
   * Solicita un número entero estrictamente positivo (mayor que 0). Muestra un mensaje de error por
   * defecto si no cumple la condición.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return El número entero positivo ingresado.
   */
  public static int getPositiveInt(String mensaje) {
    return getPositiveInt(mensaje, "ERROR: El valor ingresado NO es positivo");
  }

  /**
   * Solicita un número entero estrictamente positivo (mayor que 0) con mensaje de error
   * personalizado.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @param mensajeError El mensaje de error si el número es menor o igual a cero.
   * @return El número entero positivo ingresado.
   */
  public static int getPositiveInt(String mensaje, String mensajeError) {
    int number;
    while (true) {
      number = getInt(mensaje);

      if (number > 0) return number;
      System.out.printf("%n%s ", mensajeError);
    }
  }

  /**
   * Solicita un número entero no negativo (mayor o igual a 0). Muestra un mensaje de error por
   * defecto si la entrada es negativa.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return El número entero ingresado (mayor o igual a 0).
   */
  public static int getNonNegativeInt(String mensaje) {
    return getNonNegativeInt(mensaje, "ERROR: el valor ingresado NO puede ser negativo");
  }

  /**
   * Solicita un número entero no negativo (mayor o igual a 0) permitiendo personalizar el mensaje
   * de error.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @param mensajeError El mensaje de error que se mostrará si el número es menor a cero.
   * @return El número entero ingresado (mayor o igual a 0).
   */
  public static int getNonNegativeInt(String mensaje, String mensajeError) {
    int number;
    while (true) {
      number = getInt(mensaje);

      if (number >= 0) return number;
      System.out.printf("%n%s ", mensajeError);
    }
  }

  /**
   * Solicita un número decimal (double) al usuario. Muestra un mensaje de error por defecto si no
   * es válido.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return El número decimal ingresado.
   */
  public static double getDouble(String mensaje) {
    return getDouble(mensaje, "ERROR: El valor ingresado no corresponde a un numero decimal.");
  }

  /**
   * Solicita un número decimal (double) al usuario permitiendo personalizar el mensaje de error.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @param mensajeError El mensaje de error si el valor ingresado no es un decimal válido.
   * @return El número decimal ingresado.
   */
  public static double getDouble(String mensaje, String mensajeError) {
    double number;
    while (true) {
      try {
        System.out.printf("%n%s ", mensaje);
        number = scanner.nextDouble();
        scanner.nextLine();
        return number;
      } catch (InputMismatchException e) {
        System.out.printf("%n%s ", mensajeError);
        scanner.nextLine();
      }
    }
  }

  /**
   * Solicita un número decimal (double) estrictamente positivo (mayor que 0).
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @return El número decimal positivo ingresado.
   */
  public static double getPositiveDouble(String mensaje) {
    return getPositiveDouble(mensaje, "ERROR: El valor ingresado NO es positivo");
  }

  /**
   * Solicita un número decimal (double) estrictamente positivo (mayor que 0) con mensaje de error
   * personalizado.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @param mensajeError El mensaje de error si el número es menor o igual a cero.
   * @return El número decimal positivo ingresado.
   */
  public static double getPositiveDouble(String mensaje, String mensajeError) {
    double number;
    while (true) {
      number = getDouble(mensaje);

      if (number > 0) return number;
      System.out.printf("%n%s ", mensajeError);
    }
  }

  /**
   * Solicita una fecha en formato "dd/MM/yyyy" permitiendo personalizar el mensaje de error.
   *
   * @param mensaje El mensaje o prompt que se mostrará al usuario.
   * @param mensajeError El mensaje de error a mostrar si el usuario no ingresa la fecha con el
   *     formato correcto.
   * @return Un objeto {@link LocalDate} correspondiente a la fecha ingresada.
   */
  public static LocalDate getLocalDate(String mensaje) {
    LocalDate localDate = null;
    while (true) {
      try {
        System.out.printf("%n%s ", mensaje);
        String date = scanner.nextLine();
        localDate = LocalDate.parse(date, DATE_FORMAT);
        if (localDate != null) return localDate;
      } catch (DateTimeParseException e) {
        System.out.println(
            "\n"
                + "ERROR: formato de fecha inválido. Debe usar el formato dd/MM/yyyy (ej."
                + " 15/05/2026).");
      }
    }
  }
}