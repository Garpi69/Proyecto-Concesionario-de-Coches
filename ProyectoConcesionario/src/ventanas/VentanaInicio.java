package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class VentanaInicio extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton botonLogin;
	protected JButton botonSignUp;
	
	public VentanaInicio() {
		
		botonLogin = new JButton("Iniciar Sesión");
		botonSignUp = new JButton("Registrar cuenta");
		
		botonLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		botonSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JPanel panelBotones = new JPanel();
		
		
		
		panelBotones.add(botonLogin);
		panelBotones.add(botonSignUp);
		
		this.add(panelBotones, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Inicio");
		this.setSize(800, 600);
		this.setVisible(true);
	}
	
}
