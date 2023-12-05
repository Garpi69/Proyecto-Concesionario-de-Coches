package clases;

import java.util.Date;


public class Moto extends Vehiculo {

	protected int peso;
	protected boolean baul;
	
	public Moto(int idVehiculo,String combustible, String marca, String modelo, String color, String tipo, int potencia, int numPlazas, int precio, int cuota, Date matriculacion, int peso, boolean baul) {
		super(idVehiculo,combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion);
		this.peso = peso;
		this.baul = baul;
	}

	public Moto() {
		super(0,"","", "", "", "", 0, 0, 0, 0, null);
		this.peso = 0;
		this.baul = false;
	}
	
	public Moto(Moto m) {
		super(m.idVehiculo,m.combustible,m.marca, m.modelo, m.color, m.tipo, m.potencia, m.numPlazas, m.precio, m.cuota, m.matriculacion);
		this.peso = m.peso;
		this.baul = m.baul;
	}
	
	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public boolean isBaul() {
		return baul;
	}

	public void setBaul(boolean baul) {
		this.baul = baul;
	}

	protected String baulString() {
		String b;

		if (baul) {
			b = " con baul";
		} else {
			b = " sin baul";
		}
		return b;
	}
	
}

	
