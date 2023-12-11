package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaMenuCliente extends JFrame {
    public VentanaMenuCliente() {
        setTitle("Compra o Venta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        
        JButton verOfertasButton = new JButton("Mis vehiculos en venta");
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // new VentanaMenuClienteVerOfertas();


            }
        });
        panel.add(verOfertasButton);
        
        // Agregado: Espaciado entre panel y ventana
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agregado: Borde

        wrapperPanel.add(panel, BorderLayout.CENTER);
        add(wrapperPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
		 new VentanaMenuCliente();

	}


}
