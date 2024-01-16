package src.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;
import src.domain.Venta;


public class VentanaMenuClienteCompraVehiculo extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	JTable inventarioTable;
	private DefaultTableModel tableModel;

	public VentanaMenuClienteCompraVehiculo(DAO dao) {
		setTitle("Inventario de Vehículos");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(1000, 800);
		setLocationRelativeTo(null);

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
			dao.filtrarVehiculo("coche",marcaField.getText(), modeloField.getText(), colorField.getText(), tableModel);
			dao.filtrarVehiculo("cocheSegundaMano",marcaField.getText(), modeloField.getText(), colorField.getText(),tableModel);
			dao.filtrarVehiculo("moto",marcaField.getText(), modeloField.getText(), colorField.getText(),tableModel);
			dao.filtrarVehiculo("motoSegundaMano",marcaField.getText(), modeloField.getText(), colorField.getText(),tableModel);

		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		filterPanel.add(new JLabel("Marca:"), gbc);
		JButton cocheButton = new JButton("Coche");
		cocheButton.addActionListener(e -> {
			try {
				
				tableModel.setRowCount(0);
		
				dao.conectar();
				cargarDatosVehiculos( "coche", dao);
				dao.desconectar();
				repaint();
			} catch (SQLException e1) {

			}
		});

		JButton cocheSegundaManoButton = new JButton("Coche de Segunda Mano");
		cocheSegundaManoButton.addActionListener(e -> {
			try {
			
				tableModel.setRowCount(0);
				dao.conectar();
				cargarDatosVehiculos( "cocheSegundaMano", dao);
				dao.desconectar();
				repaint();
			} catch (SQLException e1) {

			}
		});

		JButton motoButton = new JButton("Moto");
		motoButton.addActionListener(e -> {
			try {
				
				tableModel.setRowCount(0);
				dao.conectar();
				cargarDatosVehiculos("moto", dao);
				dao.desconectar();
				repaint();
			} catch (SQLException e1) {

			}
		});

		JButton motoSegundaManoButton = new JButton("Moto de Segunda Mano");
		motoSegundaManoButton.addActionListener(e -> {
			try {
			
				tableModel.setRowCount(0);
				dao.conectar();
				cargarDatosVehiculos( "motoSegundaMano", dao);
				dao.desconectar();
				repaint();
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
		JButton comprarButton = new JButton("Comprar");
		gbc.gridx = 7;
		filterPanel.add(comprarButton, gbc);
		JButton hacerOfertaButton = new JButton("Hacer oferta");
		gbc.gridx = 8;
		filterPanel.add(hacerOfertaButton, gbc);
		tableModel = new DefaultTableModel();
		inventarioTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(inventarioTable);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(filterPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		cargarInventario(dao);
		comprarButton.addActionListener(e -> {
			if (inventarioTable.getSelectedRow() != -1) {
				confirmarCompra(dao);
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un vehiculo");
			}

		});
		hacerOfertaButton.addActionListener(e -> {
			int row = inventarioTable.getSelectedRow();
			int idVehiculo = (int) inventarioTable.getValueAt(row, 0);
			new VentanaOferta(idVehiculo, dao.cliente.getLogin(), dao);
		});
		setVisible(true);
	}

	public void cargarInventario(DAO dao) {

		try {
			dao.conectar();
			String[] columnas = { "ID", "Combustible", "Marca", "Modelo", "Color", "Tipo", "Potencia",
					"Numero de plazas", "Precio", "Cuota", "Matriculacion", "Peso (Motos)", "Baul (Motos)",
					"Kilometraje (Segunda Mano", "dniPropietario" };
			tableModel.setColumnIdentifiers(columnas);

			cargarDatosVehiculos( "coche", dao);

			cargarDatosVehiculos( "cocheSegundaMano", dao);

			cargarDatosVehiculos( "moto", dao);

			cargarDatosVehiculos("motoSegundaMano", dao);

			dao.desconectar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());
		}
	}

	private void cargarDatosVehiculos( String tabla, DAO dao) throws SQLException {
		String sql = "SELECT * FROM " + tabla;
		PreparedStatement statement = dao.conn.prepareStatement(sql);
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
			Date matriculacion = null;
			String matriculacionString = resultSet.getString("matriculacion");
			String propietario = resultSet.getString("dniPropietario");
			try {
				if (matriculacionString != null) {
					matriculacion = dao.stringToDate(resultSet.getString("matriculacion"), dao.format);
				}

			} catch (SQLException e) {

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
			String baulString = "";
			if (baul == 1) {
				baulString = "Si";
			} else {
				baulString = "No";
			}

			Object[] fila = { id, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota,
					matriculacion, peso, baulString, kilometraje, propietario };
			tableModel.addRow(fila);
		}

	
	}

	private void confirmarCompra(DAO dao) {
		int selectedRow = inventarioTable.getSelectedRow();
		if (selectedRow != -1) {
			int idVehiculo = (int) inventarioTable.getValueAt(selectedRow, 0);
			int precio = (int) inventarioTable.getValueAt(selectedRow, 8);
			String marca = (String) inventarioTable.getValueAt(inventarioTable.getSelectedRow(), 3);
			String modelo = (String) inventarioTable.getValueAt(inventarioTable.getSelectedRow(), 4);
			int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea comprar este vehículo?",
					"Confirmación de Compra", JOptionPane.YES_NO_OPTION);
			String categoria = "";
			String nombreCoche = marca + " " + modelo;
			if (opcion == JOptionPane.YES_OPTION) {
				new VentanaNuevaCita(dao, nombreCoche);
				if ((int) inventarioTable.getValueAt(selectedRow, 7) > 2) {
					if (inventarioTable.getValueAt(selectedRow, 13) != null) {
						categoria = "coche";
					} else {
						categoria = "cocheSegundaMano";
					}
				} else {
					if (inventarioTable.getValueAt(selectedRow, 13) != null) {
						categoria = "moto";
					} else {
						categoria = "motoSegundaMano";
					}
				}
				String dniCliente = dao.trabajador.getdNI();
				Venta venta = new Venta(categoria, idVehiculo, marca, modelo, precio, dniCliente);
				dao.agregarVenta(venta);
				dao.eliminarVehiculo(nombreCoche, categoria);
				cargarInventario(dao);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecciona un coche para comprar.");
		}
	}

	

}
