package songlib.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class SongLib extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/song/view/songlib.fxml"));
    }
    public static void main(String[] args){
        launch(args);
    }
}
