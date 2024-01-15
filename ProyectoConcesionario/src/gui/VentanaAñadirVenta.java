package src.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;
import src.domain.Venta;

public class VentanaAñadirVenta extends JFrame {

	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	private JTextField campoCategoria;
	private JTextField campoIdVehiculo;
	private JTextField campoMarca;
	private JTextField campoModelo;
	private JTextField campoPrecioVenta;
	private JTextField campoDniComprador;

	public VentanaAñadirVenta(DAO dao) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Añadir Venta");

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		campoCategoria = new JTextField(20);
		campoIdVehiculo = new JTextField(20);
		campoMarca = new JTextField(20);
		campoModelo = new JTextField(20);
		campoPrecioVenta = new JTextField(20);
		campoDniComprador = new JTextField(20);

		panel.add(new JLabel("Categoría:"));
		panel.add(campoCategoria);
		panel.add(new JLabel("ID del Vehículo:"));
		panel.add(campoIdVehiculo);
		panel.add(new JLabel("Marca:"));
		panel.add(campoMarca);
		panel.add(new JLabel("Modelo:"));
		panel.add(campoModelo);
		panel.add(new JLabel("Precio de Venta:"));
		panel.add(campoPrecioVenta);
		panel.add(new JLabel("DNI del Comprador:"));
		panel.add(campoDniComprador);

		JButton botonAgregar = new JButton("Agregar Venta");
		botonAgregar.addActionListener(e -> {
			Venta venta = new Venta(campoCategoria.getText(), Integer.parseInt(campoIdVehiculo.getText()),
					campoMarca.getText(), campoModelo.getText(), Integer.parseInt(campoPrecioVenta.getText()),
					campoDniComprador.getText());
			dao.agregarVenta(venta);
		});

		panel.add(botonAgregar);

		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
