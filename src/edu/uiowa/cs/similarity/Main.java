package edu.uiowa.cs.similarity;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import opennlp.tools.stemmer.*;

public class Main {

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index - Name of the file. Creates the index of a file");
    }

    private static ArrayList getStopWords() throws FileNotFoundException {
        ArrayList<String> stopwords = new ArrayList();
        String stopWords = "../stopwords.txt";
        Scanner sw = new Scanner(new File(stopWords));
        while (sw.hasNext()) {
            stopwords.add(sw.next());
        }
        return stopwords;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String delimiters = "[.?!,\\s]";
        LinkedList<ArrayList> sentences = new LinkedList();
        ArrayList stopwords = getStopWords();
        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } else if (command.equals("quit")) {
                System.exit(0);
            } else if (command.contains("index")) {
                ArrayList<String> oneSentence = new ArrayList();
                ArrayList temp;
                String fileName; //  ../cleanup_test.txt
                if (command.length() < 6) {
                    System.out.println("Enter file name");
                    fileName = input.readLine();
                } else {
                    fileName = command.substring(6);
                }
                System.out.println("Indexing " + fileName);
                Scanner sc = new Scanner(new File(fileName));
                PorterStemmer porterStemmer = new PorterStemmer();
                String word;

                while (sc.hasNext()) {
                    word = sc.next().toLowerCase();
                    while (true) {
                        if (!word.contains("?") && !word.contains(".") && !word.contains("!") && sc.hasNext()) {
                            if (!stopwords.contains(word)) {
                                word = porterStemmer.stem(word);
                                if (word.contains(",")) {
                                    oneSentence.add(word.substring(0, word.length() - 1));
                                } else {
                                    oneSentence.add(word);
                                }
                            }
                            word = sc.next().toLowerCase();
                        } else {
                            word = word.substring(0, word.length() - 1);
                            if (word.contains("?") || word.contains(".") || word.contains("!")) {
                                word = word.substring(0, word.length() - 1);
                            }
                            word = porterStemmer.stem(word);
                            oneSentence.add(word);
                            break;
                        }
                    }
                    temp = new ArrayList(oneSentence);
                    sentences.add(temp);
                    oneSentence.clear();
                }

            } else if (command.equals("sentences")) {
                System.out.println(sentences);
                System.out.println("Num sentences");
                System.out.println(sentences.size());
            } else if (command.equals("vectors")) {

            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
