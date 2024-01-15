package src.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import src.domain.Cita;

public class FicheroCitas {
	public static void añadirCita(Cita cita) {
		
		crearFicheroSiNoExiste();

		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./resources/data/appointments/appointment-log.txt", true))) {
			writer.write(cita.toString());
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Cita> leerCitas() {				// Esta función ha sido creada con ayuda de IAG, pero se ha tenido que modificar para adapartla a
															// este programa. Ha servido para entender como hacer una buena lectura teniendo en cuenta el formato de las citas
		crearFicheroSiNoExiste();							// -Jon
		ArrayList<Cita> arrayCitas = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader("./resources/data/appointments/appointment-log.txt"))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				Cita cita = construirCitaDesdeString(linea);
				arrayCitas.add(cita);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayCitas;
	}

	private static void crearFicheroSiNoExiste() {
		File fichero = new File("./resources/data/appointments/appointment-log.txt");
		if (!fichero.exists()) {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Cita construirCitaDesdeString(String cadenaCita) {
		String[] partes = cadenaCita.split(", ");

		String fecha = partes[0].substring(partes[0].indexOf("=") + 1);
		String usuario = partes[1].substring(partes[1].indexOf("=") + 1);
		String nombreVehiculo = partes[2].substring(partes[2].indexOf("=") + 1, partes[2].length() - 1);

	
		return new Cita(fecha, usuario, nombreVehiculo);
	}
}
