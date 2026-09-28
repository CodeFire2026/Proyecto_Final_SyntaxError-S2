package Juego;

import javax.swing.JOptionPane;

public class Ahorcado {

    public static int mostrarMenu() {
        int opcion;
        opcion = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "1. Jugar\n2. Salir"));

        return opcion;
    }

    public static void main(String[] args) {

        int opcion = mostrarMenu();

        switch (opcion) {
            case 1:
                String pal_secreto = "";
                String esp_letras = "";
                String letra = "";
                String letras_erradas = "";

                int numFallos = 8;

                boolean juegoTerminado = false;
                boolean letraRepetida;
                boolean[] aciertos = new boolean[20];

                // Pedir palabra secreta
                do {
                    pal_secreto = JOptionPane.showInputDialog(
                            "Introduce la palabra a adivinar");

                    pal_secreto = pal_secreto.toUpperCase();

                    if (pal_secreto.length() > 20) {
                        JOptionPane.showMessageDialog(null,
                                "No se puede tener más de 20 caracteres");
                    }

                    if (pal_secreto.length() < 1) {
                        JOptionPane.showMessageDialog(null,
                                "La palabra no puede estar vacía");
                    }

                } while (pal_secreto.length() > 20 || pal_secreto.length() < 1);

                // Comienza el juego
                do {
                    do {

                        letra = JOptionPane.showInputDialog(
                                "Introduce una letra");

                        letra = letra.toUpperCase();

                        letraRepetida = esp_letras.contains(letra);

                        if (letra.length() != 1) {
                            System.out.println("Error: Debes ingresar una sola letra");
                        } else if (letraRepetida) {

                            System.out.println("Error! Ya usó esta letra");
                        }
                    } while (letra.length() != 1 || letraRepetida);

                    // Guardar letra utilizada
                    esp_letras = esp_letras + letra + " ";

                    boolean aciertoLetra = false;

                    // Buscar la letra en la palabra
                    for (int i = 0; i < pal_secreto.length(); i++) {

                        if (letra.charAt(0) == pal_secreto.charAt(i)) {

                            aciertos[i] = true;
                            aciertoLetra = true;
                        }
                    }

                    if (aciertoLetra == false) {
                        numFallos = numFallos - 1;
                        letras_erradas = letras_erradas + letra + " ";
                    }
                    System.out.println("Intentos restantes: " + numFallos + "/8");
                    System.out.println("Letras erradas: " + letras_erradas);
                    mostrarAhorcado(numFallos);

                    // Comprobar si perdió
                    if (numFallos == 0) {

                        juegoTerminado = true;
                    }

                    // Mostrar palabra
                    for (int i = 0; i < pal_secreto.length(); i++) {

                        if (aciertos[i]) {

                            System.out.print(
                                    pal_secreto.charAt(i) + " ");

                        } else {

                            System.out.print("_ ");
                        }
                    }

                    System.out.println();

                    // Mostrar letras utilizadas
                    System.out.println(
                            "Letras introducidas: " + esp_letras);

                    // Contar cantidad de aciertos
                    int cantidadAciertos = 0;

                    for (int i = 0; i < pal_secreto.length(); i++) {

                        if (aciertos[i]) {

                            cantidadAciertos
                                    = cantidadAciertos + 1;
                        }
                    }

                    // Comprobar si ganó
                    if (cantidadAciertos == pal_secreto.length()) {

                        juegoTerminado = true;
                    }

                } while (juegoTerminado == false);
                if (numFallos == 0) {
                    System.out.println("Perdiste. La palabra era: " + pal_secreto);
                } else {
                    System.out.println("Ganaste");
                }
                break;
                
            case 2:
                System.out.println("Saliendo del juego...");
                break;

        }
    }

    public static void mostrarAhorcado(int numFallos) {
        switch (numFallos) {
            case 8:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 7:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 6:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println(" |    |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 5:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("/|    |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 4:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("/|\\    |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 3:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("/|\\   |");
                System.out.println("/     |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 2:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("/|\\   |");
                System.out.println("/ \\   |");
                System.out.println("      |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 1:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("/|\\   |");
                System.out.println("/ \\   |");
                System.out.println(" |    |");
                System.out.println("      |");
                System.out.println("=======");
                break;
            case 0:
                System.out.println(" +----+");
                System.out.println(" |    |");
                System.out.println(" O    |");
                System.out.println("/|\\   |");
                System.out.println("/ \\   |");
                System.out.println(" |    |");
                System.out.println(" |     |");
                System.out.println("=======");
                break;
        }

    }
}
