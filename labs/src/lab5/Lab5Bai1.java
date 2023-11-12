/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5;

import java.sql.*;

/**
 *
 * @author Quang
 */
public class Lab5Bai1 {

    public static void main(String[] args) {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Java3_Lab5_PS36680;user=sa;password=123;encrypt=false;";
            Connection con = DriverManager.getConnection(url);
            String sql = "select * from students";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                System.out.print(rs.getString("masv") + ", ");
                System.out.print(rs.getString("hoten") + ", ");
                System.out.print(rs.getString("email") + ", ");
                System.out.print(rs.getString("sodt") + ", ");
                System.out.print(rs.getString("gioitinh") + ", ");
                System.out.println(rs.getString("diachi"));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
