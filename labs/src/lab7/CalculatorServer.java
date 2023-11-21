/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quang
 */
public class CalculatorServer {

    public static void main(String[] args) {
        try {
            System.out.println("May chu dang mo va cho ket noi.");
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket socket = serverSocket.accept();
            System.out.println("May chu da ket noi");

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            while (true) {
                double so1 = inputStream.readDouble();
                double so2 = inputStream.readDouble();

                System.out.println("2 so nhan duoc tu client la: " + so1 + " va " + so2);

                double tong = so1 + so2;

                outputStream.writeDouble(tong);
                outputStream.flush();

                System.out.println("Tong 2 so la: " + tong);
                
                String ketnoi = inputStream.readUTF();
                
                if (ketnoi.equals("khong") || ketnoi.equals("k")) {
                    socket.close();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
