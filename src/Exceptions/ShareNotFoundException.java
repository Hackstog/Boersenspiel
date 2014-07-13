/**
 * ShareNotFoundException.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Exceptions;

/**
 * Exception falls eine Aktie aufgerufen wird, der nicht existiert
 */
public class ShareNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ShareNotFoundException(){
        super("Die angegebene Aktie existiert nicht!");
    }
}
