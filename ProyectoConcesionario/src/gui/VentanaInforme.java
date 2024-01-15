package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;
import java.util.Random;
public class VentanaInforme extends JFrame {
	protected static GeneradorNumeroSerie numeroSerie;
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	private DefaultTableModel tableModel;
	private JTable table;
	private DAO dao = new DAO();

	public VentanaInforme() {
		setTitle("Informe de Ventas del Último Mes");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Categoría");
		tableModel.addColumn("ID Vehículo");
		tableModel.addColumn("Marca");
		tableModel.addColumn("Modelo");
		tableModel.addColumn("Precio Compra");
		tableModel.addColumn("Precio Venta");
		tableModel.addColumn("Beneficio");
		tableModel.addColumn("idVenta");

		table = new JTable(tableModel);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		try {
			cargarDatosDesdeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JButton exportPDFButton = new JButton("Exportar a PDF");
		exportPDFButton.addActionListener(e -> exportarAPDF(table, tableModel));
		add(exportPDFButton, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void cargarDatosDesdeDB() throws SQLException {

		dao.conectar();
		String query = "SELECT * FROM ventas";
		PreparedStatement preparedStatement = dao.conn.prepareStatement(query);
		
		ResultSet rs = preparedStatement.executeQuery();

		tableModel.setRowCount(0);
		Random random = new Random();
		while (rs.next()) {
			double numeroAleatorio = 0.3 + (0.8 - 0.3) * random.nextDouble();

			Object[] rowData = { rs.getString("categoria"), rs.getInt("idVehiculo"), rs.getString("marca"),
					rs.getString("modelo"), Math.round(rs.getDouble("precioVenta")*numeroAleatorio), Math.round(rs.getDouble("precioVenta")),
					Math.round(rs.getDouble("precioVenta"))-Math.round(rs.getDouble("precioVenta")*numeroAleatorio), rs.getInt("idVenta") };
			tableModel.addRow(rowData);
		}

		long totalCompra = 0;
		long totalVenta = 0;
		
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			totalCompra += (long) tableModel.getValueAt(i, 4);
			totalVenta += (long) tableModel.getValueAt(i, 5);
		
		}
		long totalBeneficio = totalVenta - totalCompra;
		Object[] sumaTotales = { "Total", "", "", "", totalCompra, totalVenta, Math.round(totalBeneficio) };
		tableModel.addRow(sumaTotales);

		dao.desconectar();

	}

	private void exportarAPDF(JTable table, DefaultTableModel tableModel) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document,
					new FileOutputStream("./resources/data/informs/" + LocalDate.now() + "-informe-ventas.pdf"));
			document.open();

			com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD);
			Paragraph title = new Paragraph("Informe de Ventas", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			com.itextpdf.text.Font dateFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC);
			Paragraph date = new Paragraph("Fecha: " + new java.util.Date(), dateFont);
			date.setAlignment(Element.ALIGN_RIGHT);
			document.add(date);

			PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
			pdfTable.setWidthPercentage(100);

			for (int i = 0; i < table.getColumnCount(); i++) {
				PdfPCell header = new PdfPCell(new Phrase(table.getColumnName(i)));
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				pdfTable.addCell(header);
			}

			for (int i = 0; i < table.getRowCount(); i++) {
				for (int j = 0; j < table.getColumnCount(); j++) {
					if (table.getValueAt(i, j)!=null) {
						PdfPCell cell = new PdfPCell(new Phrase(table.getValueAt(i, j).toString()));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pdfTable.addCell(cell);
					}
					
				
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