import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class Ejercicio7 {

	public static void main(String[] args) {
		int dia, mes, anyo, plazo;

		// Lectura de datos:
		Scanner sc = new Scanner(System.in);
		System.out.print("Día del mes (1-31): ");
		dia = sc.nextInt();

		System.out.print("Mes del año (1-12): ");
		mes = sc.nextInt();

		System.out.print("Año (yyyy): ");
		anyo = sc.nextInt();

		System.out.print("Plazo (días naturales): ");
		plazo = sc.nextInt();
		sc.close();

		// Calcular fin plazo:
		Calendar c = Calendar.getInstance();
		c.set(anyo, mes - 1, dia);
		c.add(c.DATE, plazo);

		int plazo_weekday = c.get(Calendar.DAY_OF_WEEK);
		int correccion_plazo = -((plazo_weekday == Calendar.SATURDAY) ? 1
				: ((plazo_weekday == Calendar.SUNDAY) ? 2 : 0));
		c.add(Calendar.DATE, correccion_plazo);

		// Mostrar resultados:
		System.out.println("\nDebe entregarlo hasta el " + formatDate(c));
	}

	/**
	 * Formateo del calendario para mostrar la fecha
	 * 
	 * @param c
	 * @return
	 */
	private static String formatDate(Calendar c) {
		Locale l = Locale.getDefault();
		return String.format("%s %d de %s de %d", 
				c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, l),
				c.get(Calendar.DAY_OF_MONTH), 
				c.getDisplayName(Calendar.MONTH, Calendar.LONG, l),
				c.get(Calendar.YEAR)
				);
	}
}
