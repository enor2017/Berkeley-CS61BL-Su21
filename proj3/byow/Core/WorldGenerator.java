package byow.Core;

/**
 * This is the class for generating a world, with steps below:
 * 1. generate a perfect maze, using dfs
 * 2. spareness: destroy some dead-ends on perfect maze by making them to wall
 * 3. connection: destroy some dead-ends but by connecting it to another corridor
 * 4. place room: make sure room overlaps with some rooms/corridors but not to many
 *
 * The width/height of world must be odd, and coordinates we place rooms/corridors
 * must be odd, to leave place for walls. (make it aligned)
 *
 * @source: http://www.brainycode.com/downloads/RandomDungeonGenerator.pdf
 * @source: http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
 *
 * The thought is basically the same as the 1st source but have some simplification
 * while placing the room. The 2nd source provides the idea that make world/coordinates
 * odd to leave place for walls.
 */
public class WorldGenerator {
    /* a counter for the number of areas, area # starts from 0 */
    private int areaNum = -1;
    /* the map used when generating the world, -1 for wall, others for area */
    private int[][] map;
    /* the width and height of map, make sure it's odd */
    private int width, height;
    /* a factor deciding winding percent of maze, used while generating perfect maze */
    private final double WINDING_PERCENT = 0.3;

    /**
     * constructor: given height and weight
     * @param width the width of world to generated
     * @param height the height of world to generated
     */
    public WorldGenerator(int width, int height) {
        // if height or weight is even, throw exception
        if(height % 2 == 0 || width % 2 == 0) {
            throw new IllegalArgumentException("height and width can only be odd in WorldGenerator");
        }
        this.width = width;
        this.height = height;
        map = new int[width][height];
    }

    /**
     * given a coordinate, return whether it's a wall
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if map[x][y] == -1
     */
    private boolean isWall(int x, int y) {
        return map[x][y] == -1;
    }


}
