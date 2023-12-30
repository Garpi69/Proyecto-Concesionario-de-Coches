package ventanas;

public class Principal {
	private static DAO dao = new DAO();
	public static void main(String[] args) {
		 VentanaElegirTipoUsuario ventanaElegirTipoUsuario = new VentanaElegirTipoUsuario(dao);

	}

}
