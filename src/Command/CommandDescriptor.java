/**
 * CommandDescriptor.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Command;
import java.util.ArrayList;
import java.util.List;


public class CommandDescriptor {
    private CommandTypeInfo commandType;
    private List<Object> params = new ArrayList<>();
    
    /**
     * liefert den Typ des Kommandos zurück
     * @return CommandTypeInfo 
     */
    public CommandTypeInfo getCommandType(){
        return this.commandType;
    }
    
    /**
     * Liefert die Parameter des Kommandos zurück
     * @return Object[]
     */
    public List<Object> getParams(){
        return this.params;
    }
    
    /**
     * Legt die Information zum Kommandotyp fest
     * @param commandTypeInfo: CommandTypeInfo
     */
    public void setCommandTypeInfo(CommandTypeInfo commandTypeInfo){
        this.commandType = commandTypeInfo;
    }
    
    /**
     * Legt die Parameter eines Kommandos fest
     * @param parameter: Zu setzende Parameter als Object[]
     */
    public void setParams(List<Object> parameter){
        this.params = parameter;
    }
    
    public <T> void setParams(CommandTypeInfo commandTypeInfo, T... params){
        for(int i=0; i<params.length; i++){
            try{
		if (commandTypeInfo.getCommandoName().equals("getTransactions") || commandTypeInfo.getCommandoName().equals("getTransactionsWithShare")){
                    this.getParams().add(params[i]);				
		}else{
                    this.getParams().add(commandTypeInfo.getParamTypes().get(i).cast(params[i]));
		}
            }catch(final ClassCastException e){
		this.getParams().add(Integer.parseInt((String)params[i]));
            }
        }
    }
}
