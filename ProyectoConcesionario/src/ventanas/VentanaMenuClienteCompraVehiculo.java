package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenuClienteCompraVehiculo extends JFrame {
    public VentanaMenuClienteCompraVehiculo() {
        setTitle("Menú Cliente - Compra de Vehículo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton comprarCocheButton = new JButton("Comprar Coche");
        estilizarBoton(comprarCocheButton);
        comprarCocheButton.addActionListener(e -> {
            new VentanaClienteCompraCoche();
            JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Coche");
        });
        panel.add(comprarCocheButton);

        JButton comprarCocheSegundaManoButton = new JButton("Comprar Coche Segunda Mano");
        estilizarBoton(comprarCocheSegundaManoButton);
        comprarCocheSegundaManoButton.addActionListener(e -> {
            new VentanaClienteCompraCocheSegundaMano();
            JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Coche Segunda Mano");
        });
        panel.add(comprarCocheSegundaManoButton);

        JButton comprarMotoButton = new JButton("Comprar Moto");
        estilizarBoton(comprarMotoButton);
        comprarMotoButton.addActionListener(e -> {
            new VentanaClienteCompraMoto();
            JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Moto");
        });
        panel.add(comprarMotoButton);

        JButton comprarMotoSegundaManoButton = new JButton("Comprar Moto Segunda Mano");
        estilizarBoton(comprarMotoSegundaManoButton);
        comprarMotoSegundaManoButton.addActionListener(e -> {
            new VentanaClienteCompraMotoSegundaMano();
            JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Moto Segunda Mano");
        });
        panel.add(comprarMotoSegundaManoButton);

        add(panel);
        getContentPane().setBackground(Color.WHITE);
    }

    private void estilizarBoton(JButton button) {
        button.setPreferredSize(new Dimension(450, 50));
        button.setBackground(Color.decode("#3F51B5"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#6573C3"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#3F51B5"));
            }
        });
    }

    
    
}
