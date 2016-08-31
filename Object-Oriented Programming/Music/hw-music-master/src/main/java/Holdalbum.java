public class Album {
    private String title;
    private String artist;
    private Song[] songs = new Song[3];
    private int numSongs = 1;
    public Album(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
    public String getTitle() {
        return this.title;
    }
    public String getArtist() {
        return this.artist;
    }
    public Song[] getSongs() {
        return this.songs;
    }
    public int getNumSongs() {
        return this.numSongs;
    }
    public void addSong(Song s) {
        if (this.numSongs >= songs.length) {
            Song[] newsongs = new Song[this.numSongs * 2];
            for (int test2 = 0; test2 < this.numSongs; test2++) {
                newsongs[test2] = songs[test2];
            }
            songs = newsongs;
        }
        songs[this.numSongs] = s;
        this.numSongs++;
    }
    public String toString() {
        for (int i = 0; i < this.numSongs; i++) {
            if (songs[i] != null) {
                System.out.println(songs[i]);
            }
        } return ("in " + this.title + " - " + this.artist);
    }
}

