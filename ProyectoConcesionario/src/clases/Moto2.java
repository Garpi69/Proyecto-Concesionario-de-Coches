package clases;

import java.util.Date;

public class Moto2 extends Moto implements SegundaMano{

	private int kilometraje;

	public Moto2(String marca, String modelo, Color color, String tipo, int potencia, int numPlazas, 
			int precio, int cuota, Date matriculacion, int peso, boolean baul, int kilometraje) {
		super(marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, peso, baul);
		this.kilometraje = kilometraje;
	}
	
	public Moto2() {
		super("", "", null, "", 0, 0, 0, 0, null, 0, false);
		this.kilometraje = 0;
	}
	
	public Moto2(Moto2 m) {
		super(m.marca, m.modelo, m.color, m.tipo, m.potencia, m.numPlazas, m.precio, m.cuota, m.matriculacion, m.peso, m.baul);
		this.kilometraje = m.kilometraje;
	}

	public int getKilometraje() {
		return kilometraje;
	}

	@Override
	public void numKilometros(int km) {
		this.kilometraje = km;
	}
	
	
	
}
