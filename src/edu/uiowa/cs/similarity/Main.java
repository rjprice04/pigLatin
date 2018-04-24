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
                ArrayList<String> oneSentence = new ArrayList();
                LinkedList<ArrayList> allSentences = new LinkedList();
                LinkedList<ArrayList> sentences = new LinkedList();
                ArrayList<String> stopwords = new ArrayList();
                Stack<ArrayList> stack = new Stack();
                System.out.println("file name");
                String fileName = input.readLine();
                String stopWords = "../stopwords.txt";
                //file name   ../cleanup_test.txt
                Scanner sc = new Scanner(new File(fileName));//.useDelimiter(delimiters);
                //Scanner oneSentence;
                Scanner sw = new Scanner(new File(stopWords));
                PorterStemmer porterStemmer = new PorterStemmer();
                //ArrayList oneSentence;
                String word;
                ArrayList temp = new ArrayList();
                while (sw.hasNext()) {
                    stopwords.add(sw.next());
                }
                for (int i = 0; sc.hasNext(); i++) {
                    int j = 0;
                    //words.add(porterStemmer.stem(sc.nextLine().toLowerCase()));
                    //word = sc.next();
                    //while (!sc.next().equals("?") && !sc.next().equals(".") && !sc.next().equals("!")) {
                    word = porterStemmer.stem(sc.next().toLowerCase());
                    while (true) {
                        if (!word.contains("?") && !word.contains(".") && !word.contains("!") && sc.hasNext()) {
                            if (!stopwords.contains(word)) {
                                oneSentence.add(word);
                            }
                            word = porterStemmer.stem(sc.next().toLowerCase());
                        } else {
                            oneSentence.add(word.substring(0, word.length() - 1));
                            break;
                        }
                        j++;
                        //sc.next();
                    }
                    stack.push(oneSentence);
                    //allSentences.push(oneSentence);
                    //sentences.add(stack.pop());
                    allSentences.add(stack.pop());
                    sentences = allSentences;
                    //sentences.push(stack.pop());
                   oneSentence.clear();
                   //allSentences.clear();
                    
                }
                //oneSentence = words;
                //words.add(word);
//                    if (!stopwords.contains(oneSentence.get(i))) {
//                        //sentences.add(porterStemmer.stem(word));
//                        sentences.push(words);
//                        //sentences.push(porterStemmer.stem(word));
//                    }
                //}

                System.out.println("Num sentences");
                System.out.println(sentences);
                System.out.println(sentences.size());
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
