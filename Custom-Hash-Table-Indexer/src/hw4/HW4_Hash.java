/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw4;

/**
 *
 * @author harunyavas
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HW4_Hash implements Hash_Interface{
    private class HashNode {
        String word;
        int frequency;
        ArrayList<Integer> positions;
        HashNode next; 

        public HashNode(String word, int position) {
            this.word = word;
            this.frequency = 1;
            this.positions = new ArrayList<>();
            this.positions.add(position);
            this.next = null;
        }
    }

    private HashNode[] table; 
    private int currentSize; 
    private int numUniqueWords; 
    private int totalCollisions; 

    private int getHash(String key, int tableSize) {
        long hash = 0;
        int p = 31;
        long m = 1000000009; 
        long p_pow = 1;
        
        for (char c : key.toCharArray()) {
            hash = (hash + (c - 'a' + 1) * p_pow) % m;
            p_pow = (p_pow * p) % m;
        }
        
        int index = (int) (hash % tableSize);
        return (index < 0) ? index + tableSize : index;
    }

    @Override
    public void ReadFileandGenerateHash(String filename, int size) {
        this.currentSize = size;
        this.table = new HashNode[currentSize];
        this.numUniqueWords = 0;
        this.totalCollisions = 0;
        
        int globalTokenPosition = 0; 

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                line = line.replace("’", "'").replace("‘", "'"); 
                line = line.replace("–", "-"); 
                
                
                line = line.replace("-", " ");
                
                String[] rawTokens = line.split("\\s+");
                
                for (String token : rawTokens) {
                    
                    String cleanedWord = token.replaceAll("[^a-zA-Z0-9']", "");
                    
                   
                    while (cleanedWord.startsWith("'")) {
                        cleanedWord = cleanedWord.substring(1);
                    }
                    while (cleanedWord.endsWith("'")) {
                        cleanedWord = cleanedWord.substring(0, cleanedWord.length() - 1);
                    }

                    if (!cleanedWord.isEmpty()) {
                        globalTokenPosition++; 
                        
                        
                        
                        insert(cleanedWord.toLowerCase(), globalTokenPosition); 
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okuma hatası! Dosya yolunu kontrol et: " + e.getMessage());
        }
    }

    
    private void insert(String word, int position) {
        
        if ((double) numUniqueWords / currentSize > 0.75) {
            rehash();
        }

        int index = getHash(word, currentSize);
        HashNode head = table[index];

        if (head == null) {
            
            table[index] = new HashNode(word, position);
            numUniqueWords++;
        } else {
            
            HashNode current = head;
            boolean found = false;
            
            while (current != null) {
                if (current.word.equals(word)) {
                    
                    current.frequency++;
                    current.positions.add(position);
                    found = true;
                   
                    break;
                }
                if (current.next == null) break;
                current = current.next;
            }

            if (!found) {
                
                totalCollisions++; 
                
               
                current.next = new HashNode(word, position);
                numUniqueWords++;
            }
        }
    }

    
    private void rehash() {
        System.out.println("Rehashing... Old Size: " + currentSize + " -> New Size: " + (currentSize * 2));
        HashNode[] oldTable = table;
        currentSize *= 2;
        table = new HashNode[currentSize];
        numUniqueWords = 0; 
        

        for (HashNode node : oldTable) {
            while (node != null) {
                
                int newIndex = getHash(node.word, currentSize);
                HashNode newNode = new HashNode(node.word, -1); 
                newNode.frequency = node.frequency;
                newNode.positions = node.positions; 
                newNode.next = null;

                if (table[newIndex] == null) {
                    table[newIndex] = newNode;
                    numUniqueWords++;
                } else {
                    HashNode current = table[newIndex];
                    while (current.next != null) {
                        current = current.next;
                    }
                    current.next = newNode;
                    numUniqueWords++;
                }
                node = node.next;
            }
        }
    }

    @Override
    public int showFrequency(String myword) { 
        myword = myword.toLowerCase();
        int index = getHash(myword, currentSize);
        HashNode current = table[index];
        
        while (current != null) {
            if (current.word.equals(myword)) {
                return current.frequency;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public String showMaxRepeatedWord() {
        String maxWord = "";
        int maxFreq = -1;

        for (int i = 0; i < currentSize; i++) {
            HashNode current = table[i];
            while (current != null) {
                if (current.frequency > maxFreq) {
                    maxFreq = current.frequency;
                    maxWord = current.word;
                } else if (current.frequency == maxFreq) {
                   
                    if (maxWord.equals("") || current.word.compareTo(maxWord) < 0) {
                        maxWord = current.word;
                    }
                }
                current = current.next;
            }
        }
        
        
        if (maxFreq == -1) return null;
        
        return maxWord; 
    }

    @Override
    public int checkWord(String myword) { 
        myword = myword.toLowerCase();
        int index = getHash(myword, currentSize);
        HashNode current = table[index];
        
        while (current != null) {
            if (current.word.equals(myword)) {
                
                for (int pos : current.positions) {
                    System.out.print(pos + " ");
                }
                System.out.println(); 
                return current.frequency;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int NumberOfCollusion() { 
        return totalCollisions;
    }

    @Override
    public void DisplayResultOrdered(String outputFile) {
        ArrayList<HashNode> allNodes = new ArrayList<>();
        
        for (int i = 0; i < currentSize; i++) {
            HashNode current = table[i];
            while (current != null) {
                allNodes.add(current);
                current = current.next;
            }
        }
        
        
        System.out.println("DEBUG: Dosyaya yazılacak kelime sayısı: " + allNodes.size());
        

        Collections.sort(allNodes, new Comparator<HashNode>() {
            
             @Override
            public int compare(HashNode o1, HashNode o2) {
                if (o1.frequency != o2.frequency) {
                    return o2.frequency - o1.frequency; 
                } else {
                    return o1.word.compareTo(o2.word); 
                }
            }
        });

        try (FileWriter writer = new FileWriter(outputFile)) {
            for (HashNode node : allNodes) {
                
                StringBuilder sb = new StringBuilder();
                sb.append(node.word).append(", ").append(node.frequency).append(", ");
                
                for (int i = 0; i < node.positions.size(); i++) {
                    sb.append(node.positions.get(i));
                    if (i < node.positions.size() - 1) {
                        sb.append("; ");
                    }
                }
                sb.append("\n");
                writer.write(sb.toString());
            }
            
            System.out.println("DEBUG: Yazma işlemi tamamlandı. Dosya yolu: " + outputFile);
            
        } catch (IOException e) {
            System.err.println("Dosya yazma hatası: " + e.getMessage());
        }
    }
}
