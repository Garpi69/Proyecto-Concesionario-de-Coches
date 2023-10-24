package ventanas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JTextField textoLogin;
	protected JTextField textoPassword;
	protected JButton botonAceptar;
	protected JButton botonCancelar;
	
	public VentanaLogin() {
		
		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new GridLayout(2, 2));
		
		textoLogin = new JTextField(20);
		textoPassword = new JTextField(20);
		
		panelLogin.add(new JLabel("Login"));
		panelLogin.add(textoLogin);
		panelLogin.add(new JLabel("Password"));
		panelLogin.add(textoPassword);
		
		this.add(panelLogin, BorderLayout.CENTER);
		
		botonAceptar = new JButton("Aceptar");
		botonCancelar = new JButton("Cancelar");
		
		botonAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		JPanel panelBotones = new JPanel();
		
		panelBotones.add(botonAceptar);
		panelBotones.add(botonCancelar);
		
		this.add(panelBotones, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Login");
		this.pack();
		this.setVisible(true);
		
	}
}
