package clases;

import java.util.Date;


public class Coche extends Vehiculo {




	public Coche() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coche(int idVehiculo,String combustible, String marca, String modelo, String color, String tipo, int potencia, int numPlazas,
			int precio, int cuota, Date matriculacion,String ofertas, String propietario) {
		super(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, ofertas, propietario);
		this.combustible=combustible;
	}

	public Coche(Vehiculo v) {
		super(v);
		// TODO Auto-generated constructor stub
	}











}
