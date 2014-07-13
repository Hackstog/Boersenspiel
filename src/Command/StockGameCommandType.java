/**
 * StockGameCommandType.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Command;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import AccountManager.AccountManager;

/**
 * Definiert die verfügbaren Kommandos und die dazu nötigen Paramater
 */
public enum StockGameCommandType implements CommandTypeInfo{
    HELP ("help", " * list all commands"),
    EXIT ("exit", " * exit program"),
    CREATEPLAYER ("createPlayer", "<name> * create a new Player by name with given money", String.class),
    BUYSHARE ("buyShare", "<playername> <share> <amount> * buy that amount of shares", String.class, String.class, int.class),
    SELLSHARE ("sellShare", "<playername> <share> <amount> * sell that amount of shares", String.class, String.class, int.class),
    GETCASH ("getValueOfCashAccount", "<playername> * get cashaccountvalue of that player", String.class),
    GETDEPOSIT ("getValueOfDepositAccount", "<playername> * get depositaccountvalue of that player", String.class),
    STARTAGENT ("startAgent", "<playername> * start the tradeagent for that player", String.class),
    GETTRANSACTIONS ("getTransactions", "<playername> <sortKey> * list transactions of a player, sorted <t> for timer or <s> for Share", String.class, String.class),
    GETTRANSACTIONSWITHSHARE ("getTransactionsWithShare", "<playername> <shareName> * list transactions of a player containing the givven Share", String.class, String.class);
    
    private final String commandoName;
    private final String helpText;
    private final List <Class<?>> parameter = new ArrayList<>();
            
    /**
     * Erstellt eine neues Enum-Element mit den angegebenen Parametern
     * @param commandName
     * @param helpText
     * @param parameter 
     */
    private StockGameCommandType(String commandName, String helpText, Class<?>... parameter){
        this.commandoName = commandName;
        this.helpText = helpText;
        this.parameter.add(String.class);
        this.parameter.add(String.class);
        this.parameter.add(long.class);
    }
    
    /**
     * Liefert den Hilfetext des Kommandos zurück
     * @return Hilfetext als String
     */
    @Override
    public String getHelpText(){
        return this.helpText;
    }    
    
    /**
     * Liefert den Kommandonamen zurück
     * @return Kommandoname als String
     */
    @Override
    public String getCommandoName(){
        return this.commandoName;
    }
          
    /**
     * Führt das übergebene Kommando aus
     * @param commandType: CommdTypeInfo[]
     * @param am: AccountManager
     * @param command: Kommando
     * @param params: Übergebene Parameter als Object[]
     */
    @Override
    public void execute(List<CommandTypeInfo> commandType, AccountManager am, String command, List<Object> params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
//        final String commando = (String) params.get(0);
        final Class[] paramType = new Class[params.size()];
        /**
         * Durchläuft das Array aller verfügbaren Kommandos und gibt diese mit dem zugehörigen Hilfetext aus
         */
        switch (command) {
            case "help":
                commandType.forEach(element -> System.out.println(element.getCommandoName() + element.getHelpText() +"\n"));
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                for(int i=0; i<params.size(); i++){
                    paramType[i] = params.get(i).getClass() == Integer.class ? Integer.TYPE : params.get(i).getClass();
                } 
                final Method method = am.getClass().getDeclaredMethod(command, paramType);
                method.invoke(am, params.toArray());
                break;
        }
    }
    
    @Override
    public List <Class <?>> getParamTypes() 
    {
	return this.parameter;
    }
    
    /**
     * Überschreibt die toString-Methode
     * Liefert den Namen des Kommandos als String zurück
     * @return Kommandoname als String 
     */
    @Override
    public String toString(){
        return this.getCommandoName();
    }
    
}
