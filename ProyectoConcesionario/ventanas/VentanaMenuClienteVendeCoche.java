package ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import clases.Cliente;
import clases.CocheSegundaMano;

public class VentanaMenuClienteVendeCoche extends JFrame {
	DAO dao = new DAO();
	 protected JTextField idField, dniField, combustibleField, marcaField, modeloField, colorField, tipoField, potenciaField, numPlazasField, precioField, kilometrajeField, matriculacionField;
	    protected JButton enviarButton;

    public VentanaMenuClienteVendeCoche() {
        setTitle("Datos del Coche");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(13, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        idField = new JTextField();
        dniField = new JTextField();
        combustibleField = new JTextField();
        marcaField = new JTextField();
        modeloField = new JTextField();
        colorField = new JTextField();
        tipoField = new JTextField();
        potenciaField = new JTextField();
        numPlazasField = new JTextField();
        precioField = new JTextField();
        kilometrajeField = new JTextField();
        matriculacionField = new JTextField();

        panel.add(new JLabel("ID Vehículo:"));
        panel.add(idField);
        panel.add(new JLabel("DNI del Cliente:"));
        panel.add(dniField);
        panel.add(new JLabel("Combustible:"));
        panel.add(combustibleField);
        panel.add(new JLabel("Marca:"));
        panel.add(marcaField);
        panel.add(new JLabel("Modelo:"));
        panel.add(modeloField);
        panel.add(new JLabel("Color:"));
        panel.add(colorField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoField);
        panel.add(new JLabel("Potencia:"));
        panel.add(potenciaField);
        panel.add(new JLabel("Número de Plazas:"));
        panel.add(numPlazasField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);
        panel.add(new JLabel("Kilometraje:"));
        panel.add(kilometrajeField);
        panel.add(new JLabel("Fecha de Matriculación (dd/MM/yyyy):"));
        panel.add(matriculacionField);

        enviarButton = new JButton("Enviar");
        panel.add(new JLabel());
        panel.add(enviarButton);
        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener los valores ingresados por el cliente
                    int idVehiculo = Integer.parseInt(idField.getText());
                    String combustible = combustibleField.getText();
                    String marca = marcaField.getText();
                    String modelo = modeloField.getText();
                    String color = colorField.getText();
                    String tipo = tipoField.getText();
                    int potencia = Integer.parseInt(potenciaField.getText());
                    int numPlazas = Integer.parseInt(numPlazasField.getText());
                    int precio = Integer.parseInt(precioField.getText());
                    int cuota = precio/60;
                    int kilometraje = Integer.parseInt(kilometrajeField.getText());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date matriculacion = dateFormat.parse(matriculacionField.getText());
                    String dniCliente = dniField.getText();
                    Cliente cliente = dao.obtenerClientePorDNI(dniCliente);
                    // Aquí puedes hacer lo que necesites con los datos del coche ingresados por el cliente
                    JOptionPane.showMessageDialog(null, "Datos del coche recibidos correctamente");
                    CocheSegundaMano cocheSegundaMano = new CocheSegundaMano(idVehiculo,combustible,marca,modelo,color,tipo,potencia,numPlazas,precio,cuota,matriculacion,kilometraje);
                    dao.agregarCocheVendidoPorCliente(cocheSegundaMano, cliente);
                    // Lógica para usar los datos del coche ingresados por el cliente
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Ingresa valores numéricos válidos");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Formato de fecha incorrecto. Utiliza dd/MM/yyyy");
                } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        panel.add(enviarButton);

        add(panel);
        setVisible(true);
    }


}
