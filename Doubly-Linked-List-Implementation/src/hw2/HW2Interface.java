/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hw2;

/**
 *
 * @author harunyavas
 */
public interface HW2Interface {
    public void Insert(int newElement, int pos) throws Exception;
    public int Delete(int pos) throws Exception;
    public void ReverseLink();
    public void SquashL();
    public void OplashL();
    public void Output();
    public void ROutput();
    @Override
    public String toString();
    public Exception LinkedListException();
}
