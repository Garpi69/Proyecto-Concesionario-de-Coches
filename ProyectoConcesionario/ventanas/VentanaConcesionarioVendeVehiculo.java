package ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clases.Cliente;

public class VentanaConcesionarioVendeVehiculo {

    DAO dao = new DAO();

    public void abrirVentanaAgregarVehiculo() {
        JFrame ventanaAgregarVehiculo = new JFrame("Agregar Venta de Vehículo");
        ventanaAgregarVehiculo.setSize(300, 200);
        ventanaAgregarVehiculo.setLocationRelativeTo(null);
        ventanaAgregarVehiculo.setLayout(new GridLayout(4, 1));

        JButton cocheNuevoButton = new JButton("Coche Nuevo");
        JButton cocheSegundaManoButton = new JButton("Coche Segunda Mano");
        JButton motoNuevaButton = new JButton("Moto Nueva");
        JButton motoSegundaManoButton = new JButton("Moto Segunda Mano");
        //Cliente cliente = new Cliente();
     // ...

        cocheNuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones al presionar "Coche Nuevo"
                JOptionPane.showMessageDialog(null, "Añade los datos del cliente");

                VentanaRegistroCliente ventanaRegistroCliente = new VentanaRegistroCliente();
                Cliente cliente = ventanaRegistroCliente.crearObjetoCliente();
                if (cliente != null) {

                    	//CODIGO PARA ELEGIR EL COCHE QUE SE HA VENDIDO
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar los datos del cliente");
                }
            }
        });

        // ...


        cocheSegundaManoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones al presionar "Coche Segunda Mano"
            	JOptionPane.showMessageDialog(null, "Añade los datos del cliente");
            	VentanaRegistroCliente ventanaRegistroCliente = new VentanaRegistroCliente();
                Cliente cliente = ventanaRegistroCliente.crearObjetoCliente();
            	VentanaConcesionarioCompraCocheSegundaMano ventanaCompraCocheSegundaMano = new VentanaConcesionarioCompraCocheSegundaMano();
            	ventanaCompraCocheSegundaMano.agregarCocheSegundaManoForm(cliente);
            }
        });

        motoNuevaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones al presionar "Moto Nueva"
            	JOptionPane.showMessageDialog(null, "Añade los datos del cliente");
            	VentanaRegistroCliente ventanaRegistroCliente = new VentanaRegistroCliente();
                Cliente cliente = ventanaRegistroCliente.crearObjetoCliente();
            	VentanaConcesionarioCompraMoto ventanaCompraMoto = new VentanaConcesionarioCompraMoto();
                ventanaCompraMoto.agregarMoto(cliente);
            }
        });

        motoSegundaManoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acciones al presionar "Moto Segunda Mano"
            	JOptionPane.showMessageDialog(null, "Añade los datos del cliente");
            	VentanaRegistroCliente ventanaRegistroCliente = new VentanaRegistroCliente();
                Cliente cliente = ventanaRegistroCliente.crearObjetoCliente();
                VentanaConcesionarioCompraMotoSegundaMano ventana = new VentanaConcesionarioCompraMotoSegundaMano(cliente);

                // PEDIR DATOS MOTO Y AÑADIR A TABLA BD MOTOSEGUNDAMANO
            }
        });

        ventanaAgregarVehiculo.add(cocheNuevoButton);
        ventanaAgregarVehiculo.add(cocheSegundaManoButton);
        ventanaAgregarVehiculo.add(motoNuevaButton);
        ventanaAgregarVehiculo.add(motoSegundaManoButton);

        ventanaAgregarVehiculo.setVisible(true);
    }

}
