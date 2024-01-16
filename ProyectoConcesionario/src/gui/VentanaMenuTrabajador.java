package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import src.db.DAO;
import src.domain.GeneradorNumeroSerie;
import src.io.FicheroActividad;

public class VentanaMenuTrabajador extends JFrame {
	private static final long serialVersionUID = GeneradorNumeroSerie.generarSiguienteNumeroDeSerie();

	public VentanaMenuTrabajador(DAO dao) {
		setTitle("Trabajador: " + dao.trabajador.getLogin());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(980, 700);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FicheroActividad.guardarActividad(
						dao.trabajador.getLogin() + ": usuario " + dao.trabajador.getLogin() + " cerró sesión");
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3, 10, 10));

		JButton inventarioButton = new JButton("Inventario");
		estilizarBoton(inventarioButton, "/resources/images/warehouse_AdobeStock_294439367.jpeg", "Inventario");
		inventarioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaInventario(dao);
			}
		});
		panel.add(inventarioButton);

		JButton clientesButton = new JButton("Clientes");
		estilizarBoton(clientesButton,  "/resources/images/cliente-e1551799486636.jpg", "Clientes");
		clientesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaVerClientes();
			}
		});
		panel.add(clientesButton);
		JButton anadirVentaButton = new JButton("Añadir venta");
		estilizarBoton(anadirVentaButton, "/resources/images/cash-Ennio-Leanza.jpg", "Añadir Venta");
		anadirVentaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAñadirVenta(dao);

			}

		});
		panel.add(anadirVentaButton);

		JButton citasButton = new JButton("Citas");
		estilizarBoton(citasButton,  "/resources/images/reunion.jpg", "Citas");
		citasButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaVerCitas(dao);
			}
		});
		panel.add(citasButton);

		JButton informeButton = new JButton("Informe");
		estilizarBoton(informeButton,  "/resources/images/informe.jpg", "Informe");
		informeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaInforme();
			}
		});
		panel.add(informeButton);

		JButton miPerfilButton = new JButton("Mi perfil");
		estilizarBoton(miPerfilButton,  "/resources/images/1366_2000.jpg", "Mi perfil");
		miPerfilButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaMiPerfil(dao, "trabajador");

			}
		});
		panel.add(miPerfilButton);

		JButton administracionButton = new JButton("Administración");
		estilizarBoton(administracionButton,  "/resources/images/mejores-portátiles-para-profesores.jpeg",
				"Administración");
		administracionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAdministracion(dao);

			}
		});
		panel.add(administracionButton);
		administracionButton.setVisible(false);
		System.out.print(dao.trabajador.esAdmin());
		if (dao.trabajador.esAdmin()) {
			administracionButton.setVisible(true);
		}

		JButton cerrarSesionButton = new JButton("Cerrar Sesion");
		estilizarBoton(cerrarSesionButton,  "/resources/images/El-arte-de-perderse.jpg", "Cerrar Sesión");
		cerrarSesionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaInicio(dao);
				FicheroActividad.guardarActividad(dao.trabajador.getLogin()+": usuario "+dao.trabajador.getLogin()+" cerró sesión");
				dao.trabajador.setLogin("");
				dao.esAdmin = false;
				dispose();

			}
		});
		panel.add(cerrarSesionButton);

		JPanel wrapperPanel = new JPanel(new BorderLayout());
		wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Borde

		wrapperPanel.add(panel, BorderLayout.CENTER);
		add(wrapperPanel);
		setVisible(true);
	}

	private void estilizarBoton(JButton button, String imagePath, String buttonName) {					//Esta función ha sido creada con ayuda de IAG, ya que no tenía
		button.setPreferredSize(new Dimension(250, 80));												//conocimientos sobre como usar una imagen correctamente para un botón
		button.setBackground(Color.decode("#3F51B5"));													//-Jon	
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		button.setOpaque(true);

		ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));

		Image originalImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
		Image blackLayer = createBlackLayer(originalImage);
		ImageIcon newIcon = new ImageIcon(blackLayer);

		JLabel imageLabel = new JLabel(newIcon);
		imageLabel.setLayout(new BorderLayout());
		imageLabel.setPreferredSize(new Dimension(originalIcon.getIconWidth(), originalIcon.getIconHeight()));

		JLabel textLabel = new JLabel(buttonName, SwingConstants.CENTER);
		textLabel.setForeground(Color.WHITE);
		textLabel.setFont(new Font("Arial", Font.BOLD, 14));

		imageLabel.add(textLabel, BorderLayout.CENTER);
		button.add(imageLabel);

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.decode("#6573C3"));
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.decode("#3F51B5"));
			}
		});

	}

	private Image createBlackLayer(Image originalImage) {

		BufferedImage bufferedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, null);

		Color transparentBlack = new Color(0, 0, 0, 128);
		g.setColor(transparentBlack);
		g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

		g.dispose();

		return bufferedImage;
	}

}
