package ventanas;

import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class VentanaInicio extends JFrame {
	
	private JTextField textoLogin;
    private JPasswordField textoPassword;
    private JButton botonVerContraseña, botonAceptar, botonCancelar, botonRegistro;
 
  
    private Image imagenFondo;
    private JPanel panelGeneral;
  

    public VentanaInicio(DAO dao2) {
        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        setVisible(false);
        imagenFondo = new ImageIcon("/Users/jonmendizabal/Downloads/Bilbao_-_Universidad_de_Deusto_01.png").getImage();
        
        panelGeneral = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
       
     
       

        panelGeneral.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelLogin = new JLabel("Login:");
        JLabel labelPassword = new JLabel("Contraseña:");
        labelLogin.setForeground(Color.WHITE);
        labelPassword.setForeground(Color.WHITE);

        textoLogin = new JTextField(15);
        textoPassword = new JPasswordField(15);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelGeneral.add(labelLogin, gbc);	//0,1

        gbc.gridx = 2;
        panelGeneral.add(textoLogin, gbc);	//0,2

        gbc.gridy = 1;
        gbc.gridx = 1;
        panelGeneral.add(labelPassword, gbc);	//1,1

        gbc.gridx = 2;
        panelGeneral.add(textoPassword, gbc);	//1,2

       
        botonAceptar = new JButton("Aceptar");	
 
       
       
        botonRegistro = new JButton("Registrarme");
        
        botonCancelar = new JButton("Cancelar");
     
        botonVerContraseña = new JButton("Ver Contraseña");
        gbc.gridy=2;
        gbc.gridx = 1;
        panelGeneral.add(botonAceptar, gbc); // Aceptar: 1, 2

        gbc.gridx = 2;
        panelGeneral.add(botonCancelar, gbc); // Cancelar: 2, 2

        gbc.gridx = 3;
        panelGeneral.add(botonVerContraseña, gbc); // Ver Contraseña: 3, 2

        
       
   
       
        GridBagConstraints gbcBotonRegistro = new GridBagConstraints();
        gbcBotonRegistro.anchor = GridBagConstraints.PAGE_END;
        gbcBotonRegistro.insets = new Insets(5, 5, 5, 5);
        gbcBotonRegistro.gridwidth = 4;
        gbcBotonRegistro.gridx = 1;
        gbcBotonRegistro.gridy = 10; // Posición vertical inferior
        panelGeneral.add(botonRegistro, gbcBotonRegistro); // Botón "Registrarme"

        // Crear restricciones para el label "¿No tienes cuenta aún? Regístrate ahora!"
        GridBagConstraints gbcLabelRegistro = new GridBagConstraints();
        gbcLabelRegistro.anchor = GridBagConstraints.PAGE_END;
        JLabel labelRegistro = new JLabel("¿No tienes cuenta aún? Regístrate ahora!");
        labelRegistro.setForeground(Color.WHITE);
        gbcLabelRegistro.gridwidth = 4;
        gbcLabelRegistro.gridx = 1;
        gbcLabelRegistro.gridy = 9; // Posición vertical superior
        panelGeneral.add(labelRegistro, gbcLabelRegistro); // Label "¿No tienes cuenta aún? Regístrate ahora!"

    	  
    	 
        
        setContentPane(panelGeneral);

        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticacion(dao2);
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        botonRegistro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new VentanaRegistroCliente();
				
			}
        	
        });

        botonVerContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textoPassword.getEchoChar() == 0) {
                    textoPassword.setEchoChar('*');
                } else {
                    textoPassword.setEchoChar((char) 0);
                }
            }
        });
       
       
		
       
       
        setSize(980, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
        private void autenticacion(DAO dao2) {
            String usuarioIngresado = textoLogin.getText();
            String contrasenaIngresada = new String(textoPassword.getPassword());
            boolean login = false;
                try {
					if (dao2.comprobarCredencialesTrabajador(usuarioIngresado, contrasenaIngresada)) {
						VentanaMenuTrabajador ventanaMenuTrabajador = new VentanaMenuTrabajador(dao2);
						 dispose();
						 login = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
           
            	try {
					if (dao2.comprobarCredencialesCliente(usuarioIngresado, contrasenaIngresada)) {
						VentanaMenuCliente ventanaMenuCliente = new VentanaMenuCliente(dao2);
						dispose();
						login=true;
					}else {
						if(!login) {
							JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
						}
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Usuario inexistente");
					e.printStackTrace();
				}
            }

           
        }

        
        class TransparentPanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.7f)); // 0.7f representa la opacidad (0.0f = totalmente transparente, 1.0f = totalmente opaco)
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        }
       




