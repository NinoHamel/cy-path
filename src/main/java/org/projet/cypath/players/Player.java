package org.projet.cypath.players;

import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Board;
import org.projet.cypath.tools.Box;
import org.projet.cypath.tools.Position;

/**
 * Class which represent a player
 *
 * @author Lucas Velay
 * @version 1.0
 */
public class Player {

    private final int id;
    private final String name;
    private final String color;
    private Position position;
    private final Position positionInitial;

    /**
     * Create a player
     *
     * @param id the identifier
     * @param name the name of the player
     * @param color the color witch represent the player on the board
     * @param position the position of the player on the board
     */
    public Player(int id, String name, String color, Position position) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.position = position;
      	this.positionInitial = position;
    }

    /**
     * Getter of the id
     * @return the id of the player
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter of the name
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter of the color
     * @return the color of the player
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Getter of the position by creating and returning a new position with the same values
     * @return the position of the player
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public Position getPosition() throws OutOfBoardException{
        return new Position(this.position.getX(), this.position.getY());
    }

    /**
     * Getter of the intial position of the player
     * @return the position of the player
     */
    public Position getPositionInitial() {
        return positionInitial;
    }
    /**
     * Move the player by changing his position
     * @param newPosition the new position of the player
     * @param board is the board of the game
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public void moveTo(Position newPosition, Board board) throws OutOfBoardException {
        Box oldBox=board.getBox(this.getPosition());
        oldBox.setHasPlayer(false);
        Box newBox=board.getBox(newPosition);
        newBox.setHasPlayer(true);
        this.position.move(newPosition.getX() - this.position.getX(), newPosition.getY() - this.position.getY());
    }
    /**
     *
     * Move the player by changing his position
     * @param newBox the new box of the player
     * @param board is the board of the game
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public void moveTo(Box newBox, Board board) throws OutOfBoardException {
        Box oldBox=board.getBox(this.getPosition());
        oldBox.setHasPlayer(false);
        newBox.setHasPlayer(true);
        this.position.move(newBox.getX() - this.position.getX(), newBox.getY() - this.position.getY());

    }
}
