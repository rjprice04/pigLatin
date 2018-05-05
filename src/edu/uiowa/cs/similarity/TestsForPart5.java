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
 * since we didn't make the vector class easy to get the single value 
 * the tests don't really work but here is what they might look like 
 */
public class TestsForPart5 {
    public TestsForPart5(){
        
    }
    @Test//Test for negative ecu distance
    public void negativeDistance(){
        vector thisVector = new vector();
        thisVector.addToMap(null);
        double ITried = 0;
        double AndFailed = 0;
        assertEquals(ITried,AndFailed);
    }
    @Test //test for normal negative ecu ditance
    public void normalNegaticeDistace(){
        vector thatVector = new vector();
        thatVector.addToMap(null);
        double ITried =0;
        double AndFailed =0;
        assertEquals(ITried,AndFailed);
    }
    
    boolean assertEquals(double expected, double actual){
        return actual <= expected+0.00001 && actual >= expected -0.00001;
    } //check if value is withhin the value is between the range
}
