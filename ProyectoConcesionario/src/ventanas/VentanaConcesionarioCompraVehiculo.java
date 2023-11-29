package ventanas;

import javax.swing.*;
import clases.Cliente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaConcesionarioCompraVehiculo {

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
                   
                    // Después de obtener los datos del cliente, abre la ventana para agregar el coche
                    VentanaConcesionarioCompraCoche ventana = new VentanaConcesionarioCompraCoche();
                    ventana.agregarCocheForm(cliente);
                    JOptionPane.showMessageDialog(null, "Datos del cliente registrados exitosamente");
                    // La ventana "VentanaConcesionarioCompraCoche" debería manejar la lógica para agregar el coche
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
    public static void main (String[] args) {
    	VentanaConcesionarioCompraVehiculo a = new VentanaConcesionarioCompraVehiculo();
    	a.abrirVentanaAgregarVehiculo();
    }
    
}
