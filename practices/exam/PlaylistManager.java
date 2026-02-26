package practices.exam;

import java.util.ArrayList;

public class PlaylistManager {

        private ArrayList<Playlist> playlists;
         private int currentPlaylistIndex;

             public PlaylistManager() {
                 playlists = new ArrayList<Playlist>();
                    currentPlaylistIndex = -1;
        }

            public int getPlaylistsCount() {
                return playlists.size();
    }

        public Playlist getPlaylistAt(int index) {
                if (index < 0 || index >= playlists.size()) {
            return null;
        }
        return playlists.get(index);
    }

        public Playlist getCurrentPlaylist() {
                if (currentPlaylistIndex < 0 || currentPlaylistIndex >= playlists.size()) {
            return null;
        }
        return playlists.get(currentPlaylistIndex);
    }

        public int getCurrentPlaylistIndex() {
            return currentPlaylistIndex;
    }

        public Playlist createPlaylist(String name) {
            Playlist newPlaylist = new Playlist(name);
             playlists.add(newPlaylist);

            if (currentPlaylistIndex == -1) {
                currentPlaylistIndex = 0;
             Playlist current = getCurrentPlaylist();
                if (current != null) {
                current.resetToStart();
            }
        }

        return newPlaylist;
    }

    public boolean switchToPlaylist(int index) {
            if (index < 0 || index >= playlists.size()) {
            return false;
        }

            currentPlaylistIndex = index;

            Playlist current = getCurrentPlaylist();
            if (current != null) {
            current.resetToStart();
        }

                return true;
    }

            public boolean switchToPlaylistByName(String name) {
        if (name == null) {
            return false;
        }

        String target = name.trim();

            for (int i = 0; i < playlists.size(); i++) {
                Playlist p = playlists.get(i);
                     if (p != null) {
                        if (p.getName().equalsIgnoreCase(target)) {
                            return switchToPlaylist(i);
                }
            }
        }

        return false;
    }

    public boolean removePlaylistAt(int index) {
            if (index < 0 || index >= playlists.size()) {
                 return false;
        }

            playlists.remove(index);

                if (playlists.size() == 0) {
                        currentPlaylistIndex = -1;
                        return true;
        }

            if (currentPlaylistIndex >= playlists.size()) {
             currentPlaylistIndex = playlists.size() - 1;
        }

        Playlist current = getCurrentPlaylist();
            if (current != null) {
            current.resetToStart();
        }

        return true;
    }

    public int indexOfPlaylistByName(String name) {
        if (name == null) {
            return -1;
        }

        String target = name.trim();

        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            if (p != null) {
                if (p.getName().equalsIgnoreCase(target)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int removeSongFromAllPlaylists(Song song) {
        int removedFromPlaylists = 0;

        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            if (p != null) {
                boolean removed = p.removeSong(song);
                if (removed) {
                    removedFromPlaylists = removedFromPlaylists + 1;
                }
            }
        }

        return removedFromPlaylists;
    }

    public ArrayList<String> getPlaylistNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (int i = 0; i < playlists.size(); i++) {
             Playlist p = playlists.get(i);
                    if (p == null) {
                names.add("null");
                     } else {
                names.add(p.getName());
            }
        }

        return names;
    }
}