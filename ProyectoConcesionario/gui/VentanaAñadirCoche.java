package gui;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import db.DAO;
import domain.Coche;
import domain.GeneradorNumeroSerie;

public class VentanaAñadirCoche extends JFrame {

	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	private JTextField campoID;
	private JTextField campoCombustible;
	private JTextField campoMarca;
	private JTextField campoModelo;
	private JTextField campoColor;
	private JTextField campoTipo;
	private JTextField campoPotencia;
	private JTextField campoNumPlazas;
	private JTextField campoPrecio;
	private JTextField campoCuota;
	private JTextField campoMatriculacion;

	public VentanaAñadirCoche(DAO dao) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Datos del Vehículo");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(12, 2, 10, 5));

		panel.add(new JLabel("ID del Vehículo:"));
		campoID = new JTextField();
		panel.add(campoID);

		panel.add(new JLabel("Combustible:"));
		campoCombustible = new JTextField();
		panel.add(campoCombustible);

		panel.add(new JLabel("Marca:"));
		campoMarca = new JTextField();
		panel.add(campoMarca);

		panel.add(new JLabel("Modelo:"));
		campoModelo = new JTextField();
		panel.add(campoModelo);

		panel.add(new JLabel("Color:"));
		campoColor = new JTextField();
		panel.add(campoColor);

		panel.add(new JLabel("Tipo:"));
		campoTipo = new JTextField();
		panel.add(campoTipo);

		panel.add(new JLabel("Potencia:"));
		campoPotencia = new JTextField();
		panel.add(campoPotencia);

		panel.add(new JLabel("Número de Plazas:"));
		campoNumPlazas = new JTextField();
		panel.add(campoNumPlazas);

		panel.add(new JLabel("Precio:"));
		campoPrecio = new JTextField();
		panel.add(campoPrecio);

		panel.add(new JLabel("Cuota:"));
		campoCuota = new JTextField();
		panel.add(campoCuota);

		panel.add(new JLabel("Fecha de Matriculación (dd/mm/yyyy):"));
		campoMatriculacion = new JTextField();
		panel.add(campoMatriculacion);

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.addActionListener(e -> {
			Date matriculacion = dao.stringToDate(campoMatriculacion.getText(), dao.format);
			Coche coche = new Coche(Integer.parseInt(campoID.getText()), campoCombustible.getText(),
					campoMarca.getText(), campoModelo.getText(), campoColor.getText(), campoTipo.getText(),
					Integer.parseInt(campoPotencia.getText()), Integer.parseInt(campoNumPlazas.getText()),
					Integer.parseInt(campoPrecio.getText()), Integer.parseInt(campoCuota.getText()), matriculacion, "",
					"concesionario");
			try {
				dao.agregarCocheCompradoPorConcesionario(coche);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "No se ha podido guardar el coche");
				e1.printStackTrace();
			}
		});

		panel.add(botonGuardar);

		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
