import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import clases.Color;
import clases.Moto;

public class MotoTest {

    @Test
    public void getMotoConstructor() {
        Date matriculacion = new Date(System.currentTimeMillis());

        Moto moto = new Moto("Honda", "CBR", Color.ROJO, "Deportiva", 150, 1, 12000, 300, matriculacion, 200, true);

        assertEquals("Honda", moto.getMarca());
        assertEquals("CBR", moto.getModelo());
        assertEquals(Color.ROJO, moto.getColor());
        assertEquals("Deportiva", moto.getTipo());
        assertEquals(150, moto.getPotencia());
        assertEquals(1, moto.getNumPlazas());
        assertEquals(12000, moto.getPrecio());
        assertEquals(300, moto.getCuota());
        assertEquals(matriculacion, moto.getMatriculacion());
        assertEquals(200, moto.getPeso());
        assertEquals(true, moto.isBaul());
    }
}