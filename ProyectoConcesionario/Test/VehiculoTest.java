import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Test;

import clases.Color;
import clases.Vehiculo;

public class VehiculoTest {

    @Test
    public void testGetVehiculo() {
      
       
        Date matriculacion = new Date(System.currentTimeMillis());
        Color color = Color.AZUL;
        Vehiculo v = new Vehiculo("Toyota", "cora", color, "Gasolina", 7, 5, 15000, 30000, matriculacion);
        assertEquals("Toyota", v.getMarca());
        assertEquals("cora", v.getModelo());
        assertEquals(color, v.getColor());
        assertEquals("Gasolina", v.getTipo());
        assertEquals(7, v.getPotencia());
        assertEquals(5, v.getNumPlazas());
        assertEquals(15000, v.getPrecio());
        assertEquals(30000, v.getCuota());
        assertEquals(matriculacion, v.getMatriculacion());
    }
}