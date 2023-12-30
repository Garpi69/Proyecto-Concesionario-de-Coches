package ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VentanaMenuTrabajador extends JFrame{

	protected JButton botonInventario ;
	protected JButton botonInforme ;
	protected JButton botonCalendario ;
	protected JButton botonComprarVehiculo ;
	protected JButton botonVenderVehiculo ;
	protected JButton botonClientes ;
	protected JButton botonOfertas;

	public VentanaMenuTrabajador(DAO dao){
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		 JPanel contentPanel = new JPanel();
	        contentPanel.setLayout(new BorderLayout());


	        JPanel titlePanel = new JPanel();
	        titlePanel.setLayout(new FlowLayout());


	        JLabel TituloLabel = new JLabel("Concesionario");
	        TituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
	        titlePanel.add(TituloLabel);


	        contentPanel.add(titlePanel, BorderLayout.NORTH);


	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new FlowLayout());

		botonInventario = new JButton("Inventario");
		botonInforme = new JButton("Informe");
		botonOfertas = new JButton("Ofertas");
		botonCalendario = new JButton("Calendario");
		botonComprarVehiculo = new JButton("Agregar Vehiculo a la venta");
		botonVenderVehiculo = new JButton("Agregar Vehiculo Vendido");
		botonClientes = new JButton("Clientes");
		
		buttonPanel.add(botonOfertas);
		buttonPanel.add(botonInventario);
		buttonPanel.add(botonInforme);
		buttonPanel.add(botonCalendario);
		buttonPanel.add(botonComprarVehiculo);
		buttonPanel.add(botonVenderVehiculo);
		buttonPanel.add(botonClientes);

		contentPanel.add(buttonPanel,BorderLayout.CENTER);
		add(contentPanel);
		botonInventario.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			VentanaInventario ventana = new VentanaInventario();
			ventana.cargarInventario();



		}
	});
		botonOfertas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaTrabajadorVerOfertas();
				
			}
			
			
		});
		
		
		botonInforme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaInforme();

			}
		});
		botonCalendario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {



			}
		});botonComprarVehiculo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
		            new VentanaMenuTrabajadorAñadirCocheComprado();
		        });



			}
		});
		botonVenderVehiculo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ABRIR VENTANA PARA ELEGIR QUE VEHICULO SE HA VENDIDO (COCHE, MOTO, COCHE2MANO,MOTO2MANO)
				VentanaConcesionarioVendeVehiculo ventana = new VentanaConcesionarioVendeVehiculo();
				ventana.abrirVentanaAgregarVehiculo();
				//PEDIR ID VEHICULO VENDIDO Y DATOS CLIENTE (SUPONIENDO QUE NO TIENE CUENTA) USAR OBTENERCLIENTE
				//ELIMINAR DE LA TABLA COCHE/MOTO/COCHESEGUNDAMANO/MOTOSEGUNDAMANO Y AÑADIR A VENTAS

			}
		});
		botonClientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//MOSTRAR CLIENTES
				new VentanaVerClientes();

			}
		});


        this.setVisible(true);

		this.setTitle("Menu");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		setVisible(true);
		this.setLocationRelativeTo(null);


	}

}
