package clases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Persona {

	protected String login;
	protected String contra;
	protected String email;
	protected String dni;
	protected String nombre;
	protected String apellidos;
	
	public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public Persona(String login, String contra, String email, String dni, String nombre, String apellidos
		) {
		super();
		this.login = login;
		this.contra = contra;
		this.email = email;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		
	}

	public Persona() {
		super();
		this.login = "";
		this.contra = "";
		this.email = "";
		this.dni = "";
		this.nombre = "";
		this.apellidos = "";
		
	}

	public Persona(Persona p) {
		super();
		this.login = p.login;
		this.contra = p.contra;
		this.email = p.email;
		this.dni = p.dni;
		this.nombre = p.nombre;
		this.apellidos = p.apellidos;
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return contra;
	}

	public void setPassword(String contra) {
		this.contra = contra;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getdNI() {
		return dni;
	}

	public void setdNI(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	

	
}