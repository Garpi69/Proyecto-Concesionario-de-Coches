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

public class VentanaLoginCliente extends JFrame {
    protected JTextField textoLogin;
    protected JPasswordField textoPassword;
    protected JButton botonVerContraseña, botonAceptar, botonCancelar;

    public VentanaLoginCliente() {
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

        this.setVisible(true);
    }

    public void Autenticacion() {
        String usuarioIngresado = textoLogin.getText();
        String contrasenaIngresada = new String(textoPassword.getPassword());

        DAO dao = new DAO();
        try {
            if (dao.comprobarCredencialesCliente(usuarioIngresado, contrasenaIngresada)) {
                new VentanaMenuCliente();
            } else {
                JOptionPane.showMessageDialog(null, "ERROR", "Usuario y/o contraseña no reconocidos",
                        JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}