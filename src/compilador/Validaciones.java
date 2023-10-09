/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kevin
 */
public class Validaciones {

    //Validaciones Generales
    public boolean Validacion_80caracteres(String Linea, int contador) {
        try {
            if (Linea.replaceAll(" ", "").length() >= 80) {
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " supera mas de 80 caracteres");
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean Validacion_Package_Main(int contador, String Pack, String clase) {

        if (!clase.contains(Pack)) {
            System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + contador + ", el public class y el package no son iguales. ");
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
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }

            // Verifica si el nombre del package contiene solo letras y espacios
            if (!packagename.matches("[a-zA-Z ]+")) {
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", el nombre del package debe contener solo letras y espacios.");
                return false;
            }

            // Verifica si el nombre del package no contiene números ni caracteres especiales
            if (packagename.matches(".*[0-9@#$%^&*!].*")) {
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", el nombre del package NO debe contener números ni caracteres especiales.");
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
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
                return false;
            }

            // Divide la línea en partes usando espacios como separadores
            String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");

            // Deben existir al menos dos partes: "import" y el nombre
            if (partes.length < 2 || !partes[0].equals("import")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", el import debe contener un nombre valido.");
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
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " Falta el class para la expresion public class");
                return false;
            }
            if (!Linea.contains("public")) {
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " Falta el public para la expresion public class");
                return false;
            }
            if (!Linea.endsWith("{")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el { al final de la expresion.");
                return false;
            }
            if (!Linea.strip().startsWith("public class")) {
                System.out.println("\u001B[33mWarning \u001B[0m, en la linea " + formatearContador(contador) + " Java es sensible a minusculas y mayusculas, quisiste decir: public class?");
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
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el `public` al principio de la expresion.");
                return false;
            }

            // Verificar si la línea contiene "static"
            if (!Linea.contains("static")) {
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " Falta el 'static' en la expresion.");
                return false;
            }

            // Verificar si la línea contiene "void"
            if (!Linea.contains("void")) {
                System.out.println("\u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " Falta el 'void' en la expresion.");
                return false;
            }

            // Verificar si la línea contiene paréntesis
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta algun parentesis en la expresion.");
                return false;
            }

            // Verificar si la línea contiene "String[] args"
            if (!Linea.contains("String[] args")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta `String[] args` en la expresion.");
                return false;
            }

            // Verificar si la línea termina con una llave abierta "{"
            if (!Linea.endsWith("{")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el { al final de la expresion.");
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
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el `System` al principio de la expresion.");
                return false;
            }
            // Verificar si la línea contiene ".out"
            if (!Linea.contains(".out")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el `.out` en la expresion.");
                return false;
            }
            // Verificar si la línea contiene ".print" o ".println"
            if (!Linea.contains(".print") && !Linea.contains(".println")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el `.println` o `.print` en la expresion.");
                return false;
            }
            // Verificar si la línea contiene paréntesis
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta algun parentesis en la expresion.");
                return false;
            }
            // Obtener el mensaje entre paréntesis
            String mensaje = Linea.substring(Linea.indexOf("(") + 1, Linea.lastIndexOf(")"));

            // Verificar si el mensaje es una variable válida o una concatenación válida
            boolean mensajeEncontrado = false;
            if (!mensaje.isEmpty()) {
                if (mensaje.startsWith("\"") && mensaje.endsWith("\"")) {
                    // El mensaje está entre comillas, es válido
                    mensajeEncontrado = true;
                } else {
                    // Verificar si el mensaje es una variable válida
                    for (VariablesGlobales variable : ListaVariables) {
                        if (variable.getNombre().equals(mensaje)) {
                            mensajeEncontrado = true;
                            break;
                        }
                    }
                }
            } else {
                // Si el mensaje está vacío, es una concatenación válida
                mensajeEncontrado = false;
            }

            if (!mensajeEncontrado) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", variable no valida o faltan comillas.");
                return false;
            }
            // Verificar si la línea termina con un punto y coma (;)
            if (!Linea.endsWith(";")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
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
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el `Scanner` al principio de la expresion.");
                return false;
            }
            if (!Linea.contains("new") || !Linea.contains("=")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta `= new` en la expresion.");
                return false;
            }
            Pattern pattern = Pattern.compile("new\\s+Scanner");

            // Crea un objeto Matcher para buscar coincidencias
            Matcher matcher = pattern.matcher(Linea);

            // Comprueba si se encuentra la coincidencia "new Scanner"
            if (!matcher.find()) {
               System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta Scanner en la expresion.");
                return false;
            }
            if (!Linea.contains("(") || !Linea.contains(")")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta parentesis en la expresion.");
                return false;
            }
            // Verificar si la línea contiene un punto y coma al final
            if (!Linea.endsWith(";")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //Validar expresion Break;
        // Validacion de Scanner 
    public boolean ValidarBreak(String Linea, int contador) {
        try {
            // Verificar si la línea inicia con "System"
            if (!Linea.startsWith("break")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " Java es sensible a mayúsculas y minúsculas, ¿quisiste decir: 'break'?");
                return false;
            }
            if (!Linea.endsWith(";")) {
               System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Validacion de Variables --------------------------------------------------------------------------------------------------------------------
    public boolean ValidarINT(String Linea, int contador) {
        try {
            // Verificar si la línea comienza con "int" (case-sensitive)
            if (!Linea.startsWith("int")) {
                System.out.println("\u001B[33mWarning \u001B[0m, en la linea " + formatearContador(contador) + " Java es sensible a mayúsculas y minúsculas, ¿quisiste decir: 'int'?");
                return false;
            }

            // Verificar si la línea contiene un punto y coma al final
            if (!Linea.endsWith(";")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ';' al final de la expresion.");
                return false;
            }

            // Dividir la línea en partes usando espacios en blanco como separadores
            String[] partes = Linea.trim().split("\\s+");

            // Verificar si hay suficientes partes (deben ser al menos 2: "int" y el nombre de la variable)
            if (partes.length < 2) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", la declaracion de variables debe contener un nombre valido.");
                return false;
            }

            // Verificar si hay asignación de valor (debe haber al menos 3 partes: "int", nombre, "=")
            if (partes.length >= 3) {
                // Verificar si la última parte contiene "=" y es seguida por un número
                String ultimaParte = partes[partes.length - 1];
                if (ultimaParte.contains("=") && !ultimaParte.matches(".*=.*[0-9].*")) {
                    System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", si se desea asignar un valor, se requiere que sea un numero.");
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
                System.out.println("\u001B[33mWarning \u001B[0m, en la linea " + formatearContador(contador) + " Java es sensible a minusculas y mayusculas, quisiste decir: String?");

                return false;
            }
            if (!Linea.endsWith(";")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
                return false;
            }
            String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");
            if (partes.length < 2 || !partes[0].equals("String")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", la declaracion de variables debe contener un nombre valido.");
                return false;
            }

            if (VariableCompleta.matches(".*[=].*")) {
                if (!VariableCompleta.matches(".*=\\s*\"[^\"]*\".*")) {
                    System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", si se desea asignar un valor, se requiere tiene que ir entre comillas");
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
                System.out.println("\u001B[33mWarning \u001B[0m, en la linea " + formatearContador(contador) + " Java es sensible a minusculas y mayusculas, quisiste decir: boolean?");

                return false;
            }
            if (!Linea.endsWith(";")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
                return false;
            }
            String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");
            if (partes.length < 2 || !partes[0].equals("boolean")) {
                System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", la declaracion de variables debe contener un nombre valido.");
                return false;
            }

            if (NombreVariable.matches(".*[=].*")) {
                if (!NombreVariable.matches(".*=\\s*(true|false).*")) {
                    System.out.println(" \u001B[31m Error \u001B[0m, en la linea " + formatearContador(contador) + ", los boleanos tiene que ser true o false");
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

}
