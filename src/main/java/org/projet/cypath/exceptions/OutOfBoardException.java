package org.projet.cypath.exceptions;


/**
 * Class which represent the exception when a position is out of the board
 *
 * @author Lucas Velay
 * @version 1.0
 */
public class OutOfBoardException extends Exception{

    /**
     * Create an OutOfBoardException
     *
     * @param message the message of the exception
     */
    public OutOfBoardException(String message) {
        super(message);
    }
}
