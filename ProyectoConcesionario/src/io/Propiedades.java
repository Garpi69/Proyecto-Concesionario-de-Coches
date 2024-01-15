package src.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Propiedades {
	private static Properties properties;
	private static String jbdcUrl;

	private static int numeroDeSerie;

	static {
		cargarPropiedades();
	}

	private static void cargarPropiedades() {
		String numeroDeSerieString;
		properties = new Properties();
		try (FileInputStream input = new FileInputStream("config/config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jbdcUrl = properties.getProperty("jdbc.url");
		numeroDeSerieString = properties.getProperty("numeroDeSerie");
		numeroDeSerie = (numeroDeSerieString != null) ? Integer.parseInt(numeroDeSerieString) : 0;
	}

	public static String getJbdcUrl() {
		return jbdcUrl;
	}

	public static int getNumeroDeSerie() {
		return numeroDeSerie;
	}
}
