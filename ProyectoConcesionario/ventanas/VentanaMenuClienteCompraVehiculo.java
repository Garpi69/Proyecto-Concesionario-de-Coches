package ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaMenuClienteCompraVehiculo extends JFrame {
    public VentanaMenuClienteCompraVehiculo() {
        setTitle("Menú Cliente - Compra de Vehículo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        });
        panel.add(comprarMotoSegundaManoButton);
        setVisible(true);
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
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#6573C3"));
            }

            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#3F51B5"));
            }
        });
    }



}
