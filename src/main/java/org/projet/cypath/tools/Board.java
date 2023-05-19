package org.projet.cypath.tools;
import org.projet.cypath.exceptions.InvalidWallException;
import org.projet.cypath.exceptions.OutOfBoardException;

/**
 * Board is a class which creates a grid of 9 by 9 boxes and list of players.
 * It shall be used to know how many players are still playing, who won and if a move is valid or not.
 *
 * @author Côme QUINTYN
 * @version 1.0
 * @see Box
 */
public class Board {
    private Box[][] box;
    //Constructor

    /**
     * Creates a 9 by 9 array to represent a grid
     */
    public Board() throws OutOfBoardException {
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                box[i][j] = new Box(i,j);
            }
        }
    }

    public Box[][] getBox() {
        return box;
    }
    //Setter
    /**
     * Return if a coordinate is in the board or not
     * @param x coordinate
     * @param y coordinate
     * @return
     */
    public boolean onBoard(int x,int y){
        return (x>=0 && x<=8 && y>=0 && y<=8);
    }
    /**
     * Return a box from board using 2 int
     * @param x
     * @param y
     * @return box of the 2 int
     * @throws OutOfBoardException
     */
    //Setter
    public Box getBox(int x,int y) throws OutOfBoardException {
        if (onBoard(x,y)) {
            return box[x][y];
        }
        throw new OutOfBoardException("donnes pas dans le tableau");
    }
    /**
     * Validate if a wall can be set
     *
     * @param box is for first position of the wall
     * @return boolean True or False depending on the 2 positions or an error
     */
    public Boolean canSetWall(Box box) throws InvalidWallException {
        if (box.getRow() < 0 || box.getColumn() < 0){
            throw new InvalidWallException("The wall can only be put in positive coordinates.");
        }
        else if (box.getRow() > 7 || box.getColumn() >7){
            throw new InvalidWallException("The wall coordinates can only be lesser than 8");
        }
        else return true;
    }
    /**
     * Set a wall on the bottom or in the right if possible
     *
     * @param box is for the 1st box
     * @throws InvalidWallException if putting a wall on box isn't possible after verifying with {@link #canSetWall(Box)}
     */
    public void setBottomWall(Box box) throws InvalidWallException {
        if (canSetWall(box)) {
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setBottomWall(true);
            this.box[x][y+1].setBottomWall(true);
            this.box[x+1][y].setTopWall(true);
            this.box[x+1][y+1].setTopWall(true);
        }
        else {
            throw new InvalidWallException("A wall is already here");
        }
    }
    public void setRightWall(Box box) throws InvalidWallException {
        if (canSetWall(box)) {
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setRightWall(true);
            this.box[x+1][y].setRightWall(true);
            this.box[x][y+1].setLeftWall(true);
            this.box[x+1][y+1].setLeftWall(true);
        }
        else {
            throw new InvalidWallException("A wall is already here");
        }
    }
}
