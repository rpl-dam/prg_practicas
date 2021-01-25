import java.util.Scanner;

public class Ejercicio3 {

	public static void main(String[] args) {
		String userName, password;
		String validaClave = "";

		Scanner sc = new Scanner(System.in);
		System.out.print("Nombre de usuario: ");
		userName = sc.next();

		System.out.print("Password: ");
		password = sc.next();
		sc.close();

		validaClave = (userName.compareTo("admin") == 0) ? "El usuario \"admin\" no se puede utilizar\n" : "";
		validaClave += (userName.matches("^[0-9]+.*")) ? "El nombre de usuario debe comenzar por letra\n" : "";
		validaClave += (password.length() < 8) ? "Password debe contener un mínimo de 8 caracteres\n" : "";
		validaClave += (!password.matches(".*[a-z]+.*")) ? "Password debe tener al menos una letra minúscula\n" : "";
		validaClave += (!password.matches(".*[A-Z]+.*")) ? "Password debe tener al menos una letra mayúscula\n" : "";
		validaClave += (!password.matches(".*[._@]+.*"))
				? "El password debe tener al menos un símbolo \"._@\"\n"
				: "";

		System.out.println(validaClave);
	}
}
