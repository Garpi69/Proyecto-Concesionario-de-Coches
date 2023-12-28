package ventanas;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaMenuCliente extends JFrame {
	 public VentanaMenuCliente() {
	        setTitle("Compra o Venta");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(300, 200);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(2, 1, 10, 10)); // Espaciado entre componentes

	        JButton comprarButton = new JButton("Comprar");
	        estilizarBoton(comprarButton);
	        comprarButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaMenuClienteCompraVehiculo();
	            }
	        });
	        panel.add(comprarButton);

	        JButton venderButton = new JButton("Vender");
	        estilizarBoton(venderButton);
	        venderButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaMenuClienteVendeVehiculo();
	            }
	        });
	        panel.add(venderButton);

	        JButton verOfertasButton = new JButton("Mis ofertas");
	        estilizarBoton(verOfertasButton);
	        verOfertasButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaMenuClienteVerOfertas();
	            }
	        });
	        panel.add(verOfertasButton);

	        // Espaciado entre panel y ventana
	        JPanel wrapperPanel = new JPanel(new BorderLayout());
	        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Borde

	        wrapperPanel.add(panel, BorderLayout.CENTER);
	        add(wrapperPanel);
	        setVisible(true);
	    }

	 private void estilizarBoton(JButton button) {
	        button.setPreferredSize(new Dimension(250, 80));
	        button.setBackground(Color.decode("#3F51B5"));
	        button.setForeground(Color.WHITE);
	        button.setFont(new Font("Arial", Font.BOLD, 16)); // Modifica el tamaño de la fuente
	        button.setFocusPainted(false);
	        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        button.setOpaque(true);

	        button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            }
	        });

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



