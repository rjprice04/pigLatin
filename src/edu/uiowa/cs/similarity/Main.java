package edu.uiowa.cs.similarity;

import java.io.*;
import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
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
    private static ArrayList getStopWords() throws FileNotFoundException{
        ArrayList<String> stopwords = new ArrayList();
        String stopWords = "../stopwords.txt";
        Scanner sw = new Scanner(new File(stopWords));
        while (sw.hasNext()) {
            stopwords.add(sw.next());
        }
        return stopwords;
    }
/******************************TODO*********************************************
 * 1- fix the last sentences so it doesn't read in the ! and same              *
 * 2- Work on adding vectors they mention adding a class to do that            *
 *      -- I am thinking a map using the words as the key so then we don't have*
 *         duplicates of the same word. For the values I'm not sure yet.       *
 * 3- Start working on the cosine vector thing.                                *
 *******************************************************************************/
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<ArrayList> sentences = new LinkedList();
        ArrayList stopwords=getStopWords();
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
                if(command.length()<6){
                    System.out.println("Enter file name");
                    fileName = input.readLine();
                }
                else{
                    fileName=command.substring(6);
                }
                System.out.println("Indexing "+fileName);
                Scanner sc = new Scanner(new File(fileName));
                PorterStemmer porterStemmer = new PorterStemmer();
                String word;

                while(sc.hasNext()){   

                    word = porterStemmer.stem(sc.next().toLowerCase());
                    while (true) {
                        if (!word.contains("?") && !word.contains(".") && !word.contains("!") && sc.hasNext()) {
                            if (!stopwords.contains(word)) {
                                oneSentence.add(word);
                            }
                            //word = porterStemmer.stem(sc.next().toLowerCase());
                        } 
                        else {
                            oneSentence.add(word.substring(0, word.length() - 1));
                            break;
                        }
                        word = porterStemmer.stem(sc.next().toLowerCase());
                    }
                    temp = new ArrayList(oneSentence);
                    sentences.add(temp);
                    oneSentence.clear();
                }
                Vectors vectors;
            }
            else if(command.equals("sentences")){
                System.out.println(sentences);
                System.out.println("Number of sentences");
                System.out.println(sentences.size());
            }
            else if(command.equals("vectors")){
                
            }
            else {
                System.err.println("Unrecognized command");
            }
        }
    }
    public class Vectors{
        Map<String, Integer> subMap;
        Map<String, Map<String, Integer>> vectorMap;
        
      
        private Vectors(LinkedList<ArrayList> sentences) {
            ArrayList sentence;
            String word;
            for (int i=0;i<sentences.size();i++){
                sentence=sentences.get(i);
                for(int j=0; j<sentence.size();j++){
                    word= (String)sentence.get(i);
                    if(!vectorMap.containsKey(word)){
                        vectorMap.put(word, subMap);
                    }
                    else{
                        subMap=vectorMap.get(word);
                        
                    }
                }
            }
        }
    }  
}
