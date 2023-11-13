/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab5;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Quang
 */
public class Lab5Bai2 extends javax.swing.JFrame {

    List<Student> list = new ArrayList<>();
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Java3_Lab5_PS36680;username=sa;password=123;encrypt=false;";
    int index = 0;

    /**
     * Creates new form Lab5Bai2
     */
    public Lab5Bai2() {
        initComponents();
        setLocationRelativeTo(null);
        loadDataToArrayList();
        display(index);
    }

    public void display(int index) {
        Student sv = list.get(index);

        txtMaSV.setText(sv.getMaSV());
        txtHoTen.setText(sv.getHoTen());
        txtEmail.setText(sv.getEmail());
        txtSoDT.setText(sv.getSoDT());
        if (!sv.isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtDiaChi.setText(sv.getDiaChi());
    }

    public void loadDataToArrayList() {
        try {
            Connection con = DriverManager.getConnection(url);
            Statement stm = con.createStatement();
            String sql = "select * from students";
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                list.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6), rs.getBoolean(5)));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void previousStudent() {
        if (index == 0) {
            JOptionPane.showMessageDialog(this, "Đang ở đầu danh sách");
            return;
        }

        index -= 1;
        display(index);
    }

    public void nextStudent() {
        if (index == list.size() - 1) {
            JOptionPane.showMessageDialog(this, "Đang ở cuối danh sách");
            return;
        }

        index += 1;
        display(index);
    }

    public void deleteStudent() {
        if (validateInfo()) {
            if (txtMaSV.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Nhập mã sinh viên để xoá");
                return;
            }

            try {
                Connection con = DriverManager.getConnection(url);
                String sql = "delete from students where masv = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, txtMaSV.getText());
                int rowEffect = stm.executeUpdate();

                if (rowEffect == 0) {
                    JOptionPane.showMessageDialog(this, "Không tồn tại mã sinh viên này trong danh sách");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Đã xoá");
                con.close();

                if (list.size() > 1) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getMaSV().equals(txtMaSV.getText())) {
                            list.remove(list.get(i));
                            System.out.println("c");
                            System.out.println(i);
                            if (i == 0) {
                                index = i;
                                display(index);
                            } else {
                                index = i - 1;
                                display(index);
                            }
                        }
                        System.out.println(list.get(i).getMaSV());
                        System.out.println(txtMaSV.getText());
                    }
                } else {
                    if (list.get(0).getMaSV().equals(txtMaSV.getText())) {
                        list.remove(list.get(0));
                        newStudent();
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    public void newStudent() {
        txtMaSV.setText(null);
        txtHoTen.setText(null);
        txtEmail.setText(null);
        txtSoDT.setText(null);
        buttonGroup1.clearSelection();
        txtDiaChi.setText(null);
        txtMaSV.requestFocus();
    }

    public void updateStudent() {
        if (validateInfo()) {
            if (txtMaSV.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Nhập mã sinh viên để cập nhật");
                return;

            }

            try {
                Connection con = DriverManager.getConnection(url);
                String sql = "update students set hoten = ?, email = ?, sodt = ?, gioitinh = ?, diachi = ? where masv = ?;";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, txtHoTen.getText());
                stm.setString(2, txtEmail.getText());
                stm.setString(3, txtSoDT.getText());
                stm.setInt(4, rdoNam.isSelected() ? 0 : 1);
                stm.setString(5, txtDiaChi.getText());
                stm.setString(6, txtMaSV.getText());

                int rowEffect = stm.executeUpdate();
                if (rowEffect == 0) {
                    JOptionPane.showMessageDialog(this, "Không tồn tại mã sinh viên này trong danh sách");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Đã cập nhật");
                con.close();

                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i).getMaSV().equals(txtMaSV.getText())) {
                        list.get(i).setHoTen(txtHoTen.getText());
                        list.get(i).setEmail(txtEmail.getText());
                        list.get(i).setSoDT(txtSoDT.getText());
                        list.get(i).setGioiTinh(rdoNam.isSelected() ? false : true);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }

    public boolean checkNull() {
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống mã sinh viên");
            txtMaSV.requestFocus();
            return false;
        }
        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống họ tên sinh viên");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống email sinh viên");
            txtEmail.requestFocus();
            return false;
        }
        if (txtSoDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống số điện thoại sinh viên");
            txtSoDT.requestFocus();
            return false;
        }

        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính cho sinh viên");
            return false;
        }

        if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống địa chỉ sinh viên");
            txtDiaChi.requestFocus();
            return false;
        }

        return true;
    }

    public boolean validateInfo() {
        if (checkNull()) {

            if (!txtHoTen.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                JOptionPane.showMessageDialog(this, "Vui lòng không nhập ký tự số và ký tự đặc biệt");
                txtHoTen.requestFocus();
                return false;
            }

            if (!txtEmail.getText().matches("\\w+@\\w+(\\.\\w+)+")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email (***@***.***)");
                txtEmail.requestFocus();
                return false;
            }

            if (!txtSoDT.getText().matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số điện thoại (09*** - 10 chữ số)");
                txtSoDT.requestFocus();
                return false;
            }

            return true;
        }

        return false;
    }

    public void addStudent() {
        if (validateInfo()) {
            for (Student std : list) {
                if (std.getMaSV().equals(txtMaSV.getText())) {
                    JOptionPane.showMessageDialog(this, "Mã sinh viên này đã tồn tại");
                    txtMaSV.requestFocus();
                    return;
                }
            }

            try {
                Connection con = DriverManager.getConnection(url);
                String sql = "insert into students values (?, ?, ?, ?, ?, ?);";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, txtMaSV.getText());
                stm.setString(2, txtHoTen.getText());
                stm.setString(3, txtEmail.getText());
                stm.setString(4, txtSoDT.getText());
                stm.setInt(5, rdoNam.isSelected() ? 0 : 1);
                stm.setString(6, txtDiaChi.getText());
                stm.execute();

                con.close();
                list.add(new Student(txtMaSV.getText(), txtHoTen.getText(), txtEmail.getText(), txtSoDT.getText(), txtDiaChi.getText(), !rdoNam.isSelected()));

                index = list.size() - 1;
                System.out.println("b");
                System.out.println(list.size());
                JOptionPane.showMessageDialog(this, "Đã thêm");
            } catch (Exception ex) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSoDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Quản Lý Users");

        jLabel2.setText("Mã SV:");

        jLabel3.setText("Họ tên:");

        jLabel4.setText("Email:");

        jLabel5.setText("Số ĐT:");

        jLabel6.setText("Giới tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel7.setText("Địa chỉ:");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        btnAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\add.png")); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\delete.png")); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\edit.png")); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\save.png")); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnFirst.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\rewind.png")); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrevious.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\previous.png")); // NOI18N
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\next.png")); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\fast_forward.png")); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(btnFirst)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrevious)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnAdd)
                        .addGap(26, 26, 26)
                        .addComponent(btnDelete)
                        .addGap(30, 30, 30)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirst, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrevious, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLast, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        index = 0;
        display(index);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        previousStudent();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextStudent();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        index = list.size() - 1;
        display(index);
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteStudent();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        newStudent();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateStudent();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        addStudent();
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(Lab5Bai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lab5Bai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lab5Bai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lab5Bai2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lab5Bai2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtSoDT;
    // End of variables declaration//GEN-END:variables
}
