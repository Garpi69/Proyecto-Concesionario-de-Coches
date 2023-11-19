

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Cliente;

public class ClienteTest {

    @Test
    public void GetTestCliente()  {
        Date fechaNacimiento = new Date(System.currentTimeMillis());

        Cliente cliente = new Cliente("testUser", "testPassword", "test@example.com", "123456789", "John", "Doe",
                fechaNacimiento, 1234567890123456L);

      
        assertEquals("testUser", cliente.getLogin());
        assertEquals("testPassword", cliente.getPassword());
        assertEquals("test@example.com", cliente.getEmail());
        assertEquals("123456789", cliente.getdNI());
        assertEquals("John", cliente.getNombre());
        assertEquals("Doe", cliente.getApellidos());
        assertEquals(fechaNacimiento, cliente.getFechaNacimiento());
        assertEquals(1234567890123456L, cliente.getNumTarjeta());
    }
}