/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;

/**
 *
 * @author kevin
 */
public class TablaSintaxis {

    enum Tokens {
        Reservada("\\b(abstract|assert|boolean|break|byte|case|"
                + "catch|char|class|const|continue|default|do|double|else|enum|extends|"
                + "false|final|finally|float|for|if|implements|import|instanceof|int|interface|long|"
                + "native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|main|args|Scanner|String|"
                + "synchronized|this|throw|throws|transient|true|try|void|volatile|while)\\b"),
        Palabra("[A-Za-z]*"),
        Numero("[0-9]*"),
        CorcheteApetura("\\{\\]*"),
        CorcheteCierre("}"),
        ParentesisApertura("\\("),
        ParentesisCierre("\\)"),
        CuadradosApertura("\\["),
        CuadradosCierre("\\]"),
        Operadores("[-+/*]*");

        public final String patron;

        Tokens(String t) {
            this.patron = t;
        }
    }
}
