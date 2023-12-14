package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class VentanaOferta extends JFrame{
	DAO dao = new DAO();
	public VentanaOferta(int idVehiculo,String usuario) {
		 setTitle("Oferta");
	        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        setSize(350, 200);
	        setLocationRelativeTo(null);

	        JPanel panelOferta = new JPanel();
	        panelOferta.setLayout(new GridLayout(2, 2, 10, 10));
	        panelOferta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	        JTextField textoOferta = new JTextField();
	        

	        panelOferta.add(new JLabel("Oferta"));
	        panelOferta.add(textoOferta);
	       

	        this.add(panelOferta, BorderLayout.CENTER);

	        JPanel panelBotones = new JPanel();
	        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

	        JButton botonEnviar = new JButton("Enviar");
	       

	        panelBotones.add(botonEnviar);
	       
	       
	        botonEnviar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	int oferta =Integer.parseInt(textoOferta.getText());
	            	
	            	int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea enviar una oferta de "+oferta+"€ ?",
	                        "Confirmación de Oferta", JOptionPane.YES_NO_OPTION);
	            	if (opcion == JOptionPane.YES_OPTION) {
	            		JOptionPane.showConfirmDialog(null, "Oferta enviada correctamente");
	            		dao.agregarOfertaAVehiculo(idVehiculo,oferta,usuario);
	            	}
	            }
	        });

	        

	        this.add(panelBotones, BorderLayout.SOUTH);

	        setVisible(true);
	    }

	}

