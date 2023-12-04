/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DAO.ConnectionDB;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quang
 */
public class StudentMarkManagement extends javax.swing.JFrame {

    ConnectionDB connectionDB = new ConnectionDB();
    DefaultTableModel tblModel;
    int index = -1;

    /**
     * Creates new form StudentManagement
     */
    public StudentMarkManagement() {
        initComponents();
        setLocationRelativeTo(null);
        lblhoTen.setEnabled(false);
        txtMaSV.setEditable(false);
        lblDTB.setEnabled(false);
    }

    public void clearForm() {
        txtMaSVSearch.setText(null);
        lblhoTen.setText(null);
        txtMaSV.setText(null);
        txtTA.setText(null);
        txtTinHoc.setText(null);
        txtGDTC.setText(null);
        lblDTB.setText(null);
    }

    public boolean checkNull() {
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng search mã sinh viên cần cập nhật điểm");
            txtMaSVSearch.requestFocus();
            return false;
        }

        if (txtTA.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm tiếng anh");
            txtTA.requestFocus();
            return false;
        }

        if (txtTinHoc.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm tin học");
            txtTinHoc.requestFocus();
            return false;
        }

        if (txtGDTC.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm giáo dục thể chất");
            txtGDTC.requestFocus();
            return false;
        }

        return true;
    }

    public boolean checkValidate() {
        if (!txtTA.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
            txtTA.requestFocus();
            return false;
        }

        if (!txtTinHoc.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
            txtTinHoc.requestFocus();
            return false;
        }

        if (!txtGDTC.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số");
            txtGDTC.requestFocus();
            return false;
        }

        return true;
    }

    public void addMarkStudent() {
        String diemTA = txtTA.getText();
        String diemTin = txtTinHoc.getText();
        String diemGDTC = txtGDTC.getText();
        String maSV = txtMaSV.getText();

        if (checkNull()) {
            if (checkValidate()) {
                try {
                    Connection con = connectionDB.getConnection();
                    String sql = "insert into grade values (?, ?, ?, ?)";
                    PreparedStatement stm = con.prepareStatement(sql);

                    stm.setString(1, maSV);
                    stm.setFloat(2, Float.parseFloat(diemTA));
                    stm.setFloat(3, Float.parseFloat(diemTin));
                    stm.setFloat(4, Float.parseFloat(diemGDTC));

                    stm.execute();
                    JOptionPane.showMessageDialog(this, "Đã thêm điểm cho sinh viên");
                    clearForm();

                    stm.close();
                    con.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Mã sinh viên này đã có điểm");
                }
            }
        }
    }

    public void searchStudent() {
        try {
            Connection con = connectionDB.getConnection();
            String sql = "select s.masv, g.tienganh, g.tinhoc, g.gdtc, hoten, (tienganh + tinhoc + gdtc) / 3 as dtb from grade g right join students s on g.masv = s.masv where s.masv = ?";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, txtMaSVSearch.getText());

            ResultSet rs = stm.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên không tồn tại");
                txtMaSVSearch.requestFocus();
                clearForm();
                return;
            } else {
                lblhoTen.setText(rs.getString(5));
                txtMaSV.setText(rs.getString(1));

                if (rs.getString(2) == null) {
                    txtTA.setText(null);
                    txtTinHoc.setText(null);
                    txtGDTC.setText(null);
                    lblDTB.setText(null);
                } else {
                    txtTA.setText(String.valueOf(rs.getInt(2)));
                    txtTinHoc.setText(String.valueOf(rs.getInt(3)));
                    txtGDTC.setText(String.valueOf(rs.getInt(4)));
                    lblDTB.setText(new DecimalFormat("#.##").format(rs.getFloat(6)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteMarkStudent() {
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng search mã sinh viên cần xoá");
            txtMaSVSearch.requestFocus();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá không?");

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = connectionDB.getConnection();
                String sql = "delete from grade where masv = ?";
                PreparedStatement stm = con.prepareStatement(sql);

                stm.setString(1, txtMaSV.getText());

                stm.execute();

                JOptionPane.showMessageDialog(this, "Đã xoá điểm của sinh viên này");
                clearForm();

                stm.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateMarkStudent() {
        if (checkNull()) {
            if (checkValidate()) {
                try {
                    Connection con = connectionDB.getConnection();
                    String sqlSearch = "select s.masv, g.tienganh, g.tinhoc, g.gdtc, hoten from grade g right join students s on g.masv = s.masv where s.masv = ?";
                    PreparedStatement stmSearch = con.prepareStatement(sqlSearch);

                    stmSearch.setString(1, txtMaSVSearch.getText());

                    ResultSet rs = stmSearch.executeQuery();

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "Mã sinh viên không tồn tại");
                        txtMaSVSearch.requestFocus();
                        return;
                    } else {
                        lblhoTen.setText(rs.getString(5));
                        txtMaSV.setText(rs.getString(1));

                        if (rs.getString(2) == null) {
                            JOptionPane.showMessageDialog(this, "Sinh viên này chưa có điểm, vui lòng thêm trước khi cập nhật");
                            clearForm();
                            rs.close();
                            stmSearch.close();
                            con.close();
                        } else {

                            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật không?");

                            if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                    String sql = "update grade set tienganh = ?, tinhoc = ?, gdtc = ? where masv = ?";
                                    PreparedStatement stm = con.prepareStatement(sql);

                                    stm.setInt(1, Integer.parseInt(txtTA.getText()));
                                    stm.setInt(2, Integer.parseInt(txtTinHoc.getText()));
                                    stm.setInt(3, Integer.parseInt(txtGDTC.getText()));
                                    stm.setString(4, txtMaSV.getText());

                                    stm.execute();

                                    JOptionPane.showMessageDialog(this, "Đã cập nhật điểm sinh viên");
                                    clearForm();

                                    stm.close();
                                    con.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void fillTable() {
        tblModel = (DefaultTableModel) tblMarkStudent.getModel();
        tblModel.setRowCount(0);

        try {
            Connection con = connectionDB.getConnection();
            String sql = "select top 3 g.*, (tienganh + tinhoc + gdtc)/3 as dtb, hoten from grade g join students s on g.masv = s.masv order by dtb desc";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                tblModel.addRow(new Object[]{rs.getString(1), rs.getString(6), rs.getInt(2), rs.getInt(3), rs.getInt(4), new DecimalFormat("#.##").format(rs.getFloat(5))});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showStudent() {
        String selectedMaSV = (String) tblModel.getValueAt(index, 0);
//        String selectedHoTen = (String) tblModel.getValueAt(index, 1);
//        String selectedTA = (String) tblModel.getValueAt(index, 2);
//        String selectedTin = (String) tblModel.getValueAt(index, 3);
//        String selectedGDTC = (String) tblModel.getValueAt(index, 4);
//        String selectedDTB = (String) tblModel.getValueAt(index, 5);

        System.out.println(selectedMaSV);
//        System.out.println(tblModel.getValueAt(index, 2));

//        txtMaSV.setText(selectedMaSV);
//        lblhoTen.setText(selectedHoTen);
//        txtTA.setText(selectedTA);
//        txtTinHoc.setText(selectedTin);
//        txtGDTC.setText(selectedGDTC);
//        lblDTB.setText(selectedDTB);
        try {
            Connection con = connectionDB.getConnection();
            String sql = "select g.masv, hoten, tienganh, tinhoc, gdtc, (tienganh + tinhoc + gdtc) / 3 as dtb from grade g join students s on g.masv = s.masv where g.masv = ?";
            PreparedStatement stm = con.prepareStatement(sql);

            stm.setString(1, selectedMaSV);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                txtMaSV.setText(rs.getString(1));
                lblhoTen.setText(rs.getString(2));
                txtTA.setText(rs.getString(3));
                txtTinHoc.setText(rs.getString(4));
                txtGDTC.setText(rs.getString(5));
                lblDTB.setText(new DecimalFormat("#.##").format(rs.getFloat(6)));
            }

            rs.close();
            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void selectStudent() {
        index = tblMarkStudent.getSelectedRow();

        tblMarkStudent.setRowSelectionInterval(index, index);

        showStudent();
    }

    public void firstStudent() {
        index = 0;
        tblMarkStudent.setRowSelectionInterval(index, index);
        showStudent();
    }

    public void lastStudent() {
        index = tblModel.getRowCount() - 1;
        tblMarkStudent.setRowSelectionInterval(index, index);
        showStudent();
    }

    public void previousStudent() {
        if (index <= 0) {
            index = tblModel.getRowCount();
        }

        index -= 1;
        tblMarkStudent.setRowSelectionInterval(index, index);
        showStudent();
    }
    
    public void nextStudent() {
        if (index == tblModel.getRowCount() - 1) {
            index = -1;
        }
        
        index += 1;
        tblMarkStudent.setRowSelectionInterval(index, index);
        showStudent();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSVSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblhoTen = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaSV = new javax.swing.JTextField();
        txtTA = new javax.swing.JTextField();
        txtTinHoc = new javax.swing.JTextField();
        txtGDTC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblDTB = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMarkStudent = new javax.swing.JTable();
        lblTop3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Quản Lý Điểm Sinh Viên");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel2.setText("Mã SV:");

        txtMaSVSearch.setBackground(new java.awt.Color(255, 255, 255));

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\search.png")); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtMaSVSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaSVSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setText("Họ tên SV:");

        jLabel4.setText("Mã SV:");

        lblhoTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblhoTen.setForeground(new java.awt.Color(0, 51, 255));
        lblhoTen.setEnabled(false);

        jLabel6.setText("Tiếng anh:");

        jLabel7.setText("Tin học:");

        jLabel8.setText("Giáo dục TC:");

        txtMaSV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSV.setForeground(new java.awt.Color(0, 51, 255));

        txtTA.setBackground(new java.awt.Color(255, 255, 255));

        txtTinHoc.setBackground(new java.awt.Color(255, 255, 255));

        txtGDTC.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Điểm TB:");

        lblDTB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDTB.setForeground(new java.awt.Color(0, 51, 255));
        lblDTB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblhoTen)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTinHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTA, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(45, 45, 45))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblDTB, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblhoTen))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTinHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblDTB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        btnNew.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\add.png")); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\save.png")); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
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

        tblMarkStudent.setBackground(new java.awt.Color(255, 255, 255));
        tblMarkStudent.setForeground(new java.awt.Color(0, 0, 0));
        tblMarkStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Tiếng Anh", "Tin học", "GDTC", "Điểm TB"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMarkStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMarkStudentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMarkStudent);

        lblTop3.setForeground(new java.awt.Color(0, 102, 255));
        lblTop3.setText("3 sinh viên có điểm cao nhất");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(btnFirst)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrevious)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTop3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFirst)
                    .addComponent(btnPrevious)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(18, 18, 18)
                .addComponent(lblTop3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        addMarkStudent();
        fillTable();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchStudent();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteMarkStudent();
        fillTable();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateMarkStudent();
        fillTable();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        fillTable();
    }//GEN-LAST:event_formWindowActivated

    private void tblMarkStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMarkStudentMouseClicked
        selectStudent();
    }//GEN-LAST:event_tblMarkStudentMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstStudent();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastStudent();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        previousStudent();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextStudent();
    }//GEN-LAST:event_btnNextActionPerformed

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
            java.util.logging.Logger.getLogger(StudentMarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentMarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentMarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentMarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentMarkManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDTB;
    private javax.swing.JLabel lblTop3;
    private javax.swing.JLabel lblhoTen;
    private javax.swing.JTable tblMarkStudent;
    private javax.swing.JTextField txtGDTC;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtMaSVSearch;
    private javax.swing.JTextField txtTA;
    private javax.swing.JTextField txtTinHoc;
    // End of variables declaration//GEN-END:variables
}
