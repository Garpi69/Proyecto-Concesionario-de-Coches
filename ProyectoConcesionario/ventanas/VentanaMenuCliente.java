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

public class VentanaMenuCliente extends JFrame {
	 public VentanaMenuCliente(DAO dao) {
	        setTitle("Compra o Venta");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(980, 700);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(2, 1, 10, 10)); // Espaciado entre componentes

	        JButton comprarButton = new JButton("Comprar");
	        estilizarBoton(comprarButton,"/Users/jonmendizabal/Downloads/DSC09923-scaled.jpg", "Comprar");
	        comprarButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	VentanaMenuClienteCompraVehiculo ventanaMenuClienteCompraVehiculo = new VentanaMenuClienteCompraVehiculo(dao);
	            	ventanaMenuClienteCompraVehiculo.setVisible(true);
	            }
	        });
	        panel.add(comprarButton);

	        JButton venderButton = new JButton("Vender");
	        estilizarBoton(venderButton,"/Users/jonmendizabal/Downloads/beautiful-modern-cars-at-luxury-dealership-salon-2021-09-04-04-53-23-utc-scaled.jpg","Vender") ;
	        venderButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaMenuClienteVendeVehiculo();
	            }
	        });
	        panel.add(venderButton);

	        JButton verOfertasButton = new JButton("Mis ofertas");
	        estilizarBoton(verOfertasButton,"/Users/jonmendizabal/Downloads/stock-car-2022-np-20220517.jpg", "Mis Ofertas");
	        verOfertasButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new VentanaMenuClienteVerOfertas();
	            }
	        });
	        panel.add(verOfertasButton);
	        
	        JButton miPerfil = new JButton("Mi Perfil");
	        estilizarBoton(miPerfil,"/Users/jonmendizabal/Downloads/1366_2000.jpg" ,"Mi Perfil");
	        miPerfil.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					VentanaMiPerfil ventanaMiPerfil = new VentanaMiPerfil(dao);
					
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



