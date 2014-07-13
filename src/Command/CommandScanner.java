/**
 * CommandScanner.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Command;
import java.io.BufferedReader;
import java.util.List;
import java.util.Arrays;
import HelpClasses.CollectionObjectFinder;
import Exceptions.CommandNotFoundException;

/**
 * Verwaltet die eingegebenen Kommandos und Parameter
 */
public class CommandScanner {
    private List<CommandTypeInfo> commandTypeInfos;
    private CommandDescriptor commandDescriptor;
    private static final CollectionObjectFinder collectionObjectFinder = CollectionObjectFinder.getInstance();
    
    public CommandScanner(StockGameCommandType[] stockGameCommandType, BufferedReader reader){
    }
    
    public void fillInCommandDesc(CommandDescriptor commandDescriptor, List<String> input) throws Exception{
        this.setCommandDescriptor(commandDescriptor);
        commandDescriptor.setCommandTypeInfo(this.getStockGameCommandType(input.get(0)));
        commandDescriptor.setParams(this.getStockGameCommandType(input.get(0)), Arrays.copyOfRange(input.toArray(), 1, input.size()));
    }
    
    
    public CommandDescriptor getCommandDescriptor(){
        return this.commandDescriptor;
    }
    
    public List<CommandTypeInfo> getCommandTypeInfo(){
        return this.commandTypeInfos;
    }
    
    public CommandTypeInfo getStockGameCommandType(String s) throws Exception{
        CommandTypeInfo cti = (CommandTypeInfo) collectionObjectFinder.search(this.getCommandTypeInfo(), s, new CommandNotFoundException());
        return cti;
    }
    
    public void setCommandDescriptor(CommandDescriptor commandDescriptor){
        this.commandDescriptor = commandDescriptor;
    }
    
    public void setCommandTypeInfo(List<CommandTypeInfo> typeinfos){
        this.commandTypeInfos = typeinfos;
    }
    
    public void setAvailableCommands(List<CommandTypeInfo> typeinfos){
        this.setCommandTypeInfo(typeinfos);
    }
}
