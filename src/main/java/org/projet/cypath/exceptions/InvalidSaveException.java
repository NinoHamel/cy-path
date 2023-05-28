package org.projet.cypath.exceptions;


/**
 * Class which represent the exception when a game instance does not exist
 *
 * @author Clément CHRISTIAENS
 * @version 1.0
 */
public class InvalidSaveException extends Exception{

    /**
     * Create an InvalidSaveException
     */
    public InvalidSaveException() {
        System.out.println("New InvalidSaveException :");
        System.out.println("You can't save a menu :/");
    }
}