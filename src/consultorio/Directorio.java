
package consultorio;

import java.io.File;
import java.io.IOException;

public class Directorio {
    File carpeta, citas,consultorios,medicamentos;
    
    public void crearDirectorio(String ruta){
        carpeta = new File(ruta);      
        if(!carpeta.exists()){carpeta.mkdir();} 
    }
    
}
