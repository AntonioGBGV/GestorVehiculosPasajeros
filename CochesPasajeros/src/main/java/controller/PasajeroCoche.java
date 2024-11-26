package controller;

import dao.CocheDAO;
import dao.CochePasajeroDAO;
import dao.PasajeroDAO;
import model.Coche;
import model.Pasajero;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PasajeroCoche {
    private final PasajeroDAO pasajeroDAO;
    private final CocheDAO cocheDAO;
    private final CochePasajeroDAO cochePasajeroDAO;
    private final Scanner scanner;

    public PasajeroCoche() {
        pasajeroDAO = new PasajeroDAO();
        cocheDAO = new CocheDAO();
        cochePasajeroDAO = new CochePasajeroDAO();
        scanner = new Scanner(System.in);
    }

    public void menuGestionPasajeros() {
        int opcion;
        do {
            System.out.println("\nGestión de Pasajeros:");
            System.out.println("1. Añadir nuevo pasajero");
            System.out.println("2. Borrar pasajero por ID");
            System.out.println("3. Consultar pasajero por ID");
            System.out.println("4. Listar todos los pasajeros");
            System.out.println("5. Añadir pasajero a coche");
            System.out.println("6. Eliminar pasajero de coche");
            System.out.println("7. Listar pasajeros de un coche");
            System.out.println("8. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1 -> addPasajero();
                case 2 -> deletePasajeroById();
                case 3 -> getPasajeroById();
                case 4 -> listAllPasajeros();
                case 5 -> addPasajeroToCoche();
                case 6 -> removePasajeroFromCoche();
                case 7 -> listPasajerosByCoche();
                case 8 -> {
                    System.out.println("Volviendo al menú principal...");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void addPasajero() {
        try {
            System.out.print("Nombre del pasajero: ");
            String nombre = scanner.nextLine();
            System.out.print("Edad del pasajero: ");
            int edad = scanner.nextInt();
            System.out.print("Peso del pasajero: ");
            double peso = scanner.nextDouble();

            Pasajero pasajero = new Pasajero(nombre, edad, peso);
            pasajeroDAO.addPasajero(pasajero);
            System.out.println("Pasajero añadido correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al añadir el pasajero: " + e.getMessage());
        }
    }

    private void deletePasajeroById() {
        try {
            System.out.print("ID del pasajero a borrar: ");
            int id = scanner.nextInt();
            pasajeroDAO.deletePasajeroById(id);
            System.out.println("Pasajero borrado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al borrar el pasajero: " + e.getMessage());
        }
    }

    private void getPasajeroById() {
        try {
            System.out.print("ID del pasajero a consultar: ");
            int id = scanner.nextInt();
            Pasajero pasajero = pasajeroDAO.getPasajeroById(id);
            if (pasajero != null) {
                System.out.println(pasajero);
            } else {
                System.out.println("No se encontró el pasajero.");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el pasajero: " + e.getMessage());
        }
    }

    private void listAllPasajeros() {
        try {
            ArrayList<Pasajero> pasajeros = pasajeroDAO.getAllPasajeros();
            if (pasajeros.isEmpty()) {
                System.out.println("No hay pasajeros registrados.");
            } else {
                pasajeros.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los pasajeros: " + e.getMessage());
        }
    }

    private void addPasajeroToCoche() {
        try {
            System.out.println("Coches disponibles:");
            ArrayList<Coche> coches = cocheDAO.getAllCoches();
            coches.forEach(System.out::println);

            System.out.print("ID del coche: ");
            int idCoche = scanner.nextInt();

            System.out.println("Pasajeros disponibles:");
            listAllPasajeros();

            System.out.print("ID del pasajero: ");
            int idPasajero = scanner.nextInt();

            cochePasajeroDAO.addPasajeroCoche(idCoche, idPasajero);
            System.out.println("Pasajero añadido al coche correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al añadir pasajero al coche: " + e.getMessage());
        }
    }

    private void removePasajeroFromCoche() {
        try {
            System.out.println("Coches disponibles:");
            ArrayList<Coche> coches = cocheDAO.getAllCoches();
            coches.forEach(System.out::println);

            System.out.print("ID del coche: ");
            int idCoche = scanner.nextInt();

            System.out.println("Pasajeros asociados al coche:");
            ArrayList<Pasajero> pasajeros = cochePasajeroDAO.getPasajerosByCoche(idCoche);
            pasajeros.forEach(System.out::println);

            System.out.print("ID del pasajero a eliminar: ");
            int idPasajero = scanner.nextInt();

            cochePasajeroDAO.removePasajeroCoche(idCoche, idPasajero);
            System.out.println("Pasajero eliminado del coche correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar pasajero del coche: " + e.getMessage());
        }
    }

    private void listPasajerosByCoche() {
        try {
            System.out.println("Coches disponibles:");
            ArrayList<Coche> coches = cocheDAO.getAllCoches();
            coches.forEach(System.out::println);

            System.out.print("ID del coche: ");
            int idCoche = scanner.nextInt();

            ArrayList<Pasajero> pasajeros = cochePasajeroDAO.getPasajerosByCoche(idCoche);
            if (pasajeros.isEmpty()) {
                System.out.println("No hay pasajeros asociados al coche.");
            } else {
                pasajeros.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pasajeros del coche: " + e.getMessage());
        }
    }
}