import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class RailsFenceSceneController {


    @FXML
    private TextArea plainTextArea;

    @FXML
    void encrypt(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String plainText = plainTextArea.getText().trim();
        if (plainText.length() == 0) {
            alert.setContentText("The plain text area is empty!");
            alert.show();
            return;
        }
        for (char c : plainText.toCharArray())
            if(!Character.isUpperCase(c)){
                alert.setContentText("Your plain text can only contain upper case Alphabetic from A to Z!");
                alert.show();
                return;
            }
    }
}
