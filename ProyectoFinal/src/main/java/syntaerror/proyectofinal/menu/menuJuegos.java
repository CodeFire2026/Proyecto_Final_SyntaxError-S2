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
    
    private static void piedraPapelTijeras() {
       String[] nombres = {"Piedra", "Papel", "Tijeras"};
        int puntosJugador = 0;
        int puntosPC = 0;

        System.out.println("\n--- PIEDRA, PAPEL O TIJERAS ---");

        while (true) {
            System.out.println("\n1 - Piedra | 2 - Papel | 3 - Tijeras | 0 - Volver al menu");
            int eleccion = leerEntero("Tu jugada: ");

            if (eleccion == 0) {
                break;
            }
            if (eleccion < 1 || eleccion > 3) {
                System.out.println("Opcion invalida.");
                continue;
            }

            int jugador = eleccion - 1;
            int pc = rnd.nextInt(3);

            System.out.println("Vos: " + nombres[jugador] + " | PC: " + nombres[pc]);

            if (jugador == pc) {
                System.out.println("Empate!");
            } else if ((jugador + 1) % 3 == pc) {
                // Papel(1) tapa Piedra(0), Tijeras(2) corta Papel(1), Piedra(0) rompe Tijeras(2)
                // Si el siguiente del jugador es la PC, la PC gana
                System.out.println("Perdiste esta ronda.");
                puntosPC++;
            } else {
                System.out.println("Ganaste esta ronda!");
                puntosJugador++;
            }
            System.out.println("Marcador -> Vos: " + puntosJugador + " | PC: " + puntosPC);
        }

        System.out.println("Resultado final -> Vos: " + puntosJugador + " | PC: " + puntosPC);
    }

    // ---------- Juego 3: TaTeTi ----------

    private static void taTeTi() {
        System.out.println("\n--- TATETI ---");
        System.out.println("1 - Jugador vs PC");
        System.out.println("2 - Jugador vs Jugador");
        int modo;
        do {
            modo = leerEntero("Elegi el modo: ");
        } while (modo != 1 && modo != 2);

        char[] tablero = new char[9];
        java.util.Arrays.fill(tablero, ' ');
        char turno = 'X';
        char ganador = ' ';
        int jugadas = 0;

        while (ganador == ' ' && jugadas < 9) {
            mostrarTablero(tablero);
            int pos;

            if (modo == 1 && turno == 'O') {
                do {
                    pos = rnd.nextInt(9);
                } while (tablero[pos] != ' ');
                System.out.println("La PC juega en la posicion " + (pos + 1));
            } else {
                while (true) {
                    pos = leerEntero("Turno de " + turno + " (1-9): ") - 1;
                    if (pos < 0 || pos > 8) {
                        System.out.println("Elegi un numero entre 1 y 9.");
                    } else if (tablero[pos] != ' ') {
                        System.out.println("Esa casilla esta ocupada.");
                    } else {
                        break;
                    }
                }
            }

            tablero[pos] = turno;
            jugadas++;
            ganador = hayGanador(tablero);
            turno = (turno == 'X') ? 'O' : 'X';
        }

        mostrarTablero(tablero);
        if (ganador != ' ') {
            System.out.println("Gano " + ganador + "!");
        } else {
            System.out.println("Empate!");
        }
    }

    private static void mostrarTablero(char[] t) {
        System.out.println();
        for (int fila = 0; fila < 3; fila++) {
            int i = fila * 3;
            System.out.println(" " + celda(t, i) + " | " + celda(t, i + 1) + " | " + celda(t, i + 2));
            if (fila < 2) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    private static char celda(char[] t, int i) {
        return t[i] == ' ' ? (char) ('1' + i) : t[i];
    }

    private static char hayGanador(char[] t) {
        int[][] lineas = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] l : lineas) {
            if (t[l[0]] != ' ' && t[l[0]] == t[l[1]] && t[l[1]] == t[l[2]]) {
                return t[l[0]];
            }
        }
        return ' ';
    }
}


