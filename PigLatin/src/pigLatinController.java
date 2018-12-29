import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.HashSet;
import java.util.Scanner;

public class pigLatinController {

    @FXML
    private TextArea enterNext;

    @FXML
    private Button convertButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextArea outputText;

    private HashSet vowels;

    @FXML
    void convertTextButton(ActionEvent event) {
        vowels = new HashSet();
        char[] vowelArray = {'a','e','i','o','u','y'};
        for (int i = 0; i < vowelArray.length ; i++) {
            vowels.add(vowelArray[i]);
        }
        String inputTxt  = enterNext.getText();
        String outoutTxt = "";
        String subString = "";
        char[] currentWord;
        int vowelSpot = -1;
        Scanner scanner = new Scanner(inputTxt);
        scanner.useDelimiter(" ");
        while(scanner.hasNext()){
            currentWord = scanner.next().toCharArray();
            for (int i = 0; i <currentWord.length; i++) {
                if(vowels.contains(currentWord[i]) && vowelSpot == -1){
                    vowelSpot = i;
                }

            }
            if(vowelSpot != 0) {
                for (int i = vowelSpot; i < currentWord.length; i++) {
                    subString += currentWord[i];
                }
                for(int i = 0; i<vowelSpot; i++){
                    subString += currentWord[i];
                }
                subString += "ay";
            }
            else{
                for (int i = 0; i < currentWord.length; i++) {
                    subString += currentWord[i];
                }
                subString+= "way";
            }
            subString+= " ";
            outoutTxt += subString;
            vowelSpot = -1;
            subString = "";
        }



        outputText.setText(outoutTxt);
    }

    @FXML
    void clearOutput(ActionEvent event) {
        enterNext.setText("");
        outputText.setText("");
    }

}

