package ventanas;

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
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class VentanaMenuClienteVendeVehiculo extends JFrame {
    public VentanaMenuClienteVendeVehiculo() {
        setTitle("Menú Cliente - Venta de Vehículo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton venderCocheButton = new JButton("Vender Coche");
        estilizarBoton(venderCocheButton,"/Users/jonmendizabal/Downloads/coche-stock-ford-fiesta.jpg", "Vender Coche");
        venderCocheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeCoche();
                
            }
        });
        panel.add(venderCocheButton);

        JButton venderMotoButton = new JButton("Vender Moto");
        estilizarBoton(venderMotoButton,"/Users/jonmendizabal/Downloads/moto-naked-un-modelo-de-moto-de-carretera.jpeg","Vender Moto");
        venderMotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMenuClienteVendeMoto();
               
            }
        });
        panel.add(venderMotoButton);

        setVisible(true);
        add(panel);
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
}
