package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaElegirTipoUsuario extends JFrame {
    public VentanaElegirTipoUsuario() {
        setTitle("Elegir Tipo de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton trabajadorButton = new JButton("TRABAJADOR");
        estilizarBoton(trabajadorButton);
        trabajadorButton.addActionListener(e -> new VentanaLoginTrabajador());
        panel.add(trabajadorButton, constraints);

        constraints.gridy = 1;
        JButton clienteButton = new JButton("CLIENTE");
        estilizarBoton(clienteButton);
        clienteButton.addActionListener(e -> new VentanaLoginRegistroCliente());
        panel.add(clienteButton, constraints);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void estilizarBoton(JButton button) {
        button.setBackground(new Color(50, 150, 250));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 50));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 170, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 150, 250));
            }
        });
    }

}
