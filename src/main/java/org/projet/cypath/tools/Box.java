package org.projet.cypath.tools;

import org.projet.cypath.exceptions.OutOfBoardException;

/**
 * Box is a class which represents a box on the board.
 * It contains information about the box's position and walls.
 * It can also indicate if a player is present in the box.
 * @author Côme QUINTYN
 * @version 1.0
 * @see Board
 */
public class Box {

    private int row;
    private int column;
    private boolean OriginVerticalWall;
    private boolean OriginHorizontalWall;
    private boolean leftWall;
    private boolean rightWall;
    private boolean topWall;
    private boolean bottomWall;
    private boolean player;
    /**
     * Constructs a Box object with the specified coordinates.
     *
     * @param row the x-coordinate of the box
     * @param column the y-coordinate of the box
     * @throws OutOfBoardException if the coordinates are not valid
     */
    public Box(int row, int column) throws OutOfBoardException {
        this.row = row;
        this.column = column;
    }

    /**
     * Checks if the box has a vertical origin wall.
     *
     * @return true if the box has a vertical origin wall, false otherwise
     */
    public boolean hasOriginVerticalWall() {
        return OriginVerticalWall;
    }

    /**
     * Sets the presence of a vertical origin wall in the box.
     *
     * @param OriginVerticalWall true to set a vertical origin wall, false otherwise
     */
    public void setOriginVerticalWall(boolean OriginVerticalWall) {
        this.OriginVerticalWall = OriginVerticalWall;
    }



    /**
     * Checks if the box has a horizontal origin wall.
     *
     * @return true if the box has a horizontal origin wall, false otherwise
     */
    public boolean hasOriginHorizontalWall() {
        return OriginHorizontalWall;
    }

    /**
     * Sets the presence of a vertical origin wall in the box.
     *
     * @param OriginHorizontalWall true to set a vertical origin wall, false otherwise
     */
    public void setOriginHorizontalWall(boolean OriginHorizontalWall) {
        this.OriginHorizontalWall = OriginHorizontalWall;
    }



    /**
     * Checks if the box has a left wall.
     *
     * @return true if the box has a left wall, false otherwise
     */
    public boolean hasLeftWall() {
        return leftWall;
    }

    /**
     * Sets the presence of a left wall in the box.
     *
     * @param leftWall true to set a left wall, false otherwise
     */
    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    /**
     * Checks if the box has a right wall.
     *
     * @return true if the box has a right wall, false otherwise
     */
    public boolean hasRightWall() {
        return rightWall;
    }

    /**
     * Sets the presence of a right wall in the box.
     *
     * @param rightWall true to set a right wall, false otherwise
     */
    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    /**
     * Checks if the box has a top wall.
     *
     * @return true if the box has a top wall, false otherwise
     */
    public boolean hasTopWall() {
        return topWall;
    }

    /**
     * Sets the presence of a top wall in the box.
     *
     * @param topWall true to set a top wall, false otherwise
     */
    public void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    /**
     * Checks if the box has a bottom wall.
     *
     * @return true if the box has a bottom wall, false otherwise
     */
    public boolean hasBottomWall() {
        return bottomWall;
    }

    /**
     * Sets the presence of a bottom wall in the box.
     *
     * @param bottomWall true to set a bottom wall, false otherwise
     */
    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    /**
     * Gets the row-coordinate of the box.
     *
     * @return the row-coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column-coordinate of the box.
     *
     * @return the column-coordinate
     */
    public int getColumn() {
        return column;
    }


    /**
     * Checks if the box has a player.
     *
     * @return true if the box has a player, false otherwise
     */
    public boolean hasPlayer() {
        return player;
    }

    /**
     * Sets the presence of a player in the box.
     *
     * @param hasPlayer true to set a player in the box, false otherwise
     */
    public void setHasPlayer(boolean hasPlayer) {
        this.player = hasPlayer;
    }

    /**
     * Returns a string representation of the Box object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Box{" +
                ", row=" + row +
                ", column=" + column +
                ", OriginHorizontalWall=" + OriginHorizontalWall +
                ", OriginVerticalWall=" + OriginVerticalWall +
                ", leftWall=" + leftWall +
                ", rightWall=" + rightWall +
                ", topWall=" + topWall +
                ", bottomWall=" + bottomWall +
                '}';
    }
}