package practices.exam;

import javax.swing.SwingUtilities;

public class Exam {

    public static void main(String[] args) {

        MusicPlayerSystem system = new MusicPlayerSystem();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MusicPlayerGUI gui = new MusicPlayerGUI(system);
                gui.setVisible(true);
            }
        });
    }
}