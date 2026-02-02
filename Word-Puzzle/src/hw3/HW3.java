/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hw3;

/**
 *
 * @author harunyavas
 */
import java.io.*;
import java.util.*;

public class HW3 {
    
    
    
    public static void main(String[] args) {
        String fileName = "random_puzzle.txt";
        
        String[] hiddenWords = {"JAVA", "STACK", "ALGORITHM", "COMPLEXITY", "ELECTRIC", "ENGINEER"};
        
        generateRandomPuzzle(fileName, 10, 10, hiddenWords);

        System.out.println("--- NEW RANDOM PUZZLE CREATED ---");
        System.out.println("File: " + fileName + "\n");

        StudentWordPuzzleSolver solver = new StudentWordPuzzleSolver(fileName);

        for (String word : hiddenWords) {
            System.out.printf("Searching: %-10s -> ", word); 
            
            Point[] yol = solver.findTheWord(word);

            if (yol == null) {
                System.out.println("NOT FOUND");
            } else {
                
                for (Point p : yol) {
                    System.out.print("(" + p.row + "," + p.col + ") ");
                }
                System.out.println();
            }
        }
    }

    public static void generateRandomPuzzle(String filename, int rows, int cols, String[] words) {
        char[][] board = new char[rows][cols];
        Random rand = new Random();

        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], ' '); 
        }

        for (String word : words) {
            placeWordRandomly(board, word, rand);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = (char) ('A' + rand.nextInt(26));
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(rows + "\n");
            writer.write(cols + "\n");
            for (int i = 0; i < rows; i++) {
                writer.write(new String(board[i]) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    private static void placeWordRandomly(char[][] board, String word, Random rand) {
        int rows = board.length;
        int cols = board[0].length;
        
        
        int[][] directions = { {0, 1}, {1, 0}, {1, 1}, {-1, 1}, {0, -1}, {-1, 0}, {-1, -1}, {1, -1} };

        boolean placed = false;
        int attempts = 0;

        while (!placed && attempts < 100) {
            attempts++;
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            int[] dir = directions[rand.nextInt(directions.length)]; 

            if (canPlace(board, word, r, c, dir[0], dir[1])) {
                
                for (int i = 0; i < word.length(); i++) {
                    board[r + i * dir[0]][c + i * dir[1]] = word.charAt(i);
                }
                placed = true;
            }
        }
    }

    private static boolean canPlace(char[][] board, String word, int r, int c, int dr, int dc) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < word.length(); i++) {
            int nr = r + i * dr;
            int nc = c + i * dc;

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) return false;

            if (board[nr][nc] != ' ' && board[nr][nc] != word.charAt(i)) return false;
        }
        return true;
    }
    
}
