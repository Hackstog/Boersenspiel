/**
 * AccountManagerProxy.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package AccountManager;
import HelpClasses.HTMLFormatter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;
import java.lang.reflect.InvocationTargetException;

/**
 * Proxy zum AccountManager, der alle Methodenaufrufe protokolliert
 */
public final class AccountManagerProxy implements java.lang.reflect.InvocationHandler{
    private static final Logger logger = Logger.getLogger(AccountManagerProxy.class.getName());
    private final Handler handler;
    private final Object obj;
    
    public static Object newInstance(Object obj) throws IOException, SecurityException{
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new AccountManagerProxy(obj));
    }

    /**
     * Erzeugt einen neuen AccountManagerProxy
     * @param obj
     * @throws IOException
     * @throws SecurityException 
     */
    private AccountManagerProxy(Object obj) throws IOException, SecurityException{
        this.handler = new FileHandler("Log.html");
        //SimpleFormatter formatter = new SimpleFormatter();
        HTMLFormatter formatter = new HTMLFormatter();
        handler.setFormatter(formatter);
        System.setProperty("java.util.logging.config.file", "logging.properties");
        // Logger zurücksetzen
        LogManager.getLogManager().reset();
        // Logger neu initialisieren
        LogManager.getLogManager().readConfiguration();
        // Logger zum LogManager hinzufügen
        LogManager.getLogManager().addLogger(this.getLogger());
        // Logger zurückgeben
        LogManager.getLogManager().getLogger(this.getLogger().getName()).setLevel(Level.INFO);

        this.getLogger().addHandler(this.getHandler());
        this.obj = obj;
    }
    
    /**
     * Loggt die aufgerufen Befehle mit den Parametern und Rückgabewerten
     * @param proxy: Proxy als Object
     * @param method: Methode als Method
     * @param args: Argumente als Object[]
     * @return Object
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        Object result = "";
        getLogger().log(Level.INFO, "Aufgerufene Methode: "+method.getName()+"\n");
        try{
            String argInfo ="";
            if(args != null){
                for (Object element : args) {
                    if(!"null".equals(element.toString())){
                        argInfo += "("+element.getClass().getName()+") "+element.toString()+"\n";
                    }
                }
            }else{
                argInfo = "Keine Parameter!";
            }
            getLogger().log((LogManager.getLogManager().getLogger(this.getLogger().getName()).getLevel()), "Parameter: "+argInfo);
            result = method.invoke(obj, args);
            String resulttype = method.getReturnType().getName();
            getLogger().log((LogManager.getLogManager().getLogger(this.getLogger().getName()).getLevel()), "R&uuml;ckgabewert: ("+resulttype+") "+result);
        }catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
        }
        return result;
    }
    
    /**
     * Liefert den Filehandler zurück
     * @return Handler 
     */
    public Handler getHandler(){
        return handler;
    }
    
    /**
     * Liefert den Logger zurück
     * @return: Logger
     */
    public Logger getLogger(){
        return logger;
    }
}
