/**
 * NotEnoughSharesException.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Exceptions;

/**
 * Exception falls für einen Verkauf nicht genügend Aktien vorhanden sind
 */
public class NotEnoughSharesException extends Exception{
    private static final long serialVersionUID = 1L;
    public NotEnoughSharesException(){
        super("Nicht genügend Aktien vorhanden!");
    }
}

