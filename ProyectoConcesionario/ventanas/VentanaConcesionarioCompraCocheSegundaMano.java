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
import clases.CocheSegundaMano;


public class VentanaConcesionarioCompraCocheSegundaMano extends JFrame {
    private JTextField idField, marcaField, combustibleField, modeloField, colorField, tipoField, potenciaField, numPlazasField, precioField, cuotaField, matriculacionField, kilometrajeField;
    private JButton agregarButton;

    public void agregarCocheSegundaManoForm(Cliente cliente) {
    	DAO dao = new DAO();
    	try {
			dao.conectar();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "No se ha podido conectar con la base de datos");
			e.printStackTrace();
		}
        setTitle("Agregar Coche");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));

        panel.add(new JLabel("ID Vehículo:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        panel.add(marcaField);


        panel.add(new JLabel("Combustible:"));
        combustibleField = new JTextField();
        panel.add( combustibleField);

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

        panel.add(new JLabel("Kilometraje"));
        kilometrajeField = new JTextField();
        panel.add(kilometrajeField);


        agregarButton = new JButton("Agregar");







        int idVehiculo = Integer.parseInt(idField.getText());
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        String color = colorField.getText();
        String tipo = tipoField.getText();
        int potencia = Integer.parseInt(potenciaField.getText());
        int numPlazas = Integer.parseInt(numPlazasField.getText());
        int precio = Integer.parseInt(precioField.getText());
        int cuota = Integer.parseInt(cuotaField.getText());
        String matriculacionStr = matriculacionField.getText();
        int kilometraje = Integer.parseInt(kilometrajeField.getText());
        String combustible = combustibleField.getText();

        // Convertir la cadena a tipo Date utilizando SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date matriculacion= null;
        try {
          matriculacion = dateFormat.parse(matriculacionStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Utiliza el formato YYYY-MM-DD");
            return; // Salir del método si hay un error en el formato de fecha
        }

        // Crear el objeto Coche con los datos ingresados
        CocheSegundaMano cocheSegundaMano = new CocheSegundaMano(idVehiculo,combustible, marca, modelo, color, tipo, potencia, numPlazas, precio, cuota, matriculacion,kilometraje);
    	agregarButton.addActionListener(e -> {
			try {
				dao.conectar();
				dao.agregarCocheSegundaManoCompradoPorConcesionario(cocheSegundaMano, cliente);
				dao.desconectar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Error, intentelo otra vez");
				e1.printStackTrace();
			}
		});
    	 add(panel);
        // Aquí puedes usar el objeto coche según sea necesario (enviar a un DAO, almacenar en una lista, etc.)
        System.out.println("Nuevo coche añadido");
        try {
			dao.desconectar();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(this, "No se ha podido desconectar de la base de datos");
			e1.printStackTrace();
		}
}

}