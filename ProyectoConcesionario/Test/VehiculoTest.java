import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Test;

import clases.Color;
import clases.Vehiculo;

public class VehiculoTest {

    @Test
    public void testGetVehiculo() {
        Color color = Color.AZUL;
        // Use the current time in milliseconds for the matriculation date
        Date matriculacion = new Date(System.currentTimeMillis());
        Vehiculo v = new Vehiculo("Toyota", "yaris", color, "Gasolina", 7, 5, 15000, 30000, matriculacion);
        assertEquals("Toyota", v.getMarca());
        assertEquals("yaris", v.getModelo());
        assertEquals(color, v.getColor());
        assertEquals("Gasolina", v.getTipo());
        assertEquals(7, v.getPotencia());
        assertEquals(5, v.getNumPlazas());
        assertEquals(15000, v.getPrecio());
        assertEquals(30000, v.getCuota());
        assertEquals(matriculacion, v.getMatriculacion());
    }
}