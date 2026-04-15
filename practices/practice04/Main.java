import maze.ui.MazeFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MazeFrame().setVisible(true);
        });
    }
}