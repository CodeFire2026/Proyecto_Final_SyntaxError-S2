import java.util.Random;
import java.util.Scanner;

public class MenuJuegos {

    private static final Scanner sc = new Scanner(System.in);
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n==============================");
            System.out.println("        MENU DE JUEGOS");
            System.out.println("==============================");
            System.out.println("1 - Ahorcado");
            System.out.println("2 - Piedra, Papel o Tijeras");
            System.out.println("3 - TaTeTi");
            System.out.println("0 - Salir");
            opcion = leerEntero("Elegi una opcion: ");

            switch (opcion) {
                case 1 -> ahorcado();
                case 2 -> piedraPapelTijeras();
                case 3 -> taTeTi();
                case 0 -> System.out.println("Hasta la proxima!");
                default -> System.out.println("Opcion invalida, proba de nuevo.");
            }
        } while (opcion != 0);
    }

    // ---------- Utilidades ----------

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un numero valido.");
            }
        }
    }

    // ---------- Juego 1: Ahorcado ----------

    private static void ahorcado() {
        String[] palabras = {"JAVA", "PROGRAMA", "TECLADO", "VARIABLE", "CLASE",
                "OBJETO", "HERENCIA", "COMPILADOR", "ARREGLO", "METODO",
                "INTERFAZ", "BUCLE", "EXCEPCION", "SERVIDOR"};
        String palabra = palabras[rnd.nextInt(palabras.length)];
        char[] progreso = new char[palabra.length()];
        java.util.Arrays.fill(progreso, '_');

        StringBuilder usadas = new StringBuilder();
        int vidas = 6;

        System.out.println("\n--- AHORCADO ---");

        while (vidas > 0 && new String(progreso).contains("_")) {
            dibujarAhorcado(vidas);
            System.out.println("Palabra: " + String.valueOf(progreso).replace("", " ").trim());
            System.out.println("Letras usadas: " + usadas);
            System.out.println("Vidas: " + vidas);
            System.out.print("Ingresa una letra: ");
            String entrada = sc.nextLine().trim().toUpperCase();

            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Ingresa una sola letra.");
                continue;
            }
            char letra = entrada.charAt(0);

            if (usadas.indexOf(String.valueOf(letra)) >= 0) {
                System.out.println("Ya usaste esa letra.");
                continue;
            }
            usadas.append(letra).append(' ');

            if (palabra.indexOf(letra) >= 0) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (palabra.charAt(i) == letra) {
                        progreso[i] = letra;
                    }
                }
                System.out.println("Bien! La letra esta en la palabra.");
            } else {
                vidas--;
                System.out.println("Esa letra no esta.");
            }
        }

        dibujarAhorcado(vidas);
        if (vidas > 0) {
            System.out.println("GANASTE! La palabra era: " + palabra);
        } else {
            System.out.println("PERDISTE. La palabra era: " + palabra);
        }
    }

    private static void dibujarAhorcado(int vidas) {
        String[] etapas = {
                "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n      |\n      |\n      |\n      |\n========="
        };
        System.out.println(etapas[vidas]);
    }

    // ---------- Juego 2: Piedra, Papel o Tijeras ----------

        // ----- agrega lo que falta aca cristian 

}
