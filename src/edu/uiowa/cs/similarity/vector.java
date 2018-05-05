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
import java.util.Random;
import java.util.Set;
import javafx.util.Pair;

/**
 *
 * @author Ryan Price
 */
public class vector {

    public static OrderedLinkedList orderedList;
    public Map<String, Map<String, Double>> vectorMap;
    public Set<Set<String>> vectorSet;
    public String word;

    public vector(Set<Set<String>> vectorSet, String word) {
        this.vectorSet = vectorSet;
        this.word = word;
    }
    public vector(Map<String, Map<String, Double>> vectorMap, String word){
        this.vectorMap = vectorMap;
        this.word = word;
    }
    public vector() {
        vectorMap = new HashMap();
    }

    public OrderedLinkedList getList() {
        return orderedList;
    }

    public void print() {
        if (this.vectorMap.isEmpty()) {
            System.out.println("Please index a file and try again for help type help");
        } else {
            System.out.println(this.vectorMap);
        }
    }

    public void addToMap(ArrayList<String> oneSentence) {

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

    double getUSquared(String word) {
        double uSquared = 0;
        double tempValueU;
        Map<String, Double> subMapForUValues;
        subMapForUValues = this.vectorMap.get(word);
        Collection<Double> valuesForPickedWord = new ArrayList();
        valuesForPickedWord.addAll(subMapForUValues.values());
        Iterator u2Values = valuesForPickedWord.iterator();

        while (u2Values.hasNext()) { //Big O of S

            tempValueU = (double) u2Values.next();
            uSquared += Math.pow(tempValueU, 2);
        }

        return uSquared;
    }

    void computeTopJ(String word, int number) {
        OrderedLinkedList thatList = new OrderedLinkedList();
        if (this.vectorMap.containsKey(word)) {

            double uSquared = getUSquared(word);
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
        thatList.printCos(number);

    }

    public void computeEucDistance(String word, int number) {
        OrderedLinkedList finalValues = new OrderedLinkedList();
        if (this.vectorMap.containsKey(word)) {
            Map<String, Double> subMapForUValues;
            Set<String> subKeyValues;
            Map<String, Double> subMapForVValues;
            Collection<Double> valuesForPickedWord = new ArrayList();
            Collection<Double> valuesForOtherWords = new ArrayList();
            Collection<String> currentKey = new HashSet<>();
            subMapForUValues = this.vectorMap.get(word);
            subKeyValues = this.vectorMap.keySet();
            Iterator subKeys = subKeyValues.iterator();
            String subKeyWord;
            double tempValueU = 0;
            double tempValueV = 0;
            double tempDifference = 0;
            double eucDistance;
            double tempFinalValue = 0;
            String currentWord = null;

            while (subKeys.hasNext()) {
                subKeyWord = (String) subKeys.next();
                subMapForVValues = this.vectorMap.get(subKeyWord);
                valuesForOtherWords.addAll(subMapForVValues.values()); //gets the values for that key
                Iterator vValues = valuesForOtherWords.iterator();
                valuesForPickedWord.addAll(subMapForUValues.values());
                Iterator uValues = valuesForPickedWord.iterator();
                currentKey.addAll(subMapForUValues.keySet());
                currentKey.addAll(subMapForVValues.keySet());
                Iterator currentWords = currentKey.iterator();
                while (uValues.hasNext() && vValues.hasNext() && currentWords.hasNext()) {
                    currentWord = currentWords.next().toString();
                    if (subMapForUValues.containsKey(currentWord) && subMapForVValues.containsKey(currentWord)) {
                        if (uValues.hasNext()) {
                            tempValueU = (double) uValues.next();
                        }
                        if (vValues.hasNext()) {
                            tempValueV = (double) vValues.next();
                        }
                        tempDifference = tempValueU - tempValueV;
                        if (tempDifference > 0) {
                            tempFinalValue = Math.pow(tempDifference, 2) + tempFinalValue;
                        } else {
                            tempFinalValue = tempFinalValue - Math.pow(tempDifference, 2);
                        }
                    } else if (subMapForUValues.containsKey(currentWord) && !subMapForVValues.containsKey(currentWord)) {
                        if (uValues.hasNext()) {
                            tempValueU = (double) uValues.next();
                        }
                        tempFinalValue = Math.pow((tempValueU - 0), 2) + tempFinalValue;
                    } else if (!subMapForUValues.containsKey(currentWord) && subMapForVValues.containsKey(currentWord)) {
                        if (vValues.hasNext()) {
                            tempValueV = (double) vValues.next();
                        }
                        tempFinalValue = tempFinalValue - Math.pow((0 - tempValueV), 2);
                    }
                }
                if (tempFinalValue < 0) {
                    tempFinalValue = -tempFinalValue;
                }
                eucDistance = Math.sqrt(tempFinalValue);
                if (!word.equals(subKeyWord)) {
                    finalValues.addOrderEuc(subKeyWord, -eucDistance, number);
                }
                tempFinalValue = 0;
            }
        } else {
            System.out.println("Cannot compute TopJ similarity to " + word);
        }
        orderedList = finalValues;
        finalValues.printEuc(number);
    }

    public void computeNormEuc(String word, int number) {
        OrderedLinkedList finalNormValues = new OrderedLinkedList();
        if (this.vectorMap.containsKey(word)) {
            Map<String, Double> subMapForUValues;
            Set<String> subKeyValues;
            Map<String, Double> subMapForVValues;
            Collection<Double> valuesForPickedWord = new ArrayList();
            Collection<Double> valuesForOtherWords = new ArrayList();
            Collection<String> currentKey = new HashSet<>();
            subMapForUValues = this.vectorMap.get(word);
            subKeyValues = this.vectorMap.keySet();
            Iterator subKeys = subKeyValues.iterator();
            String subKeyWord;
            double tempValueU;
            double tempValueV;
            double tempDifference = 0;
            double eucNormDistance;
            double tempFinalValue = 0;
            String currentWord;
            double squareUValue = Math.sqrt(getUSquared(word));
            double squareVValue = 0;

            while (subKeys.hasNext()) {
                subKeyWord = (String) subKeys.next();
                subMapForVValues = this.vectorMap.get(subKeyWord);
                valuesForOtherWords.addAll(subMapForVValues.values()); //gets the values for that key
                Iterator vValues = valuesForOtherWords.iterator();
                Iterator uValues = valuesForPickedWord.iterator();
                Iterator v2Values = valuesForOtherWords.iterator(); //puts the values in an iterator
                currentKey.addAll(subMapForUValues.keySet());
                currentKey.addAll(subMapForVValues.keySet());
                Iterator currentWords = currentKey.iterator();
                squareVValue = 0;
                tempValueV = 0;
                tempValueU = 0;
                while (v2Values.hasNext()) {
                    tempValueV = (double) v2Values.next();
                    squareVValue += Math.pow(tempValueV, 2);
                }
                squareVValue = Math.sqrt(squareVValue);
                while (uValues.hasNext() && vValues.hasNext() && currentWords.hasNext()) {
                    currentWord = currentWords.next().toString();
                    if (subMapForUValues.containsKey(currentWord) && subMapForVValues.containsKey(currentWord)) {
                        if (uValues.hasNext()) {
                            tempValueU = (double) uValues.next();
                        }
                        if (vValues.hasNext()) {
                            tempValueV = (double) vValues.next();
                        }
                        tempValueU = tempValueU / squareUValue;
                        tempValueV = tempValueV / squareVValue;
                        tempDifference = tempValueU - tempValueV;
                        if (tempDifference > 0) {
                            tempFinalValue = Math.pow(tempDifference, 2) + tempFinalValue;
                        } else {
                            tempFinalValue = tempFinalValue - Math.pow(tempDifference, 2);
                        }
                    } else if (subMapForUValues.containsKey(currentWord) && !subMapForVValues.containsKey(currentWord)) {
                        if (uValues.hasNext()) {
                            tempValueU = (double) uValues.next();
                        }
                        tempValueU = tempValueU / squareUValue;
                        tempFinalValue = Math.pow((tempValueU - 0), 2) + tempFinalValue;
                    } else if (!subMapForUValues.containsKey(currentWord) && subMapForVValues.containsKey(currentWord)) {
                        if (vValues.hasNext()) {
                            tempValueV = (double) vValues.next();
                        }
                        tempValueV = tempValueV / squareVValue;
                        tempFinalValue = tempFinalValue - Math.pow((0 - tempValueV), 2);
                    }
                }
                if (tempFinalValue < 0) {
                    tempFinalValue = -tempFinalValue;
                }
                eucNormDistance = Math.sqrt(tempFinalValue);
                if (!word.equals(subKeyWord)) {
                    finalNormValues.addOrderEucNorm(subKeyWord, -eucNormDistance, number);
                }
                tempFinalValue = 0;
            }
        } else {
            System.out.println("Cannot compute TopJ similarity to " + word);
        }
        finalNormValues.printEucNorm(number);
    }

    void kMeanClustering(int kMean, int iter) {
        ArrayList means = new ArrayList();
        Random rand = new Random();
        double min = Double.MAX_VALUE;
        int clusterNum;
        double temp;
        double value = 0;

        for (int i = 0; i < kMean; i++) {
            means.add(rand.nextInt(kMean + 1));
        }
        for (int i = 0; i < iter; i++) {

            for (int j = 0; j < means.size(); j++) {
                //some math needs to find value using computeEucDistance
                temp = Math.abs((double) means.get(i) - value);
                if (temp < min) {
                    min = temp;
                    clusterNum = i;
                }
                //add word to the cluster with that number 
            }
        }
    }
    public ArrayList<String> findTopJOfCluster(vector mean, Map<String, Double> cluster, int j)
    {
        ArrayList<Pair<String, Double>> similarityRanking = new ArrayList<>();
        for (Map.Entry<String,Double> entry: cluster.entrySet())
        {
            vector compVec = new vector(vectorSet, entry.getKey());            
            Pair<String,Double> similarityPair = new Pair<>(entry.getKey(), mean.getList().getNodeCos(compVec.hashCode()));
            similarityRanking.add(similarityPair);
//            final Comparator<Pair<String, Double>> c = reverseOrder(comparing(Pair::getValue));
//               // Sort the values
//            Collections.sort(similarityRanking, c);
        }
        ArrayList<String> topJSimilarInCluster = new ArrayList<>();
        similarityRanking.remove(mean.word);
        for (int i=1; i<=j; i++)
        {
            if (i<cluster.size()) // Avoid indexing error - don't add a word if there are not more words in the cluster
                topJSimilarInCluster.add(similarityRanking.get(i).getKey());
//            }
        }
        return topJSimilarInCluster;
    }
}
