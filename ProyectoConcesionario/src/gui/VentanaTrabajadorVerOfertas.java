package src.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;

public class VentanaTrabajadorVerOfertas extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	public VentanaTrabajadorVerOfertas(DAO dao) {

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
				dao.cargarOfertasRecibidas("coche", tableModel, "concesionario");
				dao.cargarOfertasRecibidas("cocheSegundaMano", tableModel, "concesionario");
				dao.cargarOfertasRecibidas("moto", tableModel, "concesionario");
				dao.cargarOfertasRecibidas("motoSegundaMano", tableModel, "concesionario");
			} catch (AWTException e1) {

			}

		});
		enviadasButton.addActionListener(e -> {
			dao.cargarOfertasEnviadas("coche", tableModel, "concesionario");
			dao.cargarOfertasEnviadas("cocheSegundaMano", tableModel, "concesionario");
			dao.cargarOfertasEnviadas("moto", tableModel, "concesionario");
			dao.cargarOfertasEnviadas("motoSegundaMano", tableModel, "concesionario");
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

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
