
import controller.CochePasajero;
import controller.PasajeroCoche;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    int opcion;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\nMenú principal:");
            System.out.println("1. Gestión de coches");
            System.out.println("2. Gestión de pasajeros");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    new CochePasajero().menuGestionCoche();
                    break;
                case 2:
                    new PasajeroCoche().menuGestionPasajeros();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
