package edu.uiowa.cs.similarity;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	private static void printMenu() {
		System.out.println("Supported commands:");
		System.out.println("help - Print the supported commands");
		System.out.println("quit - Quit this program");
                System.out.println("index - Name of the file. Creates the index of a file");
	}

    public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("> ");
			String command = input.readLine();
			if (command.equals("help") || command.equals("h")) {
				printMenu();
			} else if (command.equals("quit")) {
				System.exit(0);
			} 
                        //do
                        else if(command.equals("index")){
                            ArrayList<String> sentences = new ArrayList();
                            System.out.println("file name");
                            String fileName = input.readLine();
                            //C:\Users\ryanprice\NetBeansProjects\similarity\cleanup_test.txt
                            //I think this works it just can't find the file 
                            Scanner sc = new Scanner(new File(fileName));
                            while(sc.hasNext()){
                                sentences.add(sc.next());
                            }
                            for(int i=0;i<sentences.size();i++){
                                System.out.println(sentences.get(i));
                            }
                            System.out.println(sentences.size());
                            
                        }
                        else {
				System.err.println("Unrecognized command");
			}
		}
    }
}
