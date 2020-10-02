//Feiyu Zheng
//Ying Yu
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

import java.io.*;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String songDataFilePath = "src/songlib/data/songListData.txt";
    private File songDataFile = new File(songDataFilePath);

    public void start(Stage mainStage){
        obsList = FXCollections.observableArrayList();
        songDataFile = new File(songDataFilePath);
        readData(obsList);
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
    public void readData(ObservableList<Song> obsList){
        if(songDataFile.isFile() && songDataFile.exists()){
            try{
                FileInputStream fileInputStream = new FileInputStream(songDataFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String text = "";
                String regex = "([^\\\\]+)\\\\([^\\\\]+)\\\\([^\\\\]*)\\\\([^\\\\]*)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(text);
                while((text = bufferedReader.readLine()) != null){
                    matcher = pattern.matcher(text);
                    if(matcher.find()){
                        String songName = matcher.group(1);
                        String artistName = matcher.group(2);
                        String album = matcher.group(3);
                        int year = matcher.group(4).length()==0? 0:Integer.parseInt(matcher.group(4));
                        obsList.add(new Song(songName, artistName, album, year));
                    }
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                songDataFile.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public boolean saveData(ObservableList<Song> obsList){
        FileOutputStream fileOutputStream = null;
        try{
            songDataFile.createNewFile();
            fileOutputStream = new FileOutputStream(songDataFile);
            for(int i = 0; i < obsList.size(); i++){
                Song outputSong = obsList.get(i);
                String outputText = outputSong.getSongName() + "\\" + outputSong.getArtistName() + "\\" + outputSong.getAlbum() + "\\" + outputSong.getYear() + "\n";
                fileOutputStream.write(outputText.getBytes());
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
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
        String songName = songNameTextField.getText();
        String artistName = artistNameTextField.getText();
        String album = albumTextField.getText();
        int year = convertToYearInt(yearTextField.getText());
        Song song = new Song(songName, artistName, album, year);
        String songDetails = "Entered Song:\n\tSong Name: \t" + song.getSongName() + "\n\tArtist Name: \t" + song.getArtistName() + "\n\tAlbum: \t" + song.getAlbum() + "\n\tYear: \t" + yearTextField.getText();
        //If song name or artist name is empty
        if(songNameTextField.getLength()==0 || artistNameTextField.getLength()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR, songDetails, ButtonType.OK);
            alert.setTitle("Error!");
            alert.setHeaderText("To add a song, you should at least enter a song name and an artist name!");
            alert.initOwner(((Node) e.getSource()).getScene().getWindow());
            alert.showAndWait();
        }
        else {
            //If the song already exists
            if (obsList.contains(song)) {
                int index = obsList.indexOf(song);
                Song existedSong = obsList.get(index);
                String existedSongDetails = "Existed Song:\n\tSong Name: \t" + existedSong.getSongName() + "\n\tArtist Name: \t" + existedSong.getArtistName() + "\n\tAlbum: \t" + existedSong.getAlbum() + "\n\tYear: \t" + existedSong.getYear();
                Alert alert = new Alert(Alert.AlertType.ERROR, songDetails + "\n" + existedSongDetails, ButtonType.OK);
                alert.setTitle("Error!");
                alert.setHeaderText("The song already exists!");
                alert.initOwner(((Node) e.getSource()).getScene().getWindow());
                alert.showAndWait();
            }
            //If the year of entered song is illegal
            else if(year == -1){
                Alert alert = new Alert(Alert.AlertType.ERROR, songDetails, ButtonType.OK);
                alert.setTitle("Error!");
                alert.setHeaderText("Please enter year as a positive Integer!");
                alert.initOwner(((Node) e.getSource()).getScene().getWindow());
                alert.showAndWait();
            }
            //The user makes a legal change
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, songDetails, ButtonType.YES, ButtonType.CANCEL);
                alert.setTitle("Add");
                alert.setHeaderText("Are you sure you want to add this song?");
                alert.initOwner(((Node) e.getSource()).getScene().getWindow());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == ButtonType.YES) {
                    obsList.add(song);
                    Collections.sort(obsList);
                    saveData(obsList);
                    int newIndex = obsList.indexOf(song);
                    listView.getSelectionModel().select(newIndex);
                }
            }
        }
    }
    public void editSong(ActionEvent e){
        int index = listView.getSelectionModel().getSelectedIndex();
        //If there is a song being selected
        if(index >= 0){
            Song song = obsList.get(index);
            String songDetails = "Origin:\n\tSong Name: \t" + song.getSongName() + "\n\tArtist Name: \t" + song.getArtistName() + "\n\tAlbum: \t" + song.getAlbum() + "\n\tYear: \t" + song.getYear();
            String songName = songNameTextField.getText();
            String artistName = artistNameTextField.getText();
            String album = albumTextField.getText();
            int year = convertToYearInt(yearTextField.getText());
            String editedSongDetails = "Revision:\n\tSong Name: \t" + songName + "\n\tArtist Name: \t" + artistName + "\n\tAlbum: \t" + album + "\n\tYear: \t" + yearTextField.getText();
            //If song name or artist name is empty
            if(songNameTextField.getLength()==0 || artistNameTextField.getLength()==0){
                Alert alert = new Alert(Alert.AlertType.ERROR, songDetails + "\n" + editedSongDetails, ButtonType.OK);
                alert.setTitle("Error!");
                alert.setHeaderText("You should at least enter a song name and an artist name!");
                alert.initOwner(((Node) e.getSource()).getScene().getWindow());
                alert.showAndWait();
            }
            else{
                //If the user makes any change
                if(!(song.getSongName().compareToIgnoreCase(songName)==0 && song.getArtistName().compareToIgnoreCase(artistName)==0 && song.getAlbum().compareToIgnoreCase(album)==0 && song.getYear().compareToIgnoreCase(yearTextField.getText())==0)){
                    //If the revised song is existed
                    if(obsList.contains(new Song(songName, artistName, album, year)) && (song.getSongName().compareToIgnoreCase(songName)!=0 || song.getArtistName().compareToIgnoreCase(artistName)!=0)){
                        for(int i = 0; i < obsList.size(); i++){
                            if(obsList.get(i).equals(new Song(songName, artistName, album, year))){
                                Song existedSong = obsList.get(i);
                                songDetails = "Existed Song:\n\tSong Name: \t" + existedSong.getSongName() + "\n\tArtist Name: \t" + existedSong.getArtistName() + "\n\tAlbum: \t" + existedSong.getAlbum() + "\n\tYear: \t" + existedSong.getYear();
                            }
                        }
                        Alert alert = new Alert(Alert.AlertType.ERROR, songDetails + "\n" + editedSongDetails, ButtonType.OK);
                        alert.setTitle("Error!");
                        alert.setHeaderText("The song already exists!");
                        alert.initOwner(((Node)e.getSource()).getScene().getWindow());
                        alert.showAndWait();
                    }
                    //If the revised song's year is illegal
                    else if(year == -1){
                        Alert alert = new Alert(Alert.AlertType.ERROR, songDetails + "\n" + editedSongDetails, ButtonType.OK);
                        alert.setTitle("Error!");
                        alert.setHeaderText("Please enter year as a positive Integer!");
                        alert.initOwner(((Node) e.getSource()).getScene().getWindow());
                        alert.showAndWait();
                    }
                    //The user makes a legal change
                    else{
                        song = new Song(songName, artistName, album, year);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, songDetails + "\n" + editedSongDetails, ButtonType.YES, ButtonType.CANCEL);
                        alert.setTitle("Edit");
                        alert.setHeaderText("Are you sure you want to save the change?");
                        alert.initOwner(((Node)e.getSource()).getScene().getWindow());
                        Optional<ButtonType> option =  alert.showAndWait();
                        if(option.get() == ButtonType.YES){
                            obsList.set(index, song);
                            Collections.sort(obsList);
                            int newIndex = obsList.indexOf(song);
                            listView.getSelectionModel().select(newIndex);
                        }
                    }
                }
                //If the user doesn't change anything
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You don't change anything.", ButtonType.OK);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Illegal Manipulation!");
                    alert.initOwner(((Node)e.getSource()).getScene().getWindow());
                    alert.showAndWait();
                }
            }
        }
        //If no song is selected
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is no song selected.", ButtonType.OK);
            alert.setTitle("Error!");
            alert.setHeaderText("Illegal Manipulation!");
            alert.initOwner(((Node)e.getSource()).getScene().getWindow());
            alert.showAndWait();
        }
    }
    public void deleteSong(ActionEvent e){
        int index = listView.getSelectionModel().getSelectedIndex();
        //If a song is selected
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
                //Select the
                else if(index - 1 >=0){
                    listView.getSelectionModel().select(index - 1);
                }
                else{
                    listView.getSelectionModel().clearSelection(index);
                }
                saveData(obsList);
            }
        }
        //If no song is selected
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is no song selected.", ButtonType.OK);
            alert.setTitle("Error!");
            alert.setHeaderText("Illegal Manipulation!");
            alert.initOwner(((Node)e.getSource()).getScene().getWindow());
            alert.showAndWait();
        }
    }
    public int convertToYearInt(String year){
        if(year.length() == 0)
            return 0;
        for(int i = 0; i < year.length(); i++){
            if(year.charAt(i) < '0' || year.charAt(i) >'9'){
                return -1;
            }
        }
        int yearInt = Integer.parseInt(year);
        if(yearInt>0){
            return yearInt;
        }
        else{
            return -1;
        }
    }
}
