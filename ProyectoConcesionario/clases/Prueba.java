package clases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prueba {
	
	private Persona cliente;
	private Vehiculo vehiculo;
	private Date fechaInicio;
	private Date fechaFin;
	public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private String ciudad;
	
	public Prueba(Persona cliente, Vehiculo vehiculo, Date fechaInicio, Date fechaFin, String ciudad) {
		super();
		this.cliente = cliente;
		this.vehiculo = vehiculo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.ciudad = ciudad;
	}

	public Prueba() {
		super();
		this.cliente = null;
		this.vehiculo = null;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.ciudad = null;
	}

	public Prueba(Prueba p) {
		super();
		this.cliente = p.cliente;
		this.vehiculo = p.vehiculo;
		this.fechaInicio = p.fechaInicio;
		this.fechaFin = p.fechaFin;
		this.ciudad = p.ciudad;
	}

	public Persona getCliente() {
		return cliente;
	}

	public void setCliente(Persona cliente) {
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getfechaFin() {
		return fechaFin;
	}

	public void setfechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setFechaInicioString (String fechaInicio) throws ParseException {
		this.fechaInicio = df.parse(fechaInicio);
	}
	
	public String getFechaInicioString() {
		return df.format(fechaInicio);
	}
	
	public void setfechaFinString (String fechaFin) throws ParseException {
		this.fechaFin = df.parse(fechaFin);
	}
	
	public String getfechaFinString() {
		return df.format(fechaFin);
	}

	@Override
	public String toString() {
		return "El cliente " + cliente.getNombre() + " " + cliente.getApellidos() + " con DNI " + cliente.getdNI() +
				" ha solicitado la prueba de " + vehiculo.toString() + " en " + ciudad + " el dia " + getFechaInicioString() +
				" siendo la fecha de vencimiento el dia " + getfechaFinString() ;}
}