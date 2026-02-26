package practices.exam;

import java.util.ArrayList;

public class CircularPlaylist {

    private PlaylistNode head;
    private PlaylistNode current;
    private int size;
    private boolean loopEnabled;

        public CircularPlaylist() {
            head = null;
            current = null;
            size = 0;
            loopEnabled = false;
    }

            public int getSize() {
                return size;
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

    public void resetToStart() {
        current = head;
    }

            public Song getCurrentSong() {
        if (current == null) {
            return null;
        }
        return current.getSong();
    }

    public void addSong(Song song) {
        if (song == null) {
            return;
        }

         PlaylistNode newNode = new PlaylistNode(song);

        if (head == null) {
            head = newNode;
            head.setNext(head);
            current = head;
            size = size + 1;
            return;
        }

         PlaylistNode last = head;
            while (last.getNext() != head) {
            last = last.getNext();
        }

        last.setNext(newNode);
        newNode.setNext(head);
        size = size + 1;
    }

    public Song nextSong() {
        if (current == null) {
            return null;
        }

            if (loopEnabled == false) {
             PlaylistNode last = head;
                while (last.getNext() != head) {
                last = last.getNext();
            }

            if (current == last) {
                return null;
            }
        }

        current = current.getNext();
        return current.getSong();
    }

        public Song previousSong() {
         if (current == null) {
            return null;
        }

        if (loopEnabled == false && current == head) {
            return null;
        }

            PlaylistNode prev = head;
                while (prev.getNext() != current) {
            prev = prev.getNext();
        }

            current = prev;
                return current.getSong();
    }

        public boolean removeSong(Song target) {
            if (head == null || target == null) {
            return false;
        }

        if (size == 1) {
            if (head.getSong() == target) {
                head = null;
                current = null;
                size = 0;
                return true;
            }
            return false;
        }

            PlaylistNode prev = null;
             PlaylistNode temp = head;

        do {
            if (temp.getSong() == target) {
                break;
            }
            prev = temp;
            temp = temp.getNext();
        } while (temp != head);

        if (temp.getSong() != target) {
            return false;
        }

        if (temp == head) {
            PlaylistNode last = head;
            while (last.getNext() != head) {
                last = last.getNext();
            }

            head = head.getNext();
            last.setNext(head);

            if (current == temp) {
                current = head;
            }

            size = size - 1;
            return true;
        }

        prev.setNext(temp.getNext());

        if (current == temp) {
            current = temp.getNext();
        }

        size = size - 1;
        return true;
    }

    public boolean removeAtIndex(int index) {
        if (head == null) {
            return false;
        }

        if (index < 0 || index >= size) {
            return false;
        }

        if (size == 1) {
            head = null;
            current = null;
            size = 0;
            return true;
        }

        if (index == 0) {
            PlaylistNode last = head;
            while (last.getNext() != head) {
                last = last.getNext();
            }

            PlaylistNode removed = head;
            head = head.getNext();
            last.setNext(head);

            if (current == removed) {
                current = head;
            }

            size = size - 1;
            return true;
        }

        PlaylistNode prev = head;
        int i = 0;
        while (i < index - 1) {
            prev = prev.getNext();
            i = i + 1;
        }

        PlaylistNode removed = prev.getNext();
        prev.setNext(removed.getNext());

        if (current == removed) {
            current = prev.getNext();
        }

        size = size - 1;
        return true;
    }

    public int getCurrentIndex() {
        if (head == null || current == null) {
            return -1;
        }

        int index = 0;
        PlaylistNode temp = head;

        while (temp != current) {
            temp = temp.getNext();
            index = index + 1;

            if (temp == head) {
                return -1;
            }
        }

        return index;
    }

    public ArrayList<Song> toArrayList() {
        ArrayList<Song> list = new ArrayList<Song>();

        if (head == null) {
            return list;
        }

        PlaylistNode temp = head;
        do {
            list.add(temp.getSong());
            temp = temp.getNext();
        } while (temp != head);

        return list;
    }
}