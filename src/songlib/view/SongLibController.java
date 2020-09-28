package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import songlib.resource.Song;

public class SongLibController {
    @FXML
    ListView<Song> listView;

    private ObservableList<Song> obsList = FXCollections.observableArrayList();

    public void start(Stage mainStage){
        obsList = FXCollections.observableArrayList();
        for(int i = 0; i < 10; i++){
            obsList.add(new Song("song" + String.valueOf(i), "singer" + String.valueOf(i)));
        }
        listView.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){
            @Override
            public ListCell<Song> call(ListView<Song> p){
                ListCell<Song> cell = new ListCell<>(){
                    protected void updateItem(Song s, boolean bln){
                        super.updateItem(s, bln);
                        if(s != null){
                            setText(s.toString());
                        }
                    }
                };
                return cell;
            }
        });
        listView.setItems(obsList);
        if(obsList.size()!=0) {
            listView.getSelectionModel().select(0);
        }
    }
}
