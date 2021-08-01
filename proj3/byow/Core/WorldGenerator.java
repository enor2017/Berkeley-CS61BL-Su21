package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

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
    private final double WINDING_PERCENT = 1.0;
    /* the Random object for generating random numbers */
    private Random rand;
    /* Four directions while generating maze */
    private Position[] directions = {new Position(1, 0), new Position(-1, 0),
                                    new Position(0, 1), new Position(0, -1)};

    /**
     * constructor: given width, height and random seed(string)
     * @param width the width of world to generated
     * @param height the height of world to generated
     * @param randomSeed the input random seed to get a Random instance
     */
    public WorldGenerator(int width, int height, String randomSeed) {
        // if height or weight is even, throw exception
        if(height % 2 == 0 || width % 2 == 0) {
            throw new IllegalArgumentException("height and width can only be odd in WorldGenerator");
        }
        this.width = width;
        this.height = height;
        // init map with walls
        map = new int[width][height];
        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                map[i][j] = -1;
            }
        }
        // use hash value as seed
        // TODO: need a hash converter, convert int to long causes collision.
        Long seed = (long) randomSeed.hashCode();
        this.rand = new Random(seed);
    }

    /**
     * given a position, return whether it's a wall
     * @param pos position
     * @return true if map[x][y] == -1
     */
    private boolean isWall(Position pos) {
        return (checkPositionInBound(pos)) && map[pos.getX()][pos.getY()] == -1;
    }

    /**
     * Carve the given cell on the map as areaIndex
     * @param pos cell coordinate on map
     * @param areaIndex which area it belongs
     */
    private void carveCell(Position pos, int areaIndex) {
        map[pos.getX()][pos.getY()] = areaIndex;
    }

    private void growMaze(Position start) {
        Stack<Position> fringe = new Stack<>();

        // In order to avoid to winding maze, we record last direction
        // and follow last direction under some probability
        Position lastDirection = directions[0];

        // new region start
        areaNum++;
        carveCell(start, areaNum);

        // limit iteration times, otherwise will cause infinite loop
         int iterTime = 0;

        fringe.add(start);
        while(!fringe.isEmpty() && iterTime++ <= 1000) {
            Position currentCell = fringe.pop();
            LinkedList<Position> legalDirections = getLegalDirections(currentCell);

            // if no legal directions, randomly choose another 'visited' cell
            if(legalDirections.size() == 0) {
                LinkedList<Position> visitedCells = getAllCarvedCells();
                Position chosenCell;
                // our chosen cell must have ODD coordinates.
                while(true) {
                    chosenCell = visitedCells.get(RandomUtils.uniform(rand, visitedCells.size()));
                    if(chosenCell.getX() % 2 != 0 && chosenCell.getY() % 2 != 0) break;
                }
                fringe.add(chosenCell);
                // System.out.println("chosenCell: " + chosenCell);
                continue;
            }

            // otherwise, choose whether or not to keep original direction
            Position dire;
            if(RandomUtils.uniform(rand) < WINDING_PERCENT) {
                dire = legalDirections.get(RandomUtils.uniform(rand, legalDirections.size()));
                lastDirection = dire;
            } else {
                //TODO: bug: sometimes last direction not valid
                dire = lastDirection;
            }
            // make path
            // System.out.println("current: " + currentCell);
            // System.out.println("dire: " + dire);
            carveCell(currentCell.add(dire), areaNum);
            carveCell(currentCell.add(dire, 2), areaNum);
            // add newly carved cell to fringe
            fringe.add(currentCell.add(dire, 2));
        }
    }

    /**
     * get legal directions while generating maze, given position
     * @param pos current position
     * @return all valid direction at given pos, in a linkedList
     */
    private LinkedList<Position> getLegalDirections(Position pos) {
        LinkedList<Position> legalDirections = new LinkedList<>();
        for(Position dire : directions) {
            // make sure:
            // 1. destination must not open to other area (map[x+2dx][y+2dy] not occupied)
            // 2. destination must end in bounds (map[x+3dx][y+3dy] in bound)
            Position onceDeltaPos = pos.add(dire);
            if(!isWall(onceDeltaPos)) {
                continue;
            }
            Position doubleDeltaPos = pos.add(dire, 2);
            if(!isWall(doubleDeltaPos)) {
                continue;
            }
            Position tripleDeltaPos = pos.add(dire, 3);
            if(!checkPositionInBound(tripleDeltaPos)) {
                continue;
            }

            // reaching here means direction is valid
            legalDirections.add(dire);
        }
        return legalDirections;
    }

    /**
     * check if the given position is in board bound
     * @param pos position to be checked
     * @return true if in bound, false else
     */
    private boolean checkPositionInBound(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    /**
     * return all cells that has been carved, i.e., occupied by an area
     * @return all carved cells in a LinkedList
     */
    private LinkedList<Position> getAllCarvedCells() {
        LinkedList<Position> carvedCells = new LinkedList<>();
        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                Position currentPos = new Position(i, j);
                if(!isWall(currentPos)) carvedCells.add(currentPos);
            }
        }
        return carvedCells;
    }

    /**
     * convert map into TETile map
     * @return a TETile map
     */
    public TETile[][] convertToTile() {
        growMaze(new Position(1, 1));
        System.out.println("Maze successfully generated.");

        TETile[][] tiles = new TETile[width][height];
        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                if(map[i][j] == -1) {
                    tiles[i][j] = Tileset.WALL;
                } else {
                    tiles[i][j] = Tileset.FLOOR;
                }
            }
        }
        return tiles;
    }

    /**
     * main method for test.
     */
    public static void main(String[] args) {
        WorldGenerator g = new WorldGenerator(49, 25, "cs61BL");
        TERenderer ter = new TERenderer();
        ter.initialize(49, 25);

        ter.renderFrame(g.convertToTile());
    }
}
