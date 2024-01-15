package src.gui;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import src.db.DAO;
import src.domain.Cita;
import src.domain.GeneradorNumeroSerie;
import src.io.FicheroCitas;

public class VentanaVerCitas extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();
	private JTable tablaCitas;
	private DefaultTableModel model;

	public VentanaVerCitas(DAO dao) {
		super("Citas");

		model = new DefaultTableModel();
		tablaCitas = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tablaCitas);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(980, 400);
		setLocationRelativeTo(null);

		add(scrollPane);

		cargarCitasEnTabla();
		setVisible(true);
	}

	private void cargarCitasEnTabla() {
		ArrayList<Cita> arrayCitas = FicheroCitas.leerCitas();
		// Ordenar la lista de citas por fecha de manera recursiva
		ordenarCitasRecursivamente(arrayCitas, 0, arrayCitas.size() - 1);

		// Crear el modelo de tabla
		model = new DefaultTableModel();
		model.addColumn("Fecha");
		model.addColumn("Usuario");
		model.addColumn("Nombre del Vehículo");

		// Agregar filas al modelo
		for (Cita cita : arrayCitas) {
			Object[] fila = { cita.getFecha(), cita.getUsuario(), cita.getNombreVehiculo() };
			model.addRow(fila);
		}

		// Establecer el modelo en la JTable
		tablaCitas.setModel(model);
	}

	private void ordenarCitasRecursivamente(ArrayList<Cita> citas, int izquierda, int derecha) {
		if (izquierda < derecha) {
			int indiceParticion = particion(citas, izquierda, derecha);
			ordenarCitasRecursivamente(citas, izquierda, indiceParticion - 1);
			ordenarCitasRecursivamente(citas, indiceParticion + 1, derecha);
		}
	}

	private int particion(ArrayList<Cita> citas, int izquierda, int derecha) {
		Cita pivote = citas.get(derecha);
		int indiceMenor = izquierda - 1;

		for (int j = izquierda; j < derecha; j++) {
			if (citas.get(j).compareTo(pivote) <= 0) {
				indiceMenor++;
				Collections.swap(citas, indiceMenor, j);
			}
		}

		Collections.swap(citas, indiceMenor + 1, derecha);
		return indiceMenor + 1;
	}

}
