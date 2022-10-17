import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class RailsFenceSceneController {

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TextArea decCipherTextArea;

    @FXML
    private PasswordField decKey;

    @FXML
    private TextArea decPlainTextArea;

    @FXML
    private TextArea encCipherTextArea;

    @FXML
    private PasswordField encKey;

    @FXML
    private TextArea encPlainTextArea;

    @FXML
    void bruteForce() {

    }

    @FXML
    void encrypt() {

        String plainText = encPlainTextArea.getText().replaceAll(" ", "x");
        int key;
        try {
            key = Integer.parseInt(encKey.getText().trim());
            if (key < 1 || key > 10) {
                alert.setContentText("Key must be from 1 to 10!");
                alert.show();
                return;
            }
        } catch (Exception e) {
            alert.setContentText("Please enter valid numeric key!");
            alert.show();
            return;
        }
        if (plainText.length() == 0) {
            alert.setContentText("The plain text area is empty!");
            alert.show();
            return;
        }
        ArrayList<Character>[] rails = new ArrayList[key];
        for (int i = 0; i < key; i++) {
            rails[i] = new ArrayList<>();
        }
        int markerAddress = 0;
        boolean reverseFlag = false;
        for (char c : plainText.toCharArray()) {
            if (!Character.isUpperCase(c) && c != 'x') {
                alert.setContentText("Your plain text can only contain upper case Alphabetic from A to Z!");
                alert.show();
                return;
            } else {
                rails[markerAddress].add(c);
            }
            if (key != 1) {
                if (reverseFlag) {
                    markerAddress--;
                    if (markerAddress < 0) {
                        markerAddress = 1;
                        reverseFlag = false;
                    }
                } else {
                    markerAddress++;
                    if (markerAddress == key) {
                        markerAddress -= 2;
                        reverseFlag = true;
                    }
                }
            }
        }
        String cipher = "";
        for (ArrayList<Character> a : rails) {
            for (Character character : a) {
                cipher += character;

            }
        }
        encCipherTextArea.setText(cipher);

    }

    @FXML
    void copyCipher() {
        decCipherTextArea.setText(encCipherTextArea.getText());
    }


    @FXML
    void decrypt() {
        String cipher = decCipherTextArea.getText().trim();
        for(char c : cipher.toCharArray()){
            if (!Character.isUpperCase(c) && c != 'x') {
                alert.setContentText("Your cipher text can only contain upper case Alphabetic from A to Z!");
                alert.show();
                return;
            }
        }
        int key;
        try {
            key = Integer.parseInt(decKey.getText().trim());
            if (key < 1 || key > 10) {
                alert.setContentText("Key must be from 1 to 10!");
                alert.show();
                return;
            }
        } catch (Exception e) {
            alert.setContentText("Please enter valid numeric key!");
            alert.show();
            return;
        }
        String plainText = decRails(cipher, key);
        System.out.println("plain is " + plainText);
        if(plainText == null) return;
        decPlainTextArea.setText(plainText.replaceAll("x", " "));


    }
    private String decRails(String cipher, int key){
        String plainText = "";
        if(cipher.length() == 0){
            alert.setContentText("The cipher text area is empty!");
            alert.show();
            return null;
        }
        if(key == 1) return cipher;
        char [] plainTextArray = cipher.toCharArray();
        char [][] rails = new char[key][cipher.length()];
        int markerAddress = 0;
        for(int i = 0; i < cipher.length(); i += 2 * (key - 1), markerAddress++){
            rails [0][i] = plainTextArray[markerAddress];
        }
        for(int i = 1; i < key - 1; i++){
            int oldMarker = markerAddress;
            for(int j = i; j < cipher.length(); j += (2 * (key - 1)), markerAddress += 2){
                rails [i][j] = plainTextArray[markerAddress];

            }
            int notSoOldMarker = markerAddress;
            markerAddress = oldMarker + 1;

            for(int j = 2 * (key - 1) - i; j < cipher.length(); j += 2 * (key - 1), markerAddress += 2){
                rails [i][j] = plainTextArray[markerAddress];
            }
            markerAddress = Math.max(notSoOldMarker, markerAddress) - 1;
        }

        for(int i = key - 1; i < cipher.length(); i += 2 * (key - 1), markerAddress++){
            rails [key - 1][i] = plainTextArray[markerAddress];
        }
        int railAddress = 0;
        boolean reverseDirection = false;
        for(int i = 0; i < cipher.length(); i++){
            plainText += rails[railAddress][i];
            if(reverseDirection){
                railAddress--;
                if(railAddress < 0) {
                    railAddress = 1;
                    reverseDirection = false;
                }
            }else {
                railAddress ++;
                if(railAddress == key){
                    reverseDirection = true;
                    railAddress = key-2;
                }
            }
        }


        return plainText;
    }
}
