/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quang
 */
public class UserDAO {

    List<User> list = new ArrayList<>();

    public UserDAO() {
        list.add(new User("vudquang", "123", true));
        list.add(new User("nguyentgkhanh", "123", false));
        list.add(new User("tranmduc", "123", false));
    }

    public String checkLogin(String username, String password) {
        for (User user : list) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.isRole()) {
                return "CanBo";
            }

            if (user.getUsername().equals(username) && user.getPassword().equals(password) && !user.isRole()) {
                return "GiangVien";
            }

        }
        return null;
    }
}
