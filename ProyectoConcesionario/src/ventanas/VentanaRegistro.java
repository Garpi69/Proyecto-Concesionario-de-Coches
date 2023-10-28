package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.*;

public class VentanaRegistro extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JTextField textoUsuario;
	protected JTextField textoPassword;
	protected JTextField textoRepetirPassword;
	protected JTextField textoEmail;
	protected JTextField textoDni;
	protected JTextField textoNombre;
	protected JTextField textoApellidos;
	protected LocalDate textofecha;
	protected JTextField textoNumTarjeta;
	
	protected JButton botonSignUp;
	protected JButton botonCancelar;
	
	public VentanaRegistro() {
		
		JPanel panelSignUp = new JPanel();
		panelSignUp.setLayout(new GridLayout(9, 2));
		
		textoUsuario = new JTextField(20);
		textoPassword = new JTextField(20);
		textoRepetirPassword = new JTextField(20);
		textoEmail = new JTextField(20);
		textoDni = new JTextField(9);
		textoNombre = new JTextField(20);
		textoApellidos = new JTextField(20);
		textoNumTarjeta = new JTextField(16);
		
		panelSignUp.add(new JLabel("Nombre de Usuario:"));
		panelSignUp.add(textoUsuario);
		panelSignUp.add(new JLabel("Contraseña:"));
		panelSignUp.add(textoPassword);
		panelSignUp.add(new JLabel("Repita la contraseña:"));
		panelSignUp.add(textoRepetirPassword);
		panelSignUp.add(new JLabel ("Email:"));
		panelSignUp.add(textoEmail);
		panelSignUp.add(new JLabel("DNI:"));
		panelSignUp.add(textoDni);
		panelSignUp.add(new JLabel("Nombre:"));
		panelSignUp.add(textoNombre);
		panelSignUp.add(new JLabel("Apellidos:"));
		panelSignUp.add(textoApellidos);
		panelSignUp.add(new JLabel("Fecha de nacimiento:"));
		//fecha;
		panelSignUp.add(new JLabel("Número de tarjeta:"));
		panelSignUp.add(textoNumTarjeta);
		
		this.add(panelSignUp, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel();
		
		botonSignUp = new JButton("Registrarme");
		botonCancelar = new JButton("Cancelar");
		
		panelBotones.add(botonSignUp);
		panelBotones.add(botonCancelar);
		
		botonSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		this.add(panelBotones, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Registro");
		this.pack();
		this.setVisible(true);
	}
	
}
