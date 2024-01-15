package src.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;
import src.io.FicheroActividad;

public class VentanaAdministracion extends JFrame implements Serializable {

	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();
	private DefaultTableModel tableModel;

	public VentanaAdministracion(DAO dao) {

		setTitle("Consola de administrador: " + dao.trabajador.getLogin());
		setSize(980, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		tableModel = new DefaultTableModel();
		JTable trabajadoresTable = new JTable(tableModel);
		String[] columnasGestion = { "Usuario", "Contraseña", "Email", "DNI", "Nombre", "Apellidos",
				"Fecha de nacimiento", "Sueldo", "Puesto" };
		String[] columnasActividad = { "Fecha", "Actividad" };

		JScrollPane scrollPane = new JScrollPane(trabajadoresTable);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setLayout(new GridBagLayout());
		JPanel panelOpciones = new JPanel();
		panelOpciones.setLayout(new GridBagLayout());

		JButton gestionarButton = new JButton("Gestionar trabajadores");

		JButton actividadButton = new JButton("Ver actividad");

		JButton agregarButton = new JButton("Agregar trabajador");

		JButton eliminarButton = new JButton("Eliminar trabajador");

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(5, 5, 5, 5);

		gbc2.gridx = 7;
		buttonPanel.add(agregarButton, gbc2);
		panelOpciones.add(gestionarButton, gbc2);

		gbc2.gridx = 8;
		buttonPanel.add(eliminarButton, gbc2);
		panelOpciones.add(actividadButton, gbc2);

		agregarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				new VentanaAgregarTrabajador(dao);
				tableModel.setRowCount(0);
				dao.cargarDatosTrabajadores(tableModel);
				repaint();

			}

		});

		eliminarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = trabajadoresTable.getSelectedRow();
				String loginTrabajador = "" + trabajadoresTable.getValueAt(selectedRow, 0);
				dao.eliminarTrabajador(loginTrabajador);
				tableModel.setRowCount(0);
				dao.cargarDatosTrabajadores(tableModel);
				repaint();
			}

		});

		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panelOpciones, BorderLayout.NORTH);

		dao.cargarDatosTrabajadores(tableModel);

		repaint();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		gestionarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonPanel.add(agregarButton);
				buttonPanel.add(eliminarButton);
				tableModel.setColumnIdentifiers(columnasGestion);
				tableModel.setRowCount(0);
				dao.cargarDatosTrabajadores(tableModel);
				repaint();

			}

		});
		actividadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonPanel.remove(agregarButton);
				buttonPanel.remove(eliminarButton);
				tableModel.setRowCount(0);
				tableModel.setColumnIdentifiers(columnasActividad);

				List<String> actividades = FicheroActividad.cargarActividad();
				cargarActividad(actividades);
				repaint();

			}
		});
		setVisible(true);

	}

	public void cargarActividad(List<String> actividades) {
		for (String actividad : actividades) {
			// Suponemos que cada actividad tiene el formato "[" yyyy-MM-dd HH:mm:ss "]
			// actividad"
			String[] partes = actividad.split("]", 2);
			if (partes.length == 2) {
				String fechaHora = partes[0].substring(1); // Eliminar el "[" al inicio
				String actividadTexto = partes[1].trim();
				tableModel.addRow(new Object[] { fechaHora, actividadTexto });
			} else {
				// Tratar las actividades que no cumplen el formato esperado
				System.err.println("Formato de actividad no reconocido: " + actividad);
			}
		}
	}
}
