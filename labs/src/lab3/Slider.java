/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab3;

import java.awt.Font;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Quang
 */
public class Slider extends javax.swing.JFrame {

    /**
     * Creates new form Slider
     */
    public Slider() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void setLabelValue() {
        txtValue.setText(String.valueOf(sldValue.getValue()));
    }

    public void setValueSlider() {
        try {
            sldValue.setValue(Integer.parseInt(txtValue.getText()));
        } catch (Exception ex) {

        }
    }

    public void setFont() {
        if (!txtValue.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so!");
        } else {
            if (Integer.parseInt(txtValue.getText()) < 0 || Integer.parseInt(txtValue.getText()) > 50) {
                JOptionPane.showMessageDialog(this, "Vui long nhap tu 0 den 50");
            } else {
                lblFont.setFont(new java.awt.Font("Tahoma", Font.PLAIN, sldValue.getValue()));
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

        sldValue = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        txtValue = new javax.swing.JTextField();
        btnSetValue = new javax.swing.JButton();
        lblFont = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        sldValue.setMajorTickSpacing(5);
        sldValue.setMaximum(50);
        sldValue.setMinorTickSpacing(1);
        sldValue.setPaintLabels(true);
        sldValue.setPaintTicks(true);
        sldValue.setValue(10);
        sldValue.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sldValue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldValueStateChanged(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Gia tri hien tai:");

        txtValue.setForeground(new java.awt.Color(0, 51, 255));
        txtValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValueActionPerformed(evt);
            }
        });
        txtValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValueKeyReleased(evt);
            }
        });

        btnSetValue.setText("Set value");
        btnSetValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetValueActionPerformed(evt);
            }
        });

        lblFont.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblFont.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFont.setText("Font Size");
        lblFont.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sldValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(btnSetValue))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFont, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(sldValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(btnSetValue)
                        .addContainerGap(65, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSetValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetValueActionPerformed
        setFont();
    }//GEN-LAST:event_btnSetValueActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    }//GEN-LAST:event_formWindowActivated

    private void txtValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValueActionPerformed
    }//GEN-LAST:event_txtValueActionPerformed

    private void sldValueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldValueStateChanged
        setLabelValue();
    }//GEN-LAST:event_sldValueStateChanged

    private void txtValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValueKeyReleased
        setValueSlider();
    }//GEN-LAST:event_txtValueKeyReleased

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
            java.util.logging.Logger.getLogger(Slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Slider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Slider().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSetValue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFont;
    private javax.swing.JSlider sldValue;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
