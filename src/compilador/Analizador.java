/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kevin coto
 */
public class Analizador {

    int Palabras_reservadas = 0, Palabras_usuario = 0, Numeros = 0;
    int CorcheteApertura = 0, CorcheteCierre = 0, ParentesisApertura = 0,
            ParentesisCierre = 0, CuadradoApertura = 0, CuadradoCierre = 0, Operadores = 0,igualdad=0;
    String Nombre_Package = "";
    String Nombre_Class = "";

    // Se inicializa la lista de Variables globales
    ArrayList<VariablesGlobales> ListaVariables = new ArrayList<>();

    /*
 *  Metodo para analiza el txt entrante y dividirlo en linea por linea
 *
 * @param  el nombre del fichero analizar.
     */
    public void lectura(String fichero) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            int contador = 0;
            Validaciones validar = new Validaciones(fichero.replace(".txt", "_Errores.txt"));
            validar.Validacion_Orden(fichero);
            while ((linea = br.readLine()) != null) {
                validar.escribirLinea(fichero, linea, contador);
                // Salta las líneas que comienzan con "//" (comentarios)
                if (linea.startsWith("//") || linea.startsWith("/**") || linea.startsWith("*") || linea.startsWith("*/")) {
                    continue;
                }
                //Validacion de <80 caracteres 
                // Analiza sintácticamente la línea
                analizarSintacticamente(linea);
                // Clasifica la expresión de la línea
                ClasificarExpresion(linea, contador, fichero);
                contador++;
            }

            // Imprime las variables globales encontradas
            //Validacion de Corchetes iguales
            if (CorcheteApertura != CorcheteCierre) {
                System.out.println("----- ERROR -----, La cantidad de Corchetes de apertura y cierre no son la misma.");
            }
            // Imprime estadísticas
            System.out.println("                                               ");
            System.out.println("**************Estadisticas***************** ");
            System.out.println("Palabras reservadas => " + Palabras_reservadas);
            System.out.println("Palabras usuario => " + Palabras_usuario);
            System.out.println("Corchete Apertura => " + CorcheteApertura);
            System.out.println("Corchete Cierre => " + CorcheteCierre);
            System.out.println("Operadores Aritmeticos => " + Operadores);
            System.out.println("Parentesis Apertura => " + ParentesisApertura);
            System.out.println("Parentesis Cierre => " + ParentesisCierre);
            System.out.println("Cuadrado Apertura => " + CuadradoApertura);
            System.out.println("Cuadrado Cierre => " + CuadradoCierre);
            
            System.out.println("**************Variables***************** ");
            for (VariablesGlobales objeto : ListaVariables) {
                System.out.println("Nombre : " + objeto.getNombre() +" | Tipo : " + objeto.getTipo() );
            }

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
        boolean Clasificado = false;

        // Salir del método si la línea está en blanco
        if (linea.trim().isEmpty()) {
            return;
        }

        // Crear un StringTokenizer para dividir la línea en tokens
        StringTokenizer tokenizer = new StringTokenizer(linea);

        // Iterar a través de los tokens y mostrar cada uno
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            // Se llama al método que clasifica los Tokens
            ClasificacionToken(token, Clasificado);

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
                        // Incrementa el contador de palabras reservadas
                        Palabras_reservadas++;
                        Clasificado = true;
                        break;
                    case Numero:
                        // Incrementa el contador de números
                        Numeros++;
                        Clasificado = true;
                        break;
                    case Palabra:
                        // Incrementa el contador de palabras del usuario
                        Palabras_usuario++;
                        Clasificado = true;
                        break;
                    case CorcheteApetura:

                        CorcheteApertura++;
                        Clasificado = true;
                        break;
                    case CorcheteCierre:

                        CorcheteCierre++;
                        Clasificado = true;
                        break;
                    case ParentesisApertura:

                        ParentesisApertura++;
                        Clasificado = true;
                        break;
                    case ParentesisCierre:

                        ParentesisCierre++;
                        Clasificado = true;
                        break;
                    case CuadradosApertura:

                        CuadradoApertura++;
                        Clasificado = true;
                        break;
                    case CuadradosCierre:

                        CuadradoCierre++;
                        Clasificado = true;
                        break;
                    case Operadores:
                        Operadores++;
                        Clasificado = true;
                        break;
                    case igualdad:
                        igualdad++;
                        Clasificado = true;
                        break;
                    default:
                        System.out.println(Token + " >> no se clasificó en ninguna categoría");
                        break;
                }
            }
        }
    }

    private void ClasificarExpresion(String Expresion, int contador, String fichero) {
        boolean resultado = false;
        Expresion = Expresion.trim();
        Validaciones validacion = new Validaciones(fichero.replace(".txt", "_Errores.txt"));

        //Verifica que la expresion no supere 80  caracteres
        resultado = validacion.Validacion_80caracteres(Expresion, contador);
        // Verifica si la expresión contiene "package"
        if (Expresion.trim().contains("package")) {
            resultado = validacion.ValidarPackage(Expresion, contador);
            Nombre_Package = Expresion.substring(8, Expresion.length() - 1);
        }

        // Verifica si la expresión contiene "import"
        if (Expresion.trim().contains("import")) {
            resultado = validacion.ValidarImport(Expresion, contador);
        }

        // Verifica si la expresión es una declaración de "public class"
        if (Expresion.trim().toLowerCase().contains("public class")) {
            resultado = validacion.ValidarPublic_Class(Expresion, contador);
            Nombre_Class = Expresion.substring(13, Expresion.length() - 1);
            resultado = validacion.Validacion_Package_Main(contador, Nombre_Class, Nombre_Package);
        }

        // Verifica si la expresión comienza con "int"
        if (Expresion.trim().toLowerCase().startsWith("int")) {
            Pattern Patronint = Pattern.compile("int\\s+(\\w+)\\s*(=|;)");
            Matcher matcher = Patronint.matcher(Expresion);
            if (matcher.find()) {
                String nombreVariable = matcher.group(1);
                ListaVariables.add(new VariablesGlobales("int", nombreVariable));
            }
            resultado = validacion.ValidarINT(Expresion, contador);
        }

        // Verifica si la expresión comienza con "string" y procesa la declaración
        if (Expresion.trim().toLowerCase().startsWith("string")) {
            Pattern PatronString = Pattern.compile("String\\s+(\\w+)\\s*(=|;)");
            Matcher matcher = PatronString.matcher(Expresion);
            if (matcher.find()) {
                String nombreVariable = matcher.group(1);
                ListaVariables.add(new VariablesGlobales("String", nombreVariable));
            }
            resultado = validacion.ValidarString(Expresion, contador);
        }

        // Verifica si la expresión es una declaración de "public static void main"
        if (Expresion.startsWith("public") && (Expresion.contains("main") || Expresion.contains("static"))) {
            resultado = validacion.ValidarMain(Expresion, contador);
        }

        // Verifica si la expresión contiene "print", "println" o comienza con "System"
        if (Expresion.contains("print") || Expresion.contains("println") || Expresion.trim().startsWith("System")) {
            resultado = validacion.ValidarPrint(Expresion, contador, ListaVariables);
        }

        // Verifica si la expresión contiene "break"
        if (Expresion.startsWith("break")) {
            resultado = validacion.ValidarBreak(Expresion, contador);
        }
        // Verifica si la expresión contiene "switch"
        if (Expresion.startsWith("switch")) {
            resultado = validacion.ValidarSwitch(Expresion, contador, ListaVariables);
        }
        // Verifica si la expresión contiene "case"
        if (Expresion.startsWith("case")) {
            resultado = validacion.Validarcase(Expresion, contador);
        }
        // Verifica si la expresión es una declaración de "nextLine" o "nextInt"
        if (Expresion.contains("nextInt")) {
            resultado = validacion.ValidarNextScanner(Expresion, contador, ListaVariables);
        }
        // Verifica si la expresión contiene "Scanner"
        if (Expresion.contains("Scanner")) {
            Pattern patron = Pattern.compile("Scanner\\s+(\\w+)\\s*=\\s*.*");
            Matcher matcher = patron.matcher(Expresion);
            String nombreVariable = "";
            if (matcher.find()) {
                nombreVariable = matcher.group(1);
                // Aquí puedes usar nombreVariable como el nombre de la variable
                ListaVariables.add(new VariablesGlobales("Scanner", nombreVariable));
            }
            resultado = validacion.ValidarScanner(Expresion, contador, nombreVariable);
        }

        // Verifica si la expresión comienza con "boolean"
        if (Expresion.trim().toLowerCase().startsWith("boolean")) {
            Pattern Patronint = Pattern.compile("boolean\\s+(\\w+)\\s*(=|;)");
            Matcher matcher = Patronint.matcher(Expresion);
            if (matcher.find()) {
                String nombreVariable = matcher.group(1);
                ListaVariables.add(new VariablesGlobales("boolean", nombreVariable));
            }
            resultado = validacion.ValidarBoolean(Expresion, contador);
        }
        //Validar asignacion de variables
        for (VariablesGlobales variables : ListaVariables) {
            if (Expresion.startsWith(variables.getNombre()) && Expresion.contains("=") && (Expresion.contains("+") || Expresion.contains("-") || Expresion.contains("*")
                    || Expresion.contains("/"))) {
                validacion.ValidarAsignacion(Expresion, contador, ListaVariables);
            }
        }
    }

}
