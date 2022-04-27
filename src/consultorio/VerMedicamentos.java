
package consultorio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;


public class VerMedicamentos extends javax.swing.JInternalFrame {
    String rutaArchivo = "C:\\Hospital\\Medicamentos.txt";
   
    public VerMedicamentos() {
        initComponents();
        llenarTabla();
    }

    private void llenarTabla(){
 
        String[] titulos = {
            "ID", 
            "Codigo", 
            "Nombre_comercial",
            "Principi_Activo",
            "Tipo_Medicamento",
            "Gramaje/Peso",
            "Stock"
        };

        Object[][] data = {};
        DefaultTableModel modelo = new DefaultTableModel(data, titulos);
       
        tablaMedicamentos.setModel(modelo);
        modelo.setRowCount(0);
        String linea; 
        BufferedReader br = null;
        try {
   
             br = new BufferedReader( new FileReader(rutaArchivo));      
   
            while( ( linea = br.readLine() ) != null ) {
                Object[] datos = new Object[7];
                int i = 0;
                StringTokenizer cadenaDeTexto = new StringTokenizer(linea,",");
                
                while(cadenaDeTexto.hasMoreTokens()){  
                    String token = cadenaDeTexto.nextToken();
                    datos[i] = token;
                    i++;
                }
                DefaultTableModel nuevoModelo = (DefaultTableModel)tablaMedicamentos.getModel();
                nuevoModelo.addRow(datos);
                tablaMedicamentos.setModel(nuevoModelo); 
                
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
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaMedicamentos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Ver medicamentos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tablaMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "Nombre_Comercial", "Principio_Activo", "Tipo", "Gramaje/Peso", "Stock"
            }
        ));
        jScrollPane6.setViewportView(tablaMedicamentos);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Todos los medicamentos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1)
                        .addGap(197, 197, 197))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1)))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tablaMedicamentos;
    // End of variables declaration//GEN-END:variables
}
