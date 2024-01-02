package ventanas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaVerCitas extends JFrame {

    private JTable tablaCitas;
    private DefaultTableModel model;

    public VentanaVerCitas(DAO dao) {
        super("Citas");

        model = new DefaultTableModel();
        tablaCitas = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);

        // Configurar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980	, 400);
        setLocationRelativeTo(null);

        // Agregar la tabla a la ventana
        add(scrollPane);

        // Obtener los datos de la tabla "citas" y cargarlos en la tabla
        obtenerDatosCitas(dao);
        setVisible(true);
    }

    private void obtenerDatosCitas(DAO dao) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dao.url);
            String sql = "SELECT fecha, usuario, nombreVehiculo FROM citas";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Obtener metadatos de las columnas
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear columnas para la tabla
            
            model.addColumn("Fecha");
            model.addColumn("Usuario");
            model.addColumn("Vehiculo");
            

            // Agregar filas a la tabla
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   
}
