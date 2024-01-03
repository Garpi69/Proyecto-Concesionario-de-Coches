package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAdministracion extends JFrame{
	
	public VentanaAdministracion (DAO dao) {
		
		setTitle("Consola de administrador: "+dao.trabajador.getLogin());
		setSize(980,700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

		DefaultTableModel tableModel = new DefaultTableModel();
	    JTable trabajadoresTable = new JTable(tableModel);
	    String[] columnasGestion= {"Usuario","Contraseña","Email","DNI","Nombre","Apellidos","Fecha de nacimiento","Sueldo","Puesto"};
	    String[] columnasActividad= {"Usuario","Fecha","Actividad"};
	   
		
	    JScrollPane scrollPane = new JScrollPane(trabajadoresTable);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setLayout(new GridBagLayout());
		JPanel panelOpciones = new JPanel();
		panelOpciones.setLayout(new GridBagLayout());
		
		JButton gestionarButton = new JButton("Gestionar trabajadores");
		
		
		JButton actividadButton = new JButton("Ver actividad");
		
		JButton agregarButton = new JButton("Agregar trabajador");
		
		JButton eliminarButton = new JButton("Eliminar trabajador");
	    
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5); // Espacios entre componentes
        
        gbc2.gridx = 7;
        buttonPanel.add(agregarButton, gbc2);
        panelOpciones.add(gestionarButton, gbc2);

        gbc2.gridx = 8;
        buttonPanel.add(eliminarButton, gbc2);
        panelOpciones.add(actividadButton, gbc2);
		
		agregarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				new VentanaAgregarTrabajador(dao);
				tableModel.setRowCount(0);
				dao.cargarDatosTrabajadores(tableModel);
				
			}
			
		});
		
		eliminarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = trabajadoresTable.getSelectedRow();
				String loginTrabajador = ""+trabajadoresTable.getValueAt(selectedRow, 0);
				dao.eliminarTrabajador(loginTrabajador);
				tableModel.setRowCount(0);
				dao.cargarDatosTrabajadores(tableModel);
			}
		
		});
		
		
        
       
        
        
        
        getContentPane().add(buttonPanel, BorderLayout.SOUTH );
        getContentPane().setLayout(new BorderLayout());
        
        getContentPane().add(panelOpciones, BorderLayout.NORTH);
        
		dao.cargarDatosTrabajadores(tableModel);
		repaint();
		getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		gestionarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonPanel.add(agregarButton);
				buttonPanel.add(eliminarButton);
				tableModel.setColumnIdentifiers(columnasGestion);
				tableModel.setRowCount(0);
				dao.cargarDatosTrabajadores(tableModel);
				repaint();
				
				
			}
			
		});
		 actividadButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					buttonPanel.remove(agregarButton);
					buttonPanel.remove(eliminarButton);
					tableModel.setRowCount(0);
					tableModel.setColumnIdentifiers(columnasActividad);
					dao.cargarActividad(tableModel);
					repaint();
				
					
				}
			});
		setVisible(true);
		
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
