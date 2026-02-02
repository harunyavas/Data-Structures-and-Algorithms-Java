/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw2;

/**
 *
 * @author harunyavas
 */
public class DLNode {
    public int Element;
    public DLNode left;
    public DLNode right;
    
    public DLNode(int element) {
        this.Element = element;
        this.left = null;
        this.right = null;
    }
}
