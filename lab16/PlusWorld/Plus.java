package PlusWorld;

import byowTools.TileEngine.TETile;

public class Plus {
    // the coordinate of left-top cell
    Point leftTop;
    // the size of the plus sign
    int size;
    // the tileType of plus sign
    TETile tileType;

    public Plus(Point leftTop, int size, TETile tileType) {
        this.leftTop = leftTop;
        this.size = size;
        this.tileType = tileType;
    }

    /* Draw a rectangle given left-top and right-bottom corner, and type */
    private void drawRectangle(Point leftTop, Point rightBottom, TETile tileType, TETile[][] tiles) {
        for(int i = leftTop.getX(); i <= rightBottom.getX(); ++i) {
            for(int j = leftTop.getY(); j <= rightBottom.getY(); ++j) {
                Point newPoint = new Point(i, j, tileType);
                newPoint.drawPoint(tiles);
            }
        }
    }

    /* Draw this plus sign on tiles[][] */
    public void drawPlus(TETile[][] tiles) {
        // for vertical rectangle
        Point rightBottom = new Point(leftTop.getX() + size - 1, leftTop.getY() + 3 * size - 1);
        drawRectangle(leftTop, rightBottom, tileType, tiles);

        // for horizontal rectangle
        Point left = new Point(leftTop.getX() - size, leftTop.getY() + size);
        Point right = new Point(left.getX() + 3 * size - 1, left.getY() + size - 1);
        drawRectangle(left, right, tileType, tiles);
    }
}
