package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

public class VentanaLoginCliente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JTextField textoLogin;
	protected JPasswordField textoPassword;
	protected JButton botonVerContraseña;
	protected JButton botonAceptar;
	protected JButton botonCancelar;
	
	public VentanaLoginCliente() {
		
		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new GridLayout(2, 2));
		
		textoLogin = new JTextField(20);
		textoPassword = new JPasswordField(20);
		
		panelLogin.add(new JLabel("Login"));
		panelLogin.add(textoLogin);
		panelLogin.add(new JLabel("Password"));
		panelLogin.add(textoPassword);
		
		this.add(panelLogin, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel();
		
		botonAceptar = new JButton("Aceptar");
		botonCancelar = new JButton("Cancelar");
		botonVerContraseña = new JButton("Ver Contraseña");
		
		panelBotones.add(botonAceptar);
		panelBotones.add(botonCancelar);
		panelBotones.add(botonVerContraseña);
		
		botonAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Autenticacion();
				
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		botonVerContraseña.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textoPassword.getEchoChar() == 0) {
					textoPassword.setEchoChar('*');
				} else {
					textoPassword.setEchoChar((char) 0);
				}
				
			}
		});
		
		this.add(panelBotones, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Login");
		this.pack();
		this.setVisible(true);
		  this.setLocationRelativeTo(null);
		
	
}
public void Autenticacion() {
  
    String usuarioIngresado = textoLogin.getText();
    String contrasenaIngresada = new String(textoPassword.getPassword());

    DAO dao = new DAO();
    try {
		if (dao.comprobarCredencialesCliente(usuarioIngresado, contrasenaIngresada)) {
			new VentanaMenuCliente();
		}
		else {
			JOptionPane.showMessageDialog(null,"ERROR","Usuario y/o contraseña no reconocidos", JOptionPane.PLAIN_MESSAGE);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
