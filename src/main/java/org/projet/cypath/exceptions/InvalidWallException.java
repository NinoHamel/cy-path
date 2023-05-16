package org.projet.cypath.exceptions;


/**
 * Class which represent the exception when a wall is invalid
 *
 * @author Côme QUINTYN
 * @version 1.0
 */
public class InvalidWallException extends Exception{

    /**
     * Create an InvalidWallException
     *
     * @param message the message of the exception
     */
    public InvalidWallException(String message) {
        super(message);
    }
}
