/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package compilador;

import java.io.File;
import java.io.IOException;
import java.time.temporal.Temporal;

/**
 *
 * @author Kevin Coto
 *
 */
// Proyecto compilador sintactico para materia de compiladores 3re cuatrimestre UNED 2023
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String nombretxt = "ejemplo.java";
        if(!nombretxt.endsWith(".java"))
        {
            System.err.println("Error general, el archivo tiene que tener extension .java");
            return;
        }
        String errores = nombretxt.replace(".java", "_Errores.txt");
        
        String Ruta = System.getProperty("user.dir");
        //String rutatxt = Ruta + File.separator + nombretxt;
        ArchivoTemporal temporal = new ArchivoTemporal();
        temporal.BorrarTemporarl(errores);
        //Crea un .txt temporal solo en ejecucion
        temporal.CrearTXTtemporal(nombretxt);
        // Rescribe lo del .java al .txt
        temporal.LeerJAVA(nombretxt);
       
        Analizador analisis = new Analizador();
        nombretxt = nombretxt.replace(".java", ".txt");
        //realiza todo el analisis
        analisis.lectura(nombretxt);
        //elimina el .txt temporal
        temporal.BorrarTemporarl("ejemplo.txt");
      
      

    }

    public static void CrearTXTErrores(String fichero) {
        fichero = fichero.replace(".txt", "_Errores.txt");
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
    
    
   

}
