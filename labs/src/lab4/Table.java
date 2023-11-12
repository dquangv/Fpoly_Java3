/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab4;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quang
 */
public class Table extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    int index = -1;

    /**
     * Creates new form Table
     */
    public Table() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public boolean checkNull() {
        if (txtMaSP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Khong de trong o nay");
            txtMaSP.requestFocus();
            return false;
        }

        if (txtTenSP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Khong de trong o nay");
            txtTenSP.requestFocus();
            return false;
        }

        if (txtDonGia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Khong de trong o nay");
            txtDonGia.requestFocus();
            return false;
        }

        if (txtNCC.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Khong de trong o nay");
            txtNCC.requestFocus();
            return false;
        }

        return true;
    }

    public void themSP() {
        if (checkNull()) {
            
            if (txtDonGia.getText().matches("-\\d+")) {
                JOptionPane.showMessageDialog(this, "Don gia khong duoc la so am");
                txtDonGia.requestFocus();
                return;
            }

            if (!txtDonGia.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Don gia phai la so");
                txtDonGia.requestFocus();
                return;
            }

            if (!txtNCC.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                JOptionPane.showMessageDialog(this, "Ten nha cung cap khong chua ki tu so va ki tu dac biet");
                txtNCC.requestFocus();
                return;
            }

            tblModel = (DefaultTableModel) tblSanPham.getModel();
            tblModel.addRow(new Object[]{txtMaSP.getText(), txtTenSP.getText(), cbbDVT.getSelectedItem(), txtDonGia.getText(), txtNCC.getText()});
        }
    }

    public void chonSP() {
        index = tblSanPham.getSelectedRow();

        txtMaSP.setText((String) tblSanPham.getValueAt(index, 0));
        txtTenSP.setText((String) tblSanPham.getValueAt(index, 1));
        cbbDVT.setSelectedItem((String) tblSanPham.getValueAt(index, 2));
        txtDonGia.setText((String) tblSanPham.getValueAt(index, 3));
        txtNCC.setText((String) tblSanPham.getValueAt(index, 4));
    }

    public void xoaSP() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chon dong ban muon xoa");
            return;
        }

        int choose = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa khong?");

        if (choose == JOptionPane.YES_OPTION) {
            tblModel.removeRow(index);
            index = -1;
            JOptionPane.showMessageDialog(this, "Da xoa");
        } else {
            return;
        }
    }

    public void suaSP() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chon dong ban muon sua");
            return;
        }

        int choose = JOptionPane.showConfirmDialog(this, "Ban co chac muon dieu chinh khong?");

        if (choose == JOptionPane.YES_OPTION) {
            if (checkNull()) {
                if (!txtTenSP.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                    JOptionPane.showMessageDialog(this, "Ten san pham khong chua ki tu so va ki tu dac biet");
                    txtTenSP.requestFocus();
                    return;
                }
                if (txtDonGia.getText().matches("-\\d+")) {
                    JOptionPane.showMessageDialog(this, "Don gia khong duoc la so am");
                    txtDonGia.requestFocus();
                    return;
                }

                if (!txtDonGia.getText().matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Don gia phai la so");
                    txtDonGia.requestFocus();
                    return;
                }

                if (!txtNCC.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                    JOptionPane.showMessageDialog(this, "Ten nha cung cap khong chua ki tu so va ki tu dac biet");
                    txtNCC.requestFocus();
                    return;
                }
                
                tblModel.setValueAt(txtMaSP.getText(), index, 0);
                tblModel.setValueAt(txtTenSP.getText(), index, 1);
                tblModel.setValueAt(cbbDVT.getSelectedItem(), index, 2);
                tblModel.setValueAt(txtDonGia.getText(), index, 3);
                tblModel.setValueAt(txtNCC.getText(), index, 4);
                
                index = -1;
                JOptionPane.showMessageDialog(this, "Da dieu chinh");
            }
        } else {
            return;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbbDVT = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNCC = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 102, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Danh sach San Pham", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 0, 51))); // NOI18N

        tblSanPham.setBackground(new java.awt.Color(255, 255, 255));
        tblSanPham.setForeground(new java.awt.Color(0, 0, 0));
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ma SP", "Ten SP", "DVT", "Don gia ban", "Nha Cung Cap"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));

        jLabel1.setText("Ma san pham");

        txtMaSP.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSP.setForeground(new java.awt.Color(0, 51, 255));

        jLabel2.setText("Ten san pham");

        txtTenSP.setBackground(new java.awt.Color(255, 255, 255));
        txtTenSP.setForeground(new java.awt.Color(0, 51, 255));

        jLabel3.setText("Don vi tinh");

        cbbDVT.setForeground(new java.awt.Color(0, 51, 255));
        cbbDVT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chai", "Thung", "Chiec", "Cai" }));

        jLabel4.setText("Don gia");

        txtDonGia.setBackground(new java.awt.Color(255, 255, 255));
        txtDonGia.setForeground(new java.awt.Color(0, 51, 255));

        jLabel5.setText("Nha cung cap");

        txtNCC.setBackground(new java.awt.Color(255, 255, 255));
        txtNCC.setForeground(new java.awt.Color(0, 51, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSP)
                    .addComponent(txtTenSP)
                    .addComponent(cbbDVT, 0, 147, Short.MAX_VALUE))
                .addGap(98, 98, 98)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDonGia)
                    .addComponent(txtNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        btnThem.setText("Them San Pham");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoa San Pham");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Dieu chinh thong tin");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnXoa)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(140, 140, 140))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnSua))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        themSP();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        chonSP();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        suaSP();
    }//GEN-LAST:event_btnSuaActionPerformed

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
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Table().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbbDVT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNCC;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
