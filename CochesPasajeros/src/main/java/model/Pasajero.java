package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Pasajero {

    private int id;
    private String nombre;
    private int edad;
    private double peso;

    public Pasajero(String nombre, int edad, double peso) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
    }
    public void mostrarDatos(){
        System.out.println("Id = " + id);
        System.out.println("Nombre = " + nombre);
        System.out.println("Edad = " + edad);
        System.out.println("Peso = " + peso);
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que 0");
        }
        this.peso = peso;
    }

}
