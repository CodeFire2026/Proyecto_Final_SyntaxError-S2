package syntaerror.proyectofinal.minijuegos.Ahorcado;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Ahorcado 3.0: unificación de la base (Ahorcado2_0) y la versión gráfica
 * (AhorcadoGrafico) en un solo archivo.
 *
 * La palabra secreta se pide dentro de la ventana (pantalla de inicio), sin
 * carteles flotantes. Al terminar la partida se usa un JOptionPane con las
 * opciones Continuar / Salir.
 *
 * Toda la lógica del juego vive en la clase interna {@link Logica}, que no
 * depende de Swing. Esta clase solo se encarga de la ventana: el dibujo del
 * ahorcado, la palabra, los intentos, el teclado y el flujo entre pantallas.
 *
 * Reglas: palabra secreta de 1 a 20 letras (A-Z y Ñ), 8 intentos y una parte
 * del dibujo por cada error.
 */
public class Ahorcado3_0 extends JFrame {

    private static final String CARD_INICIO = "inicio";
    private static final String CARD_JUEGO = "juego";

    // \u00D1 = Ñ (escape para no depender de la codificación del archivo)
    private static final String LETRAS = "ABCDEFGHIJKLMN\u00D1OPQRSTUVWXYZ";

    private final Logica logica = new Logica();
    private final CardLayout cards = new CardLayout();
    private final JPanel contenedor = new JPanel(cards);

    // ---------- Pantalla de inicio ----------
    private JPasswordField campoPalabra;
    private JCheckBox chkMostrar;
    private JLabel lblError;
    private char echoOriginal;

    // ---------- Pantalla de juego ----------
    private PanelDibujo panelDibujo;
    private JLabel lblPalabra;
    private JLabel lblIntentos;
    private JLabel lblErradas;
    private JButton[] botones;

    public Ahorcado3_0() {
        super("Ahorcado 3.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        contenedor.add(crearPanelInicio(), CARD_INICIO);
        contenedor.add(crearPanelJuego(), CARD_JUEGO);
        setContentPane(contenedor);

        mostrarInicio();

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    // =====================================================================
    //  PANTALLA DE INICIO
    // =====================================================================

    private JPanel crearPanelInicio() {
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("A̵̙̘̟̓͘͝h̴̦͙͓͌̒͝o̴̢̪̒͌̔r̴̻̦̿̓̈́c̴͍͓̠̿͘͠a̴̢͓͙̓̀͠d̸͕̦͊̚͠ö̵͎̠́̀̕");
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel instruccion = new JLabel("𝐼𝑛𝑡𝑟𝑜𝑑𝑢𝑐𝑒 𝑙𝑎 𝑝𝑎𝑙𝑎𝑏𝑟𝑎 𝑎 𝑎𝑑𝑖𝑣𝑖𝑛𝑎𝑟 (1 𝒂 20 𝒍𝒆𝒕𝒓𝒂𝒔) ");
        instruccion.setAlignmentX(CENTER_ALIGNMENT);

        campoPalabra = new JPasswordField(20);
        campoPalabra.setMaximumSize(new Dimension(260, 30));
        campoPalabra.setAlignmentX(CENTER_ALIGNMENT);
        campoPalabra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comenzarPartida();
            }
        });
        echoOriginal = campoPalabra.getEchoChar();

        chkMostrar = new JCheckBox("ɐɹqɐʅɐd ɹɐɹʇsoꟽ");
        chkMostrar.setAlignmentX(CENTER_ALIGNMENT);
        chkMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                campoPalabra.setEchoChar(chkMostrar.isSelected() ? (char) 0 : echoOriginal);
            }
        });

        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(CENTER_ALIGNMENT);

        JButton btnComenzar = new JButton("𝐂𝐨𝐦𝐞𝐧𝐳𝐚𝐫");
        btnComenzar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comenzarPartida();
            }
        });

        JButton btnSalirInicio = new JButton("𝐒𝐚𝐥𝐢𝐫");
        btnSalirInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);
        panelBotones.add(btnComenzar);
        panelBotones.add(btnSalirInicio);

        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(instruccion);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(campoPalabra);
        contenido.add(chkMostrar);
        contenido.add(lblError);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(panelBotones);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(contenido);
        return panel;
    }

    private void comenzarPartida() {
        String palabra = new String(campoPalabra.getPassword()).trim().toUpperCase();
        String error = Logica.validarPalabra(palabra);

        if (error != null) {
            lblError.setText(error);
            campoPalabra.requestFocusInWindow();
            return;
        }

        logica.iniciar(palabra);
        lblError.setText(" ");
        habilitarTeclado(true);
        actualizarPantalla();
        instalarAtajos();
        cards.show(contenedor, CARD_JUEGO);
    }

    private void mostrarInicio() {
        quitarAtajos();
        logica.reiniciar();

        campoPalabra.setText("");
        chkMostrar.setSelected(false);
        campoPalabra.setEchoChar(echoOriginal);
        lblError.setText(" ");
        habilitarTeclado(false);

        cards.show(contenedor, CARD_INICIO);
        campoPalabra.requestFocusInWindow();
    }

    private void enfocarCampo() {
        campoPalabra.requestFocusInWindow();
    }

    // =====================================================================
    //  PANTALLA DE JUEGO
    // =====================================================================

    private JPanel crearPanelJuego() {
        panelDibujo = new PanelDibujo();

        lblPalabra = new JLabel("Presiona \"Nueva partida\"", JLabel.CENTER);
        lblPalabra.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        lblPalabra.setAlignmentX(CENTER_ALIGNMENT);

        lblIntentos = new JLabel("Intentos restantes: " + Logica.MAX_FALLOS + "/" + Logica.MAX_FALLOS, JLabel.CENTER);
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
        }

        // ----- Botones de control -----
        JButton btnNueva = new JButton("𝐍𝐮𝐞𝐯𝐚 𝐏𝐚𝐫𝐭𝐢𝐝𝐚");
        btnNueva.setFocusable(false);
        btnNueva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarInicio();
            }
        });

        JButton btnSalir = new JButton("𝐒𝐚𝐥𝐢𝐫");
        btnSalir.setFocusable(false);
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
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
        panelSur.add(Box.createVerticalStrut(8));
        panelSur.add(panelTeclado);
        panelSur.add(panelControl);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panelDibujo, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);
        return panel;
    }

    private void jugarLetra(char letra, JButton boton) {
        if (!logica.activa()) {
            return;
        }

        boton.setEnabled(false); // así no se puede repetir una letra
        logica.comprobarLetra(letra);
        actualizarPantalla();
        verificarFin();
    }

    private void verificarFin() {
        if (!logica.haPerdido() && !logica.haGanado()) {
            return;
        }

        habilitarTeclado(false);
        quitarAtajos();

        String mensaje;
        if (logica.haPerdido()) {
            lblPalabra.setText(logica.palabraConEspacios());
            mensaje = "Perdiste, mejor suerte la próxima: " + logica.getPalabraSecreta();
        } else {
            mensaje = "¡Felicidades Ganaste❗";
        }

        Object[] opciones = {"Continuar", "Salir"};
        int eleccion = JOptionPane.showOptionDialog(
                this,
                "<html>" + mensaje + "<br>¿Deseas continuar o salir?</html>",
                "Fin de la partida",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (eleccion == 0) {
            mostrarInicio();
        } else {
            salir();
        }
    }

    private void actualizarPantalla() {
        lblPalabra.setText(logica.palabraVisible());
        lblIntentos.setText("Intentos restantes: " + logica.getFallosRestantes() + "/" + Logica.MAX_FALLOS);
        lblErradas.setText("Letras erradas: " + logica.getLetrasErradas());
        panelDibujo.setFallos(logica.getFallosCometidos());
    }

    private void habilitarTeclado(boolean habilitado) {
        for (int i = 0; i < botones.length; i++) {
            botones[i].setEnabled(habilitado);
        }
    }

    private void salir() {
        System.exit(0);
    }

    // =====================================================================
    //  ATAJOS DE TECLADO FÍSICO
    //  Se instalan solo durante la partida para no interferir con el campo
    //  de texto de la pantalla de inicio.
    // =====================================================================

    private void instalarAtajos() {
        for (int i = 0; i < LETRAS.length(); i++) {
            final JButton boton = botones[i];
            char letra = LETRAS.charAt(i);
            String nombre = "letra" + i;

            Action accion = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (boton.isEnabled()) {
                        boton.doClick(0);
                    }
                }
            };

            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(Character.toUpperCase(letra)), nombre);
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(Character.toLowerCase(letra)), nombre);
            getRootPane().getActionMap().put(nombre, accion);
        }
    }

    private void quitarAtajos() {
        for (int i = 0; i < LETRAS.length(); i++) {
            char letra = LETRAS.charAt(i);

            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .remove(KeyStroke.getKeyStroke(Character.toUpperCase(letra)));
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .remove(KeyStroke.getKeyStroke(Character.toLowerCase(letra)));
            getRootPane().getActionMap().remove("letra" + i);
        }
    }

    // =====================================================================
    //  LÓGICA DEL JUEGO (sin Swing)
    // =====================================================================

    /**
     * Estado y reglas del Ahorcado. Es la parte que comparten la versión de
     * consola/JOptionPane y la gráfica.
     */
    public static class Logica {

        public static final int MAX_FALLOS = 8;
        public static final int MAX_LETRAS = 20;

        private String palabraSecreta;
        private boolean[] aciertos;
        private int fallosRestantes;
        private final StringBuilder letrasErradas = new StringBuilder();
        private final Set<Character> letrasUsadas = new HashSet<Character>();

        /**
         * Valida la palabra secreta. Devuelve null si es válida o un mensaje
         * de error listo para mostrar.
         */
        public static String validarPalabra(String palabra) {
            if (palabra == null || palabra.length() < 1) {
                return "La palabra no puede estar vacía";
            }
            if (palabra.length() > MAX_LETRAS) {
                return "No se puede tener más de " + MAX_LETRAS + " caracteres";
            }
            if (!palabra.matches("[A-Z\u00D1]+")) {
                return "Solo se permiten letras (sin espacios, números ni tildes)";
            }
            return null;
        }

        /** Reinicia la partida con la palabra indicada (ya validada). */
        public void iniciar(String palabra) {
            reiniciar();
            palabraSecreta = palabra;
            aciertos = new boolean[palabra.length()];
        }

        /** Deja la lógica sin partida activa. */
        public void reiniciar() {
            palabraSecreta = null;
            aciertos = null;
            fallosRestantes = MAX_FALLOS;
            letrasErradas.setLength(0);
            letrasUsadas.clear();
        }

        /** Indica si hay una partida en curso. */
        public boolean activa() {
            return palabraSecreta != null && aciertos != null;
        }

        /**
         * Comprueba una letra: marca los aciertos o descuenta un fallo.
         * Devuelve true si la letra pertenece a la palabra.
         *
         * Ignora letras repetidas y llamadas cuando la partida ya terminó.
         */
        public boolean comprobarLetra(char letra) {
            if (!activa() || haGanado() || haPerdido() || letrasUsadas.contains(letra)) {
                return false;
            }

            letrasUsadas.add(letra);
            boolean acierto = false;

            for (int i = 0; i < palabraSecreta.length(); i++) {
                if (palabraSecreta.charAt(i) == letra) {
                    aciertos[i] = true;
                    acierto = true;
                }
            }

            if (!acierto) {
                fallosRestantes--;
                letrasErradas.append(letra).append(' ');
            }

            return acierto;
        }

        public boolean haGanado() {
            return activa() && contarAciertos() == palabraSecreta.length();
        }

        public boolean haPerdido() {
            return activa() && fallosRestantes <= 0;
        }

        public int contarAciertos() {
            int cantidad = 0;

            for (int i = 0; i < aciertos.length; i++) {
                if (aciertos[i]) {
                    cantidad++;
                }
            }

            return cantidad;
        }

        /** Devuelve la palabra con las letras acertadas y guiones en el resto. */
        public String palabraVisible() {
            if (!activa()) {
                return "";
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < palabraSecreta.length(); i++) {
                sb.append(aciertos[i] ? palabraSecreta.charAt(i) : '_');
                sb.append(' ');
            }

            return sb.toString().trim();
        }

        /** Devuelve la palabra completa separada por espacios (para perder). */
        public String palabraConEspacios() {
            if (!activa()) {
                return "";
            }
            return palabraSecreta.replaceAll("(?<=.)(?=.)", " ");
        }

        public String getPalabraSecreta() {
            return palabraSecreta;
        }

        public int getFallosRestantes() {
            return fallosRestantes;
        }

        public int getFallosCometidos() {
            return MAX_FALLOS - fallosRestantes;
        }

        public String getLetrasErradas() {
            return letrasErradas.toString().trim();
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
                Ahorcado3_0 ventana = new Ahorcado3_0();
                ventana.setVisible(true);
                ventana.enfocarCampo();
            }
        });
    }
}
