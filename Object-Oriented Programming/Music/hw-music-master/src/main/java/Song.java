public class Song {
    private String title;
    private String artist;
    private String genre;
    public Song(String title, String artist, String genre) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }
    public Song(String title, String artist) {
        this(title, artist, "Unknown");
    }
    public String getTitle() {
        return this.title;
    }
    public String getArtist() {
        return this.artist;
    }
    public String getGenre() {
        return this.genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String toString() {
        return ("'" + this.title + "' - " + this.artist + ", " + this.genre);
    }
}