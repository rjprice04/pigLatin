/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author ryanprice
 */
public class TestsForPart5 {
    public TestsForPart5(){
        
    }
    @Test
    public void negativeDistance(){
        vector thisVector = new vector();
        thisVector.addToMap(null);
        double ITried = 0;
        double AndFailed = 0;
        assertEquals(ITried,AndFailed);
    }
    public void normalNegaticeDistace(){
        vector thatVector = new vector();
        double ITried =0;
        double AndFailed =0;
        thatVector.addToMap(null);
        assertEquals(ITried,AndFailed);
    }
}
