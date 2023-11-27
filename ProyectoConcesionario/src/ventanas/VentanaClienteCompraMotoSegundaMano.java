package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentanaClienteCompraMotoSegundaMano extends JFrame {
    private JTable motosTable;
    private DefaultTableModel model;
    private DAO dao = new DAO();
    public VentanaClienteCompraMotoSegundaMano() {
        setTitle("Inventario de Coches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        model = new DefaultTableModel();
        motosTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(motosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = motosTable.getSelectedRow();
                if (selectedRow != -1) {
                    confirmarCompra();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un coche para comprar.");
                }
            }
        });
        panel.add(comprarButton, BorderLayout.SOUTH);

        add(panel);
        cargarDatosDesdeDB();
    }

    private void cargarDatosDesdeDB() {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection(dao.url);
            statement = conn.createStatement();
            String query = "SELECT * FROM MotoSegundaMano"; 
            ResultSet resultSet = statement.executeQuery(query);

            // Obtener información sobre las columnas
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }

            // Obtener y mostrar los datos de la tabla en la tabla Swing
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
        int selectedRow = motosTable.getSelectedRow();
        int idVehiculo = (int) motosTable.getValueAt(selectedRow, 0);
        int precio = (int) motosTable.getValueAt(selectedRow, 8); 

        int opcion = JOptionPane.showConfirmDialog(null, "Seguro que desea comprar este vehículo?",
                "Confirmación de Compra", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
           
            JOptionPane.showMessageDialog(null, "Te hemos enviado un correo electrónico para proceder con la compra.");
            
        }
    }

   

   
}
