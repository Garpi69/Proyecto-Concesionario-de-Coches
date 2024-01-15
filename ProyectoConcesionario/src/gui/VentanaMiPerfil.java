package src.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import src.db.DAO;
import src.domain.Cliente;
import src.domain.GeneradorNumeroSerie;
import src.domain.Trabajador;
import src.io.LoggerDeusto;

public class VentanaMiPerfil extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();
	
	@SuppressWarnings("unused")
	private JLabel loginLabel,emailLabel, contraLabel, dniLabel, nombreLabel, apellidoLabel, fechaNacimientoLabel, numTarjetaLabel, puestoLabel,sueldoLabel;
	private JButton guardarButton, editarButton;
	private JTextField loginField, emailField, contraField, dniField, nombreField, apellidoField, fechaNacimientoField,
			numTarjetaField, sueldoField,puestoField;
	private Trabajador trabajador = new Trabajador();

	public VentanaMiPerfil(DAO dao,String tipoUsuario) {
		setTitle("Mi Perfil");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		loginField = new JTextField(20);
		emailField = new JTextField(20);
		contraField = new JTextField(20);
		dniField = new JTextField(20);
		nombreField = new JTextField(20);
		apellidoField = new JTextField(20);
		fechaNacimientoField = new JTextField(20);
		numTarjetaField = new JTextField(20);
		puestoField = new JTextField(20);
		sueldoField = new JTextField(20);
		
		editarButton = new JButton("Editar");
		editarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				hacerEditable(panel);
			}

		});
		guardarButton = new JButton("Guardar");
		guardarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarCambios(dao, tipoUsuario);
				hacerNoEditable(panel);
				dispose();

			}
		});
		
	
	
		
		panel.add(loginLabel = new JLabel("Login:"));
		panel.add(loginField);
		panel.add(emailLabel = new JLabel("Email:"));
		panel.add(emailField);
		panel.add(contraLabel = new JLabel("Contraseña:"));
		panel.add(contraField);
		panel.add(dniLabel = new JLabel("DNI:"));
		panel.add(dniField);
		panel.add(nombreLabel = new JLabel("Nombre:"));
		panel.add(nombreField);
		panel.add(apellidoLabel = new JLabel("Apellido:"));
		panel.add(apellidoField);
		panel.add(fechaNacimientoLabel = new JLabel("Fecha de nacimiento:"));
		panel.add(fechaNacimientoField);
		panel.add(puestoLabel = new JLabel("Puesto:"));
		panel.add(puestoField);
		panel.add(sueldoLabel = new JLabel("Sueldo:"));
		panel.add(sueldoField);
		panel.add(numTarjetaLabel = new JLabel("Número de tarjeta:"));
		panel.add(numTarjetaField);
		
		if (tipoUsuario=="cliente") {
			
			panel.remove(puestoLabel);
			panel.remove(puestoField);
			panel.remove(sueldoField);
			panel.remove(sueldoLabel);
			panel.add(numTarjetaLabel);
			panel.add(numTarjetaField);
		
			repaint();
		}else {
			panel.remove(numTarjetaLabel);
			panel.remove(numTarjetaField);
			panel.add(puestoLabel);
			panel.add(puestoField);
			
			panel.add(sueldoLabel);
			panel.add(sueldoField);
			
			repaint();
		}
		
		
		
		
	
		
		panel.add(guardarButton);
		panel.add(editarButton);
		hacerNoEditable(panel);
		add(panel);
		setVisible(true);
		if (tipoUsuario=="cliente") {
			cargarDatosCliente(dao.cliente.getLogin(),dao,tipoUsuario);
		}else {
			cargarDatosCliente(dao.trabajador.getLogin(),dao,tipoUsuario);
		}
		
		

	}

	private void cargarDatosCliente(String login, DAO dao,String tipoUsuario) {
	
		Cliente cliente = null;
		
		try {
			dao.conectar();
			String query = "SELECT * FROM "+tipoUsuario+" WHERE login = ?";
			PreparedStatement statement = dao.conn.prepareStatement(query);
			statement.setString(1, login);
			boolean admin = false;
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				

			
				if (tipoUsuario=="cliente") {
					cliente = new Cliente(resultSet.getString("login"), resultSet.getString("contra"),
					resultSet.getString("email"), resultSet.getString("dni"), resultSet.getString("nombre"),
					resultSet.getString("apellidos"),
					dao.stringToDate(resultSet.getString("fechaNacimiento"), dao.format),
					resultSet.getInt("numTarjeta"), resultSet.getString("ofertasEnviadas"));
					numTarjetaField.setText("" + cliente.getNumTarjeta());
					loginField.setText(cliente.getLogin());
					emailField.setText(cliente.getEmail());
					dniField.setText(cliente.getdNI());
					contraField.setText(cliente.getContra());
					nombreField.setText(cliente.getNombre());
					apellidoField.setText(cliente.getApellidos());
					fechaNacimientoField.setText(dao.dateToString(cliente.getFechaNacimiento(), dao.format));
				}else {
					if (resultSet.getInt("esAdmin")==1) {
						trabajador.setAdmin(true);
					}
					trabajador = new Trabajador(resultSet.getString("login"), resultSet.getString("contra"),
					resultSet.getString("email"), resultSet.getString("dni"), resultSet.getString("nombre"),
					resultSet.getString("apellidos"),
					dao.stringToDate(resultSet.getString("fechaNacimiento"), dao.format),resultSet.getInt("sueldo"),admin,resultSet.getString("puesto"));
					loginField.setText(trabajador.getLogin());
					emailField.setText(trabajador.getEmail());
					dniField.setText(trabajador.getdNI());
					contraField.setText(trabajador.getContra());
					nombreField.setText(trabajador.getNombre());
					apellidoField.setText(trabajador.getApellidos());
					fechaNacimientoField.setText(dao.dateToString(trabajador.getFechaNacimiento(), dao.format));
					puestoField.setText(trabajador.getPuesto());
					sueldoField.setText(""+trabajador.getSueldo());
				}
				
			
				
				dao.desconectar();
			}
		} catch (SQLException e) {
			LoggerDeusto.log(Level.SEVERE, "No se cargan los clientes: "+e.getMessage());
		}
	}

	private void guardarCambios(DAO dao,String tipoUsuario) {
		try {
			String sentencia;
			int admin = 0;
			dao.conectar();
			if (tipoUsuario=="trabajador") {
				sentencia = " sueldo = ?, esAdmin = ?, puesto = ? ";
			}else {
				sentencia = " numTarjeta = ? ";
			}
			String query = "UPDATE "+tipoUsuario+" SET login = ?,contra = ?, email = ?, dni = ?, nombre = ?, apellidos = ?, fechaNacimiento = ?,"+ sentencia+"WHERE login = ?";
			PreparedStatement statement = dao.conn.prepareStatement(query);
			statement.setString(1, loginField.getText());
			System.out.print(loginField.getText());
			statement.setString(2, contraField.getText());
			System.out.print(contraField.getText());
			statement.setString(3, emailField.getText());
			System.out.print(emailField.getText());
			statement.setString(4, dniField.getText());
			System.out.print(dniField.getText());
			statement.setString(5, nombreField.getText());
			statement.setString(6, apellidoField.getText());
			statement.setString(7, fechaNacimientoField.getText());
			if (tipoUsuario=="cliente") {
				statement.setString(8, numTarjetaField.getText());
				statement.setString(9, dao.cliente.getLogin());
			}else {
				statement.setInt(8, Integer.parseInt(sueldoField.getText()));
				if (dao.trabajador.esAdmin()) {
					admin=1;
				}
				statement.setInt(9, admin);
				statement.setString(10, puestoField.getText());
				statement.setString(11, dao.trabajador.getLogin());
				
			}
			

			
			
			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated > 0) {
				JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente.");
			}

			dao.desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void hacerNoEditable(JPanel panel) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				((JTextField) component).setEditable(false);
			}
		}
	}

	private void hacerEditable(JPanel panel) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				((JTextField) component).setEditable(true);
			}
		}
	}
}
