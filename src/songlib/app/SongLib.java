package songlib.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SongLib extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Fahrenheit-Celsius");
        primaryStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
        GridPane root = (GridPane)loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
