/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author kevin
 */
public class ArchivoTemporal {

    // Crea el .txt temporal
    public void CrearTXTtemporal(String fichero) {
        fichero = fichero.replace(".java", ".txt");
        File archivo = new File(fichero);

        try {
            if (archivo.createNewFile()) {
                // System.out.println("El archivo " + fichero + " ha sido creado exitosamente.");
            } else {
                // System.out.println("El archivo " + fichero + " ya existe.");
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }
    // metodo para escribir lo del .java al .txt temporal 

    public void EscribirTXT(String nombreArchivo, String linea) {
        try {
            nombreArchivo = nombreArchivo.replace(".java", ".txt");
            FileWriter escritor = new FileWriter(nombreArchivo, true);

            escritor.write(linea + "\n");  // Agrega la línea al archivo

            escritor.close();  // Cierra el archivo después de escribir
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo temporal");
        }
    }
    // metodo para leer el .java

    public void LeerJAVA(String fichero) {

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                EscribirTXT(fichero, linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo .java");
            System.err.println("Consejos: ");
            System.err.println("1 - Verifica que el archivo exista");
            System.err.println("2 - Verifica que el archivo este en la misma carpeta del .jar");
            System.err.println("3 - Verifica que el nombre del archivo sea el mismo\n");
        }
    }

    public void BorrarTemporarl(String fichero) {
        File archivo = new File(fichero);

        if (archivo.exists()) {
            if (archivo.delete()) {
            // System.err.println("Errores.txt limpiado");

            } else {
                System.err.println("Error al eliminar el archivo temporal");
            }
        }

    }
}
