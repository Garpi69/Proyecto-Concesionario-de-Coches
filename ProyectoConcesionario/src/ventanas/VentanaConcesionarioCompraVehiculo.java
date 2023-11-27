package ventanas;
import javax.swing.*;

import clases.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaConcesionarioCompraVehiculo{
	
DAO dao = new DAO();
public void abrirVentanaAgregarVehiculo() {
    JFrame ventanaAgregarVehiculo = new JFrame("Agregar Vehículo");
    ventanaAgregarVehiculo.setSize(300, 200);
    ventanaAgregarVehiculo.setLocationRelativeTo(null);
    ventanaAgregarVehiculo.setLayout(new GridLayout(4, 1));

    JButton cocheNuevoButton = new JButton("Coche Nuevo");
    JButton cocheSegundaManoButton = new JButton("Coche Segunda Mano");
    JButton motoNuevaButton = new JButton("Moto Nueva");
    JButton motoSegundaManoButton = new JButton("Moto Segunda Mano");
    
    cocheNuevoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Acciones al presionar "Coche Nuevo"
        	
            JOptionPane.showMessageDialog(null, "Se seleccionó Coche Nuevo");
           //PEDIR DATOS COCHE NUEVO Y AÑADIR A BD TABLA COCHE
          
        }
    });

    cocheSegundaManoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Acciones al presionar "Coche Segunda Mano"
            JOptionPane.showMessageDialog(null, "Se seleccionó Coche Segunda Mano");
           //PEDIR DATOS COCHE SEGUNDA MANO Y AÑADIR A BD TABLA COCHESEGUNDAMANO
        }
    });

    motoNuevaButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Acciones al presionar "Moto Nueva"
            JOptionPane.showMessageDialog(null, "Se seleccionó Moto Nueva");
            //PEDIR DATOS MOTO Y AÑADIR A TABLA BD MOTO
        }
    });

    motoSegundaManoButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Acciones al presionar "Moto Segunda Mano"
            JOptionPane.showMessageDialog(null, "Se seleccionó Moto Segunda Mano");
           //PEDIR DATOS MOTO Y AÑADIR A TABLA BD MOTOSEGUNDAMANO
        }
    });

    ventanaAgregarVehiculo.add(cocheNuevoButton);
    ventanaAgregarVehiculo.add(cocheSegundaManoButton);
    ventanaAgregarVehiculo.add(motoNuevaButton);
    ventanaAgregarVehiculo.add(motoSegundaManoButton);

    ventanaAgregarVehiculo.setVisible(true);
}
}
