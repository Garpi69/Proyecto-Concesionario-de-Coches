package ventanas;

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

public class VentanaInventario extends JFrame {

	private JTable inventarioTable;
    private DefaultTableModel tableModel;
    private DAO dao = new DAO();
    public VentanaInventario() {
        setTitle("Inventario de Vehículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacios entre componentes

        JTextField marcaField = new JTextField(10);
        JTextField modeloField = new JTextField(10);
        JTextField colorField = new JTextField(10);

        JButton filtrarButton = new JButton("Filtrar");
        filtrarButton.addActionListener(e -> {
        tableModel.setRowCount(0);
        filtrarCoche(marcaField.getText(), modeloField.getText(), colorField.getText());
        filtrarCocheSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText());
        filtrarMoto(marcaField.getText(), modeloField.getText(), colorField.getText());
        filtrarMotoSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText());
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Marca:"), gbc);

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

        tableModel = new DefaultTableModel();
        inventarioTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventarioTable);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        cargarInventario();
        setVisible(true);
    }
   public void cargarInventario() {
        // Asegúrate de tener la conexión con tu base de datos
        try {
            Connection connection = DriverManager.getConnection(dao.url);
            String[] columnas = {"ID", "Combustible", "Marca","Modelo", "Color","Tipo","Potencia","Numero de plazas", "Precio","Cuota","Matriculacion","Peso (Motos)","Baul (Motos)","Kilometraje (Segunda Mano"};
            tableModel.setColumnIdentifiers(columnas);
         
            // Obtener datos de coches
            cargarDatosVehiculos(connection, "coche");

            // Obtener datos de coches de segunda mano
            cargarDatosVehiculos(connection, "cocheSegundaMano");

            // Obtener datos de motos
            cargarDatosVehiculos(connection, "moto");

            // Obtener datos de motos de segunda mano
            cargarDatosVehiculos(connection, "motoSegundaMano");

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());
        }
    }

    private void cargarDatosVehiculos(Connection connection, String tabla) throws SQLException {
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
			try {
				matriculacion = dao.stringToDate(resultSet.getString("matriculacion"),dao.format);
			} catch (ParseException | SQLException e) {
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
            boolean baulBoolean =false;
            if (baul==1) {
            	baulBoolean=true;
            }
           
			
           
            Object[] fila = {id,combustible, marca, modelo,color, tipo,potencia,numPlazas, precio,cuota,matriculacion,peso,baulBoolean,kilometraje};
            tableModel.addRow(fila);
        }
        
        resultSet.close();
        statement.close();
    }

    private void filtrarCoche(String marca, String modelo, String color) {
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
                String matriculacion = resultSet.getString("matriculacion");
                
                Date matriculacionDate = null;
                try {
					 matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate};
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
    private void filtrarCocheSegundaMano(String marca, String modelo, String color) {
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
                try {
					 matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                kilometraje = getIntOrNull(resultSet,"kilometraje");
                
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,0,0,kilometraje};
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
    private void filtrarMoto(String marca, String modelo, String color) {
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
                int baul=0;
                int peso =0;
                Date matriculacionDate = null;
                try {
					 matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                String baulString ="No";
                baul= getIntOrNull(resultSet,"baul");
                peso = getIntOrNull(resultSet,"peso");
                if (baul==1) {
                	baulString = "Si";
                }
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,peso,baulString};
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
    private void filtrarMotoSegundaMano(String marca, String modelo, String color) {
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
                int baul=0;
                int peso =0;
                int kilometraje = 0;
                Date matriculacionDate = null;
                try {
					 matriculacionDate = dao.stringToDate(matriculacion, dao.format);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                baul= getIntOrNull(resultSet,"baul");
                peso = getIntOrNull(resultSet,"peso");
                kilometraje = getIntOrNull(resultSet,"kilometraje");
                String baulString ="No";
                if (baul==1) {
                	baulString = "Si";
                }
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,peso,baulString,kilometraje};
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

