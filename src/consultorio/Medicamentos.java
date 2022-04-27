
package consultorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Medicamentos extends javax.swing.JInternalFrame {
    String ruta = "C:\\Hospital\\Medicamentos.txt";
    String ruta_temporal = "C:\\Hospital\\Medicamentos_copia.txt";
    OpcionesArchivo opciones = new OpcionesArchivo();
    String indiceDeRepetido;

    public Medicamentos() {
        initComponents();
        resetearRegistro();
        llenarTable();
    }
    
    private Boolean validarCampos(){
        //Método para verificar que no se guarden campos vacios
        
        if(codigo.getText().replaceAll(" ", "").isEmpty()){
            codigo.setText("");
            codigo.requestFocus();
            return null;
        }       
        if(nombreComercial.getText().replaceAll(" ", "").isEmpty()){
            nombreComercial.setText("");
            nombreComercial.requestFocus();
            return null;
        }
        
        if(principioActivo.getText().replaceAll(" ", "").isEmpty()){
            principioActivo.setText("");
            principioActivo.requestFocus();
            return null;
        }
        
        if(tipoMedicamento.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null,"No has seleccionado el tipo de medicamento que es");
            tipoMedicamento.requestFocus();
            return null;
        }
        
        if(gramajePeso.getText().replaceAll(" ", "").isEmpty()){
            gramajePeso.setText("");
            gramajePeso.requestFocus();
            return null;
        }
        
        if(stock.getText().replaceAll(" ", "").isEmpty()){
            stock.setText("");
            stock.requestFocus();
            return null;
        }
        
        //Si todos los campos están llenos entonces nos dejará guardar o modificar
        return true;
    }
    
    private void limpiarCampos(){
        resetearRegistro();
        codigo.setText("");
        nombreComercial.setText("");
        principioActivo.setText("");
        tipoMedicamento.setSelectedIndex(0);
        gramajePeso.setText("");
        stock.setText("");
        llenarTable();
        codigo.requestFocus();       
    }
    
    private void resetearRegistro(){
        registro.setText(String.valueOf(opciones.calcularSiguienteIndice(ruta)));
    }
    
    private void guardarDatos(){
        if(registro.getText().equals(String.valueOf(opciones.calcularSiguienteIndice(ruta)))){
            if(validarCampos() != null){
                BufferedWriter flujo = null;
                try {
                    flujo = new BufferedWriter( new FileWriter(ruta,true));
                    //Extraemos los datos de los campos de texto
                    String id = registro.getText();
                    String codigo_medicamento = codigo.getText().toLowerCase();
                    String nombre_comercial = nombreComercial.getText();
                    String principio_activo = principioActivo.getText();
                    String tipo_medicamento = tipoMedicamento.getSelectedItem().toString();
                    String gramaje_peso = gramajePeso.getText();       
                    String cantidad_stock = stock.getText();

                    //CReamos la línea con el nuevo medicamento
                    String nuevoMedicamento;
                    nuevoMedicamento = id + ",";
                    nuevoMedicamento = nuevoMedicamento + codigo_medicamento + ",";
                    nuevoMedicamento = nuevoMedicamento + nombre_comercial + ",";
                    nuevoMedicamento = nuevoMedicamento + principio_activo + ",";
                     nuevoMedicamento = nuevoMedicamento + tipo_medicamento + ",";
                    nuevoMedicamento = nuevoMedicamento + gramaje_peso + ",";
                    nuevoMedicamento = nuevoMedicamento + cantidad_stock;

                    // Insertamos los datos al archivo
                    flujo.write(nuevoMedicamento);
                    flujo.flush();
                    flujo.newLine();
                    JOptionPane.showMessageDialog(null,"Hemos registrado el medicamento");
                    limpiarCampos();
      
                }catch(IOException e){
                    System.out.println("Error al abrir el flujo: " + e.getMessage());
                }finally{
                    try{
                        flujo.close();
                    }catch(IOException e){
                       System.out.println("Error al cerrar flujo: " + e.getMessage()); 
                    }                 
                }
            }
        } 
    }

    private void llenarTable(){
        String[] titulos = {
            "ID", 
            "Codigo", 
            "Nombre_Comercial", 
            "Principio_Activo",
            "Tipo_de_Medicamento",
            "Gramaje/Peso",
            "Stock",
        };
        
        //Este objeto sindatos le pasaremos almodelo al iniciar el programa
        Object[][] data = {};
        //Cargamos el modelo, le pasamos los datos y los títulos quetendrá la tabla
        DefaultTableModel modelo = new DefaultTableModel(data, titulos);
        //Pasamos el modelo a la tabla
        tablaMedicamentos.setModel(modelo);
        //Reseteamos la tabla
        modelo.setRowCount(0);
        //Si el archivo Citas.txt tiene datos,entonces los mostrará en la tabla
        if(opciones.calcularSiguienteIndice(ruta) > 0){        
            String linea; 
            BufferedReader br = null;
            try {
                //Abrimos un flujo
                 br = new BufferedReader( new FileReader(ruta));      
                //Leemos liena a linea el archivo
                while( ( linea = br.readLine() ) != null ) {
                    Object[] datos = new Object[7];
                    int i = 0;
                    StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");
                    //Guardamos en un objeto, los datos que tiene la línea que estamos leyendo
                    while(cadenaDeTexto.hasMoreTokens()){  
                        String token = cadenaDeTexto.nextToken();
                        datos[i] = token;
                        i++;
                    }
                    //Cargamos el modelo de la tabla
                    DefaultTableModel nuevoModelo = (DefaultTableModel)tablaMedicamentos.getModel();
                    //Le pasamos el objeto con los datos, al modelo.
                    nuevoModelo.addRow(datos);
                    //Le pasamos el nuevo modelo a la tabla
                    tablaMedicamentos.setModel(nuevoModelo); 

                }     
            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado en llenar tabla: " + e.getMessage());
            } catch(IOException e){
                System.out.println("Error en el buffer al llenar tabla: " + e.getMessage());
            }  finally{
                try{
                    br.close();
                }catch(IOException e){
                    System.out.println("Error al cerrar buffer al llenar tabla: " + e.getMessage());
                }
            }
        } 
    }
    
    private void modificarDatos(){
        
        if(!registro.getText().equals(String.valueOf(opciones.calcularSiguienteIndice(ruta)))){
            if(validarCampos() != null){
                FileReader fr= null;
                FileWriter fw = null;
                BufferedReader br = null;
                BufferedWriter bw = null;
                String linea;
                //Extraemos los datos de los campos de texto
                String id = registro.getText();
                String[] nuevosDatos = new String[7];
                nuevosDatos[0] = id;
                nuevosDatos[1] = codigo.getText().toLowerCase();
                nuevosDatos[2] = nombreComercial.getText();
                nuevosDatos[3] = principioActivo.getText();
                nuevosDatos[4] = tipoMedicamento.getSelectedItem().toString();
                nuevosDatos[5] = gramajePeso.getText();       
                nuevosDatos[6] = stock.getText();
                boolean envioMismoCodigo = buscarRepetido(nuevosDatos[1]) == true && registro.getText().equals(indiceDeRepetido);
                boolean noEstaRepetido =  buscarRepetido(nuevosDatos[1]) != true;
                if(envioMismoCodigo){
                    try {
                        fr = new FileReader(ruta);
                        fw = new FileWriter(ruta_temporal);
                        br = new BufferedReader(fr);
                        bw = new BufferedWriter(fw);

                        while((linea = br.readLine()) != null){
                            int i = 0;
                            Object[] datos = new Object[7];
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
                                String lineaNueva = "";
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
                     
                    }catch(IOException e){
                        System.out.println("Error al abrir el flujo: " + e.getMessage());
                    }finally{
                        try{
                            br.close();
                            bw.close();
                            cambiarNombre("Hemos modificado datos del medicamento");
                            limpiarCampos();
                        }catch(IOException e){
                           System.out.println("Error al cerrar flujo: " + e.getMessage()); 
                        }
                    } 
                }
                
                if(noEstaRepetido){
                    try {
                        fr = new FileReader(ruta);
                        fw = new FileWriter(ruta_temporal);
                        br = new BufferedReader(fr);
                        bw = new BufferedWriter(fw);

                        while((linea = br.readLine()) != null){
                            int i = 0;
                            Object[] datos = new Object[7];
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
                                String lineaNueva = "";
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
                        
                    }catch(IOException e){
                        System.out.println("Error al abrir el flujo: " + e.getMessage());
                    }finally{
                        try{
                            br.close();
                            bw.close();
                            cambiarNombre("Hemos eliminado el medicamento");
                            limpiarCampos();
                        }catch(IOException e){
                           System.out.println("Error al cerrar flujo: " + e.getMessage()); 
                        }
                    } 
                }
            }
        }
    }
    
    public boolean buscarRepetido(String codigoMedicamento){
        FileReader fr= null;
        BufferedReader br = null;
        String linea;
        boolean encontrado = false;
        try{
            fr = new FileReader(ruta);
            br = new BufferedReader(fr);
            String lectura = "";
            while((linea = br.readLine()) != null){
                int i = 0;
                Object[] datos = new Object[7];
                StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");
                
                while(cadenaDeTexto.hasMoreTokens()){
                    String token = cadenaDeTexto.nextToken();
                    datos[i] = token;
                    i++;
                }
                
                if(datos[1].equals(codigoMedicamento)){
                    encontrado = true;
                    indiceDeRepetido = datos[0].toString();
                }
            }    
            
            return encontrado;
            
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }catch(IOException e){
           System.out.println("Error encontrado: "+ e.getMessage());
        }finally{
            try{
                br.close();          
            }catch(IOException e){
                System.out.println("Error al cerrar buffer: "+ e.getMessage());
            }
        }
        return false;
    }
    
    public void cambiarNombre(String mensaje){
        File archivoAnterior = new File(ruta);
        File archivoNuevo = new File(ruta_temporal);
        if(archivoAnterior.exists() && archivoNuevo.exists()){
            if(archivoAnterior.delete()){ 
                if(archivoNuevo.renameTo(archivoAnterior)){
                    JOptionPane.showMessageDialog(null,mensaje);
                }
            }
        }
    }
    
    public void eliminarRegistro(){
        String id = registro.getText();
        FileReader fr= null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        String linea;
        
        try{
            fr = new FileReader(ruta);
            fw = new FileWriter(ruta_temporal);
            bw = new BufferedWriter(fw);
            br = new BufferedReader(fr);
            while((linea = br.readLine()) != null){
                int i = 0;
                Object[] datos = new Object[7];
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
                cambiarNombre("Hemos modificado el medicamento");
            }catch(IOException e){
                System.out.println("Error al cerrar buffer: "+ e.getMessage());
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        principioActivo = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        codigo = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        nombreComercial = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        stock = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaMedicamentos = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        gramajePeso = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        agregarMedicamento = new javax.swing.JButton();
        modificarMedicamento = new javax.swing.JButton();
        limpiarCampos = new javax.swing.JButton();
        eliminarMedicamento = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        registro = new javax.swing.JLabel();
        tipoMedicamento = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Medicamentos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setViewportView(principioActivo);

        jScrollPane2.setViewportView(codigo);

        jScrollPane3.setViewportView(nombreComercial);

        jScrollPane5.setViewportView(stock);

        tablaMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "Nombre_Comercial", "Principio_Activo", "Tipo", "Gramaje/Peso", "Stock"
            }
        ));
        tablaMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMedicamentosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablaMedicamentos);

        jScrollPane7.setViewportView(gramajePeso);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Medicamentos");

        jLabel2.setText("Codigo");

        jLabel3.setText("Nombre Comercial");

        jLabel4.setText("Principio Activo");

        jLabel5.setText("Gramaje / Peso");

        jLabel6.setText("Cantidad en Stock");

        jLabel7.setText("Tipo");

        agregarMedicamento.setBackground(new java.awt.Color(204, 204, 255));
        agregarMedicamento.setForeground(new java.awt.Color(0, 102, 153));
        agregarMedicamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guarda_azul.png"))); // NOI18N
        agregarMedicamento.setText("Agregar");
        agregarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarMedicamentoActionPerformed(evt);
            }
        });

        modificarMedicamento.setBackground(new java.awt.Color(204, 204, 255));
        modificarMedicamento.setForeground(new java.awt.Color(0, 102, 153));
        modificarMedicamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/editar.png"))); // NOI18N
        modificarMedicamento.setText("Modificar");
        modificarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarMedicamentoActionPerformed(evt);
            }
        });

        limpiarCampos.setBackground(new java.awt.Color(204, 204, 255));
        limpiarCampos.setForeground(new java.awt.Color(0, 102, 153));
        limpiarCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/limpiar.png"))); // NOI18N
        limpiarCampos.setText("Limpiar");
        limpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarCamposActionPerformed(evt);
            }
        });

        eliminarMedicamento.setBackground(new java.awt.Color(204, 204, 255));
        eliminarMedicamento.setForeground(new java.awt.Color(0, 102, 153));
        eliminarMedicamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/reciclar.png"))); // NOI18N
        eliminarMedicamento.setText("Eliminar");
        eliminarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarMedicamentoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Reg Nº: ");

        registro.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        registro.setText("00");

        tipoMedicamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecciona un tipo", "Analgésicos", "Anestésico", "Antiácido", "Antianémico", "Antibiótico", "Antidepresivo", "Antifúgico", "Antiinflamatorio", "Antihemorrágico", "Antihistamínico", "Antineoplásticos", "Antipirético", "Antiparasitário", "Antiséptico", "Betabloqueante", "Broncodilatador", "Corticoides", "Dermatológico", "Diurético", "Inmunoestimulante", "Inmunodepresor", "Laxante", "Oftalmológico", "Psicoléptico", "Psicoanalépticos", "Vitamínico", " ", " ", " ", " " }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(agregarMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(modificarMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(eliminarMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(limpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(tipoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5)
                                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)))
                                    .addComponent(jLabel1))))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(registro)
                        .addGap(59, 59, 59))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(registro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tipoMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(limpiarCampos)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(agregarMedicamento)
                        .addComponent(modificarMedicamento)
                        .addComponent(eliminarMedicamento)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void limpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarCamposActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_limpiarCamposActionPerformed

    private void eliminarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarMedicamentoActionPerformed
        if(!registro.getText().equals(String.valueOf(opciones.calcularSiguienteIndice(ruta)))){
            eliminarRegistro();
            limpiarCampos();
        }else{
            JOptionPane.showMessageDialog(null,"No has seleccionado un medicamento");
            limpiarCampos();
        }
    }//GEN-LAST:event_eliminarMedicamentoActionPerformed

    private void agregarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarMedicamentoActionPerformed
        guardarDatos();
    }//GEN-LAST:event_agregarMedicamentoActionPerformed

    private void tablaMedicamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMedicamentosMouseClicked
        int indiceFila = tablaMedicamentos.rowAtPoint(evt.getPoint());
        registro.setText(String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 0)));
        codigo.setText(String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 1)));
        nombreComercial.setText(String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 2)));
        principioActivo.setText(String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 3)));
        String tipo = String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 4));
        int tamañoComboBox = tipoMedicamento.getItemCount();
        int indice = 0;
        boolean encontrado = false;
        for(int i = 1; i<tamañoComboBox; i++){
            tipoMedicamento.setSelectedIndex(i);
            if(tipo.equals(tipoMedicamento.getSelectedItem().toString())){
                encontrado = true;
                indice = i;
            }
        }
        
        tipoMedicamento.setSelectedIndex(indice);
        
        gramajePeso.setText(String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 5)));
        stock.setText(String.valueOf(tablaMedicamentos.getValueAt(indiceFila, 6)));
       
    }//GEN-LAST:event_tablaMedicamentosMouseClicked

    private void modificarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarMedicamentoActionPerformed
        modificarDatos();
    }//GEN-LAST:event_modificarMedicamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarMedicamento;
    private javax.swing.JTextPane codigo;
    private javax.swing.JButton eliminarMedicamento;
    private javax.swing.JTextPane gramajePeso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JButton limpiarCampos;
    private javax.swing.JButton modificarMedicamento;
    private javax.swing.JTextPane nombreComercial;
    private javax.swing.JTextPane principioActivo;
    private javax.swing.JLabel registro;
    private javax.swing.JTextPane stock;
    private javax.swing.JTable tablaMedicamentos;
    private javax.swing.JComboBox tipoMedicamento;
    // End of variables declaration//GEN-END:variables
}
