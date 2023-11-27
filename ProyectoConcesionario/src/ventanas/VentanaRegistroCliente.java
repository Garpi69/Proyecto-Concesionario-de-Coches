package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.*;
import clases.Cliente;


public class VentanaRegistroCliente extends JFrame{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private DAO dao = new DAO();
	protected JTextField textoUsuario;
	protected JTextField textoPassword;
	protected JTextField textoRepetirPassword;
	protected JTextField textoEmail;
	protected JTextField textoDni;
	protected JTextField textoNombre;
	protected JTextField textoApellidos;

	protected JTextField textoNumTarjeta;
	
	protected JButton botonSignUp;
	protected JButton botonCancelar;
	
	public VentanaRegistroCliente() {
		
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
				try {
					guardarPersonaEnArchivo();
					new VentanaLoginCliente();
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"ERROR","No se ha podido guardar el usuario", JOptionPane.PLAIN_MESSAGE);
					e1.printStackTrace();
				}
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
		  this.setLocationRelativeTo(null);
	}
	

	public void guardarPersonaEnArchivo() throws SQLException {
	   
	    String nombreUsuario = textoUsuario.getText();
	    String contrasena = textoPassword.getText();
	    String email = textoEmail.getText();
	    String dni = textoDni.getText();
	    String nombre = textoNombre.getText();
	    String apellidos = textoApellidos.getText();
	    long numTarjeta = Integer.parseInt(textoNumTarjeta.getText());
	   
	    Cliente cliente = new Cliente(nombreUsuario,contrasena,email,dni,nombre,apellidos,numTarjeta);
	  
	    dao.agregarCliente(cliente);
		
      
		JOptionPane.showMessageDialog(null,"Informacion Guardada","Guardado", JOptionPane.PLAIN_MESSAGE);
	}
}
