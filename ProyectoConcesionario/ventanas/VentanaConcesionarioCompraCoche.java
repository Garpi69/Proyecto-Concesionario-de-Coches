package ventanas;

import java.awt.GridLayout;
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
import clases.Coche;





public class VentanaConcesionarioCompraCoche extends JFrame {
    private JTextField idField, combustibleField, marcaField, modeloField, colorField, tipoField, potenciaField, numPlazasField, precioField, cuotaField, matriculacionField, kilometrajeField;
    private JButton agregarButton;

    public void agregarCocheForm(Cliente cliente) {
        DAO dao = new DAO();
        try {
			dao.conectar();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "No se ha podido conectar con la base de datos");
			e.printStackTrace();
		}
        setTitle("Agregar Coche");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(13, 2));

        panel.add(new JLabel("ID Vehículo:"));
        idField = new JTextField();
        panel.add(idField);

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

        panel.add(new JLabel("Num Plazas:"));
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

        panel.add(new JLabel("Kilometraje:"));
        kilometrajeField = new JTextField();
        panel.add(kilometrajeField);

        agregarButton = new JButton("Agregar");
        panel.add(agregarButton);
        add(panel);
        setVisible(true);
        agregarButton.addActionListener(e -> {
            Coche coche = obtenerDatosCoche();
            if (coche != null) {
                try {
                    dao.agregarCocheCompradoPorConcesionario(coche);
                    JOptionPane.showMessageDialog(this, "Coche agregado exitosamente");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al agregar el coche a la base de datos");
                    ex.printStackTrace();
                }
            }
        });



    }

    private Coche obtenerDatosCoche() {
        int idVehiculo, potencia, numPlazas, precio, cuota, kilometraje;

        try {
            idVehiculo = Integer.parseInt(idField.getText());
            potencia = Integer.parseInt(potenciaField.getText());
            numPlazas = Integer.parseInt(numPlazasField.getText());
            precio = Integer.parseInt(precioField.getText());
            cuota = Integer.parseInt(cuotaField.getText());
            kilometraje = Integer.parseInt(kilometrajeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos");
            return null;
        }

        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String color = colorField.getText();
        String tipo = tipoField.getText();
        String combustible = combustibleField.getText();
        String matriculacionStr = matriculacionField.getText();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date matriculacion;
        try {
            matriculacion = dateFormat.parse(matriculacionStr);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Utiliza el formato YYYY-MM-DD");
            return null;
        }

        return new Coche(idVehiculo, combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion);
    }

}
