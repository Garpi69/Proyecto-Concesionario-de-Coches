

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import clases.Coche;
import clases.Color;
import clases.Persona;
import clases.Prueba;
import clases.Vehiculo;

public class PruebaTest {

    @Test
    public void getTestPrueba() throws ParseException  {
        Date fechaInicio = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023");
        Date fechaFin = new SimpleDateFormat("dd/MM/yyyy").parse("15/01/2023");

        Persona cliente = new Persona("testUser", "testPassword", "test@example.com", "123456789", "John", "Doe", new Date());
        Vehiculo vehiculo = new Coche("Toyota", "Camry", Color.AZUL, "Sedan", 200, 5, 25000, 400, new Date(), "Gasolina");

        Prueba prueba = new Prueba(cliente, vehiculo, fechaInicio, fechaFin, "TestCity");

        
        assertEquals(cliente, prueba.getCliente());
        assertEquals(vehiculo, prueba.getVehiculo());
        assertEquals(fechaInicio, prueba.getFechaInicio());
        assertEquals(fechaFin, prueba.getfechaFin());
        assertEquals("TestCity", prueba.getCiudad());
    }
}