package calculadora;
import java.util.Scanner;
/**
*
* @author Usuario
*/
public class calculadora {
 /**
 * @param args the command line arguments
 */
 public static void main(String[] args) {
 
 int opcion;
 int n1;
 int n2;
 int r;
 Scanner leer = new Scanner(System.in);
 
 
 System.out.println("Elige la operación que quieres realizar");
 System.out.println("1 - Suma");
 System.out.println("2 - Resta");
 System.out.println("3 - Multiplicación");
 System.out.println("4 - División");
opcion = leer.nextInt();
 
 System.out.println ("Escribe el primer número");
 n1 =leer.nextInt();
 System.out.println ("Escribe el segundo número");
 n2 = leer.nextInt();
 
 switch (opcion) {
 case 1:
 r = n1 + n2;
 System.out.println("El resultado de sumar "+ n1 +"+"+ n2+ " es: "+ r);
 break;
case 2:
 r = n1 - n2;
 System.out.println("El resultado de restar "+ n1 +"-"+ n2+ " es: "+ r);
 break;
 
 case 3:
 r = n1 * n2;
 System.out.println("El resultado de multiplicar "+ n1 +"*"+ n2+ " es: "+ r);
 break; 
 
 case 4:
 r = n1 / n2;
 System.out.println("El resultado de dividir "+ n1 +"/"+ n2+ " es: "+ r);
 break; 
 }
 
 
 }
 
}
