package syntaerror.proyectofinal.helpers;

import javax.swing.JOptionPane;

public class MenuHelper {

    public static int solicitarOpcion() {

        try {

            String entrada = JOptionPane.showInputDialog(
                    "Elegí la modalidad:\n" +
                    "1. Mejor de 1\n" +
                    "2. Mejor de 3\n" +
                    "3. Salir"
            );

            // Si el usuario presiona Cancelar.
            if (entrada == null) {
                return 3;
            }

            return Integer.parseInt(entrada.trim());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null,
                    "Debés ingresar un número válido.");

            return 0;
        }
    }
}