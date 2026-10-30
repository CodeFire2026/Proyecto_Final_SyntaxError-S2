import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

/**
 * PROYECTO INTEGRADOR — TA TE TI (versión en un solo archivo)
 */
public class Tateti  {
    public static void main(String[] args) {
        // Fuerza UTF-8 en la salida para que tildes, "¡" y "¿" se vean bien
        // sin importar la configuración regional de la consola.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Juego juego = new Juego();
        juego.iniciar();
    }
}

class Juego {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final Tablero tablero = new Tablero();

    public void iniciar() {
        limpiarPantalla();
        System.out.println("=== TA TE TI ===");

        int puntos1 = 0;
        int puntos2 = 0;
        int salir = 0;

        do {
            int modo;
            do {
                limpiarPantalla();
                System.out.println("=== TA TE TI ===");
                System.out.println("Seleccione modo: ");
                System.out.println("1. Jugador vs Jugador");
                System.out.println("2. Jugador vs CPU");
                System.out.println("3. Salir");
                modo = leerEntero("");

                if (!(modo == 1 || modo == 2 || modo == 3)) {
                    System.out.println("Opción inexistente");
                }
            } while (!(modo == 1 || modo == 2 || modo == 3));

            if (modo == 3) {
                salir = 1;
            } else {
                System.out.print("Ingrese nombre del jugador X: ");
                String nombre1 = scanner.nextLine();

                String nombre2;
                if (modo == 1) {
                    System.out.print("Ingrese nombre del jugador O: ");
                    nombre2 = scanner.nextLine();
                } else {
                    nombre2 = "CPU";
                }

                puntos1 = 0;
                puntos2 = 0;

                int opcion;
                do {
                    int jugador = 1;
                    int jugadas = 0;
                    char ganador = ' ';

                    tablero.inicializar();

                    while (ganador == ' ' && jugadas < 9) {

                        limpiarPantalla();
                        tablero.mostrar();
                        System.out.println();

                        System.out.println("Marcador:");
                        System.out.println(nombre1 + ": " + puntos1);
                        System.out.println(nombre2 + ": " + puntos2);
                        System.out.println();

                        if (jugador == 1) {
                            System.out.println("Turno de " + nombre1 + " (X)");
                        } else {
                            if (modo == 2) {
                                System.out.println("Turno de " + nombre2 + " (O)");
                                System.out.println("Pensando la mejor jugada...");
                                esperar(1500);
                            } else {
                                System.out.println("Turno de " + nombre2 + " (O)");
                            }
                        }

                        int fila;
                        int col;

                        if (jugador == 2 && modo == 2) {
                            int[] jugada = CPU.decidirJugada(tablero);
                            fila = jugada[0];
                            col = jugada[1];
                        } else {
                            do {
                                fila = leerEntero("Ingrese FILA (1-3): ");
                                col = leerEntero("Ingrese COLUMNA (1-3): ");
                            } while (!tablero.movimientoValido(fila, col));
                        }

                        limpiarPantalla();
                        tablero.mostrar();
                        System.out.println("Colocando ficha...");
                        esperar(500);

                        if (jugador == 1) {
                            tablero.colocar(fila, col, 'X');
                            jugador = 2;
                        } else {
                            tablero.colocar(fila, col, 'O');
                            jugador = 1;
                        }

                        jugadas = jugadas + 1;
                        ganador = tablero.verificarGanador();
                    }

                    limpiarPantalla();
                    tablero.mostrar();
                    System.out.println();

                    if (ganador == 'X') {
                        System.out.println(" ¡Ganó " + nombre1 + "!");
                        puntos1 = puntos1 + 1;
                    } else if (ganador == 'O') {
                        System.out.println(" ¡Ganó " + nombre2 + "!");
                        puntos2 = puntos2 + 1;
                    } else {
                        System.out.println(" Empate ");
                    }

                    do {
                        System.out.println();
                        System.out.println("1. Revancha");
                        System.out.println("2. Regresar al menú");
                        opcion = leerEntero("");

                        if (!(opcion == 1 || opcion == 2)) {
                            System.out.println("Opción inexistente");
                        }
                    } while (!(opcion == 1 || opcion == 2));

                } while (opcion != 2);
            }
        } while (salir != 1);

        System.out.println("¡Gracias por jugar!");
        scanner.close();
    }

    /** Lee un entero desde consola, mostrando primero el mensaje indicado, y valida el formato. */
    private int leerEntero(String mensaje) {
        while (true) {
            if (!mensaje.isEmpty()) {
                System.out.print(mensaje);
            }
            if (!scanner.hasNextLine()) {
                // Se cerró la entrada (ej. Ctrl+D): salimos del programa prolijamente.
                System.out.println("\nEntrada finalizada. ¡Hasta luego!");
                System.exit(0);
            }
            String linea = scanner.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido");
            }
        }
    }

    /** Pausa la ejecución la cantidad de milisegundos indicada. */
    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Limpia la consola de verdad (equivalente a "Limpiar Pantalla" de PSeInt).
     * IMPORTANTE: para que la limpieza se vea, el programa debe ejecutarse
     * desde una terminal real (CMD, PowerShell, Git Bash, Terminal de Mac/
     * Linux), no desde la consola integrada del IDE, ya que esas consolas
     * no son una terminal real y no pueden limpiarse.
     */
    private void limpiarPantalla() {
        try {
            String so = System.getProperty("os.name").toLowerCase();
            if (so.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Respaldo si por algún motivo no se puede invocar el comando nativo.
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}

