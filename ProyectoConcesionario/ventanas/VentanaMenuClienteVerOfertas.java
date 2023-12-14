package ventanas;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
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
        
       
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
	}

}
