package ventanas;
import clases.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class VentanaMiPerfil extends JFrame{
	 private JLabel loginLabel;
	    private JLabel emailLabel, contraLabel, dniLabel, nombreLabel,apellidoLabel,fechaNacimientoLabel,numTarjetaLabel;
	    private JButton guardarButton, editarButton;
	    private JTextField loginField, emailField, contraField, dniField, nombreField, apellidoField, fechaNacimientoField, numTarjetaField;
	    private DAO dao = new DAO();
	    // Otros JLabel para mostrar los datos del cliente
	    public VentanaMiPerfil(DAO dao2) {
	        setTitle("Mi Perfil");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(500,600);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	        loginField = new JTextField(20);
	        emailField = new JTextField(20);
	        contraField = new JTextField(20);
	        dniField = new JTextField(20);
	        nombreField = new JTextField(20);
	        apellidoField = new JTextField(20);
	        fechaNacimientoField = new JTextField(20);
	        numTarjetaField = new JTextField(20);
	        // Otros JTextField para los datos editables del cliente
	        editarButton = new JButton("Editar");
	        editarButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					hacerEditable(panel);
				}
	        	
	        });
	        guardarButton = new JButton("Guardar");
	        guardarButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                guardarCambios(dao2);
	                hacerNoEditable(panel);
	                dispose();
	               
	            }
	        });

	        panel.add(loginLabel = new JLabel("Login:"));
	       
	        panel.add(emailLabel = new JLabel("Email:"));
	       
	        panel.add(contraLabel = new JLabel("Contraseña:"));
	       
	        panel.add(dniLabel = new JLabel("DNI:"));
	       
	        panel.add(nombreLabel = new JLabel("Nombre:"));
	       
	        panel.add(apellidoLabel = new JLabel("Apellido:"));
	   
	        panel.add(numTarjetaLabel = new JLabel("Número de tarjeta:"));
	     
	        panel.add(fechaNacimientoLabel = new JLabel("Fecha de nacimiento:"));
	        panel.add(loginLabel);
	        panel.add(loginField);
	        panel.add(emailLabel);
	        panel.add(emailField);
	        panel.add(contraLabel);
	        panel.add(contraField);
	        panel.add(dniLabel);
	        panel.add(dniField);
	        panel.add(nombreLabel);
	        panel.add(nombreField);
	        panel.add(apellidoLabel);
	        panel.add(apellidoField);
	        panel.add(fechaNacimientoLabel);
	        panel.add(fechaNacimientoField);
	        panel.add(numTarjetaLabel);
	        panel.add(numTarjetaField);
	        panel.add(guardarButton);
	   
	        // Agregar otros JLabel y JTextField para los datos editables del cliente
	        panel.add(guardarButton);
	        panel.add(editarButton);
	        hacerNoEditable(panel);
	        add(panel);
	        setVisible(true);

	        cargarDatosCliente(dao2.cliente.getLogin());
	      
	    }


	 private void cargarDatosCliente(String login) {
		 System.out.print(login);
	        // Realizar la conexión a la base de datos y obtener los datos del cliente
	        try {
	            Connection conn = DriverManager.getConnection(dao.url);
	            String query = "SELECT * FROM cliente WHERE login = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setString(1, login);

	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                Cliente cliente = new Cliente(
	                        resultSet.getString("login"),
	                        resultSet.getString("contra"),
	                        resultSet.getString("email"),
	                        resultSet.getString("dni"),
	                        resultSet.getString("nombre"),
	                        resultSet.getString("apellidos"),
	                        dao.stringToDate(resultSet.getString("fechaNacimiento"),dao.format),
	                        Long.parseLong(resultSet.getString("numTarjeta")),
	                        resultSet.getString("ofertasEnviadas")
	                );
	                
	                // Mostrar los datos del cliente en los JLabel correspondientes
	                loginField.setText(cliente.getLogin());
	                emailField.setText(cliente.getEmail());
	                dniField.setText(cliente.getdNI());
	                contraField.setText(cliente.getContra());
	                nombreField.setText(cliente.getNombre());
	                apellidoField.setText(cliente.getApellidos());
	                fechaNacimientoField.setText(dao.dateToString(cliente.getFechaNacimiento(),dao.format));
	                numTarjetaField.setText(""+cliente.getNumTarjeta());
	                // Actualizar otros JLabel con los datos del cliente

	                // Cerrar la conexión y liberar recursos
	                resultSet.close();
	                statement.close();
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 private void guardarCambios(DAO dao2) {
	        try {
	            Connection conn = DriverManager.getConnection(dao.url);
	            String query = "UPDATE cliente SET login = ?,contra = ?, email = ?, dni = ?, nombre = ?, apellidos = ?, fechaNacimiento = ?, numTarjeta = ? WHERE login = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setString(1, loginField.getText());
	            System.out.print(loginField.getText());
	            statement.setString(2, contraField.getText());
	            System.out.print(contraField.getText());
	            statement.setString(3, emailField.getText());
	            System.out.print(emailField.getText());
	            statement.setString(4, dniField.getText());
	            System.out.print(dniField.getText());
	            statement.setString(5, nombreField.getText());
	            statement.setString(6, apellidoField.getText());
	            statement.setString(7, fechaNacimientoField.getText());
	            statement.setString(8,numTarjetaField.getText());
	            // Establecer otros campos actualizables en la sentencia SQL
	          
	            statement.setString(9, dao2.cliente.getLogin()); // El login original
	            dao2.cliente.setLogin(loginField.getText());
	            int rowsUpdated = statement.executeUpdate();

	            if (rowsUpdated > 0) {
	                JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente.");
	            }

	            statement.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 private void hacerNoEditable(JPanel panel) {
		 Component[] components = panel.getComponents();
	        for (Component component : components) {
	            if (component instanceof JTextField) {
	                ((JTextField) component).setEditable(false);
	            }
	        }
	 }
	 private void hacerEditable(JPanel panel) {
	        Component[] components = panel.getComponents();
	        for (Component component : components) {
	            if (component instanceof JTextField) {
	                ((JTextField) component).setEditable(true);
	            }
	        }
	    }
}
