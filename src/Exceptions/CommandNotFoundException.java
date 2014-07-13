/**
 * CommandNotFoundException.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   11.07.2014
 * Version: 1.0
 */


package Exceptions;

/**
 * Exception falls ein Spieler aufgerufen wird, der nicht existiert
 */
public class CommandNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public CommandNotFoundException(){
        super("Das angegebenen Kommando existiert nicht!");
    }
}