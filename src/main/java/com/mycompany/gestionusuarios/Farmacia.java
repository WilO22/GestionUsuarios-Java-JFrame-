package com.mycompany.gestionusuarios;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Interfaz gráfica moderna para la gestión de usuarios de la farmacia.
 * Diseño tipo web con paleta azul oscuro y verde menta.
 * 
 * @author Wil
 */
public class Farmacia extends JFrame {

    // Colores del tema
    private static final Color COLOR_FONDO = new Color(15, 23, 42); // Azul muy oscuro
    private static final Color COLOR_PANEL = new Color(30, 58, 95); // Azul oscuro
    private static final Color COLOR_ACENTO = new Color(74, 222, 128); // Verde menta
    private static final Color COLOR_TEXTO = new Color(248, 250, 252); // Blanco humo
    private static final Color COLOR_TEXTO_SEC = new Color(148, 163, 184); // Gris azulado
    private static final Color COLOR_BORDE = new Color(51, 65, 85); // Gris oscuro
    private static final Color COLOR_ERROR = new Color(248, 113, 113); // Rojo suave
    private static final Color COLOR_EXITO = new Color(74, 222, 128); // Verde
    private static final Color COLOR_INPUT = new Color(22, 35, 60); // Azul input
    private static final Color COLOR_OSCURO = new Color(15, 23, 42); // Texto oscuro

    // Servicio de usuarios
    private UsuarioService usuarioService;

    // Componentes de la UI
    private JTextArea areaUsuarios;
    private JTextField txtNuevoUsuario;
    private JTextField txtBuscarUsuario;
    private JLabel lblMensaje;
    private JLabel lblContador;

    // Placeholders
    private static final String PH_AGREGAR = "Ingrese nombre del usuario...";
    private static final String PH_BUSCAR = "Ingrese nombre a buscar...";

    public Farmacia() {
        usuarioService = new UsuarioService();
        initUI();
    }

    private void initUI() {
        setTitle("Farmacia - Gestion de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 680);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(750, 550));

        // Panel principal con fondo oscuro
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        mainPanel.add(crearHeader(), BorderLayout.NORTH);

        // Contenido central
        JPanel contenido = new JPanel(new GridLayout(1, 2, 25, 0));
        contenido.setOpaque(false);
        contenido.add(crearPanelLista());
        contenido.add(crearPanelAcciones());
        mainPanel.add(contenido, BorderLayout.CENTER);

        // Footer con mensajes
        mainPanel.add(crearPanelMensajes(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
        actualizarLista();
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Icono + Título principal
        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tituloPanel.setOpaque(false);

        // Icono de farmacia estilizado
        JPanel iconoPanel = new RoundedPanel(15, COLOR_PANEL, COLOR_ACENTO);
        iconoPanel.setPreferredSize(new Dimension(50, 50));
        iconoPanel.setLayout(new GridBagLayout());
        JLabel iconoLabel = new JLabel("+");
        iconoLabel.setFont(new Font("Consolas", Font.BOLD, 28));
        iconoLabel.setForeground(COLOR_ACENTO);
        iconoPanel.add(iconoLabel);

        JLabel titulo = new JLabel("  FARMACIA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(COLOR_TEXTO);

        JLabel tituloParte2 = new JLabel(" - Gestion de Clientes");
        tituloParte2.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        tituloParte2.setForeground(COLOR_TEXTO_SEC);

        tituloPanel.add(iconoPanel);
        tituloPanel.add(titulo);
        tituloPanel.add(tituloParte2);

        // Panel izquierdo con título y subtítulo
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        textos.add(tituloPanel);
        textos.add(Box.createVerticalStrut(8));

        JLabel subtitulo = new JLabel("Sistema de registro de usuarios | Capacidad maxima: 5 clientes");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(COLOR_TEXTO_SEC);
        textos.add(subtitulo);

        header.add(textos, BorderLayout.WEST);

        // Contador de usuarios estilizado
        JPanel contadorPanel = new RoundedPanel(15, COLOR_PANEL, COLOR_BORDE);
        contadorPanel.setLayout(new BoxLayout(contadorPanel, BoxLayout.Y_AXIS));
        contadorPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        lblContador = new JLabel("0 / 5");
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblContador.setForeground(COLOR_ACENTO);
        lblContador.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblContadorTxt = new JLabel("usuarios");
        lblContadorTxt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblContadorTxt.setForeground(COLOR_TEXTO_SEC);
        lblContadorTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        contadorPanel.add(lblContador);
        contadorPanel.add(lblContadorTxt);

        header.add(contadorPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel crearPanelLista() {
        RoundedPanel panel = new RoundedPanel(20, COLOR_PANEL, COLOR_BORDE);
        panel.setLayout(new BorderLayout(0, 15));
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Título del panel
        JLabel lblTitulo = new JLabel("LISTA DE USUARIOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(COLOR_ACENTO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Área de usuarios con borde redondeado
        areaUsuarios = new JTextArea();
        areaUsuarios.setEditable(false);
        areaUsuarios.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaUsuarios.setBackground(COLOR_INPUT);
        areaUsuarios.setForeground(COLOR_TEXTO);
        areaUsuarios.setBorder(new EmptyBorder(15, 15, 15, 15));
        areaUsuarios.setLineWrap(true);
        areaUsuarios.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaUsuarios);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        scroll.getViewport().setBackground(COLOR_INPUT);

        panel.add(scroll, BorderLayout.CENTER);

        // Botón para refrescar lista
        RoundedButton btnRefrescar = new RoundedButton("ACTUALIZAR LISTA", COLOR_BORDE, COLOR_TEXTO, 12);
        btnRefrescar.addActionListener(e -> {
            actualizarLista();
            mostrarMensaje("[OK] Lista actualizada correctamente", true);
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        btnPanel.add(btnRefrescar);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAcciones() {
        RoundedPanel panel = new RoundedPanel(20, COLOR_PANEL, COLOR_BORDE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Título del panel
        JLabel lblTitulo = new JLabel("ACCIONES");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(COLOR_ACENTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // === Sección Agregar Usuario ===
        JPanel seccionAgregar = crearSeccion("AGREGAR NUEVO USUARIO");

        txtNuevoUsuario = crearCampoTexto(PH_AGREGAR);
        txtNuevoUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedButton btnAgregar = new RoundedButton("+ AGREGAR USUARIO", COLOR_ACENTO, COLOR_OSCURO, 12);
        btnAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAgregar.addActionListener(e -> agregarUsuario());

        // === Sección Buscar Usuario ===
        JPanel seccionBuscar = crearSeccion("BUSCAR USUARIO");

        txtBuscarUsuario = crearCampoTexto(PH_BUSCAR);
        txtBuscarUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedButton btnBuscar = new RoundedButton("? BUSCAR USUARIO", COLOR_ACENTO, COLOR_OSCURO, 12);
        btnBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBuscar.addActionListener(e -> buscarUsuario());

        // Agregar componentes con espaciado
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(25));

        panel.add(seccionAgregar);
        panel.add(Box.createVerticalStrut(12));
        panel.add(txtNuevoUsuario);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnAgregar);

        panel.add(Box.createVerticalStrut(40));

        panel.add(seccionBuscar);
        panel.add(Box.createVerticalStrut(12));
        panel.add(txtBuscarUsuario);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnBuscar);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel crearSeccion(String titulo) {
        JPanel seccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        seccion.setOpaque(false);
        seccion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(COLOR_TEXTO);

        seccion.add(lbl);
        return seccion;
    }

    private JPanel crearPanelMensajes() {
        RoundedPanel panel = new RoundedPanel(15, COLOR_PANEL, COLOR_BORDE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(18, 25, 18, 25));

        lblMensaje = new JLabel(">> Bienvenido al sistema de gestion de usuarios de la farmacia");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMensaje.setForeground(COLOR_TEXTO);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblMensaje, BorderLayout.CENTER);

        return panel;
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(COLOR_INPUT);
        campo.setForeground(COLOR_TEXTO_SEC);
        campo.setCaretColor(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                new EmptyBorder(14, 18, 14, 18)));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        campo.setOpaque(true);

        // Placeholder inicial
        campo.setText(placeholder);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_ACENTO, 2),
                        new EmptyBorder(13, 17, 13, 17)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COLOR_TEXTO_SEC);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE, 1),
                        new EmptyBorder(14, 18, 14, 18)));
            }
        });

        return campo;
    }

    private void agregarUsuario() {
        String nombre = txtNuevoUsuario.getText().trim();

        if (nombre.isEmpty() || nombre.equals(PH_AGREGAR)) {
            mostrarMensaje("[!] Por favor ingrese un nombre de usuario", false);
            return;
        }

        if (usuarioService.agregarUsuario(nombre)) {
            mostrarMensaje("[OK] Usuario '" + nombre + "' agregado exitosamente", true);
            txtNuevoUsuario.setText(PH_AGREGAR);
            txtNuevoUsuario.setForeground(COLOR_TEXTO_SEC);
            actualizarLista();
        } else {
            if (!usuarioService.hayEspacio()) {
                mostrarMensaje("[X] No hay espacio disponible. Capacidad maxima: 5 usuarios", false);
            } else {
                mostrarMensaje("[X] Error al agregar el usuario", false);
            }
        }
    }

    private void buscarUsuario() {
        String nombre = txtBuscarUsuario.getText().trim();

        if (nombre.isEmpty() || nombre.equals(PH_BUSCAR)) {
            mostrarMensaje("[!] Por favor ingrese un nombre para buscar", false);
            return;
        }

        if (usuarioService.buscarUsuario(nombre)) {
            mostrarMensaje("[OK] Usuario '" + nombre + "' ENCONTRADO en el sistema", true);
        } else {
            mostrarMensaje("[X] Usuario '" + nombre + "' NO existe en el sistema", false);
        }
    }

    private void actualizarLista() {
        String[] usuarios = usuarioService.listarUsuarios();
        StringBuilder sb = new StringBuilder();

        if (usuarios.length == 0) {
            sb.append("\n");
            sb.append("    No hay usuarios registrados.\n\n");
            sb.append("    Utiliza el panel de acciones\n");
            sb.append("    para agregar nuevos usuarios.\n");
        } else {
            sb.append("\n");
            for (int i = 0; i < usuarios.length; i++) {
                sb.append("    ").append(i + 1).append(". ").append(usuarios[i]).append("\n");
            }
        }

        areaUsuarios.setText(sb.toString());
        lblContador.setText(usuarioService.getCantidadUsuarios() + " / " +
                usuarioService.getCapacidadMaxima());
    }

    private void mostrarMensaje(String mensaje, boolean exito) {
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(exito ? COLOR_EXITO : COLOR_ERROR);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Farmacia().setVisible(true);
        });
    }

    /**
     * Panel con bordes redondeados.
     */
    static class RoundedPanel extends JPanel {
        private int radius;
        private Color bgColor;
        private Color borderColor;

        public RoundedPanel(int radius, Color bgColor, Color borderColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            this.borderColor = borderColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo redondeado
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));

            // Borde redondeado
            g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
