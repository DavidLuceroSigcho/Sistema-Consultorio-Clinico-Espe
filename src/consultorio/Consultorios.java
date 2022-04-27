
package consultorio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class Consultorios extends javax.swing.JInternalFrame {

    String rutaArchivo = "C:/Hospital/Consultorios.txt";
    public Consultorios() {
        initComponents();
        llenarTabla();
    }   

    private void llenarTabla(){
 
        String[] titulos = {
            "ID", 
            "Consultorio", 
        };

        Object[][] data = {};
        DefaultTableModel modelo = new DefaultTableModel(data, titulos);
       
        tablaConsultorios.setModel(modelo);
        modelo.setRowCount(0);
        String linea; 
        BufferedReader br = null;
        try {
   
             br = new BufferedReader( new FileReader(rutaArchivo));      
   
            while( ( linea = br.readLine() ) != null ) {
                Object[] datos = new Object[9];
                int i = 0;
                StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");
                
                while(cadenaDeTexto.hasMoreTokens()){  
                    String token = cadenaDeTexto.nextToken();
                    datos[i] = token;
                    i++;
                }
                DefaultTableModel nuevoModelo = (DefaultTableModel)tablaConsultorios.getModel();
                nuevoModelo.addRow(datos);
                tablaConsultorios.setModel(nuevoModelo); 
                
            }     
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado al llenar tabla consultorio: " + e.getMessage());
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConsultorios = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Consultorios");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Consultorios");

        tablaConsultorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "consultorio"
            }
        ));
        jScrollPane1.setViewportView(tablaConsultorios);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel1)))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaConsultorios;
    // End of variables declaration//GEN-END:variables
}
