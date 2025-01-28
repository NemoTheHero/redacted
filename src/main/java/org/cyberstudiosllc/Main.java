package org.cyberstudiosllc;

import com.googlecode.concurrenttrees.common.Iterables;
import com.googlecode.concurrenttrees.common.PrettyPrinter;

import com.googlecode.concurrenttrees.radix.node.NodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;
import com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree;
import org.apache.commons.lang3.StringUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final NodeFactory nodeFactory = new DefaultCharArrayNodeFactory();

    protected static NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public static void main(String[] args) throws IOException {
        ConcurrentInvertedRadixTree<Integer> tree = createRadixTreeFromArrays();

        System.out.println("Tree structure:");
        // PrettyPrintable is a non-public API for testing, prints semi-graphical representations of trees...
        PrettyPrinter.prettyPrint((PrettyPrintable) tree, System.out);
        String harryPotterBook3 = fileToText();

        long startNanoTime = System.nanoTime();
        long startMilliTime = System.currentTimeMillis();
        tree.getValuesForKeysContainedIn(harryPotterBook3);
        //even creating the radix tree on the fly is way faster
        //createRadixTreeFromArrays().getKeysContainedIn(harryPotterBook3);
        long endTime = System.nanoTime() - startNanoTime;
        long endMilliTime = System.currentTimeMillis() - startMilliTime;
        System.out.println("Trie Search - Elapsed time in nano seconds: " + endTime);
        System.out.println("Trie Search -Elapsed time in milli seconds: " + endMilliTime);


        long loopNanoStartTime = System.nanoTime();
        long loopMilliStartTime = System.currentTimeMillis();
        for (String redactedPhrase : redactedPhrases()) {
            StringUtils.countMatches(harryPotterBook3, redactedPhrase);
        }
        long loopNanoEndTime = System.nanoTime() - loopNanoStartTime;
        long loopNanoMilliTime = System.currentTimeMillis() - loopMilliStartTime;
        System.out.println("Loop Search - Elapsed time in nano seconds: " + loopNanoEndTime);
        System.out.println("Loop Search =Elapsed time in milli seconds: " + loopNanoMilliTime);

        // loop search gets exponentially longer as we add more arrays to search
        // comment out some
    }


    private static String fileToText() throws IOException {
        {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


            // Reading File name
            String path = "src/main/java/org/cyberstudiosllc/harry_potter_3.txt";

            FileReader fr = new FileReader(path);

            // Declaring loop variable
            int i;
            StringBuilder line = new StringBuilder();


            // Holds true till there is nothing to read
            while ((i = fr.read()) != -1) {

                // Print all the content of a file
                line.append((char) i);
            }
            return line.toString().replaceAll("[^a-zA-Z ]", "").toLowerCase();
        }
    }

    private static ConcurrentInvertedRadixTree<Integer> createRadixTreeFromArrays() {
        ConcurrentInvertedRadixTree<Integer> tree = new ConcurrentInvertedRadixTree<Integer>(getNodeFactory());

        List<String> arrayList = redactedPhrases();

        int counter = 1;
        for (String str : arrayList) {
            tree.putIfAbsent(str, counter);
            counter++;
        }
        return tree;

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
