/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.util.Arrays;

/**
 *
 * @author kevin
 */
public class Validaciones {

public boolean ValidarPackage(String Linea, int contador) {
    try {
        // Extrae el nombre del package eliminando el "package " y el ";"
        String packagename = Linea.substring(8, Linea.length() - 1);

        // Verifica si la línea termina con un punto y coma (;)
        if (!Linea.endsWith(";")) {
            System.out.println("Error, en la linea " + formatearContador(contador) + " falta el ; al final de la expresion.");
            return false;
        }

        // Verifica si el nombre del package contiene caracteres válidos
        if (!packagename.matches("[a-zA-Z0-9. ]+")) {
            System.out.println("Error, en la linea " + formatearContador(contador) + ", el nombre del package debe contener caracteres validos.");
            return false;
        }
        return true;
    } catch (Exception e) {
        return false;
    }
}

   public boolean ValidarImport(String Linea, int contador) {
    try {
        // Verifica si la línea termina con un punto y coma (;)
        if (!Linea.endsWith(";")) {
            System.out.println("Error, en la linea " + formatearContador(contador) + " falta el ; al final de la expresión.");
            return false;
        }

        // Divide la línea en partes usando espacios como separadores
        String[] partes = Linea.substring(0, Linea.length() - 1).split(" ");

        // Deben existir al menos dos partes: "import" y el nombre
        if (partes.length < 2 || !partes[0].equals("import")) {
            System.out.println("Error, en la linea " + formatearContador(contador) + ", el import debe contener un nombre valido.");
            return false;
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
