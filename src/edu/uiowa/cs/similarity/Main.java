package edu.uiowa.cs.similarity;

import java.io.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import opennlp.tools.stemmer.*;

public class Main {

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index - Input:Name of the file. Creates the index of a file");
        System.out.println("sentences - Prints the sentences and number of sentences");
        System.out.println("vectors - Prints the vectors");
        System.out.println("topj- Input: Word(Q) and number(J). Find the J most simalar words to Q");
        System.out.println("within topj: euc: Word(Q) and number(J). Find the euclidean distance of two vectors.");
        System.out.println("within topj: eucnorm: Word(Q) and number(J). Find the normal euclidean distance of two vectors");
    }

    private static Set getStopWords() throws FileNotFoundException {
        Set<String> stopwords = new HashSet();
        String stopWords = "../stopwords.txt";
        Scanner sw = new Scanner(new File(stopWords));
        while (sw.hasNext()) {
            stopwords.add(sw.next());
        }
        return stopwords;
    }

    /**
     * ****************************TODO*********************************************
     * 2- Work on adding vectors they mention adding a class to do that * -- I
     * am thinking a map using the words as the key so then we don't have*
     * duplicates of the same word. For the values I'm not sure yet. * 3- Start
     * working on the cosine vector thing. *
     * *****************************************************************************
     */
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        List<ArrayList> sentences = new ArrayList();
        Set<String> stopwords = getStopWords();
        String delimiter = "[?!.]";
        vector aVector = new vector();
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
                    fileName = command.substring(6);// six being the index after the space after index
                }
                System.out.println("Indexing " + fileName);
                Scanner sc = new Scanner(new File(fileName)).useDelimiter(delimiter);
                PorterStemmer porterStemmer = new PorterStemmer();
                String word;
                String sentence;
                while (sc.hasNext()) {
                    //read in a sentence
                   // ArrayList<String> cleanedInput = sc.next()(a.toLowerCase());
                    sentence = sc.next().toLowerCase();
                    Scanner sw = new Scanner(sentence);
                    while (sw.hasNext()) {
                        //should have individual words now
                        word = sw.next().toLowerCase();
                        if (word.endsWith(",")) {
                            word = word.substring(0, word.length() - 1);
                        }
                        if (!stopwords.contains(word) && word.length() > 1) {
                            word = porterStemmer.stem(word);
                            if (word.contains(",")) {
                                oneSentence.add(word.substring(0, word.length() - 1));
                            } else {
                                oneSentence.add(word);
                            }
                        }
                    }
                    if (!oneSentence.isEmpty()) {
                        temp = new ArrayList(oneSentence);
                        aVector.addToMap(oneSentence);
                        sentences.add(temp);
                        oneSentence.clear();
                    }

                }
                //aVector = new Vector(sentences);
            } else if (command.equals("sentences")) {
                System.out.println(sentences);
                System.out.println("Number of sentences");
                System.out.println(sentences.size());
            } else if (command.equals("vectors")) {

                aVector.print();

            } else if (command.equals("topj")) {
                String word;
                System.out.println("What word would you like to use");
                word = input.readLine();
                // int number;
                System.out.println("How many word do you want to compare to?");
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                int number = reader.nextInt();
                System.out.println("What method would like to use?");
                String choiceWord = input.readLine();
                if(choiceWord.equals("euc")){
                    aVector.computeEucDistance(word, number);
                } else if(choiceWord.equals("eucnorm")){
                    
                } else if(choiceWord.equals("cosine") || choiceWord.equals("")) {
                    aVector.computeTopJ(word, number);
                } else{
                    aVector.computeTopJ(word, number);
                }
            } 
            else if(command.equals("kmean")){
                System.out.println("What k do you want to use?");
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                int kMean = reader.nextInt();
                System.out.println("How many iterations?");
                int iter = reader.nextInt();
                
            }
                
            else {
                System.err.println("Unrecognized command");
            }
        }
    }

}
