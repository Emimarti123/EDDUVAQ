package practices.exam;

public class MusicPlayerSystem {

    private MusicLibrary library;
    private PlaylistManager playlistManager;
    private boolean loopEnabled;

    public MusicPlayerSystem() {
        library = new MusicLibrary();
        playlistManager = new PlaylistManager();
        loopEnabled = false;
    }

    public MusicLibrary getLibrary() {
        return library;
    }

    public PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public boolean isLoopEnabled() {
        return loopEnabled;
    }

    public void setLoopEnabled(boolean enabled) {
        loopEnabled = enabled;
    }

    public void toggleLoop() {
        loopEnabled = !loopEnabled;
    }

    public void addSongToLibrary(Song song) {
        if (song == null) {
            return;
        }
        library.addSong(song);
    }

    public boolean removeSongFromLibrary(Song song) {
        if (song == null) {
            return false;
        }

        boolean removedFromLibrary = library.removeSong(song);

        if (removedFromLibrary) {
            playlistManager.removeSongFromAllPlaylists(song);
        }

        return removedFromLibrary;
    }

    public Playlist createPlaylist(String name) {
        return playlistManager.createPlaylist(name);
    }

    public boolean switchToPlaylist(int index) {
        return playlistManager.switchToPlaylist(index);
    }

    public boolean switchToPlaylistByName(String name) {
        return playlistManager.switchToPlaylistByName(name);
    }

    public Playlist getCurrentPlaylist() {
        return playlistManager.getCurrentPlaylist();
    }

    public boolean addSongToCurrentPlaylist(Song song) {
        Playlist current = getCurrentPlaylist();
        if (current == null) {
            return false;
        }
        current.addSong(song);
        return true;
    }

    public boolean removeSongFromCurrentPlaylist(Song song) {
        Playlist current = getCurrentPlaylist();
        if (current == null) {
            return false;
        }
        return current.removeSong(song);
    }

    public Song getCurrentSong() {
        Playlist current = getCurrentPlaylist();
        if (current == null) {
            return null;
        }
        return current.getCircularPlaylist().getCurrentSong();
    }

    public Song nextSong() {
        Playlist current = getCurrentPlaylist();
        if (current == null) {
            return null;
        }

        if (loopEnabled) {
            current.getCircularPlaylist().setLoopEnabled(true);
        } else {
            current.getCircularPlaylist().setLoopEnabled(false);
        }

        return current.nextSong();
    }

    public Song previousSong() {
        Playlist current = getCurrentPlaylist();
        if (current == null) {
            return null;
        }

        if (loopEnabled) {
            current.getCircularPlaylist().setLoopEnabled(true);
        } else {
            current.getCircularPlaylist().setLoopEnabled(false);
        }

        return current.previousSong();
    }

    public void resetCurrentPlaylistToStart() {
        Playlist current = getCurrentPlaylist();
        if (current == null) {
            return;
        }
        current.resetToStart();
    }
}