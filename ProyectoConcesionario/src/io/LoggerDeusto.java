package src.io;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import src.db.DAO;

public class LoggerDeusto {
	private static Logger logger = Logger.getLogger(DAO.class.getName());
	private static FileHandler fileHandler;

	static {
		try {
			inicializarLogger();
			logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido inicializar el logger: " + e.getMessage());
		}
	}

	public static void log(Level level, String message) {
		logger.log(level, message);
	}

	private static void inicializarLogger() throws SecurityException, IOException {
		if (fileHandler == null) {
			fileHandler = new FileHandler("log/log-" + LocalDate.now() + LocalDateTime.now() + ".log");
			fileHandler.setFormatter(new SimpleFormatter());
		}
	}
}