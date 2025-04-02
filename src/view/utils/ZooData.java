/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import model.*;

/**
 *
 * @author iker-hurtado-caballo
 */
public class ZooData {

    public static String apiUrl = "http://172.17.40.25:8080/";

    public static ArrayList<Animal> getAnimalDataFromDatabase() {
        ArrayList<Animal> animales = new ArrayList<Animal>();

        try {
            URL url = new URL(apiUrl + "animales");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: " + conn.getResponseCode());
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder response = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

            String json = response.toString().replace("[", "").replace("]", "");
            String[] items = json.split("\\},\\{");

            for (String item : items) {
                item = item.replace("{", "").replace("}", "");

                String[] fields = item.split(",");
                String nombre = "", especie = "";
                int id = 0;
                int salud = 0;

                for (String field : fields) {
                    String[] keyValue = field.split(":");
                    String key = keyValue[0].replace("\"", "").trim();
                    String value = keyValue[1].replace("\"", "").trim();

                    switch (key) {
                        case "id":
                            id = Integer.parseInt(value);
                        case "nombre":
                            nombre = value;
                            break;
                        case "especie":
                            especie = value;
                            break;
                        case "salud":
                            salud = Integer.parseInt(value);
                            break;
                    }
                }
                animales.add(new Animal(id, nombre, especie, null, salud));
            }
            return animales;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Cuidador> getCuidadorDataFromDatabase() {
    ArrayList<Cuidador> cuidadores = new ArrayList<>();

    try {
        URL url = new URL(apiUrl + "cuidadores");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            System.out.println("Error: " + conn.getResponseCode());
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder response = new StringBuilder();
        String output;

        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        conn.disconnect();

        String jsonStr = response.toString();
        
        if (jsonStr.startsWith("[") && jsonStr.endsWith("]")) {
            jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
            
            ArrayList<String> jsonObjects = new ArrayList<>();
            int depth = 0;
            StringBuilder currentObject = new StringBuilder();
            
            for (int i = 0; i < jsonStr.length(); i++) {
                char c = jsonStr.charAt(i);
                
                if (c == '{') {
                    depth++;
                    currentObject.append(c);
                } else if (c == '}') {
                    depth--;
                    currentObject.append(c);
                    
                    if (depth == 0) {
                        jsonObjects.add(currentObject.toString());
                        currentObject = new StringBuilder();
                        
                        while (i + 1 < jsonStr.length() && (jsonStr.charAt(i + 1) == ',' || jsonStr.charAt(i + 1) == ' ')) {
                            i++;
                        }
                    }
                } else if (depth > 0) {
                    currentObject.append(c);
                }
            }
            
            for (String jsonObject : jsonObjects) {
                int id = 0;
                String nombre = "";
                Date fecha = null;
                double sueldo = 0;
                ArrayList<Animal> animales = new ArrayList<>();
                
                int fechaStartIndex = jsonObject.indexOf("\"fecha\":\"");
                if (fechaStartIndex != -1) {
                    fechaStartIndex += 9;
                    int fechaEndIndex = jsonObject.indexOf("\"", fechaStartIndex);
                    String fechaStr = jsonObject.substring(fechaStartIndex, fechaEndIndex);
                    try {
                        fecha = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(fechaStr);
                    } catch (Exception e) {
                    }
                }
                
                int idStartIndex = jsonObject.indexOf("\"id\":");
                if (idStartIndex != -1) {
                    idStartIndex += 5; 
                    int idEndIndex = jsonObject.indexOf(",", idStartIndex);
                    id = Integer.parseInt(jsonObject.substring(idStartIndex, idEndIndex).trim());
                }
                
                int nombreStartIndex = jsonObject.indexOf("\"nombre\":\"");
                if (nombreStartIndex != -1) {
                    nombreStartIndex += 10; 
                    int nombreEndIndex = jsonObject.indexOf("\"", nombreStartIndex);
                    nombre = jsonObject.substring(nombreStartIndex, nombreEndIndex);
                }
                
                int sueldoStartIndex = jsonObject.indexOf("\"sueldo\":");
                if (sueldoStartIndex != -1) {
                    sueldoStartIndex += 9; 
                    int sueldoEndIndex = jsonObject.indexOf(",", sueldoStartIndex);
                    if (sueldoEndIndex == -1) {
                        sueldoEndIndex = jsonObject.indexOf("}", sueldoStartIndex);
                    }
                    sueldo = Double.parseDouble(jsonObject.substring(sueldoStartIndex, sueldoEndIndex).trim());
                }
                
                int animalesStartIndex = jsonObject.indexOf("\"animales\":[");
                if (animalesStartIndex != -1) {
                    animalesStartIndex += 11;
                    
                    int animalesEndIndex = -1;
                    int bracketCount = 1;
                    for (int i = animalesStartIndex; i < jsonObject.length(); i++) {
                        if (jsonObject.charAt(i) == '[') {
                            bracketCount++;
                        } else if (jsonObject.charAt(i) == ']') {
                            bracketCount--;
                            if (bracketCount == 0) {
                                animalesEndIndex = i;
                                break;
                            }
                        }
                    }
                    
                    if (animalesEndIndex > animalesStartIndex) {
                        String animalesArray = jsonObject.substring(animalesStartIndex, animalesEndIndex);
                        
                        if (!animalesArray.trim().isEmpty()) {
                            ArrayList<String> animalObjects = new ArrayList<>();
                            depth = 0;
                            currentObject = new StringBuilder();
                            
                            for (int i = 0; i < animalesArray.length(); i++) {
                                char c = animalesArray.charAt(i);
                                
                                if (c == '{') {
                                    depth++;
                                    currentObject.append(c);
                                } else if (c == '}') {
                                    depth--;
                                    currentObject.append(c);
                                    
                                    if (depth == 0) {
                                        animalObjects.add(currentObject.toString());
                                        currentObject = new StringBuilder();
                                        
                                        while (i + 1 < animalesArray.length() && (animalesArray.charAt(i + 1) == ',' || animalesArray.charAt(i + 1) == ' ')) {
                                            i++;
                                        }
                                    }
                                } else if (depth > 0) {
                                    currentObject.append(c);
                                }
                            }
                            
                            for (String animalObject : animalObjects) {
                                int animalId = 0;
                                String animalNombre = "";
                                String especie = "";
                                int salud = 0;
                                
                                int animalIdStartIndex = animalObject.indexOf("\"id\":");
                                if (animalIdStartIndex != -1) {
                                    animalIdStartIndex += 5; // Length of "id":
                                    int animalIdEndIndex = animalObject.indexOf(",", animalIdStartIndex);
                                    animalId = Integer.parseInt(animalObject.substring(animalIdStartIndex, animalIdEndIndex).trim());
                                }
                                
                                int animalNombreStartIndex = animalObject.indexOf("\"nombre\":\"");
                                if (animalNombreStartIndex != -1) {
                                    animalNombreStartIndex += 10;
                                    int animalNombreEndIndex = animalObject.indexOf("\"", animalNombreStartIndex);
                                    animalNombre = animalObject.substring(animalNombreStartIndex, animalNombreEndIndex);
                                }
                                
                                int especieStartIndex = animalObject.indexOf("\"especie\":\"");
                                if (especieStartIndex != -1) {
                                    especieStartIndex += 11; // Length of "especie":"
                                    int especieEndIndex = animalObject.indexOf("\"", especieStartIndex);
                                    especie = animalObject.substring(especieStartIndex, especieEndIndex);
                                }
                                
                                int saludStartIndex = animalObject.indexOf("\"salud\":");
                                if (saludStartIndex != -1) {
                                    saludStartIndex += 8; // Length of "salud":
                                    int saludEndIndex = animalObject.indexOf("}", saludStartIndex);
                                    salud = Integer.parseInt(animalObject.substring(saludStartIndex, saludEndIndex).trim());
                                }
                                
                                Animal animal = new Animal(animalId, animalNombre, especie, null, salud);
                                animales.add(animal);
                            }
                        }
                    }
                }
                
                Cuidador cuidador = new Cuidador(nombre, id, fecha, (int)sueldo, animales);
                cuidadores.add(cuidador);
            }
        }
        
        return cuidadores;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}

    public static Zoo zoo = new Zoo(new ArrayList<Habitat>(), new ArrayList<Animal>(getAnimalDataFromDatabase()), new ArrayList<Empleado>(getCuidadorDataFromDatabase()), new ArrayList<Visitante>());
}
