package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BorderFactory;
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

public class VentanaClienteCompraCocheSegundaMano extends JFrame {
    private JTable cochesTable;
    private DefaultTableModel model;
    private DAO dao = new DAO();

    public VentanaClienteCompraCocheSegundaMano() {
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
         model.setRowCount(0);
         filtrarCocheSegundaMano(marcaField.getText(), modeloField.getText(), colorField.getText());
         
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

         model = new DefaultTableModel();
         cochesTable = new JTable(model);
         JScrollPane scrollPane = new JScrollPane(cochesTable);

         getContentPane().setLayout(new BorderLayout());
         getContentPane().add(filterPanel, BorderLayout.NORTH);
         getContentPane().add(scrollPane, BorderLayout.CENTER);

         cargarDatosDesdeDB();
         setVisible(true);
     }

    private void cargarDatosDesdeDB() {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection(dao.url);
            statement = conn.createStatement();
            String query = "SELECT * FROM cocheSegundaMano";

            ResultSet resultSet = statement.executeQuery(query);

            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void confirmarCompra() {
        int selectedRow = cochesTable.getSelectedRow();
        if (selectedRow != -1) {
            int idVehiculo = (int) cochesTable.getValueAt(selectedRow, 0);
            int precio = (int) cochesTable.getValueAt(selectedRow, 8);
            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea comprar este vehículo?",
                    "Confirmación de Compra", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Te hemos enviado un correo electrónico para proceder con la compra.");
                // FALTA ELIMINAR EL COCHE COMPRADO DE LA TABLA
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un coche para comprar.");
        }
    }

    private void estilizarBoton(JButton button) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(Color.decode("#3F51B5"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#6573C3"));
            }

            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#3F51B5"));
            }
        });
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
                matriculacionDate = dao.stringToDate(matriculacion, dao.format);
                kilometraje = getIntOrNull(resultSet,"kilometraje");
                
                // Resto del código para obtener los valores del vehículo

                Object[] fila = {id, combustible, marcaReal, modeloReal, colorReal, tipo, potencia, numPlazas, precio, cuota, matriculacionDate,0,0,kilometraje};
                model.addRow(fila);
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar el inventario: " + ex.getMessage());
        }
        cochesTable.repaint();
       
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

