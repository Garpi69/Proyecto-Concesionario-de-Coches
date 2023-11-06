package ventanas;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaMenu extends JFrame{
	
	protected JButton botonInventario ;
	protected JButton botonInforme ;
	protected JButton botonCalendario ;
	protected JButton botonComprarVehiculo ;
	protected JButton botonVenderVehiculo ;
	protected JButton botonClientes ;
	
	public VentanaMenu(){
		
		 JPanel contentPanel = new JPanel();
	        contentPanel.setLayout(new BorderLayout());

	      
	        JPanel titlePanel = new JPanel();
	        titlePanel.setLayout(new FlowLayout());

	       
	        JLabel TituloLabel = new JLabel("Concesionario");
	        TituloLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
	        titlePanel.add(TituloLabel);

	     
	        contentPanel.add(titlePanel, BorderLayout.NORTH);

	     
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new FlowLayout());
		
		botonInventario = new JButton("Inventario");
		botonInforme = new JButton("Informe");
		botonCalendario = new JButton("Calendario");
		botonComprarVehiculo = new JButton("Comprar Vehiculo");
		botonVenderVehiculo = new JButton("Vender Vehiculo");
		botonClientes = new JButton("Clientes");
		
		buttonPanel.add(botonInventario);
		buttonPanel.add(botonInforme);
		buttonPanel.add(botonCalendario);
		buttonPanel.add(botonComprarVehiculo);
		buttonPanel.add(botonVenderVehiculo);
		buttonPanel.add(botonClientes);
		
		contentPanel.add(buttonPanel,BorderLayout.CENTER);
		add(contentPanel);
		botonInventario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			
		}
	});
		botonInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		botonCalendario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CalendarVentana();
				dispose();
				
			}
		});botonComprarVehiculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		botonVenderVehiculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			
			}
		});
		botonClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
	
        this.setVisible(true);
		
		this.setTitle("Menu");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.setVisible(true); 
		this.setLocationRelativeTo(null);
		
	
	}
}
