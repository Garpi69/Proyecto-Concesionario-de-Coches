package src.main;

import src.db.DAO;
import src.gui.VentanaInicio;

public class Main {

	private static DAO dao = new DAO();

	public static void main(String[] args) {
		new VentanaInicio(dao);
	}

}
