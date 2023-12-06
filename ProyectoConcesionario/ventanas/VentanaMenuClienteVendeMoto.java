package ventanas;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import clases.Cliente;
import clases.MotoSegundaMano;
public class VentanaMenuClienteVendeMoto extends JFrame {
	private DAO dao = new DAO();
    public VentanaMenuClienteVendeMoto() {
        setTitle("Datos de la Moto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 2));

        JLabel idLabel = new JLabel("ID Vehículo:");
        JTextField idField = new JTextField();
        panel.add(idLabel);
        panel.add(idField);

        JLabel combustibleLabel = new JLabel("Combustible:");
        JTextField combustibleField = new JTextField();
        panel.add(combustibleLabel);
        panel.add(combustibleField);

        JLabel marcaLabel = new JLabel("Marca:");
        JTextField marcaField = new JTextField();
        panel.add(marcaLabel);
        panel.add(marcaField);

        JLabel modeloLabel = new JLabel("Modelo:");
        JTextField modeloField = new JTextField();
        panel.add(modeloLabel);
        panel.add(modeloField);

        JLabel colorLabel = new JLabel("Color:");
        JTextField colorField = new JTextField();
        panel.add(colorLabel);
        panel.add(colorField);

        JLabel tipoLabel = new JLabel("Tipo:");
        JTextField tipoField = new JTextField();
        panel.add(tipoLabel);
        panel.add(tipoField);

        JLabel potenciaLabel = new JLabel("Potencia:");
        JTextField potenciaField = new JTextField();
        panel.add(potenciaLabel);
        panel.add(potenciaField);

        JLabel numPlazasLabel = new JLabel("Número de Plazas:");
        JTextField numPlazasField = new JTextField();
        panel.add(numPlazasLabel);
        panel.add(numPlazasField);
       
        JLabel dniClienteField= new JLabel("Introduzca su DNI:");
        JTextField dniField = new JTextField();
        panel.add(dniClienteField);
        panel.add(dniField);

        JLabel precioLabel = new JLabel("Precio:");
        JTextField precioField = new JTextField();
        panel.add(precioLabel);
        panel.add(precioField);
        
        JLabel kilometrajeLabel = new JLabel("Kilometraje:");
        JTextField kilometrajeField = new JTextField();
        panel.add(kilometrajeLabel);
        panel.add(kilometrajeField);
        
        JLabel cuotaLabel = new JLabel("Cuota:");
        JTextField cuotaField = new JTextField();
        panel.add(cuotaLabel);
        panel.add(cuotaField);

        JLabel matriculacionLabel = new JLabel("Fecha de Matriculación (dd/MM/yyyy):");
        JTextField matriculacionField = new JTextField();
        panel.add(matriculacionLabel);
        panel.add(matriculacionField);

        JLabel pesoLabel = new JLabel("Peso:");
        JTextField pesoField = new JTextField();
        panel.add(pesoLabel);
        panel.add(pesoField);

        JLabel baulLabel = new JLabel("¿Tiene baúl? (true/false):");
        JTextField baulField = new JTextField();
        panel.add(baulLabel);
        panel.add(baulField);

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
                    int cuota = Integer.parseInt(cuotaField.getText());
                    int kilometraje = Integer.parseInt(kilometrajeField.getText());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date matriculacion = dateFormat.parse(matriculacionField.getText());

                    int peso = Integer.parseInt(pesoField.getText());
                    boolean baul = Boolean.parseBoolean(baulField.getText());
                    String dniCliente = dniField.getText();
                    Cliente cliente = dao.obtenerClientePorDNI(dniCliente);
                    MotoSegundaMano moto = new MotoSegundaMano(idVehiculo,combustible,marca,modelo,color,tipo,potencia,numPlazas,precio,cuota,matriculacion,kilometraje,baul,peso);

                    dao.agregarMotoVendidaPorCliente(moto,cliente);
                    JOptionPane.showMessageDialog(null, "Datos de la moto recibidos correctamente");

                   
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
        setVisible(true);
        add(panel);
    }

   
}