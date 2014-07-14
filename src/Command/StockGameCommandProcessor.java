/**
 * StockGameCommandProcessor.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Command;
import AccountManager.AccountManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;

public class StockGameCommandProcessor {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final PrintWriter writer = new PrintWriter(System.out);
    private final AccountManager am;
    
    public StockGameCommandProcessor(AccountManager am){
        this.am = am;
    }
    
    /**
     * Liefert den AccountManager zurück
     * @return AccountManager
     */
    public AccountManager getAccountManager(){
        return this.am;
    }
    
    /**
     * Liefert den BufferedReader zurück
     * @return BufferedReader
     */
    public BufferedReader getReader(){
        return reader;
    }
    
    /**
     * Liefert den PrintWriter zurück
     * @return PrintWriter
     */
    public PrintWriter getWriter(){
        return writer;
    }

    public void process() throws Exception{
        final CommandScanner scanner = new CommandScanner (StockGameCommandType.values(), this.reader);
	final List<CommandTypeInfo> commandTypeInfo = Arrays.asList(StockGameCommandType.values());
        while(true){
            /**
             * Liest eine Zeile als String ein
             */
            final String scan = this.getReader().readLine();
            /**
             * Teilt den eingelesenen String in einzelne Strings auf basierend auf Leerzeichen
             */
            final List<String> input = Arrays.asList(scan.split(Pattern.quote(" ")));
            scanner.setAvailableCommands(commandTypeInfo);
            final CommandDescriptor command = new CommandDescriptor();
            scanner.fillInCommandDesc(command, input);
            final List<Object> params = command.getParams();
            final StockGameCommandType sct = (StockGameCommandType)command.getCommandType();
            sct.execute(commandTypeInfo, this.getAccountManager(), input.get(0), params);
        }
    }
}
