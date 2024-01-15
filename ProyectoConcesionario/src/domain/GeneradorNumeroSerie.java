package src.domain;

public class GeneradorNumeroSerie {
	private static long numeroDeSerieActual = 10000000;

	public static synchronized long generarSiguienteNumeroDeSerie() {
		return numeroDeSerieActual++;
	}
}