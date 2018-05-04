/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Adam
 */
public class kCluster {

    private Map<String, Map<String, Integer>> vector;
    private Set<Set<String>> words;
    public kCluster(Map<String, Map<String, Integer>> vector, Set<Set<String>> words){
        this.vector = vector;
        this.words = words;
    }

    private String calculateMean
}
