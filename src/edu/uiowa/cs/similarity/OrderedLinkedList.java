/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.LinkedList;

public class OrderedLinkedList extends LinkedList {

    private ListNode header;

    public OrderedLinkedList() {
        header = new ListNode(null, 0);
    }

    void addOrder(String word, double value) {
        ListNode current = header;
        ListNode temp = header;
        ListNode newNode = new ListNode(word, value);
        while (current.next != null && current.getCosValue() >= value) {
            current = current.next;
        }
        temp = current.next;
        current.next = newNode;
        newNode.next = temp;
    }

    public void print(int num) {
        ListNode current = header.next;
        int i=0;
        System.out.print("[");
        while(current.next!=null&&i < num){// (int i = 0; i < num; i++) {
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
