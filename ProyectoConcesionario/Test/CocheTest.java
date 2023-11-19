
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Coche;
import clases.Color;

public class CocheTest {

    @Test
    public void getTestCocheConstructor()  {
        Date matriculacion = new Date(System.currentTimeMillis());

        Coche coche = new Coche("Toyota", "cora", Color.AZUL, "Sedan", 350, 6, 25000, 400, matriculacion, "Gasolina");


        assertEquals("cora", coche.getModelo());
        assertEquals(Color.AZUL, coche.getColor());
        assertEquals("Sedan", coche.getTipo());
        assertEquals(350, coche.getPotencia());
        assertEquals(6, coche.getNumPlazas());
        assertEquals(25000, coche.getPrecio());
        assertEquals(400, coche.getCuota());
        assertEquals(matriculacion, coche.getMatriculacion());
        assertEquals("Gasolina", coche.getCombustible());
    }
}