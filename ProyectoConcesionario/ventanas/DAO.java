package ventanas;
import java.awt.AWTException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
import java.util.Properties;
import java.util.Base64;
import java.util.Collections;
import java.util.List;



import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



import clases.Cita;
import clases.Cliente;
import clases.Coche;
import clases.CocheSegundaMano;
import clases.Moto;
import clases.MotoSegundaMano;
import clases.Trabajador;
import clases.Venta;

public class DAO {


	
	protected  String[] administradores = new String[]{"worker1", "worker2"};
	protected boolean esAdmin = false;
	protected SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    protected final String url = "jdbc:sqlite:/Users/jonmendizabal/ProyectoProgram/Proyecto-Concesionario-de-Coches-master/basedatos/baseDeDatos.sqlite";
    protected Connection conn;
    protected Cliente cliente= new Cliente();
    protected Trabajador trabajador = new Trabajador();
    
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
    public Cliente obtenerClientePorDNI(String dniCliente) {
    	try {
			conectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
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
                java.util.Date fechaNacimientoDate = stringToDate(fechaNacimiento,format);
                long numTarjeta = resultSet.getLong("numTarjeta");

                // Construir el objeto Cliente
                cliente = new Cliente(login, contraseña, email,dni, nombre, apellidos,fechaNacimientoDate,numTarjeta,"");
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

        return cliente;
    }
    public Cliente obtenerClientePorLogin(String loginCliente) {
    	try {
			conectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
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
                java.util.Date fechaNacimientoDate = stringToDate(fechaNacimiento,format);
                long numTarjeta = resultSet.getLong("numTarjeta");

                // Construir el objeto Cliente
                cliente = new Cliente(login, contraseña, email,dni, nombre, apellidos,fechaNacimientoDate,numTarjeta,"");
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
            pstmt.setString(7, dateToString(trabajador.getFechaNacimiento(),format));
            pstmt.setLong(8,  trabajador.getSueldo());
            pstmt.setBoolean(9, trabajador.esAdmin());
            pstmt.setString(10, trabajador.getPuesto());
            pstmt.executeUpdate();
            System.out.println("Nuevo usuario agregado a la base de datos");
        }
        desconectar();
        guardarActividad("Trabajador "+trabajador.getLogin()+" agregado");
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

            preparedStatement.setLong(7, cliente.getNumTarjeta());

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
        guardarActividad("Cliente "+cliente.getLogin()+" agregado");
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
	    guardarActividad("Cliente "+login+" eliminado");
	   
	}
	public boolean comprobarCredencialesTrabajador(String usuarioIngresado, String contrasenaIngresada) throws SQLException {
		trabajador.setAdmin(esAdmin(usuarioIngresado));
		System.out.print(trabajador.esAdmin());
		conectar();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean credencialesCorrectas = false;
        String dniTrabajador="";
        trabajador.setLogin(usuarioIngresado);
        try {

            String query = "SELECT * FROM trabajador WHERE login = ? AND contra = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, usuarioIngresado);
            preparedStatement.setString(2, contrasenaIngresada);
            
            resultSet = preparedStatement.executeQuery();
            dniTrabajador = resultSet.getString("dni");
            trabajador.setdNI(dniTrabajador);
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
        if (credencialesCorrectas) {
        	guardarActividad("Trabajador "+trabajador.getLogin()+" logeado");
        }
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
            if (credencialesCorrectas) {
            	cliente.setLogin(usuarioIngresado);
            	guardarActividad("Cliente "+usuarioIngresado+" logeado");
            
            	
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
	public void agregarCocheCompradoPorConcesionario(Coche coche) throws SQLException {
		String query = "INSERT INTO coche	 (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,ofertas,propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
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
            java.sql.Date matriculacionSQL = utilDateToSqlDate( coche.getMatriculacion());
            pstmt.setDate(10, matriculacionSQL);
            pstmt.setString(11, coche.getOfertas());
            pstmt.setString(12, coche.getPropietario());

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
	            java.sql.Date matriculacionSQL = utilDateToSqlDate( moto.getMatriculacion());
	            pstmt.setDate(10, matriculacionSQL);
	            pstmt.setInt(11, moto.getPeso());
	            pstmt.setBoolean(12, moto.isBaul());
	            pstmt.setString(13, moto.getOfertas());
	            pstmt.setString(14, moto.getPropietario());

	            pstmt.executeUpdate();
	            System.out.println("Moto agregada a la base de datos");
	        }
	        desconectar();
	    }
	public void agregarCocheSegundaManoCompradoPorConcesionario(CocheSegundaMano cocheSegundaMano, Cliente cliente) throws SQLException {
		String query = "INSERT INTO cocheSegundaMano	 (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,kilometraje,ofertas,propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
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
            java.sql.Date matriculacionSQL = utilDateToSqlDate( cocheSegundaMano.getMatriculacion());
            pstmt.setDate(11, matriculacionSQL);
            pstmt.setInt(12, cocheSegundaMano.getKilometraje());
            pstmt.setString(13, cocheSegundaMano.getOfertas());
            pstmt.setString(14, cocheSegundaMano.getPropietario());
            pstmt.executeUpdate();
            System.out.println("Coche agregado a la base de datos");

        }
        desconectar();
    }
	public void agregarMotoSegundaManoCompradaPorConcesionario(MotoSegundaMano motoSegundaMano, Cliente cliente) throws SQLException  {
		String query = "INSERT INTO CocheSegundaMano (idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,baul, kilometraje,ofertas,propietario) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
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
	            java.sql.Date matriculacionSQL = utilDateToSqlDate(motoSegundaMano.getMatriculacion());
	            pstmt.setDate(11, matriculacionSQL);
	            pstmt.setInt(12, motoSegundaMano.getPeso());
	            pstmt.setBoolean(13,motoSegundaMano.isBaul());
	            pstmt.setInt(14, motoSegundaMano.getKilometraje());
	            pstmt.setString(15, motoSegundaMano.getOfertas());
	            pstmt.setString(16, motoSegundaMano.getPropietario());
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
		            
		            String query = "INSERT INTO cocheSegundaMano(idVehiculo,dniCliente, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, kilometraje,ofertas,propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
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
		            preparedStatement.setString(14, cliente.getLogin());

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
	            String query = "INSERT INTO motoSegundaMano(idVehiculo, dniCliente, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,peso,potencia, kilometraje,ofertas,propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
	            preparedStatement = conn.prepareStatement(query);
	            preparedStatement.setInt(1, motoSegundaMano.getIdVehiculo());
	            preparedStatement.setString(2, motoSegundaMano.getCombustible());
	            preparedStatement.setString(3, motoSegundaMano.getMarca());
	            preparedStatement.setString(4, motoSegundaMano.getModelo());
	            preparedStatement.setString(5, motoSegundaMano.getColor());
	            preparedStatement.setString(6, motoSegundaMano.getTipo());
	            preparedStatement.setInt(7, motoSegundaMano.getPotencia());
	            preparedStatement.setInt(8, motoSegundaMano.getNumPlazas());
	            preparedStatement.setInt(9,motoSegundaMano.getPrecio());
	            preparedStatement.setInt(10, motoSegundaMano.getCuota());
	            java.sql.Date sqlMatriculacion = utilDateToSqlDate(motoSegundaMano.getMatriculacion());
	            preparedStatement.setDate(11, sqlMatriculacion);
	            preparedStatement.setInt(12, motoSegundaMano.getPeso());
	            preparedStatement.setInt(13, motoSegundaMano.getPotencia());
	            preparedStatement.setInt(14, motoSegundaMano.getKilometraje());
	            preparedStatement.setString(15, motoSegundaMano.getOfertas());
	            preparedStatement.setString(16, cliente.getLogin());
	            


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
	public void agregarVenta(Venta venta) {
	       
	       

	        String query = "INSERT INTO ventas (categoria, idVehiculo, marca, modelo, precioVenta, dniComprador) VALUES (?, ?, ?, ?, ?, ?)";

	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement pstmt = conn.prepareStatement(query)) {

	            pstmt.setString(1, venta.getCategoria());
	            pstmt.setInt(2, venta.getIdVehiculo());
	            pstmt.setString(3, venta.getMarca());
	            pstmt.setString(4, venta.getModelo());
	            pstmt.setInt(5, venta.getPrecioVenta());
	            pstmt.setString(6, venta.getDniComprador());

	            pstmt.executeUpdate();
	            System.out.println("Venta agregada correctamente a la base de datos.");
	        } catch (SQLException e) {
	            System.out.println("Error al agregar venta a la base de datos: " + e.getMessage());
	        }
	        guardarActividad("Venta de "+venta.getMarca()+" "+venta.getModelo()+" añadida");
	    }
	public void agregarOfertaAVehiculo(int idVehiculo, int oferta,String usuario) {
		
		 
         try {
        	 String query =  "SELECT * FROM coche WHERE idVehiculo = ? ";
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
		 	preparedStatement.setInt(0, idVehiculo);
		 	ResultSet resultSet = preparedStatement.executeQuery();
		 	String ofertas = resultSet.getString("ofertas");
		 	ofertas = ofertas +"Usuario: "+usuario+" Oferta: "+oferta+" €, ";
		 	preparedStatement.setString(12, ofertas);
		 	
         }catch (SQLException e){
        	 try {
        		 String query =  "SELECT * FROM cocheSegundaMano WHERE idVehiculo = ? ";
             	PreparedStatement preparedStatement = conn.prepareStatement(query);
     		 	preparedStatement.setInt(0, idVehiculo);
     		 	ResultSet resultSet = preparedStatement.executeQuery();
     		 	String ofertas = resultSet.getString("ofertas");
     		 	ofertas = ofertas +", Usuario: "+usuario+" Oferta: "+oferta+" €";
     			preparedStatement.setString(14, ofertas);
              }catch (SQLException e1){
            	  try {
             		 String query =  "SELECT * FROM moto WHERE idVehiculo = ? ";
                  	PreparedStatement preparedStatement = conn.prepareStatement(query);
          		 	preparedStatement.setInt(0, idVehiculo);
          		 	ResultSet resultSet = preparedStatement.executeQuery();
          		 	String ofertas = resultSet.getString("ofertas");
          		 	ofertas = ofertas +", Usuario: "+usuario+" Oferta: "+oferta+" €";
          			preparedStatement.setString(13, ofertas);
                   }catch (SQLException e2){
                	   try {
                  		 String query =  "SELECT * FROM motoSegundaMano WHERE idVehiculo = ? ";
                       	PreparedStatement preparedStatement = conn.prepareStatement(query);
               		 	preparedStatement.setInt(0, idVehiculo);
               		 	ResultSet resultSet = preparedStatement.executeQuery();
               		 	String ofertas = resultSet.getString("ofertas");
               		 	ofertas = ofertas +", Usuario: "+usuario+" Oferta: "+oferta+" €";
               		 	preparedStatement.setString(15, ofertas);
                        }catch (SQLException e3){
                       	 
                        }
                   }
              }
        	 
         }
         guardarActividad("Oferta de "+oferta+"€ añadida a vehiculo "+idVehiculo);
	 }
	public void cargarOfertasRecibidas(String tabla, DefaultTableModel tableModel,String propietario) throws AWTException {
			try {
				conectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String sql = "SELECT marca, modelo, ofertas FROM " + tabla + " WHERE propietario LIKE ?";
		  String  ofertaString="";
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
		                String vehiculo = marca +" "+ modelo;
		                
		                String[] ofertasArray = ofertas.split(",");
		                for (String oferta : ofertasArray) {
		                	 oferta = oferta.trim().replaceAll("\\s+", "");
		                    String[] ofertaArray = oferta.split(":");
		                    
		                    if (ofertaArray.length == 2) { // Verificamos si la oferta tiene el formato adecuado "login:oferta"
		                    	usuarioQueHaceOferta = ofertaArray[0];
		                        int ofertaFinal = Integer.parseInt(ofertaArray[1]);
		                        Object[] fila = {vehiculo, ofertaFinal, usuarioQueHaceOferta};
		                        tableModel.addRow(fila);
		                    } else {
		                        // Aquí podrías manejar ofertas con un formato incorrecto o inesperado
		                        System.out.println("Oferta con formato incorrecto: " + oferta);
		                    }
		                }
		                
		               
	            }else {
	            	
	            }
	        }
	         
	     }catch (SQLException e){
	    	 JOptionPane.showMessageDialog(null, "No tienes ofertas");
	     }
	     try {
			desconectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void cargarOfertasEnviadas(String tabla,DefaultTableModel tableModel,String usuario) {
		try {
			conectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 String sql = "SELECT * FROM " + tabla ;
     try {   
	 PreparedStatement statement = conn.prepareStatement(sql);
        	
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
           try {
            String ofertas = resultSet.getString("ofertas");
            String[] ofertasArray = null;
            if (ofertas != null) {
            	ofertasArray = ofertas.split(",");
            }
           
            String marca = resultSet.getString("marca");
            String modelo = resultSet.getString("modelo");
            String vehiculo = marca+""+modelo;
            boolean encontrado = false;
            if(ofertas!=null) {
            	for (String oferta: ofertasArray) {
            		while(encontrado=false){
            			String[] ofertaArray = oferta.split(" ");
            			String usuarioQueHaceOferta = ofertaArray[2];
            			if (usuarioQueHaceOferta==usuario) {
            				encontrado=true;
            				int ofertaHecha = Integer.parseInt(ofertaArray[4]);
            				Object[] fila = {vehiculo, ofertaHecha};
            				tableModel.addRow(fila);
            			}
            		}
            	}
            }	
           }catch (SQLException e) {
        	   
           }
        } 
        
     }catch (SQLException e){
    	 JOptionPane.showMessageDialog(null, "No tienes ofertas");
     }
     try {
		desconectar();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}	
	public java.util.Date sqlDateToJavaDate (java.sql.Date sqlDate){
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		return utilDate;
	}
	public java.sql.Date utilDateToSqlDate (java.util.Date utilDate){
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}
	public java.util.Date stringToDate(String fechaNacimientoString, SimpleDateFormat format2) {
		 try {
	            return format2.parse(fechaNacimientoString);
	        } catch (ParseException e) {
	            // Manejar cualquier error de análisis de fecha aquí
	        	java.util.Date fechaNacimiento = new java.util.Date();
	            return fechaNacimiento; // O podrías lanzar una excepción, dependiendo del caso
	        }
	}
	public String dateToString (java.util.Date date, SimpleDateFormat format2) {
		
		return format2.format(date);
	}
	public void eliminarVehiculo(String nombreCoche, String tabla) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establecer la conexión con la base de datos (reemplaza con tus propios datos de conexión)
            connection = DriverManager.getConnection(url);

            // Sentencia SQL para eliminar un coche por nombre
            String sql = "DELETE FROM "+tabla+" WHERE marca = ? AND modelo = ? and propietario = ?";
            String[] nombreCocheDividido = nombreCoche.split(" ");
            System.out.print(nombreCocheDividido);
            String marcaCoche = nombreCocheDividido[0];
            String modeloCoche = nombreCocheDividido[1];
            // Preparar la sentencia
            statement = connection.prepareStatement(sql);
            statement.setString(1, marcaCoche);
            statement.setString(2, modeloCoche);
            statement.setString(3, cliente.getLogin());
            // Establecer el nombre del coche a eliminar

            // Ejecutar la sentencia de eliminación
            int filasEliminadas = statement.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("El coche ha sido eliminado correctamente.");
            } else {
                System.out.println("No se encontró el coche para eliminar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (registra, notifica, etc.)
        } finally {
            // Cerrar statement y conexión
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        guardarActividad("Vehiculo "+nombreCoche+" eliminado");
    }
	public void eliminarOferta(String cocheAceptado, String usuarioAceptado, int precioAceptado) {
	        // Conexión a la base de datos (asegúrate de tener los datos correctos)
	       	String[] cocheDatos = cocheAceptado.split(" ");
	       	String marca = cocheDatos[0];
	       	String modelo = cocheDatos[1];
	       	String ofertaNueva = "";
	        try (Connection connection = DriverManager.getConnection(url)) {
	            String[] tablas = { "coche", "cocheSegundaMano", "moto", "motoSegundaMano" };

	            for (String tabla : tablas) {
	                String sql = "SELECT ofertas FROM " + tabla + " WHERE propietario = ? AND marca = ? AND modelo = ?";
	                PreparedStatement statement = connection.prepareStatement(sql);
	                statement.setString(1, cliente.getLogin());
	                statement.setString(2, marca);
	                statement.setString(3, modelo);

	                ResultSet resultSet = statement.executeQuery();
	                String ofertaABorrar = usuarioAceptado+": "+precioAceptado;
	                if (resultSet.next()) {
	                	String oferta = resultSet.getString("ofertas");
	                	String[] ofertas = oferta.split(",");
	                	for(String ofertaString: ofertas) {
	                		if (ofertaString ==ofertaABorrar) {
	                			ofertaString="";
	                			
	                		}else {
	                			ofertaNueva= ofertaNueva+", "+ofertaString;
	                		}
	                	}
	                    // Modificar el valor de la oferta (restar, sumar, etc.)
	                   

	                    // Actualizar el valor de la oferta en la tabla
	                    sql = "UPDATE " + tabla + " SET ofertas = ? WHERE propietario = ? AND marca = ? AND modelo = ?";
	                    statement = connection.prepareStatement(sql);
	                    statement.setString(1, ofertaNueva);
	                    statement.setString(2, usuarioAceptado);
	                    statement.setString(3, marca);
	                    statement.setString(4, modelo);
	                    statement.executeUpdate();

	                    // Eliminar la oferta si es necesario
	                 
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Error al eliminar la oferta: " + e.getMessage());
	        }
	    }
	public void añadirCita(Cita cita, DAO dao) {
		Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establecer la conexión con la base de datos (reemplaza con tus propios datos de conexión)
            connection = DriverManager.getConnection(url);

            // Sentencia SQL para eliminar un coche por nombre
            String sql = "INSERT INTO citas(fecha, usuario, nombreVehiculo) VALUES (?,?,?)";
            String fecha = cita.getFecha();
           
            // Preparar la sentencia
            statement = connection.prepareStatement(sql);
            statement.setString(1, fecha);
            statement.setString(2,cita.getUsuario());
            statement.setString(3, cita.getNombreVehiculo());
            // Establecer el nombre del coche a eliminar

            // Ejecutar la sentencia de eliminación
            int filasEliminadas = statement.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("La cita ha sido añadida correctamente");
               
            } else {
                System.out.println("No se pudo añadir la cita, contacte con atención al cliente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (registra, notifica, etc.)
        } finally {
            // Cerrar statement y conexión
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
	}
	public boolean esAdmin (String usuario) {
		boolean adminEs = false;
		Connection conn = null;
		PreparedStatement statement = null;
		int adminInt = 0;
		try {
			conn = DriverManager.getConnection(url);
			String sql = "SELECT * FROM trabajador WHERE login = ?";
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, usuario);
			ResultSet resultSet = statement.executeQuery();
			adminInt = resultSet.getInt("esAdmin");
			conn.close();
		}catch (SQLException e) {
			
		}
		if (adminInt==1) {
			adminEs=true;
		}
		return adminEs;
	}
	public void cargarDatosTrabajadores(DefaultTableModel tableModel) {
		Connection conn;
		PreparedStatement statement;
		try {
			conn = DriverManager.getConnection(url);
			
			String sql = "SELECT * FROM trabajador";
			statement = conn.prepareStatement(sql);
        	
	        ResultSet resultSet = statement.executeQuery();
	        
	        while(resultSet.next()) {
	        	String usuario = resultSet.getString("login");
	        	String contra = resultSet.getString("contra");
	        	String  email = resultSet.getString("email");
	        	String dni = resultSet.getString("dni");
	        	String nombre = resultSet.getString("nombre");
	        	String apellidos = resultSet.getString("apellidos");
	        	String fechaNacimiento = resultSet.getString("fechaNacimiento");
	        	int sueldo = resultSet.getInt("sueldo");
	        	String puesto = resultSet.getString("puesto");
	        	Object[] fila = {usuario,contra,email,dni,nombre,apellidos,fechaNacimiento,sueldo,puesto};
	 	        tableModel.addRow(fila);
	        }
	       
			
			
		}catch (SQLException e){
			
		}
		try {
			desconectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void eliminarTrabajador(String loginTrabajador) {
		Connection conn = null;
		PreparedStatement statement = null;
		int filasEliminadas = 0;
		try {
			conn = DriverManager.getConnection(url);
			String sql = "DELETE FROM trabajador WHERE login = ?";
			System.out.print(loginTrabajador);
			statement = conn.prepareStatement(sql);
			statement.setString(1, loginTrabajador);
			
			filasEliminadas = statement.executeUpdate();
			conn.close();
		} catch (SQLException e) {
	        e.printStackTrace(); // Imprime la traza del error para depurar
	        JOptionPane.showMessageDialog(null, "Error al eliminar el trabajador: " + e.getMessage());
	    } 
		guardarActividad("Trabajador "+loginTrabajador+" eliminado");
         
	}
	public void cargarActividad(DefaultTableModel tableModel) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = DriverManager.getConnection(url);
			String sql = "SELECT * FROM actividad";
			statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				String usuario = resultSet.getString("usuario");
				String fecha = resultSet.getString("fecha");
				String actividad = resultSet.getString("actividad");
				Object[] fila = {usuario,fecha,actividad};
				tableModel.addRow(fila);
			}
			conn.close();
			
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar la actividad");
		}
		
	}
	public void guardarActividad(String actividad) {
		Connection conn = null;
		PreparedStatement statement = null;
		LocalTime hora = LocalTime.now();
		LocalDate dia = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = hora.format(formato);
		String fecha = dia+" "+horaFormateada;
		int filasAfectadas = 0;
		try {
			conn = DriverManager.getConnection(url);
			String sql = "INSERT INTO actividad(usuario,fecha,actividad)  VALUES (?,?,?)";
			statement = conn.prepareStatement(sql);
			statement.setString(1, trabajador.getLogin());
			statement.setString(2, fecha);
			statement.setString(3, actividad);
			
			filasAfectadas = statement.executeUpdate();
			conn.close();
			
		}catch (SQLException e) {
			
		}
		if (filasAfectadas>-1) {
			
		}else {
			JOptionPane.showMessageDialog(null, "No se pudo registrar la actividad");
		}
		
	}
	
	public void guardarSiCierraVentana() {
		if (trabajador.getLogin()!=null) {
			guardarActividad("Trabajador: "+trabajador.getLogin()+" cerró sesión");
		}else {
			guardarActividad("Usuario: "+cliente.getLogin()+" cerró sesión");
		}
	}
	public static int generarID() {
        return (int) (Math.random() * 1000000); // Puedes ajustar el rango según tus necesidades
    }
	 public static boolean idExiste(Connection conn, int id) throws SQLException {
	        String[] tablas = {"coche", "cocheSegundaMano", "moto", "motoSegundaMano"};

	        for (String tabla : tablas) {
	            String query = "SELECT COUNT(*) FROM " + tabla + " WHERE idVehiculo = ?";
	            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	                pstmt.setInt(1, id);
	                ResultSet rs = pstmt.executeQuery();
	                if (rs.next() && rs.getInt(1) > 0) {
	                    return true; // El ID existe en al menos una tabla
	                }
	            }
	        }
	        return false; // El ID no existe en ninguna tabla
	    }
	public int asignarIdAVehiculo() throws SQLException {
		 int idVehiculo;
	        do {
	            idVehiculo = generarID(); // Generar un ID aleatorio
	        } while (idExiste(conn, idVehiculo)); // Verificar si el ID ya existe en las tablas

	        return idVehiculo;
	}
	
	
	
	
	}
	

	



