import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Color;
import clases.Moto2;

public class Moto2Test {

    @Test
    public void getTestMoto2()  {
        Date matriculacion = new Date(System.currentTimeMillis());

        Moto2 moto2 = new Moto2("Yamaha", "YZF", Color.AZUL, "Deportiva", 200, 1, 15000, 300, matriculacion, 150, true, 20000);

        assertEquals(1, moto2.getNumPlazas());
        assertEquals(15000, moto2.getPrecio());
        assertEquals(300, moto2.getCuota());
        assertEquals(matriculacion, moto2.getMatriculacion());
        assertEquals(150, moto2.getPeso());
        assertEquals(true, moto2.isBaul());
        assertEquals(20000, moto2.getKilometraje());
    }
}