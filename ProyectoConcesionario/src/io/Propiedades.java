package src.io;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

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
		  try (InputStream input = Propiedades.class.getClassLoader().getResourceAsStream("config/config.properties")) {
	            if (input != null) {
	                properties.load(input);
	            } else {
	               JOptionPane.showMessageDialog(null,"No se pudo cargar el archivo de propiedades.");
	            }
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
