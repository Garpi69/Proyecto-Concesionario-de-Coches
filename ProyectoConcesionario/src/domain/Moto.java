package src.domain;

import java.util.Date;

public class Moto extends Vehiculo {

	protected int peso;
	protected boolean baul;

	public Moto(int idVehiculo, String combustible, String marca, String modelo, String color, String tipo,
			int potencia, int numPlazas, int precio, int cuota, Date matriculacion, int peso, boolean baul,
			String ofertas, String propietario) {
		super(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,
				ofertas, propietario);
		this.peso = peso;
		this.baul = baul;
	}

	public Moto() {
		super(0, "", "", "", "", "", 0, 0, 0, 0, null, null, "");
		this.peso = 0;
		this.baul = false;
	}

	public Moto(Moto m) {
		super(m.idVehiculo, m.combustible, m.marca, m.modelo, m.color, m.tipo, m.potencia, m.numPlazas, m.precio,
				m.cuota, m.matriculacion, m.ofertas, m.propietario);
		this.peso = m.peso;
		this.baul = m.baul;
	}

	@Override
	public int getIdVehiculo() {
		return idVehiculo;
	}

	@Override
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
