package PlusWorld;

import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

public class Point {
    // (x, y) is cell coordinate, should be int
    private int x, y;
    // default tile type
    private TETile tileType = Tileset.WALL;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, TETile type) {
        this.x = x;
        this.y = y;
        this.tileType = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /* Fill the corresponding point in tiles to current type */
    public void drawPoint(TETile[][] tiles) {
        tiles[x][y] = tileType;
    }
}
