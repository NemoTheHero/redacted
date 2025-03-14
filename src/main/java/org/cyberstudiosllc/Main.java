package org.cyberstudiosllc;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        // PrettyPrintable is a non-public API for testing, prints semi-graphical representations of trees...
        File file = new File("src/main/java/org/cyberstudiosllc/normalized_harry_potter.txt");
        if (!file.exists()) {
            fileToText();
        }


        try {
            String normalizedContent = readFileToString("src/main/java/org/cyberstudiosllc/normalized_harry_potter.txt");

            long startNanoTime = System.nanoTime();
            long startMilliTime = System.currentTimeMillis();
            Trie.TrieBuilder trieBuilder = Trie.builder().ignoreOverlaps().onlyWholeWords().ignoreCase();
            for (String e : redactedPhrases()) {
                trieBuilder.addKeyword(e.replaceAll("[^a-zA-Z0-9.\\s]", "‡"));
            }
            Collection<Emit> emits = trieBuilder.build().parseText(normalizedContent);
            emits.forEach(System.out::println);
            long endTime = System.nanoTime() - startNanoTime;
            long endMilliTime = System.currentTimeMillis() - startMilliTime;
            System.out.println("Trie Search - Elapsed time in nano seconds: " + endTime);
            System.out.println("Trie Search -Elapsed time in milli seconds: " + endMilliTime);


            long loopNanoStartTime = System.nanoTime();
            long loopMilliStartTime = System.currentTimeMillis();
            for (String redactedPhrase : redactedPhrases()) {
                StringUtils.countMatches(normalizedContent, redactedPhrase);
            }
            long loopNanoEndTime = System.nanoTime() - loopNanoStartTime;
            long loopNanoMilliTime = System.currentTimeMillis() - loopMilliStartTime;
            System.out.println("Loop Search - Elapsed time in nano seconds: " + loopNanoEndTime);
            System.out.println("Loop Search =Elapsed time in milli seconds: " + loopNanoMilliTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void fileToText() throws IOException {
        {

            String inputFile = "src/main/java/org/cyberstudiosllc/harry_potter_3.txt"; // Replace with your input file path
            String outputFile = "src/main/java/org/cyberstudiosllc/normalized_harry_potter.txt"; // Replace with your desired output file path

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String cleanedLine = line.replaceAll("[^a-zA-Z0-9.\\s]", "‡"); //Removes all special characters except alphanumeric and spaces
                    writer.write(cleanedLine);
                    writer.newLine();
                }

                System.out.println("Special characters removed and written to " + outputFile);

            } catch (IOException e) {
                System.err.println("Error processing file: " + e.getMessage());
            }
        }
    }

    public static String readFileToString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private static List<String> redactedPhrases() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("expecto patronum");
        arrayList.add("harry potter");
        arrayList.add("professor snape");
        arrayList.add("voldemort had");
        arrayList.add("james potter");
        arrayList.add("opened their books");
        arrayList.add("inner eye");
        arrayList.add("professor trelawney had");
        arrayList.add("professor trelawney");
        arrayList.add("professor mcgonagall");
        arrayList.add("the gryffindor");
        arrayList.add("people like");
        arrayList.add("muggle studies");
        arrayList.add("quidditch cup");
        arrayList.add("ron and");
        arrayList.add("harry and");
        arrayList.add("harry sat down");
        arrayList.add("mouth was slightly open");
        arrayList.add("he said");
        arrayList.add("she said");
        arrayList.add("said suddenly");
        return arrayList;
    }
}
