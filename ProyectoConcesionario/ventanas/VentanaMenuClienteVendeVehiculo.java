package ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaMenuClienteVendeVehiculo extends JFrame {
    public VentanaMenuClienteVendeVehiculo() {
        setTitle("Menú Cliente - Venta de Vehículo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton venderCocheButton = new JButton("Vender Coche");
        estilizarBoton(venderCocheButton);
        venderCocheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeCoche();
                
            }
        });
        panel.add(venderCocheButton);

        JButton venderMotoButton = new JButton("Vender Moto");
        estilizarBoton(venderMotoButton);
        venderMotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeMoto();
               
            }
        });
        panel.add(venderMotoButton);

        setVisible(true);
        add(panel);
    }

    private void estilizarBoton(JButton button) {
        button.setPreferredSize(new Dimension(250, 80));
        button.setBackground(Color.decode("#3F51B5"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
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
