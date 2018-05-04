/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import edu.uiowa.cs.similarity.vector;
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
        eucList.add(temp.getList());
    }
    private double centroidFinder(HashMap<String,Double> clusterMap){
        
        if (!clusterMap.isEmpty()){
               double soFar = 0;
            Iterator<HashMap.Entry<String,Double>> it = clusterMap.entrySet().iterator();

            while(it.hasNext()){

                soFar += it.next().getValue();
            }
            

            return soFar/clusterMap.size();
            
           
        }
        else{
            return 0;
        }
    }

    private String calculateMean(Map<String, Double> clusters, double centroid, String word) {
        String output = word;
        double newCentroid = Double.MAX_VALUE;
        Map.Entry<String, Double> entries = null;
        Set<Entry<String, Double>> clusterSet = clusters.entrySet();
        Iterator iter = clusterSet.iterator();
        while (iter.hasNext()) {
            entries = (Entry<String, Double>) iter.next();
            if ((Math.abs(centroid - entries.getValue())) < Math.abs(centroid - newCentroid)) {
                output = entries.getKey();
                newCentroid = entries.getValue();
            }
        }
        return output;
    }
    
    public ArrayList<Pair<vector, Map<String, Double>>> findClusters(int k, int iters)
    {
        ArrayList<Pair<vector, Map<String, Double>>> toReturn = new ArrayList<>();
    
         Random generator = new Random();
        Object[] keyWords = vector.keySet().toArray();
        vector[] means = new vector[k];
       
        // Set the initial Vectors in the means
        for (int i=0; i<k; i++)
        {
            String baseWord = (String) keyWords[generator.nextInt(keyWords.length)];
            means[i] = new vector(words, baseWord);
        }
        
        Map<String, Map<String, Double>> vectorsCopy = vector;
        
        // improve the means elements iters times
        for (int iterCount=1; iterCount<=iters; iterCount++)
        {
            double sumOfDistances = 0;
            double numberOfPoints = 0;
            
            //clusters = [[this is a hashmap of a word and its closest euc dist], [], [], ..., []] // k of these
            ArrayList<HashMap<String, Double>> clusters = new ArrayList<>();
            for (int i=0; i<k; i++){
                clusters.add(new HashMap<>());
            }
            
            // Go through every point and put it in one of the clusters
            for (Map.Entry<String, Map<String, Double>> pointBaseEntry: vector.entrySet())
            {
//                System.out.println(pointBaseEntry.getValue());
                // Get the hashmap corresponding to the current word
                HashMap<String,Integer> currVecMap = new HashMap<>();
                currVecMap=(HashMap) pointBaseEntry.getValue();
                int closestMeansIndex = 0;
                
                vector currentMeansVector = means[0];
                OrderedLinkedList tempNode = eucList;
                double closestEucDist = tempNode.getNodeEucDistance(0);
                for (int meansIndex=1; meansIndex<clusters.size(); meansIndex++)
                {
                   // double compDist = means[meansIndex].negEuclideanDist(currVecMap);
                    double compDist = means[meansIndex].getList().getNodeEucDistance(meansIndex);
                    if (Math.abs(compDist)<Math.abs(closestEucDist))
                    {
                        // If the distance to the current mean is less than the previous closest mean, update the closest mean
                        closestMeansIndex = meansIndex;
                        closestEucDist = compDist;
                    }
                    
                }
                
                // Don't add the mean to the cluster
//                if (!means[closestMeansIndex].getList().getNodeEucDistance(0).getWord().equals(pointBaseEntry.getKey()))
//                {
                    Map<String, Double> cluster = clusters.get(closestMeansIndex);
                    cluster.put(pointBaseEntry.getKey(), closestEucDist);
                    sumOfDistances += closestEucDist;
                    numberOfPoints++;
//                }
            }
            System.out.println("Iteration " + iterCount + ":");
            System.out.println("Average Euclidean Distance: " + sumOfDistances/numberOfPoints);
            for (int clusterIndex=0; clusterIndex<clusters.size(); clusterIndex++)
            {
//                System.out.println();
                
                System.out.println("Cluster " + (clusterIndex+1) + " with mean node " + means[clusterIndex].getWord());
            
                for (Map.Entry<String, Double> entry: clusters.get(clusterIndex).entrySet())
                {
                    System.out.print("\t" + entry.getKey() + ": " + entry.getValue());
                }
                System.out.println();
                
            }
            System.out.println();
            System.out.println();
//            String str = "";
            for(int i = 0; i < means.length; i++){
                Map<String, Double> clusterCopy = new HashMap<String, Double>();
                clusterCopy=(Map) clusters.get(i).clone();
                double centroid = centroidFinder(clusters.get(i));
                String str = calculateNewMeans(clusters.get(i),centroid, means[i].getWord());
                if(!str.equals(means[i].getWord())){                    
                    vector vec = new vector(words,str);
                    means[i] = vec;
                    
                }    
                if (clusterCopy.isEmpty() || clusterCopy.size() == 1)
                {
                    // If a cluster is empty, it needs a better mean.  Randomly select another mean.
                    Random rand2 = new Random();
                    // Make an array of words we have to choose from
                    Object[] keyWordsSet = vector.keySet().toArray();
                    String baseWordFill = (String) keyWordsSet[rand2.nextInt(keyWordsSet.length)];
                    // Fill means with k number of random Vectors
                    means[i] = new vector(words, baseWordFill);
                }
                else 
                {
//                    System.out.println("not changing the base word.");
                    means[i] = means[i];
                }
            }
        }
    }

}
