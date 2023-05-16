package org.projet.cypath.tools;

import org.projet.cypath.exceptions.OutOfBoardException;

/**
 * Class which represent a position
 *
 * @author Lucas Velay
 * @version 1.0
 */
public class Position{

    private int abscissa;
    private int ordinate;

    /**
     * Create a position with its abscissa and its ordinate
     *
     * @param abscissa the coordinate on the abscissa axis
     * @param ordinate the coordinate ont the ordinate axis
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public Position(int abscissa, int ordinate) throws OutOfBoardException{
        if(abscissa <= 0 || ordinate <= 0 ) throw new OutOfBoardException("The grid has only positive coordinates.");
        if(abscissa > 9 || ordinate > 9 ) throw new OutOfBoardException("The grid has only coordinates greater than 9.");
        this.abscissa = abscissa;
        this.ordinate = ordinate;
    }

    /**
     * Getter of the abscissa
     * @return the abscissa
     */
    public int getX(){
        return this.abscissa;
    }

    /**
     * Getter of the ordinate
     * @return the ordinate
     */
    public int getY(){
        return this.ordinate;
    }

    /**
     * Move the position by changing its abscissa and its ordinate
     *
     * @param abscissaShift shift on the abscissa axis
     * @param ordinateShift shift on the ordinate axis
     * @throws OutOfBoardException the exception when a position is out of the board
     */
    public void move(int abscissaShift, int ordinateShift) throws OutOfBoardException {
        // TODO Remplacer la taille min et la taille max de la grille 0 et 9 par une constante de la classe Board
        if(this.abscissa + abscissaShift <= 0 || this.ordinate + ordinateShift <= 0 ) throw new OutOfBoardException("The grid has only positive coordinates.");
        if(this.abscissa + abscissaShift > 9 || this.ordinate + ordinateShift > 9 ) throw new OutOfBoardException("The grid has only coordinates greater than 9.");
        this.abscissa += abscissaShift;
        this.ordinate += ordinateShift;
    }

}
