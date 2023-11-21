/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Quang
 */
public class CalculatorClient {
    public static void main(String[] args) {
        try {
            System.out.println("Dang cho ket noi voi may chu");
            Socket socket = new Socket("localhost", 9999);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Da ket noi voi may chu");
            
            while (true) {
                System.out.println("So thu 1: ");
                outputStream.writeDouble(new Scanner(System.in).nextDouble());
                outputStream.flush();
                System.out.println("So thu 2: ");
                outputStream.writeDouble(new Scanner(System.in).nextDouble());
                outputStream.flush();
                System.out.println("Tong 2 so: " + inputStream.readDouble());
                System.out.println("Tiep tuc? (co/khong): ");
                String traloi = new Scanner(System.in).nextLine();
                outputStream.writeUTF(traloi);
                if (traloi.equals("khong") || traloi.equals("k")) {
                    socket.close();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
