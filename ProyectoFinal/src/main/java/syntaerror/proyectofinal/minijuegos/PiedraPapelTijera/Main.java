package syntaerror.proyectofinal.minijuegos.PiedraPapelTijera;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        String nombre = JOptionPane.showInputDialog(
            null,
            "Bienvenido a Piedra, Papel o Tijera\n\n"
            + "Ingresá tu nombre:"
        );

        if (nombre == null) {
            return;
        }

        if (nombre.trim().isEmpty()) {
            nombre = "Jugador";
        }

        PiedraPapelTijera juego =
                new PiedraPapelTijera(nombre);

        int opcion;

        do {

            opcion = PiedraPapelTijera.solicitarNumero(
                "===== MENÚ PRINCIPAL =====\n\n"
                + "1. Jugar\n"
                + "2. Ver estadísticas\n"
                + "3. Reiniciar puntuación\n"
                + "4. Salir\n\n"
                + "Seleccioná una opción:"
            );

            switch (opcion) {

                case 1:
                    juego.jugar();
                    break;

                case 2:
                    juego.mostrarEstadisticas();
                    break;

                case 3:
                    juego.reiniciarPuntuacion();
                    break;

                case 4:
                case -1:
                    JOptionPane.showMessageDialog(
                        null,
                        "Gracias por jugar. ¡Hasta pronto!"
                    );
                    break;

                default:
                    JOptionPane.showMessageDialog(
                        null,
                        "Opción incorrecta. Elegí del 1 al 4."
                    );
                    break;
            }

        } while (opcion != 4 && opcion != -1);
    }
}
