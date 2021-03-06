/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Dao.DataBase_Connection;
import PanelForms.Test.Encryption;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author sandeep
 */
public class LoginFrame extends javax.swing.JFrame {

    protected Connection conInstance;
    protected Statement smtInstance;
    ResultSet rs;
    DataBase_Connection dao;
    public String namefromDatabase;
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gbr;
    forgotPasswordPanel fpp;
    regPanel rp;
    BufferedImage bi;
    //Login_pojo logp;

    /**
     * Creates new form LoginFrame
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LoginFrame() {
        //getImage();
        initComponents();
        dao = new DataBase_Connection();
        conInstance = dao.getConnection();
        this.getRootPane().setDefaultButton(Submit_btn);
        this.setLocationRelativeTo(null);
        try {
            bi = ImageIO.read(getClass().getResource("/BillingIcon/invoice.png"));
            this.setIconImage(bi);
             this.setTitle("E-Bill Book || Sri Vijaya Teja Communications");
        } catch (IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtUserName.requestFocus();
        introPanel.setLayout(layout);
        init();
        addPanel();
        state();
       logInPanel.setVisible(true);
    }
    
    private void init(){
        gbr= new GridBagConstraints();
        fpp = new forgotPasswordPanel();
        rp = new regPanel();
        
    }
    
    private void addPanel(){
        introPanel.add(logInPanel,gbr);
        introPanel.add(fpp, gbr);
        introPanel.add(rp,gbr);
    }
    
    private void state(){
        
        logInPanel.setVisible(false);
        fpp.setVisible(false);
        rp.setVisible(false);
    }
    
      
    
    private void login(){
        try {
            String queryToGetName = "select UserId,Password,UserName from Login_tbl where UserId = '" + txtUserName.getText()+ "'";
            
            String user = txtUserName.getText();
            String password1 = new String(pwd_txt.getPassword());
            String password = Encryption.SHA1(password1);
            
            smtInstance = conInstance.createStatement();
            rs = smtInstance.executeQuery(queryToGetName);
            while (rs.next()) {

                String usname = rs.getString(1);
                String pswrd = rs.getString(2);
                namefromDatabase = rs.getString(3);

                if (user.equals(usname) && password.equals(pswrd) && !"".equals(password1)) {
                    homeFrame cal = new homeFrame();
                    cal.setGlobalVariableCashier(namefromDatabase);
                    cal.setVisible(true);
                    close();

                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect login or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    pwd_txt.setText("");
                    pwd_txt.requestFocus();
                }
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void close() {
        WindowEvent winClosingEvent = new WindowEvent(SwingUtilities.getWindowAncestor(introPanel), WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
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
        introPanel = new javax.swing.JPanel();
        logInPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Submit_btn = new javax.swing.JButton();
        txtUserName = new javax.swing.JTextField();
        pwd_txt = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        forgotPassword = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TitlePanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblreg = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(java.awt.SystemColor.control);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));

        introPanel.setBackground(java.awt.Color.gray);
        introPanel.setBorder(null);
        introPanel.setOpaque(false);

        logInPanel.setBackground(java.awt.SystemColor.control);
        logInPanel.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel3.setText("Password        :");

        Submit_btn.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        Submit_btn.setText("Login");
        Submit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Submit_btnActionPerformed(evt);
            }
        });

        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserNameKeyPressed(evt);
            }
        });

        pwd_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pwd_txtKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel4.setText("Click Here -->");

        forgotPassword.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        forgotPassword.setForeground(java.awt.Color.blue);
        forgotPassword.setText("Forgot Password ??");
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotPasswordMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel2.setText("Username      :");

        javax.swing.GroupLayout logInPanelLayout = new javax.swing.GroupLayout(logInPanel);
        logInPanel.setLayout(logInPanelLayout);
        logInPanelLayout.setHorizontalGroup(
            logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logInPanelLayout.createSequentialGroup()
                .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(logInPanelLayout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pwd_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 221, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logInPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Submit_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logInPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(forgotPassword)))))
                .addContainerGap())
        );
        logInPanelLayout.setVerticalGroup(
            logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logInPanelLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pwd_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(Submit_btn)
                .addGap(18, 18, 18)
                .addGroup(logInPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(forgotPassword)
                    .addComponent(jLabel4))
                .addGap(71, 71, 71))
        );

        logInPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Submit_btn, jLabel2, jLabel3, pwd_txt});

        javax.swing.GroupLayout introPanelLayout = new javax.swing.GroupLayout(introPanel);
        introPanel.setLayout(introPanelLayout);
        introPanelLayout.setHorizontalGroup(
            introPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(introPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logInPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        introPanelLayout.setVerticalGroup(
            introPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logInPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TitlePanel.setBackground(new java.awt.Color(254, 58, 79));

        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 1, 40)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("E- Bill Book");

        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("A Product by Ravi Ranjan");

        jLabel7.setFont(new java.awt.Font("Century Schoolbook L", 0, 11)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("@ 91 - 7382125672");

        jLabel8.setFont(new java.awt.Font("Century Schoolbook L", 0, 11)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("bunty_karn@rediffmail.com");

        javax.swing.GroupLayout TitlePanelLayout = new javax.swing.GroupLayout(TitlePanel);
        TitlePanel.setLayout(TitlePanelLayout);
        TitlePanelLayout.setHorizontalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addGroup(TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TitlePanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TitlePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addGroup(TitlePanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)))
                .addContainerGap())
        );
        TitlePanelLayout.setVerticalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        lblreg.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        lblreg.setForeground(java.awt.Color.blue);
        lblreg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/add-profile.png"))); // NOI18N
        lblreg.setText("Register Now.");
        lblreg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblregMouseClicked(evt);
            }
        });

        lblLogin.setFont(new java.awt.Font("Century Schoolbook L", 0, 14)); // NOI18N
        lblLogin.setForeground(java.awt.Color.blue);
        lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BillingIcon/avatar.png"))); // NOI18N
        lblLogin.setText("Log In");
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblreg)
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(introPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(TitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogin)
                    .addComponent(lblreg))
                .addGap(18, 18, 18)
                .addComponent(introPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void Submit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Submit_btnActionPerformed
        try {
            if (txtUserName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Username",
                        "Error", JOptionPane.ERROR_MESSAGE);
                txtUserName.requestFocus();
            } else {
                login();
            }
        } catch (HeadlessException headlessException) {
        }
    }//GEN-LAST:event_Submit_btnActionPerformed

    private void forgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotPasswordMouseClicked
        try {
            state();
            fpp.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_forgotPasswordMouseClicked

    private void lblregMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblregMouseClicked
        try {
            state();
            rp.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lblregMouseClicked

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        try {
            state();
            logInPanel.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lblLoginMouseClicked

    private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyPressed
       int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            pwd_txt.requestFocus();
        }
    }//GEN-LAST:event_txtUserNameKeyPressed

    private void pwd_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwd_txtKeyPressed
       int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            Submit_btn.requestFocus();
        }
    }//GEN-LAST:event_pwd_txtKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Submit_btn;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JLabel forgotPassword;
    private javax.swing.JPanel introPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblreg;
    private javax.swing.JPanel logInPanel;
    private javax.swing.JPasswordField pwd_txt;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
