	import java.text.ParseException;
	import java.util.Date;

	import org.junit.Test;
	import static org.junit.Assert.assertEquals;

	import clases.Persona;

	public class PersonaTest {

	    @Test
	    public void GetTestPersona()  {
	  

	       
	        Date fechaNacimiento = new Date(System.currentTimeMillis());

	        Persona persona = new Persona("123", "contra123", "per@Hotmail", "132456", "Juan", "Sanchez", fechaNacimiento);

	      
	        assertEquals("123", persona.getLogin());
	        assertEquals("contra123", persona.getPassword());
	        assertEquals("per@Hotmail", persona.getEmail());
	        assertEquals("132456", persona.getdNI());
	        assertEquals("Juan", persona.getNombre());
	        assertEquals("Sanchez", persona.getApellidos());
	        assertEquals(fechaNacimiento, persona.getFechaNacimiento());
	    }
	}