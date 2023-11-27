package clases;

import java.util.Date;

public class MotoSegundaMano extends Moto implements SegundaMano{

	private int kilometraje;

	

	public MotoSegundaMano() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MotoSegundaMano(int idVehiculo,String combustible, String marca, String modelo, String color, String tipo, int potencia,
			int numPlazas, int precio, int cuota, Date matriculacion, int peso, boolean baul, int kilometraje) {
		super(idVehiculo, combustible,marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, peso, baul);
			this.kilometraje = kilometraje;
	}

	public MotoSegundaMano(Moto m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	public int getKilometraje() {
		return kilometraje;
	}

	
	public void kilometraje(int km) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
