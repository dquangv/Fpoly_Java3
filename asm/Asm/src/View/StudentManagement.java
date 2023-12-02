/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DAO.ConnectionDB;
import Model.Student;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 *
 * @author Quang
 */
public class StudentManagement extends javax.swing.JFrame {

//    StudentDAO stdDAO = new StudentDAO();
    String pathImage = null, nameImage = null;
    int index = -1;
    DefaultTableModel tblModel;
    ConnectionDB connectionDB = new ConnectionDB();
//    List<Student> list = stdDAO.dsSV();

    /**
     * Creates new form StudentManagement
     */
    public StudentManagement() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void addPhoto() {
        JFileChooser photoDialog = new JFileChooser();

        photoDialog.setCurrentDirectory(new File("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\img\\bin"));
        FileNameExtensionFilter extPhoto = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
        photoDialog.setFileFilter(extPhoto);

        if (photoDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = photoDialog.getSelectedFile();
            pathImage = selectedFile.getAbsolutePath();
            nameImage = selectedFile.getName();

            if (checkPhotoFile(selectedFile)) {
                ImageIcon img = new ImageIcon(selectedFile.getAbsolutePath());

                int labelWidth = lblHinh.getWidth();
                int labelHeight = lblHinh.getHeight();

//                double scaleX = (double) labelWidth / img.getIconWidth();
//                double scaleY = (double) labelHeight / img.getIconHeight();
//                double scale = Math.min(scaleX, scaleY);
//
//                int scaleWidth = (int) (img.getIconWidth() * scale);
//                int scaleHeight = (int) (img.getIconHeight() * scale);
                Image scaledImg = img.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                ImageIcon imgIcon = new ImageIcon(scaledImg);

                lblHinh.setText(null);
                lblHinh.setIcon(imgIcon);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn file ảnh (jpg, jpeg, png)");
                addPhoto();
            }
        }
    }

    public boolean checkPhotoFile(File file) {
        String path = file.getName().toLowerCase();
        return path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png");
    }

    public void clearForm() {
        txtMaSV.setText(null);
        txtHoTen.setText(null);
        txtEmail.setText(null);
        txtSDT.setText(null);
        buttonGroup1.clearSelection();
        txtDiaChi.setText(null);
        lblHinh.setText("Thêm hình ảnh");
        lblHinh.setIcon(null);
    }

    public boolean checkNull() {
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên");
            txtMaSV.requestFocus();
            return false;
        }

        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên sinh viên");
            txtHoTen.requestFocus();
            return false;
        }

        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email sinh viên");
            txtEmail.requestFocus();
            return false;
        }

        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại sinh viên");
            txtSDT.requestFocus();
            return false;
        }

        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính sinh viên");
            return false;
        }

        if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ sinh viên");
            txtDiaChi.requestFocus();
            return false;
        }

        if (lblHinh.getText() != null) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm hình sinh viên");
            return false;
        }

        return true;
    }

    public Student setStudent() {
        String maSV = txtMaSV.getText();
        String hoTen = txtHoTen.getText();
        String email = txtEmail.getText();
        String sdt = txtSDT.getText();
        String diaChi = txtDiaChi.getText();
        String hinh = pathImage;
        boolean gioiTinh = rdoNam.isSelected();
        Student std = new Student(maSV, hoTen, email, sdt, diaChi, hinh, gioiTinh);

        return std;
    }

    public void fillTable() {
        tblModel = (DefaultTableModel) tblSinhVien.getModel();
        tblModel.setRowCount(0);

        try {
            Connection con = connectionDB.getConnection();
            String sql = "select * from students";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tblModel.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5) ? "Nam" : "Nữ", rs.getString(6), rs.getString(7)});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        for (Student std : list) {
//            String[] ls = list.get(index).getHinh().split("\\\\");
//            System.out.println(ls);
//            System.out.println(ls.length);
//            tblModel.addRow(new Object[]{std.getMaSV(), std.getHoTen(), std.getEmail(), std.getSdt(), std.isGioiTinh() ? "Nam" : "Nữ", std.getDiaChi(), ls[ls.length - 1]});
//        }
    }

    public void saveStudent() {
        if (checkNull()) {
            if (!txtHoTen.getText().matches("[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*")) {
                JOptionPane.showMessageDialog(this, "Vui lòng không nhập số hay ký tự đặc biệt");
                txtHoTen.requestFocus();
                return;
            }

            if (!txtEmail.getText().matches("\\w+@fpt.edu.vn")) {
                JOptionPane.showMessageDialog(this, "Hệ thống chỉ nhận email của fpt");
                txtEmail.requestFocus();
                return;
            }

            if (!txtSDT.getText().matches("09\\d{8}")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng kiểu số điện thoại 09... (10 chữ số)");
                txtSDT.requestFocus();
                return;
            }

            Student std = setStudent();

//            if (stdDAO.themSV(std) > 0) {
//                JOptionPane.showMessageDialog(this, "Lưu thành công");
//                fillTable();
//            }
        }
    }

    public void showDetail() {
        txtMaSV.setText((String) tblModel.getValueAt(index, 0));
        txtHoTen.setText((String) tblModel.getValueAt(index, 1));
        txtEmail.setText((String) tblModel.getValueAt(index, 2));
        txtSDT.setText((String) tblModel.getValueAt(index, 3));

        System.out.println((String) tblModel.getValueAt(index, 4));
        
        if (((String) tblModel.getValueAt(index, 4)).equals("Nam")) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        txtDiaChi.setText((String) tblModel.getValueAt(index, 5));
        lblHinh.setText(null);
        lblHinh.setIcon(new ImageIcon(new ImageIcon((String) tblModel.getValueAt(index, 6)).getImage().getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), 0)));
    }

    public void selectStudent() {
        index = tblSinhVien.getSelectedRow();

        tblSinhVien.setRowSelectionInterval(index, index);

        showDetail();
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
        txtSDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        lblHinh = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSinhVien = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("Quản Lý Sinh Viên");

        jLabel2.setText("Mã SV:");

        txtMaSV.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Họ tên:");

        txtHoTen.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Email:");

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Số ĐT:");

        txtSDT.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Giới tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel7.setText("Địa chỉ:");

        txtDiaChi.setBackground(new java.awt.Color(255, 255, 255));
        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Thêm hình ảnh");
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblHinhAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        btnNew.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\add.png")); // NOI18N
        btnNew.setText("New");
        btnNew.setPreferredSize(new java.awt.Dimension(99, 39));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\save.png")); // NOI18N
        btnSave.setText("Save");
        btnSave.setPreferredSize(new java.awt.Dimension(99, 39));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\delete.png")); // NOI18N
        btnDelete.setText("Delete");

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\edit.png")); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setPreferredSize(new java.awt.Dimension(99, 39));

        tblSinhVien.setBackground(new java.awt.Color(255, 255, 255));
        tblSinhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Email", "Số ĐT", "Giới tính", "Địa chỉ", "Hình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSinhVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSinhVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSinhVien);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27)
                                .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtHoTen)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNu))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                    .addComponent(txtEmail)
                                    .addComponent(txtSDT))))
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(262, 262, 262))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblHinhAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblHinhAncestorAdded
    }//GEN-LAST:event_lblHinhAncestorAdded

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        addPhoto();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveStudent();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblSinhVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSinhVienMouseClicked
        selectStudent();
    }//GEN-LAST:event_tblSinhVienMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        fillTable();
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblSinhVien;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
