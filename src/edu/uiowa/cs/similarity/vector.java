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
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author ryanprice
 */
class Vector {

    public Map<String, Map<String, Double>> vectorMap;

    void print() {
        if (this.vectorMap.isEmpty()) {
            System.out.println("Please index a file and try again for help type help");
        } else {
            System.out.println(this.vectorMap);
        }
    }

    Vector() {
        this.vectorMap = new HashMap();
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
        double uValue = 1;
        double uSquared = 1;
        double vValue = 1;
        double vSquared = 1;
        double numerator = 1;
        double denomiator = 1;
        double cosValue = 1;
        String subKeyWord;
        Set<String> keyValues = new HashSet();
        Map<String, Double> subMap = new HashMap();
        Collection<Double> values = new ArrayList();
//   ../easy_sanity_test.txt
        if (this.vectorMap.containsKey(word)) {
            subMap = this.vectorMap.get(word);
            values.addAll(subMap.values());
            Iterator uV = values.iterator();
            //Gets the value of u
            double temp = 0.0;
            while (uV.hasNext()) {
                temp = (double) uV.next();
                uValue += temp;
                uSquared = uValue + Math.pow(temp,2);
                //uSquared = uValue + (temp * temp);
            }
            //Need to find the v values now
            keyValues = this.vectorMap.keySet();
            Iterator keys = keyValues.iterator();
            temp = 0.0;
            while (keys.hasNext()) {
                subKeyWord = (String) keys.next();
                if (!subKeyWord.equals(word)) { //dont check the word cause it will be 1
                    subMap = this.vectorMap.get(subKeyWord);
                    values.addAll(subMap.values());
                    Iterator vV = values.iterator();
                    while (vV.hasNext()) {
                        temp = (double) vV.next();
                        vValue += temp;
                        //vSquared = vValue + (temp * temp);
                        vSquared = vValue + Math.pow(temp, 2);
                    }
                    temp = 0;
                    numerator = uValue * vValue;
                    denomiator = uSquared * vSquared;
                    denomiator = Math.sqrt(denomiator);
                    cosValue = numerator / denomiator;
                    thatList.addOrder(subKeyWord, cosValue);
                    //need to compare and add to a list of some sort 
                }
            }
        } else {
            System.out.println("Cannot compute TopJ similarity to " + word);
        }
        thatList.print(number);
    }
}
