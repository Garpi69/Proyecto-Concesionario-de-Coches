package ventanas;
import com.toedter.calendar.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;




public class VentanaCalendario extends JPanel {

	
	

    private Date selectedDate;
    private JCalendar calendar;

    @SuppressWarnings("deprecation")
	public VentanaCalendario() {
        setLayout(new BorderLayout());
        selectedDate = null;
        Locale locale = new Locale("es");
        JPanel calendarPanel = new JPanel(new BorderLayout());

        calendar = new JCalendar();
        calendar.setLocale(locale); // Establecer el idioma a español
        calendar.setWeekOfYearVisible(true);

        calendar.getDayChooser().getDayPanel().setBackground(Color.WHITE);

        calendar.getDayChooser().addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("day")) {
                selectDay((Date) evt.getNewValue());
            }
        });

        calendarPanel.add(calendar, BorderLayout.CENTER);
        add(calendarPanel, BorderLayout.CENTER);
    }

    private void selectDay(Date date) {
        selectedDate = date;

        String hora = JOptionPane.showInputDialog(this, "Selecciona la hora (HH:MM)");

        if (hora != null && !hora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cita agregada el " + formatDate(selectedDate) + " a las " + hora);
            // Aquí puedes manejar la cita en la fecha y hora seleccionadas
        } else {
            JOptionPane.showMessageDialog(this, "No se ingresó una hora válida (HH:MM)");
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Calendar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            VentanaCalendario customCalendar = new VentanaCalendario();
            frame.add(customCalendar);
            frame.setVisible(true);
        });
    }
}
