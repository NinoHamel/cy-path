package org.projet.cypath.exceptions;


/**
 * Class which represent the exception when a scene does not exist
 *
 * @author Clément CHRISTIAENS
 * @version 1.0
 */
public class InvalidSceneException extends Exception{

    /**
     * Create an InvalidSceneException
     *
     * @param message the message of the exception
     */
    public InvalidSceneException(String message) {
        super(message);
    }
}