package practices.exam;

import java.util.ArrayList;

public class MusicLibrary {

    private ArrayList<Song> songs;

    public MusicLibrary() {
        songs = new ArrayList<Song>();
    }

    public int getSize() {
        return songs.size();
    }

    public ArrayList<Song> getAllSongs() {
        return songs;
    }

    public Song getSongAt(int index) {
        if (index < 0 || index >= songs.size()) {
            return null;
        }
        return songs.get(index);
    }

    public boolean addSong(Song song) {
        if (song == null) {
            return false;
        }
        songs.add(song);
        return true;
    }

    public boolean removeSongAt(int index) {
        if (index < 0 || index >= songs.size()) {
            return false;
        }
        songs.remove(index);
        return true;
    }

    public boolean removeSong(Song song) {
        if (song == null) {
            return false;
        }
        return songs.remove(song);
    }

    public int indexOf(Song song) {
        if (song == null) {
            return -1;
        }
        return songs.indexOf(song);
    }
}