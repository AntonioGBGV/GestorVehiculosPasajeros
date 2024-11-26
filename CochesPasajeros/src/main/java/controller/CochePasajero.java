package controller;

import dao.CocheDAO;
import dao.CochePasajeroDAO;
import dao.PasajeroDAO;
import model.Coche;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CochePasajero {
    private final CocheDAO cocheDAO;
    private final PasajeroDAO pasajeroDAO;
    private final CochePasajeroDAO cochePasajeroDAO;

    public CochePasajero() {
        this.cocheDAO = new CocheDAO();
        this.pasajeroDAO = new PasajeroDAO();
        this.cochePasajeroDAO = new CochePasajeroDAO();
    }

    public void menuGestionCoche() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Añadir nuevo coche");
            System.out.println("2. Borrar coche por ID");
            System.out.println("3. Consultar coche por ID");
            System.out.println("4. Modificar coche por ID");
            System.out.println("5. Listar todos los coches");
            System.out.println("6. Volver al menú principal");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1 -> addCoche(scanner);
                case 2 -> deleteCocheById(scanner);
                case 3 -> getCocheById(scanner);
                case 4 -> updateCocheById(scanner);
                case 5 -> listAllCoches();
                case 6 -> {
                    System.out.println("Volviendo al menú principal...");
                    return;
                }
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 0);
        scanner.close();
    }

    public void addCoche(Scanner scanner) {
        System.out.println("Introduce el modelo del coche:");
        String modelo = scanner.nextLine();
        System.out.println("Introduce la marca del coche:");
        String marca = scanner.nextLine();
        System.out.println("Introduce el precio del coche:");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        try {
            cocheDAO.addCoche(new Coche(modelo, marca, precio));
            System.out.println("Coche añadido con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al añadir el coche: " + e.getMessage());
        }
    }

    public void deleteCocheById(Scanner scanner) {
        System.out.println("Coches disponibles:");
        listAllCoches();
        System.out.println("Introduce el ID del coche a eliminar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            cocheDAO.deleteCocheById(id);
            System.out.println("Coche eliminado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el coche: " + e.getMessage());
        }
    }

    public void getCocheById(Scanner scanner) {
        System.out.println("Introduce el ID del coche a consultar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Coche coche = cocheDAO.getCocheById(id);
            if (coche != null) {
                coche.mostrarDatos();
            } else {
                System.out.println("No se encontró ningún coche con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el coche: " + e.getMessage());
        }
    }

    public void updateCocheById(Scanner scanner) {
        System.out.println("Coches disponibles:");
        listAllCoches();
        System.out.println("Introduce el ID del coche a modificar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Coche cocheExistente = cocheDAO.getCocheById(id);
            if (cocheExistente != null) {
                System.out.println("Introduce el nuevo modelo del coche:");
                String modelo = scanner.nextLine();
                System.out.println("Introduce la nueva marca del coche:");
                String marca = scanner.nextLine();
                System.out.println("Introduce el nuevo precio del coche:");
                double precio = scanner.nextDouble();
                scanner.nextLine();

                cocheDAO.updateCocheById(new Coche(id, modelo, marca, precio));
                System.out.println("Coche actualizado con éxito.");
            } else {
                System.out.println("No se encontró ningún coche con el ID especificado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al modificar el coche: " + e.getMessage());
        }
    }

    public void listAllCoches() {
        try {
            ArrayList<Coche> coches = cocheDAO.getAllCoches();
            if (coches.isEmpty()) {
                System.out.println("No hay coches registrados.");
            } else {
                coches.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los coches: " + e.getMessage());
        }
    }
}
