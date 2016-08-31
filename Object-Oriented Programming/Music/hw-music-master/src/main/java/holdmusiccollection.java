import java.util.Scanner;

public class MusicCollection {
    private static Scanner keyboard;
    public static void main(String[] args) {
        keyboard = new Scanner(System.in);
        Album ubw = new Album("Fate/Stay Night", "Nasu Kinoko");
        Song snop1 = new Song("Ideal White", "Ayano");
        Album zero = new Album("Fate/Zero", "Kaijura Yuki");
        Song zop2 = new Song("to the beginning", "kalafina");
        Album seed = new Album("Gundam", "Tanaka Rie");
        Song kira = new Song("Meteor", "TMR");
        seed.addSong(kira);
        zero.addSong(zop2);
        ubw.addSong(snop1);
        Album[] albums = {zero, ubw, seed};
        int no = 0;
        while (no != -1) {
            for (int i = 0; i < albums.length; i++) {
                System.out.println("[" + i + "]" + " "
                    + albums[i].getTitle() + " - " + albums[i].getArtist());
            }
            keyboard = new Scanner(System.in);
            int input1 = keyboard.nextInt();
            if (input1 != -1) {
                albumOptions(albums[input1]);
            } else if (input1 == -1) {
                no = -1;
            }
        }
    }
    public static void albumOptions(Album albumName) {
        System.out.println(albumName);
        System.out.println("Enter [0] to Add Song or [1] to Go Back");
        keyboard = new Scanner(System.in);
        int input2 = keyboard.nextInt();
        if (input2 == 0) {
            System.out.println("Enter title, artist, and (optional) genre, "
                + "seperated by commas");
            keyboard = new Scanner(System.in);
            String userinput = keyboard.nextLine().trim();
            String[] components = userinput.split(",");
            if (components.length == 3) {
                Song user = new Song(components[0].trim(), components[1].trim(),
                     components[2].trim());
                albumName.addSong(user);
            } else {
                Song user = new Song(components[0].trim(),
                    components[1].trim());
                albumName.addSong(user);
            }
        } else if (input2 == 1) {
            return;
        }
    }
}
