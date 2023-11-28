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

public class VentanaClienteCompraMoto extends JFrame {
    private JTable motosTable;
    private DefaultTableModel model;
    private DAO dao = new DAO();

    public VentanaClienteCompraMoto() {
        setTitle("Inventario de Motos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        model = new DefaultTableModel();
        motosTable = new JTable(model);
        motosTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(motosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton comprarButton = new JButton("Comprar");
        estilizarBoton(comprarButton);
        comprarButton.addActionListener(e -> confirmarCompra());
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
            String query = "SELECT * FROM Moto";

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
        int selectedRow = motosTable.getSelectedRow();
        if (selectedRow != -1) {
            int idVehiculo = (int) motosTable.getValueAt(selectedRow, 0);
            int precio = (int) motosTable.getValueAt(selectedRow, 8);

            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea comprar este vehículo?",
                    "Confirmación de Compra", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Te hemos enviado un correo electrónico para proceder con la compra.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una moto para comprar.");
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#6573C3"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#3F51B5"));
            }
        });
    }

}
