/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw2;

/**
 *
 * @author harunyavas
 */
public class LinkedList implements HW2Interface {
     private DLNode head;
    private DLNode tail;
    private int size;
    
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
     
    @Override
    public void Insert(int newElement, int pos) throws Exception {
         
        if (pos < 0 || pos > size) {
            throw LinkedListException();
        }
        
        DLNode newNode = new DLNode(newElement);
        
        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        if (pos == 0) {
            newNode.right = head;
            head.left = newNode;
            head = newNode;
            size++;
            return;
        }

        if (pos == size) {
            newNode.left = tail;
            tail.right = newNode;
            tail = newNode;
            size++;
            return;
        }

        DLNode current = head;
        for (int i = 0; i < pos; i++) {
            current = current.right;
        }
        
        newNode.left = current.left;
        newNode.right = current;
        current.left.right = newNode;
        current.left = newNode;
        size++;
    }
    
    
    @Override
    public int Delete(int pos) throws Exception {
        
        if (pos < 0 || pos >= size || head == null) {
            throw LinkedListException();
        }
        
        if (size == 1) {
            int value = head.Element;
            head = null;
            tail = null;
            size = 0;
            return value;
        }
        
        if (pos == 0) {
            int value = head.Element;
            head = head.right;
            head.left = null;
            size--;
            return value;
        }
        
         
        if (pos == size - 1) {
            int value = tail.Element;
            tail = tail.left;
            tail.right = null;
            size--;
            return value;
        }
        
        DLNode current = head;
        for (int i = 0; i < pos; i++) {
            current = current.right;
        }
        
        int value = current.Element;
        current.left.right = current.right;
        current.right.left = current.left;
        size--;
        return value;
    }
    
    @Override
    public void ReverseLink() {
        if (head == null || size <= 1) {
            return;
        }
        
        DLNode current = head;
        DLNode temp = null;

        while (current != null) {
            temp = current.left;
            current.left = current.right;
            current.right = temp;
            current = current.left;
        }
        
        temp = head;
        head = tail;
        tail = temp;
    }

    @Override
    public void SquashL() {
        if (head == null) {
            return;
        }
        
        DLNode current = head;
        DLNode newHead = null;
        DLNode newTail = null;
        int newSize = 0;
        
        while (current != null) {
            int element = current.Element;
            int count = 1;
            
             
            while (current.right != null && current.right.Element == element) {
                count++;
                current = current.right;
            }
            
            
            DLNode elementNode = new DLNode(element);
            if (newHead == null) {
                newHead = elementNode;
                newTail = elementNode;
            } else {
                newTail.right = elementNode;
                elementNode.left = newTail;
                newTail = elementNode;
            }
            newSize++;
            
            
            DLNode countNode = new DLNode(count);
            newTail.right = countNode;
            countNode.left = newTail;
            newTail = countNode;
            newSize++;
            
            current = current.right;
        }
        
        head = newHead;
        tail = newTail;
        size = newSize;
    }

    @Override
    public void OplashL() {
        if (head == null) {
            return;
        }
        
        DLNode current = head;
        DLNode newHead = null;
        DLNode newTail = null;
        int newSize = 0;
         
        while (current != null && current.right != null) {
            int element = current.Element;
            int count = current.right.Element;
            
            
            for (int i = 0; i < count; i++) {
                DLNode newNode = new DLNode(element);
                if (newHead == null) {
                    newHead = newNode;
                    newTail = newNode;
                } else {
                    newTail.right = newNode;
                    newNode.left = newTail;
                    newTail = newNode;
                }
                newSize++;
            }
            
            current = current.right.right;  
        }
        
        head = newHead;
        tail = newTail;
        size = newSize;
    }
    
    @Override
    public void Output() {
        System.out.print("The Elements in the list are : ");
        DLNode current = head;
        while (current != null) {
            System.out.print(current.Element + " ");
            current = current.right;
        }
        System.out.println();
    }
    
    @Override
    public void ROutput() {
        System.out.print("The Reverse Elements in the list are : ");
        DLNode current = tail;
        while (current != null) {
            System.out.print(current.Element + " ");
            current = current.left;
        }
        System.out.println();
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DLNode current = head;
        while (current != null) {
            sb.append(current.Element);
            if (current.right != null) {
                sb.append(" ");
            }
            current = current.right;
        }
        return sb.toString();
    }
    
    
    @Override
    public Exception LinkedListException() {
        return new HW2Exception("Not supported yet.");
    }

}
