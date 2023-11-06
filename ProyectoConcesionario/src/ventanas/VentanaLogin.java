package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class VentanaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JTextField textoLogin;
	protected JPasswordField textoPassword;
	protected JButton botonVerContraseña;
	protected JButton botonAceptar;
	protected JButton botonCancelar;
	
	public VentanaLogin() {
		
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

   
    String rutaCompleta = System.getProperty("user.dir") + "/src/usuarios.txt";

    try {
        
        BufferedReader lector = new BufferedReader(new FileReader(rutaCompleta));

        String linea;
        boolean usuarioAutenticado = false;

        while ((linea = lector.readLine()) != null) {
            String[] datosUsuario = linea.split(","); 

            if ( usuarioIngresado.equals(datosUsuario[0]) && contrasenaIngresada.equals(datosUsuario[1])) {
                usuarioAutenticado = true;
                break;
            }
        }

        lector.close();

        if (usuarioAutenticado) {
            new VentanaMenu();
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecto", "Error ", JOptionPane.ERROR_MESSAGE);
        }

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al autenticar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
