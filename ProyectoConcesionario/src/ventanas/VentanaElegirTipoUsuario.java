package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaElegirTipoUsuario extends JFrame {
    public VentanaElegirTipoUsuario() {
        setTitle("Elegir Tipo de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton trabajadorButton = new JButton("TRABAJADOR");
        trabajadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		new VentanaLoginTrabajador();
                JOptionPane.showMessageDialog(null, "Has elegido ser TRABAJADOR");
            }
        });
        panel.add(trabajadorButton);

        JButton clienteButton = new JButton("CLIENTE");
        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                	new VentanaLoginRegistroCliente();
                JOptionPane.showMessageDialog(null, "Has elegido ser CLIENTE");
            }
        });
        panel.add(clienteButton);

        add(panel);
    }

   
}
