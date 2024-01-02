package ventanas;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class VentanaMenuTrabajador extends JFrame {
	 public VentanaMenuTrabajador(DAO dao) {
	        setTitle("Trabajador: "+dao.trabajador.getLogin());
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(980, 700);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(2, 1, 10, 10)); // Espaciado entre componentes

	        JButton inventarioButton = new JButton("Inventario");
	        estilizarBoton( inventarioButton,"/Users/jonmendizabal/Downloads/warehouse_AdobeStock_294439367.jpeg", "Inventario");
	        inventarioButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	new VentanaInventario(dao);
	            }
	        });
	        panel.add( inventarioButton);

	        JButton clientesButton = new JButton("Clientes");
	        estilizarBoton(clientesButton,"/Users/jonmendizabal/Downloads/cliente-e1551799486636.jpg","Clientes") ;
	        clientesButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaVerClientes();
	            }
	        });
	        panel.add(clientesButton);
	        
	        JButton citasButton = new JButton("Citas");
	        estilizarBoton(citasButton,"/Users/jonmendizabal/Downloads/reunion.jpg","Citas") ;
	        citasButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaVerCitas(dao);
	            }
	        });
	        panel.add(citasButton);

	        JButton informeButton = new JButton("Informe");
	        estilizarBoton(informeButton,"/Users/jonmendizabal/Downloads/informe.jpg", "Informe");
	       informeButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaInforme();
	            }
	        });
	        panel.add(informeButton);
	        
	        JButton miPerfil = new JButton("Añadir venta");
	        estilizarBoton(miPerfil,"/Users/jonmendizabal/Downloads/cash-Ennio-Leanza.jpg" ,"Añadir Venta");
	        miPerfil.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new VentanaAñadirVenta(dao);
					
				}
	        	
	        });
	        panel.add(miPerfil);
	       

	        // Espaciado entre panel y ventana
	        JPanel wrapperPanel = new JPanel(new BorderLayout());
	        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Borde

	        wrapperPanel.add(panel, BorderLayout.CENTER);
	        add(wrapperPanel);
	        setVisible(true);
	    }

	 private void estilizarBoton(JButton button, String imagePath,String buttonName) {
		 button.setPreferredSize(new Dimension(250, 80));
		    button.setBackground(Color.decode("#3F51B5"));
		    button.setForeground(Color.WHITE);
		    button.setFont(new Font("Arial", Font.BOLD, 16));
		    button.setFocusPainted(false);
		    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		    button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		    button.setOpaque(true);

		    ImageIcon originalIcon = new ImageIcon(imagePath);
		    Image originalImage = originalIcon.getImage();
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
	        // Crear una BufferedImage para la imagen original
		  BufferedImage bufferedImage = new BufferedImage(
		            originalImage.getWidth(null),
		            originalImage.getHeight(null),
		            BufferedImage.TYPE_INT_ARGB
		    );

		    Graphics2D g = bufferedImage.createGraphics();
		    g.drawImage(originalImage, 0, 0, null);

		    Color transparentBlack = new Color(0, 0, 0, 128); // 128 para una opacidad del 50%
		    g.setColor(transparentBlack);
		    g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

		    g.dispose();

		    return bufferedImage;
	    }
	 
}



