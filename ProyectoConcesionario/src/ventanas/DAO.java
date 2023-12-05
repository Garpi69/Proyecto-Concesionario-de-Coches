package ventanas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import clases.Cliente;
import clases.Coche;
import clases.CocheSegundaMano;
import clases.Moto;
import clases.MotoSegundaMano;
import clases.Trabajador;

public class DAO {
	

    
	
    protected final String url = "jdbc:sqlite:/Users/jonmendizabal/ProyectoProgram/Proyecto-Concesionario-de-Coches-master/basedatos/baseDeDatos.sqlite";
    Connection conn;
    
    public void conectar() throws SQLException {
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        conn = DriverManager.getConnection(url);
        System.out.println("Conexión establecida");
    }

    public void desconectar() throws SQLException {
        if (conn != null) {
            conn.close();
            System.out.println("Conexión cerrada");
        }
    }

    String format ="dd/mm/yyyy";
    public String dateToString(java.util.Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
	 public java.util.Date stringToDate(String dateString, String format) throws ParseException {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	        return dateFormat.parse(dateString);
	    }

    
   
  
       
    public Cliente obtenerClientePorDNI(String dniCliente) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Cliente cliente = null;

        try {
        	 String query = "SELECT * FROM cliente WHERE Identifiacion = ?";
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
                java.util.Date fechaNacimiento = stringToDate(resultSet.getString("fechaNacimiento"),format);
                long numTarjeta = resultSet.getLong("numTarjeta");
               
                // Construir el objeto Cliente
                cliente = new Cliente(login, contraseña, email,dni, nombre, apellidos,fechaNacimiento,numTarjeta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cliente;
    }
    public void agregarTrabajador(Trabajador trabajador) throws SQLException {
        String query = "INSERT INTO trabajador (login, contraseña, email, dNi, nombre, apellidos,fechaNacimiento,sueldo,esAdmin,puesto) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)";
        String string = "";
        conectar();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, trabajador.getLogin());
            pstmt.setString(2, trabajador.getPassword());
            pstmt.setString(3, trabajador.getEmail());
            pstmt.setString(4, trabajador.getdNI());
            pstmt.setString(5, trabajador.getNombre());
            pstmt.setString(6, trabajador.getApellidos());
            pstmt.setString(7, dateToString(trabajador.getFechaNacimiento(),string));
            pstmt.setLong(8,  trabajador.getSueldo());
            pstmt.setBoolean(9, trabajador.esAdmin());
            pstmt.setString(10, trabajador.getPuesto());
            pstmt.executeUpdate();
            System.out.println("Nuevo usuario agregado a la base de datos");
        }
        desconectar();
    }
	public void agregarCliente(Cliente cliente) throws SQLException {
		conectar();
        PreparedStatement preparedStatement = null;
        

        try {
        	String string = "";
            String query = "INSERT INTO cliente(login, contra, email, nombre, apellidos, fechaNacimiento, numTarjeta) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, cliente.getLogin());
            preparedStatement.setString(2, cliente.getPassword());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getdNI());
            preparedStatement.setString(5, cliente.getNombre());
            preparedStatement.setString(6, cliente.getApellidos());
            preparedStatement.setString(7, dateToString(cliente.getFechaNacimiento(),string));
            preparedStatement.setLong(8, cliente.getNumTarjeta());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
	public boolean comprobarCredencialesTrabajador(String usuarioIngresado, String contrasenaIngresada) throws SQLException {
		conectar();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean credencialesCorrectas = false;

        try {
           
            String query = "SELECT * FROM trabajador WHERE login = ? AND contra = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, usuarioIngresado);
            preparedStatement.setString(2, contrasenaIngresada);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Se encontró una coincidencia en la base de datos
                credencialesCorrectas = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        desconectar();
        return credencialesCorrectas;
    }
	public boolean comprobarCredencialesCliente(String usuarioIngresado, String contrasenaIngresada) throws SQLException {
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
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        desconectar();
        return credencialesCorrectas;
    }
	public void agregarCocheCompradoPorConcesionario(Coche coche) throws SQLException {
		String query = "INSERT INTO coche	 (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conectar();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, coche.getidVehiculo());
            pstmt.setString(2, coche.getMarca());
            pstmt.setString(3,coche.getModelo());
            pstmt.setString(4, coche.getColor());
            pstmt.setString(5, coche.getTipo());
            pstmt.setInt(6,coche.getPotencia());
            pstmt.setInt(7, coche.getNumPlazas());
            pstmt.setInt(8, coche.getPrecio());
            pstmt.setInt(9, coche.getCuota());
            pstmt.setDate(10, (Date) coche.getMatriculacion());
           
     
            pstmt.executeUpdate();
            System.out.println("Coche agregado a la base de datos");
          
        }
        desconectar();
    }
	public void agregarMotoCompradaPorConcesionario(Moto moto) throws SQLException {
			String query = "INSERT INTO moto (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,baul) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
			conectar();
	        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, moto.getidVehiculo());
	            pstmt.setString(2,  moto.getMarca());
	            pstmt.setString(3,  moto.getModelo());
	            pstmt.setString(4,  moto.getColor());
	            pstmt.setString(5,  moto.getTipo());
	            pstmt.setInt(6, moto.getPotencia());
	            pstmt.setInt(7,  moto.getNumPlazas());
	            pstmt.setInt(8,  moto.getPrecio());
	            pstmt.setInt(9,  moto.getCuota());
	            pstmt.setDate(10, (Date)  moto.getMatriculacion());
	            pstmt.setInt(11, moto.getPeso());
	            pstmt.setBoolean(12, moto.isBaul());
	           
	            pstmt.executeUpdate();
	            System.out.println("Moto agregada a la base de datos");
	        }
	        desconectar();
	    }
	public void agregarCocheSegundaManoCompradoPorConcesionario(CocheSegundaMano cocheSegundaMano, Cliente cliente) throws SQLException {
		String query = "INSERT INTO cocheSegundaMano	 (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,kilometraje,dniVendedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        conectar();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cocheSegundaMano.getidVehiculo());
            pstmt.setString(2, cocheSegundaMano.getCombustible());
            pstmt.setString(3, cocheSegundaMano.getMarca());
            pstmt.setString(4,cocheSegundaMano.getModelo());
            pstmt.setString(5, cocheSegundaMano.getColor());
            pstmt.setString(6, cocheSegundaMano.getTipo());
            pstmt.setInt(7,cocheSegundaMano.getPotencia());
            pstmt.setInt(8, cocheSegundaMano.getNumPlazas());
            pstmt.setInt(9, cocheSegundaMano.getPrecio());
            pstmt.setInt(10, cocheSegundaMano.getCuota());
            pstmt.setDate(11, (Date) cocheSegundaMano.getMatriculacion());
            pstmt.setInt(12, cocheSegundaMano.getKilometraje());
            pstmt.setString(13, cliente.getdNI());
          
            pstmt.executeUpdate();
            System.out.println("Coche agregado a la base de datos");
          
        }
        desconectar();
    }
	public void agregarMotoSegundaManoCompradaPorConcesionario(MotoSegundaMano motoSegundaMano, Cliente cliente) throws SQLException  {
		String query = "INSERT INTO CocheSegundaMano (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,baul, kilometraje,dniVendedor) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
	   	 conectar();
	        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, motoSegundaMano.getidVehiculo());
	            pstmt.setString(2, motoSegundaMano.getCombustible());
	            pstmt.setString(3,  motoSegundaMano.getMarca());
	            pstmt.setString(4,  motoSegundaMano.getModelo());
	            pstmt.setString(5,  motoSegundaMano.getColor());
	            pstmt.setString(6, motoSegundaMano.getTipo());
	            pstmt.setInt(7, motoSegundaMano.getPotencia());
	            pstmt.setInt(8,  motoSegundaMano.getNumPlazas());
	            pstmt.setInt(9,  motoSegundaMano.getPrecio());
	            pstmt.setInt(10,  motoSegundaMano.getCuota());
	            pstmt.setDate(11, (Date)  motoSegundaMano.getMatriculacion());
	            pstmt.setInt(12, motoSegundaMano.getPeso());
	            pstmt.setBoolean(13,motoSegundaMano.isBaul());
	            pstmt.setInt(14, motoSegundaMano.getKilometraje());
	            pstmt.setString(15, cliente.getdNI());
	            pstmt.executeUpdate();
	            System.out.println("Moto agregada a la base de datos");
	        }
	        desconectar();
	    }
	public void agregarCocheVendidoPorCliente(CocheSegundaMano cocheSegundaMano, Cliente cliente) throws SQLException {
		 	conectar();
		 	 PreparedStatement preparedStatement = null;
		     String string = "";

		        try {
		            conn = DriverManager.getConnection("jdbc:sqlite:ruta_de_tu_base_de_datos");
		            String query = "INSERT INTO ofertasCoches(idVehiculo,dniCliente, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, kilometraje) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		            preparedStatement = conn.prepareStatement(query);
		            preparedStatement.setInt(1, cocheSegundaMano.getIdVehiculo());
		            preparedStatement.setString(2, cliente.getdNI());
		            preparedStatement.setString(3, cocheSegundaMano.getCombustible());
		            preparedStatement.setString(4, cocheSegundaMano.getMarca());
		            preparedStatement.setString(5, cocheSegundaMano.getModelo());
		            preparedStatement.setString(6, cocheSegundaMano.getColor());
		            preparedStatement.setString(7, cocheSegundaMano.getTipo());
		            preparedStatement.setInt(8, cocheSegundaMano.getPotencia());
		            preparedStatement.setInt(9, cocheSegundaMano.getNumPlazas());
		            preparedStatement.setInt(10, cocheSegundaMano.getPrecio());
		            preparedStatement.setInt(11, cocheSegundaMano.getCuota());
		            preparedStatement.setString(12, dateToString(cocheSegundaMano.getMatriculacion(),string));
		            preparedStatement.setInt(13, cocheSegundaMano.getKilometraje());

		            preparedStatement.executeUpdate();

		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (preparedStatement != null) {
		                    preparedStatement.close();
		                }
		                if (conn != null) {
		                    conn.close();
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		        }

		        
		    }

	public void agregarMotoVendidaPorCliente(MotoSegundaMano motoSegundaMano, Cliente cliente) throws SQLException {
		conectar();
	 	 PreparedStatement preparedStatement = null;
	       

	        try {
	            String string = "";
	            String query = "INSERT INTO ofertasMotos(idVehiculo, dniCliente, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,potencia, kilometraje) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	            preparedStatement = conn.prepareStatement(query);
	            preparedStatement.setInt(1, motoSegundaMano.getIdVehiculo());
	          
	            preparedStatement.setString(2, cliente.getdNI());
	            preparedStatement.setString(3, motoSegundaMano.getCombustible());
	            preparedStatement.setString(4, motoSegundaMano.getMarca());
	            preparedStatement.setString(5, motoSegundaMano.getModelo());
	            preparedStatement.setString(6, motoSegundaMano.getColor());
	            preparedStatement.setString(7, motoSegundaMano.getTipo());
	            preparedStatement.setInt(8, motoSegundaMano.getPotencia());
	            preparedStatement.setInt(9, motoSegundaMano.getNumPlazas());
	            preparedStatement.setInt(10,motoSegundaMano.getPrecio());
	            preparedStatement.setInt(11, motoSegundaMano.getCuota());
	            preparedStatement.setString(12, dateToString(motoSegundaMano.getMatriculacion(),string));
	            preparedStatement.setInt(13, motoSegundaMano.getPeso());
	            preparedStatement.setInt(14, motoSegundaMano.getPotencia());
	            preparedStatement.setInt(15, motoSegundaMano.getKilometraje());
	            

	            preparedStatement.executeUpdate();

	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (preparedStatement != null) {
	                    preparedStatement.close();
	                }
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }

	        
	    }
	
	}


	
