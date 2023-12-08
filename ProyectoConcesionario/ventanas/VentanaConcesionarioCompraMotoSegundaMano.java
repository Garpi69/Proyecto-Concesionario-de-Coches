package ventanas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import clases.Cliente;
import clases.MotoSegundaMano;

public class VentanaConcesionarioCompraMotoSegundaMano extends JFrame {
    private JTextField idVehiculoField;
    private JTextField marcaField;
    private JTextField modeloField;
    private JTextField colorField;
    private JTextField tipoField;
    private JTextField combustibleField;
    private JTextField potenciaField;
    private JTextField numPlazasField;
    private JTextField precioField;
    private JTextField cuotaField;
    private JTextField matriculacionField;
    private JTextField pesoField;
    private JCheckBox baulCheckBox;
    private JTextField kilometrajeField;
    private JButton agregarButton;
    DAO dao = new DAO();
    public VentanaConcesionarioCompraMotoSegundaMano(Cliente cliente) {
        setTitle("Agregar Moto de Segunda Mano");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(14, 2));

        panel.add(new JLabel("ID del Vehículo:"));
        idVehiculoField = new JTextField();
        panel.add(idVehiculoField);
        panel.add(new JLabel("Combustible:"));
      	combustibleField = new JTextField();
        panel.add(combustibleField);

        panel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        panel.add(marcaField);

        panel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        panel.add(modeloField);

        panel.add(new JLabel("Color:"));
        colorField = new JTextField();
        panel.add(colorField);

        panel.add(new JLabel("Tipo:"));
        tipoField = new JTextField();
        panel.add(tipoField);

        panel.add(new JLabel("Potencia:"));
        potenciaField = new JTextField();
        panel.add(potenciaField);

        panel.add(new JLabel("Número de Plazas:"));
        numPlazasField = new JTextField();
        panel.add(numPlazasField);

        panel.add(new JLabel("Precio:"));
        precioField = new JTextField();
        panel.add(precioField);

        panel.add(new JLabel("Cuota:"));
        cuotaField = new JTextField();
        panel.add(cuotaField);

        panel.add(new JLabel("Matriculación (YYYY-MM-DD):"));
        matriculacionField = new JTextField();
        panel.add(matriculacionField);

        panel.add(new JLabel("Peso:"));
        pesoField = new JTextField();
        panel.add(pesoField);

        panel.add(new JLabel("¿Tiene baúl?"));
        baulCheckBox = new JCheckBox();
        panel.add(baulCheckBox);

        panel.add(new JLabel("Kilometraje:"));
        kilometrajeField = new JTextField();
        panel.add(kilometrajeField);

        agregarButton = new JButton("Agregar Moto de Segunda Mano");
        agregarMotoSegundaMano(cliente);
        panel.add(agregarButton);
        setVisible(true);
        add(panel);
    }

   public void agregarMotoSegundaMano(Cliente cliente) {
        try {
            // Obtener los valores de los campos
            int idVehiculo = Integer.parseInt(idVehiculoField.getText());
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            String color = colorField.getText();
            String tipo = tipoField.getText();
            int potencia = Integer.parseInt(potenciaField.getText());
            int numPlazas = Integer.parseInt(numPlazasField.getText());
            int precio = Integer.parseInt(precioField.getText());
            int cuota = Integer.parseInt(cuotaField.getText());
            Date matriculacion = java.sql.Date.valueOf(matriculacionField.getText());
            int peso = Integer.parseInt(pesoField.getText());
            boolean tieneBaul = baulCheckBox.isSelected();
            int kilometraje = Integer.parseInt(kilometrajeField.getText());
            String combustible = combustibleField.getText();
            MotoSegundaMano motoSegundaMano = new MotoSegundaMano(idVehiculo,combustible,marca,modelo,color,tipo,potencia,numPlazas,precio,cuota,matriculacion,peso,tieneBaul,kilometraje);
            agregarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                	try {

                    	dao.agregarMotoSegundaManoCompradaPorConcesionario(motoSegundaMano, cliente);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
            // Conexión a la base de datos SQLite

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa valores numéricos válidos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



}
