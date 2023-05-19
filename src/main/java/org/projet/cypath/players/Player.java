package org.projet.cypath.players;

import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Board;
import org.projet.cypath.tools.Box;

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
    private Box currentCell;
    private final Box[] victoryBoxes;

    /**
     * Create a player
     *
     * @param id the identifier
     * @param name the name of the player
     * @param color the color witch represent the player on the board
     * @param cell the cell where the player is on the board
     */
    public Player(int id, String name, String color, Box cell, Board board) throws OutOfBoardException {
        this.id = id;
        this.name = name;
        this.color = color;
        this.currentCell = cell;
      	this.victoryBoxes = new Box[9];
        if(cell.getRow() == 0 && cell.getColumn() == 4){
            for(int i=0; i< 9; i++){
                victoryBoxes[i] = board.getBox(8, i);
            }
        }
        if(cell.getRow() == 8 && cell.getColumn() == 4){
            for(int i=0; i< 9; i++){
                victoryBoxes[i] = board.getBox(0, i);
            }
        }
        if(cell.getRow() == 4 && cell.getColumn() == 0){
            for(int i=0; i< 9; i++){
                victoryBoxes[i] = board.getBox(i, 8);
            }
        }
        if(cell.getRow() == 4 && cell.getColumn() == 8){
            for(int i=0; i< 9; i++){
                victoryBoxes[i] = board.getBox(i, 0);
            }
        }
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
     */
    public Box getCurrentCell(){
        return this.currentCell;
    }

    public Box[] getVictoryBoxes(){
        return this.victoryBoxes;
    }

    /**
     * Move the player by changing his position
     * @param newRow the new row of the player
     * @param newColumn the new column of the player
     * @param board is the board of the game
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public void moveTo(int newRow, int newColumn, Board board) throws OutOfBoardException {
        /*
        Box oldBox=board.getBox(this.getPosition());
        oldBox.setHasPlayer(false);
        Box newBox=board.getBox(newPosition);
        newBox.setHasPlayer(true);
        this.position.move(newPosition.getX() - this.position.getX(), newPosition.getY() - this.position.getY());
         */
        this.currentCell.setHasPlayer(false);
        this.currentCell = board.getBox(newRow, newColumn);
        this.currentCell.setHasPlayer(true);
    }
    /**
     *
     * Move the player by changing his position
     * @param newBox the new box of the player
     * @param board is the board of the game
     */
    public void moveTo(Box newBox, Board board){
        /*
        Box oldBox=board.getBox(this.getPosition());
        oldBox.setHasPlayer(false);
        newBox.setHasPlayer(true);
        this.position.move(newBox.getX() - this.position.getX(), newBox.getY() - this.position.getY());
        */
        this.currentCell.setHasPlayer(false);
        newBox.setHasPlayer(true);
        this.currentCell = newBox;
    }
}
