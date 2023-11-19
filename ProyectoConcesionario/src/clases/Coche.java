package clases;

import java.util.Date;


public class Coche extends Vehiculo {

	protected String combustible;
	
	public Coche(String marca, String modelo, Color color, String tipo, int potencia, int numPlazas, int precio, int cuota, Date matriculacion, String combustible) {
		super(marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion);
		this.combustible = combustible;
	}
	
	public Coche() {
		super("", "", null, "", 0, 0, 0, 0, null);
		this.combustible = "";
	}
	
	public Coche(Coche c) {
		super(c.marca, c.modelo, c.color, c.tipo, c.potencia, c.numPlazas, c.precio, c.cuota, c.matriculacion);
		this.combustible = c.combustible;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	
} 
