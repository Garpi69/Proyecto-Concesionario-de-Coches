package ventanas;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenuClienteCompraVehiculo extends JFrame {
    public VentanaMenuClienteCompraVehiculo() {
        setTitle("Menú Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton comprarCocheButton = new JButton("Comprar Coche");
        comprarCocheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaClienteCompraCoche();
                JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Coche");
                
            }
        });
        panel.add(comprarCocheButton);

        JButton comprarCocheSegundaManoButton = new JButton("Comprar Coche Segunda Mano");
        comprarCocheSegundaManoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                new VentanaClienteCompraCocheSegundaMano();
            	JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Coche Segunda Mano");
               
            }});
        panel.add(comprarCocheSegundaManoButton);

        JButton comprarMotoButton = new JButton("Comprar Moto");
        comprarMotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	new VentanaClienteCompraMoto();
                JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Moto");
                
            }
        });
        panel.add(comprarMotoButton);

        JButton comprarMotoSegundaManoButton = new JButton("Comprar Moto Segunda Mano");
        comprarMotoSegundaManoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	new VentanaClienteCompraMotoSegundaMano();
                JOptionPane.showMessageDialog(null, "Has seleccionado Comprar Moto Segunda Mano");
                
            }
        });
        panel.add(comprarMotoSegundaManoButton);

       
        add(panel);
    
    
    }
}