
package consultorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;


public class OpcionesArchivo {
    int contador = 0;
    public void eliminar(String ruta){
        File archivo = new File(ruta);
        if(archivo.exists()){
            if(archivo.delete()){
                JOptionPane.showMessageDialog(null,"Se elimino");
            }
        }    
    }
    
    public int calcularSiguienteIndice(String ruta){
        FileReader fr= null;
        File archivo = new File(ruta);
        BufferedReader br = null;
        String linea = "";
        try{
            fr = new FileReader(ruta);
            br =new BufferedReader(fr);
            ArrayList datos = new ArrayList();
            if(archivo.length() != 0){
                while((linea = br.readLine()) != null){        
                    StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");
                    int i = 0;
                    Object[] indices = new Object[8];
                    while(cadenaDeTexto.hasMoreTokens()){
                        String token = cadenaDeTexto.nextToken();
                        indices[i] = token;
                        i++;
                    }
                    datos.add(indices[0]);     
                }
     
                int ultimoIndice = datos.size() - 1;
                String contadorInterno = String.valueOf(datos.get(ultimoIndice));
                int contador = Integer.valueOf(contadorInterno) + 1; 
                return contador;
                
            }else {       
                return contador + 1;
            }  
            
        }catch(FileNotFoundException e){
            System.out.println("Archivo no fué encontrado al contar registros");
        }catch(IOException e){
              System.out.println("Error al contar registros: " + e.getMessage());  
        }  finally{
            try{
                br.close();
            }catch(IOException e){
                System.out.println("Error al contar registros: " + e.getMessage());
            }
        }
        return 0;
    }
    
    public void cambiarNombre(String rutaNueva,String rutaVieja, String mensaje){
        File archivoAnterior = new File(rutaVieja);
        File archivoNuevo = new File(rutaNueva);
        if(archivoAnterior.exists() && archivoNuevo.exists()){
            if(archivoAnterior.delete()){ 
                if(archivoNuevo.renameTo(archivoAnterior)){
                    JOptionPane.showMessageDialog(null,mensaje);
                }
            }
        }
    }
    
    public void eliminar(String ruta, String temporal, String id){
        FileReader fr= null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String linea;
        
        try{
            fr = new FileReader(ruta);
            fw = new FileWriter(temporal);
            bw = new BufferedWriter(fw);
            br = new BufferedReader(fr);
           
            while((linea = br.readLine()) != null){
                int i = 0;
                Object[] datos = new Object[8];
                StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");
                
                while(cadenaDeTexto.hasMoreTokens()){
                    String token = cadenaDeTexto.nextToken();
                    datos[i] = token;
                    i++;
                }
                
                if(!datos[0].equals(id)){
                    bw.write(linea);
                    bw.flush();
                    bw.newLine();
                }
            }           
            
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }catch(IOException e){
           System.out.println("Error encontrado: "+ e.getMessage());
        }finally{
            try{
                br.close();
                bw.close();           
            }catch(IOException e){
                System.out.println("Error al cerrar buffer: "+ e.getMessage());
            }
        }
    }
    
    public void eliminarRegistro(String ruta, String temporal, String id){
        String indiceSiguiente = String.valueOf(calcularSiguienteIndice(ruta));
        if(!id.equals(indiceSiguiente)){
            String mensaje = "Hemos cancelado la cita";
            eliminar(ruta, temporal, id);
            cambiarNombre(temporal,ruta,mensaje);
        }else{
            JOptionPane.showMessageDialog(null,"Debes seleccionar una cita");
        }
    }
    
    public void modificar(String ruta, String temporal, String[] nuevosDatos){
        String indiceSiguiente = String.valueOf(calcularSiguienteIndice(ruta));
        String id = nuevosDatos[0];
        if(!id.equals(indiceSiguiente)){
            
            FileReader fr= null;
            FileWriter fw = null;
            BufferedReader br = null;
            BufferedWriter bw = null;
            String linea, lineaNueva;

            try{
                fr = new FileReader(ruta);
                fw = new FileWriter(temporal);
                bw = new BufferedWriter(fw);
                br = new BufferedReader(fr);

                while((linea = br.readLine()) != null){
                    int i = 0;
                    Object[] datos = new Object[8];
                    StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");

                    while(cadenaDeTexto.hasMoreTokens()){
                        String token = cadenaDeTexto.nextToken();
                        datos[i] = token;
                        i++;
                    }

                    if(!datos[0].equals(id)){
                        bw.write(linea);
                        bw.flush();
                        bw.newLine();
                    } else{
                        lineaNueva = "";
                        for(int j = 0; j < nuevosDatos.length; j++){
                            int longitud = nuevosDatos.length - 1;
                            if(j != longitud){
                                lineaNueva += String.valueOf(nuevosDatos[j]) + ",";
                            }else{
                                lineaNueva += String.valueOf(nuevosDatos[j]);
                            }
                        }                    
                        bw.write(lineaNueva);
                        bw.flush();
                        bw.newLine();
                    }
                }                       

            }catch(FileNotFoundException e){
                System.out.println("Archivo no encontrado");
            }catch(IOException e){
               System.out.println("Error encontrado: "+ e.getMessage());
            }finally{
                try{
                    br.close();
                    bw.close();        
                }catch(IOException e){
                    System.out.println("Error al cerrar buffer: "+ e.getMessage());
                }
            }
            String mensaje = "Hemos modificado la cita";
            cambiarNombre(temporal,ruta, mensaje);
            
        }else{
            JOptionPane.showMessageDialog(null,"Debes seleccionar una cita");
        }
    }
  
 }

