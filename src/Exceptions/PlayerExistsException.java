/**
 * PlayerExistsException.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Exceptions;

/**
 * Exception falls ein Spieler angelegt werden soll der bereits existiert
 */
public class PlayerExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public PlayerExistsException(){
        super("Der Spieler existiert bereits!");
    }
}
