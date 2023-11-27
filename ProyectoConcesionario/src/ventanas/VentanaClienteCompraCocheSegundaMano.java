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

public class VentanaClienteCompraCocheSegundaMano extends JFrame {
    private JTable cochesTable;
    private DefaultTableModel model;
    private DAO dao = new DAO();
    public VentanaClienteCompraCocheSegundaMano() {
        setTitle("Inventario de Coches de Segunda Mano");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        model = new DefaultTableModel();
        cochesTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(cochesTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cochesTable.getSelectedRow();
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
            String query = "SELECT * FROM CocheSegundaMano"; 

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
        int selectedRow = cochesTable.getSelectedRow();
        int idVehiculo = (int) cochesTable.getValueAt(selectedRow, 0);
        int precio = (int) cochesTable.getValueAt(selectedRow, 8); // Suponiendo que el precio está en la columna 8

        int opcion = JOptionPane.showConfirmDialog(null, "Seguro que desea comprar este vehículo?",
                "Confirmación de Compra", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
           
            JOptionPane.showMessageDialog(null, "Te hemos enviado un correo electrónico para proceder con la compra.");
           // FALTA QUE EL COCHE SE ELIMINE DE LA TABLA Y SE AÑADA A VENTAS
        }
    }

   

   
}

