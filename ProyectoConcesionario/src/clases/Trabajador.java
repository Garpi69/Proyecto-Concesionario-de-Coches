package clases;

import java.util.Date;

public class Trabajador extends Persona{

	private int sueldo;
	private boolean esAdmin;
	private String puesto;

	public Trabajador(String login, String contra, String email, String dni, String nombre, String apellidos,
			Date fechaNacimiento, int sueldo, boolean esAdmin, String puesto) {
		super(login, contra, email, dni, nombre, apellidos);
		this.sueldo = sueldo;
		this.esAdmin = esAdmin;
		this.puesto = puesto;
	}

	public Trabajador() {
		super("", "", "", "", "", "");
		this.sueldo = 0;
		this.esAdmin = false;
		this.puesto = "";
	}

	public Trabajador(Trabajador t) {
		super(t.login, t.contra, t.email, t.dni, t.nombre, t.apellidos);
		this.sueldo = t.sueldo;
		this.esAdmin = t.esAdmin;
		this.puesto = t.puesto;
	}

	public int getSueldo() {
		return sueldo;
	}

	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

	public boolean esAdmin() {
		return esAdmin;
	}

	public void setAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	@Override
	public String toString() {
		String admin;
		if (this.esAdmin) {
			admin = "es";
		} else {
			admin = "no es";
		}
		return nombre + " " + apellidos + " con dni " + dni + " " + admin + " administrador y posee un sueldo de " + sueldo + "como" + puesto;
	}


}
