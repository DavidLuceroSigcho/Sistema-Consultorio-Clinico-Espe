
package consultorio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class VerCitas extends javax.swing.JInternalFrame {
    String rutaArchivo = "C:/Hospital/Citas.txt";
    public VerCitas() {
        initComponents();
        llenarTabla();
    }
    
    private void llenarTabla(){
 
        String[] titulos = {
            "ID", 
            "Nombre", 
            "Apellido_Paterno", 
            "Apellido_Materno",
            "Fecha_Nacimiento",
            "Fecha_Cita",
            "Hora_cita",
            "Consultorio",
            "Descripción"
        };

        Object[][] data = {};
        DefaultTableModel modelo = new DefaultTableModel(data, titulos);
       
        tablaDeCitas.setModel(modelo);
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
                DefaultTableModel nuevoModelo = (DefaultTableModel)tablaDeCitas.getModel();
                nuevoModelo.addRow(datos);
                tablaDeCitas.setModel(nuevoModelo); 
                
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
        tablaDeCitas = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setTitle("Ver todas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Todas las citas");

        tablaDeCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido_Paterno", "Apellido_Materno", "Fecha_Nacimiento", "Fecha_Agendada", "Hora", "Consultorio", "Descripción"
            }
        ));
        jScrollPane1.setViewportView(tablaDeCitas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jLabel1)))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaDeCitas;
    // End of variables declaration//GEN-END:variables
}
