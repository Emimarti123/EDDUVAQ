package maze.ui;

import maze.model.Cell;
import maze.model.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazePanel extends JPanel {

    private static final int CELL_SIZE = 40;

    private static final Color COLOR_OPEN = Color.WHITE;
    private static final Color COLOR_WALL = new Color(40, 40, 40);
    private static final Color COLOR_START = new Color(0, 180, 0);
    private static final Color COLOR_END = new Color(200, 0, 0);
    private static final Color COLOR_VISITED = new Color(173, 216, 230);
    private static final Color COLOR_PATH = new Color(255, 215, 0);
    private static final Color COLOR_GRID = new Color(200, 200, 200);

    private Maze maze;

    public enum EditMode {
        WALL,
        OPEN,
        SET_START,
        SET_END
    }

    private EditMode editMode = EditMode.WALL;

    public MazePanel(Maze maze) {
        this.maze = maze;

        setPreferredSize(new Dimension(
                maze.getCols() * CELL_SIZE,
                maze.getRows() * CELL_SIZE
        ));

        setupMouseListener();
    }

    public void setMaze(Maze maze) {
        this.maze = maze;

        setPreferredSize(new Dimension(
                maze.getCols() * CELL_SIZE,
                maze.getRows() * CELL_SIZE
        ));

        revalidate();
        repaint();
    }

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                int x = c * CELL_SIZE;
                int y = r * CELL_SIZE;

                Cell.Type type = maze.getCell(r, c).getType();

                g2.setColor(getColor(type));
                g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                g2.setColor(COLOR_GRID);
                g2.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private Color getColor(Cell.Type type) {
        switch (type) {
            case WALL:
                return COLOR_WALL;
            case START:
                return COLOR_START;
            case END:
                return COLOR_END;
            case VISITED:
                return COLOR_VISITED;
            case PATH:
                return COLOR_PATH;
            default:
                return COLOR_OPEN;
        }
    }

    private void setupMouseListener() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleClick(e);
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void handleClick(MouseEvent e) {
        int c = e.getX() / CELL_SIZE;
        int r = e.getY() / CELL_SIZE;

        if (!maze.inBounds(r, c)) {
            return;
        }

        Cell cell = maze.getCell(r, c);

        switch (editMode) {
            case WALL:
                cell.setType(Cell.Type.WALL);
                break;
            case OPEN:
                cell.setType(Cell.Type.OPEN);
                break;
            case SET_START:
                maze.setStart(r, c);
                break;
            case SET_END:
                maze.setEnd(r, c);
                break;
        }

        repaint();
    }
}