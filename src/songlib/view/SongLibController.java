package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import songlib.model.Song;

public class SongLibController {
    @FXML ListView<Song> listView;
    @FXML TextField songNameField;
    @FXML TextField artistNameField;
    @FXML TextField albumField;
    @FXML TextField yearField;

    private ObservableList<Song> obsList = FXCollections.observableArrayList();

    public void start(Stage mainStage){
        obsList = FXCollections.observableArrayList();
        for(int i = 0; i < 10; i++){
            obsList.add(new Song("song" + String.valueOf(i), "singer" + String.valueOf(i)));
        }
        obsList.get(0).setAlbum("first");
        obsList.get(1).setYear(1999);
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
        listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal)-> showItem(mainStage));
        if(obsList.size()!=0) {
            listView.getSelectionModel().select(0);
        }
    }
    public void showItem(Stage mainStage){
        int index = listView.getSelectionModel().getSelectedIndex();
        Song selectedSong = obsList.get(index);
        songNameField.setText(selectedSong.getSongName());
        artistNameField.setText(selectedSong.getArtistName());
        albumField.setText(selectedSong.getAlbum());
        yearField.setText(selectedSong.getYear());
    }
}
