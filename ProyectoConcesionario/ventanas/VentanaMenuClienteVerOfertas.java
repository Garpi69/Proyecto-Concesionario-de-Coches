package ventanas;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;


public class VentanaMenuClienteVerOfertas extends JFrame {
	private DAO dao = new DAO();

	public VentanaMenuClienteVerOfertas() {
		
		setTitle("Ofertas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable ofertasTable = new JTable(tableModel);
        String[] columnas= {"Vehiculo","Oferta (€)","Usuario"};
        tableModel.setColumnIdentifiers(columnas);
        JButton enviadasButton = new JButton("Ofertas enviadas");

        JButton recibidasButton = new JButton("Ofertas recibidas");
        
        recibidasButton.addActionListener( e->{
        	try {
        		
        		dao.cargarOfertasRecibidas("coche",tableModel,dao.cliente.getLogin());
        		dao.cargarOfertasRecibidas("cocheSegundaMano",tableModel,dao.cliente.getLogin());
        		dao.cargarOfertasRecibidas("moto",tableModel,dao.cliente.getLogin());
        		dao.cargarOfertasRecibidas("motoSegundaMano",tableModel,dao.cliente.getLogin());
        	}catch (AWTException e1) {
        		
        	}
        	
        });
        enviadasButton.addActionListener(e->{
        
        	dao.cargarOfertasEnviadas("coche",tableModel,dao.cliente.getLogin());
        	dao.cargarOfertasEnviadas("cocheSegundaMano", tableModel,dao.cliente.getLogin());
        	dao.cargarOfertasEnviadas("moto", tableModel,dao.cliente.getLogin());
        	dao.cargarOfertasEnviadas("motoSegundaMano", tableModel,dao.cliente.getLogin());
        });
        JScrollPane scrollPane = new JScrollPane(ofertasTable);
       
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5); // Espacios entre componentes
        
        gbc2.gridx = 7;
        buttonPanel.add(enviadasButton, gbc2);

        gbc2.gridx = 8;
        buttonPanel.add(recibidasButton, gbc2);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH );
        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la fila seleccionada en la tabla
                int selectedRow = ofertasTable.getSelectedRow();
                if (selectedRow != -1) {
                	Object cocheAceptado = ofertasTable.getValueAt(selectedRow,0);
                	Object precioAceptado = ofertasTable.getValueAt(selectedRow, 1);
                	String precioAceptadoString = precioAceptado.toString()
;                	int precioAceptadoInt = Integer.parseInt(precioAceptadoString);
                	Object usuarioAceptado = ofertasTable.getValueAt(selectedRow, 2);
                	String cocheAceptadoString = cocheAceptado.toString();
                	String usuarioAceptadoString = usuarioAceptado.toString();
                	System.out.print(cocheAceptadoString);
                	dao.eliminarVehiculo(cocheAceptadoString,"coche");
					dao.eliminarVehiculo(cocheAceptadoString,"cocheSegundaMano");
					dao.eliminarVehiculo(cocheAceptadoString,"moto");
					dao.eliminarVehiculo(cocheAceptadoString,"motoSegundaMano");
                	
                	JOptionPane.showMessageDialog(null, "Felicidades, "+ofertasTable.getValueAt(selectedRow, 1)+"€ serán ingresados en su cuenta");
                	dao.eliminarOferta(cocheAceptadoString,usuarioAceptadoString,precioAceptadoInt);
                	dao.cargarOfertasEnviadas("coche",tableModel,dao.cliente.getLogin());
                	dao.cargarOfertasEnviadas("cocheSegundaMano", tableModel,dao.cliente.getLogin());
                	dao.cargarOfertasEnviadas("moto", tableModel,dao.cliente.getLogin());
                	dao.cargarOfertasEnviadas("motoSegundaMano", tableModel,dao.cliente.getLogin());
                	ofertasTable.repaint();
                    // Aquí puedes implementar la lógica para aceptar la oferta seleccionada
                    // Usando dao.cliente.getLogin() para obtener el usuario actual
                    //dao.aceptarOferta(ofertasTable.getValueAt(selectedRow, 0), dao.cliente.getLogin());
                }
            }
        });

        JButton rechazarButton = new JButton("Rechazar");
        rechazarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la fila seleccionada en la tabla
                int selectedRow = ofertasTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Aquí puedes implementar la lógica para rechazar la oferta seleccionada
                    // Usando dao.cliente.getLogin() para obtener el usuario actual
                    //dao.rechazarOferta(ofertasTable.getValueAt(selectedRow, 0), dao.cliente.getLogin());
                }
            }
        });

        gbc2.insets = new Insets(5, 5, 5, 5);

        gbc2.gridx = 9;
        buttonPanel.add(aceptarButton, gbc2);

        gbc2.gridx = 10;
        buttonPanel.add(rechazarButton, gbc2);
       
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
	}

}
