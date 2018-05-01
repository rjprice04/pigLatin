/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class OrderedLinkedList extends LinkedList {

    private ListNode header;
    private Set wordsInList;
    public OrderedLinkedList( ) {
        header = new ListNode(null, 0);
        wordsInList = new HashSet();
    }

    void addOrder(String word, double value, int length) {
        ListNode current = header;
        ListNode temp;
        ListNode newNode = new ListNode(word, value);
        int count=0;
        if(!wordsInList.contains(word)){
            while (current.next != null && current.getCosValue() >= value && count<length) {
                current = current.next;
                count++;
            }
            temp = current.next;
            current.next = newNode;
            newNode.next = temp;
            wordsInList.add(word);
        }
    }

    public void print(int num) {
        if(header.next == null){
            return;
        }
        ListNode current = header.next;
        int i = 0;
        System.out.print("[");
        while (current.next != null && i < num) {
            System.out.print("Pair{" + current.getWord() + ", " + current.getCosValue() + "}");
            current = current.next;
            i++;
        }
        System.out.print("]");
        System.out.println();
    }

    private class ListNode {

        private double cosValue;
        private String word;
        private ListNode next;

        public ListNode(String w, double d) {
            cosValue = d;
            word = w;
        }

        public double getCosValue() {
            return this.cosValue;
        }

        public void setCosValue(double data) {
            this.cosValue = data;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public ListNode getNext() {
            return next;
        }

        public String getWord() {
            return this.word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
