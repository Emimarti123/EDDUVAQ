package maze.model;

public class Maze {

    private int rows;
    private int cols;
    private Cell[][] grid;

    private int startRow;
    private int startCol;

    private int endRow;
    private int endCol;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initEmpty();
    }

    public void initEmpty() {
        grid = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(Cell.Type.OPEN);
            }
        }
    }

    public void loadFromPattern(String[] pattern) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = pattern[r].charAt(c);

                switch (ch) {
                    case '#':
                        grid[r][c] = new Cell(Cell.Type.WALL);
                        break;

                    case 'S':
                        grid[r][c] = new Cell(Cell.Type.START);
                        startRow = r;
                        startCol = c;
                        break;

                    case 'E':
                        grid[r][c] = new Cell(Cell.Type.END);
                        endRow = r;
                        endCol = c;
                        break;

                    default:
                        grid[r][c] = new Cell(Cell.Type.OPEN);
                        break;
                }
            }
        }
    }

    public void resetSolution() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell.Type type = grid[r][c].getType();

                if (type == Cell.Type.VISITED || type == Cell.Type.PATH) {
                    grid[r][c].setType(Cell.Type.OPEN);
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public void setStart(int r, int c) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].getType() == Cell.Type.START) {
                    grid[i][j].setType(Cell.Type.OPEN);
                }
            }
        }

        grid[r][c].setType(Cell.Type.START);
        startRow = r;
        startCol = c;
    }

    public void setEnd(int r, int c) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].getType() == Cell.Type.END) {
                    grid[i][j].setType(Cell.Type.OPEN);
                }
            }
        }

        grid[r][c].setType(Cell.Type.END);
        endRow = r;
        endCol = c;
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
}