package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.WindowConstants;

import db.DAO;
import domain.Cliente;
import domain.GeneradorNumeroSerie;

public class VentanaRegistroCliente extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	protected JTextField textoUsuario, textoEmail, textoDni, textoNombre, textoApellidos, textoNumTarjeta;
	protected JPasswordField textoPassword, textoRepetirPassword;
	protected JButton botonSignUp, botonCancelar;
	protected JSpinner fechaNacimientoSpinner;

	public VentanaRegistroCliente(DAO dao) {
		setTitle("Registro");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);

		JPanel panelSignUp = new JPanel();
		panelSignUp.setLayout(new GridLayout(9, 2, 10, 10));
		panelSignUp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		fechaNacimientoSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaNacimientoSpinner, "dd/MM/yyyy");
		fechaNacimientoSpinner.setEditor(dateEditor);

		textoUsuario = new JTextField();
		textoPassword = new JPasswordField();
		textoRepetirPassword = new JPasswordField();
		textoEmail = new JTextField();
		textoDni = new JTextField();
		textoNombre = new JTextField();
		textoApellidos = new JTextField();
		textoNumTarjeta = new JTextField();

		panelSignUp.add(new JLabel("Nombre de Usuario:"));
		panelSignUp.add(textoUsuario);
		panelSignUp.add(new JLabel("Contraseña:"));
		panelSignUp.add(textoPassword);
		panelSignUp.add(new JLabel("Repita la contraseña:"));
		panelSignUp.add(textoRepetirPassword);
		panelSignUp.add(new JLabel("Email:"));
		panelSignUp.add(textoEmail);
		panelSignUp.add(new JLabel("DNI:"));
		panelSignUp.add(textoDni);
		panelSignUp.add(new JLabel("Nombre:"));
		panelSignUp.add(textoNombre);
		panelSignUp.add(new JLabel("Apellidos:"));
		panelSignUp.add(textoApellidos);
		panelSignUp.add(new JLabel("Número de tarjeta:"));
		panelSignUp.add(textoNumTarjeta);

		panelSignUp.add(new JLabel("Fecha de nacimiento:"));
		panelSignUp.add(fechaNacimientoSpinner);
		this.add(panelSignUp, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		botonSignUp = new JButton("Registrarme");
		botonCancelar = new JButton("Cancelar");

		panelBotones.add(botonSignUp);
		panelBotones.add(botonCancelar);

		botonSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					Cliente cliente = crearObjetoCliente(dao);
					dao.agregarCliente(cliente);

					JOptionPane.showMessageDialog(null, "Informacion Guardada", "Guardado", JOptionPane.PLAIN_MESSAGE);
					new VentanaInicio(dao);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR", "No se ha podido guardar el usuario",
							JOptionPane.PLAIN_MESSAGE);
					e1.printStackTrace();
				}
			}
		});

		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		this.add(panelBotones, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public Cliente crearObjetoCliente(DAO dao) {
		String nombreUsuario = textoUsuario.getText();
		String contrasena = new String(textoPassword.getPassword());
		String email = textoEmail.getText();
		String dni = textoDni.getText();
		String nombre = textoNombre.getText();
		String apellidos = textoApellidos.getText();
		long numTarjeta = Long.parseLong(textoNumTarjeta.getText());

		String fechaNacimientoString = dao.dateToString((Date) fechaNacimientoSpinner.getValue(), dao.format);
		Date fechaNacimiento = null;
		fechaNacimiento = dao.stringToDate(fechaNacimientoString, dao.format);
		Cliente cliente = new Cliente(nombreUsuario, contrasena, email, dni, nombre, apellidos, fechaNacimiento,
				numTarjeta, "");
		return cliente;
	}

}
