/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hw4;


/**
 *
 * @author harunyavas
 */
public class HW4 {

    /**
     * @param args the command line arguments
     */
    private static final String INPUT_FILENAME = "input.txt";

    public static void main(String[] args) {
        
        int[] testSizes = {100, 1000, 10000};

        System.out.println("==========================================");
        System.out.println("    AUTOMATED HASH COLLISION TESTER");
        System.out.println("==========================================");

        for (int size : testSizes) {
            runTest(size);
        }
    }

    private static void runTest(int size) {
        HW4_Hash hashTable = new HW4_Hash();
        String outputFilename = "output_" + size + ".csv"; 

        System.out.println("\n------------------------------------------");
        System.out.println("Running Simulation for Size: " + size);
        
        hashTable.ReadFileandGenerateHash(INPUT_FILENAME, size);
        
        int collisions = hashTable.NumberOfCollusion();
        
        System.out.printf(" > Input: %s | Size: %-5d | Collisions: %d%n", 
                          INPUT_FILENAME, size, collisions);
        
        hashTable.DisplayResultOrdered(outputFilename);
        System.out.println(" > Result saved to: " + outputFilename);
    }
}
    

