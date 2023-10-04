/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

/**
 *
 * @author kevin
 */
public class TablaExpresiones {
     enum Expresiones 
    {
        ExpresionImport("^package\\s+[a-zA-Z_][a-zA-Z_0-9]*(\\.[a-zA-Z_][a-zA-Z_0-9]*)*;$");
        public final String patron;
        Expresiones(String e){
            this.patron =e;
        }
    }
}
