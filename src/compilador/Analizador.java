/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author kevin
 */
public class Analizador {

    int Palabras_reservadas = 0, Palabras_usuario = 0, Numeros = 0, Parentesis = 0;

    /*
 *  Metodo para analiza el txt entrante y dividirlo en linea por linea
 *
 * @param  el nombre del fichero analizar.
     */
    public void lectura(String fichero) {

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                // Llama al método analizarSintacticamente para procesar la línea
                analizarSintacticamente(linea);
                ClasificarExpresion(linea, contador);
                
                contador++;
            }
            System.out.println("**************Estadisticas***************** ");
            System.out.println("Palabras reservadas => " + Palabras_reservadas);
            System.out.println("Palabras usuario => " + Palabras_usuario);
            System.out.println("Parentesis => " + Parentesis);

        } catch (FileNotFoundException e) {
            // Maneja el caso en el que el archivo no se encuentra
            System.err.println("El archivo '" + fichero + "' no se encontró.");
        } catch (IOException e) {
            // Maneja otros errores de lectura de archivo
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /*
 *  Metodo para analiza una línea de texto y extrae y muestra cada token.
 *
 * @param  La línea de texto a analizar.
     */
    public void analizarSintacticamente(String linea) {

        // Variables a utilizar
        int contador = 0;
        String Expresion = "";

        boolean Clasificado = false, PalabraEncontrada = false;

        if (linea.trim().isEmpty()) {
            return; // Salir del método si la línea está en blanco
        }
        // Crear un StringTokenizer para dividir la línea en tokens
        StringTokenizer tokenizer = new StringTokenizer(linea);

        // Iterar a través de los tokens y mostrar cada uno
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            // Se llama el metodo que clasifica los Tokens
            ClasificacionToken(token, Clasificado);

            if (contador == 0) {
                Expresion = Expresion + token;
            } else {
                Expresion = Expresion + " " + token;
            }
            contador++;
        }

       

    }

    /*
     * Clasifica un token en diferentes categorías.
     *
     * @param Token La palabra a clasificar
     * @param Clasificado Un booleano que indica si el token ya ha sido
     * clasificado previamente
     */
    private void ClasificacionToken(String Token, boolean Clasificado) {
        //variables de Tokens

        // Recorre todas las categorías de tokens en la tabla de sintaxis
        for (TablaSintaxis.Tokens match : TablaSintaxis.Tokens.values()) {
            // Verifica si el token ya ha sido clasificado
            if (Clasificado) {
                Clasificado = false; // Restablece la marca de clasificación
                break; // Sale del bucle si ya está clasificado
            }

            // Comprueba si el token coincide con el patrón de la categoría actual
            if (Token.matches(match.patron)) {
                // Clasifica el token en función de su categoría
                switch (match) {
                    case Reservada:
                        //System.out.println(Token + " >> es una palabra reservada");
                        Palabras_reservadas++;
                        Clasificado = true;
                        break;
                    case Numero:
                        // System.out.println(Token + " >> es un numero");
                        Numeros++;
                        Clasificado = true;
                        break;
                    case Palabra:
                        //System.out.println(Token + " >> es una palabra del usuario");
                        Palabras_usuario++;
                        Clasificado = true;
                        break;
                    case Parentesis:
                        // System.out.println(Token + " >> es un parentesis");
                        Parentesis++;
                        Clasificado = true;
                        break;
                    default:
                        System.out.println(Token + " >> no se clasifico en ninguna categoria");
                        break;
                }
            }
        }
    }

    private void ClasificarExpresion(String Expresion, int contador) {
        boolean resultado = false;
        Validaciones validacion = new Validaciones();
        if (Expresion.contains("package")) {
            resultado = validacion.ValidarPackage(Expresion, contador);
//            if (resultado) {
//                System.out.println("Todo correcto");
//            }
        }
        if (Expresion.contains("import")) {
            resultado = validacion.ValidarImport(Expresion, contador);
//            if (resultado) {
//                System.out.println("Todo correcto");
//            }
        }
    }

}
