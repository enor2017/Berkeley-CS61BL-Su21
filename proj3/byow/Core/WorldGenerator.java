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
 * 3. place room: make sure room overlaps with some rooms/corridors but not to many
 *
 * The width/height of world must be odd, and coordinates we place rooms/corridors
 * must be odd, to leave place for walls. (make it aligned)
 *
 * @source: http://www.brainycode.com/downloads/RandomDungeonGenerator.pdf
 * @source: http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
 *
 * The thought is basically the same as the 1st source but have some simplification
 * while placing the room. The 2nd source provides the idea that make world/coordinates
 * odd to leave place for walls, as well as the excellent winding factor idea.
 */
public class WorldGenerator {
    /* a counter for the number of areas, area # starts from 0
      Actually corridors are labelled as 0, while rooms are labelled from 1. */
    private int areaNum = -1;
    /* the map used when generating the world, -2 for nothing, -1 for wall,
        -3 for avatar, others for area */
    private int[][] map;
    /* the width and height of map, make sure it's odd */
    private int width, height;
    /* a factor deciding winding percent of maze, used while generating perfect maze */
    private final double WINDING_PERCENT = 0.6;
    /* the Random object for generating random numbers */
    private Random rand;
    /* Four directions while generating maze */
    private final Position[] directions = {new Position(1, 0), new Position(-1, 0),
                                            new Position(0, 1), new Position(0, -1)};
    /* Eight directions while removing redundant walls */
    private final Position[] FullDirections = {new Position(1, 0), new Position(-1, 0),
            new Position(0, 1), new Position(0, -1),
            new Position(1, 1), new Position(1, -1),
            new Position(-1, 1), new Position(-1, -1)};
    /* How many times will we perform spareness
    * Before and After generating room, [0]:before, [1]:after */
    private final int SPARE_FACTOR[] = new int[]{450, 150};
    /* randomly choose how many rooms will we place */
    private int roomNum;
    /* max cell toleration can a room overlap with previous objects */
    private final int MAX_OVERLAP = 8;
    /* record avatar's position */
    private Position avatar = null;

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
        // choose how many rooms to generate: [13, 16)
        roomNum = RandomUtils.uniform(rand, 13, 16);
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
     * given a position, return whether it's a floor
     * @param pos position
     * @return true if map[x][y] == -1
     */
    private boolean isFloor(Position pos) {
        return (checkPositionInBound(pos)) && map[pos.getX()][pos.getY()] >= 0;
    }

    /**
     * Carve the given cell on the map as areaIndex
     * @param pos cell coordinate on map
     * @param areaIndex which area it belongs
     */
    private void carveCell(Position pos, int areaIndex) {
        map[pos.getX()][pos.getY()] = areaIndex;
    }

    /* ======================================================== */
    /* ===============  Part I: Maze Generator  =============== */
    /* ======================================================== */
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
            // keep if rand > WINDING_PERCENT, and last direction is valid
            Position dire;
            if(RandomUtils.uniform(rand) > WINDING_PERCENT && contains(legalDirections, lastDirection)) {
                dire = lastDirection;
            } else {
                dire = legalDirections.get(RandomUtils.uniform(rand, legalDirections.size()));
                lastDirection = dire;
            }
            // make path
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
     * Used in generating maze, check whether last direction in valid directions
     * @param list given list
     * @param dire direction
     * @return true if direction in given list
     */
    private boolean contains(LinkedList<Position> list, Position dire) {
        for(Position pos : list) {
            if(dire.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    /* ========================================================= */
    /* ===============  Part II: Spare Dead-End  =============== */
    /* ========================================================= */
    /**
     * perform spareness to delete some dead-ends
     * delete exactly SPARE_FACTOR cells.
     * @param stage 0 if before and 1 if after generating room
     */
    private void spareness(int stage) {
        int spareCount = 0;
        // don't iterator over 1000 times: avoid infinite loop
        int iterTime = 0;
        while(true && iterTime++ < 1000) {
            for(int i = 0; i < width; ++i) {
                for(int j = 0; j < height; ++j) {
                    Position currentPos = new Position(i, j);
                    // if this is dead end, carve back to wall
                    if(isDeadEnd(currentPos)) {
                        carveCell(currentPos, -1);
                        spareCount++;
                    }
                    // if reach spare limit, terminate
                    if(spareCount == SPARE_FACTOR[stage]) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * helper function: check if given pos is a dead-end
     * @param pos the pos to check
     * @return true if given pos is a dead-end
     */
    private boolean isDeadEnd(Position pos) {
        // pos is dead-end iff itself is not wall, and it's surrounded by three walls
        if(isWall(pos)) {
            return false;
        }
        int countSurrounding = 0;
        for(Position dire : directions) {
            Position neighbour = pos.add(dire);
            if(isWall(neighbour)) {
                countSurrounding++;
            }
        }
        return countSurrounding == 3;
    }

    /* ======================================================= */
    /* ===============  Part III: Place Rooms  =============== */
    /* ======================================================= */
    /**
     * place ROOM_NUM rooms on the board, follow the algorithm:
     * 1. generate a rectangle that is not too thin/tall
     * 2. randomly choose locations on the board, find somewhere that
     *  (1) has overlap with previous rooms/corridors
     *  (2) but not overlap too much
     */
    private void placeRooms() {
        // add a loop time limit, to avoid infinite loop
        int loopTime = 0;
        for(int roomCount = 0; roomCount < roomNum && loopTime <= 4000; ++roomCount, ++loopTime) {
            // randomly generate a room size (not too thin/tall)
            // (1) start from a square with ODD size
            // (2) add either width or height an even length
            int squareLength = RandomUtils.uniform(rand, 2, 4) * 2 + 1;
            int deltaLength = RandomUtils.uniform(rand, 0, 1 + squareLength / 2) * 2;
            int roomWidth = squareLength;
            int roomHeight = squareLength;
            if(RandomUtils.uniform(rand) < 0.5) {
                roomWidth += deltaLength;
            } else {
                roomHeight += deltaLength;
            }

            // randomly choose a cell to put room's bottomLeft corner
            // the cell must have ODD coordinates
            // if 1 <= overlap <= MAX_OVERLAP, carve it in the map
            // again, avoid infinite loop
            int loopTime2 = 0;
            while(true && loopTime2++ <= 2000) {
                int roomX = RandomUtils.uniform(rand, (width - roomWidth) / 2 - 1) * 2 + 1;
                int roomY = RandomUtils.uniform(rand, (height - roomHeight) / 2 - 1) * 2 + 1;
                Position bottomLeftPos = new Position(roomX, roomY);
                int overlapCnt = overlapCount(bottomLeftPos, roomWidth, roomHeight);
                if(overlapCnt >= 1 && overlapCnt <= MAX_OVERLAP) {
                    // don't forget to setup a new area
                    ++areaNum;
                    carveRoom(bottomLeftPos, roomWidth, roomHeight, areaNum);
                    break;
                }
            }
        }
    }

    /**
     * count how many cells does the given room overlap with previous objects
     * @param bottomLeft the bottom-left coordinate of given room
     * @param width room width
     * @param height room height
     * @return num of cells overlapped
     */
    private int overlapCount(Position bottomLeft, int width, int height) {
        int count = 0;
        for(int dx = 0; dx < width; ++dx) {
            for(int dy = 0; dy < height; ++dy) {
                Position delta = new Position(dx, dy);
                Position newPos = bottomLeft.add(delta);
                // if the position is not wall, which means it's occupied
                // counter += 1
                if(!isWall(newPos)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * carve the given room on the map we generated, with a new areaIndex
     * @param bottomLeft the bottom-left coordinate of given room
     * @param width room width
     * @param height room height
     * @param areaIndex area index of the room to carve on map
     */
    private void carveRoom(Position bottomLeft, int width, int height, int areaIndex) {
        for(int dx = 0; dx < width; ++dx) {
            for(int dy = 0; dy < height; ++dy) {
                Position delta = new Position(dx, dy);
                Position newPos = bottomLeft.add(delta);
                carveCell(newPos, areaIndex);
            }
        }
    }

    /* =================================================================== */
    /* ===============  Part IV: Remove unnecessary walls  =============== */
    /* =================================================================== */

    /**
     * Remove unnecessary walls in the map
     * for the sake of simplicity, remove walls who has four wall neighbours
     */
    private void removeUnnecessaryWalls() {
        // store positions that are to removed
        Stack<Position> toRemove = new Stack<>();

        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                Position pos = new Position(i, j);
                // loop 8 directions, including diagonals
                int countWallNeighbours = 0;
                for(Position dire : FullDirections) {
                    Position neighbour = pos.add(dire);
                    // count neighbours that: out-of bound, or is wall
                    if(!checkPositionInBound(neighbour) || isWall(neighbour)) {
                        countWallNeighbours++;
                    }
                }
                // uncarve the cell iff has 8 wall neighbours or out-of-bound neighbours
                // notice that we cannot flag those cells on original map!
                if(countWallNeighbours == 8) {
                    toRemove.add(pos);
                }
            }
        }

        // here we pop cells and mark on original map
        while(!toRemove.isEmpty()) {
            Position pos = toRemove.pop();
            map[pos.getX()][pos.getY()] = -2;
        }
    }

    /* ================================================================ */
    /* ===============  Part V: Place avatar in a room  =============== */
    /* ================================================================ */

    /**
     * Place the avatar at a proper position
     */
    public void placeAvatar() {
        // keep generating random positions
        while(true) {
            // try to place avatar in the middle area
            int x = RandomUtils.uniform(rand, width / 6, width * 5 / 6);
            int y = RandomUtils.uniform(rand, height / 6, height * 5 / 6);
            Position pos = new Position(x, y);
            // continue to check only if current position is floor
            if(!isFloor(pos)) {
                continue;
            }
            boolean okPlace = true;
            // place it if four neighbours are all floor
            for(Position dire : directions) {
                Position neighbour = pos.add(dire);
                // if neighbour out of bound or is not floor, break.
                if(!checkPositionInBound(neighbour) || !isFloor(neighbour)) {
                    okPlace = false;
                    break;
                }
            }
            // good place? record it and return
            if(okPlace) {
                map[x][y] = -3;
                avatar = pos;
                return;
            }
        }
    }

    /**
     * get avatar's position
     * @return avatar's position, with type Position
     */
    public Position getAvatarPos() {
        return avatar;
    }

    /* ================================================ */
    /* ===============  Test Functions  =============== */
    /* ================================================ */
    /**
     * convert map into TETile map
     * @return a TETile map
     */
    public TETile[][] convertToTile() {
        growMaze(new Position(1, 1));
        // System.out.println("Maze successfully generated.");
        spareness(0);
        // System.out.println("Maze successfully spared.");
        placeRooms();
        // System.out.println("Room successfully generated.");
        spareness(1);
        // System.out.println("Maze successfully spared AFTER generating room.");
        removeUnnecessaryWalls();
        // System.out.println("Unnecessary Walls successfully removed.");
        placeAvatar();
        // System.out.println("Avatar successfully placed.");

        TETile[][] tiles = new TETile[width][height];
        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                if(map[i][j] == -2) {
                    tiles[i][j] = Tileset.NOTHING;
                } else if(map[i][j] == -1) {
                    tiles[i][j] = Tileset.WALL;
                } else if (map[i][j] == -3){
                    tiles[i][j] = Tileset.AVATAR;
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
        WorldGenerator g = new WorldGenerator(59, 41, "cs61bl");
        TERenderer ter = new TERenderer();
        ter.initialize(59, 41);

        ter.renderFrame(g.convertToTile());
    }
}
