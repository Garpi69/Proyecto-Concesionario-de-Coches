package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import db.DAO;
import domain.GeneradorNumeroSerie;

public class VentanaInventario extends JFrame {

	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	private JTable inventarioTable;
	private DefaultTableModel tableModel;
	private boolean esTrabajador = false;

	public VentanaInventario(DAO dao) {
		if (dao.cliente.getLogin() != null) {
			esTrabajador = true;
		}
		setTitle("Inventario de Vehículos");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		JPanel filterPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(5, 5, 5, 5);

		JTextField marcaField = new JTextField(10);
		JTextField modeloField = new JTextField(10);
		JTextField colorField = new JTextField(10);

		JButton filtrarButton = new JButton("Filtrar");
		filtrarButton.addActionListener(e -> {
			tableModel.setRowCount(0);
			filtrarCoche(marcaField.getText(), modeloField.getText(), colorField.getText(), dao);
			filtrarCocheSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText(), dao);
			filtrarMoto(marcaField.getText(), modeloField.getText(), colorField.getText(), dao);
			filtrarMotoSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText(), dao);
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		filterPanel.add(new JLabel("Marca:"), gbc);
		JButton cocheButton = new JButton("Coche");
		cocheButton.addActionListener(e -> {
			try {
				Connection connection = DriverManager.getConnection(dao.url);
				tableModel.setRowCount(0);
				cargarDatosVehiculos(connection, "coche", dao);
			} catch (SQLException e1) {

			}
		});

		JButton cocheSegundaManoButton = new JButton("Coche de Segunda Mano");
		cocheSegundaManoButton.addActionListener(e -> {
			try {
				Connection connection = DriverManager.getConnection(dao.url);
				tableModel.setRowCount(0);
				cargarDatosVehiculos(connection, "cocheSegundaMano", dao);
			} catch (SQLException e1) {

			}
		});

		JButton motoButton = new JButton("Moto");
		motoButton.addActionListener(e -> {
			try {
				Connection connection = DriverManager.getConnection(dao.url);
				tableModel.setRowCount(0);
				cargarDatosVehiculos(connection, "moto", dao);
			} catch (SQLException e1) {

			}
		});

		JButton motoSegundaManoButton = new JButton("Moto de Segunda Mano");
		motoSegundaManoButton.addActionListener(e -> {
			try {
				Connection connection = DriverManager.getConnection(dao.url);
				tableModel.setRowCount(0);
				cargarDatosVehiculos(connection, "motoSegundaMano", dao);
			} catch (SQLException e1) {

			}
		});

		gbc2.gridx = 7;
		buttonPanel.add(cocheButton, gbc2);

		gbc2.gridx = 8;
		buttonPanel.add(cocheSegundaManoButton, gbc2);

		gbc2.gridx = 9;
		buttonPanel.add(motoButton, gbc2);

		gbc2.gridx = 10;
		buttonPanel.add(motoSegundaManoButton, gbc2);

		gbc.gridx = 1;
		filterPanel.add(marcaField, gbc);

		gbc.gridx = 2;
		filterPanel.add(new JLabel("Modelo:"), gbc);

		gbc.gridx = 3;
		filterPanel.add(modeloField, gbc);

		gbc.gridx = 4;
		filterPanel.add(new JLabel("Color:"), gbc);

		gbc.gridx = 5;
		filterPanel.add(colorField, gbc);

		gbc.gridx = 6;
		filterPanel.add(filtrarButton, gbc);

		tableModel = new DefaultTableModel();
		inventarioTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(inventarioTable);
		if (esTrabajador) {
			mostrarTrabajador(buttonPanel, dao);
		}
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(filterPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		cargarInventario(dao);
		setVisible(true);
	}

	public void cargarInventario(DAO dao) {

		try {
			Connection connection = DriverManager.getConnection(dao.url);
			String[] columnas = { "ID", "Combustible", "Marca", "Modelo", "Color", "Tipo", "Potencia",
					"Numero de plazas", "Precio", "Cuota", "Matriculacion", "Peso (Motos)", "Baul (Motos)",
					"Kilometraje (Segunda Mano)", "Ofertas", "Propietario" };
			tableModel.setColumnIdentifiers(columnas);

			cargarDatosVehiculos(connection, "coche", dao);

			cargarDatosVehiculos(connection, "cocheSegundaMano", dao);

			cargarDatosVehiculos(connection, "moto", dao);

			cargarDatosVehiculos(connection, "motoSegundaMano", dao);

			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());
		}
	}

	private void cargarDatosVehiculos(Connection connection, String tabla, DAO dao) throws SQLException {
		String sql = "SELECT * FROM " + tabla;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			int id = resultSet.getInt("idVehiculo");
			String combustible = resultSet.getString("combustible");
			String marca = resultSet.getString("marca");
			String modelo = resultSet.getString("modelo");
			String color = resultSet.getString("color");
			String tipo = resultSet.getString("tipo");
			int potencia = resultSet.getInt("potencia");
			int numPlazas = resultSet.getInt("numPlazas");
			int precio = resultSet.getInt("precio");
			int cuota = resultSet.getInt("cuota");
			String propietario = resultSet.getString("propietario");
			String ofertas = resultSet.getString("ofertas");
			String matriculacion = null;
			try {
				matriculacion = resultSet.getString("matriculacion");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int kilometraje = 0;
			int peso = 0;
			int baul = 0;

			try {
				kilometraje = resultSet.getInt("kilometraje");
			} catch (SQLException e) {

			}
			try {
				peso = resultSet.getInt("peso");
			} catch (SQLException e) {

			}
			try {
				baul = resultSet.getInt("baul");

			} catch (SQLException e) {
			}
			String baulString = "No";
			if (baul == 1) {
				baulString = "Si";

			}

			Object[] fila = { id, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota,
					matriculacion, peso, baulString, kilometraje, ofertas, propietario };
			tableModel.addRow(fila);
		}

		resultSet.close();
		statement.close();
	}

	private void filtrarCoche(String marca, String modelo, String color, DAO dao) {
		try {
			Connection connection = DriverManager.getConnection(dao.url);

			String sql = "SELECT * FROM coche WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "%" + marca + "%");
			statement.setString(2, "%" + modelo + "%");
			statement.setString(3, "%" + color + "%");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("idVehiculo");
				String combustible = resultSet.getString("combustible");
				String tipo = resultSet.getString("tipo");
				String marcaReal = resultSet.getString("marca");
				String modeloReal = resultSet.getString("modelo");
				String colorReal = resultSet.getString("color");
				int potencia = resultSet.getInt("potencia");
				int numPlazas = resultSet.getInt("numPlazas");
				int precio = resultSet.getInt("precio");
				int cuota = resultSet.getInt("cuota");
				String matriculacion = resultSet.getString("matriculacion");
				String ofertas = resultSet.getString("ofertas");
				String propietario = resultSet.getString("propietario");

				Date matriculacionDate = null;
				matriculacionDate = dao.stringToDate(matriculacion, dao.format);

				Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio,
						cuota, matriculacionDate, ofertas, propietario };
				tableModel.addRow(fila);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
		}
		inventarioTable.repaint();
	}

	private void filtrarCocheSegundaMano(String marca, String modelo, String color, DAO dao) {
		try {
			Connection connection = DriverManager.getConnection(dao.url);

			String sql = "SELECT * FROM cocheSegundaMano WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "%" + marca + "%");
			statement.setString(2, "%" + modelo + "%");
			statement.setString(3, "%" + color + "%");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("idVehiculo");
				String combustible = resultSet.getString("combustible");
				String tipo = resultSet.getString("tipo");
				String marcaReal = resultSet.getString("marca");
				String modeloReal = resultSet.getString("modelo");
				String colorReal = resultSet.getString("color");
				int potencia = resultSet.getInt("potencia");
				int numPlazas = resultSet.getInt("numPlazas");
				int precio = resultSet.getInt("precio");
				int cuota = resultSet.getInt("cuota");
				String matriculacion = resultSet.getString("matriculacion");
				int kilometraje = 0;
				Date matriculacionDate = null;
				String ofertas = resultSet.getString("ofertas");
				String propietario = resultSet.getString("propietario");
				matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				kilometraje = getIntOrNull(resultSet, "kilometraje");

				Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio,
						cuota, matriculacionDate, 0, 0, kilometraje, ofertas, propietario };
				tableModel.addRow(fila);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
		}
		inventarioTable.repaint();

	}

	private void filtrarMoto(String marca, String modelo, String color, DAO dao) {
		try {
			Connection connection = DriverManager.getConnection(dao.url);

			String sql = "SELECT * FROM moto WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "%" + marca + "%");
			statement.setString(2, "%" + modelo + "%");
			statement.setString(3, "%" + color + "%");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("idVehiculo");
				String combustible = resultSet.getString("combustible");
				String marcaReal = resultSet.getString("marca");
				String modeloReal = resultSet.getString("modelo");
				String colorReal = resultSet.getString("color");
				String tipo = resultSet.getString("tipo");
				int potencia = resultSet.getInt("potencia");
				int numPlazas = resultSet.getInt("numPlazas");
				int precio = resultSet.getInt("precio");
				int cuota = resultSet.getInt("cuota");
				String matriculacion = resultSet.getString("matriculacion");
				int baul = 0;
				int peso = 0;
				Date matriculacionDate = null;
				matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				String baulString = "No";
				baul = getIntOrNull(resultSet, "baul");
				peso = getIntOrNull(resultSet, "peso");
				if (baul == 1) {
					baulString = "Si";
				}

				String propietario = resultSet.getString("propietario");
				String ofertas = resultSet.getString("ofertas");
				Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio,
						cuota, matriculacionDate, peso, baulString, ofertas, propietario };
				tableModel.addRow(fila);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
		}
		inventarioTable.repaint();

	}

	private void filtrarMotoSegundaMano(String marca, String modelo, String color, DAO dao) {
		try {
			Connection connection = DriverManager.getConnection(dao.url);

			String sql = "SELECT * FROM moto WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "%" + marca + "%");
			statement.setString(2, "%" + modelo + "%");
			statement.setString(3, "%" + color + "%");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("idVehiculo");
				String combustible = resultSet.getString("combustible");
				String marcaReal = resultSet.getString("marca");
				String modeloReal = resultSet.getString("modelo");
				String colorReal = resultSet.getString("color");
				String tipo = resultSet.getString("tipo");
				int potencia = resultSet.getInt("potencia");
				int numPlazas = resultSet.getInt("numPlazas");
				int precio = resultSet.getInt("precio");
				int cuota = resultSet.getInt("cuota");
				String matriculacion = resultSet.getString("matriculacion");
				int baul = 0;
				int peso = 0;
				int kilometraje = 0;
				Date matriculacionDate = null;
				matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				baul = getIntOrNull(resultSet, "baul");
				peso = getIntOrNull(resultSet, "peso");
				kilometraje = getIntOrNull(resultSet, "kilometraje");
				String baulString = "No";
				if (baul == 1) {
					baulString = "Si";
				}

				String propietario = resultSet.getString("propietario");
				String ofertas = resultSet.getString("ofertas");
				Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio,
						cuota, matriculacionDate, peso, baulString, kilometraje, ofertas, propietario };
				tableModel.addRow(fila);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
		}
		inventarioTable.repaint();

	}

	private int getIntOrNull(ResultSet resultSet, String column) {
		int valor = 0;
		try {
			valor = resultSet.getInt(column);
		} catch (SQLException e) {

		}
		return valor;
	}

	private void mostrarTrabajador(JPanel buttonPanel, DAO dao) {
		JButton buttonAñadir = new JButton("Añadir");
		JButton buttonEliminar = new JButton("Eliminar");
		buttonPanel.add(buttonAñadir);
		buttonPanel.add(buttonEliminar);
		buttonAñadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaAñadirCoche(dao);

			}
		});
		buttonEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = inventarioTable.getSelectedRow();
				String marca = inventarioTable.getValueAt(selectedRow, 2).toString();
				String modelo = inventarioTable.getValueAt(selectedRow, 3).toString();
				dao.eliminarVehiculo(marca + " " + modelo, "coche");
				dao.eliminarVehiculo(marca + " " + modelo, "cocheSegundaMano");
				dao.eliminarVehiculo(marca + " " + modelo, "moto");
				dao.eliminarVehiculo(marca + " " + modelo, "motoSegundaMano");
				JOptionPane.showMessageDialog(null, "Coche eliminado correctamente");

			}

		});
	}

}
