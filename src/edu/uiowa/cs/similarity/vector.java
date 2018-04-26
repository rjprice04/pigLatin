/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ryanprice
 */
class Vector {

    public Map<String, Map<String, Double>> vectorMap;

    void print() {
        System.out.println(this.vectorMap);
    }

    Vector() {
        this.vectorMap = new HashMap();
    }

    void addToMap(ArrayList<String> oneSentence) {
        /*Step 1 get a word for the key
        Step 2: add the values for the subMap
            get subWordKey
            get sub word value
        Step 3 put keyWord and subMap together
         */
        //                  index ../cleanup_test.txt
        //                  index ../vector_test.txt
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
            }
            temp = new HashMap();
            temp.putAll(subMap);
            this.vectorMap.put(keyWord, temp);
            subMap.clear();
        }
    }
}
