package clases;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Vehiculo {

	protected String marca;
	protected String modelo;
	protected Color color;
	protected String tipo;
	protected int potencia;
	protected int numPlazas;
	protected int precio;
	protected int cuota;
	protected Date matriculacion;
	public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");


	public Vehiculo(String marca, String modelo, Color color, String tipo, int potencia, int numPlazas, int precio, int cuota, Date matriculacion) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.tipo = tipo;
		this.potencia = potencia;
		this.numPlazas = numPlazas;
		this.precio = precio;
		this.cuota = cuota;
		this.matriculacion = matriculacion;
	}
	
	public Vehiculo() {
		super();
		this.marca = "";
		this.modelo = "";
		this.color = null;
		this.tipo = "";
		this.potencia = 0;
		this.numPlazas = 0;
		this.precio = 0;
		this.cuota = 0;
		this.matriculacion = null;
	}

	public Vehiculo(Vehiculo v) {
		super();
		this.marca = v.marca;
		this.modelo = v.modelo;
		this.color = v.color;
		this.tipo = v.tipo;
		this.potencia = v.potencia;
		this.numPlazas = v.numPlazas;
		this.precio = v.precio;
		this.cuota = v.cuota;
		this.matriculacion = v.matriculacion;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
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

	public void setColorString(String string) {
		switch (string) {
		case "negro":
			Color colorN = Color.NEGRO;
			setColor(colorN);
			break;
		case "gris":
			Color colorG = Color.GRIS;
			setColor(colorG);
			break;
		case "blanco":
			Color colorB = Color.BLANCO;
			setColor(colorB);
			break;
		case "rojo":
			Color colorR = Color.ROJO;
			setColor(colorR);
			break;
		case "azul":
			Color colorA = Color.AZUL;
			setColor(colorA);
			break;
		case "verde":
			Color colorV = Color.VERDE;
			setColor(colorV);
			break;
		case "dorado":
			Color colorD = Color.DORADO;
			setColor(colorD);
			break;
		case "marron":
			Color colorM = Color.MARRON;
			setColor(colorM);
		default:
			break;  
		}
	}	

	public String getColorString() {
		return color.toString().toLowerCase();
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}


}