/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hw2;

/**
 *
 * @author harunyavas
 */
public class HW2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        LinkedList myList = new LinkedList();
        try {
            myList.Insert(1, 0);
            myList.Insert(2, 0);
            myList.Insert(3, 0);
            myList.Insert(3, 0);
            myList.Insert(3, 0);
            myList.Insert(3, 0);
            myList.Insert(2, 0);
            myList.Insert(2, 0);
            myList.Insert(2, 0);
            myList.Insert(2, 0);
            myList.Insert(9, 5);
            myList.Insert(9, 6);
            myList.Insert(1, 0);
            myList.Insert(2, 0);
            myList.Insert(2, 0);
            myList.Insert(3, 0);
            myList.Insert(2, 0);
            myList.Insert(5, 54);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        myList.Output();
        myList.ReverseLink();
        myList.Output();
        myList.SquashL();
        myList.Output();
        myList.OplashL();
        myList.Output();
        myList.ROutput();
        System.out.println(myList);
    }
}