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
        panel.setLayout(new GridLayout(2, 1, 10, 10)); // Agregado: Espaciado entre componentes

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteCompraVehiculo();
               
              
            }
        });
        panel.add(comprarButton);

        JButton venderButton = new JButton("Vender");
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeVehiculo();
              
                
            }
        });
        panel.add(venderButton);

        // Agregado: Espaciado entre panel y ventana
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agregado: Borde

        wrapperPanel.add(panel, BorderLayout.CENTER);
        add(wrapperPanel);
    }

    public static void main(String[] args) {
        new VentanaMenuCliente().setVisible(true);
    }
}
