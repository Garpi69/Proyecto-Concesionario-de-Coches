package ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import clases.Cita;

public class DateTimePicker {
    private JSpinner yearSpinner;
    private JSpinner monthSpinner;
    private JSpinner daySpinner;
    private JSpinner hourSpinner;
    private JDialog dialog;

    public DateTimePicker(DAO dao, String nombreCoche) {
        dialog = new JDialog();
        dialog.setTitle("Seleccione fecha y hora para concertar una cita:");
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(450, 400);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10,5, 10);

        // Año
        yearSpinner = new JSpinner(new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 2023, 3000, 1));
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "####");
        yearSpinner.setEditor(yearEditor);
        addSpinnerWithLabel("Año:", yearSpinner, gbc);

        // Mes
        monthSpinner = new JSpinner(new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1));
        JSpinner.NumberEditor monthEditor = new JSpinner.NumberEditor(monthSpinner, "##");
        monthSpinner.setEditor(monthEditor);
        addSpinnerWithLabel("Mes:", monthSpinner, gbc);

        // Día
        daySpinner = new JSpinner(new SpinnerNumberModel(Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 1, 31, 1));
        addSpinnerWithLabel("Día:", daySpinner, gbc);

        // Hora
        hourSpinner = new JSpinner(new SpinnerNumberModel(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0, 23, 1));
        addSpinnerWithLabel("Hora:", hourSpinner, gbc);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(e -> {
            Date selectedDate = getSelectedDateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(selectedDate);
            JOptionPane.showMessageDialog(dialog, "Fecha y Hora Seleccionadas: " + sdf.format(selectedDate));
            Cita cita = new Cita(date,dao.cliente.getLogin(),nombreCoche);
            dao.añadirCita(cita, dao);
            dialog.dispose();
        });

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(selectButton, gbc);

        dialog.setVisible(true);
    }

    private void addSpinnerWithLabel(String label, JSpinner spinner, GridBagConstraints gbc) {
        JLabel spinnerLabel = new JLabel(label);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        dialog.add(spinnerLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        dialog.add(spinner, gbc);

        gbc.gridy++;
    }

    private Date getSelectedDateTime() {
        int year = (int) yearSpinner.getValue();
        int month = (int) monthSpinner.getValue();
        int day = (int) daySpinner.getValue();
        int hour = (int) hourSpinner.getValue();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, 0, 0);

        return calendar.getTime();
    }


}