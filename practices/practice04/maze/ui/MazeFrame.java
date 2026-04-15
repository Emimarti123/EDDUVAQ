package maze.ui;

import maze.model.Maze;
import maze.model.PreloadedMazes;
import maze.solver.MazeSolver;

import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame {

    private Maze maze;
    private MazePanel mazePanel;

    public MazeFrame() {
        super("Maze Solver - Backtracking");

        maze = new Maze(10, 10);
        maze.initEmpty();

        mazePanel = new MazePanel(maze);

        setLayout(new BorderLayout());
        add(new JScrollPane(mazePanel), BorderLayout.CENTER);
        add(buildControlPanel(), BorderLayout.EAST);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(190, 0));

        panel.add(new JLabel("Edit mode:"));

        JButton btnWall = new JButton("Place wall");
        JButton btnOpen = new JButton("Erase wall");
        JButton btnStart = new JButton("Set start (S)");
        JButton btnEnd = new JButton("Set end (E)");

        btnWall.addActionListener(e -> mazePanel.setEditMode(MazePanel.EditMode.WALL));
        btnOpen.addActionListener(e -> mazePanel.setEditMode(MazePanel.EditMode.OPEN));
        btnStart.addActionListener(e -> mazePanel.setEditMode(MazePanel.EditMode.SET_START));
        btnEnd.addActionListener(e -> mazePanel.setEditMode(MazePanel.EditMode.SET_END));

        panel.add(btnWall);
        panel.add(btnOpen);
        panel.add(btnStart);
        panel.add(btnEnd);

        panel.add(new JSeparator());

        panel.add(new JLabel("Preloaded mazes:"));

        JComboBox<String> combo = new JComboBox<>(PreloadedMazes.NAMES);
        JButton btnLoad = new JButton("Load");

        btnLoad.addActionListener(e -> {
            int index = combo.getSelectedIndex();
            maze = new Maze(10, 10);
            maze.loadFromPattern(PreloadedMazes.MAZES[index]);
            mazePanel.setMaze(maze);
        });

        panel.add(combo);
        panel.add(btnLoad);

        panel.add(new JSeparator());

        panel.add(new JLabel("Solver:"));

        JSlider speedSlider = new JSlider(10, 300, 80);
        speedSlider.setBorder(BorderFactory.createTitledBorder("Speed (ms)"));
        panel.add(speedSlider);

        JButton btnSolve = new JButton("Solve");
        JButton btnReset = new JButton("Clear solution");
        JButton btnClear = new JButton("New maze");

        btnSolve.addActionListener(e -> {
            MazeSolver solver = new MazeSolver(
                    maze,
                    new MazeSolver.SolverCallback() {
                        @Override
                        public void onCellUpdated() {
                            SwingUtilities.invokeLater(mazePanel::repaint);
                        }

                        @Override
                        public void onSolved(boolean success) {
                            SwingUtilities.invokeLater(() -> {
                                mazePanel.repaint();

                                String message;
                                if (success) {
                                    message = "Solution found.";
                                } else {
                                    message = "There is no solution for this maze.";
                                }

                                JOptionPane.showMessageDialog(
                                        MazeFrame.this,
                                        message,
                                        "Result",
                                        success ? JOptionPane.INFORMATION_MESSAGE
                                                : JOptionPane.WARNING_MESSAGE
                                );
                            });
                        }
                    },
                    speedSlider.getValue()
            );

            solver.solveAsync();
        });

        btnReset.addActionListener(e -> {
            maze.resetSolution();
            mazePanel.repaint();
        });

        btnClear.addActionListener(e -> {
            maze = new Maze(10, 10);
            maze.initEmpty();
            mazePanel.setMaze(maze);
        });

        panel.add(btnSolve);
        panel.add(btnReset);
        panel.add(btnClear);

        return panel;
    }
}