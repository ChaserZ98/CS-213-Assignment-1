package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import songlib.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class SongLibController {
    @FXML ListView<Song> listView;
    @FXML Text songNameText;
    @FXML Text artistNameText;
    @FXML Text albumText;
    @FXML Text yearText;
    @FXML TextField songNameTextField;
    @FXML TextField artistNameTextField;
    @FXML TextField albumTextField;
    @FXML TextField yearTextField;
    @FXML Button addButton;
    @FXML Button editButton;
    @FXML Button deleteButton;

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
                        else{
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        Collections.sort(obsList);
        listView.setItems(obsList);
        listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal)-> showDetails(mainStage));
        if(obsList.size()!=0) {
            listView.getSelectionModel().select(0);
        }
    }
    public void showDetails(Stage mainStage){
        int index = listView.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            Song selectedSong = obsList.get(index);
            songNameText.setText(selectedSong.getSongName());
            songNameTextField.setText(selectedSong.getSongName());
            artistNameText.setText(selectedSong.getArtistName());
            artistNameTextField.setText(selectedSong.getArtistName());
            albumText.setText(selectedSong.getAlbum());
            albumTextField.setText(selectedSong.getAlbum());
            yearText.setText(selectedSong.getYear());
            yearTextField.setText(selectedSong.getYear());
        }
        else{
            songNameText.setText("");
            songNameTextField.setText("");
            artistNameText.setText("");
            artistNameTextField.setText("");
            albumText.setText("");
            albumTextField.setText("");
            yearText.setText("");
            yearTextField.setText("");
        }
    }

    public void addSong(ActionEvent e){

    }
    public void editSong(ActionEvent e){

    }
    public void deleteSong(ActionEvent e){
        int index = listView.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            Song song = obsList.get(index);
            String songDetails = "Song Name: \t" + song.getSongName() + "\nArtist Name: \t" + song.getArtistName() + "\nAlbum: \t" + song.getAlbum() + "\nYear: \t" + song.getYear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, songDetails, ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Delete");
            alert.setHeaderText("Are you sure you want to delete this song?");
            alert.initOwner(((Node)e.getSource()).getScene().getWindow());
            Optional<ButtonType> option =  alert.showAndWait();
            if(option.get() == ButtonType.YES){
                obsList.remove(index);
                if(index < obsList.size()){
                    listView.getSelectionModel().clearSelection(index);
                    listView.getSelectionModel().select(index);
                }
                else if(index - 1 >=0){
                    listView.getSelectionModel().select(index - 1);
                }
                else{
                    listView.getSelectionModel().clearSelection(index);
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is no song selected.", ButtonType.OK);
            alert.setTitle("Error!");
            alert.setHeaderText("Illegal Manipulation!");
            alert.initOwner(((Node)e.getSource()).getScene().getWindow());
            alert.showAndWait();
        }
    }
}
