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

    private int size;
    private ListNode header;
    private Set<String> wordsInList;

    public OrderedLinkedList() {
        header = new ListNode(null, 0, 0, 0);
        wordsInList = new HashSet();
        size = 0;
    }
    public double getNodeEucDistance(int index){
        ListNode current = header;
        int count = 0;
        while(current != null){
            if(index == count){
                return current.getEucValue();
            }
        }
        return 0;
    }
    public double getNodeCos(int index){
        ListNode current = header;
        int count = 0;
        while(current != null){
            if(index == count){
                return current.getCosValue();
            }
        }
        return 0;
    }
    
    void addOrder(String word, double value, int length) {
        ListNode current = header;
        ListNode temp;
        ListNode newNode = new ListNode(word, value, 0, 0);
        int count = 0;
        if (!wordsInList.contains(word)) {
            while (current.next != null && current.getCosValue() >= value && count < length) {
                current = current.next;
                count++;
            }
            temp = current.next;
            current.next = newNode;
            newNode.next = temp;
            wordsInList.add(word);
            size++;
        }
    }

    void addOrderEuc(String word, double value, int length) {
        ListNode current = header;
        ListNode temp;
        ListNode newNode = new ListNode(word, 0, value, 0);
        int count = 0;
        if (!wordsInList.contains(word)) {
            while (current.next != null && value <= current.next.getEucValue() && count < length) {
                current = current.next;
                count++;
            }
            temp = current.next;
            current.next = newNode;
            newNode.next = temp;
            wordsInList.add(word);
            size++;
        }
    }

    void addOrderEucNorm(String word, double value, int length) {
        ListNode current = header;
        ListNode temp;
        ListNode newNode = new ListNode(word, 0, 0, value);
        newNode.setEucNormValue(value);
        int count = 0;
        if (!wordsInList.contains(word)) {
            while (current.next != null && value <= current.next.getEucNormValue() && count < length) {
                current = current.next;
                count++;
            }
            temp = current.next;
            current.setNext(newNode);
            newNode.next = temp;
            wordsInList.add(word);
            size++;
        }
    }

    public void printCos(int num) {
        if (header.next == null) {
            return;
        }
        ListNode current = header.next;
        int i = 0;
        System.out.print("[");
        while (current.next != null && i < num) {
            System.out.print(" Pair{" + current.getWord() + ", " + current.getCosValue() + "}");
            current = current.next;
            i++;
        }
        System.out.print(" ]");
        System.out.println();
    }

    public void printEuc(int num) {
        if (header.next == null) {
            return;
        }
        ListNode current = header.next;
        int i = 0;
        System.out.print("[");
        while (current.next != null && i < num) {
            System.out.print(" Pair{" + current.getWord() + ", " + current.getEucValue() + "}");
            i++;
            current = current.next;
        }
        System.out.print(" ]");
        System.out.println();
    }

    public void printEucNorm(int num) {
        if (header.next == null) {
            return;
        }
        ListNode current = header.next;
        int i = 0;
        System.out.print("[");
        while (current.next != null && i < num) {
            System.out.print(" Pair{" + current.getWord() + ", " + current.getEucNormValue() + "}");
            current = current.next;
            i++;
        }
        System.out.print(" ]");
        System.out.println();
    }

    private class ListNode {

        private double cosValue;
        private double eucValue;
        private double eucNorm;
        private String word;
        private ListNode next;

        public ListNode(String w, double cos, double euc, double norm) {
            cosValue = cos;
            word = w;
            eucValue = euc;
            eucNorm = norm;
        }

        public double getCosValue() {
            return this.cosValue;
        }

        public void setCosValue(double data) {
            this.cosValue = data;
        }

        public double getEucValue() {
            return this.eucValue;
        }

        public void setEucValue(double data) {
            this.eucValue = data;
        }

        public double getEucNormValue() {
            return this.eucNorm;
        }

        public void setEucNormValue(double data) {
            this.eucNorm = data;
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
