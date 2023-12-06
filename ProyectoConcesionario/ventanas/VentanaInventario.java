package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class VentanaInventario extends JFrame {
	
	private JTable inventarioTable;
    private DefaultTableModel tableModel;
    private DAO dao = new DAO();
    public VentanaInventario() {
        setTitle("Inventario de Vehículos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        JTextField marcaField = new JTextField(10);
        JTextField modeloField = new JTextField(10);
        JTextField colorField = new JTextField(10);

        JButton filtrarButton = new JButton("Filtrar");
        filtrarButton.addActionListener(e -> filtrarVehiculos(marcaField.getText(), modeloField.getText(), colorField.getText()));

        filterPanel.add(new JLabel("Marca:"));
        filterPanel.add(marcaField);
        filterPanel.add(new JLabel("Modelo:"));
        filterPanel.add(modeloField);
        filterPanel.add(new JLabel("Color:"));
        filterPanel.add(colorField);
        filterPanel.add(filtrarButton);
        tableModel = new DefaultTableModel();
        inventarioTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventarioTable);
        setVisible(true);
        add(scrollPane);
        cargarInventario();
        
    }

   public void cargarInventario() {
        // Asegúrate de tener la conexión con tu base de datos
        try {
            Connection connection = DriverManager.getConnection(dao.url);
            String[] columnas = {"ID", "Combustible", "Marca","Modelo", "Color","Tipo","Potencia","Numero de plazas", "Precio","Cuota","Matriculacion","Peso (Motos)","Baul (Motos)","Kilometraje (Segunda Mano"};
            tableModel.setColumnIdentifiers(columnas);

            // Obtener datos de coches
            cargarDatosVehiculos(connection, "Coche");

            // Obtener datos de coches de segunda mano
            cargarDatosVehiculos(connection, "CocheSegundaMano");

            // Obtener datos de motos
            cargarDatosVehiculos(connection, "Moto");

            // Obtener datos de motos de segunda mano
            cargarDatosVehiculos(connection, "MotoSegundaMano");

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
           
           
            try {
               kilometraje = resultSet.getInt("kilometraje");
            } catch (SQLException e) {
                // La columna "kilometraje" no existe o no se puede obtener, se asigna el valor predeterminado (0 en este caso)
            }
            Object[] fila = {id,combustible, marca, modelo,color, tipo,potencia,numPlazas, precio,cuota,matriculacion};
            tableModel.addRow(fila);
        }

        resultSet.close();
        statement.close();
    }

    private void filtrarVehiculos(String marca, String modelo, String color) {
        try {
            Connection connection = DriverManager.getConnection(dao.url);
            tableModel.setRowCount(0);

            String sql = "SELECT * FROM Coche WHERE marca LIKE ? AND modelo LIKE ? AND color LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + marca + "%");
            statement.setString(2, "%" + modelo + "%");
            statement.setString(3, "%" + color + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	while (resultSet.next()) {
            	    int id = resultSet.getInt("idVehiculo");
            	    String combustible = resultSet.getString("combustible");
            	    
            	    String tipo = resultSet.getString("tipo");
            	    int potencia = resultSet.getInt("potencia");
            	    int numPlazas = resultSet.getInt("plazas");
            	    int precio = resultSet.getInt("precio");
            	    int cuota = resultSet.getInt("cuota");
            	    Date matriculacion = resultSet.getDate("matriculacion");

            	    int peso = 0;
            	    boolean baul = false;
            	    int kilometraje = 0;

            	    // Resto del código para obtener los valores del vehículo

            	    Object[] fila = {id, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, peso, baul, kilometraje};
            	    tableModel.addRow(fila);
            	}

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
    	new VentanaInventario();
    }
}

