package clases;

import java.util.Date;

public class CocheSegundaMano extends Coche implements SegundaMano {
	
	
	
	private int kilometraje;



	public CocheSegundaMano() {
		super();
		// TODO Auto-generated constructor stub
	}




	




	public CocheSegundaMano(int idVehiculo, String combustible, String marca, String modelo, String color, String tipo,
			int potencia, int numPlazas, int precio, int cuota, Date matriculacion, int kilometraje) {
		super(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion);
		// TODO Auto-generated constructor stub
		this.kilometraje=kilometraje;
	}









	public CocheSegundaMano(Vehiculo v) {
		super(v);
		// TODO Auto-generated constructor stub
	}




	public void setKilometraje(int kilometraje) {
		this.kilometraje = kilometraje;
	}

	
	
	
	public int getKilometraje() {
		return kilometraje;
	}

	


	@Override
	public void kilometraje(int km) {
		// TODO Auto-generated method stub
		
	}

}
