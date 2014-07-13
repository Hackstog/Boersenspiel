/**
 * PlayerNotFoundException.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Exceptions;

/**
 * Exception falls ein Spieler aufgerufen wird, der nicht existiert
 */
public class PlayerNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public PlayerNotFoundException(){
        super("Der angegebene Spieler existiert nicht!");
    }
}
