import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("/view/startForm.fxml"));
            stage.setTitle("Welcome Form");
            stage.centerOnScreen();
            stage.setScene(new Scene(root));
            stage.show();
    }
}
