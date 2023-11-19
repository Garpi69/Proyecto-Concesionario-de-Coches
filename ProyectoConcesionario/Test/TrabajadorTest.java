

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Trabajador;

public class TrabajadorTest {

    @Test
    public void getTestTrabajador() {
        Date fechaNacimiento = new Date(System.currentTimeMillis());

        Trabajador trabajador = new Trabajador("testUser", "testPassword", "test@example.com", "123456789", "John",
                "Doe", fechaNacimiento, 50000, true, "Manager");

     
        assertEquals("testUser", trabajador.getLogin());
        assertEquals("testPassword", trabajador.getPassword());
        assertEquals("test@example.com", trabajador.getEmail());
        assertEquals("123456789", trabajador.getdNI());
        assertEquals("John", trabajador.getNombre());
        assertEquals("Doe", trabajador.getApellidos());
        assertEquals(fechaNacimiento, trabajador.getFechaNacimiento());
        assertEquals(50000, trabajador.getSueldo());
        assertEquals(true, trabajador.esAdmin());
        assertEquals("Manager", trabajador.getPuesto());

        
    }  
}