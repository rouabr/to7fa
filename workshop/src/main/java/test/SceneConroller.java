

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import test.FxMain;

import java.io.IOException;
import java.util.Objects;

public class SceneConroller {

    public  SceneConroller(AnchorPane curentAnchorPane, String fxml) throws IOException {
        AnchorPane nextAnchorpane= FXMLLoader.load(Objects.requireNonNull(FxMain.class.getResource(fxml)));
        curentAnchorPane.getChildren().removeAll();
        curentAnchorPane.getChildren().setAll(nextAnchorpane);

    }


}