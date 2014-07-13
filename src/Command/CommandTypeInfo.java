/**
 * CommandTypeInfo.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Command;

import AccountManager.AccountManager;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Definiert allgemein die Zugriffsmethoden auf die Kommandos
 */
public interface CommandTypeInfo {
    public String getCommandoName();
    public String getHelpText();
    List <Class <? >> getParamTypes ();
    public void execute(List<CommandTypeInfo> commandType, AccountManager am, String command, List<Object> params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}