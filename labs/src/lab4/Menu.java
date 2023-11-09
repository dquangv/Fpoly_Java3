/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab4;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Quang
 */
public class Menu extends javax.swing.JFrame {

    File defaultDirectory = new File("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\labs\\src\\lab4");

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void clearText() {
        txtText.setText(null);
    }

    public void saveFile() {
        JFileChooser saveDialog = new JFileChooser();
        saveDialog.setCurrentDirectory(defaultDirectory);

        FileNameExtensionFilter fileSaveExt = new FileNameExtensionFilter("DAT File", "dat");
        saveDialog.setFileFilter(fileSaveExt);

        try {
            if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = saveDialog.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                if (selectedFile.exists()) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to overwrite this file?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.NO_OPTION) {
                        selectedFile = saveToNewFile(saveDialog.getCurrentDirectory());
                        filePath = selectedFile.getAbsolutePath();
                    } else {
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

                            bw.write(txtText.getText());
                            bw.close();

                            JOptionPane.showMessageDialog(this, "Saved");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex);
                        }
                    }
                } else {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + ".dat"));

                        bw.write(txtText.getText());
                        bw.close();

                        JOptionPane.showMessageDialog(this, "Saved");
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public File saveToNewFile(File currentDirectory) {
        JFileChooser saveDialog = new JFileChooser();
        saveDialog.setCurrentDirectory(defaultDirectory);

        FileNameExtensionFilter fileSaveExt = new FileNameExtensionFilter("DAT file", "dat");
        saveDialog.setFileFilter(fileSaveExt);

        try {
            if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = saveDialog.getSelectedFile();

                if (selectedFile.exists()) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to overwrite this file?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.NO_OPTION) {
                        return saveToNewFile(currentDirectory);
                    }
                }

                return selectedFile;
            }
        } catch (Exception ex) {
        }

        return null;
    }

    public void openFile() {
        JFileChooser openDialog = new JFileChooser();

        FileNameExtensionFilter openFileExt = new FileNameExtensionFilter("Database", "dat");
        openDialog.setFileFilter(openFileExt);

        openDialog.setCurrentDirectory(defaultDirectory);

        if (openDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = openDialog.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            txtText.setText(null);

            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));

                while (true) {
                    String line = br.readLine();

                    if (line == null) {
                        break;
                    } else {
                        txtText.append(line);
                        txtText.append("\n");
                    }
                }

                br.close();
            } catch (Exception ex) {
            }
        }
    }

    public void setRedText() {
        txtColorText.setBackground(Color.red);
    }

    public void setGreenText() {
        txtColorText.setBackground(Color.GREEN);
    }

    public void setBlueText() {
        txtColorText.setBackground(Color.blue);
    }

    public void setColorText() {
        Color color = JColorChooser.showDialog(this, "Chon mau chu", Color.white);

        txtColorText.setForeground(color);
    }

    public void informAbout() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String msg = new String();
        msg += "Program Demo Menu";
        msg += "\nAuthor: Dang Quang";
        msg += "\nLast Update: " + currentTime.format(formatter);
        msg += "\nEducation: Fpoly";

        JOptionPane.showMessageDialog(this, msg);
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
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtText = new javax.swing.JTextArea();
        txtColorText = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itmNew = new javax.swing.JMenuItem();
        itmOpen = new javax.swing.JMenuItem();
        itemSave = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        rdoRed = new javax.swing.JRadioButtonMenuItem();
        rdoGreen = new javax.swing.JRadioButtonMenuItem();
        rdoBlue = new javax.swing.JRadioButtonMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itmColor = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        itmAbout = new javax.swing.JMenuItem();
        itmExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setRollover(true);

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\folder.png")); // NOI18N
        jLabel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jToolBar1.add(jLabel1);

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\user.png")); // NOI18N
        jLabel2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel2AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jToolBar1.add(jLabel2);

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java3\\icons\\shut_down.png")); // NOI18N
        jLabel3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel3AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jToolBar1.add(jLabel3);

        txtText.setBackground(new java.awt.Color(255, 255, 255));
        txtText.setColumns(20);
        txtText.setRows(5);
        jScrollPane1.setViewportView(txtText);

        txtColorText.setBackground(new java.awt.Color(255, 255, 255));
        txtColorText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtColorText.setForeground(new java.awt.Color(0, 0, 0));
        txtColorText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtColorText.setText("Fpoly - Java 3");

        jMenu1.setText("File");

        itmNew.setMnemonic('N');
        itmNew.setText("<html><u>N</u>ew");
        itmNew.setActionCommand("<html><u>N</u>ew</html>");
        itmNew.setName(""); // NOI18N
        itmNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmNewActionPerformed(evt);
            }
        });
        jMenu1.add(itmNew);

        itmOpen.setText("<html><u>O</u>pen");
        itmOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmOpenActionPerformed(evt);
            }
        });
        jMenu1.add(itmOpen);

        itemSave.setText("<html><u>S</u>ave");
        itemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(itemSave);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Color");

        buttonGroup1.add(rdoRed);
        rdoRed.setSelected(true);
        rdoRed.setText("Red");
        rdoRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRedActionPerformed(evt);
            }
        });
        jMenu2.add(rdoRed);

        buttonGroup1.add(rdoGreen);
        rdoGreen.setText("Green");
        rdoGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGreenActionPerformed(evt);
            }
        });
        jMenu2.add(rdoGreen);

        buttonGroup1.add(rdoBlue);
        rdoBlue.setText("Blue");
        rdoBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBlueActionPerformed(evt);
            }
        });
        jMenu2.add(rdoBlue);
        jMenu2.add(jSeparator1);

        itmColor.setText("Text Color");
        itmColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmColorActionPerformed(evt);
            }
        });
        jMenu2.add(itmColor);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("System");

        itmAbout.setText("About Us");
        itmAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmAboutActionPerformed(evt);
            }
        });
        jMenu3.add(itmAbout);

        itmExit.setText("Exit");
        itmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmExitActionPerformed(evt);
            }
        });
        jMenu3.add(itmExit);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addComponent(txtColorText))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtColorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmNewActionPerformed
        clearText();
    }//GEN-LAST:event_itmNewActionPerformed

    private void itemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSaveActionPerformed
        saveFile();
    }//GEN-LAST:event_itemSaveActionPerformed

    private void itmOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmOpenActionPerformed
        openFile();
    }//GEN-LAST:event_itmOpenActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_formWindowOpened

    private void rdoBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBlueActionPerformed
        setBlueText();
    }//GEN-LAST:event_rdoBlueActionPerformed

    private void rdoGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGreenActionPerformed
        setGreenText();
    }//GEN-LAST:event_rdoGreenActionPerformed

    private void rdoRedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRedActionPerformed
        setRedText();
    }//GEN-LAST:event_rdoRedActionPerformed

    private void itmColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmColorActionPerformed
        setColorText();
    }//GEN-LAST:event_itmColorActionPerformed

    private void itmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_itmExitActionPerformed

    private void itmAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmAboutActionPerformed
        informAbout();
    }//GEN-LAST:event_itmAboutActionPerformed

    private void jLabel1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel1AncestorAdded
    }//GEN-LAST:event_jLabel1AncestorAdded

    private void jLabel2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel2AncestorAdded
    }//GEN-LAST:event_jLabel2AncestorAdded

    private void jLabel3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel3AncestorAdded
    }//GEN-LAST:event_jLabel3AncestorAdded

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        clearText();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        informAbout();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem itemSave;
    private javax.swing.JMenuItem itmAbout;
    private javax.swing.JMenuItem itmColor;
    private javax.swing.JMenuItem itmExit;
    private javax.swing.JMenuItem itmNew;
    private javax.swing.JMenuItem itmOpen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JRadioButtonMenuItem rdoBlue;
    private javax.swing.JRadioButtonMenuItem rdoGreen;
    private javax.swing.JRadioButtonMenuItem rdoRed;
    private javax.swing.JTextField txtColorText;
    private javax.swing.JTextArea txtText;
    // End of variables declaration//GEN-END:variables
}
