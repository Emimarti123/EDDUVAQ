package practices.exam;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MusicPlayerGUI extends JFrame {

    private MusicPlayerSystem system;

    private DefaultListModel<Song> libraryModel;
    private JList<Song> libraryList;

    private DefaultListModel<Song> playlistModel;
    private JList<Song> playlistList;

    private DefaultComboBoxModel<String> playlistNamesModel;
    private JComboBox<String> playlistCombo;

    private JLabel currentSongLabel;
    private JCheckBox loopCheckBox;

    private JButton addSongButton;
    private JButton removeSongButton;

    private JButton createPlaylistButton;
    private JButton deletePlaylistButton;

    private JButton addToPlaylistButton;
    private JButton removeFromPlaylistButton;

    private JButton prevButton;
    private JButton nextButton;

    public MusicPlayerGUI(MusicPlayerSystem system) {
        this.system = system;

        setTitle("Music Player System");
        setSize(920, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
        refreshAll();
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel playlistPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        playlistNamesModel = new DefaultComboBoxModel<String>();
        playlistCombo = new JComboBox<String>(playlistNamesModel);

        createPlaylistButton = new JButton("Create Playlist");
        deletePlaylistButton = new JButton("Delete Playlist");

        playlistPanel.add(new JLabel("Playlist:"));
        playlistPanel.add(playlistCombo);
        playlistPanel.add(createPlaylistButton);
        playlistPanel.add(deletePlaylistButton);

        loopCheckBox = new JCheckBox("Loop enabled");
        playlistPanel.add(loopCheckBox);

        topPanel.add(playlistPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 12, 12));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel libraryPanel = new JPanel(new BorderLayout(8, 8));
        libraryPanel.setBorder(BorderFactory.createTitledBorder("Music Library"));

        libraryModel = new DefaultListModel<Song>();
        libraryList = new JList<Song>(libraryModel);
        libraryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane libraryScroll = new JScrollPane(libraryList);
        libraryPanel.add(libraryScroll, BorderLayout.CENTER);

        JPanel libraryButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addSongButton = new JButton("Add Song");
        removeSongButton = new JButton("Remove Song");
        libraryButtons.add(addSongButton);
        libraryButtons.add(removeSongButton);

        libraryPanel.add(libraryButtons, BorderLayout.SOUTH);

        JPanel playlistSongsPanel = new JPanel(new BorderLayout(8, 8));
        playlistSongsPanel.setBorder(BorderFactory.createTitledBorder("Current Playlist Songs"));

        playlistModel = new DefaultListModel<Song>();
        playlistList = new JList<Song>(playlistModel);
        playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane playlistScroll = new JScrollPane(playlistList);
        playlistSongsPanel.add(playlistScroll, BorderLayout.CENTER);

        JPanel playlistButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addToPlaylistButton = new JButton("Add selected from Library");
        removeFromPlaylistButton = new JButton("Remove selected from Playlist");
        playlistButtons.add(addToPlaylistButton);
        playlistButtons.add(removeFromPlaylistButton);

        playlistSongsPanel.add(playlistButtons, BorderLayout.SOUTH);

        centerPanel.add(libraryPanel);
        centerPanel.add(playlistSongsPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));

        currentSongLabel = new JLabel("Current: (none)");
        currentSongLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        bottomPanel.add(currentSongLabel, BorderLayout.CENTER);

        JPanel playerControls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        playerControls.add(prevButton);
        playerControls.add(nextButton);

        bottomPanel.add(playerControls, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        wireEvents();
    }

    private void wireEvents() {

        loopCheckBox.addActionListener(e -> {
            system.setLoopEnabled(loopCheckBox.isSelected());
            refreshCurrentSongLabel();
        });

        createPlaylistButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Playlist name:");
            if (name == null) return;

            name = name.trim();
            if (name.length() == 0) {
                showMessage("Playlist name cannot be empty.");
                return;
            }

            system.createPlaylist(name);
            refreshPlaylists();
            system.switchToPlaylistByName(name);
            refreshPlaylistSongs();
            refreshCurrentSongLabel();
        });

        deletePlaylistButton.addActionListener(e -> {
            int index = playlistCombo.getSelectedIndex();
            if (index < 0) {
                showMessage("No playlist selected.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete the selected playlist?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            system.getPlaylistManager().removePlaylistAt(index);
            refreshPlaylists();
            refreshPlaylistSongs();
            refreshCurrentSongLabel();
        });

        playlistCombo.addActionListener(e -> {
            int index = playlistCombo.getSelectedIndex();
            if (index < 0) return;

            boolean ok = system.switchToPlaylist(index);
            if (ok) {
                refreshPlaylistSongs();
                refreshCurrentSongLabel();
            }
        });

        addSongButton.addActionListener(e -> {
            Song created = askForSong();
            if (created == null) return;

            system.addSongToLibrary(created);
            refreshLibrary();
        });

        removeSongButton.addActionListener(e -> {
            Song selected = libraryList.getSelectedValue();
            if (selected == null) {
                showMessage("Select a song from the Library first.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Remove selected song from Library?\nThis will also remove it from all playlists.",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            system.removeSongFromLibrary(selected);
            refreshLibrary();
            refreshPlaylistSongs();
            refreshCurrentSongLabel();
        });

        addToPlaylistButton.addActionListener(e -> {
            Song selected = libraryList.getSelectedValue();
            if (selected == null) {
                showMessage("Select a song from the Library first.");
                return;
            }

            Playlist current = system.getCurrentPlaylist();
            if (current == null) {
                showMessage("Create and select a playlist first.");
                return;
            }

            system.addSongToCurrentPlaylist(selected);
            refreshPlaylistSongs();
            refreshCurrentSongLabel();
        });

        removeFromPlaylistButton.addActionListener(e -> {
            Song selected = playlistList.getSelectedValue();
            if (selected == null) {
                showMessage("Select a song from the Playlist first.");
                return;
            }

            system.removeSongFromCurrentPlaylist(selected);
            refreshPlaylistSongs();
            refreshCurrentSongLabel();
        });

        nextButton.addActionListener(e -> {
            Song next = system.nextSong();
            if (next == null) showMessage("No next song.");
            refreshCurrentSongLabel();
        });

        prevButton.addActionListener(e -> {
            Song prev = system.previousSong();
            if (prev == null) showMessage("No previous song.");
            refreshCurrentSongLabel();
        });
    }

    private Song askForSong() {
        JTextField titleField = new JTextField();
        JTextField artistField = new JTextField();
        JTextField durationField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Artist:"));
        panel.add(artistField);
        panel.add(new JLabel("Duration (mm:ss):"));
        panel.add(durationField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Song",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return null;

        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String durationText = durationField.getText().trim();

        if (title.length() == 0) {
            showMessage("Title cannot be empty.");
            return null;
        }

        if (artist.length() == 0) {
            showMessage("Artist cannot be empty.");
            return null;
        }

        int totalSeconds = parseDurationToSeconds(durationText);
        if (totalSeconds <= 0) return null;

        return new Song(title, artist, totalSeconds);
    }

    private int parseDurationToSeconds(String text) {
        String[] parts = text.split(":");
        if (parts.length != 2) {
            showMessage("Duration must be in format mm:ss");
            return -1;
        }

        int minutes;
        int seconds;

        try {
            minutes = Integer.parseInt(parts[0]);
            seconds = Integer.parseInt(parts[1]);
        } catch (Exception ex) {
            showMessage("Minutes and seconds must be numbers.");
            return -1;
        }

        if (minutes < 0 || seconds < 0 || seconds >= 60) {
            showMessage("Invalid time format.");
            return -1;
        }

        return (minutes * 60) + seconds;
    }

    private void refreshAll() {
        refreshPlaylists();
        refreshLibrary();
        refreshPlaylistSongs();
        refreshCurrentSongLabel();
        loopCheckBox.setSelected(system.isLoopEnabled());
    }

    private void refreshPlaylists() {
        playlistNamesModel.removeAllElements();

        ArrayList<String> names = system.getPlaylistManager().getPlaylistNames();
        for (String name : names) {
            playlistNamesModel.addElement(name);
        }

        int idx = system.getPlaylistManager().getCurrentPlaylistIndex();
        if (idx >= 0 && idx < playlistNamesModel.getSize()) {
            playlistCombo.setSelectedIndex(idx);
        }
    }

    private void refreshLibrary() {
        libraryModel.clear();
        int i = 0;
        while (true) {
            Song s = system.getLibrary().getSongAt(i);
            if (s == null) break;
            libraryModel.addElement(s);
            i++;
        }
    }

    private void refreshPlaylistSongs() {
        playlistModel.clear();
        Playlist current = system.getCurrentPlaylist();
        if (current == null) return;

        ArrayList<Song> songs = current.toArrayList();
        for (Song s : songs) {
            playlistModel.addElement(s);
        }
    }

    private void refreshCurrentSongLabel() {
        Playlist current = system.getCurrentPlaylist();
        if (current == null) {
            currentSongLabel.setText("Current: (no playlist selected)");
            return;
        }

        Song s = system.getCurrentSong();
        if (s == null) {
            currentSongLabel.setText("Current: (playlist empty)");
            return;
        }

        currentSongLabel.setText("Current: " + s.getTitle() + " - " + s.getArtist() + " (" + s.getFormattedDuration() + ")");
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}