/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hw3;

/**
 *
 * @author harunyavas
 */
import java.io.IOException;

public interface MatrixLoader {
    char[][] loadMatrix(String filePath) throws IOException;
}