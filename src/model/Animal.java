/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ikerhurcab
 */
public class Animal {
    private String nombre;
    private String especie;
    private Empleado empleado;
    private int salud;

    public Animal() {
    }

    public Animal(String nombre, String especie, Empleado empleado, int salud) {
        this.nombre = nombre;
        this.especie = especie;
        this.empleado = empleado;
        this.salud = salud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Animal{");
        sb.append("nombre=").append(nombre);
        sb.append(", especie=").append(especie);
        sb.append(", empleado=").append(empleado);
        sb.append(", salud=").append(salud);
        sb.append('}');
        return sb.toString();
    }
    
    public void asignarCuidador(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public void realizarCuidados() {
        this.salud = 100;
    }
}
