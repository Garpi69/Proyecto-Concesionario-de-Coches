package src.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;

public class VentanaMenuClienteVerOfertas extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();
	private DAO dao = new DAO();

	public VentanaMenuClienteVerOfertas() {

		setTitle("Ofertas");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable ofertasTable = new JTable(tableModel);
		String[] columnas = { "Vehiculo", "Oferta (€)", "Usuario" };
		tableModel.setColumnIdentifiers(columnas);
		JButton enviadasButton = new JButton("Ofertas enviadas");

		JButton recibidasButton = new JButton("Ofertas recibidas");

		recibidasButton.addActionListener(e -> {
			try {

				dao.cargarOfertasRecibidas("coche", tableModel, dao.cliente.getLogin());
				dao.cargarOfertasRecibidas("cocheSegundaMano", tableModel, dao.cliente.getLogin());
				dao.cargarOfertasRecibidas("moto", tableModel, dao.cliente.getLogin());
				dao.cargarOfertasRecibidas("motoSegundaMano", tableModel, dao.cliente.getLogin());
			} catch (AWTException e1) {

			}

		});
		enviadasButton.addActionListener(e -> {

			dao.cargarOfertasEnviadas("coche", tableModel, dao.cliente.getLogin());
			dao.cargarOfertasEnviadas("cocheSegundaMano", tableModel, dao.cliente.getLogin());
			dao.cargarOfertasEnviadas("moto", tableModel, dao.cliente.getLogin());
			dao.cargarOfertasEnviadas("motoSegundaMano", tableModel, dao.cliente.getLogin());
		});
		JScrollPane scrollPane = new JScrollPane(ofertasTable);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(5, 5, 5, 5);

		gbc2.gridx = 7;
		buttonPanel.add(enviadasButton, gbc2);

		gbc2.gridx = 8;
		buttonPanel.add(recibidasButton, gbc2);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int selectedRow = ofertasTable.getSelectedRow();
				if (selectedRow != -1) {
					Object cocheAceptado = ofertasTable.getValueAt(selectedRow, 0);
					Object precioAceptado = ofertasTable.getValueAt(selectedRow, 1);
					String precioAceptadoString = precioAceptado.toString();
					int precioAceptadoInt = Integer.parseInt(precioAceptadoString);
					Object usuarioAceptado = ofertasTable.getValueAt(selectedRow, 2);
					String cocheAceptadoString = cocheAceptado.toString();
					String usuarioAceptadoString = usuarioAceptado.toString();
					System.out.print(cocheAceptadoString);
					dao.eliminarVehiculo(cocheAceptadoString, "coche");
					dao.eliminarVehiculo(cocheAceptadoString, "cocheSegundaMano");
					dao.eliminarVehiculo(cocheAceptadoString, "moto");
					dao.eliminarVehiculo(cocheAceptadoString, "motoSegundaMano");

					JOptionPane.showMessageDialog(null, "Felicidades, " + ofertasTable.getValueAt(selectedRow, 1)
							+ "€ serán ingresados en su cuenta");
					dao.eliminarOferta(cocheAceptadoString, usuarioAceptadoString, precioAceptadoInt);
					dao.cargarOfertasEnviadas("coche", tableModel, dao.cliente.getLogin());
					dao.cargarOfertasEnviadas("cocheSegundaMano", tableModel, dao.cliente.getLogin());
					dao.cargarOfertasEnviadas("moto", tableModel, dao.cliente.getLogin());
					dao.cargarOfertasEnviadas("motoSegundaMano", tableModel, dao.cliente.getLogin());
					ofertasTable.repaint();

				}
			}
		});

		JButton rechazarButton = new JButton("Rechazar");
		rechazarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int selectedRow = ofertasTable.getSelectedRow();
				if (selectedRow != -1) {
					dao.eliminarOferta("" + ofertasTable.getValueAt(selectedRow, 0),
							"" + ofertasTable.getValueAt(selectedRow, 2),
							(int) ofertasTable.getValueAt(selectedRow, 1));

					ofertasTable.repaint();
				}
			}
		});

		gbc2.insets = new Insets(5, 5, 5, 5);

		gbc2.gridx = 9;
		buttonPanel.add(aceptarButton, gbc2);

		gbc2.gridx = 10;
		buttonPanel.add(rechazarButton, gbc2);

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
