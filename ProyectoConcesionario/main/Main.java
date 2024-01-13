package main;

import db.DAO;
import gui.VentanaInicio;

public class Main {
	private static DAO dao = new DAO();
	public static void main(String[] args) {
		 new VentanaInicio(dao);
	}

}
