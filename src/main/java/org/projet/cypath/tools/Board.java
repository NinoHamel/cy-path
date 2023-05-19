package org.projet.cypath.tools;
import javafx.geometry.Pos;
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
    public Board() throws OutOfBoardException {
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Position position=new Position(i,j);
                box[i][j] = new Box(position);
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
    public boolean onBoard(Position position){
        return (position.getX()>=0 && position.getX()<=8 && position.getY()>=0 && position.getY()<=8);
    }
    public boolean onBoard(int x,int y){
        return (x>=0 && x<=8 && y>=0 && y<=8);
    }

    /**
     *Give a list of box, with every possible move for a player
     * @param player is the player who wants to move
     * @return possibleMove a list of box where the player can move to
     * @throws OutOfBoardException
     */
    public List<Box> possibleMove(Player player) throws OutOfBoardException {
        List<Box> possibleMove;
        possibleMove = new ArrayList<>();
        Position positionPlayer=player.getPosition();
        Box boxPlayer=box[positionPlayer.getX()-1][positionPlayer.getY()-1];
        //Déplacement bas
        Position positionBottom=new Position(positionPlayer.getX()+1,positionPlayer.getY());
        Box boxBottom=new Box(positionBottom);
        //Déplacement haut
        Position positionTop=new Position(positionPlayer.getX()-1,positionPlayer.getY());
        Box boxTop=new Box(positionTop);
        //Déplacement droite
        Position positionRight=new Position(positionPlayer.getX(),positionPlayer.getY()+1);
        Box boxRight=new Box(positionRight);
        //Déplacement gauche
        Position positionLeft=new Position(positionPlayer.getX(),positionPlayer.getY()-1);
        Box boxLeft=new Box(positionLeft);
        if (onBoard(positionRight) && !boxPlayer.hasRightWall()){
            if (!boxRight.hasPlayer()){
                possibleMove.add(boxRight);
            }
            else{
                possibleMoveJump(player,boxRight,possibleMove);
            }
        }
        if (onBoard(positionLeft) && !boxPlayer.hasLeftWall()){
            if (!boxLeft.hasPlayer()){
                possibleMove.add(boxLeft);
            }
            else{
                possibleMoveJump(player,boxLeft,possibleMove);
            }
        }
        if (onBoard(positionTop) && !boxPlayer.hasTopWall()){
            if (!boxTop.hasPlayer()){
                possibleMove.add(boxTop);
            }
            else{
                possibleMoveJump(player,boxTop,possibleMove);
            }
        }
        if (onBoard(positionBottom) && !boxPlayer.hasBottomWall()){
            if (!boxBottom.hasPlayer()){
                possibleMove.add(boxBottom);
            }
            else{
                possibleMoveJump(player,boxBottom,possibleMove);
            }
        }
        return possibleMove;
    }
    /**
     * This method is used in {@link #possibleMove(Player)} to give differents move possible if there is 2 players next to each other.
     * @param player is the player who wants to move
     * @param newBox is the box where another player is
     * @param possibleMove is
     * @throws OutOfBoardException
     */
    public void possibleMoveJump(Player player,Box newBox,List<Box> possibleMove) throws OutOfBoardException {
        Position positionPlayer=player.getPosition();
        //Déplacement bas
        Position newPositionBottom=new Position(newBox.getX()+1,newBox.getY());
        Box newBoxBottom=new Box(newPositionBottom);
        //Déplacement haut
        Position newPositionTop=new Position(newBox.getX()-1,newBox.getY());
        Box newBoxTop=new Box(newPositionTop);
        //Déplacement droite
        Position newPositionRight=new Position(newBox.getX(),newBox.getY()+1);
        Box newBoxRight=new Box(newPositionRight);
        //Déplacement gauche
        Position newPositionLeft=new Position(newBox.getX(),newBox.getY()-1);
        Box newBoxLeft=new Box(newPositionLeft);
        if (onBoard(newPositionRight) && !newBox.hasRightWall() && newPositionRight.equal(positionPlayer)){
            possibleMove.add(newBoxRight);
        }
        if (onBoard(newPositionRight) && !newBox.hasRightWall() && newPositionRight.equal(positionPlayer)){
            possibleMove.add(newBoxLeft);
        }
        if (onBoard(newPositionRight) && !newBox.hasRightWall() && newPositionRight.equal(positionPlayer)){
            possibleMove.add(newBoxTop);
        }
        if (onBoard(newPositionRight) && !newBox.hasRightWall() && newPositionRight.equal(positionPlayer)){
            possibleMove.add(newBoxBottom);
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

    /**
     * Check if there is a path between two positions
     * @param player the player
     * @return boolean True or False depending on the 2 positions
     */
    /*
    public boolean hasPath(Player player) throws OutOfBoardException{
        Position winPositionArray[] = new Position[9];
        //if(player.get)
        int directions[][] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // droite, gauche, bas, haut
        int saveVisitedBoxArray[][] = new int[9][9];
        Queue<Position> queue = new LinkedList<>();
        queue.add(player.getPosition());
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
     */
}
