/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ikerhurcab
 */
public abstract class Visitante extends Persona{
    
    public Visitante() {
    }

    public Visitante(String nombre, int id, Date fecha) {
        super(nombre, id, fecha);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Visitante{");
        sb.append('}');
        return sb.toString();
    }


        
    public abstract int comprarEntrada();
}
