
package consultorio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class OpcionesConsultorios {
   
    public void agregarTodos(String ruta){
        FileWriter fw = null;
        BufferedWriter bw = null;
        String[] consultorio = {
            "alpa",
            "beta",
            "gamma",
            "delta",
            "epsilon",
            "dseta",
            "eta",
            "zeta",
            "iota",
            "kappa"
        };
        try{
            fw = new FileWriter(ruta);
            bw = new BufferedWriter(fw);
            int indice = 1;
            for(String i: consultorio){
                bw.write(indice + "," + i);
                bw.flush();
                bw.newLine();
                indice++;
            }
  
        }catch(IOException e){
            System.out.println("Error al llenar archivo Consultorios: " + e.getMessage());
        }finally{
            try{
                bw.close();
            }catch(IOException e){
                System.out.println("Error al llenar archivo Consultorios: " + e.getMessage());
            }
        }
    }
   
}
