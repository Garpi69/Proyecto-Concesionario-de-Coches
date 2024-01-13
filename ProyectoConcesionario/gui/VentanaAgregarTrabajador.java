package gui;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import db.DAO;
import domain.GeneradorNumeroSerie;
import domain.Trabajador;

public class VentanaAgregarTrabajador extends JFrame {

	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	private JFrame frame;
	private JTextField loginField, contraField, emailField, dniField, nombreField, apellidosField, fechaNacimientoField,
			sueldoField, puestoField;
	private JCheckBox esAdminCheckBox;

	public VentanaAgregarTrabajador(DAO dao) {
		frame = new JFrame("Agregar Trabajador");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columnas

		frame.add(new JLabel("Login:"));
		loginField = new JTextField();
		frame.add(loginField);

		frame.add(new JLabel("Contraseña:"));
		contraField = new JTextField();
		frame.add(contraField);

		frame.add(new JLabel("Email:"));
		emailField = new JTextField();
		frame.add(emailField);

		frame.add(new JLabel("DNI:"));
		dniField = new JTextField();
		frame.add(dniField);

		frame.add(new JLabel("Nombre:"));
		nombreField = new JTextField();
		frame.add(nombreField);

		frame.add(new JLabel("Apellidos:"));
		apellidosField = new JTextField();
		frame.add(apellidosField);

		frame.add(new JLabel("Fecha de Nacimiento (dd/MM/yyyy):"));
		fechaNacimientoField = new JTextField();
		frame.add(fechaNacimientoField);

		frame.add(new JLabel("Sueldo:"));
		sueldoField = new JTextField();
		frame.add(sueldoField);

		frame.add(new JLabel("Puesto:"));
		puestoField = new JTextField();
		frame.add(puestoField);

		frame.add(new JLabel("¿Es Administrador?"));
		esAdminCheckBox = new JCheckBox();
		frame.add(esAdminCheckBox);

		JButton guardarButton = new JButton("Guardar");
		guardarButton.addActionListener(e -> guardarDatos(dao));
		frame.add(guardarButton);

		frame.pack();
		frame.setVisible(true);
	}

	private void guardarDatos(DAO dao) {
		String login = loginField.getText();
		String contra = contraField.getText();
		String email = emailField.getText();
		String dni = dniField.getText();
		String nombre = nombreField.getText();
		String apellidos = apellidosField.getText();
		String fechaNacimientoStr = fechaNacimientoField.getText();
		int sueldo = Integer.parseInt(sueldoField.getText());
		String puesto = puestoField.getText();
		boolean esAdmin = esAdminCheckBox.isSelected();
		Date fechaNacimiento = dao.stringToDate(fechaNacimientoStr, dao.format);

		Trabajador trabajador = new Trabajador(login, contra, email, dni, nombre, apellidos, fechaNacimiento, sueldo,
				esAdmin, puesto);
		try {
			dao.agregarTrabajador(trabajador);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
