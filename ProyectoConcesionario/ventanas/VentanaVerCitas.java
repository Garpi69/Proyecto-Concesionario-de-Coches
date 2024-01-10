package ventanas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class VentanaVerCitas extends JFrame {

    private JTable tablaCitas;
    private DefaultTableModel model;

    public VentanaVerCitas(DAO dao) {
        super("Citas");

        model = new DefaultTableModel();
        tablaCitas = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);

       
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(980	, 400);
        setLocationRelativeTo(null);

       
        add(scrollPane);

     
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

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

      

            model.addColumn("Fecha");
            model.addColumn("Usuario");
            model.addColumn("Vehiculo");

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
