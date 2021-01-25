import java.util.Scanner;

public class Ejercicio4 {

	static final double LONGITUD_CANYON = 5; // metros
	static final double G = 9.8; // gravedad m/s^2
	static final double PIXEL_TO_REAL_WORLD = 5;// metros
	static final int MAX_X_PIXEL = 80;// tamaño máximo píxeles eje horizontal
	static final int MAX_Y_PIXEL = 24;// tamaño máximo píxeles eje vertical
	// Formateo de texto salida:
	static final String ANSI_RESET = "\u001B[0m";
	static final String ANSI_GREEN = "\u001B[32m";

	static double alfa; // ángulo inclinación
	static double alfa_radianes; // ángulo inclinación en radianes
	static double velocidadInicial; // velocidad inicial (m/s)
	// Posición inicial del cañón en el mundo real:
	static double xc = 0;
	static double yc = 0;
	// Posición inicial del cañón en la pantalla:
	static int xc_px = 0;
	static int yc_px = MAX_Y_PIXEL;

	public static void main(String[] args) {

		double xp, yp;// Posición inicial del proyectil en el mundo real:
		double ts, tv; // Tiempos (subida y vuelo)

		// Lectura de datos:
		Scanner sc = new Scanner(System.in);
		System.out.print("Teclee ángulo (grados): ");
		alfa = sc.nextDouble();
		alfa_radianes = Math.toRadians(alfa);

		System.out.print("Teclee velocidad(m/s): ");
		velocidadInicial = sc.nextDouble();
		sc.close();

		// Calculamos posición inicial del proyectil en el mundo real:
		xp = calculaXP();
		yp = calculaYP();
		// Calculamos tiempos:
		ts = calculaTiempoSubida();
		tv = calculaTiempoVuelo(ts);

		// Resultados:
		System.out.printf("\tPos. inicial del proyectil: (%.2f, %.2f)\n", xp, yp);
		System.out.printf("\tTiempo de subida:\t%s segundos y de vuelo %.2f segundos\n", formatGreenRealNumber(ts), tv);
		System.out.printf("\tMax. altura:\t\t%s metros\n", formatGreenRealNumber(calculaAlturaMaxima()));
		System.out.printf("\tMax. distancia:\t\t%s metros\n",formatGreenRealNumber(calculaDistanciaHorizontalMaxima()));
		imprimeTabla(tv, xp, yp);
	}

	/**
	 * Posición inicial del proyectil en el eje horizontal (metros)
	 * 
	 * @return
	 */
	private static double calculaXP() {
		return xc + LONGITUD_CANYON * Math.cos(alfa_radianes);
	}

	/**
	 * Posición inicial del proyectil en el eje vertical (metros)
	 * 
	 * @return
	 */
	private static double calculaYP() {
		return yc + LONGITUD_CANYON * Math.sin(alfa_radianes);
	}

	/**
	 * Velocidad horizontal (m/s)
	 * 
	 * @return
	 */
	private static double calculaVelocidadHorizontal() {
		return velocidadInicial * Math.cos(alfa_radianes);
	}

	/**
	 * Velocidad vertical (m/s)
	 * 
	 * @return
	 */
	private static double calculaVelocidadVertical() {
		return velocidadInicial * Math.sin(alfa_radianes);
	}

	/**
	 * El tiempo que tarda el proyectil en alcanzar la altura máxima (segundos)
	 * 
	 * @return
	 */
	private static double calculaTiempoSubida() {
		return calculaVelocidadVertical() / G;
	}

	/**
	 * El tiempo que tarda el proyectil en alcanzar la distancia máxima (segundos)
	 * 
	 * @param tiempoSubida
	 * @return
	 */
	private static double calculaTiempoVuelo(double tiempoSubida) {
		return tiempoSubida * 2;
	}

	/**
	 * Distancia máxima alcanzada por el proyectil (metros)
	 * 
	 * @return
	 */
	private static double calculaDistanciaHorizontalMaxima() {
		return (Math.pow(velocidadInicial, 2) * Math.sin(2 * alfa_radianes)) / G;
	}

	/**
	 * Altura máxima alcanzada por el proyectil (metros)
	 * 
	 * @return
	 */
	private static double calculaAlturaMaxima() {

		return (Math.pow(velocidadInicial, 2) * Math.pow(Math.sin(alfa_radianes), 2)) / (G * 2);
	}

	/**
	 * Posición horizontal del proyectil en un instante de tiempo t
	 * 
	 * @param t  segundos
	 * @param xp posición horizontal inicial del proyectil
	 * @return metros
	 */
	private static double calculaPosicionX(double t, double xp) {
		double Vx = calculaVelocidadHorizontal();
		return xp + Vx * t;
	}

	/**
	 * Posición vertical del proyectil en un instante de tiempo t
	 * 
	 * @param t  segundos
	 * @param yp posición vertical inicial del proyectil
	 * @return metros
	 */
	private static double calculaPosicionY(double t, double yp) {
		double Vy = calculaVelocidadVertical();
		return yp + Vy * t - 0.5 * G * t * t;
	}

	/**
	 * Obtiene la posición horizontal en pantalla
	 * 
	 * @param x metros
	 * @return pixels
	 */
	private static int xToPixels(double x) {
		int pixel = xc_px + metersToPixels(x);
		pixel = (pixel > MAX_X_PIXEL) ? MAX_X_PIXEL : pixel;
		pixel = (pixel < 0) ? 0 : pixel;
		return pixel;
	}

	/**
	 * Obtiene la posición vertical en pantalla
	 * 
	 * @param y metros
	 * @return pixels
	 */
	private static int yToPixels(double y) {
		int pixel = yc_px - metersToPixels(y);
		pixel = (pixel > MAX_Y_PIXEL) ? MAX_Y_PIXEL : pixel;
		pixel = (pixel < 0) ? 0 : pixel;
		return pixel;
	}

	/**
	 * Convierte de metros a píxeles
	 * 
	 * @param meters
	 * @return pixels
	 */
	private static int metersToPixels(double meters) {
		return (int) (meters / PIXEL_TO_REAL_WORLD);
	}

	/**
	 * Imprime una tabla informando de la posición del proyectil en diferentes
	 * instantes de tiempo
	 * 
	 * @param tVuelo
	 * @param xp
	 * @param yp
	 */
	private static void imprimeTabla(double tVuelo, double xp, double yp) {
		double t;
		String rowSeparator = "+-------------+---------------------------+----------------------+";
		String title = "| Tiempo      |	  Posicion en el mundo 	  | Posicion en Pantalla |";

		System.out.println(rowSeparator);
		System.out.println(title);
		System.out.println(rowSeparator);
		imprimeFila(0, xp, yp);
		System.out.println(rowSeparator);
		t = tVuelo * 0.25;
		imprimeFila(t, calculaPosicionX(t, xp), calculaPosicionY(t, yp));
		System.out.println(rowSeparator);
		t = tVuelo * 0.50;
		imprimeFila(t, calculaPosicionX(t, xp), calculaPosicionY(t, yp));
		System.out.println(rowSeparator);
		t = tVuelo * 0.75;
		imprimeFila(t, calculaPosicionX(t, xp), calculaPosicionY(t, yp));
		System.out.println(rowSeparator);
		t = tVuelo;
		imprimeFila(t, calculaPosicionX(t, xp), calculaPosicionY(t, yp));
		System.out.println(rowSeparator);
	}

	/**
	 * Imprime una fila de la tabla de resultados
	 * 
	 * @param t    tiempo en segundos
	 * @param posX posición del proyectil en el mundo real en el eje horizontal
	 *             (metros)
	 * @param posY posición del proyectil en el mundo real en el eje vertical
	 *             (metros)
	 */
	private static void imprimeFila(double t, double posX, double posY) {
		String row = "| %10.2f  | (%10.2f, %10.2f)  |    (%5d, %5d)    |\n";
		System.out.printf(row, t, posX, posY, xToPixels(posX), yToPixels(posY));
	}

	/**
	 * Devuelve string con un número real formateado y de color verde
	 * 
	 * @param number
	 * @return
	 */
	private static String formatGreenRealNumber(double number) {
		return String.format(ANSI_GREEN + "%10.2f" + ANSI_RESET, number);
	}
}
