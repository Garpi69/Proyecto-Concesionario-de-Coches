package src.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import src.domain.Cliente;
import src.domain.GeneradorNumeroSerie;

public class VentanaVerClientes extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();
	private JTable inventarioTable;
	private DefaultTableModel tableModel;
	private DAO dao = new DAO();

	public VentanaVerClientes() {
		setTitle("Inventario de Vehículos");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new FlowLayout());
		JButton agregarClienteButton = new JButton("Agregar Cliente");
		agregarClienteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaAñadirCliente ventana = new VentanaAñadirCliente();
				ventana.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {

						String dniCliente = ventana.devolverDni();
						Cliente cliente = dao.obtenerClientePorDNI(dniCliente);
						try {
							dao.agregarCliente(cliente);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					}
				});
			}

		});

		JButton eliminarClienteButton = new JButton("Eliminar Cliente");
		eliminarClienteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int numero = inventarioTable.getSelectedRow();
				Object login = inventarioTable.getValueAt(numero, 0);
				System.out.println(login);
				String loginString = login.toString();
				System.out.println(login);

				try {

					dao.eliminarCliente(loginString);
					inventarioTable.repaint();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JLabel labelComboBox = new JLabel("Login: ");

		tableModel = new DefaultTableModel();
		inventarioTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(inventarioTable);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		buttonPanel.add(agregarClienteButton);
		buttonPanel.add(eliminarClienteButton);

		String[] opciones = { "login", "email", "dni", "apellidos" };
		JComboBox<String> buscarPorComboBox = new JComboBox<>(opciones);

		JTextField valorBusquedaField = new JTextField(20);

		JButton buscarButton = new JButton("Buscar");
		buscarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String opcionSeleccionada = (String) buscarPorComboBox.getSelectedItem();
				String valorBusqueda = valorBusquedaField.getText();
				if (opcionSeleccionada != null && valorBusqueda != null && !valorBusqueda.isEmpty()) {
					buscarClientePor(opcionSeleccionada, valorBusqueda, dao);
				} else {
					JOptionPane.showMessageDialog(VentanaVerClientes.this,
							"Por favor, seleccione una opción y ingrese un valor para buscar.");
				}
			}
		});
		buscarPorComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				labelComboBox.setText("" + buscarPorComboBox.getSelectedItem() + ": ");
				repaint();

			}

		});

		filterPanel.add(buscarPorComboBox);

		filterPanel.add(new JLabel("Buscar por:"));
		filterPanel.add(buscarPorComboBox);

		filterPanel.add(labelComboBox);
		filterPanel.add(valorBusquedaField);
		filterPanel.add(buscarButton);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(filterPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		add(scrollPane);
		cargarClientes();
		setVisible(true);
	}

	public void cargarClientes() {

		try {
			dao.conectar();
			Connection connection = dao.conn;

			String[] columnas = { "Login", "Contraseña", "Email", "DNI", "Nombre", "Apellido", "Fecha de nacimiento",
					"Numero de tarjeta" };
			tableModel.setColumnIdentifiers(columnas);

			cargarDatosClientes(connection, "cliente");

			connection.close();
			dao.desconectar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());
		}
	}

	private void cargarDatosClientes(Connection connection, String tabla) throws SQLException {

		String sql = "SELECT * FROM " + tabla;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String login = resultSet.getString("login");
			String contrasena = resultSet.getString("contra");
			String email = resultSet.getString("email");
			String dni = resultSet.getString("dni");
			String nombre = resultSet.getString("nombre");
			String apellido = resultSet.getString("apellidos");

			Date fechaNacimiento = null;
			try {
				String fechaNacimientoString = resultSet.getString("fechaNacimiento");
				fechaNacimiento = dao.stringToDate(fechaNacimientoString, dao.format);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int numTarjeta = resultSet.getInt("numTarjeta");
			Object[] fila = { login, contrasena, email, dni, nombre, apellido, fechaNacimiento, numTarjeta };
			tableModel.addRow(fila);
		}

		resultSet.close();
		statement.close();
	}

	public void buscarClientePor(String opcion, String valorBusqueda, DAO dao) {

		tableModel.setRowCount(0);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(dao.url);
			String sql = "SELECT * FROM cliente WHERE " + opcion + " = ?";

			if (valorBusqueda != null && !valorBusqueda.isEmpty()) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, valorBusqueda);
				rs = stmt.executeQuery();

				while (rs.next()) {

					String login = rs.getString("login");
					String contra = rs.getString("contra");
					String correo = rs.getString("email");
					String dni = rs.getString("dni");
					String nombre = rs.getString("nombre");
					String apellido = rs.getString("apellidos");
					String fechaNacimiento = rs.getString("fechaNacimiento");
					int numTarjeta = rs.getInt("numTarjeta");

					Object[] fila = { login, contra, correo, dni, nombre, apellido, fechaNacimiento, numTarjeta };
					tableModel.addRow(fila);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor para buscar.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al buscar clientes: " + e.getMessage());
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
