/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.console;

import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.*;

/**
 *
 * @author ikerhurcab
 */
public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        scan.useDelimiter("\n");
        String answer = "";
        Zoo zoo = new Zoo();
        int id = 1;
        do {
            System.out.println("MENU");
            System.out.println("0. Salir");
            System.out.println("1. Anadir animal al Zoo");
            System.out.println("2. Quitar animal del Zoo");
            System.out.println("3. Ver todos los animales");
            System.out.println("4. Ver animales por habitat");
            System.out.println("5. Anadir habitat al Zoo");
            System.out.println("6. Quitar habitat del Zoo");
            System.out.println("7. Ver todos los habitats");
            System.out.println("8. Contratar empleado");
            System.out.println("9. Despedir empleado");
            System.out.println("10. Ver todos los empleados");
            System.out.println("11. Anadir visitante al zoo");
            System.out.println("12. Expulsar visitante del zoo");
            System.out.println("13. Ver todos los visitantes");
            System.out.println("14. Calcular ingresos del zoo");
            System.out.println("15. Realizar cuidados a un animal");

            answer = scan.next();

            switch (answer) {
                case "0":
                    break;
                case "1":
                    Animal animal = new Animal();
                    if (zoo.getHabitats().isEmpty()) {
                        int index;
                        int i = 1;
                        System.out.println("Como no hay habitats disponibles, el animal se anadira a un habitat por defecto.");

                        System.out.println("Escribe el nombre del animal");
                        answer = scan.next();
                        animal.setNombre(answer);

                        System.out.println("Escribe la especie del animal");
                        answer = scan.next();
                        animal.setEspecie(answer);

                        animal.setSalud(100);

                        if (zoo.getEmpleados().isEmpty()) {
                            System.out.println("Como no hay empleados, este animal no tendra un cuidador asignado.");
                        } else {
                            System.out.println("A que empleado asignaras este animal?");
                            for (Empleado empleado : zoo.getEmpleados()) {
                                System.out.println(i + " - " + empleado.getNombre());
                                i++;
                            }

                            i = 1;
                            answer = "-1";
                            try {
                                while (Integer.parseInt(answer) > zoo.getEmpleados().size() || Integer.parseInt(answer) < 0) {
                                    answer = scan.next();

                                    if (Integer.parseInt(answer) > zoo.getEmpleados().size() || Integer.parseInt(answer) < 0) {
                                        System.out.println("Numero no valido");
                                    }
                                }

                                index = Integer.parseInt(answer);

                            } catch (NumberFormatException e) {
                                System.out.println("Error: " + e);
                                break;
                            }
                            Empleado empleadoSeleccionado = zoo.getEmpleados().get(index - 1);
                            animal.asignarCuidador(empleadoSeleccionado);

                        }

                        zoo.agregarAnimal(animal);

                    } else {
                        System.out.println("A que habitat quieres anadir el animal?");
                        int i = 1;
                        int index;
                        int index2;
                        for (Habitat habitat : zoo.getHabitats()) {
                            System.out.println(i + " - " + habitat.getNombre());
                            i++;
                        }

                        i = 1;
                        answer = "-1";
                        try {
                            while (Integer.parseInt(answer) > zoo.getHabitats().size() || Integer.parseInt(answer) < 0) {
                                answer = scan.next();

                                if (Integer.parseInt(answer) > zoo.getHabitats().size() || Integer.parseInt(answer) < 0) {
                                    System.out.println("Numero no valido");
                                }
                            }

                            index = Integer.parseInt(answer);

                        } catch (NumberFormatException e) {
                            System.out.println("Error: " + e);
                            break;
                        }

                        System.out.println("Escribe el nombre del animal");
                        answer = scan.next();
                        animal.setNombre(answer);

                        System.out.println("Escribe la especie del animal");
                        answer = scan.next();
                        animal.setEspecie(answer);
                        answer = "-1";
                        animal.setSalud(100);

                        if (zoo.getEmpleados().isEmpty()) {
                            System.out.println("Como no hay empleados, este animal no tendra un cuidador asignado.");
                        } else {
                            System.out.println("A que empleado asignaras este animal?");
                            for (Empleado empleado : zoo.getEmpleados()) {
                                System.out.println(i + " - " + empleado.getNombre());
                                i++;
                            }
                            try {
                                while (Integer.parseInt(answer) > zoo.getEmpleados().size() || Integer.parseInt(answer) < 0) {
                                    answer = scan.next();

                                    if (Integer.parseInt(answer) > zoo.getEmpleados().size() || Integer.parseInt(answer) < 0) {
                                        System.out.println("Numero no valido");
                                    }
                                }

                                index2 = Integer.parseInt(answer);

                            } catch (NumberFormatException e) {
                                System.out.println("Error: " + e);
                                break;
                            }
                            Empleado empleadoSeleccionado = zoo.getEmpleados().get(index2 - 1);
                            animal.asignarCuidador(empleadoSeleccionado);

                        }

                        zoo.agregarAnimal(animal);
                        zoo.getHabitats().get(index - 1).agregarAnimal(animal);
                        i = 1;
                    }

                    break;

                case "2":
                    if (zoo.getAnimales().isEmpty()) {
                        System.out.println("No hay animales en el zoo.");
                    } else {
                        System.out.println("Selecciona el animal que deseas quitar:");
                        int i = 1;
                        for (Animal animals : zoo.getAnimales()) {
                            System.out.println(i + ". " + animals.getNombre());
                            i++;
                        }

                        answer = scan.next();
                        try {
                            int index = Integer.parseInt(answer) - 1;

                            if (index >= 0 && index < zoo.getAnimales().size()) {
                                Animal animalSeleccionado = zoo.getAnimales().get(index);
                                boolean encontrado = false;
                                for (Habitat habitat : zoo.getHabitats()) {
                                    if (habitat.getAnimales().contains(animalSeleccionado)) {
                                        habitat.eliminarAnimal(animalSeleccionado);
                                        System.out.println("Animal " + animalSeleccionado.getNombre() + " eliminado del hábitat " + habitat.getNombre() + ".");
                                        encontrado = true;
                                        break;
                                    }
                                }

                                zoo.eliminarAnimal(animalSeleccionado);
                                if (!encontrado) {
                                    System.out.println("Animal " + animalSeleccionado.getNombre() + " no estaba asignado a ningún hábitat.");
                                }
                                System.out.println("Animal " + animalSeleccionado.getNombre() + " eliminado del zoo.");
                            } else {
                                System.out.println("Número de animal no válido.");
                                answer = "";
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, ingresa un número válido.");
                            break;
                        }
                    }
                    break;

                case "3":
                    if (zoo.getAnimales().isEmpty()) {
                        System.out.println("No hay animales en el zoo.");
                    } else {
                        System.out.println("\nLista de Animales:");
                        for (Animal animals : zoo.getAnimales()) {
                            System.out.println("Nombre: " + animals.getNombre());
                            System.out.println("Especie: " + animals.getEspecie());
                            System.out.println("Salud: " + (animals.getSalud() - 5));
                            System.out.println("-------------------------");
                        }
                    }
                    break;

                case "4":
                    if (zoo.getHabitats().isEmpty()) {
                        System.out.println("No hay habitats disponibles.");
                    } else {
                        int i = 1;
                        for (Habitat habitat : zoo.getHabitats()) {
                            System.out.println(i + ". " + habitat.getNombre());
                            if (habitat.getAnimales().isEmpty()) {
                                System.out.println("No hay animales en este habitat.");
                            } else {
                                System.out.println("Animales en este habitat:");
                                for (Animal animals : habitat.getAnimales()) {
                                    System.out.println("   - " + animals.getNombre() + " (Especie: " + animals.getEspecie() + ")");
                                }
                            }
                            i++;
                        }
                    }
                    break;

                case "5":
                    Habitat habitat = new Habitat();
                    System.out.println("Introduce el nombre del habitat:");
                    answer = scan.next();
                    habitat.setNombre(answer);
                    System.out.println("Cuanto cuesta mantener este habitat?");
                    answer = scan.next();

                    try {
                        habitat.setCosteMantenimiento(Integer.valueOf(answer));
                        zoo.getHabitats().add(habitat);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: " + e);
                    }
                    break;

                case "6":
                    if (zoo.getHabitats().isEmpty()) {
                        System.out.println("No hay habitats en el zoo.");
                    } else {
                        System.out.println("Selecciona el habitat que deseas eliminar:");
                        int i = 1;
                        for (Habitat habitats : zoo.getHabitats()) {
                            System.out.println(i + ". " + habitats.getNombre());
                            i++;
                        }

                        answer = scan.next();
                        try {
                            int index = Integer.parseInt(answer) - 1;

                            if (index >= 0 && index < zoo.getHabitats().size()) {
                                Habitat habitatSeleccionado = zoo.getHabitats().get(index);
                                zoo.destruirHabitat(habitatSeleccionado);
                                System.out.println("Habitat " + habitatSeleccionado.getNombre() + " eliminado.");
                            } else {
                                System.out.println("Número de habitat no válido.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, ingresa un número válido.");
                        }
                    }
                    break;

                case "7":
                    if (zoo.getHabitats().isEmpty()) {
                        System.out.println("No hay habitats en el zoo.");
                    } else {
                        System.out.println("\nLista de Habitats:");
                        for (Habitat habitats : zoo.getHabitats()) {
                            System.out.println("Nombre: " + habitats.getNombre());
                            System.out.println("Coste de Mantenimiento: " + habitats.getCosteMantenimiento());
                            System.out.println("-------------------------");
                        }
                    }
                    break;

                case "8":
                    Cuidador cuidador = new Cuidador();
                    cuidador.setId(id);
                    System.out.println("Introduce el nombre del empleado:");
                    answer = scan.next();
                    cuidador.setNombre(answer);

                    System.out.println("Introduce la fecha de nacimiento (formato: dd/mm/yyyy):");
                    answer = scan.next();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);

                    try {
                        Date fechaNacimiento = sdf.parse(answer);
                        cuidador.setFecha(fechaNacimiento);
                        System.out.println("Fecha de nacimiento registrada: " + fechaNacimiento);
                    } catch (ParseException e) {
                        System.out.println("Error: El formato de fecha es incorrecto. Asegúrate de usar el formato dd/mm/yyyy.");
                    }

                    System.out.println("Introduce el sueldo del cuidador:");
                    answer = scan.next();

                    try {
                        cuidador.setSueldo(Integer.parseInt(answer));
                        zoo.getEmpleados().add(cuidador);
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: " + ex);
                    }
                    break;

                case "9":
                    if (zoo.getEmpleados().isEmpty()) {
                        System.out.println("No hay empleados en el zoo.");
                    } else {
                        System.out.println("Selecciona el empleado que deseas despedir:");
                        int i = 1;
                        for (Empleado empleado : zoo.getEmpleados()) {
                            System.out.println(i + ". " + empleado.getNombre());
                            i++;
                        }

                        answer = scan.next();
                        try {
                            int index = Integer.parseInt(answer) - 1;

                            if (index >= 0 && index < zoo.getEmpleados().size()) {
                                Empleado empleadoSeleccionado = zoo.getEmpleados().get(index);
                                zoo.despedirEmpleado(empleadoSeleccionado);
                                System.out.println("Empleado " + empleadoSeleccionado.getNombre() + " despedido.");
                            } else {
                                System.out.println("Número de empleado no válido.");
                                answer = "";
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, ingresa un número válido.");
                        }
                    }
                    break;
                case "10":
                    if (zoo.getEmpleados().isEmpty()) {
                        System.out.println("No hay empleados en el zoo.");
                    } else {
                        System.out.println("\nLista de Empleados:");
                        for (Empleado empleado : zoo.getEmpleados()) {
                            System.out.println("Nombre: " + empleado.getNombre());
                            System.out.println("Fecha: " + empleado.getFecha());
                            System.out.println("Sueldo: " + empleado.getSueldo());
                            System.out.println("-------------------------");
                        }
                    }
                    break;
                case "11":
                    Socio socio = new Socio();
                    NoSocio noSocio = new NoSocio();
                    socio.setId(id);
                    noSocio.setId(id);
                    System.out.println("Introduce el nombre del visitante:");
                    answer = scan.next();
                    socio.setNombre(answer);
                    noSocio.setNombre(answer);

                    System.out.println("Introduce la fecha de nacimiento (formato: dd/mm/yyyy):");
                    answer = scan.next();

                    sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);

                    try {
                        Date fechaNacimiento = sdf.parse(answer);
                        socio.setFecha(fechaNacimiento);
                        noSocio.setFecha(fechaNacimiento);
                        System.out.println("Fecha de nacimiento registrada: " + fechaNacimiento);
                    } catch (ParseException e) {
                        System.out.println("Error: El formato de fecha es incorrecto. Asegúrate de usar el formato dd/mm/yyyy.");
                    }

                    do {
                        System.out.println("Es el visitante socio?");
                        System.out.println("1 - Si");
                        System.out.println("2 - No");
                        answer = scan.next();
                    } while (!answer.equals("1") && !answer.equals("2"));

                    if (answer.equals("1")) {
                        zoo.getVisitantes().add(socio);
                    } else {
                        zoo.getVisitantes().add(noSocio);
                    }
                    break;

                case "12":
                    if (!zoo.getVisitantes().isEmpty()) {
                        int i = 1;
                        for (Visitante visitante : zoo.getVisitantes()) {
                            System.out.println(i + ". " + visitante.getNombre());
                            i++;
                        }

                        i = 1;

                        answer = scan.next();
                        try {
                            int index = Integer.parseInt(answer) - 1;

                            if (index >= 0 && index < zoo.getEmpleados().size()) {
                                Visitante visitanteSeleccionado = zoo.getVisitantes().get(index);
                                zoo.expulsarVisitante(visitanteSeleccionado);
                                System.out.println("Visitante " + visitanteSeleccionado.getNombre() + " expulsado.");
                            } else {
                                System.out.println("Número de visitante no válido.");
                                answer = "";
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, ingresa un número válido.");
                        }
                    } else {
                        System.out.println("No hay visitantes en el zoo.");
                    }
                    break;

                case "13":
                    if (zoo.getVisitantes().isEmpty()) {
                        System.out.println("No hay visitantes en el zoo.");
                    } else {
                        System.out.println("\nLista de Empleados:");
                        for (Visitante visitante : zoo.getVisitantes()) {
                            String soc = "";
                            boolean esSocio = false;
                            if (visitante.comprarEntrada() == 30) {
                                soc = "No";
                            } else {
                                soc = "Si";
                            }
                            System.out.println("Nombre: " + visitante.getNombre());
                            System.out.println("Fecha: " + visitante.getFecha());
                            System.out.println("Es socio?: " + soc);
                            System.out.println("-------------------------");
                        }
                    }
                    break;

                case "14":
                    System.out.println("Ingresos totales del zoo: " + zoo.calcularIngresos() + "€");
                    break;

                case "15":
                    if (zoo.getAnimales().isEmpty()) {
                        System.out.println("No hay animales en el zoo.");
                    } else {
                        System.out.println("Elige a que animal asignar cuidados:");
                        int i = 1;
                        ArrayList<Animal> animalesConEmpleado = new ArrayList<>();
                        for (Animal animals : zoo.getAnimales()) {
                            if (animals.getEmpleado() != null) {
                                animalesConEmpleado.add(animals);
                                System.out.println(i + ". " + animals.getNombre() + " (Empleado: " + animals.getEmpleado().getNombre() + ")");
                                i++;
                            }
                        }

                        if (animalesConEmpleado.isEmpty()) {
                            System.out.println("No hay animales con empleados asignados.");
                        } else {
                            try {
                                answer = scan.next();
                                int index = Integer.parseInt(answer) - 1;

                                if (index >= 0 && index < animalesConEmpleado.size()) {
                                    Animal animalSeleccionado = animalesConEmpleado.get(index);
                                    animalSeleccionado.realizarCuidados();
                                } else {
                                    System.out.println("Número de animal no válido.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Por favor, ingresa un número válido.");
                            }
                        }
                    }
                    break;
            }

            if (!zoo.getAnimales().isEmpty()) {
                ArrayList<Animal> animalesAEliminar = new ArrayList<>();

                for (Animal animal : new ArrayList<>(zoo.getAnimales())) {
                    animal.setSalud(animal.getSalud() - 5);

                    if (animal.getSalud() <= 0) {
                        System.out.println("El animal " + animal.getNombre() + " ha muerto.");
                        animalesAEliminar.add(animal);
                    } else if (animal.getSalud() <= 30) {
                        System.out.println("El animal " + animal.getNombre() + " necesita atención.");
                    }
                }
                
                for (Animal animal : animalesAEliminar) {
                    zoo.eliminarAnimal(animal);
                }
            }

            id++;
        } while (!answer.equals("0"));
    }
}
