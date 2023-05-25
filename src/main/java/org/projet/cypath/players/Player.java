package org.projet.cypath.players;

import javafx.geometry.Orientation;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.tools.Board;
import org.projet.cypath.tools.Box;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which represent a player
 *
 * @author Lucas Velay
 * @version 1.0
 */
public class Player implements Serializable {

    private final int id;
    private final String name;
    private final String color;
    private Box currentBox;
    private final List<Box> victoryBoxes;
    private static final long SerialVersionUID=1L;
    private boolean victory;
    /**
     * Create a player
     *
     * @param id the identifier
     * @param name the name of the player
     * @param color the color witch represent the player on the board
     * @param Box the Box where the player is on the board
     */
    public Player(int id, String name, String color, Box Box, Board board) throws OutOfBoardException {
        this.id = id;
        this.name = name;
        this.color = color;
        this.currentBox = Box;
        this.currentBox.setHasPlayer(true);
        this.victoryBoxes = new ArrayList<>();
        if(Box.getRow() == 0 && Box.getColumn() == 4){
            for(int i=0; i< 9; i++){
                victoryBoxes.add(board.getBox(8, i));
            }
        }
        if(Box.getRow() == 8 && Box.getColumn() == 4){
            for(int i=0; i< 9; i++){
                victoryBoxes.add(board.getBox(0, i));
            }
        }
        if(Box.getRow() == 4 && Box.getColumn() == 0){
            for(int i=0; i< 9; i++){
                victoryBoxes.add(board.getBox(i, 8));
            }
        }
        if(Box.getRow() == 4 && Box.getColumn() == 8){
            for(int i=0; i< 9; i++){
                victoryBoxes.add(board.getBox(i, 0));
            }
        }
    }

    /**
     * Setter of victory
     * @param victory
     */
    public void setVictory(boolean victory){
        this.victory=victory;
    }
    /**
     * Getter of victory
     * @return
     */
    public boolean isVictory(){
        return this.victory;
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
    public Box getCurrentBox(){
        return this.currentBox;
    }

    public List<Box> getVictoryBoxes(){
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
        this.currentBox.setHasPlayer(false);
        this.currentBox = board.getBox(newRow, newColumn);
        this.currentBox.setHasPlayer(true);
    }
    /**
     *
     * Move the player by changing his position
     * @param newBox the new box of the player
     */
    public void moveTo(Box newBox){
        this.currentBox.setHasPlayer(false);
        newBox.setHasPlayer(true);
        this.currentBox = newBox;
    }

    /**
     *Give a list of box, with every possible move for a player
     * @param board is the board of the game
     * @return possibleMove a list of box where the player can move to
     * @throws OutOfBoardException
     */
    public List<Box> possibleMove(Board board) throws OutOfBoardException {
        List<Box> possibleMove = new ArrayList<>();
        int rowPlayer = this.getCurrentBox().getRow();
        int columnPlayer = this.getCurrentBox().getColumn();
        Box boxPlayer = this.getCurrentBox();

        // Déplacement bas
        if (board.onBoard(rowPlayer + 1, columnPlayer)) {
            int rowBottom = rowPlayer + 1;
            int columnBottom = columnPlayer;
            Box boxBottom = board.getBox(rowBottom, columnBottom);
            if (board.onBoard(rowBottom, columnBottom) && !boxPlayer.hasBottomWall()) {
                if (!boxBottom.hasPlayer()) {
                    possibleMove.add(boxBottom);
                } else {
                    possibleMoveJump(board, boxBottom, possibleMove,"Bottom");
                }
            }
        }

        // Déplacement haut
        if (board.onBoard(rowPlayer - 1, columnPlayer)) {
            int rowTop = rowPlayer - 1;
            int columnTop = columnPlayer;
            Box boxTop = board.getBox(rowTop, columnTop);
            if (board.onBoard(rowTop, columnTop) && !boxPlayer.hasTopWall()) {
                if (!boxTop.hasPlayer()) {
                    possibleMove.add(boxTop);
                } else {
                    possibleMoveJump(board, boxTop, possibleMove,"Top");
                }
            }
        }

        // Déplacement droite
        if (board.onBoard(rowPlayer, columnPlayer + 1)) {
            int rowRight = rowPlayer;
            int columnRight = columnPlayer + 1;
            Box boxRight = board.getBox(rowRight, columnRight);
            if (board.onBoard(rowRight, columnRight) && !boxPlayer.hasRightWall()) {
                if (!boxRight.hasPlayer()) {
                    possibleMove.add(boxRight);
                } else {
                    possibleMoveJump(board, boxRight, possibleMove,"Right");
                }
            }
        }

        // Déplacement gauche
        if (board.onBoard(rowPlayer, columnPlayer - 1)) {
            int rowLeft = rowPlayer;
            int columnLeft = columnPlayer - 1;
            Box boxLeft = board.getBox(rowLeft, columnLeft);
            if (board.onBoard(rowLeft, columnLeft) && !boxPlayer.hasLeftWall()) {
                if (!boxLeft.hasPlayer()) {
                    possibleMove.add(boxLeft);
                } else {
                    possibleMoveJump(board, boxLeft, possibleMove,"Left");
                }
            }
        }

        return possibleMove;
    }

    /**
     * This method is used in {@link #possibleMove(Board)} to give differents move possible if there is 2 players next to each other.
     * @param board is the board where the player moves
     * @param newBox is the box where another player is
     * @param possibleMove is
     * @throws OutOfBoardException
     */
    public void possibleMoveJump(Board board, Box newBox, List<Box> possibleMove, String Orientation) throws OutOfBoardException {
        int rowPlayer = this.getCurrentBox().getRow();
        int columnPlayer = this.getCurrentBox().getColumn();
        Box newNewBox=null;
        if (Orientation=="Right" && board.onBoard(newBox.getRow(), newBox.getColumn()+1)) {
                    newNewBox = board.getBox(newBox.getRow(), newBox.getColumn() + 1);
        }
        if (Orientation=="Left" && board.onBoard(newBox.getRow(), newBox.getColumn()-1)) {
                    newNewBox = board.getBox(newBox.getRow(), newBox.getColumn() - 1);
        }
        if (Orientation=="Top" && board.onBoard(newBox.getRow()-1, newBox.getColumn())) {
                    newNewBox = board.getBox(newBox.getRow()-1, newBox.getColumn());
        }
        if (Orientation=="Bottom" && board.onBoard(newBox.getRow()+1, newBox.getColumn())) {
                    newNewBox = board.getBox(newBox.getRow()+1, newBox.getColumn());
        }
        if(
                Orientation=="Right"&& (newBox.hasRightWall())||
                Orientation=="Left"&& newBox.hasLeftWall()||
                Orientation=="Top"&& newBox.hasTopWall()||
                Orientation=="Bottom"&& newBox.hasBottomWall()||
                newNewBox!=null && newNewBox.hasPlayer() && !newNewBox.equals(this.getCurrentBox()))
     {
            // Déplacement bas
            if (board.onBoard(newBox.getRow() + 1, newBox.getColumn())) {
                int newRowBottom = newBox.getRow() + 1;
                int newColumnBottom = newBox.getColumn();
                Box newBoxBottom = board.getBox(newRowBottom, newColumnBottom);
                if (board.onBoard(newRowBottom, newColumnBottom) && !newBox.hasBottomWall() && !newBoxBottom.equals(this.getCurrentBox()) && newBoxBottom.hasPlayer()==false) {
                    possibleMove.add(newBoxBottom);
                }
            }
            // Déplacement haut
            if (board.onBoard(newBox.getRow() - 1, newBox.getColumn())) {
                int newRowTop = newBox.getRow() - 1;
                int newColumnTop = newBox.getColumn();
                Box newBoxTop = board.getBox(newRowTop, newColumnTop);
                if (board.onBoard(newRowTop, newColumnTop) && !newBox.hasTopWall() && !newBoxTop.equals(this.getCurrentBox()) && newBoxTop.hasPlayer()==false) {
                    possibleMove.add(newBoxTop);
                }
            }
            // Déplacement droite
            if (board.onBoard(newBox.getRow(), newBox.getColumn() + 1)) {
                int newRowRight = newBox.getRow();
                int newColumnRight = newBox.getColumn() + 1;
                Box newBoxRight = board.getBox(newRowRight, newColumnRight);
                if (board.onBoard(newRowRight, newColumnRight) && !newBox.hasRightWall() && !newBoxRight.equals(this.getCurrentBox()) && newBoxRight.hasPlayer()==false) {
                    possibleMove.add(newBoxRight);
                }
            }

            // Déplacement gauche
            if (board.onBoard(newBox.getRow(), newBox.getColumn() - 1)) {
                int newRowLeft = newBox.getRow();
                int newColumnLeft = newBox.getColumn() - 1;
                Box newBoxLeft = board.getBox(newRowLeft, newColumnLeft);
                if (board.onBoard(newRowLeft, newColumnLeft) && !newBox.hasLeftWall() && !newBoxLeft.equals(this.getCurrentBox()) && newBoxLeft.hasPlayer()==false) {
                    possibleMove.add(newBoxLeft);
                }
            }
        }
        else{
            //Déplacement Bas
            if(Orientation == "Bottom"){
                int newRowBottom = newBox.getRow()+1;
                int newColumnBottom = newBox.getColumn();
                Box newBoxBottom = board.getBox(newRowBottom, newColumnBottom);
                if (board.onBoard(newRowBottom, newColumnBottom) && newBoxBottom.hasPlayer()==false) {
                    possibleMove.add(newBoxBottom);
                }
            }
            //Déplacement Haut
            if(Orientation == "Top"){
                int newRowTop = newBox.getRow()-1;
                int newColumnTop = newBox.getColumn();
                Box newBoxTop = board.getBox(newRowTop, newColumnTop);
                if (board.onBoard(newRowTop, newColumnTop) && newBoxTop.hasPlayer()==false) {
                    possibleMove.add(newBoxTop);
                }
            }
            //Déplacement Droit
            if(Orientation == "Right"){
                int newRowRight = newBox.getRow();
                int newColumnRight = newBox.getColumn() + 1;
                Box newBoxRight = board.getBox(newRowRight, newColumnRight);
                if (board.onBoard(newRowRight, newColumnRight) && newBoxRight.hasPlayer()==false) {
                    possibleMove.add(newBoxRight);
                }
            }
            //Déplacement Gauche
            if(Orientation == "Left"){
                int newRowLeft = newBox.getRow();
                int newColumnLeft = newBox.getColumn() - 1;
                Box newBoxLeft = board.getBox(newRowLeft, newColumnLeft);
                if (board.onBoard(newRowLeft, newColumnLeft) && newBoxLeft.hasPlayer()==false) {
                    possibleMove.add(newBoxLeft);
                }
            }
        }
    }
}
