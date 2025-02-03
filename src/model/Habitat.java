/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ikerhurcab
 */
public class Habitat {
    private ArrayList<Animal> animales;
    private String nombre;
    private int costeMantenimiento;

    public ArrayList<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(ArrayList<Animal> animales) {
        this.animales = animales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosteMantenimiento() {
        return costeMantenimiento;
    }

    public void setCosteMantenimiento(int costeMantenimiento) {
        this.costeMantenimiento = costeMantenimiento;
    }

    public Habitat(ArrayList<Animal> animales, String nombre, int costeMantenimiento) {
        this.animales = animales;
        this.nombre = nombre;
        this.costeMantenimiento = costeMantenimiento;
    }

    public Habitat() {
        this.animales = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Habitat{");
        sb.append("animales=").append(animales);
        sb.append(", nombre=").append(nombre);
        sb.append(", costeMantenimiento=").append(costeMantenimiento);
        sb.append('}');
        return sb.toString();
    }
    
    public void agregarAnimal(Animal animal) {
        this.animales.add(animal);
    }
    
    public void eliminarAnimal (Animal animal) {
        this.animales.remove(animal);
    }
}
