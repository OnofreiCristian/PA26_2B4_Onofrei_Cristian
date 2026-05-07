package maze_logic;

import actors.Entity;

public class Cell {
    private final int x;
    private final int y;
    private boolean isWall;
    private Entity currentOccupant;

    public Cell(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.currentOccupant = null;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isWall() { return isWall; }
    public void setWall(boolean wall) { isWall = wall; }

    public Entity getCurrentOccupant() { return currentOccupant; }
    public void setCurrentOccupant(Entity occupant) { this.currentOccupant = occupant; }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}