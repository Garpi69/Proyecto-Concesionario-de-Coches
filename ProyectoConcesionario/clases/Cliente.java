package clases;

import java.util.Date;


public class Cliente extends Persona {

	private long numTarjeta;

	public Cliente(String login, String contra, String email, String dni, String nombre, String apellidos,Date fechaNacimiento,
			 long numTarjeta) {
		super(login, contra, email, dni, nombre, apellidos,fechaNacimiento);
		this.numTarjeta = numTarjeta;
	}

	public Cliente() {
		super("", "", "", "", "","",null);
		this.numTarjeta = 0000000000000000;
	}

	public Cliente(Cliente c) {
		super(c.login, c.contra, c.email, c.dni, c.nombre, c.apellidos,c.fechaNacimiento);
		this.numTarjeta = c.numTarjeta;
	}

	public long getNumTarjeta() {
		return numTarjeta;
	}

	public void setNumTarjeta(long numTarjeta) {
		this.numTarjeta = numTarjeta;
	}

	@Override
	public String toString() {
		return nombre + " " + apellidos + ", con dni " + dni + " y n�mero de tarjeta " + numTarjeta;
	}
}