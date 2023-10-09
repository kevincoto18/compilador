/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

/**
 *
 * @author kevin
 */
public class VariablesGlobales {
    
    private String TipoVariable;
    private String NombreVariable;

    public VariablesGlobales(String Tipo, String Nombre) {
        this.TipoVariable = Tipo;
        this.NombreVariable = Nombre;
    }
      public String getTipo() {
        return TipoVariable;
    }

    public String getNombre() {
        return NombreVariable;
    }
}
