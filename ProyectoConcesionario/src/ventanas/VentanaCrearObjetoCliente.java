package ventanas;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import clases.Cliente;
public class VentanaCrearObjetoCliente extends JFrame {
    private JTextField loginField;
    private JPasswordField contraField;
    private JTextField emailField;
    private JTextField dniField;
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField fechaNacimientoField;
    private JTextField numTarjetaField;

    public VentanaCrearObjetoCliente() {
        setTitle("Datos del Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        panel.add(new JLabel("Login:"));
        loginField = new JTextField();
        panel.add(loginField);

        panel.add(new JLabel("Contraseña:"));
        contraField = new JPasswordField();
        panel.add(contraField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        panel.add(dniField);

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Apellidos:"));
        apellidosField = new JTextField();
        panel.add(apellidosField);

        panel.add(new JLabel("Fecha Nacimiento (dd/mm/yyyy):"));
        fechaNacimientoField = new JTextField();
        panel.add(fechaNacimientoField);

        panel.add(new JLabel("Número de Tarjeta:"));
        numTarjetaField = new JTextField();
        panel.add(numTarjetaField);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente=guardarCliente();
            }
        });
        panel.add(guardarButton);

        add(panel);
    }

    public Cliente guardarCliente() {
        String login = loginField.getText();
        String contra = new String(contraField.getPassword());
        String email = emailField.getText();
        String dni = dniField.getText();
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        String fechaNacimientoStr = fechaNacimientoField.getText();
        long numTarjeta = Long.parseLong(numTarjetaField.getText());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNacimiento = null;
		try {
			fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
        Cliente cliente = new Cliente(login, contra, email, dni, nombre, apellidos,fechaNacimiento ,numTarjeta);
		return cliente;

       
    }

   

    
   
}

