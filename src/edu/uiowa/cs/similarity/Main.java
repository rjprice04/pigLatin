package edu.uiowa.cs.similarity;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import opennlp.tools.stemmer.*;

public class Main {

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index - Name of the file. Creates the index of a file");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String delimiters = "[.?!\\s]";

        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } else if (command.equals("quit")) {
                System.exit(0);
            } else if (command.equals("index")) {
                ArrayList<String> sentences = new ArrayList();
                ArrayList<String> stopwords = new ArrayList();
                System.out.println("file name");
                String fileName = input.readLine();
                String stopWords = "../stopwords.txt";
                //file name   ../cleanup_test.txt
                Scanner sc = new Scanner(new File(fileName)).useDelimiter(delimiters);
                Scanner sw = new Scanner(new File(stopWords));
                PorterStemmer porterStemmer = new PorterStemmer();
                String word;
                while (sw.hasNext()) {
                    stopwords.add(sw.next());
                }
                while (sc.hasNext()) {
                    word = sc.next().toLowerCase();
                    if (!stopwords.contains(word)) {
                        sentences.add(porterStemmer.stem(word));

                    }
                }
                System.out.println("Num sentences");
                System.out.println(sentences);
                System.out.println(sentences.size());
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
