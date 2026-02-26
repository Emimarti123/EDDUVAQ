package practices.exam;

import java.util.ArrayList;

public class Playlist {

    private String name;
    private CircularPlaylist circularPlaylist;

    public Playlist(String name) {
        if (name == null) {
            name = "";
        }

        this.name = name.trim();

        if (this.name.length() == 0) {
            this.name = "Untitled Playlist";
        }

        this.circularPlaylist = new CircularPlaylist();
    }

    public String getName() {
        return name;
    }

    public CircularPlaylist getCircularPlaylist() {
        return circularPlaylist;
    }

        public int size() {
        return circularPlaylist.getSize();
        }

    public void addSong(Song song) {
        circularPlaylist.addSong(song);
    }

    public boolean removeSong(Song song) {
        return circularPlaylist.removeSong(song);
    }

    public Song nextSong() {
        return circularPlaylist.nextSong();
    }

    public Song previousSong() {
        return circularPlaylist.previousSong();
    }

    public void resetToStart() {
        circularPlaylist.resetToStart();
    }

    public int getCurrentIndex() {
        return circularPlaylist.getCurrentIndex();
    }

    public ArrayList<Song> toArrayList() {
        return circularPlaylist.toArrayList();
    }

    @Override
    public String toString() {
        return "Playlist{name='" + name + "', size=" + size() + "}";
    }
}