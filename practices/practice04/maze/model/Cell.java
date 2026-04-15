package maze.model;

public class Cell {

    public enum Type {
        OPEN,
        WALL,
        START,
        END,
        VISITED,
        PATH
    }

    private Type type;

    public Cell(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return type != Type.WALL;
    }
}