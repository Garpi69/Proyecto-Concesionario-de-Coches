package clases;

import java.util.Date;

public class Coche2 extends Coche implements SegundaMano {

	private int kilometraje;
	
	public Coche2(String marca, String modelo, Color color, String tipo, int potencia, int numPlazas, int precio,
			int cuota, Date matriculacion, String combustible, int kilometraje) {
		super(marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, combustible);
		this.kilometraje = kilometraje;
	}

	public Coche2() {
		super("", "", null, "", 0, 0, 0, 0, null, "");
		this.kilometraje = 0;
	}

	public Coche2(Coche2 c) {
		super(c.marca, c.modelo, c.color, c.tipo, c.potencia, c.numPlazas, c.precio, c.cuota, c.matriculacion, c.combustible);
		this.kilometraje = c.kilometraje;
	}
	
	public int getKilometraje() {
		return kilometraje;
	}

	@Override
	public void numKilometros(int km) {
		this.kilometraje = km;	
	}

}
