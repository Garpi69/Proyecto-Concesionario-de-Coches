package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import clases.Cliente;

public class VentanaAñadirCliente extends JFrame {
    private DAO dao = new DAO();
    protected JTextField textoUsuario, textoEmail, textoDni, textoNombre, textoApellidos, textoNumTarjeta;
    protected JPasswordField textoPassword, textoRepetirPassword;
    protected JButton botonSignUp, botonCancelar;

    public Cliente VentanaAñadirCliente() {
        setTitle("Registro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panelSignUp = new JPanel();
        panelSignUp.setLayout(new GridLayout(9, 2, 10, 10));
        panelSignUp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        textoUsuario = new JTextField();
        textoPassword = new JPasswordField();
        textoRepetirPassword = new JPasswordField();
        textoEmail = new JTextField();
        textoDni = new JTextField();
        textoNombre = new JTextField();
        textoApellidos = new JTextField();
        textoNumTarjeta = new JTextField();

        panelSignUp.add(new JLabel("Nombre de Usuario:"));
        panelSignUp.add(textoUsuario);
        panelSignUp.add(new JLabel("Contraseña:"));
        panelSignUp.add(textoPassword);
        panelSignUp.add(new JLabel("Repita la contraseña:"));
        panelSignUp.add(textoRepetirPassword);
        panelSignUp.add(new JLabel("Email:"));
        panelSignUp.add(textoEmail);
        panelSignUp.add(new JLabel("DNI:"));
        panelSignUp.add(textoDni);
        panelSignUp.add(new JLabel("Nombre:"));
        panelSignUp.add(textoNombre);
        panelSignUp.add(new JLabel("Apellidos:"));
        panelSignUp.add(textoApellidos);
        panelSignUp.add(new JLabel("Número de tarjeta:"));
        panelSignUp.add(textoNumTarjeta);

        this.add(panelSignUp, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        botonSignUp = new JButton("Registra");
        botonCancelar = new JButton("Cancelar");

        panelBotones.add(botonSignUp);
        panelBotones.add(botonCancelar);
        Cliente cliente = new Cliente();
        boolean crear = false;
        botonSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = crearObjetoCliente();
               

				JOptionPane.showMessageDialog(null, "Informacion Guardada", "Guardado", JOptionPane.PLAIN_MESSAGE);
				dispose();
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(panelBotones, BorderLayout.SOUTH);
       
        this.setVisible(true);
		return cliente;
		
		
    }
    public Cliente crearObjetoCliente() {
    	 String nombreUsuario = textoUsuario.getText();
         String contrasena = new String(textoPassword.getPassword());
         String email = textoEmail.getText();
         String dni = textoDni.getText();
         String nombre = textoNombre.getText();
         String apellidos = textoApellidos.getText();
         long numTarjeta = Long.parseLong(textoNumTarjeta.getText());

         Cliente cliente = new Cliente(nombreUsuario, contrasena, email, dni, nombre, apellidos, numTarjeta);
         return cliente;
    }
    	
    
   

    
}
