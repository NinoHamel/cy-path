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

    private List<Player> listWinners;
    private List<Player> listOnGoing;
    private Box[][] box;
    //toString
    /**
     * Display the elements of Board
     * @return String with listWinners,listOnGoing and box
     */
    public String toString() {
        return "Board{" +
                "listWinners=" + listWinners +
                ", listOnGoing=" + listOnGoing +
                ", box=" + Arrays.deepToString(box) +
                '}';
    }
    //Constructor

    /**
     * Creates a 9 by 9 array to represent a grid and a list for 2 players
     *
     * @param player1 is for player 1
     * @param player2 is for player 2
     */
    public Board(Player player1, Player player2) {
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                box[i][j] = new Box(i, j);
            }
        }
        listOnGoing = new ArrayList<>();
        listOnGoing.add(player1);
        listOnGoing.add(player2);

        listWinners = new ArrayList<>();
    }

    /**
     * Creates a 9 by 9 array to represent a grid and a list for 3 players
     *
     * @param player1 is for player 1
     * @param player2 is for player 2
     * @param player3 is for player 3
     */
    public Board(Player player1, Player player2, Player player3) {
        /*
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                box[i][j] = new Box(i, j);
            }
        }
        listOnGoing.add(player1);
        listOnGoing.add(player2);
        listOnGoing.add(player3);
         */

        this(player1, player2);
        listOnGoing.add(player3);
    }
    /**
     * Creates a 9 by 9 array to represent a grid and a list for 4 player
     * @param player1 is for player 1
     * @param player2 is for player 2
     * @param player3 is for player 3
     * @param player4 is for player 4
     */
    public Board(Player player1, Player player2, Player player3, Player player4) {
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                box[i][j] = new Box(i, j);
            }
        }
        listOnGoing.add(player1);
        listOnGoing.add(player2);
        listOnGoing.add(player3);
        listOnGoing.add(player4);
    }
    //Getter
    /**
     * getter of the list of winners
     *
     * @return list of winners from first to last
     */
    public List<Player> getListWinners() {
        return listWinners;
    }
    /**
     * getter of the list of players
     *
     * @return list of players who are still playing
     */
    public List<Player> getListOnGoing() {
        return listOnGoing;
    }
    /**
     * getter of the box
     *
     * @return box
     */
    public Box[][] getBox() {
        return box;
    }
    //Setter
    /**
     *add the winner in listWinners and remove the player from listOnGoing
     * @param player is for the player who won
     */
    public void addPlayerListWinners(Player player) {
        listWinners.add(player);
        removePlayerListOnGoing(player);
    }
    /**
     * remove the player from listOnGoing
     * @param player
     */
    public void removePlayerListOnGoing(Player player) {
        listOnGoing.remove(player);
    }
    //Function

    /**
     * Validate if a wall can be set
     *
     * @param box1 is for first position of the wall
     * @param box2 is for the second position of the wall
     * @return boolean True or False depending on the 2 positions or an error
     */
    public Boolean canSetWall(Box box1, Box box2) throws InvalidWallException {
        if (box1!=box2) {
            if (((box1.getY() == box2.getY() && Math.abs(box1.getX() - box2.getX()) <= 1) && (!box1.hasTopWall() && !box2.hasTopWall())) ||
                    ((box1.getX() == box2.getX() && Math.abs(box1.getY() - box2.getY()) <= 1) && (!box1.hasRightWall() && !box2.hasRightWall()))) {
                return true;
            }
            if (((box1.getY() == box2.getY() && Math.abs(box1.getX() - box2.getX()) > 1) && (!box1.hasTopWall() && !box2.hasTopWall())) ||
                    ((box1.getX() == box2.getX() && Math.abs(box1.getY() - box2.getY()) > 1) && (!box1.hasRightWall() && !box2.hasRightWall()))) {
                throw new InvalidWallException("This wall is not continuous");
            }
        }
        if (box1==box2){
            throw new InvalidWallException("There is only one box selected");
        }
        return false;
    }
    /**
     * Set a wall if possible
     *
     * @param box1 is for the first part of the wall
     * @param box2 is for the second part of the wall
     * @throws InvalidWallException if putting a wall on box1 and box2 isn't possible after verifying with {@link #canSetWall(Box, Box)}
     */
    public void setWall(Box box1, Box box2) throws InvalidWallException {
        if (canSetWall(box1, box2)) {
            //Right wall
            if (box1.getY() == box2.getY()) {
                box1.setRightWall(true);
                box2.setRightWall(true);
            }
            //Left wall
            if (box1.getX() == box2.getX()) {
                box1.setTopWall(true);
                box2.setTopWall(true);
            }
        } else {
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
