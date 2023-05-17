package org.projet.cypath.tools;
import org.projet.cypath.tools.Box;
import org.projet.cypath.exceptions.InvalidWallException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;
import org.projet.cypath.tools.Position;

import java.util.*;

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
    public Board() {
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                box[i][j] = new Box(i, j);
            }
        }
    }

    public Box[][] getBox() {
        return box;
    }
    //Setter


    /**
     * Validate if a wall can be set
     *
     * @param box is for first position of the wall
     * @return boolean True or False depending on the 2 positions or an error
     */

    public Boolean canSetWall(Box box) throws InvalidWallException {
        if (box.getX() < 0 || box.getY() < 0){
            throw new InvalidWallException("The wall can only be put in positive coordinates.");
        }
        else if (box.getX() > 7 || box.getY() >7){
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
            int x=box.getX();
            int y=box.getY();
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
            int x=box.getX();
            int y=box.getY();
            this.box[x][y].setRightWall(true);
            this.box[x+1][y].setRightWall(true);
            this.box[x][y+1].setLeftWall(true);
            this.box[x+1][y+1].setLeftWall(true);
        }
        else {
            throw new InvalidWallException("A wall is already here");
        }
    }
    /**
     * Validate if a  move from a position  to another is possible or not
     * @param position1 is for the player who wants to move
     * @param position2 is for the end position
     * @return boolean True or False depending on the 2 positions or OutOfBoardException if positions are not valid
     */
    public boolean canMove(Position position1, Position position2) throws Exception {
        if (position1.getX() <= 0 || position1.getY() <= 0 || position2.getX() <= 0 || position2.getY() <= 0) {
            throw new OutOfBoardException("The grid has only positive coordinates.");
        }
        else if (position1.getX() > 9 || position1.getY() > 9 || position2.getX() > 9 || position2.getY() > 9) {
            throw new OutOfBoardException("The grid has only coordinates greater than 9.");
        }
        else {
            return (position1.getY() == position2.getY() && Math.abs(position1.getX() - position2.getX()) <= 1) ||
                    (position1.getX() == position2.getX() && Math.abs(position1.getY() - position2.getY()) <= 1);
        }
    }
    /**
     * Validate if a player move is possible or not
     * @param player is for the player who wants to move
     * @param position2 is for the end position
     * @return boolean True or False depending on the 2 positions
     */
    public boolean canMove(Player player, Position position2) throws Exception {
        Position position1=player.getPosition();
        if (position1.getX() <= 0 || position1.getY() <= 0 || position2.getX() <= 0 || position2.getY() <= 0) {
            throw new OutOfBoardException("The grid has only positive coordinates.");
        }
        else if (position1.getX() > 9 || position1.getY() > 9 || position2.getX() > 9 || position2.getY() > 9) {
            throw new OutOfBoardException("The grid has only coordinates greater than 9.");
        }
        else {
            return (position1.getY() == position2.getY() && Math.abs(position1.getX() - position2.getX()) <= 1) ||
                    (position1.getX() == position2.getX() && Math.abs(position1.getY() - position2.getY()) <= 1);
        }
    }

    public boolean hasPath(Position start, Position end) throws OutOfBoardException{
        int directions[][] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // droite, gauche, bas, haut
        int saveVisitedBoxArray[][] = new int[9][9];
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        boolean hasWall = false;

        while(queue.size() > 0){
            Position treatedPosition = queue.peek();
            queue.remove();
            saveVisitedBoxArray[treatedPosition.getX()][treatedPosition.getY()] = -1;
            if(treatedPosition.getX() == end.getX() && treatedPosition.getY() == end.getY()){
                return true;
            }
            for(int i=0; i < 4; i++){
                int newPositionRow = treatedPosition.getX() + directions[i][0];
                int newPositionColumn = treatedPosition.getY() + directions[i][1];

                if(newPositionRow >= 0 && newPositionColumn >= 0 && newPositionRow < 9 && newPositionColumn < 9 && saveVisitedBoxArray[newPositionRow][newPositionColumn] != -1){
                    switch (i){
                        case 0:
                            hasWall = box[newPositionRow][newPositionColumn].hasRightWall();
                            break;
                        case 1:
                            hasWall = box[newPositionRow][newPositionColumn].hasLeftWall();
                            break;
                        case 2:
                            hasWall = box[newPositionRow][newPositionColumn].hasBottomWall();
                            break;
                        case 3:
                            hasWall = box[newPositionRow][newPositionColumn].hasTopWall();
                            break;
                        default:
                            throw new RuntimeException("Loop error");
                    }
                    if(!hasWall){
                        if(treatedPosition.getX() == end.getX() && treatedPosition.getY() == end.getY()){
                            return true;
                        }
                        queue.add(new Position(newPositionRow, newPositionColumn));
                    }
                }

            }
        }
        return false;
    }
}
