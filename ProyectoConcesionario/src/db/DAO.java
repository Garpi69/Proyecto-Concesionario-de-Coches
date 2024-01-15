package src.db;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import src.domain.Cita;
import src.domain.Cliente;
import src.domain.Coche;
import src.domain.CocheSegundaMano;
import src.domain.Moto;
import src.domain.MotoSegundaMano;
import src.domain.Trabajador;
import src.domain.Venta;
import src.io.FicheroActividad;
import src.io.LoggerDeusto;
import src.io.Propiedades;

public class DAO {

	public boolean esAdmin = false;
	public SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

	public final String url = "jdbc:sqlite:./resources/db/database.sqlite";
	public Connection conn;
	public Cliente cliente = new Cliente();
	public Trabajador trabajador = new Trabajador();
	public long numeroDeSerie = Propiedades.getNumeroDeSerie();
	public LoggerDeusto logger;

	public long generarNumeroSerie() {
		return numeroDeSerie++;
	}

	public void conectar() {
		try {
			Class.forName("org.sqlite.JDBC");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No se encontró la clase org.sqlite.JBDC: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se encontró la clase org.sqlite.JBDC: " + e.getMessage());
		}
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudo conectar con la base de datos: " + e.getMessage());
		}
		System.out.println("Conexión establecida");
	}

	public void desconectar() {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Conexión cerrada");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se pudo cerrar la base de datos: " + e.getMessage());
				LoggerDeusto.log(Level.SEVERE, "No se pudo cerrar la base de datos: " + e.getMessage());

			}
		}
	}

	public Cliente obtenerClientePorDNI(String dniCliente) {
		conectar();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Cliente cliente = null;

		try {
			String query = "SELECT * FROM cliente WHERE dni = ?";
			preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setString(1, dniCliente);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				String login = resultSet.getString("login");
				String contraseña = resultSet.getString("contra");
				String email = resultSet.getString("email");
				String dni = resultSet.getString("dni");
				String nombre = resultSet.getString("nombre");

				String apellidos = resultSet.getString("apellidos");
				String fechaNacimiento = resultSet.getString("fechaNacimiento");
				java.util.Date fechaNacimientoDate = stringToDate(fechaNacimiento, format);
				long numTarjeta = resultSet.getLong("numTarjeta");
				cliente = new Cliente(login, contraseña, email, dni, nombre, apellidos, fechaNacimientoDate, numTarjeta,
						"");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo obtener el cliente: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudo obtener el cliente: ");
		} finally {
			desconectar();
		}

		return cliente;
	}

	public Cliente obtenerClientePorLogin(String loginCliente) {
		conectar();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Cliente cliente = null;

		try {
			String query = "SELECT * FROM cliente WHERE login = ?";
			preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setString(1, loginCliente);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				String login = resultSet.getString("login");
				String contraseña = resultSet.getString("contra");
				String email = resultSet.getString("email");
				String dni = resultSet.getString("dni");
				String nombre = resultSet.getString("nombre");

				String apellidos = resultSet.getString("apellidos");
				String fechaNacimiento = resultSet.getString("fechaNacimiento");
				java.util.Date fechaNacimientoDate = stringToDate(fechaNacimiento, format);
				long numTarjeta = resultSet.getLong("numTarjeta");

				cliente = new Cliente(login, contraseña, email, dni, nombre, apellidos, fechaNacimientoDate, numTarjeta,
						"");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo obtener el cliente: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudo obtener el cliente: " + e.getMessage());
		} finally {
			desconectar();
		}

		return cliente;
	}

	public void agregarTrabajador(Trabajador trabajador) throws SQLException {
		String query = "INSERT INTO trabajador (login, contraseña, email, dNi, nombre, apellidos,fechaNacimiento,sueldo,esAdmin,puesto) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)";
		conectar();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, trabajador.getLogin());
			pstmt.setString(2, trabajador.getPassword());
			pstmt.setString(3, trabajador.getEmail());
			pstmt.setString(4, trabajador.getdNI());
			pstmt.setString(5, trabajador.getNombre());
			pstmt.setString(6, trabajador.getApellidos());
			pstmt.setString(7, dateToString(trabajador.getFechaNacimiento(), format));
			pstmt.setLong(8, trabajador.getSueldo());
			pstmt.setBoolean(9, trabajador.esAdmin());
			pstmt.setString(10, trabajador.getPuesto());
			pstmt.executeUpdate();
			System.out.println("Nuevo usuario agregado a la base de datos");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar el trabajador: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo agregar el trabajador: " + e.getMessage());
		}

		FicheroActividad
				.guardarActividad(trabajador.getLogin() + ": Trabajador " + trabajador.getLogin() + " agregado");
		desconectar();
	}

	public void agregarCliente(Cliente cliente) throws SQLException {
		conectar();
		PreparedStatement preparedStatement = null;

		try {

			String query = "INSERT INTO cliente(login, contra, email, nombre, apellidos, fechaNacimiento, numTarjeta) VALUES (?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, cliente.getLogin());
			preparedStatement.setString(2, cliente.getPassword());
			preparedStatement.setString(3, cliente.getEmail());
			preparedStatement.setString(4, cliente.getdNI());
			preparedStatement.setString(5, cliente.getNombre());
			preparedStatement.setString(6, cliente.getApellidos());

			preparedStatement.setLong(7, cliente.getNumTarjeta());

			int filasAfectadas = preparedStatement.executeUpdate();

			if (filasAfectadas > 0) {

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar el cliente: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo agregar el cliente: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": Cliente " + cliente.getLogin() + " agregado");
			desconectar();
		}

	}

	public void eliminarCliente(String login) throws SQLException {
		conectar();
		PreparedStatement preparedStatement = null;
		Connection conn = this.conn;
		try {

			String query = "DELETE FROM cliente WHERE login = ?";
			preparedStatement = conn.prepareStatement(query);

			preparedStatement.setString(1, login);

			int filasAfectadas = preparedStatement.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Cliente eliminado correctamente");
			} else {
				System.out.println("No se encontró ningún cliente con ese login");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo eliminar el cliente: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": Cliente " + login + " eliminado");
			desconectar();
		}

	}

	public boolean comprobarCredencialesTrabajador(String usuarioIngresado, String contrasenaIngresada)
			throws SQLException {
		trabajador.setAdmin(esAdmin(usuarioIngresado));

		conectar();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean credencialesCorrectas = false;
		String dniTrabajador = "";

		try {

			String query = "SELECT * FROM trabajador WHERE login = ? AND contra = ?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, usuarioIngresado);
			preparedStatement.setString(2, contrasenaIngresada);

			resultSet = preparedStatement.executeQuery();
			dniTrabajador = resultSet.getString("dni");
			trabajador.setdNI(dniTrabajador);
			if (resultSet.next()) {
				credencialesCorrectas = true;
			}
			if (credencialesCorrectas) {
				trabajador.setLogin(usuarioIngresado);
				FicheroActividad
						.guardarActividad(trabajador.getLogin() + ": Trabajador " + trabajador.getLogin() + " logeado");

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudieron verificar las credenciales: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE,
					"No se pudieron verificar las credenciales del trabajador: " + e.getMessage());
		} finally {
			desconectar();
		}

		return credencialesCorrectas;
	}

	public boolean comprobarCredencialesCliente(String usuarioIngresado, String contrasenaIngresada)
			throws SQLException {
		conectar();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean credencialesCorrectas = false;

		try {

			String query = "SELECT * FROM cliente WHERE login = ? AND contra = ?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, usuarioIngresado);
			preparedStatement.setString(2, contrasenaIngresada);

			resultSet = preparedStatement.executeQuery();

			credencialesCorrectas = resultSet.next();
			if (credencialesCorrectas) {
				cliente.setLogin(usuarioIngresado);
				FicheroActividad.guardarActividad(cliente.getLogin() + ": Cliente " + usuarioIngresado + " logeado");

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudieron verificar las credenciales: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudieron verificar las credenciales del cliente: " + e.getMessage());
		} finally {
			desconectar();
		}
		return credencialesCorrectas;
	}

	public void agregarCocheCompradoPorConcesionario(Coche coche) throws SQLException {
		String query = "INSERT INTO coche	 (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,ofertas,dniPropietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
		conectar();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, coche.getidVehiculo());
			pstmt.setString(2, coche.getMarca());
			pstmt.setString(3, coche.getModelo());
			pstmt.setString(4, coche.getColor());
			pstmt.setString(5, coche.getTipo());
			pstmt.setInt(6, coche.getPotencia());
			pstmt.setInt(7, coche.getNumPlazas());
			pstmt.setInt(8, coche.getPrecio());
			pstmt.setInt(9, coche.getCuota());
			java.sql.Date matriculacionSQL = utilDateToSqlDate(coche.getMatriculacion());
			pstmt.setDate(10, matriculacionSQL);
			pstmt.setString(11, coche.getOfertas());
			pstmt.setString(12, coche.getPropietario());

			pstmt.executeUpdate();
			System.out.println("Coche agregado a la base de datos");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar el coche: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo agregar el coche:" + e.getMessage());
		} finally {
			FicheroActividad
					.guardarActividad(trabajador.getLogin() + ": coche con id:" + coche.getidVehiculo() + " añadido");
			desconectar();
		}

	}

	public void agregarMotoCompradaPorConcesionario(Moto moto) throws SQLException {
		String query = "INSERT INTO moto (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,baul) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
		conectar();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, moto.getidVehiculo());
			pstmt.setString(2, moto.getMarca());
			pstmt.setString(3, moto.getModelo());
			pstmt.setString(4, moto.getColor());
			pstmt.setString(5, moto.getTipo());
			pstmt.setInt(6, moto.getPotencia());
			pstmt.setInt(7, moto.getNumPlazas());
			pstmt.setInt(8, moto.getPrecio());
			pstmt.setInt(9, moto.getCuota());
			java.sql.Date matriculacionSQL = utilDateToSqlDate(moto.getMatriculacion());
			pstmt.setDate(10, matriculacionSQL);
			pstmt.setInt(11, moto.getPeso());
			pstmt.setBoolean(12, moto.isBaul());
			pstmt.setString(13, moto.getOfertas());
			pstmt.setString(14, moto.getPropietario());

			pstmt.executeUpdate();
			System.out.println("Moto agregada a la base de datos");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar la moto: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo agregar la moto:" + e.getMessage());
		} finally {
			FicheroActividad
					.guardarActividad(trabajador.getLogin() + ": moto con id:" + moto.getidVehiculo() + " añadida");
			desconectar();
		}

	}

	public void agregarCocheSegundaManoCompradoPorConcesionario(CocheSegundaMano cocheSegundaMano, Cliente cliente)
			throws SQLException {
		String query = "INSERT INTO cocheSegundaMano	 (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,kilometraje,ofertas,dniPropietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
		conectar();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, cocheSegundaMano.getidVehiculo());
			pstmt.setString(2, cocheSegundaMano.getCombustible());
			pstmt.setString(3, cocheSegundaMano.getMarca());
			pstmt.setString(4, cocheSegundaMano.getModelo());
			pstmt.setString(5, cocheSegundaMano.getColor());
			pstmt.setString(6, cocheSegundaMano.getTipo());
			pstmt.setInt(7, cocheSegundaMano.getPotencia());
			pstmt.setInt(8, cocheSegundaMano.getNumPlazas());
			pstmt.setInt(9, cocheSegundaMano.getPrecio());
			pstmt.setInt(10, cocheSegundaMano.getCuota());
			java.sql.Date matriculacionSQL = utilDateToSqlDate(cocheSegundaMano.getMatriculacion());
			pstmt.setDate(11, matriculacionSQL);
			pstmt.setInt(12, cocheSegundaMano.getKilometraje());
			pstmt.setString(13, cocheSegundaMano.getOfertas());
			pstmt.setString(14, cocheSegundaMano.getPropietario());
			pstmt.executeUpdate();
			System.out.println("Coche agregado a la base de datos");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar el coche de segunda mano: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo agregar el coche de segunda mano:" + e.getMessage());
		} finally {
			desconectar();
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": coche de segunda mano con id:"
					+ cocheSegundaMano.getidVehiculo() + " añadido");
		}

	}

	public void agregarMotoSegundaManoCompradaPorConcesionario(MotoSegundaMano motoSegundaMano, Cliente cliente)
			throws SQLException {
		String query = "INSERT INTO CocheSegundaMano (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,baul, kilometraje,ofertas,dniPropietario) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		conectar();
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, motoSegundaMano.getidVehiculo());
			pstmt.setString(2, motoSegundaMano.getCombustible());
			pstmt.setString(3, motoSegundaMano.getMarca());
			pstmt.setString(4, motoSegundaMano.getModelo());
			pstmt.setString(5, motoSegundaMano.getColor());
			pstmt.setString(6, motoSegundaMano.getTipo());
			pstmt.setInt(7, motoSegundaMano.getPotencia());
			pstmt.setInt(8, motoSegundaMano.getNumPlazas());
			pstmt.setInt(9, motoSegundaMano.getPrecio());
			pstmt.setInt(10, motoSegundaMano.getCuota());
			java.sql.Date matriculacionSQL = utilDateToSqlDate(motoSegundaMano.getMatriculacion());
			pstmt.setDate(11, matriculacionSQL);
			pstmt.setInt(12, motoSegundaMano.getPeso());
			pstmt.setBoolean(13, motoSegundaMano.isBaul());
			pstmt.setInt(14, motoSegundaMano.getKilometraje());
			pstmt.setString(15, motoSegundaMano.getOfertas());
			pstmt.setString(16, motoSegundaMano.getPropietario());
			pstmt.executeUpdate();
			System.out.println("Moto agregada a la base de datos");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar la moto de segunda mano: " + e.getMessage());
			LoggerDeusto.log(Level.WARNING, "No se pudo agregar la moto de segunda mano: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": moto de segunda mano  con id:"
					+ motoSegundaMano.getidVehiculo() + " añadido");
			desconectar();
		}

	}

	public void agregarCocheVendidoPorCliente(CocheSegundaMano cocheSegundaMano, Cliente cliente) throws SQLException {
		conectar();
		PreparedStatement preparedStatement = null;

		try {

			String query = "INSERT INTO cocheSegundaMano(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, kilometraje,ofertas,dniPropietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, cocheSegundaMano.getIdVehiculo());
			preparedStatement.setString(2, cocheSegundaMano.getCombustible());
			preparedStatement.setString(3, cocheSegundaMano.getMarca());
			preparedStatement.setString(4, cocheSegundaMano.getModelo());
			preparedStatement.setString(5, cocheSegundaMano.getColor());
			preparedStatement.setString(6, cocheSegundaMano.getTipo());
			preparedStatement.setInt(7, cocheSegundaMano.getPotencia());
			preparedStatement.setInt(8, cocheSegundaMano.getNumPlazas());
			preparedStatement.setInt(9, cocheSegundaMano.getPrecio());
			preparedStatement.setInt(10, cocheSegundaMano.getCuota());
			java.sql.Date sqlDate = utilDateToSqlDate(cocheSegundaMano.getMatriculacion());
			preparedStatement.setDate(11, sqlDate);
			preparedStatement.setInt(12, cocheSegundaMano.getKilometraje());
			preparedStatement.setString(13, null);
			preparedStatement.setString(14, cliente.getdNI());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"No se pudo agregar el coche vendido por el cliente: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudo agregar el coche vendido por el cliente: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(
					cliente.getLogin() + ": coche con id:" + cocheSegundaMano.getidVehiculo() + " añadido a la venta");
			desconectar();
		}

	}

	public void agregarMotoVendidaPorCliente(MotoSegundaMano motoSegundaMano, Cliente cliente) throws SQLException {
		conectar();
		PreparedStatement preparedStatement = null;
		int baul = 0;
		try {

			String query = "INSERT INTO motoSegundaMano(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,baul, kilometraje,ofertas,dniPropietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, motoSegundaMano.getIdVehiculo());
			preparedStatement.setString(2, motoSegundaMano.getCombustible());
			preparedStatement.setString(3, motoSegundaMano.getMarca());
			preparedStatement.setString(4, motoSegundaMano.getModelo());
			preparedStatement.setString(5, motoSegundaMano.getColor());
			preparedStatement.setString(6, motoSegundaMano.getTipo());
			preparedStatement.setInt(7, motoSegundaMano.getPotencia());
			preparedStatement.setInt(8, motoSegundaMano.getNumPlazas());
			preparedStatement.setInt(9, motoSegundaMano.getPrecio());
			preparedStatement.setInt(10, motoSegundaMano.getCuota());
			java.sql.Date sqlMatriculacion = utilDateToSqlDate(motoSegundaMano.getMatriculacion());
			preparedStatement.setDate(11, sqlMatriculacion);
			preparedStatement.setInt(12, motoSegundaMano.getPeso());
			if (motoSegundaMano.isBaul()) {
				baul = 1;
			}
			preparedStatement.setInt(13, baul);
			preparedStatement.setInt(14, motoSegundaMano.getKilometraje());
			preparedStatement.setString(15, motoSegundaMano.getOfertas());
			preparedStatement.setString(16, cliente.getdNI());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar la moto vendida por el cliente: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudo agregar la moto vendida por el cliente: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(
					trabajador.getLogin() + ": moto con id:" + motoSegundaMano.getidVehiculo() + " añadida a la venta");
			desconectar();
		}

	}

	public void agregarVenta(Venta venta) {

		String query = "INSERT INTO ventas (categoria, idVehiculo, marca, modelo, precioVenta, dniComprador) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			conectar();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, venta.getCategoria());
			pstmt.setInt(2, venta.getIdVehiculo());
			pstmt.setString(3, venta.getMarca());
			pstmt.setString(4, venta.getModelo());
			pstmt.setInt(5, venta.getPrecioVenta());
			pstmt.setString(6, venta.getDniComprador());

			pstmt.executeUpdate();
			System.out.println("Venta agregada correctamente a la base de datos.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se pudo agregar la venta: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se pudo agregar la venta: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": Venta de " + venta.getMarca() + " "
					+ venta.getModelo() + "por " + venta.getPrecioVenta() + "€ añadida");
			desconectar();
		}

	}

	public void agregarOfertaAVehiculo(int idVehiculo, int oferta, String usuario) {
		conectar();
		try {

			String query = "SELECT * FROM coche WHERE idVehiculo = ? ";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(0, idVehiculo);
			ResultSet resultSet = preparedStatement.executeQuery();

			String ofertas = resultSet.getString("ofertas");
			ofertas = ofertas + "Usuario: " + usuario + " Oferta: " + oferta + " €, ";

			String query2 = "UPDATE coche SET ofertas = " + ofertas + " WHERE idVehiculo = " + idVehiculo + "";
			PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
			preparedStatement2.executeUpdate();

		} catch (SQLException e) {
			try {
				String query = "SELECT * FROM cocheSegundaMano WHERE idVehiculo = ? ";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(0, idVehiculo);
				ResultSet resultSet = preparedStatement.executeQuery();

				String ofertas = resultSet.getString("ofertas");
				ofertas = ofertas + ", Usuario: " + usuario + " Oferta: " + oferta + " €";

				String query2 = "UPDATE cocheSegundaMano SET ofertas = " + ofertas + " WHERE idVehiculo = " + idVehiculo
						+ "";
				PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
				preparedStatement2.executeUpdate();

			} catch (SQLException e1) {
				try {
					String query = "SELECT * FROM moto WHERE idVehiculo = ? ";
					PreparedStatement preparedStatement = conn.prepareStatement(query);
					preparedStatement.setInt(0, idVehiculo);
					ResultSet resultSet = preparedStatement.executeQuery();
					String ofertas = resultSet.getString("ofertas");
					ofertas = ofertas + ", Usuario: " + usuario + " Oferta: " + oferta + " €";
					preparedStatement.setString(13, ofertas);
				} catch (SQLException e2) {
					try {
						String query = "SELECT * FROM motoSegundaMano WHERE idVehiculo = ? ";
						PreparedStatement preparedStatement = conn.prepareStatement(query);
						preparedStatement.setInt(0, idVehiculo);
						ResultSet resultSet = preparedStatement.executeQuery();

						String ofertas = resultSet.getString("ofertas");
						ofertas = ofertas + ", Usuario: " + usuario + " Oferta: " + oferta + " €";

						String query2 = "UPDATE moto SET ofertas = " + ofertas + " WHERE idVehiculo = " + idVehiculo
								+ "";
						PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
						preparedStatement2.executeUpdate();
					} catch (SQLException e3) {
						JOptionPane.showMessageDialog(null, "No se ha podido añadir la oferta: " + e3.getMessage());
						LoggerDeusto.log(Level.WARNING, "No se ha podido añadir la oferta: " + e.getMessage());
					}
				}
			}

		} finally {
			FicheroActividad.guardarActividad(
					cliente.getLogin() + ": Oferta de " + oferta + "€ añadida a vehiculo " + idVehiculo);
			desconectar();
		}

	}

	public void cargarOfertasRecibidas(String tabla, DefaultTableModel tableModel, String propietario)
			throws AWTException {
		conectar();

		String sql = "SELECT marca, modelo, ofertas FROM " + tabla + " WHERE dniPropietario LIKE ?";

		String usuarioQueHaceOferta = null;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "%" + propietario + "%");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String marca = resultSet.getString("marca");
				String modelo = resultSet.getString("modelo");
				String ofertas = resultSet.getString("ofertas");

				if (ofertas != null) {
					String vehiculo = marca + " " + modelo;

					String[] ofertasArray = ofertas.split(",");
					for (String oferta : ofertasArray) {
						oferta = oferta.trim().replaceAll("\\s+", "");
						String[] ofertaArray = oferta.split(":");

						if (ofertaArray.length == 2) {
							usuarioQueHaceOferta = ofertaArray[0];
							int ofertaFinal = Integer.parseInt(ofertaArray[1]);
							Object[] fila = { vehiculo, ofertaFinal, usuarioQueHaceOferta };
							tableModel.addRow(fila);
						} else {

							System.out.println("Oferta con formato incorrecto: " + oferta);
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "No tienes ofertas");
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han podido cargar las ofertas recibidas: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se han podido cargar las ofertas recibidas: " + e.getMessage());
		} finally {
			desconectar();
		}

	}

	public void cargarOfertasEnviadas(String tabla, DefaultTableModel tableModel, String usuario) {
		conectar();
		String sql = "SELECT * FROM " + tabla;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				try {
					String ofertas = resultSet.getString("ofertas");
					String[] ofertasArray = null;
					if (ofertas != null) {
						ofertasArray = ofertas.split(",");
					} else {
						JOptionPane.showMessageDialog(null, "No tienes ofertas");
					}

					String marca = resultSet.getString("marca");
					String modelo = resultSet.getString("modelo");
					String vehiculo = marca + "" + modelo;
					@SuppressWarnings("unused")
					boolean encontrado = false;
					if (ofertas != null) {
						for (String oferta : ofertasArray) {
							while (encontrado = false) {
								String[] ofertaArray = oferta.split(" ");
								String usuarioQueHaceOferta = ofertaArray[2];
								if (usuarioQueHaceOferta == usuario) {
									encontrado = true;
									int ofertaHecha = Integer.parseInt(ofertaArray[4]);
									Object[] fila = { vehiculo, ofertaHecha };
									tableModel.addRow(fila);
								}
							}
						}
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							"No se han podido cargar las ofertas enviadas: " + e.getMessage());
					LoggerDeusto.log(Level.SEVERE, "No se han podido cargar las ofertas recibidas: " + e.getMessage());
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han podido cargar las ofertas enviadas: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se han podido cargar las ofertas recibidas: " + e.getMessage());
		} finally {
			desconectar();
		}

	}

	public java.util.Date sqlDateToJavaDate(java.sql.Date sqlDate) {
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		return utilDate;
	}

	public java.sql.Date utilDateToSqlDate(java.util.Date utilDate) {
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	public java.util.Date stringToDate(String fechaNacimientoString, SimpleDateFormat format2) {
		try {
			return format2.parse(fechaNacimientoString);
		} catch (ParseException e) {
			java.util.Date fechaNacimiento = new java.util.Date();
			return fechaNacimiento;
		}
	}

	public String dateToString(java.util.Date date, SimpleDateFormat format2) {
		return format2.format(date);
	}

	public void eliminarVehiculo(String nombreCoche, String tabla) {

		PreparedStatement statement = null;
		conectar();
		try {

			String sql = "DELETE FROM " + tabla + " WHERE marca = ? AND modelo = ? and dniPropietario = ?";
			String[] nombreCocheDividido = nombreCoche.split(" ");
			System.out.print(nombreCocheDividido);
			String marcaCoche = nombreCocheDividido[0];
			String modeloCoche = nombreCocheDividido[1];

			statement = conn.prepareStatement(sql);
			statement.setString(1, marcaCoche);
			statement.setString(2, modeloCoche);
			statement.setString(3, cliente.getLogin());

			int filasEliminadas = statement.executeUpdate();

			if (filasEliminadas > 0) {
				System.out.println("El vehiculo ha sido eliminado correctamente.");
			} else {
				System.out.println("No se encontró el vehiculo para eliminar.");
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han podido eliminar el vehiculo: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se ha podido eliminar el vehiculo: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": Vehiculo " + nombreCoche + " eliminado");
			desconectar();
		}

	}

	public void eliminarOferta(String cocheAceptado, String usuarioAceptado, int precioAceptado) {
		conectar();
		String[] cocheDatos = cocheAceptado.split(" ");
		String marca = cocheDatos[0];
		String modelo = cocheDatos[1];
		String ofertaNueva = "";
		try {
			String[] tablas = { "coche", "cocheSegundaMano", "moto", "motoSegundaMano" };

			for (String tabla : tablas) {
				String sql = "SELECT ofertas FROM " + tabla + " WHERE dniPropietario = ? AND marca = ? AND modelo = ?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, cliente.getLogin());
				statement.setString(2, marca);
				statement.setString(3, modelo);

				ResultSet resultSet = statement.executeQuery();
				String ofertaABorrar = usuarioAceptado + ": " + precioAceptado;
				if (resultSet.next()) {
					String oferta = resultSet.getString("ofertas");
					String[] ofertas = oferta.split(",");
					for (String ofertaString : ofertas) {
						if (ofertaString == ofertaABorrar) {
							ofertaNueva = oferta.replace(ofertaABorrar, "");
						}
					}

					sql = "UPDATE " + tabla + " SET ofertas = ? WHERE dniPropietario = ? AND marca = ? AND modelo = ?";
					statement = conn.prepareStatement(sql);
					statement.setString(1, ofertaNueva);
					statement.setString(2, usuarioAceptado);
					statement.setString(3, marca);
					statement.setString(4, modelo);
					statement.executeUpdate();

					JOptionPane.showMessageDialog(null, "Oferta eliminada con éxito");

				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido eliminar la oferta: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se ha podido eliminar la oferta: " + e.getMessage());
		} finally {
			desconectar();
		}
	}

	public void añadirCita(Cita cita, DAO dao) {

		PreparedStatement statement = null;
		conectar();
		try {

			String sql = "INSERT INTO citas(fecha, usuario, nombreVehiculo) VALUES (?,?,?)";
			String fecha = cita.getFecha();

			statement = conn.prepareStatement(sql);
			statement.setString(1, fecha);
			statement.setString(2, cita.getUsuario());
			statement.setString(3, cita.getNombreVehiculo());

			int filasEliminadas = statement.executeUpdate();

			if (filasEliminadas > 0) {
				System.out.println("La cita ha sido añadida correctamente");

			} else {
				System.out.println("No se pudo añadir la cita, contacte con atención al cliente");
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido añadir la cita: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se ha podido añadir la cita: " + e.getMessage());

		} finally {
			FicheroActividad.guardarActividad(cliente.getLogin() + ": cita añadida el " + cita.getFecha());
			desconectar();
		}

	}

	public boolean esAdmin(String usuario) {
		boolean adminEs = false;

		PreparedStatement statement = null;
		int adminInt = 0;
		try {
			conectar();
			String sql = "SELECT * FROM trabajador WHERE login = ?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, usuario);
			ResultSet resultSet = statement.executeQuery();
			adminInt = resultSet.getInt("esAdmin");
			desconectar();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido verificar si el usuario es admin: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se ha podido verificar si el usuario es admin");
		}
		if (adminInt == 1) {
			adminEs = true;
		}
		return adminEs;
	}

	public void cargarDatosTrabajadores(DefaultTableModel tableModel) {

		PreparedStatement statement;
		conectar();
		try {

			String sql = "SELECT * FROM trabajador";
			statement = conn.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String usuario = resultSet.getString("login");
				String contra = resultSet.getString("contra");
				String email = resultSet.getString("email");
				String dni = resultSet.getString("dni");
				String nombre = resultSet.getString("nombre");
				String apellidos = resultSet.getString("apellidos");
				String fechaNacimiento = resultSet.getString("fechaNacimiento");
				int sueldo = resultSet.getInt("sueldo");
				String puesto = resultSet.getString("puesto");
				Object[] fila = { usuario, contra, email, dni, nombre, apellidos, fechaNacimiento, sueldo, puesto };
				tableModel.addRow(fila);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han podido cargar los datos de trabajadores: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "No se han podido podido cargar los datos de trabajadores");
		} finally {
			desconectar();
		}

	}

	public void eliminarTrabajador(String loginTrabajador) {
		conectar();
		PreparedStatement statement = null;

		try {

			String sql = "DELETE FROM trabajador WHERE login = ?";
			System.out.print(loginTrabajador);
			statement = conn.prepareStatement(sql);
			statement.setString(1, loginTrabajador);

			statement.executeUpdate();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Error al eliminar el trabajador: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "Error al eliminar el trabajador: " + e.getMessage());
		} finally {
			FicheroActividad.guardarActividad(trabajador.getLogin() + ": Trabajador " + loginTrabajador + " eliminado");
			desconectar();
		}

	}

	public void cargarActividad(DefaultTableModel tableModel) {
		List<String> actividades = FicheroActividad.cargarActividad();
		for (String actividad : actividades) {
			String fechaString = actividad.substring(1, actividad.indexOf("]"));
			String usuario = actividad.substring(actividad.indexOf("]") + 2, actividad.indexOf(":"));
			String actividadTexto = actividad.substring(actividad.indexOf(":") + 2);
			Object[] fila = { fechaString, usuario, actividadTexto };
			tableModel.addRow(fila);
		}
	}

	public void guardarActividad(String actividad) {
		conectar();
		PreparedStatement statement = null;
		LocalTime hora = LocalTime.now();
		LocalDate dia = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
		String horaFormateada = hora.format(formato);
		String fecha = dia + " " + horaFormateada;

		try {

			String sql = "INSERT INTO actividad(usuario,fecha,actividad)  VALUES (?,?,?)";
			statement = conn.prepareStatement(sql);
			statement.setString(1, trabajador.getLogin());
			statement.setString(2, fecha);
			statement.setString(3, actividad);

			statement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al guardar la actividad: " + e.getMessage());
			LoggerDeusto.log(Level.SEVERE, "Error al guardar la actividad: " + e.getMessage());
		} finally {
			desconectar();
		}

	}

	public void guardarSiCierraVentana() {
		if (trabajador.getLogin() != null) {
			FicheroActividad.guardarActividad("Trabajador: " + trabajador.getLogin() + " cerró sesión");
		} else {
			FicheroActividad.guardarActividad("Usuario: " + cliente.getLogin() + " cerró sesión");
		}
	}

	public static int generarID() {
		return (int) (Math.random() * 1000000);
	}

	public boolean idExiste(int id) throws SQLException {
		String[] tablas = { "coche", "cocheSegundaMano", "moto", "motoSegundaMano" };
		conectar();
		for (String tabla : tablas) {
			String query = "SELECT COUNT(*) FROM " + tabla + " WHERE idVehiculo = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next() && rs.getInt(1) > 0) {
					return true;
				}
			}
		}
		desconectar();
		return false;
	}

	public int asignarIdAVehiculo() throws SQLException {
		int idVehiculo;
		do {
			idVehiculo = generarID();
		} while (idExiste(idVehiculo));

		return idVehiculo;
	}

	public void filtrarVehiculo(String categoria, String marca, String modelo, String color,
			DefaultTableModel tableModel) {
		try {

			String sql = "SELECT * FROM coche WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, "%" + marca + "%");
			statement.setString(2, "%" + modelo + "%");
			statement.setString(3, "%" + color + "%");

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("idVehiculo");
				String combustible = resultSet.getString("combustible");
				String tipo = resultSet.getString("tipo");
				String marcaReal = resultSet.getString("marca");
				String modeloReal = resultSet.getString("modelo");
				String colorReal = resultSet.getString("color");
				int potencia = resultSet.getInt("potencia");
				int numPlazas = resultSet.getInt("numPlazas");
				int precio = resultSet.getInt("precio");
				int cuota = resultSet.getInt("cuota");
				String matriculacion = resultSet.getString("matriculacion");
				String ofertas = resultSet.getString("ofertas");
				String propietario = resultSet.getString("dniPropietario");
				String baulString = "No";
				Date matriculacionDate = null;
				matriculacionDate = stringToDate(matriculacion, format);
				Object[] filaFinal;
				if (categoria == "cocheSegundaMano") {
					int kilometraje = resultSet.getInt("kilometraje");
					Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas,
							precio, cuota, matriculacionDate, kilometraje, ofertas, propietario };
					filaFinal = fila;
				} else if (categoria == "moto") {
					int baul = resultSet.getInt("baul");
					int peso = resultSet.getInt("peso");
					if (baul == 1)
						baulString = "Si";
					Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas,
							precio, cuota, matriculacionDate, peso, baulString, ofertas, propietario };
					filaFinal = fila;
				} else if (categoria == "motoSegundaMano") {
					int kilometraje = resultSet.getInt("kilometraje");
					int peso = resultSet.getInt("peso");
					int baul = resultSet.getInt("baul");

					if (baul == 1)
						baulString = "Si";
					Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas,
							precio, cuota, matriculacionDate, peso, baulString, kilometraje, ofertas, propietario };
					filaFinal = fila;
				} else {
					Object[] fila = { id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas,
							precio, cuota, matriculacionDate, ofertas, propietario };
					filaFinal = fila;
				}

				tableModel.addRow(filaFinal);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al filtrar el inventario: " + ex.getMessage());
			LoggerDeusto.log(Level.WARNING, "Error al filtrar el inventario: " + ex.getMessage());
		}

	}

	public void cargarDatosVehiculos(String tabla, DefaultTableModel tableModel) throws SQLException {
		String sql = "SELECT * FROM " + tabla;

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			int id = resultSet.getInt("idVehiculo");
			String combustible = resultSet.getString("combustible");
			String marca = resultSet.getString("marca");
			String modelo = resultSet.getString("modelo");
			String color = resultSet.getString("color");
			String tipo = resultSet.getString("tipo");
			int potencia = resultSet.getInt("potencia");
			int numPlazas = resultSet.getInt("numPlazas");
			int precio = resultSet.getInt("precio");
			int cuota = resultSet.getInt("cuota");
			String propietario = resultSet.getString("dniPropietario");
			String ofertas = resultSet.getString("ofertas");
			String matriculacion = null;

			matriculacion = resultSet.getString("matriculacion");

			int kilometraje = 0;
			int peso = 0;
			int baul = 0;
			if (tabla == "cocheSegundaMano") {
				kilometraje = resultSet.getInt("kilometraje");
			}
			if (tabla == "motoSegundaMano") {
				kilometraje = resultSet.getInt("kilometraje");
				peso = resultSet.getInt("peso");
				baul = resultSet.getInt("baul");
			}
			if (tabla == "moto") {
				peso = resultSet.getInt("peso");
				baul = resultSet.getInt("baul");
			}
			String baulString = "No";
			if (baul == 1) {
				baulString = "Si";

			}

			Object[] fila = { id, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota,
					matriculacion, peso, baulString, kilometraje, ofertas, propietario };
			tableModel.addRow(fila);
		}

	}
	
}
