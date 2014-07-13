/**
 * NotEnoughMoneyException.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Exceptions;

/**
 * Exception falls für einen Kaufvorgang nicht genügend Geld vorhanden ist
 */
public class NotEnoughMoneyException extends Exception{
    private static final long serialVersionUID = 1L;
    public NotEnoughMoneyException(){
        super("Der Kontostand ist zu gering!");
    }
}
