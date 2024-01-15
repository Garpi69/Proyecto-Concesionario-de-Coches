package src.domain;

public class Venta {
	protected String categoria;
	protected int idVehiculo;
	protected String marca;
	protected String modelo;

	protected int precioVenta;

	protected String dniComprador;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
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

	public int getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(int precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getDniComprador() {
		return dniComprador;
	}

	public void setDniComprador(String dniComprador) {
		this.dniComprador = dniComprador;
	}

	public Venta(String categoria, int idVehiculo, String marca, String modelo, int precioVenta, String dniComprador) {
		super();
		this.categoria = categoria;
		this.idVehiculo = idVehiculo;
		this.marca = marca;
		this.modelo = modelo;

		this.precioVenta = precioVenta;

		this.dniComprador = dniComprador;
	}

	public Venta() {
		super();
	}
}
