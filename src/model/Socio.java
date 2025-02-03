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
public class Socio extends Visitante {

    public Socio() {
    }

    public Socio(String nombre, int id, Date fecha) {
        super(nombre, id, fecha);
    }
    
    @Override
    public int comprarEntrada(){
        return 15;
    }
}
