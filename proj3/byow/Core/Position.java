package byow.Core;

/**
 * A position class, for storing a point/grid
 */
public class Position {
    private int x, y;

    /**
     * Construct a position given x and y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A copy constructor given another position p
     * @param p another position to copy from
     */
    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Get the x-coordinate of this position
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of this position
     */
    public int getY() {
        return y;
    }

    /**
     * Move current position with delta position
     * @param delta delta position, including dx and dy
     * @return new position after movement
     */
    public Position add(Position delta) {
        return new Position(this.x + delta.x, this.y + delta.y);
    }

    /**
     * Move current position with delta position for rep times
     * @param delta delta position, including dx and dy
     * @param rep move for rep times
     * @return new position after movement
     */
    public Position add(Position delta, int rep) {
        return new Position(this.x + delta.x * rep, this.y + delta.y * rep);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
