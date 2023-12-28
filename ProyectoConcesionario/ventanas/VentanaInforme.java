package ventanas;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.awt.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.*;
import com.itextpdf.text.pdf.PdfPTable;



import java.awt.BorderLayout;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaInforme extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private DAO dao = new DAO();
    public VentanaInforme() {
        setTitle("Informe de Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        tableModel.addColumn("Categoría");
        tableModel.addColumn("ID Vehículo");
        tableModel.addColumn("Marca");
        tableModel.addColumn("Modelo");
        tableModel.addColumn("Precio Compra");
        tableModel.addColumn("Precio Venta");
        tableModel.addColumn("Beneficio");

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Fuente de la tabla
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12)); // Fuente de encabezado
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        try {
			cargarDatosDesdeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Método para cargar los datos desde la base de datos

        JButton exportPDFButton = new JButton("Exportar a PDF");
        exportPDFButton.addActionListener(e -> exportarAPDF(table,tableModel));
        add(exportPDFButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarDatosDesdeDB() throws SQLException{
    	 // Establecer la conexión con la base de datos
        Connection conn = DriverManager.getConnection(dao.url);
        Statement stmt = conn.createStatement();

        // Ejecutar la consulta SQL
        ResultSet rs = stmt.executeQuery("SELECT * FROM ventas");

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Agregar filas de la base de datos a la tabla
        while (rs.next()) {
            Object[] rowData = {
                    rs.getString("categoria"),
                    rs.getInt("idVehiculo"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getDouble("precioCompra"),
                    rs.getDouble("precioVenta"),
                    rs.getDouble("beneficio")
            };
            tableModel.addRow(rowData);
        }

        // Calcular la suma de todas las ventas
        double totalCompra = 0.0;
        double totalVenta = 0.0;
        double totalBeneficio = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalCompra += (double) tableModel.getValueAt(i, 4);
            totalVenta += (double) tableModel.getValueAt(i, 5);
            totalBeneficio += (double) tableModel.getValueAt(i, 6);
        }
        Object[] sumaTotales = {"Total", "", "", "", totalCompra, totalVenta, totalBeneficio};
        tableModel.addRow(sumaTotales);

        // Cerrar la conexión y los recursos
        try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }



    private void exportarAPDF(JTable table, DefaultTableModel tableModel) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("InformeConcesionario.pdf"));
            document.open();

            // Agregar título
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Informe de Ventas", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Agregar fecha
            com.itextpdf.text.Font dateFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC);
            Paragraph date = new Paragraph("Fecha: " + new java.util.Date(), dateFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            // Agregar tabla
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Encabezados de columna
            for (int i = 0; i < table.getColumnCount(); i++) {
                PdfPCell header = new PdfPCell(new Phrase(table.getColumnName(i)));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(header);
            }

            // Datos de la tabla
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    PdfPCell cell = new PdfPCell(new Phrase(table.getValueAt(i, j).toString()));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cell);
                }
            }

            document.add(pdfTable);
            document.close();
            JOptionPane.showMessageDialog(null, "Tabla exportada a PDF correctamente.");
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }


}