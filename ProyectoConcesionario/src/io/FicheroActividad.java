package src.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FicheroActividad {

	public static void crearSiNoExiste() throws IOException {
		Path projectPath = FileSystems.getDefault().getPath("").toAbsolutePath();
		String filePath = projectPath.toString() + "/resources/data/activity/activity-log.txt";

		File file = new File(filePath);

		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public static void guardarActividad(String activity) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());

		Path projectPath = FileSystems.getDefault().getPath("").toAbsolutePath();
		String filePath = projectPath.toString() + "/resources/data/activity/activity-log.txt";

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write("[" + formattedDate + "] " + activity);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> cargarActividad() {									//Esta función ha sido creada con ayuda de IAG, al igual que la del FicheroCitas. Ha sido modificado 
		List<String> actividad = new ArrayList<>();									// ya que la IAG solo ofrecía un ejemplo de como leer desde un archivo de texto.
																					// -Jon
		Path projectPath = FileSystems.getDefault().getPath("").toAbsolutePath();
		String filePath = projectPath.toString() + "/resources/data/activity/activity-log.txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;

			while ((line = reader.readLine()) != null) {
				actividad.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al leer el archivo de registro.");
		}
		ordenarRecursivo(actividad, actividad.size());

		return actividad;
	}

	static class ComparadorFechaActividad implements Comparator<String> {
		private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		@Override
		public int compare(String activity1, String activity2) {
			try {
				Date date1 = dateFormat.parse(activity1.substring(1, activity1.indexOf("]")));
				Date date2 = dateFormat.parse(activity2.substring(1, activity2.indexOf("]")));

				return date1.compareTo(date2);
			} catch (ParseException e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	private static void ordenarRecursivo(List<String> lista, int n) {
		if (n == 1) {
			return;
		}

		for (int i = 0; i < n - 1; i++) {
			if (lista.get(i).compareTo(lista.get(i + 1)) > 0) {
				intercambiar(lista, i, i + 1);
			}
		}

		ordenarRecursivo(lista, n - 1);
	}

	private static void intercambiar(List<String> lista, int i, int j) {
		String temp = lista.get(i);
		lista.set(i, lista.get(j));
		lista.set(j, temp);
	}
}
