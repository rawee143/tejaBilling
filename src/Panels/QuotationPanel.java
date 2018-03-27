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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;


/**
 *
 * @author ranjan
 */
public class QuotationPanel extends javax.swing.JPanel {
    
    protected Connection conInstance;
    protected Statement smtInstance;
    ResultSet rs,rs1, rs2;
    protected String proCode,BillId;
    DataBase_Connection dao;
    DefaultTableModel model, model1;
    private int billTableIndex = 0;
  

    /**
     * Creates new form pharmacySalesPanel
     */
    public QuotationPanel() {
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        txtCustName.requestFocus();
    }

    private void remove(){
        
        if(!isEmpty1(searchTable)){
            int rowCount = model.getRowCount();
    //Remove rows one by one from the end of the table
    for (int i = rowCount - 1; i >= 0; i--) {
    model.removeRow(i);
        }
        }
     }
     
    private void removeBill(){
        
        if(!isEmpty1(billTable)){
            int rowCount = model1.getRowCount();
    //Remove rows one by one from the end of the table
    for (int i = rowCount - 1; i >= 0; i--) {
    model1.removeRow(i);
        }
        }
    }
    
    public static boolean isEmpty(JTable billTable) {
        if (billTable != null && billTable.getModel() != null) {
            return billTable.getModel().getRowCount()<=0;
        }
        return false;
    }
    
    public static boolean isEmpty1(JTable medTable) {
        if (medTable != null && medTable.getModel() != null) {
            return medTable.getModel().getRowCount()<=0;
        }
        return false;
    }
    
    private void resetBill(){
        removeBill();
        resetCustomerDetail();
        lblSubTotal.setText("");
    }
     
     
    private void fillSearchTable(){
        model= (DefaultTableModel)searchTable.getModel();
        try{
            
            String queryUsingSelection = "select * from product_stock where proName Like '%" + txtProName.getText() + "%' AND quan > 0  order by proCode";
            
            smtInstance = conInstance.createStatement();
            rs1 = smtInstance.executeQuery(queryUsingSelection);
            if (rs1 != null)
                {
                    int i = 0;
                    while ( rs1.next() ) //step through the result set
                    {
                        i++;//count raws
                    }
                    if(i>0){
                    int j = 0;
                    rs1.beforeFirst();
                    remove();
                    while (rs1.next()) 
                    {
                    
                    
                        proCode= rs1.getString("proCode");
                        String proName = rs1.getString("proName");
                        String qty = rs1.getString("quan");
                        String cost = rs1.getString("cost");
                        model.insertRow(j,new Object[]{proCode,proName,qty,cost});
                        j++;
                    }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No Product Found");
                        txtProName.setText("");
                        txtProName.requestFocus();
                    }
                }
              } catch (SQLException ex) {
             Logger.getLogger(QuotationPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        }
    
    public void resetProSelection() {
        txtProName.setText("");
        txtReq.setText("0");
        txtRate.setText("");
        txtCost.setText("");
    }
    
    private void resetCustomerDetail(){
        txtCustName.setText("");
        txtAddress.setText("");
        txtContact.setText("0");
    }
    
    private void calculate(){
        int i=0;
        double  subTotal = 0;
        if(billTable.getRowCount()!=0){
        while(i<billTable.getRowCount())
        {
        double  subs= Double.parseDouble((String) billTable.getValueAt(i, 4));
        
        subTotal = subTotal+subs;
        
        lblSubTotal.setText( new DecimalFormat("##.##").format(subTotal));
        i++;
        }
        }
        else{
             lblSubTotal.setText("");
        }
    }
    
    public void addToTable() {
        String Product = txtProName.getText();
        String quan = txtReq.getText();
        String rate = txtRate.getText();
        String cost = txtCost.getText();
        
        model1 = (DefaultTableModel) billTable.getModel();
        
        
            model1.insertRow(billTableIndex, new Object[]{proCode,Product, quan, rate, cost});
            billTableIndex++;
         resetProSelection();
        JOptionPane.showMessageDialog(null, "Item Entered");
        calculate();
        
    }
    
    private void saveQuotation(){
        
//            qop.setName(txtCustName.getText());
//            qop.setAddress(txtAddress.getText());
//            qop.setContact(txtContact.getText());
//            qop.setTotal(lblSubTotal.getText());
//           
//            saveProductDetails();
//        
    }
    
    public void saveProductDetails(){
        try{
            int rows=billTable.getRowCount();
            conInstance.setAutoCommit(false);
            
            String queryco = "insert into quotation_tbl (BillNo,proCode,proName,qty,rate,amount) values (?,?,?,?,?,?)";
            PreparedStatement pst = conInstance.prepareStatement(queryco);
                for(int row = 0; row<rows; row++){
                    proCode = (String)billTable.getValueAt(row, 0);
                    String proName = (String) billTable.getValueAt(row, 1);
                    String quan = (String)billTable.getValueAt(row,2);
                    String rate = (String)billTable.getValueAt(row,3);
                    String cost = (String)billTable.getValueAt(row,4);
            
            
                pst.setString(1, BillId);
                pst.setString(2, proCode);
                pst.setString(3, proName);
                pst.setString(4, quan);
                pst.setString(5, rate);
                pst.setString(6, cost);
                
                pst.addBatch();
                }
            pst.executeBatch();
            conInstance.commit();
            String maxId = "Select max(BillNo) from quotation_tbl";
            smtInstance = conInstance.createStatement();
            ResultSet max = smtInstance.executeQuery(maxId);
            while(max.next()){
                BillId = max.getString(1);
            }
            
            resetCustomerDetail();
            resetBill();
            billTableIndex = 0;
            QuotationFrame lf = new QuotationFrame();
                lf.setId(BillId);
//                lf.fillDetails(qop);
                lf.setVisible(true);
            
        }   catch (SQLException ex) {
                Logger.getLogger(SalesPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtReq = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCustName = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        txtRate = new javax.swing.JTextField();
        txtProName = new javax.swing.JTextField();
        txtCost = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        billBtn = new javax.swing.JButton();
        lblSubTotal = new javax.swing.JLabel();

        setBackground(java.awt.Color.gray);

        jPanel1.setBackground(new java.awt.Color(254, 58, 79));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(0));

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Quotation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(java.awt.Color.lightGray);

        jLabel1.setBackground(java.awt.Color.lightGray);
        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel1.setText("Customer Name :");

        addButton.setText("Add ");
        addButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel6.setText("Quantity :");

        jLabel13.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel13.setText("Product Name   :");

        txtReq.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        txtReq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtReqFocusGained(evt);
            }
        });
        txtReq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtReqKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReqKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel12.setText("Cost  :");

        jLabel11.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel11.setText("Rate  :");

        txtCustName.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        txtCustName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustNameActionPerformed(evt);
            }
        });
        txtCustName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustNameKeyReleased(evt);
            }
        });

        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProCode", "Product Name", "Stock", "Rate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchTable.setFillsViewportHeight(true);
        searchTable.setGridColor(java.awt.Color.white);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(searchTable);
        if (searchTable.getColumnModel().getColumnCount() > 0) {
            searchTable.getColumnModel().getColumn(0).setPreferredWidth(6);
            searchTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            searchTable.getColumnModel().getColumn(2).setPreferredWidth(5);
            searchTable.getColumnModel().getColumn(3).setPreferredWidth(8);
        }

        txtRate.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        txtRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRateFocusGained(evt);
            }
        });
        txtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRateKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRateKeyReleased(evt);
            }
        });

        txtProName.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
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

        txtCost.setEditable(false);
        txtCost.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        txtCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCostFocusGained(evt);
            }
        });
        txtCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel3.setText("Address :");

        txtAddress.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        jLabel4.setText("Contact No. :");

        txtContact.setFont(new java.awt.Font("Century Schoolbook L", 1, 12)); // NOI18N
        txtContact.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContactFocusGained(evt);
            }
        });
        txtContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContactKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContactKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel13))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(txtCustName))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtProName))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtReq, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRate)
                            .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtReq, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel11, jLabel12, jLabel13, jLabel3, jLabel4, jLabel6, txtAddress, txtContact, txtCost, txtCustName, txtProName, txtRate, txtReq});

        jPanel3.setBackground(java.awt.Color.lightGray);

        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Item", "Qty", "Rate", "Cost"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        billTable.setFillsViewportHeight(true);
        billTable.setGridColor(java.awt.Color.white);
        billTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(billTable);
        if (billTable.getColumnModel().getColumnCount() > 0) {
            billTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            billTable.getColumnModel().getColumn(0).setHeaderValue("Code");
            billTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            billTable.getColumnModel().getColumn(2).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(3).setPreferredWidth(8);
            billTable.getColumnModel().getColumn(4).setPreferredWidth(8);
        }

        cancelButton.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel21.setText("Sub total :");

        billBtn.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        billBtn.setText("Quotation");
        billBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billBtnActionPerformed(evt);
            }
        });

        lblSubTotal.setBackground(java.awt.Color.white);
        lblSubTotal.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        lblSubTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(cancelButton)
                            .addGap(20, 20, 20)))
                    .addComponent(billBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114)
                .addComponent(billBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel21, lblSubTotal});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCustNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustNameActionPerformed

    private void txtCustNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustNameKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtCustNameKeyReleased

    private void txtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyReleased
                if ((!"".equals(txtReq.getText())) && (!"".equals(txtRate.getText()))) {
              float cost = Float.parseFloat(txtReq.getText()) * Float.parseFloat(txtRate.getText());
                txtCost.setText(new DecimalFormat("##.##").format(cost));
                
            } 
    }//GEN-LAST:event_txtRateKeyReleased

    private void txtCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostFocusGained

    }//GEN-LAST:event_txtCostFocusGained

    private void txtCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyTyped
        if ((!"".equals(txtReq.getText())) && (!"".equals(txtRate.getText()))) {
              float cost = Float.parseFloat(txtReq.getText()) * Float.parseFloat(txtRate.getText());
                txtCost.setText(new DecimalFormat("##.##").format(cost));
                
            }
    }//GEN-LAST:event_txtCostKeyTyped

    private void searchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMouseClicked
        int index = searchTable.getSelectedRow();
        TableModel model2 = searchTable.getModel();
        String Proname = model2.getValueAt(index, 1).toString();
        String ratePrice = model2.getValueAt(index, 3).toString();
        proCode = model2.getValueAt(index, 0).toString();
        txtProName.setText(Proname);
        txtRate.setText(ratePrice);
        txtReq.requestFocus();
        
    }//GEN-LAST:event_searchTableMouseClicked

    private void billTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billTableMouseClicked
                int index = billTable.getSelectedRow();
                DefaultTableModel del = (DefaultTableModel) billTable.getModel();
                del.removeRow(index);
                billTableIndex--;
                calculate();
          
    }//GEN-LAST:event_billTableMouseClicked

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
       resetBill();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void txtReqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReqKeyReleased
        if ((!"".equals(txtReq.getText())) && (!"".equals(txtRate.getText()))) {
              float cost = Float.parseFloat(txtReq.getText()) * Float.parseFloat(txtRate.getText());
                txtCost.setText(new DecimalFormat("##.##").format(cost));
                
            }
    }//GEN-LAST:event_txtReqKeyReleased

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if(!isEmpty1(searchTable)&&(!"".equals(txtCost.getText()))){
        addToTable();
        resetProSelection();
        txtProName.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Nothing to Add");
        }
        
        
        
    }//GEN-LAST:event_addButtonActionPerformed

    private void billBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billBtnActionPerformed
        if(!isEmpty1(billTable)){
            saveQuotation();
        }
        else{
            JOptionPane.showMessageDialog(null,"Nothing to Print");
        }
    }//GEN-LAST:event_billBtnActionPerformed

    private void txtReqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReqKeyPressed
        int key = evt.getKeyCode();
        if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9) || (key == KeyEvent.VK_BACK_SPACE)) {
            txtReq.setEditable(true);
        }
        else if (key == KeyEvent.VK_ENTER) {
            txtRate.requestFocus();
        }
        else {
            evt.consume();
            JOptionPane.showMessageDialog(null, "INVALID INSERT");
        txtReq.setText("");
        }
    }//GEN-LAST:event_txtReqKeyPressed

    private void txtProNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProNameActionPerformed

    private void txtProNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProNameKeyReleased
        fillSearchTable();
    }//GEN-LAST:event_txtProNameKeyReleased

    private void txtReqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtReqFocusGained
        txtReq.selectAll();
    }//GEN-LAST:event_txtReqFocusGained

    private void txtRateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRateFocusGained
        txtRate.selectAll();
    }//GEN-LAST:event_txtRateFocusGained

    private void txtContactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyPressed
       ((AbstractDocument) txtContact.getDocument()).setDocumentFilter(new MyIntFilter());
       int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtProName.requestFocus();
        }
    }//GEN-LAST:event_txtContactKeyPressed

    private void txtContactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyTyped
       if(txtContact.getText().length()>9){
          evt.consume();
      }
    }//GEN-LAST:event_txtContactKeyTyped

    private void txtContactFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactFocusGained
       txtContact.selectAll();
    }//GEN-LAST:event_txtContactFocusGained

    private void txtCustNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustNameKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtAddress.requestFocus();
        }
    }//GEN-LAST:event_txtCustNameKeyPressed

    private void txtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyPressed
       int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            txtContact.requestFocus();
        }
    }//GEN-LAST:event_txtAddressKeyPressed

    private void txtRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRateKeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
        if(!isEmpty1(searchTable)&&(!"".equals(txtCost.getText()))){
        addToTable();
        resetProSelection();
        txtProName.requestFocus();
        }
        else{
            JOptionPane.showMessageDialog(null,"Nothing to Add");
        }
        }    
    }//GEN-LAST:event_txtRateKeyPressed

    private void txtProNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProNameKeyPressed
       int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            txtProName.setText("");
            billBtn.requestFocus();
        }
    }//GEN-LAST:event_txtProNameKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton billBtn;
    private javax.swing.JTable billTable;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtCost;
    public javax.swing.JTextField txtCustName;
    private javax.swing.JTextField txtProName;
    private javax.swing.JTextField txtRate;
    private javax.swing.JTextField txtReq;
    // End of variables declaration//GEN-END:variables
}
