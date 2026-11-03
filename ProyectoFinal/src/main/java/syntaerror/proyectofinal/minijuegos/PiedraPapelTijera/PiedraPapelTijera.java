package syntaerror.proyectofinal.minijuegos.PiedraPapelTijera;

import javax.swing.JOptionPane;
import java.util.Random;

public class PiedraPapelTijera {

    private Jugador jugador;
    private Jugador computadora;
    private Random random;

    public PiedraPapelTijera(String nombre) {
        jugador = new Jugador(nombre);
        computadora = new Jugador("Computadora");
        random = new Random();
    }

    public void jugar() {

        Object[] modos = {"Una partida", "Al mejor de 3", "Volver"};
        int modo = JOptionPane.showOptionDialog(null,
                "Elegí el modo de juego:\n"
                + "Una partida: una ronda, puede terminar en empate.\n"
                + "Al mejor de 3: gana quien logra 2 victorias.\n"
                + "Los empates se repiten.",
                "Modo de juego", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, modos, modos[0]);
        if (modo != 0 && modo != 1) {
            return;
        }

        int victoriasJugador = 0;
        int victoriasPc = 0;
        int ronda = 1;
        int opcion;

        do {

            opcion = solicitarNumero(
                "===== PIEDRA, PAPEL O TIJERA =====\n\n"
                + "Modo: " + (modo == 0 ? "Una partida" : "Al mejor de 3") + "\n"
                + "Ronda: " + ronda + "\n"
                + "Marcador de esta partida: " + victoriasJugador + " - " + victoriasPc + "\n\n"
                + "1. Piedra\n"
                + "2. Papel\n"
                + "3. Tijera\n"
                + "4. Volver al menú"
            );

            if (opcion == 4 || opcion == -1) {
                JOptionPane.showMessageDialog(null,
                        "Partida interrumpida. Las rondas jugadas se conservan en las estadísticas.");
                return;
            }

            if (opcion < 1 || opcion > 3) {
                JOptionPane.showMessageDialog(
                    null, "Opción incorrecta."
                );
                continue;
            }

            int jugadaPc = random.nextInt(3) + 1;

            String eleccionJugador = convertirJugada(opcion);
            String eleccionPc = convertirJugada(jugadaPc);

            String resultado;

            if (opcion == jugadaPc) {

                resultado = "¡EMPATE!";
                jugador.empatar();
                computadora.empatar();

            } else if (
                (opcion == 1 && jugadaPc == 3)
                || (opcion == 2 && jugadaPc == 1)
                || (opcion == 3 && jugadaPc == 2)
            ) {

                resultado = "¡GANASTE!";
                jugador.ganar();
                computadora.perder();
                victoriasJugador++;

            } else {

                resultado = "¡PERDISTE!";
                jugador.perder();
                computadora.ganar();
                victoriasPc++;
            }

            JOptionPane.showMessageDialog(
                null,
                "===== RESULTADO =====\n\n"
                + jugador.getNombre() + ": "
                + eleccionJugador + "\n"
                + "Computadora: " + eleccionPc + "\n\n"
                + resultado + "\n\n"
                + "Marcador de esta partida: " + victoriasJugador + " - " + victoriasPc + "\n\n"
                + "===== PUNTOS ACUMULADOS =====\n"
                + jugador.getNombre() + ": "
                + jugador.getPuntos() + "\n"
                + "Computadora: "
                + computadora.getPuntos()
            );

            if (modo == 0 || victoriasJugador == 2 || victoriasPc == 2) {
                String ganador = victoriasJugador == victoriasPc ? "¡Empate!"
                        : victoriasJugador > victoriasPc ? "¡Ganaste la partida!"
                        : "La computadora ganó la partida.";
                JOptionPane.showMessageDialog(null,
                        "===== FIN DE LA PARTIDA =====\n\n" + ganador
                        + "\nResultado final: " + victoriasJugador + " - " + victoriasPc);
                return;
            }
            ronda++;

        } while (true);
    }

    private String convertirJugada(int jugada) {

        switch (jugada) {
            case 1:
                return "Piedra";
            case 2:
                return "Papel";
            case 3:
                return "Tijera";
            default:
                return "Desconocida";
        }
    }

    public void mostrarEstadisticas() {

        int partidas = jugador.getVictorias()
                + jugador.getDerrotas()
                + jugador.getEmpates();

        JOptionPane.showMessageDialog(
            null,
            "===== ESTADÍSTICAS =====\n\n"
            + "Jugador: " + jugador.getNombre() + "\n"
            + "Rondas jugadas: " + partidas + "\n\n"
            + "Victorias: " + jugador.getVictorias() + "\n"
            + "Derrotas: " + jugador.getDerrotas() + "\n"
            + "Empates: " + jugador.getEmpates() + "\n\n"
            + "Puntos: " + jugador.getPuntos()
        );
    }

    public void reiniciarPuntuacion() {

        int respuesta = JOptionPane.showConfirmDialog(
            null,
            "¿Querés reiniciar las estadísticas?",
            "Confirmación",
            JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            jugador.reiniciar();
            computadora.reiniciar();

            JOptionPane.showMessageDialog(
                null, "Estadísticas reiniciadas."
            );
        }
    }

    public static int solicitarNumero(String mensaje) {

        while (true) {

            String entrada = JOptionPane.showInputDialog(
                null, mensaje
            );

            if (entrada == null) {
                return -1;
            }

            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Ingresá un número válido."
                );
            }
        }
    }
}
