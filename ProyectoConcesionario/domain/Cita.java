package domain;

public class Cita {

	public String fecha;
	public String usuario;
	public String nombreVehiculo;

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombreVehiculo() {
		return nombreVehiculo;
	}
	public void setNombreVehiculo(String nombreVehiculo) {
		this.nombreVehiculo = nombreVehiculo;
	}
	public Cita(String fecha, String usuario, String nombreVehiculo) {
		super();
		this.fecha = fecha;
		this.usuario = usuario;
		this.nombreVehiculo = nombreVehiculo;
	}
	public Cita() {
		super();
	}

}
