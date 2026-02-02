/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package harun_yavas_hw1;

/**
 *
 * @author harunyavas
 */
public class Student implements Comparable<Student> {
    int age;
    long ID;
    int atdYear;
    String name;
    String surname;
    
    public Student(int _age, long _ID, int _atdYear, String _name, String _surname) {
        age = _age;
        ID = _ID;
        atdYear = _atdYear;
        name = _name;
        surname = _surname;
    }
    
    @Override
    public int compareTo(Student other) {
        return Long.compare(this.ID, other.ID);
    }
    
    @Override
    public String toString() {
        return ID + " " + name + " " + surname + " " + age + " " + atdYear;
    }
}
