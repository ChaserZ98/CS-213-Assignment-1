package songlib.model;

//import java.util.ArrayList;
//import java.util.Collections;

public class Song implements Comparable<Song>{
    String songName;
    String artistName;
    String album;
    int year;
    public Song(String songName, String artistName, String album, int year) {
        this.songName = songName;
        this.artistName = artistName;
        this.album = album;
        this.year = year;
    }
    public Song(String songName, String artistName, String album){
        this(songName, artistName, album, -1);
    }
    public Song(String songName, String artistName, int year){
        this(songName, artistName, "", -1);
    }
    public Song(String songName, String artistName){
        this(songName, artistName, "", -1);
    }
    public String getSongName(){
        return this.songName;
    }
    public void setSongName(String songName){
        this.songName = songName;
    }
    public String getArtistName(){
        return this.artistName;
    }
    public void setArtistName(String artistName){
        this.artistName = artistName;
    }
    public String getAlbum(){
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear(){
        return this.year==-1?"":String.valueOf(this.year);
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString(){
        return this.songName + "-" + this.artistName;
    }

    @Override
    public int compareTo(Song newSong){
        if(this.songName.compareToIgnoreCase(newSong.getSongName()) == 0){
            return this.artistName.compareToIgnoreCase(newSong.getArtistName());
        }
        else{
            return this.songName.compareToIgnoreCase(newSong.getSongName());
        }
    }
}
