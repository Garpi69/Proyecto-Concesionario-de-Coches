import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Coche2;
import clases.Color;

public class Coche2Test {

    @Test
    public void getTestCoche2()  {
        Date matriculacion = new Date(System.currentTimeMillis());

        Coche2 coche2 = new Coche2("Ford", "Focus", Color.NEGRO, "Compacto", 120, 5, 20000, 400, matriculacion, "Gasolina", 50000);

        
        assertEquals("Ford", coche2.getMarca());
        assertEquals("Focus", coche2.getModelo());
        assertEquals(Color.NEGRO, coche2.getColor());
        assertEquals("Compacto", coche2.getTipo());
        assertEquals(120, coche2.getPotencia());
        assertEquals(5, coche2.getNumPlazas());
        assertEquals(20000, coche2.getPrecio());
        assertEquals(400, coche2.getCuota());
        assertEquals(matriculacion, coche2.getMatriculacion());
        assertEquals("Gasolina", coche2.getCombustible());
        assertEquals(50000, coche2.getKilometraje());
    }
}