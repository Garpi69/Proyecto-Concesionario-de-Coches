package ventanas;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaInicio extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton botonIniciarSesion;
	protected JButton botonRegistrarse;
	
	public VentanaInicio() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Inicio");
		this.setSize(800, 600);
		this.setVisible(true);
		
		JPanel panelBotones = new JPanel();
		
		botonIniciarSesion = new JButton("Iniciar Sesión");
		botonRegistrarse = new JButton("Registrar cuenta");
		
		panelBotones.add(botonIniciarSesion);
		panelBotones.add(botonRegistrarse);
		
		this.add(panelBotones, BorderLayout.CENTER);
	}
	
}
