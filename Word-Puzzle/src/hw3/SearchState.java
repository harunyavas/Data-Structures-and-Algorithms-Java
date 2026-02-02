/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw3;

/**
 *
 * @author harunyavas
 */
import java.util.ArrayList;
import java.util.List;

public class SearchState {
    public int row;
    public int col;
    public int charIndex;      
    public List<Point> path;   

    public SearchState(int r, int c, int index, List<Point> currentPath) {
        this.row = r;
        this.col = c;
        this.charIndex = index;

        this.path = new ArrayList<>(currentPath);
        this.path.add(new Point(r, c));
    }
}