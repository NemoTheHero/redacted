package org.cyberstudiosllc;

import com.googlecode.concurrenttrees.common.Iterables;
import com.googlecode.concurrenttrees.common.PrettyPrinter;

import com.googlecode.concurrenttrees.radix.node.NodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radix.node.util.PrettyPrintable;
import com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

        System.out.println();
        System.out.println("Value for 'velit pulvinar eleifend' (exact match): " + tree.getValueForExactKey("velit pulvinar eleifend"));
        System.out.println("Value for 'velit pulvinar eleifend' (exact match): " + tree.getValueForExactKey("velit"));

        System.out.println("Values for keys starting with 'velit': " + Iterables.toString(tree.getValuesForKeysStartingWith("velit")));
        System.out.println("Values for keys starting with 'vel f': " + Iterables.toString(tree.getValuesForKeysStartingWith("vel")));
        long startTime = System.currentTimeMillis();

//        System.out.println(Iterables.toString(tree.getKeysContainedIn(fileToText())));
        tree.getKeysContainedIn(fileToText());
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time in milliseconds: " + endTime);
//        System.out.println(Iterables.toString(tree.getValuesForKeysContainedIn(fileToText())));


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
            while ((i = fr.read()) != -1){

                // Print all the content of a file
                line.append((char) i);
            }
            return line.toString().replaceAll("[^a-zA-Z ]", "").toLowerCase();
        }
    }

    private static ConcurrentInvertedRadixTree<Integer> createRadixTreeFromArrays() {
        ConcurrentInvertedRadixTree<Integer> tree = new ConcurrentInvertedRadixTree<Integer>(getNodeFactory());

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
        int counter = 1;
        for (String str : arrayList) {
            tree.putIfAbsent(str, counter);
            counter++;
        }
        return tree;

    }
}
