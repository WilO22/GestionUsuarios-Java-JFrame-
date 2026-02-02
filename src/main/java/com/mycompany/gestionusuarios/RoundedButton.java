package com.mycompany.gestionusuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Botón personalizado con bordes redondeados y colores personalizados.
 * Evita problemas con el Look and Feel del sistema.
 * 
 * @author Wil
 */
public class RoundedButton extends JButton {

    private Color backgroundColor;
    private Color hoverColor;
    private Color textColor;
    private int radius;
    private boolean isHovering = false;

    public RoundedButton(String text, Color bgColor, Color txtColor, int radius) {
        super(text);
        this.backgroundColor = bgColor;
        this.hoverColor = bgColor.brighter();
        this.textColor = txtColor;
        this.radius = radius;

        // Desactivar renderizado por defecto
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                isHovering = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                isHovering = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Pintar fondo redondeado
        if (isHovering) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(backgroundColor);
        }
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        // Pintar texto centrado
        g2.setColor(textColor);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 3;
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int width = fm.stringWidth(getText()) + 50;
        int height = fm.getHeight() + 24;
        return new Dimension(Math.max(width, 180), Math.max(height, 42));
    }
}
