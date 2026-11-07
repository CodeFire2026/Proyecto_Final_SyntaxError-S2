package syntaxError.proyectofinal.minijuegos.Ahorcado;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Versión gráfica del Ahorcado (Swing).
 *
 * Reutiliza la misma lógica de Ahorcado2_0 (palabra secreta, vector de
 * aciertos, 8 intentos), pero en lugar de la consola y los JOptionPane
 * muestra una ventana con el dibujo del ahorcado, la palabra y un teclado.
 */
public class AhorcadoGrafico extends JFrame {

    private static final int MAX_FALLOS = 8;

    // \u00D1 = Ñ (con escape para no depender de la codificación del archivo)
    private static final String LETRAS = "ABCDEFGHIJKLMN\u00D1OPQRSTUVWXYZ";

    // ---------- Estado del juego ----------
    private String palSecreto;
    private boolean[] aciertos;
    private int numFallos;                       // intentos restantes (8 -> 0)
    private StringBuilder letrasErradas = new StringBuilder();

    // ---------- Componentes ----------
    private PanelDibujo panelDibujo;
    private JLabel lblPalabra;
    private JLabel lblIntentos;
    private JLabel lblErradas;
    private JButton[] botones;

    public AhorcadoGrafico() {
        super("Ahorcado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        numFallos = MAX_FALLOS;

        panelDibujo = new PanelDibujo();

        lblPalabra = new JLabel("Presiona \"Nueva partida\"", JLabel.CENTER);
        lblPalabra.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        lblPalabra.setAlignmentX(CENTER_ALIGNMENT);

        lblIntentos = new JLabel("Intentos restantes: " + MAX_FALLOS + "/" + MAX_FALLOS, JLabel.CENTER);
        lblIntentos.setAlignmentX(CENTER_ALIGNMENT);

        lblErradas = new JLabel("Letras erradas: ", JLabel.CENTER);
        lblErradas.setAlignmentX(CENTER_ALIGNMENT);

        // ----- Teclado en pantalla -----
        JPanel panelTeclado = new JPanel(new GridLayout(3, 9, 4, 4));
        panelTeclado.setAlignmentX(CENTER_ALIGNMENT);
        botones = new JButton[LETRAS.length()];

        for (int i = 0; i < LETRAS.length(); i++) {
            final char letra = LETRAS.charAt(i);
            final JButton boton = new JButton(String.valueOf(letra));
            boton.setFocusable(false);
            boton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jugarLetra(letra, boton);
                }
            });
            botones[i] = boton;
            panelTeclado.add(boton);

            // También se puede jugar con el teclado físico (mayúscula o minúscula)
            Action accion = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (boton.isEnabled()) {
                        boton.doClick(0);
                    }
                }
            };
            String nombre = "letra" + i;
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(Character.toUpperCase(letra)), nombre);
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(Character.toLowerCase(letra)), nombre);
            getRootPane().getActionMap().put(nombre, accion);
        }

        // ----- Botones de control -----
        JButton btnNueva = new JButton("Nueva partida");
        btnNueva.setFocusable(false);
        btnNueva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nuevaPartida();
            }
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFocusable(false);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelControl.setAlignmentX(CENTER_ALIGNMENT);
        panelControl.add(btnNueva);
        panelControl.add(btnSalir);

        // ----- Armado de la ventana -----
        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
        panelSur.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        panelSur.add(lblPalabra);
        panelSur.add(lblIntentos);
        panelSur.add(lblErradas);
        panelSur.add(javax.swing.Box.createVerticalStrut(8));
        panelSur.add(panelTeclado);
        panelSur.add(panelControl);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelDibujo, BorderLayout.CENTER);
        getContentPane().add(panelSur, BorderLayout.SOUTH);

        habilitarTeclado(false);
        pack();
        setLocationRelativeTo(null);
    }

    // =====================================================================
    //  LÓGICA (adaptada de Ahorcado2_0)
    // =====================================================================

    /** Pide la palabra con un campo oculto. Devuelve null si cancelan. */
    private String leerPalabraSecreta() {
        while (true) {
            JPasswordField campo = new JPasswordField(20);
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    campo,
                    "Introduce la palabra a adivinar (1 a 20 letras)",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (respuesta != JOptionPane.OK_OPTION) {
                return null;
            }

            String palabra = new String(campo.getPassword()).trim().toUpperCase();

            if (palabra.length() < 1) {
                JOptionPane.showMessageDialog(this, "La palabra no puede estar vacía");
            } else if (palabra.length() > 20) {
                JOptionPane.showMessageDialog(this, "No se puede tener más de 20 caracteres");
            } else if (!palabra.matches("[A-Z\u00D1]+")) {
                JOptionPane.showMessageDialog(this,
                        "Solo se permiten letras (sin espacios, números ni tildes)");
            } else {
                return palabra;
            }
        }
    }

    private boolean comprobarSecreto(char letra) {
        boolean aciertoLetra = false;
        for (int i = 0; i < palSecreto.length(); i++) {
            if (letra == palSecreto.charAt(i)) {
                aciertos[i] = true;
                aciertoLetra = true;
            }
        }
        return aciertoLetra;
    }

    private String mostrarPalabra() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palSecreto.length(); i++) {
            if (aciertos[i]) {
                sb.append(palSecreto.charAt(i));
            } else {
                sb.append('_');
            }
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    private int contarAciertos() {
        int cantidad = 0;
        for (int i = 0; i < aciertos.length; i++) {
            if (aciertos[i]) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // =====================================================================
    //  FLUJO DE LA PARTIDA
    // =====================================================================

    public void nuevaPartida() {
        String palabra = leerPalabraSecreta();
        if (palabra == null) {
            return; // cancelaron: queda todo como estaba
        }

        palSecreto = palabra;
        aciertos = new boolean[palSecreto.length()];
        numFallos = MAX_FALLOS;
        letrasErradas.setLength(0);

        habilitarTeclado(true);
        actualizarPantalla();
    }

    private void jugarLetra(char letra, JButton boton) {
        boton.setEnabled(false); // así no se puede repetir una letra

        boolean aciertoLetra = comprobarSecreto(letra);
        if (!aciertoLetra) {
            numFallos = numFallos - 1;
            letrasErradas.append(letra).append(' ');
        }

        actualizarPantalla();
        verificarFin();
    }

    private void verificarFin() {
        boolean perdio = (numFallos == 0);
        boolean gano = (contarAciertos() == palSecreto.length());

        if (!perdio && !gano) {
            return;
        }

        habilitarTeclado(false);
        String mensaje;
        if (perdio) {
            lblPalabra.setText(palSecreto.replaceAll("(?<=.)(?=.)", " "));
            mensaje = "Perdiste. La palabra era: " + palSecreto;
        } else {
            mensaje = "¡Ganaste!";
        }

        int otra = JOptionPane.showConfirmDialog(
                this,
                mensaje + "\n¿Jugar otra vez?",
                "Fin de la partida",
                JOptionPane.YES_NO_OPTION);

        if (otra == JOptionPane.YES_OPTION) {
            nuevaPartida();
        }
    }

    private void actualizarPantalla() {
        lblPalabra.setText(mostrarPalabra());
        lblIntentos.setText("Intentos restantes: " + numFallos + "/" + MAX_FALLOS);
        lblErradas.setText("Letras erradas: " + letrasErradas);
        panelDibujo.setFallos(MAX_FALLOS - numFallos);
    }

    private void habilitarTeclado(boolean habilitado) {
        for (int i = 0; i < botones.length; i++) {
            botones[i].setEnabled(habilitado);
        }
    }

    // =====================================================================
    //  DIBUJO DEL AHORCADO
    // =====================================================================

    /** Dibuja una parte más del ahorcado por cada error (8 etapas). */
    private static class PanelDibujo extends JPanel {

        private int fallos = 0;

        public PanelDibujo() {
            setPreferredSize(new Dimension(320, 300));
            setBackground(Color.WHITE);
        }

        public void setFallos(int fallos) {
            this.fallos = fallos;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Se dibuja en un lienzo de 320x300 y se escala al tamaño real
            double escala = Math.min(getWidth() / 320.0, getHeight() / 300.0);
            double dx = (getWidth() - 320 * escala) / 2;
            g2.translate(dx, 0);
            g2.scale(escala, escala);

            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Estructura de madera
            g2.setColor(new Color(110, 70, 30));
            if (fallos >= 1) {                        // 1) base
                g2.drawLine(30, 280, 200, 280);
            }
            if (fallos >= 2) {                        // 2) poste
                g2.drawLine(80, 280, 80, 30);
            }
            if (fallos >= 3) {                        // 3) viga + refuerzo
                g2.drawLine(80, 30, 210, 30);
                g2.drawLine(80, 70, 120, 30);
            }

            // Soga
            if (fallos >= 4) {                        // 4) soga
                g2.setColor(Color.GRAY);
                g2.drawLine(210, 30, 210, 70);
            }

            // Persona
            g2.setColor(Color.DARK_GRAY);
            if (fallos >= 5) {                        // 5) cabeza
                g2.drawOval(190, 70, 40, 40);
            }
            if (fallos >= 6) {                        // 6) torso
                g2.drawLine(210, 110, 210, 190);
            }
            if (fallos >= 7) {                        // 7) brazos
                g2.drawLine(210, 130, 180, 165);
                g2.drawLine(210, 130, 240, 165);
            }
            if (fallos >= 8) {                        // 8) piernas
                g2.drawLine(210, 190, 185, 240);
                g2.drawLine(210, 190, 235, 240);

                // Ojos en X: el muñeco perdió
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(199, 82, 205, 88);
                g2.drawLine(205, 82, 199, 88);
                g2.drawLine(215, 82, 221, 88);
                g2.drawLine(221, 82, 215, 88);
            }

            g2.dispose();
        }
    }

    // =====================================================================
    //  MAIN
    // =====================================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AhorcadoGrafico ventana = new AhorcadoGrafico();
                ventana.setVisible(true);
                ventana.nuevaPartida();
            }
        });
    }
}
