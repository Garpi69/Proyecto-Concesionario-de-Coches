package clases;

import java.util.Date;


public class Cliente extends Persona {

	private long numTarjeta;
	private String ofertasEnviadas;

	public Cliente(String login, String contra, String email, String dni, String nombre, String apellidos,Date fechaNacimiento,
			 long numTarjeta,String ofertasEnviadas) {
		super(login, contra, email, dni, nombre, apellidos,fechaNacimiento);
		this.numTarjeta = numTarjeta;
		this.ofertasEnviadas=ofertasEnviadas;
	}

	public Cliente() {
		super("", "", "", "", "","",null);
		this.numTarjeta = 0000000000000000;
		this.ofertasEnviadas="";
	}

	public Cliente(Cliente c) {
		super(c.login, c.contra, c.email, c.dni, c.nombre, c.apellidos,c.fechaNacimiento);
		this.numTarjeta = c.numTarjeta;
		this.ofertasEnviadas=c.ofertasEnviadas;
	}

	public String getOfertasEnviadas() {
		return ofertasEnviadas;
	}

	public void setOfertasEnviadas(String ofertasEnviadas) {
		this.ofertasEnviadas = ofertasEnviadas;
	}
	public String[] getOfertasEnviadasArray() {
		String[] ofertasEnviadasArray = ofertasEnviadas.split(",");
		return ofertasEnviadasArray;
		
	}
	public void setOfertasEnviadasArray(String[] ofertasEnviadasArray) {
		String ofertasEnviadas = "";
		for (String oferta: ofertasEnviadasArray) {
			ofertasEnviadas = ofertasEnviadas +","+oferta;
		}
		this.ofertasEnviadas=ofertasEnviadas;
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