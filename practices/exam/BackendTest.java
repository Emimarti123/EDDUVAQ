package practices.exam;

public class BackendTest {

    public static void main(String[] args) {

        MusicLibrary library = new MusicLibrary();
        CircularPlaylist playlist = new CircularPlaylist();

        Song s1 = new Song("Song A", "Artist A", 210);
        Song s2 = new Song("Song B", "Artist B", 185);
        Song s3 = new Song("Song C", "Artist C", 240);

        library.addSong(s1);
        library.addSong(s2);
        library.addSong(s3);

        playlist.addSong(s1);
        playlist.addSong(s2);
        playlist.addSong(s3);

        System.out.println("Library size: " + library.getSize());
        System.out.println("Playlist size: " + playlist.getSize());

        System.out.println("Current: " + playlist.getCurrentSong());
        System.out.println("Next: " + playlist.nextSong());
        System.out.println("Next: " + playlist.nextSong());
        System.out.println("Next (no loop, should be null): " + playlist.nextSong());

        playlist.setLoopEnabled(true);
        playlist.resetToStart();

        System.out.println("Loop enabled: " + playlist.isLoopEnabled());
        System.out.println("Current after reset: " + playlist.getCurrentSong());
        System.out.println("Previous (loop, should go to last): " + playlist.previousSong());
        System.out.println("Next (loop): " + playlist.nextSong());

        boolean removedB = playlist.removeSong(s2);
        System.out.println("Removed Song B: " + removedB);
        System.out.println("Playlist size after remove: " + playlist.getSize());

        playlist.resetToStart();
        System.out.println("Current after reset: " + playlist.getCurrentSong());
    }
}