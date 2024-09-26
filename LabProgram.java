import java.util.Scanner;

// Song class
class Song {
    private String name;
    private String artist;
    private String album;
    private int length;
    private Song next;
    private Song previous;

    // No-arg constructor
    public Song() {
    }

    // Constructor with arguments
    public Song(String name, String artist, String album, int length) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Song getNext() {
        return next;
    }

    public void setNext(Song next) {
        this.next = next;
    }

    public Song getPrevious() {
        return previous;
    }

    public void setPrevious(Song previous) {
        this.previous = previous;
    }
}

// Playlist class
class Playlist {
    private Song head;
    private Song tail;
    private int size;

    // No-arg constructor
    public Playlist() {
        head = new Song();
        tail = new Song();
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
    }

    // Add a Song to the end of the Playlist
    public void addSong(Song song) {
        Song lastSong = tail.getPrevious();
        lastSong.setNext(song);
        song.setPrevious(lastSong);
        song.setNext(tail);
        tail.setPrevious(song);
        size++;
    }

    // Insert a new Song after the Song that is currently playing
    public void insertSongAfterCurrent(Song newSong, Song currentSong) {
        if (currentSong == null) {
            System.out.println("Error: No current song to insert after.");
            return;
        }

        Song nextSong = currentSong.getNext();
        newSong.setPrevious(currentSong);
        newSong.setNext(nextSong);
        currentSong.setNext(newSong);
        nextSong.setPrevious(newSong);
        size++;
    }

    // Remove the specified Song from the Playlist
    public void removeSong(Song song) {
        if (song == null) {
            System.out.println("Error: No song to remove.");
            return;
        }

        Song previousSong = song.getPrevious();
        Song nextSong = song.getNext();
        previousSong.setNext(nextSong);
        nextSong.setPrevious(previousSong);
        size--;
    }

    // Print the contents of the Playlist
    public void printPlaylist() {
        if (size == 0) {
            System.out.println("The playlist is empty.");
            return;
        }

        Song currentSong = head.getNext();
        int count = 1;

        while (currentSong != tail) {
            System.out.println("Song " + count + ":");
            System.out.println("Name: " + currentSong.getName());
            System.out.println("Artist: " + currentSong.getArtist());
            System.out.println("Album: " + currentSong.getAlbum());
            System.out.println("Length: " + currentSong.getLength() + " seconds");
            System.out.println("---------------------");
            currentSong = currentSong.getNext();
            count++;
        }

        System.out.println("Number of songs in the playlist: " + size);
        int totalLength = calculateTotalLength();
        int minutes = totalLength / 60;
        int seconds = totalLength % 60;
        System.out.println("Length of the playlist: " + minutes + " minutes " + seconds + " seconds");
    }

    // Calculate the total length of the playlist
    private int calculateTotalLength() {
        Song currentSong = head.getNext();
        int totalLength = 0;

        while (currentSong != tail) {
            totalLength += currentSong.getLength();
            currentSong = currentSong.getNext();
        }

        return totalLength;
    }
}

// Demo class


// Demo class
public class Demo {
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add a Song to the Playlist");
            System.out.println("2. Insert a new Song after the Song that is currently playing");
            System.out.println("3. Print the contents of the Playlist");
            System.out.println("4. Display the current Song");
            System.out.println("5. Remove the current song");
            System.out.println("6. Skip to the next song");
            System.out.println("7. Return to the previous song");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter the name of the song: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the name of the artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("Enter the name of the album: ");
                    String album = scanner.nextLine();
                    System.out.print("Enter the length of the song in seconds: ");
                    int length = scanner.nextInt();
                    Song song = new Song(name, artist, album, length);
                    playlist.addSong(song);
                    System.out.println("Song added to the playlist.");
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter the name of the song to insert: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter the name of the artist: ");
                    String newArtist = scanner.nextLine();
                    System.out.print("Enter the name of the album: ");
                    String newAlbum = scanner.nextLine();
                    System.out.print("Enter the length of the song in seconds: ");
                    int newLength = scanner.nextInt();
                    Song newSong = new Song(newName, newArtist, newAlbum, newLength);
                    System.out.print("Enter the name of the current song: ");
                    scanner.nextLine(); // Consume the newline character
                    String currentName = scanner.nextLine();
                    boolean foundCurrent = false;
                    Song currentSong = playlist.head.getNext();

                    while (currentSong != playlist.tail) {
                        if (currentSong.getName().equals(currentName)) {
                            playlist.insertSongAfterCurrent(newSong, currentSong);
                            foundCurrent = true;
                            break;
                        }
                        currentSong = currentSong.getNext();
                    }

                    if (!foundCurrent) {
                        System.out.println("Error: Current song not found in the playlist.");
                    } else {
                        System.out.println("Song inserted into the playlist.");
                    }
                    break;
                case 3:
                    playlist.printPlaylist();
                    break;
                case 4:
                    if (playlist.getSize() == 0) {
                        System.out.println("The playlist is empty.");
                    } else {
                        System.out.println("Current Song:");
                        System.out.println("Name: " + playlist.getCurrentSong().getName());
                        System.out.println("Artist: " + playlist.getCurrentSong().getArtist());
                        System.out.println("Album: " + playlist.getCurrentSong().getAlbum());
                        System.out.println("Length: " + playlist.getCurrentSong().getLength() + " seconds");
                    }
                    break;
                case 5:
                    if (playlist.getSize() == 0) {
                        System.out.println("The playlist is empty.");
                    } else {
                        playlist.removeSong(playlist.getCurrentSong());
                        System.out.println("Current song removed from the playlist.");
                    }
                    break;
                case 6:
                    if (playlist.getSize() == 0) {
                        System.out.println("The playlist is empty.");
                    } else {
                        playlist.setCurrentSong(playlist.getCurrentSong().getNext());
                        System.out.println("Skipped to the next song.");
                    }
                    break;
                case 7:
                    if (playlist.getSize() == 0) {
                        System.out.println("The playlist is empty.");
                    } else {
                        playlist.setCurrentSong(playlist.getCurrentSong().getPrevious());
                        System.out.println("Returned to the previous song.");
                    }
                    break;
                case 8:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid menu option.");
                    break;
            }
        } while (choice != 8);

        scanner.close();
    }
}
