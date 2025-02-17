/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author iker-hurtado-caballo
 */
public class NoEmployeesException extends Exception {

    public NoEmployeesException() {
        super("Error: no hay empleados en el Zoo.");
    }

}