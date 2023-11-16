/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab6;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quang
 */
public class Standard extends javax.swing.JFrame {

    //DBCC CHECKIDENT (['TABLENAME'], RESEED, 0);
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Java3_Lab6_PS36680;username=sa;password=123;encrypt=false;";
    String header[] = {"Name", "Standard"};
    DefaultTableModel tblModel = new DefaultTableModel(header, 0);

    List<Float> listFee = new ArrayList<>();
    int index = -1;

    /**
     * Creates new form Standard
     */
    public Standard() {
        initComponents();
        setLocationRelativeTo(null);
        fillComboBox();
        loadTable();
        lockBtn();
        lockText();
        btnInsert.setEnabled(false);
    }

    public Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void clearForm() {
        txtName.setText(null);
        txtAddress.setText(null);
        txtParentName.setText(null);
        txtContact.setText(null);
        cbbStandard.setSelectedIndex(0);
        cbbFee.setSelectedIndex(0);
        lockBtn();
    }

    public void lockBtn() {
        btnInsert.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnNext.setEnabled(true);
        btnPrevious.setEnabled(true);
    }

    public void unlockBtn() {
        btnInsert.setEnabled(false);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(false);
    }

    public void lockText() {
        txtName.setEnabled(false);
        txtAddress.setEnabled(false);
        txtParentName.setEnabled(false);
        txtContact.setEnabled(false);
        cbbStandard.setEnabled(false);
        cbbFee.setEnabled(false);
    }

    public void unlockText() {
        txtName.setEnabled(true);
        txtAddress.setEnabled(true);
        txtParentName.setEnabled(true);
        txtContact.setEnabled(true);
        cbbStandard.setEnabled(true);
        cbbFee.setEnabled(true);
    }

    public void fillComboBox() {
        try {
            Connection con = getConnection(url);
            String sql = "select * from standards";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                cbbStandard.addItem(rs.getString("standard"));
                listFee.add(rs.getFloat("fee"));
            }

            rs.close();
            stm.close();
            con.close();
        } catch (Exception ex) {
        }
    }

    public void loadTable() {
        try {
            tblModel.setRowCount(0);

            Connection con = getConnection(url);
            String sql = "select name, standard from students";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tblModel.addRow(new Object[]{rs.getString(1), rs.getString(2)});
            }

            tblStandard.setModel(tblModel);

            rs.close();
            stm.close();
            con.close();
        } catch (Exception ex) {

        }
    }

    public void insertStudent() {
        if (validateForm()) {
            try {
                LocalDateTime curentTime = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(curentTime);

                Connection con = getConnection(url);
                String sqlStd = "insert into students values (?, ?, ?, ?, ?, ?);";
                PreparedStatement stm = con.prepareStatement(sqlStd);
                stm.setString(1, txtName.getText());
                stm.setString(2, txtAddress.getText());
                stm.setString(3, txtParentName.getText());
                stm.setString(4, txtContact.getText());
                stm.setString(5, (String) cbbStandard.getSelectedItem());
                stm.setTimestamp(6, timestamp);

                int rowEffect = stm.executeUpdate();

                if (rowEffect != 0) {
                    JOptionPane.showMessageDialog(this, "Added");
                }

                stm.close();
                con.close();

                clearForm();
                loadTable();
                lockText();
                btnInsert.setEnabled(false);
            } catch (Exception ex) {
            }
        }
    }

    public void selectStudent(int index) {
        try {
            Connection con = getConnection(url);
            String sql = "select * from students order by regid offset ? rows fetch next 1 row only;";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setInt(1, index);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                txtName.setText(rs.getString(2));
                txtAddress.setText(rs.getString(3));
                txtParentName.setText(rs.getString(4));
                txtContact.setText(rs.getString(5));
                cbbStandard.setSelectedItem(rs.getString(6));
            }

            rs.close();
            stm.close();
            con.close();
        } catch (Exception ex) {
        }
    }

    public void updateStudent() {
        if (validateForm()) {
            try {
                Connection con = getConnection(url);
                String sql = "update students set name = ?, address = ?, parentname = ?, phone = ?, standard = ? where regid = (select regid from students order by regid offset ? rows fetch next 1 row only);";
                PreparedStatement stm = con.prepareStatement(sql);

                stm.setString(1, txtName.getText());
                stm.setString(2, txtAddress.getText());
                stm.setString(3, txtParentName.getText());
                stm.setString(4, txtContact.getText());
                stm.setString(5, (String) cbbStandard.getSelectedItem());
                stm.setInt(6, index);

                int rowEffect = stm.executeUpdate();

                if (rowEffect != 0) {
                    JOptionPane.showMessageDialog(this, "Updated");
                }

                stm.close();
                con.close();

                loadTable();
                clearForm();
                lockText();
            } catch (Exception ex) {
            }
        }
    }

    public void deleteStudent() {
        try {
            Connection con = getConnection(url);

            String sql = "delete from students where regid = (select regid from students order by regid offset ? rows fetch next 1 row only);";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setInt(1, index);

            int rowEffect = stm.executeUpdate();

            if (rowEffect != 0) {
                JOptionPane.showMessageDialog(this, "Deleted");
            }

            stm.close();
            con.close();

            loadTable();
            lockText();
            clearForm();
            index = -1;
        } catch (Exception ex) {

        }
    }

    public void nextStudent() {
        if (index == tblStandard.getRowCount() - 1) {
            index = -1;
        }

        index += 1;
        selectStudent(index);
        tblStandard.setRowSelectionInterval(index, index);
        unlockBtn();
    }

    public void previousStudent() {
        if (index <= 0) {
            index = tblStandard.getRowCount();
        }

        index -= 1;
        selectStudent(index);
        tblStandard.setRowSelectionInterval(index, index);
        unlockBtn();
    }

    public boolean checkNull() {
        if (txtName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please do not leave blank");
            txtName.requestFocus();
            return false;
        }

        if (txtAddress.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please do not leave blank");
            txtAddress.requestFocus();
            return false;
        }

        if (txtParentName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please do not leave blank");
            txtParentName.requestFocus();
            return false;
        }

        if (txtContact.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please do not leave blank");
            txtContact.requestFocus();
            return false;
        }

        return true;
    }

    public boolean validateForm() {
        if (checkNull()) {
            if (!txtName.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                JOptionPane.showMessageDialog(this, "Please do not input digits or special characters");
                txtName.requestFocus();
                return false;
            }

            if (!txtParentName.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                JOptionPane.showMessageDialog(this, "Please do not input digits or special characters");
                txtParentName.requestFocus();
                return false;
            }

            if (!txtContact.getText().matches("09\\d{8}")) {
                JOptionPane.showMessageDialog(this, "Please input a valid phone number (09*** - 10 digits)");
                txtContact.requestFocus();
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblStandard = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtParentName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbbStandard = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbbFee = new javax.swing.JComboBox<>();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblStandard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Standard"
            }
        ));
        tblStandard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStandardMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStandard);

        jLabel1.setText("Name:");

        jLabel2.setText("Address:");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        jLabel3.setText("ParentName:");

        jLabel4.setText("ContactNo:");

        jLabel5.setText("Standard:");

        cbbStandard.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbStandardItemStateChanged(evt);
            }
        });
        cbbStandard.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbbStandardPropertyChange(evt);
            }
        });

        jLabel6.setText("Fees:");

        btnNew.setText("New");
        btnNew.setMaximumSize(null);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnInsert.setText("Insert");
        btnInsert.setMaximumSize(null);
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.setMaximumSize(null);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.setMaximumSize(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.setMaximumSize(null);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrevious.setText("Previous");
        btnPrevious.setMaximumSize(new java.awt.Dimension(200, 200));
        btnPrevious.setMinimumSize(new java.awt.Dimension(100, 23));
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.setMaximumSize(null);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.setMaximumSize(null);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(txtParentName)
                            .addComponent(txtContact)
                            .addComponent(cbbStandard, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbFee, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtParentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbbStandard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbbFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
        unlockText();
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);
        tblStandard.clearSelection();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insertStudent();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void cbbStandardPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbbStandardPropertyChange
        for (int i = 0; i < cbbStandard.getItemCount(); i++) {
            if (cbbStandard.getSelectedIndex() == i) {
                cbbFee.removeAllItems();
                cbbFee.addItem(String.valueOf(listFee.get(i)));
            }
        }
    }//GEN-LAST:event_cbbStandardPropertyChange

    private void cbbStandardItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbStandardItemStateChanged
    }//GEN-LAST:event_cbbStandardItemStateChanged

    private void tblStandardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStandardMouseClicked
        index = tblStandard.getSelectedRow();
        selectStudent(index);
        unlockBtn();
        lockText();
        btnNext.setEnabled(true);
        btnPrevious.setEnabled(true);
    }//GEN-LAST:event_tblStandardMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        unlockText();
        btnUpdate.setEnabled(true);
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateStudent();
        btnNext.setEnabled(true);
        btnPrevious.setEnabled(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteStudent();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextStudent();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        previousStudent();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Standard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Standard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Standard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Standard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Standard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbbFee;
    private javax.swing.JComboBox<String> cbbStandard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblStandard;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtParentName;
    // End of variables declaration//GEN-END:variables
}
