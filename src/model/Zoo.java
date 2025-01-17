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
public class Zoo {

    private ArrayList<Habitat> habitats;
    private ArrayList<Animal> animales;
    private ArrayList<Empleado> empleados;
    private ArrayList<Visitante> visitantes;

    public ArrayList<Habitat> getHabitats() {
        return habitats;
    }

    public void setHabitats(ArrayList<Habitat> habitats) {
        this.habitats = habitats;
    }

    public ArrayList<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(ArrayList<Animal> animales) {
        this.animales = animales;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public ArrayList<Visitante> getVisitantes() {
        return visitantes;
    }

    public void setVisitantes(ArrayList<Visitante> visitantes) {
        this.visitantes = visitantes;
    }

    public Zoo(ArrayList<Habitat> habitats, ArrayList<Animal> animales, ArrayList<Empleado> empleados, ArrayList<Visitante> visitantes) {
        this.habitats = habitats;
        this.animales = animales;
        this.empleados = empleados;
        this.visitantes = visitantes;
    }

    public Zoo() {
        this.habitats = new ArrayList<Habitat>();
        this.animales = new ArrayList<Animal>();
        this.empleados = new ArrayList<Empleado>();
        this.visitantes = new ArrayList<Visitante>();
        
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Zoo{");
        sb.append("habitats=").append(habitats);
        sb.append(", animales=").append(animales);
        sb.append(", empleados=").append(empleados);
        sb.append(", visitantes=").append(visitantes);
        sb.append('}');
        return sb.toString();
    }

    public void agregarHabitat(Habitat habitat) {
        this.habitats.add(habitat);
    }

    public void destruirHabitat(Habitat habitat) {
        this.habitats.remove(habitat);
    }

    public void despedirEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
    }

    public void agregarVisitante(Visitante visitante) {
        this.visitantes.add(visitante);
    }

    public void expulsarVisitante(Visitante visitante) {
        this.visitantes.remove(visitante);
    }

    public void agregarAnimal(Animal animal) {
        this.animales.add(animal);
    }

    public void eliminarAnimal(Animal animal) {
        this.animales.remove(animal);
    }

    public int calcularIngresos() {
        int total = 0;

        if (!this.empleados.isEmpty()) {
            for (Empleado empleado : this.empleados) {
                total -= empleado.cobrarSueldo();
            }
        }

        if (!this.visitantes.isEmpty()) {
            for (Visitante visitante : this.visitantes) {
                total += visitante.comprarEntrada();
            }
        }
        
        if(!this.habitats.isEmpty()) {
            for (Habitat habitat : this.habitats) {
                total -= habitat.getCosteMantenimiento();
            }
        }

        return total;
    }

}
