package ventanas;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import clases.Cliente;
import clases.MotoSegundaMano;
public class VentanaMenuClienteVendeMoto extends JFrame {
    private DAO dao = new DAO();

    public VentanaMenuClienteVendeMoto() {
    	  setTitle("Datos de la Moto");
          setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          setSize(400, 650);
          setLocationRelativeTo(null);

          JPanel panel = new JPanel();
          panel.setLayout(new GridBagLayout());

          GridBagConstraints gbc = new GridBagConstraints();
          gbc.anchor = GridBagConstraints.WEST;
          gbc.gridx = 0;
          gbc.gridy = 0;
          gbc.insets.bottom = 5;
          gbc.insets.top = 5;
          gbc.insets.left = 10;
          gbc.fill = GridBagConstraints.HORIZONTAL;

          panel.add(new JLabel("ID Vehículo:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Combustible:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Marca:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Modelo:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Color:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Tipo:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Potencia:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Número de Plazas:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Introduzca su DNI:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Precio:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Kilometraje:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Cuota:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Fecha de Matriculación (dd/MM/yyyy):"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("Peso:"), gbc);
          gbc.gridy++;
          panel.add(new JLabel("¿Tiene baúl? (true/false):"), gbc);

          gbc.gridx = 1;
          gbc.gridy = 0;
          gbc.weightx = 1.0;
          gbc.insets.left = 0;
          gbc.fill = GridBagConstraints.HORIZONTAL;

          JTextField idField = new JTextField();
          panel.add(idField, gbc);
          gbc.gridy++;
          JTextField combustibleField = new JTextField();
          panel.add(combustibleField, gbc);
          gbc.gridy++;
          JTextField marcaField = new JTextField();
          panel.add(marcaField, gbc);
          gbc.gridy++;
          JTextField modeloField = new JTextField();
          panel.add(modeloField, gbc);
          gbc.gridy++;
          JTextField colorField = new JTextField();
          panel.add(colorField, gbc);
          gbc.gridy++;
          JTextField tipoField = new JTextField();
          panel.add(tipoField, gbc);
          gbc.gridy++;
          JTextField potenciaField = new JTextField();
          panel.add(potenciaField, gbc);
          gbc.gridy++;
          JTextField numPlazasField = new JTextField();
          panel.add(numPlazasField, gbc);
          gbc.gridy++;
          JTextField dniField = new JTextField();
          panel.add(dniField, gbc);
          gbc.gridy++;
          JTextField precioField = new JTextField();
          panel.add(precioField, gbc);
          gbc.gridy++;
          JTextField kilometrajeField = new JTextField();
          panel.add(kilometrajeField, gbc);
          gbc.gridy++;
          JTextField cuotaField = new JTextField();
          panel.add(cuotaField, gbc);
          gbc.gridy++;
          JTextField matriculacionField = new JTextField();
          panel.add(matriculacionField, gbc);
          gbc.gridy++;
          JTextField pesoField = new JTextField();
          panel.add(pesoField, gbc);
          gbc.gridy++;
          JTextField baulField = new JTextField();
          panel.add(baulField, gbc);

          JButton enviarButton = new JButton("Enviar");
          gbc.gridy++;
          gbc.gridx = 0;
          gbc.gridwidth = 2;
          panel.add(enviarButton, gbc);

          enviarButton.addActionListener(e -> {
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
                  MotoSegundaMano moto = new MotoSegundaMano(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion, kilometraje, baul, peso,null,dao.cliente.getLogin());

                  dao.agregarMotoVendidaPorCliente(moto, cliente);
                  JOptionPane.showMessageDialog(null, "Datos de la moto recibidos correctamente");

              } catch (NumberFormatException ex) {
                  JOptionPane.showMessageDialog(null, "Error: Ingresa valores numéricos válidos");
              } catch (ParseException ex) {
                  JOptionPane.showMessageDialog(null, "Error: Formato de fecha incorrecto. Utiliza dd/MM/yyyy");
              } catch (SQLException e1) {
                  e1.printStackTrace();
              }
          });

          add(panel);
          setVisible(true);
      }
}
