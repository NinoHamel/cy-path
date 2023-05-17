package org.projet.cypath.players;

import org.projet.cypath.exceptions.OutOfBoardException;
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
    private int startOrientation;

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
     * Move the player by changing his position
     * @param newPosition the new position of the player
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public void moveTo(Position newPosition) throws OutOfBoardException {
        this.position.move(newPosition.getX() - this.position.getX(), newPosition.getY() - this.position.getY());
    }

    public void setWall(Position position, boolean orientation){
        // TODO verifier que le mur peut être poser
        // TODO poser le mur (attente des classes Board et Box)
    }
}
