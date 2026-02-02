/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw3;
/**
 *
 * @author harunyavas
 */
import java.io.IOException;
import java.io.*;
import java.util.*;

public class StudentWordPuzzleSolver implements WordPuzzleSolver, MatrixLoader {
    
    private char[][] board;  
    private int rows;
    private int cols;
 
    public StudentWordPuzzleSolver(String filename) {
        try {
             
            this.board = loadMatrix(filename);
            this.rows = board.length;
            this.cols = board[0].length;
        } catch (IOException e) {
            System.err.println("File could not be read! Default board is loading for testing.");
            
            this.rows = 4;
            this.cols = 4;
            this.board = new char[][] {
                {'C', 'A', 'T', 'X'},
                {'H', 'X', 'A', 'I'},
                {'A', 'B', 'C', 'D'},
                {'I', 'F', 'G', 'H'}
            };
        }
    }

    
    @Override
    public Point[] findTheWord(String word) {
        if (word == null || word.length() == 0) return null;

        Stack<SearchState> stack = new Stack<>();
        char firstChar = word.charAt(0);
 
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == firstChar) {
                    
                    stack.push(new SearchState(r, c, 0, new ArrayList<>()));
                }
            }
        }

        int[] rowOffsets = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] colOffsets = {-1,  0,  1, -1, 1, -1, 0, 1};

        while (!stack.isEmpty()) {
            SearchState current = stack.pop();

            if (current.charIndex == word.length() - 1) {
                
                Point[] result = new Point[current.path.size()];
                return current.path.toArray(result);
            }

            
            int nextIndex = current.charIndex + 1;
            char nextCharToFind = word.charAt(nextIndex);

            for (int i = 0; i < 8; i++) {
                int nextR = current.row + rowOffsets[i];
                int nextC = current.col + colOffsets[i];

                // Sınır Kontrolü
                if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols) {

                    if (board[nextR][nextC] == nextCharToFind) {

                        boolean visitedBefore = false;
                        for (Point p : current.path) {
                            if (p.row == nextR && p.col == nextC) {
                                visitedBefore = true;
                                break;
                            }
                        }

                        if (!visitedBefore) {
                            stack.push(new SearchState(nextR, nextC, nextIndex, current.path));
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public char[][] loadMatrix(String filePath) throws IOException {
        
        File file = new File(filePath);
        if (!file.exists()) throw new IOException("Dosya bulunamadi: " + filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));
        
        
        String line = br.readLine(); 
        if (line == null) throw new IOException("Dosya bos!");
        int numRows = Integer.parseInt(line.trim());

        line = br.readLine();
        int numCols = Integer.parseInt(line.trim());

        char[][] matrix = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            line = br.readLine();
           
            if (line != null) {
                line = line.replaceAll("\\s+", ""); 
                
                for (int j = 0; j < numCols && j < line.length(); j++) {
                    matrix[i][j] = line.charAt(j);
                }
            }
        }
        br.close();
        return matrix;
    }
}