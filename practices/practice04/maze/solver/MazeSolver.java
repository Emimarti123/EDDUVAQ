package maze.solver;

import maze.model.Cell;
import maze.model.Maze;

public class MazeSolver {

    public interface SolverCallback {
        void onCellUpdated();
        void onSolved(boolean success);
    }

    private Maze maze;
    private SolverCallback callback;
    private int delayMs;

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public MazeSolver(Maze maze, SolverCallback callback, int delayMs) {
        this.maze = maze;
        this.callback = callback;
        this.delayMs = delayMs;
    }

    public void solveAsync() {
        Thread thread = new Thread(() -> {
            maze.resetSolution();
            boolean result = solve(maze.getStartRow(), maze.getStartCol());
            callback.onSolved(result);
        });

        thread.setDaemon(true);
        thread.start();
    }

    private boolean solve(int r, int c) {

        if (!maze.inBounds(r, c)) return false;

        Cell cell = maze.getCell(r, c);
        Cell.Type type = cell.getType();

        if (type == Cell.Type.WALL) return false;
        if (type == Cell.Type.VISITED) return false;
        if (type == Cell.Type.PATH) return false;

        if (type == Cell.Type.END) return true;

        if (type != Cell.Type.START) {
            cell.setType(Cell.Type.VISITED);
            notifyAndWait();
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + DR[i];
            int nc = c + DC[i];

            if (solve(nr, nc)) {
                if (type != Cell.Type.START) {
                    cell.setType(Cell.Type.PATH);
                }
                notifyAndWait();
                return true;
            }
        }

        return false;
    }

    private void notifyAndWait() {
        callback.onCellUpdated();

        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}