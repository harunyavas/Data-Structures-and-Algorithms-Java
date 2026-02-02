/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw2;

public class HW2Exception extends Exception {
    
    public HW2Exception(String message) {
        super(message);
    }
    
    @Override
    public String toString() {
        return "hw2.HW2Exception: " + getMessage();
    }
}
