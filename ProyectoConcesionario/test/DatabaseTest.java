package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import src.db.DAO;
import src.domain.Cliente;

public class DatabaseTest {
	DAO dao = new DAO();

	Cliente cliente = new Cliente("usuarioPrueba", "contrasenaPrueba", "email@prueba.com", "78226394S", "Prueba",
			"Test", new Date(2000 / 10 / 04), 0, "usuario2: 1300");

	@Test
	public void inserccionClienteTest() {
		try {
			dao.agregarCliente(cliente);
			Cliente clienteObtenido = dao.obtenerClientePorDNI("78226394S");
			assertNotNull(clienteObtenido);
			assertEquals(cliente, clienteObtenido);
			dao.eliminarCliente("usuarioPrueba");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
