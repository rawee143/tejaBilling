/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Dao.DataBase_Connection;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ranjan
 */
public class salesSummaryPanel extends javax.swing.JPanel {
        protected Connection conInstance;
        protected Statement smtInstance,smtUsingDate;
        ResultSet rs,rs1, rsOpen,rsClose;
        protected String queryUsingSelection,queryOpen, queryClose;
        protected String excelFilePath = null;
        DataBase_Connection dao;
        DefaultTableModel model, model1;
        private JTextField filename = new JTextField(), dir = new JTextField();
       

    /**
     * Creates new form salesSummaryPanel
     */
    public salesSummaryPanel() {
        initComponents();
        dir.setEditable(false);
        filename.setEditable(false);
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        ButtonGroup();
        dateFrom.setDate(Calendar.getInstance().getTime());
        dateTo.setDate(Calendar.getInstance().getTime());
    }
    
    public void excelPerformed() {
      JFileChooser c = new JFileChooser();
      // Demonstrate "Save" dialog:
      int rVal = c.showSaveDialog(salesSummaryPanel.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
        filename.setText(c.getSelectedFile().getName());
        dir.setText(c.getCurrentDirectory().toString());
        
          excelFilePath = dir.getText()+"/"+filename.getText();
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {
        filename.setText("You pressed cancel");
        dir.setText("");
      }
    }
    
    private void ButtonGroup() {
        ButtonGroup sb = new ButtonGroup();
        sb.add(rbnDate);
        sb.add(rbnMonthly);
        
    }

    private void remove(){
        
        if(!isEmpty1(salesTable)){
            int rowCount = model.getRowCount();
    //Remove rows one by one from the end of the table
    for (int i = rowCount - 1; i >= 0; i--) {
    model.removeRow(i);
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
    
    private void fillTable(){
         model= (DefaultTableModel)salesTable.getModel();
        try
        {
            java.sql.Date dFrom = new java.sql.Date(dateFrom.getDate().getTime());
            java.sql.Date dTo = new java.sql.Date(dateTo.getDate().getTime());
            if(rbnMonthly.isSelected() ||rbnDate.isSelected()){
            if(rbnMonthly.isSelected()){
            queryOpen ="Select sum(totalDue) from productBills where date <'" + dFrom + "'";
            queryUsingSelection = "select * from productBills where (date between '"+dFrom+"' And '"+dTo+"')order by date";
            queryClose ="Select sum(totalDue) from productBills where (date between '"+dFrom+"' And '"+dTo+"')";
            
        }
            else if(rbnDate.isSelected()){
            queryOpen ="Select sum(totalDue) from productBills where date <'" + dFrom + "'";
            queryUsingSelection = "select * from productBills where date ='" + dFrom + "' order by date";
            queryClose ="Select sum(totalDue) from productBills where date ='" + dFrom + "'";
            
        }
            smtInstance = conInstance.createStatement();
            rsOpen = smtInstance.executeQuery(queryOpen);
            while ( rsOpen.next() ) //step through the result set
                    {
                      Double sum = rsOpen.getDouble(1);
                        txtOpen.setText(new DecimalFormat("##.##").format(sum));
                        if(txtOpen.getText().isEmpty()){
                            txtOpen.setText("0.0"); 
                        }
                    }
            }
            else{
            //queryOpen ="Select sum(product_sales.amount) from product_sales, productBills where product_sales.BillNo = productBills.BillNo and productBills.date <'" + dFrom + "'";
            queryUsingSelection = "select * from productBills order by date";
            queryClose ="Select sum(totalDue) from productBills";
            txtOpen.setText("0.0");    
        }
            
            smtInstance = conInstance.createStatement();
            rsClose = smtInstance.executeQuery(queryClose);
            while ( rsClose.next() ) //step through the result set
                    {
                        Double sum = Double.parseDouble(txtOpen.getText())+ rsClose.getDouble(1);
                        txtClose.setText(new DecimalFormat("##.##").format(sum));
                        if(txtClose.getText().isEmpty()){
                            txtClose.setText("0.0"); 
                        }
                    }
            
            smtUsingDate = conInstance.createStatement();
            rs1 = smtUsingDate.executeQuery(queryUsingSelection);
            
            
            if (rs1 != null){
                    
                int i = 0;
                while ( rs1.next() ) //step through the result set
                    {
                        i++;//count raws
                    }
                    int j = 0;
                    rs1.beforeFirst();
                    remove();
                    while (rs1.next()) 
                    {
                        Date dbDate = rs1.getDate("date");
                        DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                        String todayDate =dateformat.format(dbDate);
                        String billId = rs1.getString("BillNo");
                        String NAME = rs1.getString("custName");
                        String total = rs1.getString("totalDue");
                        //String rate = rs1.getString("product_sales.rate");
                        Double amt = (Double.parseDouble(total));
                        String amount = Double.toString(amt);

                        model.insertRow(j,new Object[]{todayDate,billId,NAME,amount});
                        j++;
                    }
                }
              } catch (SQLException ex) {
             Logger.getLogger(salesSummaryPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
        finally
        {
             try {
                 rs1.close();
             } catch (SQLException ex) {
                 Logger.getLogger(salesSummaryPanel.class.getName()).log(Level.SEVERE, null, ex);
             }
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

        jPanel3 = new javax.swing.JPanel();
        rbnDate = new javax.swing.JRadioButton();
        btnSearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        salesTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtClose = new javax.swing.JTextField();
        txtOpen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        dateTo = new com.toedter.calendar.JDateChooser();
        rbnMonthly = new javax.swing.JRadioButton();
        ExportToExcel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(java.awt.Color.gray);

        jPanel3.setBackground(java.awt.Color.lightGray);
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        rbnDate.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        rbnDate.setText("Daily");
        rbnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnDateActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Invoice", "Customer Name", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        salesTable.setFillsViewportHeight(true);
        salesTable.setGridColor(java.awt.Color.white);
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(salesTable);
        if (salesTable.getColumnModel().getColumnCount() > 0) {
            salesTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            salesTable.getColumnModel().getColumn(1).setPreferredWidth(8);
            salesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            salesTable.getColumnModel().getColumn(3).setPreferredWidth(8);
        }

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel4.setText("Closing Balance :");

        txtClose.setEditable(false);
        txtClose.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N

        txtOpen.setEditable(false);
        txtOpen.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel2.setText("Opening Balance :");

        rbnMonthly.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        rbnMonthly.setText("Monthly");
        rbnMonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMonthlyActionPerformed(evt);
            }
        });

        ExportToExcel.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        ExportToExcel.setText("Export to Excel");
        ExportToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportToExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtClose, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ExportToExcel)
                .addGap(59, 59, 59))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rbnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(111, 111, 111)
                                .addComponent(rbnMonthly, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbnDate)
                    .addComponent(rbnMonthly))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch))
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ExportToExcel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(java.awt.Color.lightGray);

        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel1.setText("Sales Summary");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(379, 379, 379))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnDateActionPerformed
        if(rbnDate.isSelected()){
            dateTo.setEnabled(false);
            
        }
    }//GEN-LAST:event_rbnDateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        fillTable();
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
            try {
                int index = salesTable.getSelectedRow();
                model  =(DefaultTableModel)salesTable.getModel();
                String billId = model.getValueAt(index, 1).toString();
                
                String sql= "SELECT\n" +
                        "     productBills.BillNo AS productBills_BillNo,\n" +
                        "     productBills.date AS productBills_date,\n" +
                        "     productBills.custName AS productBills_custName,\n" +
                        "     productBills.address AS productBills_address,\n" +
                        "     productBills.contact AS productBills_contact,\n" +
                        "     productBills.totalDue AS productBills_totalDue,\n" +
                        "     productBills.paid AS productBills_paid,\n" +
                        "     productBills.due AS productBills_due,\n" +
                        "     product_sales.id AS product_sales_id,\n" +
                        "     product_sales.BillNo AS product_sales_BillNo,\n" +
                        "     product_sales.proCode AS product_sales_proCode,\n" +
                        "     product_sales.proName AS product_sales_proName,\n" +
                        "     product_sales.qty AS product_sales_qty,\n" +
                        "     product_sales.rate AS product_sales_rate,\n" +
                        "     product_sales.amount AS product_sales_amount\n" +
                        "FROM\n" +
                        "     productBills productBills,\n" +
                        "     product_sales product_sales WHERE productBills.BillNo= product_sales.BillNo and product_sales.BillNo ='"+billId+"'";
                InputStream url7 = getClass().getResourceAsStream("/report/billreport/bill.jrxml");
                JasperDesign jd = JRXmlLoader.load(url7);
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, conInstance);
                JasperViewer.viewReport(jp,false);
                JasperExportManager.exportReportToPdfFile(jp,"sample_report.pdf");
            } catch (JRException ex) {
                Logger.getLogger(salesSummaryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_salesTableMouseClicked

    private void rbnMonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnMonthlyActionPerformed
        dateTo.setEnabled(true);
    }//GEN-LAST:event_rbnMonthlyActionPerformed

    private void ExportToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportToExcelActionPerformed
        excelPerformed();

        if (excelFilePath != null){
        try {
            exportTable(salesTable, new File(excelFilePath));
            String file = "src/report/tabledata.xls";
            File pdfFile = new File(excelFilePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Awt Desktop is not supported!");
                    System.out.println("Awt Desktop is not supported!");
                }
            } else {
            }
        } catch (IOException ex) {
            Logger.getLogger(salesSummaryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_ExportToExcelActionPerformed
public void exportTable(JTable table, File file) throws IOException {
            TableModel model2 = table.getModel();
            
        try (FileWriter out = new FileWriter(file)) {
            
            for(int i=0; i < model2.getColumnCount(); i++) {
                out.write(model2.getColumnName(i) + "\t");
                
            }
            out.write("\n");

            for(int i=0; i< model2.getRowCount(); i++) {
                for(int j=0; j < model2.getColumnCount(); j++) {
                    out.write(model2.getValueAt(i,j).toString()+"\t");
                }
                out.write("\n");
            }
        }
        
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExportToExcel;
    public javax.swing.JButton btnSearch;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbnDate;
    private javax.swing.JRadioButton rbnMonthly;
    private javax.swing.JTable salesTable;
    private javax.swing.JTextField txtClose;
    private javax.swing.JTextField txtOpen;
    // End of variables declaration//GEN-END:variables
}
