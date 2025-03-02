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
public abstract class Empleado extends Persona {

    protected int sueldo;
    private ArrayList<Animal> animales;

    public Empleado() {
        this.animales = new ArrayList<Animal>();

    }

    public Empleado(String nombre, int id, Date fecha) {
        super(nombre, id, fecha);
    }

    public int getSueldo() {
        return sueldo;
    }

    public ArrayList<Animal> getAnimales() {
        return animales;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Empleado{");
        sb.append("sueldo=").append(sueldo);
        sb.append('}');
        return sb.toString();
    }

    public abstract int cobrarSueldo();

}
