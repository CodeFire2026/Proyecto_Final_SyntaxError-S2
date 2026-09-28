package Juego;

import javax.swing.JOptionPane;

public class Ahorcado2_0 {

    public static int mostrarMenu() {
        int opcion;
        opcion = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "1. Jugar\n2. Salir"));

        return opcion;
    }

    public static String LeerPalabraSecreta() {
        String pal_secreto = "";
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
        return pal_secreto;

    }

    public static String LeerLetra(String esp_letras) {

        boolean letraRepetida;
        String letra = "";
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

        return letra;
    }

    public static boolean ComprobarSecreto(
            String letra,
            String pal_secreto,
            boolean[] aciertos) {

        boolean aciertoLetra = false;

        // Buscar la letra en la palabra
        for (int i = 0; i < pal_secreto.length(); i++) {

            if (letra.charAt(0) == pal_secreto.charAt(i)) {

                aciertos[i] = true;
                aciertoLetra = true;
            }
        }
        return aciertoLetra;
    }

    public static void MostrarPalabra(
            String pal_secreto,
            boolean[] aciertos) {
        for (int i = 0; i < pal_secreto.length(); i++) {

            if (aciertos[i]) {
                System.out.print(
                        pal_secreto.charAt(i) + " ");
            } else {
                System.out.print("_ ");
            }
        }

        System.out.println();
    }

    public static int ContarAciertos(boolean[] aciertos) {

        int cantidadAciertos = 0;

        for (int i = 0; i < aciertos.length; i++) {

            if (aciertos[i]) {

                cantidadAciertos
                        = cantidadAciertos + 1;
            }
        }
        return cantidadAciertos;
    }

    public static void main(String[] args) {

        int opcion = mostrarMenu();

        switch (opcion) {

            case 1:
                String letras_erradas = "";
                String pal_secreto = LeerPalabraSecreta();
                String esp_letras = "";

                int numFallos = 8;

                boolean[] aciertos = new boolean[20];
                boolean juegoTerminado = false;

                do {
                    String letra = LeerLetra(esp_letras);
                    esp_letras = esp_letras + letra + " ";
                    boolean aciertoLetra = ComprobarSecreto(
                            letra,
                            pal_secreto,
                            aciertos);
                    if (aciertoLetra == false) {
                        numFallos = numFallos - 1;
                        letras_erradas = letras_erradas + letra + " ";
                    }
                    System.out.println("Intentos restantes: " + numFallos + "/8");
                    System.out.println("Letras erradas: " + letras_erradas);

                    //mostrarAhorcado(numFallos);
                    MostrarPalabra(pal_secreto, aciertos);
                    if (numFallos == 0) {
                        juegoTerminado = true;
                    }
                    int cantidadAciertos = ContarAciertos(aciertos);

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

}
