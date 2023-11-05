/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Quang
 */
public class Layout extends javax.swing.JFrame {

    private JFrame mainFrame;
    private JPanel pn1 = new JPanel();
    private JPanel pn2 = new JPanel();
    private JPanel pn3 = new JPanel();
    private JPanel pn4 = new JPanel();
    JTextField txtComment = new JTextField();

    public Layout() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Demo Layout");
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        mainFrame.setBounds(50, 50, 800, 500);

        pn1.setLayout(new FlowLayout(FlowLayout.RIGHT));

        pn2.setLayout(new BorderLayout());
        pn2.setBorder(new EmptyBorder(20, 20, 20, 20));

        pn4.setLayout(new GridLayout(2, 5, 5, 5));

        mainFrame.add(Box.createRigidArea(new Dimension(10, 10)));
        mainFrame.add(pn1);

        mainFrame.add(Box.createRigidArea(new Dimension(10, 10)));
        mainFrame.add(pn2);

        mainFrame.add(Box.createRigidArea(new Dimension(10, 10)));
        mainFrame.add(pn3);

        mainFrame.add(Box.createRigidArea(new Dimension(10, 10)));
        mainFrame.add(pn4);

        mainFrame.setVisible(true);
    }

    private void showPanel1() {
        JButton btnRed = new JButton("Red");
        JButton btnGreen = new JButton("Green");
        JButton btnYellow = new JButton("Yellow");

        btnRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn1.setBackground(Color.red);
            }
        });

        btnGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn1.setBackground(Color.green);
            }
        });

        btnYellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pn1.setBackground(Color.yellow);
            }
        });

        pn1.setPreferredSize(new Dimension(10, 50));

        pn1.add(btnRed);
        pn1.add(btnGreen);
        pn1.add(btnYellow);

        pn1.setVisible(true);
    }

    private void showPanel2() {
        JButton btnNorth = new JButton("North");
        JButton btnWest = new JButton("West");
        JButton btnCenter = new JButton("Center");
        JButton btnEast = new JButton("East");
        JButton btnSouth = new JButton("South");

        btnNorth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtComment.setText(btnNorth.getText());
            }
        });

        btnWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtComment.setText(btnWest.getText());
            }
        });

        btnCenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtComment.setText(btnCenter.getText());
            }
        });

        btnEast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtComment.setText(btnEast.getText());
            }
        });

        btnSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtComment.setText(btnSouth.getText());
            }
        });

        pn2.add(btnNorth, BorderLayout.NORTH);
        pn2.add(btnSouth, BorderLayout.SOUTH);
        pn2.add(btnEast, BorderLayout.EAST);
        pn2.add(btnWest, BorderLayout.WEST);
        pn2.add(btnCenter, BorderLayout.CENTER);

        pn2.setVisible(true);
    }

    private void showPanel3() {
        txtComment.setPreferredSize(new Dimension(mainFrame.getWidth() - 100, 50));

        pn3.add(txtComment);
    }

    private void showPanel4() {
        JButton buttons[] = new JButton[10];
        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton();
            buttons[i].setMargin(new Insets(20, 20, 20, 20));

            pn4.add(buttons[i]);
        }

        pn4.setBackground(Color.pink);
    }

    public static void main(String[] args) {
        Layout demoLayout = new Layout();
        demoLayout.showPanel1();
        demoLayout.showPanel2();
        demoLayout.showPanel3();
        demoLayout.showPanel4();
    }
}
