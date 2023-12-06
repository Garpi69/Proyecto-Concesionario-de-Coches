package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class VentanaLoginTrabajador extends JFrame {
    protected JTextField textoLogin;
    protected JPasswordField textoPassword;
    protected JButton botonVerContraseña, botonAceptar, botonCancelar;

    public VentanaLoginTrabajador() {
        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);

        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new GridLayout(2, 2, 10, 10));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        textoLogin = new JTextField();
        textoPassword = new JPasswordField();

        panelLogin.add(new JLabel("Login"));
        panelLogin.add(textoLogin);
        panelLogin.add(new JLabel("Password"));
        panelLogin.add(textoPassword);

        this.add(panelLogin, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

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

        setVisible(true);
    }

    public void Autenticacion() {
        String usuarioIngresado = textoLogin.getText();
        String contrasenaIngresada = new String(textoPassword.getPassword());
        System.out.println("Correcto");
        DAO dao = new DAO();

        boolean correcto = false;
        try {
			correcto = dao.comprobarCredencialesTrabajador(usuarioIngresado, contrasenaIngresada);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (correcto) {
        	VentanaMenuTrabajador ventana = new VentanaMenuTrabajador();
        	ventana.setVisible(true);
        	ventana.setLocationRelativeTo(null);
        }else {
        	JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                    "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
    }


}
