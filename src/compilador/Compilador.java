/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package compilador;

import java.io.File;

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

        String nombretxt = "ejemplo.txt";
        String Ruta = System.getProperty("user.dir");
        String rutatxt = Ruta + File.separator + nombretxt;
        //"C:\Users\kevin\OneDrive\Documentos\NetBeansProjects\compilador\dist\ejemplo.txt"
        //C:\Users\kevin\OneDrive\Documentos\NetBeansProjects\compilador\ejemplo.txt
        //System.out.println(rutatxt);
        Analizador analisis = new Analizador();
        analisis.lectura(nombretxt);

    }

}
