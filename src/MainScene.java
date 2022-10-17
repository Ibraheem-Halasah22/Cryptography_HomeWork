import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainScene {
    @FXML
    void goToRailsFence(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                stage.setScene(new Scene(
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rails_fence_scene.fxml")))
        ));
        stage.show();
    }

    @FXML
    void goToLeastSimple(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("least_simple_scene.fxml")))
        ));
        stage.show();
    }
}
