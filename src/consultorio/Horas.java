
package consultorio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JComboBox;

public class Horas {
     
    public void inicializar(String ruta,JComboBox horaCita){
        String[] horas = new String[8];
        boolean[] horasOcupadas = new boolean[8];
        int indice = 0;
        for(int i = 9; i<= 16; i++){
                if(i != 9){
                    horas[indice] = String.valueOf(i+"h00");                 
                }else{
                    horas[indice] = String.valueOf("0"+i+"h00");           
                }
                indice++;
            }
        
        File archivo = new File(ruta);
        if(archivo.length() == 0){
            for(String hora: horas){
                    horaCita.addItem(hora);            
            }
        }
        
        if(archivo.length() != 0){
              
            BufferedReader br = null;
            String linea;
            try{
                br = new BufferedReader( new FileReader(ruta));
                //Recorreamos todas las horas buscando cual está full
                for(int j = 0; j< horas.length; j++){
                    int numeroDeRepeticiones = 0;                  
                    while( ( linea = br.readLine() ) != null ) {
                        
                        Object[] datos = new Object[8];
                        int i = 0;
                        StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");

                        while(cadenaDeTexto.hasMoreTokens()){  
                            String token = cadenaDeTexto.nextToken();
                            datos[i] = token;
                            i++;
                        }
                       //Verificamos cuantos consultorios tienen esa hora ocupada
                        if(datos[5].toString().equals(horas[j])){
                            numeroDeRepeticiones++;
                        }                      
                        
                    }
                    
                    //Activamos o desactivamos las horas si está ocupadas o no
                    if(numeroDeRepeticiones != 10){
                        horasOcupadas[j] = false;
                    }else{
                        horasOcupadas[j] = true;
                    }
                }
                int full = 0;
                
                for(int h = 0; h<horasOcupadas.length; h++){
                    if(horasOcupadas[h] == false){
                        horaCita.addItem(horas[h]); 
                    }else{
                        full++;
                    }
                }
                
                if(full == 10){
                    horaCita.addItem("No hay disponible"); 
                }
                
            }catch(IOException e){
                System.out.println("Error al cargar horas: " + e.getMessage());
            } finally{
                try{
                    br.close();
                }catch(IOException e){
                }
            } 
        }
    }
}
