import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;

public class RailsFenceSceneController {


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
    void bruteForce(ActionEvent event) {

    }

    @FXML
    void encrypt(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String plainText = encPlainTextArea.getText().trim();
        int key = 0;
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
            if (Character.isSpace(c)) {
                rails[markerAddress].add('x');
            } else if (!Character.isUpperCase(c)) {
                alert.setContentText("Your plain text can only contain upper case Alphabetic from A to Z!");
                alert.show();
                return;
            } else {
                rails[markerAddress].add(c);
            }
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
        String cipher = "";
        for (ArrayList<Character> a : rails) {
            for (Character character : a) {
                cipher += character;

            }
            System.out.println(cipher);
        }
        encCipherTextArea.setText(cipher);

    }

    @FXML
    void copyCipher() {
        decCipherTextArea.setText(encCipherTextArea.getText());
    }

    @FXML
    void decrypt(ActionEvent event) {

    }
}
