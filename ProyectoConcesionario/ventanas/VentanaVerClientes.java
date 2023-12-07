package ventanas;
import java.sql.Connection;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clases.Cliente;

public class VentanaVerClientes extends JFrame {

	private JTable inventarioTable;
    private DefaultTableModel tableModel;
    private DAO dao = new DAO();
    public VentanaVerClientes() {
        setTitle("Inventario de Vehículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());
        JButton agregarClienteButton = new JButton("Agregar Cliente");
        agregarClienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	VentanaAñadirCliente ventana = new VentanaAñadirCliente();
            	Cliente cliente = ventana.VentanaAñadirCliente();
            	try {
					dao.agregarCliente(cliente);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        JButton eliminarClienteButton = new JButton("Eliminar Cliente");
        eliminarClienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	int numero= inventarioTable.getSelectedRow();
            	Object login = inventarioTable.getValueAt(numero, 1);
            	String loginString = login.toString();
            	System.out.println(login);
            	
            	try {
            		
					dao.eliminarCliente(loginString);
					inventarioTable.repaint();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	
            }
        });
        

        
        
        tableModel = new DefaultTableModel();
        inventarioTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventarioTable);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(agregarClienteButton);
        buttonPanel.add(eliminarClienteButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane);
        cargarClientes(); // Método para cargar la lista de clientes al iniciar la ventana
        setVisible(true);
    }

   public void cargarClientes() {
        // Asegúrate de tener la conexión con tu base de datos
        try {
        	dao.conectar();
        	Connection connection = dao.conn;
       
            String[] columnas = {"Login", "Contraseña", "Email","DNI","Nombre", "Apellido","Fecha de nacimiento","Numero de tarjeta"};
            tableModel.setColumnIdentifiers(columnas);

            // Obtener datos de coches
            cargarDatosClientes(connection, "cliente");

           
           
            connection.close();
            dao.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage());
        }
    }

    private void cargarDatosClientes(Connection connection, String tabla) throws SQLException {
        
    	String sql = "SELECT * FROM " + tabla;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String login = resultSet.getString("login");
            String contrasena = resultSet.getString("contra");
            String email = resultSet.getString("email");
            String dni = resultSet.getString("dni");
            String nombre = resultSet.getString("nombre");
            String apellido = resultSet.getString("apellidos");
          
            
            
            Date fechaNacimiento=null;
			try {
				String fechaNacimientoString = resultSet.getString("fechaNacimiento");
				fechaNacimiento = dao.stringToDate(fechaNacimientoString,dao.format);
			} catch (ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int numTarjeta = resultSet.getInt("numTarjeta");
            Object[] fila = {login,contrasena,  email, dni,nombre,apellido, fechaNacimiento,numTarjeta};
            tableModel.addRow(fila);
        }

        resultSet.close();
        statement.close();
    }

    public static void main(String[] args) {
    	new VentanaVerClientes();
    }
}

