package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import src.io.FicheroActividad;

public class FicheroTest {

	@Test
	public void testEscrituraYLectura() {
		FicheroActividad.guardarActividad("Prueba test escritura");
		List<String> actividad = FicheroActividad.cargarActividad();
		assertNotNull(actividad);
		assertEquals(actividad.get(0), "Prueba test escritura");
	}
}
