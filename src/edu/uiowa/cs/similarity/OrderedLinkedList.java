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

    public OrderedLinkedList() {
        header = new ListNode(null, 0, 0);
        wordsInList = new HashSet();
    }

    void addOrder(String word, double value, int length) {
        ListNode current = header;
        ListNode temp;
        ListNode newNode = new ListNode(word, value, 0);
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
        }
    }

    void addOrderSmaller(String word, int length, double value) {
        ListNode current = header;
        ListNode temp;
        ListNode newNode = new ListNode(word, 0, value);
        int count = 0;
        if (!wordsInList.contains(word)) {
            while (current.next != null && current.getEucValue() <= value && count < length) {
                current = current.next;
                count++;
            }
            temp = current.next;
            current.next = newNode;
            newNode.next = temp;
            wordsInList.add(word);
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
            current = current.next;
            i++;
        }
        System.out.print(" ]");
        System.out.println();
    }

    private class ListNode {

        private double cosValue;
        private double eucValue;
        private String word;
        private ListNode next;

        public ListNode(String w, double d, double e) {
            cosValue = d;
            word = w;
            eucValue = e;
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
