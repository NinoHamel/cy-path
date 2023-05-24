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

    private Box[][] box;

    private int remainingWalls;

    //Constructor

    /**
     * Creates a 9 by 9 array to represent a grid
     * @param playerNumber the number of player of the game
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
     * @throws OutOfBoardException
     */
    //Setter
    public Box getBox(int row,int column) throws OutOfBoardException {
        if (onBoard(row,column)) {
            return box[row][column];
        }
        throw new OutOfBoardException("donnes pas dans le tableau");
    }


    /**
     * Validate if a wall can be set
     *
     * @param box is for first position of the wall
     * @return boolean True or False depending on the 2 positions or an error
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
            throw new InvalidWallException("There is a conflict with a wall already here");
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
     * Set a wall on the bottom or in the right if possible
     *
     * @param box is for the 1st box
     * @throws InvalidWallException if putting a wall on box isn't possible after verifying with {@link #canSetWall(Box,int)}
     */
    public void setBottomWall(Box box) throws InvalidWallException {
        if (canSetWall(box,0)) {
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setBottomWall(true);
            this.box[x][y+1].setBottomWall(true);
            this.box[x+1][y].setTopWall(true);
            this.box[x+1][y+1].setTopWall(true);
            this.box[x][y].setOriginHorizontalWall(true);
            this.remainingWalls=this.remainingWalls-1;
        }
        else {
            throw new InvalidWallException("A wall is already here");
        }
    }

    public void setRightWall(Box box) throws InvalidWallException {
        if (canSetWall(box,1)) {
            int x=box.getRow();
            int y=box.getColumn();
            this.box[x][y].setRightWall(true);
            this.box[x+1][y].setRightWall(true);
            this.box[x][y+1].setLeftWall(true);
            this.box[x+1][y+1].setLeftWall(true);
            this.box[x][y].setOriginVerticalWall(true);
            this.remainingWalls=this.remainingWalls-1;
        }
        else {
            throw new InvalidWallException("A wall is already here");
        }
    }
    /**
     * Set a wall on the bottom or in the right if possible
     *
     * @param row is for the row of the board
     * @param column is for the column of the board
     * @throws InvalidWallException if putting a wall on box isn't possible after verifying with {@link #canSetWall(Box, int)}
     */
    public void setBottomWall(int row,int column) throws InvalidWallException, OutOfBoardException {
        if (onBoard(row,column)){
            setBottomWall(this.getBox(row,column));
        }
    }

    public void setRightWall(int row,int column) throws InvalidWallException, OutOfBoardException {
        if (onBoard(row,column)){
            setRightWall(this.getBox(row,column));
        }
    }

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
        //List<Box> winBoxList = new ArrayList<>(Arrays.asList(player.getVictoryBoxes()));
        int[][] saveVisitedBoxArray = new int[9][9];
        Queue<Box> queue = new LinkedList<>();
        queue.add(player.getCurrentBox());
        boolean hasWall = false;

        while(queue.size() > 0){
            Box treatedBox = queue.peek();
            queue.remove();
            saveVisitedBoxArray[treatedBox.getRow()][treatedBox.getColumn()] = -1;
            //if(winBoxList.contains(treatedBox)){return true;}
            for(int i=0; i < 4; i++){
                int newPositionRow = treatedBox.getRow() + directions[i][0];
                int newPositionColumn = treatedBox.getColumn() + directions[i][1];

                if(newPositionRow >= 0 && newPositionColumn >= 0 && newPositionRow < 9 && newPositionColumn < 9 && saveVisitedBoxArray[newPositionRow][newPositionColumn] != -1){
                    switch (i){
                        case 0:
                            hasWall = this.box[newPositionRow][newPositionColumn].hasLeftWall();
                            break;
                        case 1:
                            hasWall = this.box[newPositionRow][newPositionColumn].hasRightWall();
                            break;
                        case 2:
                            hasWall = this.box[newPositionRow][newPositionColumn].hasTopWall();
                            break;
                        case 3:
                            hasWall = this.box[newPositionRow][newPositionColumn].hasBottomWall();
                            break;
                        default:
                            throw new RuntimeException("Loop error");
                    }
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

    public boolean hasPathAll(List<Player> players){
        for(Player player : players){
            if(!hasPath(player)){
                return false;
            }
        }
        return true;
    }


    public String displayBoard(){
        String displayedBoard = "";
        for(int i=0; i < 9; i++){
            displayedBoard += "++---";
        }
        displayedBoard += "++\n";
        for(int i=0; i < 9; i++){
            for (int j=0; j < 9; j++){
                if(this.box[i][j].hasTopWall()){
                    displayedBoard += "++---";
                }
                else{
                    displayedBoard += "++   ";
                }
            }
            displayedBoard += "++\n|";
            for (int j=0; j < 9; j++){
                if(this.box[i][j].hasLeftWall()){
                    displayedBoard += "|";
                }
                else{
                    displayedBoard += " ";
                }
                if(this.box[i][j].hasPlayer()){
                    displayedBoard += " P ";
                }
                else{
                    displayedBoard += "   ";
                }
                if(this.box[i][j].hasRightWall()){
                    displayedBoard += "|";
                }
                else{
                    displayedBoard += " ";
                }
            }
            displayedBoard += "|\n";
            for (int j=0; j < 9; j++){
                if(this.box[i][j].hasBottomWall()){
                    displayedBoard += "++---";
                }
                else{
                    displayedBoard += "++   ";
                }
            }
            displayedBoard += "++\n";
        }
        for(int i=0; i < 9; i++){
            displayedBoard += "++---";
        }
        displayedBoard += "++\n";
        return displayedBoard;
    }
}
