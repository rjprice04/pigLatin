package edu.uiowa.cs.similarity;

import java.io.*;
import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
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

    private static ArrayList getStopWords() throws FileNotFoundException {
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
        ArrayList stopwords = getStopWords();
        //Vectors vector;
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
                 Vectors vector =new Vectors(sentences);
            } else if (command.equals("sentences")) {
                System.out.println(sentences);
                System.out.println("Number of sentences");
                System.out.println(sentences.size());
            } else if (command.equals("vectors")) {
                    
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
    
    private static class Vectors{
        //public Map<String, Integer> subMap;
        public Map<String, Map<String, Double>> vectorMap;
        
      
        private Vectors(LinkedList<ArrayList> sentences) {
            this.vectorMap=new HashMap();
            ArrayList sentence; 
            String word; // key for vector map
            Map<String, Double> subMap = new HashMap();
            Set<String> listOfWords = new HashSet<>();
            Map<String, Map<String, Double>> vectorMap= new HashMap();
            for (int i=0;i<sentences.size();i++){ //Fills the map that will go within the vectorMap
                sentence=sentences.get(i);
                for(int j=0; j<sentence.size();j++){
                    word= (String)sentence.get(j);
                    subMap.put(word, 0.0); 
                    
                }
            }
            System.out.println("SubMap of words " +subMap);
            //Fills the vectorMap
            for (int k=0;k<sentences.size();k++){ //Fills the map that will go within the vectorMap
                sentence=sentences.get(k);
                for(int m=0; m<sentence.size();m++){
                    word = (String)sentence.get(m);
                    vectorMap.put(word, subMap); 
                    
                }
            }
            String keyWord;
            String subKeyWord;
            double value;
            this.vectorMap=vectorMap;
            System.out.println("VectorMap of words " +vectorMap);
            //Add the values for words
            for(int i = 0; i<sentences.size();i++){
                sentence=sentences.get(i);
                for(int j=0; j<sentence.size();j++){
                    keyWord=(String)sentence.get(j);
                    subMap=this.vectorMap.get(keyWord);
                    for(int k=0; k<sentence.size();k++){
                        subKeyWord=(String)sentence.get(k);
                        value=subMap.get(subKeyWord);
                        subMap.replace(subKeyWord, value, value+1);
                    }
                    this.vectorMap.put(keyWord, subMap);
                }
                
            }
            System.out.println("New vectorMap "+this.vectorMap);
       }
    }
}
    

