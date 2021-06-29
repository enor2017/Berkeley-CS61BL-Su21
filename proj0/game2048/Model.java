package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author enor2017
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        // since 'score' inside board's constructor is useless, we pass -1 here
        board = new Board(rawValues, -1);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     *
     * @author: enor2017
     * */

    /**
     *  Below are comments by enor2017.
     *
     *  The board looks like:
     *  (0,3) (1,3) (2,3) (3,3)
     *  (0,2) (1,2) (2,2) (3,2)
     *  (0,1) (1,1) (2,1) (3,1)
     *  (0,0) (1,0) (2,0) (3,0)
     */

    /**
     * helper function:
     * go from up to bottom, return the top empty cell above (row, col)
     * if does not exist, return -1
     */
    private int top_space(int col, int row){
        int size = board.size();
        for(int i = size - 1; i > row; --i){
            if(board.tile(col, i) == null){
                return i;
            }
        }
        return -1;
    }

    /**
     * helper function to tilt one column
     * regard side == NORTH
     *
     * 1. Deal with tile movement
     * 2. Deal with tile merge
     * 3. Update score
     * 4. Declare change
     */
    private boolean tilt_col(int col){
        boolean changed = false;
        int size = board.size();

        // record whether cell can be merged.
        // default value for boolean is <false>.
        boolean[] forbid_merge = new boolean[4];

        for(int i = size - 2; i >= 0; --i){
            // current cell: (col, i)
            Tile current_cell = board.tile(col, i);

            // 1. If current cell is null, do nothing.
            if(current_cell == null) continue;

            // 2. find if there is any blank space
            int blank_space = top_space(col, i);
            if(blank_space != -1){
                // find if it can be merged with upper cell AFTER
                // move to that blank space
                Tile upperTileAfterMove = null;
                if(blank_space + 1 < size) {
                    upperTileAfterMove = board.tile(col, blank_space + 1);
                }
                if(upperTileAfterMove != null
                        && current_cell.value() == upperTileAfterMove.value()
                        && (!forbid_merge[blank_space + 1])){
                    // merge!
                    board.move(col, blank_space + 1, current_cell);
                    // update score
                    score += (current_cell.value() * 2);
                    // cannot be merged again
                    forbid_merge[blank_space + 1] = true;
                }else{
                    // can't be merged, so we just move
                    board.move(col, blank_space, current_cell);
                    // No score update.
                }

                // declare change
                changed = true;

                continue;
            }

            // 3. Otherwise, find if it can be merged with upper cell
            Tile upper_cell = null;
            // if doesn't have upper_cell, or upper_cell is empty, continue
            if(i <= size - 2) {
                upper_cell = board.tile(col, i + 1);
            }
            if(upper_cell == null){
                continue;
            }
            // now that have upper cell, check whether merge is available
            if((current_cell.value() == upper_cell.value()) && (!forbid_merge[i + 1])){
                int original_value = current_cell.value();
                // merge!
                board.move(col, i + 1, current_cell);
                // declare change
                changed = true;
                // update score
                score += (original_value * 2);
                // cannot be merged again
                forbid_merge[i + 1] = true;

                continue;
            }

            // 4. If cannot be moved nor merged, do nothing.
        }
        return changed;
    }
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        board.setViewingPerspective(side);

        int size = board.size();
        for(int i = 0; i < size; ++i){
            boolean col_changed = tilt_col(i);
            if(col_changed) changed = true;
        }

        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     *
     *  @author: enor2017
     * */
    public static boolean emptySpaceExists(Board b) {
        int board_size = b.size();
        for(int i = 0; i < board_size; ++i){
            for(int j = 0; j < board_size; ++j){
                if(b.tile(i, j) == null) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     *
     * @author: enor2017
     */
    public static boolean maxTileExists(Board b) {
        int board_size = b.size();
        for(int i = 0; i < board_size; ++i){
            for(int j = 0; j < board_size; ++j){
                if(b.tile(i, j) == null) continue;
                if(b.tile(i, j).value() == MAX_PIECE) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     *
     * @author: enor2017
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // condition 1: exists blank space
        if(emptySpaceExists(b)) return true;

        // condition 2: can be merged
        /// 1. check up
        int board_size = b.size();
        for(int i = 0; i < board_size; ++i){
            for(int j = 0; j < board_size - 1; ++j){
                /// no need to check null
                if(b.tile(i, j).value() == b.tile(i, j + 1).value()) return true;
            }
        }
        /// 2. check right
        for(int i = 0; i < board_size - 1; ++i){
            for(int j = 0; j < board_size; ++j){
                if(b.tile(i, j).value() == b.tile(i + 1, j).value()) return true;
            }
        }

        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
