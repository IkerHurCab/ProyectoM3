/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ikerhurcab
 */
public class Cuidador extends Empleado {
    private ArrayList<Animal> animales;

    public Cuidador() {
        this.animales = new ArrayList<Animal>();
    }

    public Cuidador(String nombre, int id, Date fecha) {
        super(nombre, id, fecha);
    }

    public ArrayList<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(ArrayList<Animal> animales) {
        this.animales = animales;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cuidador{");
        sb.append("animales=").append(animales);
        sb.append('}');
        return sb.toString();
    }
    
    @Override
    public int cobrarSueldo() {
      return this.sueldo;
    }
}
