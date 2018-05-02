/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ryan Price
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
                    subKeyWord = oneSentence.get(j);
                    if (!(keyWord.equals(subKeyWord))) {
                        subMap.put(subKeyWord, 1.0);
                    }
                }
            } else {
                subMap = this.vectorMap.get(keyWord);
                for (int k = 0; k < oneSentence.size(); k++) {
                    subKeyWord = oneSentence.get(k);
                    if (subMap.containsKey(subKeyWord)) {
                        double tempValue;
                        tempValue = subMap.get(subKeyWord);
                        subMap.replace(subKeyWord, tempValue, tempValue + 1.0);
                    } else {
                        if (!subKeyWord.equals(keyWord)) {
                            subMap.put(subKeyWord, 1.0);
                        }
                    }

                }
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
            double numerator = 0;
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

            subMapForUValues = this.vectorMap.get(word); 
            valuesForPickedWord.addAll(subMapForUValues.values()); 
            Iterator u2Values = valuesForPickedWord.iterator(); 
            
            while (u2Values.hasNext()) { //Big O of S

                tempValueU = (double) u2Values.next(); 
                uSquared += Math.pow(tempValueU, 2);
            }

            Iterator uValues = valuesForPickedWord.iterator(); 
            while (uValues.hasNext()) { //Big O of S

                tempValueU = (double) uValues.next(); 

                subKeyValues = this.vectorMap.keySet(); 
                Iterator subKeys = subKeyValues.iterator(); 

                while (subKeys.hasNext()) { 

                    subKeyWord = (String) subKeys.next();
                    Collection<String> currentKey = new ArrayList();
                    subMapForVValues = this.vectorMap.get(subKeyWord); 
                    currentKey.addAll(subMapForUValues.keySet());
                    Iterator currentWords = currentKey.iterator();
                    valuesForOtherWords.addAll(subMapForVValues.values()); 
                    Iterator vValues = valuesForOtherWords.iterator(); 

                    String currentWord = null;

                    while (vValues.hasNext()) { //Big O of J
                        if (currentWords.hasNext()) {
                            currentWord = currentWords.next().toString();
                        }
                        tempValueV = (double) vValues.next(); 

                        vSquared += Math.pow(tempValueV, 2);
                        if (subMapForUValues.containsKey(subKeyWord) && !currentWord.equals(subKeyWord)) { 
                            numerator += (tempValueU * tempValueV); 
                        }
                    }
                    valuesForOtherWords.clear();
                    denomiator = Math.sqrt(uSquared * vSquared);  
                    cosValue = numerator / denomiator; 
                    thatList.addOrder(subKeyWord, cosValue, number); 
                    vSquared = 0;
                    numerator = 0;

                }

            }

        } else {
            System.out.println("Cannot compute TopJ similarity to " + word);

        }

        thatList.print(number);

    }

    void computeEucDistance(String word, int number) {
        OrderedLinkedList finalValues = new OrderedLinkedList();
        if (this.vectorMap.containsKey(word)) {
            Map<String, Double> subMapForUValues;
            Set<String> subKeyValues;
            Map<String, Double> subMapForVValues;
            Collection<Double> valuesForPickedWord = new ArrayList();
            Collection<Double> valuesForOtherWords = new ArrayList();
            subMapForUValues = this.vectorMap.get(word);
            subKeyValues = this.vectorMap.keySet();
            Iterator subKeys = subKeyValues.iterator();
            valuesForPickedWord.addAll(subMapForUValues.values());
            Iterator uValues = valuesForPickedWord.iterator();
            String subKeyWord;
            double tempValueU;
            double uSquared;
            double tempValueV;
            double vSquared;
            double finalSquareRoot;
            double tempFinalValue = 0;
            while (subKeys.hasNext()) {
                subKeyWord = (String) subKeys.next();
                subMapForVValues = this.vectorMap.get(subKeyWord);
                valuesForOtherWords.addAll(subMapForVValues.values()); //gets the values for that key
                Iterator vValues = valuesForOtherWords.iterator();
                while (uValues.hasNext() && vValues.hasNext()) {
                    tempValueU = (double) uValues.next();
                    tempValueV = (double) vValues.next();
                    uSquared = Math.pow(tempValueU, 2);
                    vSquared = Math.pow(tempValueV, 2);
                    tempFinalValue = (uSquared - vSquared) + tempFinalValue;
                }
                finalSquareRoot = Math.sqrt(tempFinalValue);
                finalValues.addOrderSmaller(subKeyWord, -finalSquareRoot, number);
            }
        }
        finalValues.print(number);
    }
    
}
