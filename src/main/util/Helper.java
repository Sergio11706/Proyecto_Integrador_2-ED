package main.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import java.util.Set;

/**
 * Clase de ayuda (helper) general para los trabajos prácticos.
 *
 * @author Sergio Acuña
 *
 * Descripción:
 *    Reúne en un solo lugar la lectura y validación de datos ingresados por consola
 *    y la generación de números aleatorios, para reutilizar codigo.
 *
 *    - Lee int, double, String y LocalDate, repitiendo la solicitud hasta que el valor sea válido.
 *    - Permite exigir rangos (entre, mayor que, menor que) en enteros, decimales y fechas.
 *    - Valida formatos de texto: Gmail y DNI.
 *    - Lee opciones de menú (de un entero a otro) y respuestas de sí/no en varios formatos.
 *    - Usa un único Scanner compartido para evitar problemas con el buffer de entrada.
 */
public final class Helper {

    private static final Scanner scanner = new Scanner(System.in);

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private static final String GMAIL_FORMAT = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
    private static final String DNI_FORMAT = "\\d{7,8}";
    private static final String DOUBLE_FORMAT = "[+-]?\\d+([.,]\\d+)?";

    private static final Set<String> YES_VALUES = Set.of("s", "si", "sí", "y", "yes");
    private static final Set<String> NO_VALUES = Set.of("n", "no");

    private Helper() { }

    // Muestra el mensaje y lee una línea sin espacios extremos; corta si se cierra la entrada
    public static String readLine(String message) {
        System.out.print(message);
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Entrada de datos cerrada.");
        }
        return scanner.nextLine().trim();
    }

    // ENTEROS

    // Lee un entero válido (rechaza texto, decimales y vacío) y repite hasta lograrlo
    public static int readInt(String message) {
        while (true) {
            String input = readLine(message);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero válido.");
            }
        }
    }

    // Lee un entero entre min y max (ambos incluidos)
    public static int readIntBetween(String message, int min, int max) {
        while (true) {
            int value = readInt(message);
            if (value >= min && value <= max) return value;
            System.out.println("Error: el valor debe estar entre " + min + " y " + max + ".");
        }
    }

    // Lee un entero estrictamente mayor que min
    public static int readIntGreaterThan(String message, int min) {
        while (true) {
            int value = readInt(message);
            if (value > min) return value;
            System.out.println("Error: el valor debe ser mayor que " + min + ".");
        }
    }

    // Lee un entero estrictamente menor que max
    public static int readIntLessThan(String message, int max) {
        while (true) {
            int value = readInt(message);
            if (value < max) return value;
            System.out.println("Error: el valor debe ser menor que " + max + ".");
        }
    }

    // DECIMALES

    // Lee un decimal válido (acepta punto o coma; rechaza texto y vacío) y repite hasta lograrlo
    public static double readDouble(String message) {
        while (true) {
            String input = readLine(message);
            if (input.matches(DOUBLE_FORMAT)) {
                double value = Double.parseDouble(input.replace(',', '.'));
                if (Double.isFinite(value)) return value;
            }
            System.out.println("Error: ingrese un número decimal válido.");
        }
    }

    // Lee un decimal entre min y max (ambos incluidos)
    public static double readDoubleBetween(String message, double min, double max) {
        while (true) {
            double value = readDouble(message);
            if (value >= min && value <= max) return value;
            System.out.println("Error: el valor debe estar entre " + min + " y " + max + ".");
        }
    }

    // Lee un decimal estrictamente mayor que min
    public static double readDoubleGreaterThan(String message, double min) {
        while (true) {
            double value = readDouble(message);
            if (value > min) return value;
            System.out.println("Error: el valor debe ser mayor que " + min + ".");
        }
    }

    // Lee un decimal estrictamente menor que max
    public static double readDoubleLessThan(String message, double max) {
        while (true) {
            double value = readDouble(message);
            if (value < max) return value;
            System.out.println("Error: el valor debe ser menor que " + max + ".");
        }
    }

    // TEXTO

    // Lee un texto que no esté vacío ni formado solo por espacios
    public static String readString(String message) {
        while (true) {
            String input = readLine(message);
            if (!input.isEmpty()) return input;
            System.out.println("Error: el texto no puede estar vacío.");
        }
    }

    // Lee un correo con formato Gmail (algo@gmail.com)
    public static String readGmail(String message) {
        while (true) {
            String input = readString(message);
            if (input.matches(GMAIL_FORMAT)) return input;
            System.out.println("Error: ingrese un correo Gmail válido (ejemplo: nombre@gmail.com).");
        }
    }

    // Lee un DNI con formato válido (7 u 8 dígitos) y lo devuelve como texto
    public static String readDni(String message) {
        while (true) {
            String input = readString(message);
            if (input.matches(DNI_FORMAT)) return input;
            System.out.println("Error: el DNI debe tener 7 u 8 dígitos, sin puntos ni letras.");
        }
    }

    // FECHAS (formato dd/MM/yyyy)

    // Lee una fecha real en formato dd/MM/yyyy (rechaza fechas inexistentes como 31/02/2026)
    public static LocalDate readLocalDate(String message) {
        while (true) {
            String input = readLine(message + " (dd/MM/yyyy): ");
            try {
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Error: fecha inválida. Use el formato dd/MM/yyyy.");
            }
        }
    }

    // Lee una fecha estrictamente posterior a la fecha indicada
    public static LocalDate readLocalDateAfter(String message, LocalDate date) {
        while (true) {
            LocalDate value = readLocalDate(message);
            if (value.isAfter(date)) return value;
            System.out.println("Error: la fecha debe ser posterior al " + date.format(DATE_FORMAT) + ".");
        }
    }

    // Lee una fecha estrictamente anterior a la fecha indicada
    public static LocalDate readLocalDateBefore(String message, LocalDate date) {
        while (true) {
            LocalDate value = readLocalDate(message);
            if (value.isBefore(date)) return value;
            System.out.println("Error: la fecha debe ser anterior al " + date.format(DATE_FORMAT) + ".");
        }
    }

    // Lee una fecha entre from y to (ambas incluidas)
    public static LocalDate readLocalDateBetween(String message, LocalDate from, LocalDate to) {
        while (true) {
            LocalDate value = readLocalDate(message);
            if (!value.isBefore(from) && !value.isAfter(to)) return value;
            System.out.println("Error: la fecha debe estar entre el " + from.format(DATE_FORMAT)
                    + " y el " + to.format(DATE_FORMAT) + ".");
        }
    }

    // Lee una opción de menú entre min y max; si es incorrecta avisa y vuelve a pedirla
    public static int readMenuOption(String message, int min, int max) {
        while (true) {
            int option = readInt(message);
            if (option >= min && option <= max) return option;
            System.out.println("Opción incorrecta.");
        }
    }

    // Lee una respuesta sí/no en varios formatos (s, si, sí, y, yes / n, no, en cualquier capitalización)
    public static boolean readYesNo(String message) {
        while (true) {
            String answer = readLine(message + " (si/no): ").toLowerCase();
            if (YES_VALUES.contains(answer)) return true;
            if (NO_VALUES.contains(answer)) return false;
            System.out.println("Error: responda con si/no (también se acepta s, n, yes).");
        }
    }

}
