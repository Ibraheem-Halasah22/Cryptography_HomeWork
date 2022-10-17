import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LeastSimpleScene implements Initializable {
    private void shuffleReferenceArray() {
        List<Character> intList = Arrays.asList(referenceArray);
        Collections.shuffle(intList);
        intList.toArray(referenceArray);
    }

    private void setReferenceTextAreaText() {
        String referenceText = "Reference Array\n";
        int i = 0;
        for (; i < 13; i++) {
            referenceText += ((char) ('A' + i) + "         ");
        }
        referenceText += "\n";
        i = 0;
        for (; i < 13; i++) {
            referenceText += (referenceArray[i] + "         ");
        }
        referenceText += "\n";
        referenceText += "\n";
        for (; i < 26; i++) {
            referenceText += ((char) ('A' + i) + "         ");
        }
        referenceText += "\n";
        i = 13;
        for (; i < 26; i++) {
            referenceText += (referenceArray[i] + "         ");
        }

        referenceTextArea.setText(referenceText);
    }

    private final Character[] referenceArray = new Character[26];

    @FXML
    private TextArea cipherTextArea;

    @FXML
    private TextArea expectationsArea;

    @FXML
    private BarChart<?, ?> lettersChart;

    @FXML
    private TextArea plainTextArea;

    @FXML
    private TextArea referenceTextArea;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void encrypt(ActionEvent event) {
        char[] strArray = plainTextArea.getText().toCharArray();
        if (strArray.length == 0) {
            alert.setContentText("The plain text area is empty!");
            alert.show();
            return;
        }
        for (int i = 0; i < strArray.length; i++)
            if (Character.isUpperCase(strArray[i]))
                strArray[i] = referenceArray[(strArray[i]) - 'A'];
            else if (Character.isSpace(strArray[i])) ;
            else {
                alert.setContentText("Your cipher text can only contain upper case Alphabetic from A to Z!");
                alert.show();
                return;
            }
        cipherTextArea.setText(new String(strArray));
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(Objects.requireNonNull(FXMLLoader.load(getClass().getResource("main_scene.fxml")))));
        stage.show();

    }

    @FXML
    void staticAnalysis(ActionEvent event) {
        CharacterCounter[] characterCounters = new CharacterCounter[26];
        for (int i = 0; i < 26; i++) {
            characterCounters[i] = new CharacterCounter();
            characterCounters[i].x = (char) ('A' + i);
        }
        String cipher = cipherTextArea.getText().trim();
        if (cipher.length() == 0) {
            alert.setContentText("The plain text area is empty!");
            alert.show();
            return;
        }
        for (char c : cipher.toCharArray())
            if (Character.isUpperCase(c))
                characterCounters[(c) - 'A'].count++;
            else if (Character.isSpace(c)) ;
            else {
                alert.setContentText("Your cipher text can only contain upper case Alphabetic from A to Z!");
                alert.show();
                return;
            }
        Arrays.sort(characterCounters);
        XYChart.Series set = new XYChart.Series();
        for (CharacterCounter c : characterCounters) {
            set.getData().add(new XYChart.Data<>(Character.toString(c.x), c.count));
        }
        lettersChart.getData().addAll(set);

        String expectationsText = characterCounters[0].x + " seems to represent E\n";
        int i = 1;
        for (; i < 4; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent T, A, O\n";

        for (; i < 9; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent I, N, S, R, H \n";

        for (; i < 11; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent D, L\n";

        for (; i < 15; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent U, C, M, F\n";

        for (; i < 18; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent Y, W, G\n";

        for (; i < 20; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent P, B\n";

        for (; i < 22; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent V, K\n";

        for (; i < 26; i++) expectationsText += characterCounters[i].x + ", ";
        expectationsText += "seems to represent X, Q, J, Z\n";

        expectationsArea.setText(expectationsText);


    }

    @FXML
    void updateReference(ActionEvent event) {
        shuffleReferenceArray();
        setReferenceTextAreaText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 26; i++) referenceArray[i] = (char) ('A' + i);
        shuffleReferenceArray();
        setReferenceTextAreaText();
    }

    class CharacterCounter implements Comparable<CharacterCounter> {
        char x;
        int count;

        @Override
        public int compareTo(CharacterCounter characterCounter) {
            return characterCounter.count - this.count;
        }
    }

}

