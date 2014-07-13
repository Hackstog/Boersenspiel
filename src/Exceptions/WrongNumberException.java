/**
 * WrongNumberException.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Exceptions;

/**
 * Exception falls eine Fehlerhalfte Zahl eingegeben wird (Verkauf/Ankauf einer negativen Anzahl)
 */
public class WrongNumberException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public WrongNumberException(){
        super("Ungültige Anzahl!");
    }
}
