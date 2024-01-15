package src.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Comparator;

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
			dao.filtrarVehiculo("coche", marcaField.getText(), modeloField.getText(), colorField.getText(), tableModel);
			dao.filtrarVehiculo("cocheSegundaMano", marcaField.getText(), modeloField.getText(), colorField.getText(),
					tableModel);
			dao.filtrarVehiculo("moto", marcaField.getText(), modeloField.getText(), colorField.getText(), tableModel);
			dao.filtrarVehiculo("motoSegundaMano", marcaField.getText(), modeloField.getText(), colorField.getText(),
					tableModel);
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		filterPanel.add(new JLabel("Marca:"), gbc);
		JButton cocheButton = new JButton("Coche");
		cocheButton.addActionListener(e -> {
			try {
				tableModel.setRowCount(0);
				dao.conectar();
				dao.cargarDatosVehiculos("coche", tableModel);
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
				dao.cargarDatosVehiculos("cocheSegundaMano", tableModel);
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
				dao.cargarDatosVehiculos("moto", tableModel);
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
				dao.cargarDatosVehiculos("motoSegundaMano", tableModel);
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
			dao.conectar();
			String[] columnas = { "ID", "Combustible", "Marca", "Modelo", "Color", "Tipo", "Potencia",
					"Numero de plazas", "Precio", "Cuota", "Matriculacion", "Peso (Motos)", "Baul (Motos)",
					"Kilometraje (Segunda Mano)", "Ofertas", "Propietario" };
			tableModel.setColumnIdentifiers(columnas);

			dao.cargarDatosVehiculos("coche", tableModel);

			dao.cargarDatosVehiculos("cocheSegundaMano", tableModel);

			dao.cargarDatosVehiculos("moto", tableModel);

			dao.cargarDatosVehiculos("motoSegundaMano", tableModel);

			ordenarTablaPorId();
			dao.desconectar();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());

		}
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
				repaint();

			}
		});
		buttonEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = inventarioTable.getSelectedRow();
				if (selectedRow!=-1) {
					String marca = inventarioTable.getValueAt(selectedRow, 2).toString();
					String modelo = inventarioTable.getValueAt(selectedRow, 3).toString();
					dao.eliminarVehiculo(marca + " " + modelo, "coche");
					dao.eliminarVehiculo(marca + " " + modelo, "cocheSegundaMano");
					dao.eliminarVehiculo(marca + " " + modelo, "moto");
					dao.eliminarVehiculo(marca + " " + modelo, "motoSegundaMano");
					JOptionPane.showMessageDialog(null, "Coche eliminado correctamente");
					repaint();
				}
				

			}

		});
	}

	public void ordenarTablaPorId() {
		Comparator<Object> comparator = (a, b) -> String.valueOf(a).compareTo(String.valueOf(b));
		tableModel.getDataVector().sort((row1, row2) -> comparator.compare(row1.get(0), row2.get(0)));
	}

}
