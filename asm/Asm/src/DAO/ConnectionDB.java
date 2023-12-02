/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;

/**
 *
 * @author Quang
 */
public class ConnectionDB {
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Java3_ASM_PS36680_FPL_DaoTao;user=sa;password=123;encrypt=false;";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
