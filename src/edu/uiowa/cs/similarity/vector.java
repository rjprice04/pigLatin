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
         * index ../cleanup_test.txt index ../vector_test.txt
         ******
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
        if (this.vectorMap.containsKey(word)) {
            double uValue = 0;
            double uSquared = 0;
            double vValue = 0;
            double vSquared = 0;
            double cosValue;
            String subKeyWord;
            LinkedList<String> simWords = new LinkedList();
            Set<String> keyValues = new HashSet();
            Map<String, Double> subMap = new HashMap();
            Collection<Double> values = new ArrayList();
            subMap = this.vectorMap.get(word);
            values.addAll(subMap.values());
            Iterator uV = values.iterator();
            //Gets the value of u
            double temp;
            while (uV.hasNext()) {
                temp = (Double) uV.next();
                uValue += temp;
                uSquared = uValue + (temp * temp);
            }
            //Need to find the v values now
            keyValues = this.vectorMap.keySet();
            Iterator keys = keyValues.iterator();
            while (keys.hasNext()) {
                subKeyWord = (String) keys.next();
                if (!subKeyWord.equals(word)) { //dont check the word cause it will be 1
                    subMap = this.vectorMap.get(subKeyWord);
                    values.addAll(subMap.values());
                    Iterator vV = values.iterator();
                    while (vV.hasNext()) {
                        temp = (Double) vV.next();
                        vValue += temp;
                        vSquared = vValue + (temp * temp);
                    }
                    cosValue = (uValue * vValue) / (Math.sqrt(uSquared * vSquared));
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
