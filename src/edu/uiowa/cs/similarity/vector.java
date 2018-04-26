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

    Vector(LinkedList<ArrayList> sentences) {
        this.vectorMap = new HashMap();
        ArrayList oneSentence;
        Map<String, Double> subMap = new HashMap();
        Set<String> wordsInSentence = new HashSet();
        double value;
        String keyWord;
        for (int i = 0; i < sentences.size(); i++) { //Fills the subMap 
            //gets one sentence of the paragraph
            oneSentence = sentences.get(i);
            wordsInSentence.addAll(oneSentence);
            for (int j = 0; j < oneSentence.size(); j++) {
                //gets one word of the sentence
                keyWord=(String)oneSentence.get(j);
                
            }
        }

            
        for (int i=0;i<sentences.size();i++){ //Fills the vectorMap
            oneSentence=sentences.get(i);
            for(int j=0; j<oneSentence.size();j++){
                this.vectorMap.put((String)oneSentence.get(j), subMap); 
            }
        }

        System.out.println("New vectorMap " + this.vectorMap);
    }

    void addToMap(ArrayList<String> oneSentence) {
        
    }
}
