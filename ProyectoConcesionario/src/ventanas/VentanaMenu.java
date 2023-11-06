package ventanas;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
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
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new GridLayout(4,2));
		
		botonInventario = new JButton("Inventario");
		botonInforme = new JButton("Informe");
		botonCalendario = new JButton("Calendario");
		botonComprarVehiculo = new JButton("Comprar Vehiculo");
		botonVenderVehiculo = new JButton("Vender Vehiculo");
		botonClientes = new JButton("Clientes");
		
		panelMenu.add(botonInventario);
		panelMenu.add(botonInforme);
		panelMenu.add(botonCalendario);
		panelMenu.add(botonComprarVehiculo);
		panelMenu.add(botonVenderVehiculo);
		panelMenu.add(botonClientes);
		
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
		
		
		
		
		this.add(panelMenu,BorderLayout.SOUTH);
		
		
        this.setVisible(true);
		
		this.setTitle("Menu");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack(); 
		this.setVisible(true); 
		this.setLocationRelativeTo(null);
		
	
	}
}
