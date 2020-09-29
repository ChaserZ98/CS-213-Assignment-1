package songlib.model;

//import java.util.ArrayList;
//import java.util.Collections;

import java.util.ArrayList;

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
    public boolean equals(Object newSong){
        if(newSong instanceof Song){
            if(this.compareTo((Song)newSong)==0){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

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
    public static void main(String[] args){
        Song s1 = new Song("1", "1");
        Song s2 = new Song("1", "1", "2", 1992);
        ArrayList<Song> list = new ArrayList<Song>();
        list.add(s1);
        System.out.println(list.contains(s2));
//        System.out.println(s1.equ);
//        list.add(s2);

    }
}
