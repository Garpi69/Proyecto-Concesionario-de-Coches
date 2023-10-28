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
				new VentanaLogin();
				
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
		
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(botonLogin, gbc);

        gbc.gridy = 1;
        this.add(botonSignUp, gbc);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Inicio");
		this.setSize(400, 300);
		this.setVisible(true);
	}
	
}
