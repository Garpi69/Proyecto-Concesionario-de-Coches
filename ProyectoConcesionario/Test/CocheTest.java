
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Coche;
import clases.Color;

public class CocheTest {

    @Test
    public void testCocheConstructor() throws ParseException {
        Date matriculacion = new Date(System.currentTimeMillis());

        Coche coche = new Coche("Toyota", "Camry", Color.AZUL, "Sedan", 200, 5, 25000, 400, matriculacion, "Gasolina");

        // Verify that the fields are set correctly
        assertEquals("Toyota", coche.getMarca());
        assertEquals("Camry", coche.getModelo());
        assertEquals(Color.AZUL, coche.getColor());
        assertEquals("Sedan", coche.getTipo());
        assertEquals(200, coche.getPotencia());
        assertEquals(5, coche.getNumPlazas());
        assertEquals(25000, coche.getPrecio());
        assertEquals(400, coche.getCuota());
        assertEquals(matriculacion, coche.getMatriculacion());
        assertEquals("Gasolina", coche.getCombustible());
    }
}