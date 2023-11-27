package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenuCliente extends JFrame {
    public VentanaMenuCliente() {
        setTitle("Compra o Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteCompraVehiculo();
                JOptionPane.showMessageDialog(null, "Has seleccionado Comprar");
                // Puedes abrir la ventana de compra aquí
            }
        });
        panel.add(comprarButton);

        JButton venderButton = new JButton("Vender");
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeVehiculo();
                JOptionPane.showMessageDialog(null, "Has seleccionado Vender");
                // Puedes abrir la ventana de venta aquí
            }
        });
        panel.add(venderButton);

        add(panel);
    }

   
}

