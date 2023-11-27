package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLoginRegistroCliente extends JFrame {
    public VentanaLoginRegistroCliente() {
        setTitle("Login o Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        DAO dao = new DAO();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaLoginCliente();
                JOptionPane.showMessageDialog(null, "Has seleccionado Iniciar Sesión");
                // Aquí puedes abrir la ventana de inicio de sesión
            }
        });
        panel.add(loginButton);

        JButton registroButton = new JButton("Registrarse");
        registroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para el registro
                new VentanaRegistroCliente();
            	JOptionPane.showMessageDialog(null, "Has seleccionado Registrarse");
                // Aquí puedes abrir la ventana de registro
            }
        });
        panel.add(registroButton);

        add(panel);
    }

  
}
