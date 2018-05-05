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
import java.util.Random;
import java.util.Set;
import javafx.util.Pair;

/**
 *
 * @author Adam
 */
public class kCluster {

    private OrderedLinkedList eucList;
    vector temp;
    private Map<String, Map<String, Double>> vector;
    private Set<Set<String>> words;

    public kCluster(Map<String, Map<String, Double>> vector, Set<Set<String>> words) {
        this.vector = vector;
        this.words = words;
        // eucList.add(temp.getList());
    }

    //Did not have time to finish this method or this part.
    public ArrayList<Pair<vector, Map<String, Double>>> computeKMean(int num, int times, vector aVector) {
        Random generator = new Random();
        Object[] keyWords = vector.keySet().toArray();
        vector[] averages = new vector[num];
        ArrayList<Pair<vector, Map<String, Double>>> output = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            String baseWord = (String) keyWords[generator.nextInt(keyWords.length)];
            averages[i] = new vector(words, baseWord);
        }
        for (int count = 1; count < times; count++) {
            ArrayList<HashMap<String, Double>> clusters = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                clusters.add(new HashMap<>());
            }
            for (Map.Entry<String, Map<String, Double>> pointBaseEntry : vector.entrySet()) {
                int closeIndex = 0;
                Set<String> allStrings;
                vector currentMeansVector = averages[0];
                allStrings = aVector.vectorMap.keySet();
                Collection<Map<String, Double>> entrySet = averages[0].vectorMap.values();
                Iterator eucIter = entrySet.iterator();
                int index = 1;
                double eucDis = 0;
                while (index < clusters.size() && eucIter.hasNext()) {
                    double compDist = (Double) eucIter.next();
                    if (Math.abs(compDist) < Math.abs(eucDis)) {
                        closeIndex = index;
                        eucDis = compDist;
                    }
                }
                if (!averages[closeIndex].word.equals(pointBaseEntry.getKey())) {
                    HashMap<String, Double> cluster = clusters.get(closeIndex);
                    cluster.put(pointBaseEntry.getKey(), eucDis);
                }
            }
        }
        return output;
    }
    //Did not finish this method or this part

    public void printClusters(int num, int times) {
        for (int iterCount = 1; iterCount < times; iterCount++) {
            ArrayList<HashMap<String, Double>> clusters = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                clusters.add(new HashMap<>());
            }
            Map.Entry<String, Map<String, Double>> entry = null;
            for (int cIndex = 0; cIndex < clusters.size(); cIndex++) {
                System.out.println("Cluster " + (cIndex + 1));
                for (Map.Entry<String, Double> Clusterentry : clusters.get(cIndex).entrySet()) {
                    System.out.print(entry.getKey() + " " + entry.getValue());
                }
                System.out.println();
            }
        }
    }
}
