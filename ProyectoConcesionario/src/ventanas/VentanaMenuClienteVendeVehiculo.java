package ventanas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class VentanaMenuClienteVendeVehiculo extends JFrame {
    public VentanaMenuClienteVendeVehiculo() {
        setTitle("Menú Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton VenderCocheButton = new JButton("Vender Coche");
        VenderCocheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeCoche();
                JOptionPane.showMessageDialog(null, "Has seleccionado Vender Coche");
             
            }
        });
        panel.add(VenderCocheButton);

       

        JButton VenderMotoButton = new JButton("Vender Moto");
        VenderMotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	new VentanaMenuClienteVendeMoto();
                JOptionPane.showMessageDialog(null, "Has seleccionado Vender Moto");
                
            }
        });
        panel.add(VenderMotoButton);

       

       
        add(panel);
    }
}
