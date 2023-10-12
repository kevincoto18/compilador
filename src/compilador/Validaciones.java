/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kevin
 */
public class Validaciones {

    private String archivoErrores;

    public Validaciones(String archivoErrores) {
        this.archivoErrores = archivoErrores;
         //redirectSystemOutToFile(archivoErrores); // Redirige System.out a tu archivo de errores
    }

    public void escribirLinea(String nombreArchivo, String linea, int contador) {
        try {
            nombreArchivo = nombreArchivo.replace(".txt", "_Errores.txt");
            FileWriter escritor = new FileWriter(nombreArchivo, true);  // El segundo parámetro true permite agregar al archivo existente

            escritor.write(String.format("%04d", contador) + " - " + linea + "\n");  // Agrega la línea al archivo

            escritor.close();  // Cierra el archivo después de escribir

            // System.out.println("Línea escrita en el archivo " + nombreArchivo);
        } catch (IOException e) {
            // System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    //Validaciones Generales
    public boolean Validacion_80caracteres(String Linea, int contador) {
        try {
            String Error = "";
            if (Linea.replaceAll(" ", "").length() >= 80) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + " supera mas de 80 caracteres");
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // aqui se validara le orden del package, imports, y el main
   public boolean Validacion_Orden(String fichero) {
    try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
        String Linea = "";
        int continuidad = 0;
        boolean bloqueComentario = false; // Variable para rastrear si estamos dentro de un comentario de bloque

        while ((Linea = br.readLine()) != null) {
            Linea = Linea.trim(); // Elimina espacios en blanco al inicio y al final

            if (bloqueComentario) {
                if (Linea.contains("*/")) {
                    bloqueComentario = false;
                }
                continue;
            }

            if (Linea.startsWith("/*")) {
                bloqueComentario = true;
                continue;
            }

            if (Linea.startsWith("//")) {
                continue; // Saltar líneas de comentarios de una sola línea
            }

            if (Linea.isEmpty()) {
                continue; // Saltar líneas en blanco
            }

            switch (continuidad) {
                case 0:
                    if (Linea.contains("package")) {
                        continuidad = 1;
                    } else {
                        System.out.println("Error General, verifica el orden de las sentencias, 01");
                        return false;
                    }
                    break;
                case 1:
                    if (Linea.contains("import")) {
                        continuidad = 2;
                    } else {
                        System.out.println("Error General, verifica el orden de las sentencias, 02");
                        return false;
                    }
                    break;
                case 2:
                    if (Linea.contains("class")) {
                        continuidad = 3;
                    } else {
                        System.out.println("Error General, verifica el orden de las sentencias, 03");
                        return false;
                    }
                    break;
                case 3:
                    if (Linea.contains("main")) {
                        // El orden es correcto
                        return true;
                    } else {
                        System.out.println("Error General, verifica el orden de las sentencias, 04");
                        return false;
                    }
                default:
                    break;
            }
        }
    } catch (Exception e) {
        return false;
    }
    return true;
}


    public boolean Validacion_Package_Main(int contador, String Pack, String clase) {
        clase = clase.trim();  // Elimina espacios en blanco alrededor de la clase
        Pack = Pack.trim();    // Elimina espacios en blanco alrededor del package

        if (!clase.equals(Pack)) {
            System.out.println("----- ERROR -----, en la línea " + contador + ", el public class y el package no son iguales.");
            return false;
        } else {
            return true;
        }
    }

    public boolean ValidarPackage(String Linea, int contador) {
        try {
            // Extrae el nombre del package eliminando el "package " y el ";"
            String packagename = Linea.substring(8, Linea.length() - 1).trim();

            // Verifica si la línea termina con un punto y coma (;)
            if (!Linea.endsWith(";")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }

            // Verifica si el nombre del package contiene solo letras y espacios
            if (!packagename.matches("[a-zA-Z ]+")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + ", el nombre del package debe contener solo letras y espacios.");
                return false;
            }

            // Verifica si el nombre del package no contiene números ni caracteres especiales
            if (packagename.matches(".*[0-9@#$%^&*!].*")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + ", el nombre del package NO debe contener números ni caracteres especiales.");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean ValidarImport(String Linea, int contador) {
        try {
            // Verifica si la línea termina con un punto y coma (;)
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
                return false;
            }

            // Divide la línea en partes usando espacios como separadores
            String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");

            // Deben existir al menos dos partes: "import" y el nombre
            if (partes.length < 2 || !partes[0].equals("import")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", el import debe contener un nombre valido.");
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ValidarPublic_Class(String Linea, int contador) {

        try {

            if (!Linea.contains("class")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + " Falta el class para la expresion public class");
                return false;
            }
            if (!Linea.contains("public")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + " Falta el public para la expresion public class");
                return false;
            }
            if (!Linea.endsWith("{")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el { al final de la expresion.");
                return false;
            }
            if (!Linea.strip().startsWith("public class")) {
                System.out.println("Warning , en la linea " + formatearContador(contador) + " Java es sensible a minusculas y mayusculas, quisiste decir: public class?");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ValidarMain(String Linea, int contador) {
        try {
            // Verificar si la línea inicia con "public"
            if (!Linea.startsWith("public")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el `public` al principio de la expresion.");
                return false;
            }

            // Verificar si la línea contiene "static"
            if (!Linea.contains("static")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + " Falta el 'static' en la expresion.");
                return false;
            }

            // Verificar si la línea contiene "void"
            if (!Linea.contains("void")) {
                System.out.println(" Error , en la linea " + formatearContador(contador) + " Falta el 'void' en la expresion.");
                return false;
            }

            // Verificar si la línea contiene paréntesis
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta algun parentesis en la expresion.");
                return false;
            }

            // Verificar si la línea contiene "String[] args"
            if (!Linea.contains("String[] args")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta `String[] args` en la expresion.");
                return false;
            }

            // Verificar si la línea termina con una llave abierta "{"
            if (!Linea.endsWith("{")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el { al final de la expresion.");
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ValidarPrint(String Linea, int contador, ArrayList<VariablesGlobales> ListaVariables) {
        try {
            // Verificar si la línea inicia con "System"
            if (!Linea.startsWith("System")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta el `System` al principio de la expresión.");
                return false;
            }

            // Verificar si la línea contiene ".out"
            if (!Linea.contains(".out")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta el `.out` en la expresión.");
                return false;
            }

            // Verificar si la línea contiene ".print" o ".println"
            if (!Linea.contains(".print") && !Linea.contains(".println")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta el `.println` o `.print` en la expresión.");
                return false;
            }

            // Verificar si la línea contiene paréntesis
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta algún paréntesis en la expresión.");
                return false;
            }

            // Extraer el contenido entre paréntesis
            String contenido = Linea.substring(Linea.indexOf("(") + 1, Linea.lastIndexOf(")")).trim();

            // Dividir el contenido en partes separadas por el símbolo '+'
            String[] partes = contenido.split("\\+");

            for (String parte : partes) {
                parte = parte.trim();

                if (parte.startsWith("\"") && parte.endsWith("\"")) {
                    // La parte es texto entre comillas, se considera válida
                    continue;
                }

                // Verificar si la parte es una variable válida
                boolean variableEncontrada = false;

                for (VariablesGlobales variable : ListaVariables) {
                    if (variable.getNombre().equals(parte)) {
                        variableEncontrada = true;
                        break;
                    }
                }

                if (!variableEncontrada) {
                    System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + ", variable no válida o falta comillas.");
                    return false;
                }
            }

            // Verificar si la línea termina con punto y coma
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta el ';' al final de la expresión.");
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Validacion de Scanner 
    public boolean ValidarScanner(String Linea, int contador, String VariableScanner) {
        try {
            // Verificar si la línea inicia con "System"
            if (Linea.startsWith("import")) {
                return true;
            }
            if (!Linea.startsWith("Scanner")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el `Scanner` al principio de la expresion.");
                return false;
            }
            if (!Linea.contains("new") || !Linea.contains("=")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta `= new` en la expresion.");
                return false;
            }
            Pattern pattern = Pattern.compile("new\\s+Scanner");

            // Crea un objeto Matcher para buscar coincidencias
            Matcher matcher = pattern.matcher(Linea);

            // Comprueba si se encuentra la coincidencia "new Scanner"
            if (!matcher.find()) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta Scanner en la expresion.");
                return false;
            }
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta parentesis en la expresion.");
                return false;
            }
            // Verificar si la línea contiene un punto y coma al final
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Validar expresion Break;
    public boolean ValidarBreak(String Linea, int contador) {
        try {
            // Verificar si la línea inicia con "System"
            if (!Linea.startsWith("break")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " Java es sensible a mayúsculas y minúsculas, ¿quisiste decir: 'break'?");
                return false;
            }
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //Validar expresion Break;

    public boolean ValidarAsignacion(String Linea, int contador, ArrayList<VariablesGlobales> ListaVariables) {
        try {
            String TipoVariable = "";
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Validar expresion Switch;
    public boolean ValidarSwitch(String Linea, int contador, ArrayList<VariablesGlobales> ListaVariables) {
        try {
            if (!Linea.startsWith("switch")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " Java es sensible a mayúsculas y minúsculas, ¿quisiste decir: 'switch'?");
                return false;
            }
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta paréntesis en la expresión.");
                return false;
            }

            if (!Linea.endsWith("{")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta el '{' al final de la expresión.");
                return false;
            }

            int inicioParentesis = Linea.indexOf("(");
            int finParentesis = Linea.indexOf(")");
            String contenido = Linea.substring(inicioParentesis + 1, finParentesis).trim();

            boolean variableEncontrada = false;
            for (VariablesGlobales variable : ListaVariables) {
                if (contenido.contains(variable.getNombre())) {
                    variableEncontrada = true;
                    break; // Terminar el bucle cuando se encuentre la variable
                }
            }

            if (!variableEncontrada) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " variable no encontrada o no declarada previamente.");
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ValidarNextScanner(String Linea, int contador, ArrayList<VariablesGlobales> ListaVariables) {
        try {
            // Verificar si la línea termina con un punto y coma (;)
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " falta el ';' al final de la expresión.");
                return false;
            }

            // Obtener el nombre de la variable de la línea (lo que está antes del '=')
            String[] partes = Linea.split("=");
            if (partes.length != 2) {
                System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " expresión no valida.");
                return false;
            }
            String nombreVariable = partes[0].trim();
            String variableScanner;
            // Buscar la variable en ListaVariables
            for (VariablesGlobales variable : ListaVariables) {

                if ("Scanner".equals(variable.getTipo())) {
                    variableScanner = variable.getNombre();
                    // Se valida que contenga la variable inicializada en Scanner variable = new Scanner();
                    if (!Linea.contains(variableScanner)) {
                        System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " variable Scanner no encontrada o no inicializada.");
                        return false;
                    }
                }
                if (variable.getNombre().equals(nombreVariable)) {
                    // Verificar el tipo de la variable y el método Scanner utilizado
                    if ("int".equals(variable.getTipo())) {
                        if (Linea.contains("nextInt()")) {
                            return true; // Es una variable tipo int y se usa nextInt()
                        }
                    } else if ("String".equals(variable.getTipo())) {
                        if (Linea.contains("nextLine()")) {
                            return true; // Es una variable tipo String y se usa nextLine()
                        }
                    }
                    System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " método Scanner no válido para el tipo de variable.");
                    return false;
                }
            }

            System.out.println("----- ERROR -----, en la línea " + formatearContador(contador) + " variable no encontrada, verifica que se haya declarado.");
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Validacion de Variables --------------------------------------------------------------------------------------------------------------------
    public boolean ValidarINT(String Linea, int contador) {
        try {
            // Verificar si la línea comienza con "int" (case-sensitive)
            if (!Linea.startsWith("int")) {
                System.out.println("Warning , en la linea " + formatearContador(contador) + " Java es sensible a mayúsculas y minúsculas, ¿quisiste decir: 'int'?");
                return false;
            }

            // Verificar si la línea contiene un punto y coma al final
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }

            // Dividir la línea en partes usando espacios en blanco como separadores
            String[] partes = Linea.trim().split("\\s+");

            // Verificar si hay suficientes partes (deben ser al menos 2: "int" y el nombre de la variable)
            if (partes.length < 2) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", la declaracion de variables debe contener un nombre valido.");
                return false;
            }

            // Verificar si hay asignación de valor (debe haber al menos 3 partes: "int", nombre, "=")
            if (partes.length >= 3) {
                // Verificar si la última parte contiene "=" y es seguida por un número
                String ultimaParte = partes[partes.length - 1];
                if (ultimaParte.contains("=") && !ultimaParte.matches(".*=.*[0-9].*")) {
                    System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", si se desea asignar un valor, se requiere que sea un numero.");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ValidarString(String Linea, int contador) {
        try {

            String VariableCompleta = Linea.trim().substring(7, Linea.length() - 1);
            if (!Linea.contains("String")) {
                System.out.println("Warning , en la linea " + formatearContador(contador) + " Java es sensible a minusculas y mayusculas, quisiste decir: String?");

                return false;
            }
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
                return false;
            }
            String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");
            if (partes.length < 2 || !partes[0].equals("String")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", la declaracion de variables debe contener un nombre valido.");
                return false;
            }

            if (VariableCompleta.matches(".*[=].*")) {
                if (!VariableCompleta.matches(".*=\\s*\"[^\"]*\".*")) {
                    System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", si se desea asignar un valor, se requiere tiene que ir entre comillas");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ValidarBoolean(String Linea, int contador) {
        try {
            String NombreVariable = Linea.substring(8, Linea.length() - 1);
            if (!Linea.startsWith("boolean")) {
                System.out.println("Warning , en la linea " + formatearContador(contador) + " Java es sensible a minusculas y mayusculas, quisiste decir: boolean?");

                return false;
            }
            if (!Linea.endsWith(";")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
                return false;
            }
            String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");
            if (partes.length < 2 || !partes[0].equals("boolean")) {
                System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", la declaracion de variables debe contener un nombre valido.");
                return false;
            }

            if (NombreVariable.matches(".*[=].*")) {
                if (!NombreVariable.matches(".*=\\s*(true|false).*")) {
                    System.out.println("----- ERROR -----, en la linea " + formatearContador(contador) + ", los boleanos tiene que ser true o false");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

// Método auxiliar para formatear el contador como "0001", "0002", etc.
    private String formatearContador(int contador) {
        return String.format("%04d", contador);
    }

    private void redirectSystemOutToFile(String archivoErrores) {
        try {
            PrintStream fileOut = new PrintStream(new FileOutputStream(archivoErrores, true));
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            //System.err.println("Error al redirigir System.out a archivo: " + e.getMessage());
        }
    }

}
