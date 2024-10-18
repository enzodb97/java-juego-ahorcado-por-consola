package org.enzodb;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ahorcado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Intentar obtener una palabra aleatoria desde la API
        String palabraSecreta = obtenerPalabraAleatoria();
        if (palabraSecreta == null) {
            System.out.println("No se pudo obtener la palabra desde la API. Inténtalo más tarde.");
            return;
        }

        // Declaraciones y asignaciones
        int intentosMaximos = 5;
        int intentos = 0;
        boolean palabraAdivinada = false;

        // Arreglos:
        char[] letrasAdivinadas = new char[palabraSecreta.length()];

        // Inicializar con guiones bajos
        for (int i = 0; i < letrasAdivinadas.length; i++) {
            letrasAdivinadas[i] = '_';
        }

        // Bucle de juego
        while (!palabraAdivinada && intentos < intentosMaximos) {
            System.out.println("Palabra del diccionario en inglés a adivinar tiene : " + String.valueOf(letrasAdivinadas) + " (" + palabraSecreta.length() + " letras)");
            System.out.println("Introduce una letra: ");
            char letra = Character.toLowerCase(scanner.next().charAt(0));

            boolean letraCorrecta = false;

            // Revisar si la letra está en la palabra secreta
            for (int i = 0; i < palabraSecreta.length(); i++) {
                if (palabraSecreta.charAt(i) == letra) {
                    letrasAdivinadas[i] = letra;
                    letraCorrecta = true;
                }
            }

            if (!letraCorrecta) {
                intentos++;
                if (intentos >= intentosMaximos) {
                    break; // Salir si ya no quedan intentos
                }
                System.out.println("¡CASI! Un intento menos, te quedan " + (intentosMaximos - intentos) + " intentos.");
            }

            if (String.valueOf(letrasAdivinadas).equals(palabraSecreta)) {
                palabraAdivinada = true;
                System.out.println("¡EXCELENTE! Has adivinado la palabra secreta: " + palabraSecreta);
            }
        }

        if (!palabraAdivinada) {
            System.out.println("¡Estuviste cerca o no tanto quizás! FIN. Palabra secreta: " + palabraSecreta);
        }

        scanner.close();
    }

    // Método para obtener una palabra aleatoria desde la API
    public static String obtenerPalabraAleatoria() {
        String apiUrl = "https://random-word-api.herokuapp.com/word";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // El formato de respuesta es un JSON array, por ejemplo: ["alien"]
            String palabra = response.body().replace("[", "").replace("]", "").replace("\"", "");
            return palabra;
        } catch (Exception e) {
            System.out.println("Error al intentar obtener la palabra: " + e.getMessage());
            return null;
        }
    }
}


