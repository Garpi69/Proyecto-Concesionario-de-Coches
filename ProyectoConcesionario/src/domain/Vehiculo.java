package src.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Vehiculo {
	protected int idVehiculo;

	protected String combustible;
	protected String marca;
	protected String modelo;
	protected String color;
	protected String tipo;
	protected int potencia;
	protected int numPlazas;
	protected int precio;
	protected int cuota;
	protected Date matriculacion;
	protected String ofertas;
	protected String propietario;

	public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public Vehiculo(int idVehiculo, String combustible, String marca, String modelo, String color, String tipo,
			int potencia, int numPlazas, int precio, int cuota, Date matriculacion, String ofertas,
			String propietario) {
		super();
		this.idVehiculo = idVehiculo;

		this.combustible = combustible;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.tipo = tipo;
		this.potencia = potencia;
		this.numPlazas = numPlazas;
		this.precio = precio;
		this.cuota = cuota;
		this.matriculacion = matriculacion;
		this.ofertas = ofertas;
		this.propietario = propietario;
	}

	public Vehiculo() {
		super();
		this.idVehiculo = 0;

		this.combustible = "";
		this.marca = "";
		this.modelo = "";
		this.color = "";
		this.tipo = "";
		this.potencia = 0;
		this.numPlazas = 0;
		this.precio = 0;
		this.cuota = 0;
		this.matriculacion = null;
		this.ofertas = null;
		this.propietario = "";
	}

	public Vehiculo(Vehiculo v) {
		super();
		this.idVehiculo = v.idVehiculo;
		this.combustible = v.combustible;
		this.marca = v.marca;
		this.modelo = v.modelo;
		this.color = v.color;
		this.tipo = v.tipo;
		this.potencia = v.potencia;
		this.numPlazas = v.numPlazas;
		this.precio = v.precio;
		this.cuota = v.cuota;
		this.matriculacion = v.matriculacion;
		this.ofertas = v.ofertas;
		this.propietario = v.propietario;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public int getNumPlazas() {
		return numPlazas;
	}

	public void setNumPlazas(int numPlazas) {
		this.numPlazas = numPlazas;
	}

	public int getCuota() {
		return cuota;
	}

	public void setCuota(int cuota) {
		this.cuota = cuota;
	}

	public Date getMatriculacion() {
		return matriculacion;
	}

	public void setMatriculacion(Date matriculacion) {
		this.matriculacion = matriculacion;
	}

	public static SimpleDateFormat getDf() {
		return df;
	}

	public static void setDf(SimpleDateFormat df) {
		Vehiculo.df = df;
	}

	public String getOfertas() {
		return ofertas;
	}

	public void setOfertas(String ofertas) {
		this.ofertas = ofertas;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public void setidVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public int getidVehiculo() {
		return idVehiculo;
	}

}