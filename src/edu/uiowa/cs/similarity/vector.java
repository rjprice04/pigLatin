/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ryanprice
 */
public class vector {

    public Map<String, Map<String, Double>> vectorMap;

    vector() {
        this.vectorMap = new HashMap();
    }
    
    void print() {
        if (this.vectorMap.isEmpty()) {
            System.out.println("Please index a file and try again for help type help");
        } else {
            System.out.println(this.vectorMap);
        }
    }


    void addToMap(ArrayList<String> oneSentence) {
        /**
         * ***
         * Step 1 get a word for the key Step 2: add the values for the subMap
         * get subWordKey get sub word value Step 3 put keyWord and subMap
         * together
         *
         * index ../cleanup_test.txt index ../vector_test.txt *****
         */

        String keyWord;
        Map<String, Double> subMap = new HashMap();
        Map<String, Double> temp;
        String subKeyWord;
        for (int i = 0; i < oneSentence.size(); i++) {
            keyWord = oneSentence.get(i);
            if (!this.vectorMap.containsKey(keyWord)) {
                for (int j = 0; j < oneSentence.size(); j++) {
                    // subMap = new HashMap();
                    subKeyWord = oneSentence.get(j);
                    if (!(keyWord.equals(subKeyWord))) {
                        subMap.put(subKeyWord, 1.0);
                    }
                }
            } 
            else {
                subMap = this.vectorMap.get(keyWord);
                for (int k = 0; k < oneSentence.size(); k++) {
                    subKeyWord = oneSentence.get(k);
                    if (subMap.containsKey(subKeyWord)) {
                        double tempValue;
                        tempValue = subMap.get(subKeyWord);
                        subMap.replace(subKeyWord, tempValue, tempValue + 1.0);
                    } 
                    else {
                        if (!subKeyWord.equals(keyWord)) {
                            subMap.put(subKeyWord, 1.0);
                        }
                    }

                }
                // this.vectorMap.put(keyWord, subMap);
            }
            if (!subMap.isEmpty()) {
                temp = new HashMap();
                temp.putAll(subMap);
                this.vectorMap.put(keyWord, temp);
                subMap.clear();
            }
        }
    }

    void computeTopJ(String word, int number) {
        OrderedLinkedList thatList = new OrderedLinkedList();
        if (this.vectorMap.containsKey(word)) {
            
            double uSquared = 0;
            double vSquared = 0;
            double numerator=0;
            double denomiator;
            double cosValue;
            double tempValueU;
            double tempValueV;
            String subKeyWord;
            Set<String> subKeyValues;
            Map<String, Double> subMapForUValues;
            Map<String, Double> subMapForVValues;
            Collection<Double> valuesForPickedWord = new ArrayList();
            Collection<Double> valuesForOtherWords = new ArrayList();
            
            subMapForUValues = this.vectorMap.get(word); //gets the map assiocated with the word they picked
            valuesForPickedWord.addAll(subMapForUValues.values()); //gets the values for that map
            Iterator u2Values = valuesForPickedWord.iterator(); //puts the values in an iterator
            //Runs through the sentences that conatins the word U
            while (u2Values.hasNext()) { 
                
                tempValueU = (double) u2Values.next(); //gets the uSquared value
                uSquared += Math.pow(tempValueU, 2); 
            }

            Iterator uValues = valuesForPickedWord.iterator(); //puts the values in an iterator
            //Runs through the sentences that conatins the word U
            while (uValues.hasNext()) { //still values to add
               
                tempValueU = (double) uValues.next(); //the value we are looking at 

                subKeyValues = this.vectorMap.keySet(); //gets the keys of the vector map
                Iterator subKeys = subKeyValues.iterator(); //puts the keys in an iterator
                //Run through the other sentences
                while (subKeys.hasNext()) { //looks at all the keys that
                    
                    subKeyWord = (String) subKeys.next(); //gets the next key in the list
                    subMapForVValues = this.vectorMap.get(subKeyWord); //gets the map for the the key we are looking at 
                    valuesForOtherWords.addAll(subMapForVValues.values()); //gets the values for that key
                    Iterator vValues = valuesForOtherWords.iterator(); //puts those keys in an iterator
                   
                   
                    while (vValues.hasNext()) { //looks through all of the values
                       numerator=0;
                        
                        tempValueV = (double) vValues.next(); //looks a the current v value

                        vSquared+=Math.pow(tempValueV, 2);

                        if (subMapForUValues.containsKey(subKeyWord)) { //sees if the subWord is in the U values sub map
                            numerator += (tempValueU * tempValueV); //adds them to the numerator if the are
                            //System.out.println(numerator);
                        }

                    }
                    denomiator = Math.sqrt( uSquared * vSquared);  //gets the denomiator
                    cosValue = numerator / denomiator;  //gets the cosValue
                    thatList.addOrder(subKeyWord, cosValue); //addeds to a list
                    vSquared=0;

                }
             

            }

        } 
        else {
            System.out.println("Cannot compute TopJ similarity to " + word);

        }
 
            thatList.print(number);
        
    }
}
