package ventanas;
import clases.Venta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class VentanaMenuClienteCompraVehiculo extends JFrame {

	JTable inventarioTable;
    private DefaultTableModel tableModel;
  
    public VentanaMenuClienteCompraVehiculo(DAO dao) {
        setTitle("Inventario de Vehículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacios entre componentes
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5); // Espacios entre componentes
        
        JTextField marcaField = new JTextField(10);
        JTextField modeloField = new JTextField(10);
        JTextField colorField = new JTextField(10);

        JButton filtrarButton = new JButton("Filtrar");
        filtrarButton.addActionListener(e -> {
        tableModel.setRowCount(0);
        try {
			filtrarCoche(marcaField.getText(), modeloField.getText(), colorField.getText(),dao);
			filtrarCocheSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText(),dao);
		    filtrarMoto(marcaField.getText(), modeloField.getText(), colorField.getText(),dao);
		    filtrarMotoSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText(),dao);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Marca:"), gbc);
        JButton cocheButton = new JButton("Coche");
        cocheButton.addActionListener(e -> {
        	try {	
            	Connection connection = DriverManager.getConnection(dao.url);
            	tableModel.setRowCount(0);
                cargarDatosVehiculos(connection,"coche",dao);
            }catch (SQLException e1) {
            	
            }
            });

        JButton cocheSegundaManoButton = new JButton("Coche de Segunda Mano");
        cocheSegundaManoButton.addActionListener(e -> {
        	try {	
            	Connection connection = DriverManager.getConnection(dao.url);
            	tableModel.setRowCount(0);
                cargarDatosVehiculos(connection,"cocheSegundaMano",dao);
            }catch (SQLException e1) {
            	
            }
            });

        JButton motoButton = new JButton("Moto");
        motoButton.addActionListener(e -> {
        	try {	
            	Connection connection = DriverManager.getConnection(dao.url);
            	tableModel.setRowCount(0);
                cargarDatosVehiculos(connection,"moto",dao);
            }catch (SQLException e1) {
            	
            }
            });

        JButton motoSegundaManoButton = new JButton("Moto de Segunda Mano");
        motoSegundaManoButton.addActionListener(e -> {
        try {	
        	Connection connection = DriverManager.getConnection(dao.url);
        	tableModel.setRowCount(0);
            cargarDatosVehiculos(connection,"motoSegundaMano",dao);
        }catch (SQLException e1) {
        	
        }
        });

        // Añadir los botones al panel de filtros
        gbc2.gridx = 7;
        buttonPanel.add(cocheButton, gbc2);

        gbc2.gridx = 8;
        buttonPanel.add(cocheSegundaManoButton, gbc2);

        gbc2.gridx = 9;
        buttonPanel.add(motoButton, gbc2);

        gbc2.gridx = 10;
        buttonPanel.add(motoSegundaManoButton, gbc2);

        gbc.gridx = 1;
        filterPanel.add(marcaField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 3;
        filterPanel.add(modeloField, gbc);

        gbc.gridx = 4;
        filterPanel.add(new JLabel("Color:"), gbc);

        gbc.gridx = 5;
        filterPanel.add(colorField, gbc);

        gbc.gridx = 6;
        filterPanel.add(filtrarButton, gbc);
        JButton comprarButton = new JButton("Comprar");
        gbc.gridx=7;
        filterPanel.add(comprarButton,gbc);
        JButton hacerOfertaButton = new JButton("Hacer oferta");
        gbc.gridx=8;
        filterPanel.add(hacerOfertaButton,gbc);
        tableModel = new DefaultTableModel();
        inventarioTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventarioTable);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH );
        cargarInventario(dao);
        comprarButton.addActionListener(e ->{
        	 if (inventarioTable.getSelectedRow()!=-1) {
             	confirmarCompra(dao);
             }else {
            	 JOptionPane.showMessageDialog(null, "Seleccione un vehiculo");
             }
        	
        });
       hacerOfertaButton.addActionListener(e -> {
    	   int row = inventarioTable.getSelectedRow();
    	   int idVehiculo = (int) inventarioTable.getValueAt(row, 0);
    	   VentanaOferta ventanaOferta = new VentanaOferta(idVehiculo,dao.cliente.getLogin()
    			   );
       });
        setVisible(true);
    }
   public void cargarInventario(DAO dao) {
        // Asegúrate de tener la conexión con tu base de datos
        try {
            Connection connection = DriverManager.getConnection(dao.url);
            String[] columnas = {"ID", "Combustible", "Marca","Modelo", "Color","Tipo","Potencia","Numero de plazas", "Precio","Cuota","Matriculacion","Peso (Motos)","Baul (Motos)","Kilometraje (Segunda Mano","Propietario"};
            tableModel.setColumnIdentifiers(columnas);
         
            // Obtener datos de coches
            cargarDatosVehiculos(connection, "coche",dao);

            // Obtener datos de coches de segunda mano
            cargarDatosVehiculos(connection, "cocheSegundaMano",dao);

            // Obtener datos de motos
            cargarDatosVehiculos(connection, "moto",dao);

            // Obtener datos de motos de segunda mano
            cargarDatosVehiculos(connection, "motoSegundaMano",dao);

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());
        }
    }

    private void cargarDatosVehiculos(Connection connection, String tabla, DAO dao) throws SQLException {
        String sql = "SELECT * FROM " + tabla;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        
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
            Date matriculacion=null;
            String matriculacionString= resultSet.getString("matriculacion");
            String propietario = resultSet.getString("propietario");
			try {
				if (matriculacionString!=null) {
					matriculacion = dao.stringToDate(resultSet.getString("matriculacion"),dao.format);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            int kilometraje =0;
            int peso =0;
            int baul = 0;

            try {
               kilometraje = resultSet.getInt("kilometraje");
            } catch (SQLException e) {
                // La columna "kilometraje" no existe o no se puede obtener, se asigna el valor predeterminado (0 en este caso)
            }try{
            	 peso = resultSet.getInt("peso");
            }catch (SQLException e) {
            	
            }try {
            	  baul = resultSet.getInt("baul");
                 
            }catch (SQLException e) {
            }
           String baulString= "";
            if (baul==1) {
            	baulString="Si";
            }else {
            	baulString = "No";
            }
           
        	
           
            Object[] fila = {id,combustible, marca, modelo,color, tipo,potencia,numPlazas, precio,cuota,matriculacion,peso,baulString,kilometraje,propietario};
            tableModel.addRow(fila);
        }
       
        resultSet.close();
        statement.close();
    }
    private void confirmarCompra(DAO dao) {
        int selectedRow = inventarioTable.getSelectedRow();
        if (selectedRow != -1) {
            int idVehiculo = (int) inventarioTable.getValueAt(selectedRow, 0);
            int precio = (int) inventarioTable.getValueAt(selectedRow, 8);
        	String marca = (String)inventarioTable.getValueAt(inventarioTable.getSelectedRow(),3 );
    		String modelo = (String)inventarioTable.getValueAt(inventarioTable.getSelectedRow(),4 );
            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea comprar este vehículo?",
                    "Confirmación de Compra", JOptionPane.YES_NO_OPTION);
            String categoria = "";
            String nombreCoche = marca+" "+modelo;
            if (opcion == JOptionPane.YES_OPTION) {
              new DateTimePicker(dao,nombreCoche);
              if ((int)inventarioTable.getValueAt(selectedRow,7)>2) {
            	  if(inventarioTable.getValueAt(selectedRow, 13)!=null) {
            		  categoria = "Coche";
            	  }else {
            		  categoria = "Coche de segunda mano";
            	  }
              }else {
            	  if(inventarioTable.getValueAt(selectedRow, 13)!=null) {
            		  categoria = "Moto";
            	  }else {
            		  categoria = "Moto de segunda mano";
            	  }
              }
              String dniCliente=dao.trabajador.getdNI();
              Venta venta = new Venta(categoria,idVehiculo,marca,modelo,precio,dniCliente);
              dao.agregarVenta(venta);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un coche para comprar.");
        }
    }
    private void filtrarCoche(String marca, String modelo, String color,DAO dao) throws ParseException {
        try {
            Connection connection = DriverManager.getConnection(dao.url);
           
            String sql = "SELECT * FROM coche WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";;;
            PreparedStatement statement = connection.prepareStatement(sql);
           
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
            
                String propietario = resultSet.getString("propietario");
                Date matriculacionDate = null;
                try {
                	 matriculacionDate = dao.stringToDate( resultSet.getString("matriculacion"), dao.format);
                }catch (SQLException e){
                	
                }
               
               
                
                
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,propietario};
                tableModel.addRow(fila);
            }
           
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
        }
        inventarioTable.repaint();
    }
    private void filtrarCocheSegundaMano(String marca, String modelo, String color, DAO dao) {
        try {
            Connection connection = DriverManager.getConnection(dao.url);
           

            String sql = "SELECT * FROM cocheSegundaMano WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";;;
            PreparedStatement statement = connection.prepareStatement(sql);
           
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
                int kilometraje = 0;
                Date matriculacionDate = null;
                String propietario = resultSet.getString("propietario");
                matriculacionDate = dao.stringToDate(matriculacion, dao.format);
                kilometraje = getIntOrNull(resultSet,"kilometraje");
                
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,0,0,kilometraje,propietario};
                tableModel.addRow(fila);
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
        }
        inventarioTable.repaint();
       
    }
    private void filtrarMoto(String marca, String modelo, String color, DAO dao) {
        try {
            Connection connection = DriverManager.getConnection(dao.url);
           

            String sql = "SELECT * FROM moto WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";;;
            PreparedStatement statement = connection.prepareStatement(sql);
           
                statement.setString(1, "%" + marca + "%");
                statement.setString(2, "%" + modelo + "%");
                statement.setString(3, "%" + color + "%");
              
            
            

            ResultSet resultSet = statement.executeQuery();
           
            while (resultSet.next()) {
                int id = resultSet.getInt("idVehiculo");
                String combustible = resultSet.getString("combustible");
                String marcaReal = resultSet.getString("marca");
                String modeloReal = resultSet.getString("modelo");
                String colorReal = resultSet.getString("color");
                String tipo = resultSet.getString("tipo");
                int potencia = resultSet.getInt("potencia");
                int numPlazas = resultSet.getInt("numPlazas");
                int precio = resultSet.getInt("precio");
                int cuota = resultSet.getInt("cuota");
                String matriculacion = resultSet.getString("matriculacion");
                String propietario = resultSet.getString("propietario");
                int baul=0;
                int peso =0;
                Date matriculacionDate = null;
                matriculacionDate = dao.stringToDate(matriculacion, dao.format);
                String baulString ="No";
                baul= getIntOrNull(resultSet,"baul");
                peso = getIntOrNull(resultSet,"peso");
                if (baul==1) {
                	baulString = "Si";
                }
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,peso,baulString,propietario};
                tableModel.addRow(fila);
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
        }
        inventarioTable.repaint();
       
        }
    private void filtrarMotoSegundaMano(String marca, String modelo, String color, DAO dao) {
        try {
            Connection connection = DriverManager.getConnection(dao.url);
           

            String sql = "SELECT * FROM moto WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";;;
            PreparedStatement statement = connection.prepareStatement(sql);
           
                statement.setString(1, "%" + marca + "%");
                statement.setString(2, "%" + modelo + "%");
                statement.setString(3, "%" + color + "%");
              
            
            

            ResultSet resultSet = statement.executeQuery();
           
            while (resultSet.next()) {
                int id = resultSet.getInt("idVehiculo");
                String combustible = resultSet.getString("combustible");
                String marcaReal = resultSet.getString("marca");
                String modeloReal = resultSet.getString("modelo");
                String colorReal = resultSet.getString("color");
                String tipo = resultSet.getString("tipo");
                int potencia = resultSet.getInt("potencia");
                int numPlazas = resultSet.getInt("numPlazas");
                int precio = resultSet.getInt("precio");
                int cuota = resultSet.getInt("cuota");
                String matriculacion = resultSet.getString("matriculacion");
                String propietario = resultSet.getString("propietario");
                int baul=0;
                int peso =0;
                int kilometraje = 0;
                Date matriculacionDate = null;
                matriculacionDate = dao.stringToDate(matriculacion, dao.format);
                baul= getIntOrNull(resultSet,"baul");
                peso = getIntOrNull(resultSet,"peso");
                kilometraje = getIntOrNull(resultSet,"kilometraje");
                String baulString ="No";
                if (baul==1) {
                	baulString = "Si";
                }
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,peso,baulString,kilometraje,propietario};
                tableModel.addRow(fila);
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
        }
        inventarioTable.repaint();
       
    }
    private int getIntOrNull(ResultSet resultSet, String column) {
        int valor = 0; // Valor por defecto si la columna no existe
        try {
            valor = resultSet.getInt(column);
        } catch (SQLException e) {
            // La columna no existe o no se puede obtener
            // Se mantiene el valor por defecto
        }
        return valor;
    }
    
}

