package syntaerror.proyectofinal.menu;

import java.util.Random;
import java.util.Scanner;

import syntaerror.proyectofinal.minijuegos.Ahorcado.Ahorcado3_0;
import syntaerror.proyectofinal.minijuegos.TaTeTi.Tateti;
import syntaerror.proyectofinal.minijuegos.PiedraPapelTijera.PiedraPapelTijera;

public class menuJuegos {

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
                case 1 -> Ahorcado3_0.main(new String[0]);
                case 2 -> new PiedraPapelTijera("jugador").jugar();
                case 3 -> Tateti.main(new String[0]);
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

}