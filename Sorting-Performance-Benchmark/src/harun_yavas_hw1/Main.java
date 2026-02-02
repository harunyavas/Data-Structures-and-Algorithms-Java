/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package harun_yavas_hw1;

/**
 *
 * @author harunyavas
 */
import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            printMenu();
            System.out.print("Your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Boş satırı temizle
                
                switch (choice) {
                    case 1:
                        runPartA(scanner);
                        break;
                    case 2:
                        runFileGenerator();
                        break;
                    case 3:
                        runBatchTester();
                        break;
                    case 4:
                        runQuickTest(scanner);
                        break;
                    case 0:
                        running = false;
                        System.out.println("The program is ending....");
                        break;
                    default:
                        System.out.println("Invalid selection! Please try again.\n");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.\n");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    EEM 480 - HOMEWORK 1 - MAIN MENU      ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ 1. Part A - Sort Sinle File              ║");
        System.out.println("║ 2. Part B - Create Test Files            ║");
        System.out.println("║ 3. Part B - Batch Testing and Analysis   ║");
        System.out.println("║ 4. Quick Test (student_200.txt)          ║");
        System.out.println("║ 0. Exit                                  ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
    
    // PART A - Tek dosya sıralama
    private static void runPartA(Scanner scanner) {
        System.out.println("\n--- PART A: Sorting File ---");
        
        System.out.print("Input file path: ");
        String inputPath = scanner.nextLine().trim();
        
        System.out.print("Output file path: ");
        String outputPath = scanner.nextLine().trim();
        
        try {
            List<Student> students = readStudents(inputPath);
            
            // Sadece sıralama süresini ölç
            long startTime = System.nanoTime();
            Collections.sort(students);
            long endTime = System.nanoTime();
            
            long timeNano = endTime - startTime;
            double timeMs = timeNano / 1_000_000.0;
            double timeMicro = timeNano / 1_000.0;
            
            writeStudents(outputPath, students, timeMs, timeMicro);
            
            System.out.println("\n✓ The operation was successfully completed!");
            System.out.printf("✓ %d students lined up\n", students.size());
            System.out.printf("✓ Sorting time: %.2f ms (%.2f μs)\n", timeMs, timeMicro);
            System.out.println("✓ Result file: " + outputPath);
            
        } catch (Exception e) {
            System.err.println("✗ ERROR: " + e.getMessage());
        }
    }
    
    // PART B - Test dosyaları oluştur
    private static void runFileGenerator() {
        System.out.println("\n--- PART B: Test Files are Being Created ---");
        
        int[] sizes = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 
                       100000, 200000, 500000, 1000000};
        
        Random random = new Random();
        int successCount = 0;
        
        for (int size : sizes) {
            String filename = "students_" + size + ".txt";
            try {
                generateFile(filename, size, random);
                System.out.println("✓ " + filename + " (" + formatNumber(size) + " entries)");
                successCount++;
            } catch (IOException e) {
                System.err.println("✗ " + filename + " - ERROR: " + e.getMessage());
            }
        }
        
        System.out.println("\n" + successCount + "/" + sizes.length + " dosya başarıyla oluşturuldu!");
    }
    
    // PART B - Toplu test ve performans analizi
    private static void runBatchTester() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      PERFORMANS ANALİZİ BAŞLIYOR         ║");
        System.out.println("╚══════════════════════════════════════════╝\n");
        
        int[] sizes = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 
                       100000, 200000, 500000, 1000000};
        
        List<TestResult> results = new ArrayList<>();
        
        for (int size : sizes) {
            String inputFile = "students_" + size + ".txt";
            String outputFile = "output_" + size + ".txt";
            
            System.out.print("Test: " + inputFile + " ... ");
            
            try {
                TestResult result = testFile(inputFile, outputFile, size);
                results.add(result);
                System.out.printf("✓ %.2f ms\n", result.timeMs);
                
            } catch (Exception e) {
                System.out.println("✗ " + e.getMessage());
            }
        }
        
        if (!results.isEmpty()) {
            printResultsTable(results);
            analyzeResults(results);
        } else {
            System.out.println("\n✗ The file to be tested could not be found!");
            System.out.println("Tip: First, create the test files using ‘Option 2’.");
        }
    }
    
    
    private static void runQuickTest(Scanner scanner) {
        System.out.println("\n--- Quick Test: student_200.txt ---");
        
        String inputFile = "student_200.txt";
        String outputFile = "output_200.txt";
        
        File file = new File(inputFile);
        if (!file.exists()) {
            System.out.println("✗ " + inputFile + " could not be found!");
            System.out.print("Would you like to create the file? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            
            if (answer.equals("y") || answer.equals("yes")) {
                try {
                    generateFile(inputFile, 200, new Random());
                    System.out.println("✓ " + inputFile + " oluşturuldu!");
                } catch (IOException e) {
                    System.err.println("The file could not be created.: " + e.getMessage());
                    return;
                }
            } else {
                return;
            }
        }
        
        try {
            List<Student> students = readStudents(inputFile);
            
            System.out.println("\nTop 5 entries before ranking:");
            for (int i = 0; i < Math.min(5, students.size()); i++) {
                System.out.println("  " + students.get(i));
            }
            
            long startTime = System.nanoTime();
            Collections.sort(students);
            long endTime = System.nanoTime();
            
            long timeNano = endTime - startTime;
            double timeMs = timeNano / 1_000_000.0;
            double timeMicro = timeNano / 1_000.0;
            
            System.out.println("\nTop 5 entries after sorting:");
            for (int i = 0; i < Math.min(5, students.size()); i++) {
                System.out.println("  " + students.get(i));
            }
            
            writeStudents(outputFile, students, timeMs, timeMicro);
            
            System.out.println("\n✓ Total: " + students.size() + " students");
            System.out.printf("✓ Sorting time: %.2f ms (%.2f μs)\n", timeMs, timeMicro);
            System.out.println("✓ Output: " + outputFile);
            
        } catch (Exception e) {
            System.err.println("✗ ERROR: " + e.getMessage());
        }
    }
    
   
    
    private static List<Student> readStudents(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String firstLine = br.readLine();
            if (firstLine == null) {
                throw new IOException("File is empty!");
            }
            
            int n = Integer.parseInt(firstLine.trim());
            
            for (int i = 0; i < n; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Expected " + n + " entries, only " + i + " entires found!");
                }
                
                String[] parts = line.trim().split("[,\\s]+");
                
                if (parts.length < 5) {
                    throw new IOException("Line " + (i + 2) + " invalid: " + line);
                }
                
                long id = Long.parseLong(parts[0]);
                String name = parts[1];
                String surname = parts[2];
                int age = Integer.parseInt(parts[3]);
                int atdYear = Integer.parseInt(parts[4]);
                
                students.add(new Student(age, id, atdYear, name, surname));
            }
        }
        
        return students;
    }
    
    private static void writeStudents(String filePath, List<Student> students, 
                                     double timeMs, double timeMicro) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Student student : students) {
                bw.write(student.toString());
                bw.newLine();
            }
            bw.write(String.format("Time: %.2f ms (%.2f μs)", timeMs, timeMicro));
            bw.newLine();
        }
    }
    
    private static void generateFile(String filename, int n, Random random) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(String.valueOf(n));
            bw.newLine();
            
            Set<Long> usedIds = new HashSet<>();
            
            for (int i = 0; i < n; i++) {
                long id;
                do {
                    id = 100_000_000_000_000L + (long)(random.nextDouble() * 900_000_000_000_000L);
                } while (usedIds.contains(id));
                usedIds.add(id);
                
                String name = "Name" + i;
                String surname = "Surname" + i;
                int age = random.nextInt(28) + 18;
                int atdYear = random.nextInt(26) + 2000;
                
                bw.write(String.format("%d %s %s %d %d", id, name, surname, age, atdYear));
                bw.newLine();
            }
        }
    }
    
    private static TestResult testFile(String inputPath, String outputPath, int expectedN) 
            throws IOException {
        List<Student> students = readStudents(inputPath);
        
        if (students.size() != expectedN) {
            throw new IOException("Expected: " + expectedN + ", Read: " + students.size());
        }
        
        long startTime = System.nanoTime();
        Collections.sort(students);
        long endTime = System.nanoTime();
        
        long timeNano = endTime - startTime;
        double timeMs = timeNano / 1_000_000.0;
        double timeMicro = timeNano / 1_000.0;
        
        writeStudents(outputPath, students, timeMs, timeMicro);
        
        return new TestResult(inputPath, expectedN, timeMs);
    }
    
    private static void printResultsTable(List<TestResult> results) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              PERFORMANCE RESULTS                     ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║ %-25s %12s %12s ║\n", "File", "N", "Time (ms)");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        
        for (TestResult result : results) {
            System.out.printf("║ %-25s %,12d %,12.2f ║\n", 
                            result.filename, result.n, result.timeMs);
        }
        System.out.println("╚════════════════════════════════════════════════════╝");
    }
    
    private static void analyzeResults(List<TestResult> results) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              PERFORMANCE ANALYSIS                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");
        
        if (results.size() >= 2) {
            System.out.println("Timing Growth Rates:");
            System.out.println("─────────────────────────────────────────────");
            
            for (int i = 1; i < Math.min(results.size(), 6); i++) {
                TestResult prev = results.get(i - 1);
                TestResult curr = results.get(i);
                
                double nRatio = (double) curr.n / prev.n;
                double timeRatio = curr.timeMs / prev.timeMs;
                
                System.out.printf("%,8d → %,8d: N=%.2fx, Time=%.2fx\n",
                                prev.n, curr.n, nRatio, timeRatio);
            }
            
            TestResult first = results.get(0);
            TestResult last = results.get(results.size() - 1);
            
            double totalNRatio = (double) last.n / first.n;
            double totalTimeRatio = last.timeMs / first.timeMs;
            
            System.out.println("\nOverall Assessment:");
            System.out.println("─────────────────────");
            System.out.printf("• Data increased %,d-fold\n", (int)totalNRatio);
            System.out.printf("• Time increased %.0f-fold\n", totalTimeRatio);
            System.out.println("• Algorithm: Time sort - O(n log n)");
            System.out.println("• Performance: " + evaluatePerformance(totalNRatio, totalTimeRatio));
        }
    }
    
    private static String evaluatePerformance(double nRatio, double timeRatio) {
        double expected = nRatio * (Math.log(nRatio) / Math.log(2));
        if (timeRatio < expected * 1.5) {
            return "Perfect ✓";
        } else if (timeRatio < expected * 2) {
            return "Good ✓";
        } else {
            return "Acceptable";
        }
    }
    
    private static String formatNumber(int num) {
        return String.format("%,d", num);
    }
    
    // İç sınıflar
    
    static class TestResult {
        String filename;
        int n;
        double timeMs;
        
        TestResult(String filename, int n, double timeMs) {
            this.filename = filename;
            this.n = n;
            this.timeMs = timeMs;
        }
    }
}
