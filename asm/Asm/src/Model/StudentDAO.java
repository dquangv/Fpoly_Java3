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
public class StudentDAO {
    List<Student> list = new ArrayList<>();
    
    public int themSV(Student std) {
        list.add(std);
        return list.size();
    }
    
    public List<Student> dsSV() {
        return list;
    }
}
