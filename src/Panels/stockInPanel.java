/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Dao.DataBase_Connection;
import PanelForms.Test.MyIntFilter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author ranjan
 */
public class stockInPanel extends javax.swing.JPanel {
    DataBase_Connection dao;
    protected Statement smtInstance;
    protected Connection conInstance;
    DefaultTableModel model, model1;
    
    /**
     * Creates new form stockInPanel
     */
    public stockInPanel() {
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        txtProName.requestFocus();
    }
    
    
    private void remove() {

        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    private void validateData(){
        if(txtProName.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Product Name");
         txtProName.requestFocus();
        }
       else if(txtQuan.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Product Quantity");
         txtQuan.requestFocus();
        }
        else if(txtCost.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Product Cost");
         txtCost.requestFocus();
        }
        else if(txtMRP.getText().isEmpty()){
         JOptionPane.showMessageDialog(null,"Enter Product MRP");
         txtMRP.requestFocus();
        }
        else {
             try{
            String search = "SELECT * FROM product_stock WHERE proName = '"+txtProName.getText()+"'";
            smtInstance = conInstance.createStatement();
            ResultSet resultSearch = smtInstance.executeQuery(search);
            if(resultSearch.next()){
                int oldStock = resultSearch.getInt("quan");
                oldStock+=Integer.parseInt(txtQuan.getText());
                String newStock= Integer.toString(oldStock);
                String sqlupdate;
                sqlupdate = "update product_stock set quan ='"+ newStock +"' where proName = '"+txtProName.getText()+"'";
                smtInstance = conInstance.createStatement();
                int result = smtInstance.executeUpdate(sqlupdate);
                if(result!=0){
                    JOptionPane.showMessageDialog(null,"Stock Updated");
                }
                
            }
            else{
                String sql;

                sql= "insert into product_stock (proName,VendName, quan, cost, mrp)values ('"+txtProName.getText()+"','"+txtVendorName.getText()+"',"+txtQuan.getText()+","+txtCost.getText()+","+txtMRP.getText()+")";
                smtInstance = conInstance.createStatement();
                int result = smtInstance.executeUpdate(sql);
                if(result!=0){
                    JOptionPane.showMessageDialog(null,"Stock inserted");
                }
            }
                
            
        }
        catch(SQLException ex){

        }
        resetStockDetails();
        fillTable();
        txtProName.requestFocus();
       
            
        }
    }
    
    
    private void resetStockDetails(){
        txtProName.setText("");
        txtVendorName.setText("");
        txtQuan.setText("0");
        txtMRP.setText("");
        txtCost.setText("");
    }
    
    private void fillTable(){
       model  =(DefaultTableModel)stockTable.getModel();
       String values = txtProName.getText();
        try {
            
            String  sql1= "SELECT * FROM product_stock where proName Like '%"+values+"%' order by quan";
            Statement st = conInstance.createStatement();
            ResultSet rs1 = st.executeQuery(sql1);
                remove();
                int i = 0;
                while ( rs1.next() ) //step through the result set
                {
                    i++;//count raws
                }
                if (i>0){
                        int j= 0;
                        rs1.beforeFirst();
                        
                        while (rs1.next()) {
                                      
                    String A = rs1.getString("proCode");
                    String B = rs1.getString("proName");
                    String D = rs1.getString("quan");
                    String E = rs1.getString("cost");
                    String F = rs1.getString("mrp");
                    model.insertRow(j,new Object[]{A,B,D,E,F});
                 
                    j++;
                }
                }
            
             
         } catch (SQLException ex) {
           // Logger.getLogger(Pharmacy_In_Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
              
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtQuan = new javax.swing.JTextField();
        txtCost = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtProName = new javax.swing.JTextField();
        addStockButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtMRP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtVendorName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();

        setBackground(java.awt.Color.gray);
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBackground(java.awt.Color.lightGray);

        jPanel3.setBackground(java.awt.Color.lightGray);
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel3.setText("Quantity :");

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel4.setText("Purchase Rate:");

        txtQuan.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        txtQuan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQuanFocusGained(evt);
            }
        });
        txtQuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuanKeyReleased(evt);
            }
        });

        txtCost.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        txtCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel2.setText("Product Name");

        txtProName.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        txtProName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProNameActionPerformed(evt);
            }
        });
        txtProName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProNameKeyReleased(evt);
            }
        });

        addStockButton.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        addStockButton.setText("Add Stock");
        addStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStockButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel5.setText("MRP :");

        txtMRP.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        txtMRP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMRPKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMRPKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel9.setText("Ven. Name:");

        txtVendorName.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        txtVendorName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtVendorNameKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtProName, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(41, 41, 41)
                        .addComponent(txtVendorName))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMRP, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(addStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel9});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(txtVendorName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addComponent(txtQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMRP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel5, jLabel9});

        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProCode", "Product Name", "Qty", "Price", "M.R.P"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stockTable.setIntercellSpacing(new java.awt.Dimension(2, 2));
        stockTable.setRowHeight(22);
        stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(stockTable);
        if (stockTable.getColumnModel().getColumnCount() > 0) {
            stockTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            stockTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            stockTable.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel2.setBackground(new java.awt.Color(254, 58, 79));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setForeground(java.awt.Color.white);

        jLabel18.setBackground(new java.awt.Color(254, 58, 79));
        jLabel18.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel18.setForeground(java.awt.Color.white);
        jLabel18.setText("In-Stock Entry");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(344, 344, 344))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStockButtonActionPerformed
              validateData();
              txtProName.requestFocus();
    }//GEN-LAST:event_addStockButtonActionPerformed

    private void txtQuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuanKeyPressed
        ((AbstractDocument) txtQuan.getDocument()).setDocumentFilter(new MyIntFilter());
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtCost.requestFocus();
        }
    }//GEN-LAST:event_txtQuanKeyPressed

    private void txtQuanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuanKeyReleased
        
    }//GEN-LAST:event_txtQuanKeyReleased

    private void txtCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyReleased
        
    }//GEN-LAST:event_txtCostKeyReleased

    private void txtProNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProNameKeyReleased
        // TODO add your handling code here:
        fillTable();
    }//GEN-LAST:event_txtProNameKeyReleased

    private void txtProNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProNameActionPerformed

    private void txtMRPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMRPKeyReleased
        
    }//GEN-LAST:event_txtMRPKeyReleased

    private void stockTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableMouseClicked
       int index = stockTable.getSelectedRow();
       model  =(DefaultTableModel)stockTable.getModel();
       String Name = model.getValueAt(index, 1).toString();
       txtProName.setText(Name);
       txtProName.requestFocus();
       
        
    }//GEN-LAST:event_stockTableMouseClicked

    private void txtMRPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMRPKeyPressed
            int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            validateData();
            txtProName.requestFocus();
        }
    }//GEN-LAST:event_txtMRPKeyPressed

    private void txtCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyPressed
        char car = evt.getKeyChar();
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtMRP.requestFocus();
        }
//        else if((car < '0'|| car > '9') && txtCost.getText().contains(".") && (car!=(char)KeyEvent.VK_BACK_SPACE))
//        {
//            evt.consume();
//            JOptionPane.showMessageDialog(null, "Invalid Insert");
//            txtCost.setText("");
//        }
        else if((car < '0'|| car > '9') && (car!='.') && (car!=(char)KeyEvent.VK_BACK_SPACE)&& (car!=(char)KeyEvent.VK_ENTER)){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Invalid Insert");
            txtCost.setText("");
        }
        
    }//GEN-LAST:event_txtCostKeyPressed

    private void txtQuanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuanFocusGained
        txtQuan.selectAll();
    }//GEN-LAST:event_txtQuanFocusGained

    private void txtProNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProNameKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtVendorName.requestFocus();
        }
    }//GEN-LAST:event_txtProNameKeyPressed

    private void txtVendorNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVendorNameKeyPressed
       int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtQuan.requestFocus();
        }
    }//GEN-LAST:event_txtVendorNameKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStockButton;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable stockTable;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtMRP;
    public javax.swing.JTextField txtProName;
    private javax.swing.JTextField txtQuan;
    private javax.swing.JTextField txtVendorName;
    // End of variables declaration//GEN-END:variables
}
