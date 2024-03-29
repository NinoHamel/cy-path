package org.projet.cypath.tools;
import org.projet.cypath.exceptions.InvalidWallException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;

import java.io.Serializable;
import java.io.IOException;
import java.util.*;


/**
 * Board is a class which creates a grid of 9 by 9 boxes and list of players.
 * It shall be used to know how many players are still playing, who won and if a move is valid or not.
 *
 * @author Côme QUINTYN
 * @version 1.0
 * @see Box
 */
public class Board implements Serializable {

    /**
     * Represents a two-dimensional array of Box objects.
     */
    private Box[][] box;

    /**
     * Represents the number of remaining walls in the game.
     */
    private int remainingWalls;

    //Constructor

    /**
     * Creates a 9 by 9 array to represent a grid
     * @param playerNumber the number of players
     * @throws OutOfBoardException the exception when a position is out of the board
     * @throws IOException input or output exception
     */
    public Board(int playerNumber) throws OutOfBoardException, IOException {
        this.remainingWalls=20;
        if(playerNumber < 2 || playerNumber > 4) throw new IOException("The player number must be between 2 and 4.");
        box = new Box[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                box[i][j] = new Box(i, j);
            }
        }
    }

    /**
     * getter of the box
     * @return the box
     */
    public Box[][] getBox() {
        return box;
    }
    //Setter
    /**
     * Return if a coordinate is in the board or not
     * @param row coordinate
     * @param column coordinate
     * @return if the coordinates are on the board
     */
    public boolean onBoard(int row,int column){
        return (row>=0 && row<=8 && column>=0 && column<=8);
    }

    /**
     * Return a box from board using 2 int
     * @param row coordinate
     * @param column coordinate
     * @return box of the 2 int
     * @throws OutOfBoardException when the box isn't on the board
     */
    //Setter
    public Box getBox(int row,int column) throws OutOfBoardException {
        if (onBoard(row,column)) {
            return box[row][column];
        }
        throw new OutOfBoardException("donnes pas dans le tableau");
    }
    /**
     * Validate if a wall can be set or not
     * @param box is the first position of the wall
     * @param orientation is to determine the orientation of the wall, 0 for horizontal and 1 for vertical
     * @return boolean True or False depending on the 2 positions or an error
     * @throws InvalidWallException the wall is invalid
     */
    public Boolean canSetWall(Box box, int orientation) throws InvalidWallException {
        int row=box.getRow();
        int column=box.getColumn();
        if (this.remainingWalls==0){
            throw new InvalidWallException("No remaining walls");
        }
        if (row < 0 || column < 0){
            throw new InvalidWallException("The wall can only be put in positive coordinates.");
        }
        else if (row > 7 || column >7){
            throw new InvalidWallException("The wall coordinates can only be lesser than 8");
        }
        else if (box.hasOriginHorizontalWall() || box.hasOriginVerticalWall()){
            throw new InvalidWallException("There is a conflict with a wall already here!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        //Orientation 0 pour setBottomWall() ; cas column>0
        else if (column>0 && orientation == 0 && (this.box[row][column-1].hasOriginHorizontalWall() || this.box[row][column+1].hasOriginHorizontalWall())){
            throw new InvalidWallException("There is a conflict with the adjacent walls");
        }
        //Orientation 1 pour setRightWall() ; cas row>0
        else if (row>0 && orientation == 1 && (this.box[row-1][column].hasOriginVerticalWall() || this.box[row+1][column].hasOriginVerticalWall())){
            throw new InvalidWallException("There is a conflict with the adjacent walls");
        }
        //Orientation 0 pour setBottomWall() ; cas column=0
        else if (column==0 && orientation == 0 && (this.box[row][column+1].hasOriginHorizontalWall())){
            throw new InvalidWallException("There is a conflict with the adjacent walls");
        }
        //Orientation 1 pour setRightWall() ; cas row=0
        else if (row==0 && orientation == 1 && (this.box[row+1][column].hasOriginVerticalWall())){
            throw new InvalidWallException("There is a conflict with the adjacent walls");
        }
        else return true;
    }
    /**
     * Set a bottom wall by using the first position of the wall
     *
     * @param box is for the 1st box
     */
    public void setBottomWall(Box box){
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setBottomWall(true);
            this.box[x][y+1].setBottomWall(true);
            this.box[x+1][y].setTopWall(true);
            this.box[x+1][y+1].setTopWall(true);
            this.box[x][y].setOriginHorizontalWall(true);
            remainingWalls--;
    }
    /**
     * Set or Destroy a bottom wall by using the first position
     * @param box is for the 1st box
     * @param wallState true or false to add or delete a wall
     */
    public void setBottomWall(Box box,Boolean wallState){
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setBottomWall(wallState);
            this.box[x][y+1].setBottomWall(wallState);
            this.box[x+1][y].setTopWall(wallState);
            this.box[x+1][y+1].setTopWall(wallState);
            this.box[x][y].setOriginHorizontalWall(wallState);
            if (wallState) {
                remainingWalls--;
            }
            else {
                remainingWalls++;
            }
    }
    /**
     * Set a right wall by using the first position of the wall
     *
     * @param box is for the 1st box
     */
    public void setRightWall(Box box){
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setRightWall(true);
            this.box[x+1][y].setRightWall(true);
            this.box[x][y+1].setLeftWall(true);
            this.box[x+1][y+1].setLeftWall(true);
            this.box[x][y].setOriginVerticalWall(true);
            remainingWalls--;
    }
    /**
     * Set or Destroy a right wall by using the first position
     * @param box is for the 1st box
     * @param wallState true or false to add or delete a wall
     */
    public void setRightWall(Box box,Boolean wallState){
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setRightWall(wallState);
            this.box[x+1][y].setRightWall(wallState);
            this.box[x][y+1].setLeftWall(wallState);
            this.box[x+1][y+1].setLeftWall(wallState);
            this.box[x][y].setOriginVerticalWall(wallState);
            if (wallState) {
                remainingWalls--;
            }
            else {
                remainingWalls++;
            }
    }

    /**
     * Set a bottom wall by using the first position by getting the box and the using {@link #setBottomWall(Box)}
     * @param row is the row of the wall
     * @param column is the column of the wall
     * @throws OutOfBoardException if the row or column aren't on the board
     */
    public void setBottomWall(int row,int column) throws OutOfBoardException {
        if (onBoard(row,column)){
            setBottomWall(this.getBox(row,column));
        }
    }
    /**
     * Set or Destroy a bottom wall by using the first position by getting the box and the using {@link #setBottomWall(Box,Boolean)}
     * @param row is the row of the wall
     * @param column is the column of the wall
     * @param wallState true or false to add or delete a wall
     * @throws OutOfBoardException if the row or column aren't on the board
     */
    public void setBottomWall(int row,int column,Boolean wallState) throws OutOfBoardException {
        if (onBoard(row,column)){
            setBottomWall(this.getBox(row,column),wallState);
        }
    }
    /**
     * Set or Destroy a right wall by using the first position by getting the box and the using {@link #setBottomWall(Box)}
     * @param row is the row of the wall
     * @param column is the column of the wall
     * @throws OutOfBoardException if the row or column aren't on the board
     */
    public void setRightWall(int row,int column) throws OutOfBoardException {
        if (onBoard(row,column)){
            setRightWall(this.getBox(row,column));
        }
    }
    /**
     * Set or Destroy a right wall by using the first position by getting the box and the using {@link #setBottomWall(Box,Boolean)}
     * @param row is the row of the wall
     * @param column is the column of the wall
     * @param wallState true or false to add or delete a wall
     * @throws OutOfBoardException if the row or column aren't on the board
     */
    public void setRightWall(int row,int column,Boolean wallState) throws OutOfBoardException {
        if (onBoard(row,column)){
            setRightWall(this.getBox(row,column),wallState);
        }
    }

    /**
     * Getter of the attribute remaining walls
     * @return int of the remaining walls
     */
    public int getRemainingWalls() {
        return remainingWalls;
    }


    /**
     * Check if there is a path between two positions
     * @param player the player
     * @return True or False depending on the 2 positions
     */
    public boolean hasPath(Player player){
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // droite, gauche, bas, haut
        int[][] saveVisitedBoxArray = new int[9][9];
        Queue<Box> queue = new LinkedList<>();
        queue.add(player.getCurrentBox());
        boolean hasWall;

        while(queue.size() > 0){
            Box treatedBox = queue.peek();
            queue.remove();
            saveVisitedBoxArray[treatedBox.getRow()][treatedBox.getColumn()] = -1;
            for(int i=0; i < 4; i++){
                int newPositionRow = treatedBox.getRow() + directions[i][0];
                int newPositionColumn = treatedBox.getColumn() + directions[i][1];

                if(newPositionRow >= 0 && newPositionColumn >= 0 && newPositionRow < 9 && newPositionColumn < 9 && saveVisitedBoxArray[newPositionRow][newPositionColumn] != -1){
                    hasWall = switch (i) {
                        case 0 -> this.box[newPositionRow][newPositionColumn].hasLeftWall();
                        case 1 -> this.box[newPositionRow][newPositionColumn].hasRightWall();
                        case 2 -> this.box[newPositionRow][newPositionColumn].hasTopWall();
                        case 3 -> this.box[newPositionRow][newPositionColumn].hasBottomWall();
                        default -> throw new RuntimeException("Loop error");
                    };
                    if(!hasWall){
                        Box newBox = this.box[newPositionRow][newPositionColumn];
                        if(player.getVictoryBoxes().contains(newBox)){
                            return true;
                        }
                        queue.add(newBox);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Function used to display on the console a schematic board
     * @return string that can be displayed using the command System.ont.println
     */
    public String displayBoard(){
        StringBuilder displayedBoard = new StringBuilder();
        displayedBoard.append("++---".repeat(9));
        displayedBoard.append("++\n");
        for(int i=0; i < 9; i++){
            for (int j=0; j < 9; j++){
                if(this.box[i][j].hasTopWall()){
                    displayedBoard.append("++---");
                }
                else{
                    displayedBoard.append("++   ");
                }
            }
            displayedBoard.append("++\n|");
            for (int j=0; j < 9; j++){
                if(this.box[i][j].hasLeftWall()){
                    displayedBoard.append("|");
                }
                else{
                    displayedBoard.append(" ");
                }
                if(this.box[i][j].hasPlayer()){
                    displayedBoard.append(" P ");
                }
                else{
                    displayedBoard.append("   ");
                }
                if(this.box[i][j].hasRightWall()){
                    displayedBoard.append("|");
                }
                else{
                    displayedBoard.append(" ");
                }
            }
            displayedBoard.append("|\n");
            for (int j=0; j < 9; j++){
                if(this.box[i][j].hasBottomWall()){
                    displayedBoard.append("++---");
                }
                else{
                    displayedBoard.append("++   ");
                }
            }
            displayedBoard.append("++\n");
        }
        displayedBoard.append("++---".repeat(9));
        displayedBoard.append("++\n");
        return displayedBoard.toString();
    }
}
