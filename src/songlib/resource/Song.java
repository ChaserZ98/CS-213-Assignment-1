package songlib.resource;

public class Song {
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
    public String getAlbum(){
        return album;
    }
    public String getYear(){
        return this.year==-1?"":String.valueOf(this.year);
    }
    public String toString(){
        return this.songName + "-" + this.artistName;
    }
}
