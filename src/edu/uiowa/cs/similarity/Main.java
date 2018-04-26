package edu.uiowa.cs.similarity;

import java.io.*;

import java.util.ArrayList;
import java.util.HashSet;
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
        System.out.println("sentences - Prints the sentences. Prints the number of sentences");
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
/******************************TODO*********************************************
 * 2- Work on adding vectors they mention adding a class to do that            *
 *      -- I am thinking a map using the words as the key so then we don't have*
 *         duplicates of the same word. For the values I'm not sure yet.       *
 * 3- Start working on the cosine vector thing.                                *
 *******************************************************************************/
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<ArrayList> sentences = new LinkedList();
        Set<String> stopwords = getStopWords();
        String delimiter = "[?!.]";
        //Vectors vector;
        Vector aVector = null;
        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } 
            else if (command.equals("quit")) {
                System.exit(0);
            } 
            else if (command.contains("index")) {
                ArrayList<String> oneSentence = new ArrayList();
                ArrayList temp;
                String fileName; //  ../cleanup_test.txt
                if (command.length() < 6) {
                    System.out.println("Enter file name");
                    fileName = input.readLine();
                } else {
                    fileName=command.substring(6);// six being the index after the space after index
                }
                System.out.println("Indexing " + fileName);
                Scanner sc = new Scanner(new File(fileName)).useDelimiter(delimiter);
                PorterStemmer porterStemmer = new PorterStemmer();
                String word;
                String sentence;
                while (sc.hasNext()) {
                    //read in a sentence
                    sentence = sc.next().toLowerCase();
                    Scanner sw = new Scanner(sentence);
                    while(sw.hasNext()){
                        //should have individual words now
                        word=sw.next().toLowerCase();
                        if(!stopwords.contains(word) && word.length()>1){
                           word = porterStemmer.stem(word);
                           if(word.contains(",")){
                               oneSentence.add(word.substring(0,word.length()-1));
                           }
                           else{
                                oneSentence.add(word);
                           }
                        }
                    }
                    if(!oneSentence.isEmpty()){
                        temp= new ArrayList(oneSentence);
                        aVector.addToMap(oneSentence);
                        sentences.add(temp);
                        oneSentence.clear();
                    }
                    
                }
                //aVector = new Vector(sentences);
            }
//                    word = sc.next().toLowerCase();
//                    while (true) {
//                        if (!word.contains("?") && !word.contains(".") && !word.contains("!") && sc.hasNext()) {
//                            if (!stopwords.contains(word)) {
//                                word = porterStemmer.stem(word);
//                                if (word.contains(",")) {
//                                    oneSentence.add(word.substring(0, word.length() - 1));
//                                } else {
//                                    oneSentence.add(word);
//                                }
//                            }
//                            word = sc.next().toLowerCase();
//                        } else {
//                            word = word.substring(0, word.length() - 1);
//                            if (word.contains("?") || word.contains(".") || word.contains("!")) {
//                                word = word.substring(0, word.length() - 1);
//                            }
//                            word = porterStemmer.stem(word);
//                            oneSentence.add(word);
//                            break;
//                        }
//                    }
//                    temp = new ArrayList(oneSentence);
//                    sentences.add(temp);
//                    oneSentence.clear();
//                }
//                 aVector = new Vector(sentences);
//                }
//            }
            else if (command.equals("sentences")) {
                System.out.println(sentences);
                System.out.println("Number of sentences");
                System.out.println(sentences.size());
            }
            else if (command.equals("vectors")) {
                if(aVector == null){
                    System.out.println("Please index a file");
                }
                else{
                    aVector.print();
                }
            }
            else {
                System.err.println("Unrecognized command");
            }
        }
    }

}
    

