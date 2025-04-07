/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
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

    public static ArrayList<Habitat> getHabitatDataFromDatabase() {
        ArrayList<Habitat> habitats = new ArrayList<Habitat>();

        try {
            URL url = new URL(apiUrl + "habitats");
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
                int id = 0;
                String nombre = "";
                int costeMantenimiento = 0;

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
                        case "coste":
                            costeMantenimiento = Integer.parseInt(value);
                            break;
                    }
                }
                habitats.add(new Habitat(id, nombre, costeMantenimiento));
            }
            return habitats;

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
                            e.printStackTrace();
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
                        animalesStartIndex += 12;

                        int animalesEndIndex = -1;
                        int bracketCount = 1;
                        for (int i = animalesStartIndex; i < jsonObject.length(); i++) {
                            char c = jsonObject.charAt(i);
                            if (c == '[') {
                                bracketCount++;
                            } else if (c == ']') {
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
                                int animalDepth = 0;
                                StringBuilder animalObject = new StringBuilder();

                                for (int i = 0; i < animalesArray.length(); i++) {
                                    char c = animalesArray.charAt(i);

                                    if (c == '{') {
                                        animalDepth++;
                                        animalObject.append(c);
                                    } else if (c == '}') {
                                        animalDepth--;
                                        animalObject.append(c);

                                        if (animalDepth == 0) {
                                            animalObjects.add(animalObject.toString());
                                            animalObject = new StringBuilder();

                                            while (i + 1 < animalesArray.length() && (animalesArray.charAt(i + 1) == ',' || animalesArray.charAt(i + 1) == ' ')) {
                                                i++;
                                            }
                                        }
                                    } else if (animalDepth > 0) {
                                        animalObject.append(c);
                                    }
                                }

                                for (String animalJson : animalObjects) {
                                    int animalId = 0;
                                    String animalNombre = "";
                                    String especie = "";
                                    int salud = 0;

                                    int animalIdStartIndex = animalJson.indexOf("\"id\":");
                                    if (animalIdStartIndex != -1) {
                                        animalIdStartIndex += 5;
                                        int animalIdEndIndex = animalJson.indexOf(",", animalIdStartIndex);
                                        animalId = Integer.parseInt(animalJson.substring(animalIdStartIndex, animalIdEndIndex).trim());
                                    }

                                    int animalNombreStartIndex = animalJson.indexOf("\"nombre\":\"");
                                    if (animalNombreStartIndex != -1) {
                                        animalNombreStartIndex += 10;
                                        int animalNombreEndIndex = animalJson.indexOf("\"", animalNombreStartIndex);
                                        animalNombre = animalJson.substring(animalNombreStartIndex, animalNombreEndIndex);
                                    }

                                    int especieStartIndex = animalJson.indexOf("\"especie\":\"");
                                    if (especieStartIndex != -1) {
                                        especieStartIndex += 11;
                                        int especieEndIndex = animalJson.indexOf("\"", especieStartIndex);
                                        especie = animalJson.substring(especieStartIndex, especieEndIndex);
                                    }

                                    int saludStartIndex = animalJson.indexOf("\"salud\":");
                                    if (saludStartIndex != -1) {
                                        saludStartIndex += 8;
                                        int saludEndIndex;
                                        if (animalJson.indexOf(",", saludStartIndex) != -1) {
                                            saludEndIndex = animalJson.indexOf(",", saludStartIndex);
                                        } else {
                                            saludEndIndex = animalJson.indexOf("}", saludStartIndex);
                                        }
                                        salud = Integer.parseInt(animalJson.substring(saludStartIndex, saludEndIndex).trim());
                                    }

                                    Animal animal = new Animal(animalId, animalNombre, especie, null, salud);
                                    animales.add(animal);
                                }
                            }
                        }
                    }

                    Cuidador cuidador = new Cuidador(nombre, id, fecha, (int) sueldo, animales);
                    cuidadores.add(cuidador);
                }
            }

            return cuidadores;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void realizarCuidados(int id) {
        try {
            URL url = new URL(apiUrl + "animales/cuidados/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al realizar cuidados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void guardarCuidador(String nombre, Date fecha, double sueldo) {
        try {
            URL url = new URL(apiUrl + "cuidadores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = dateFormat.format(fecha);

            String jsonInputString = "{"
                    + "\"nombre\": \"" + nombre + "\","
                    + "\"fecha\": \"" + fechaFormateada + "\","
                    + "\"sueldo\": " + sueldo + ","
                    + "\"animales\": []"
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode >= 400) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Error response: " + response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Success response: " + response.toString());
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al guardar cuidador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void guardarHabitat(String nombre, int costeMantenimiento) {
        try {
            URL url = new URL(apiUrl + "habitats");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{"
                    + "\"nombre\": \"" + nombre + "\","
                    + "\"coste\": " + costeMantenimiento
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode >= 400) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Error response: " + response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Success response: " + response.toString());
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al guardar hábitat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void guardarAnimal(String nombre, String especie, int cuidadorId) {
        try {
            URL url = new URL(apiUrl + "animales");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString;
            if (cuidadorId != 0) {
                jsonInputString = "{"
                        + "\"nombre\": \"" + nombre + "\","
                        + "\"especie\": \"" + especie + "\","
                        + "\"salud\": 100,"
                        + "\"cuidador\": { \"id\": " + cuidadorId + " }"
                        + "}";
            } else {
                jsonInputString = "{"
                        + "\"nombre\": \"" + nombre + "\","
                        + "\"especie\": \"" + especie + "\","
                        + "\"salud\": 100,"
                        + "\"cuidador\": null"
                        + "}";
            }

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode >= 400) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Error response: " + response.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Success response: " + response.toString());
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al guardar animal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void borrarAnimal(int id) {
        try {
            URL url = new URL(apiUrl + "animales/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al borrar el animal: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
        public static void borrarEmpleado(int id) {
        try {
            URL url = new URL(apiUrl + "cuidadores/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al borrar el animal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Zoo zoo = new Zoo(new ArrayList<Habitat>(getHabitatDataFromDatabase()), new ArrayList<Animal>(getAnimalDataFromDatabase()), new ArrayList<Empleado>(getCuidadorDataFromDatabase()), new ArrayList<Visitante>());
}
