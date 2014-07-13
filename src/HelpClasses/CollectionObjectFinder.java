/**
 * CollectionObjectFinder.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   10.07.2014
 * Version: 1.0
 */


package HelpClasses;
import java.util.Collection;
import java.util.Optional;


public class CollectionObjectFinder extends Exception{
    private static final CollectionObjectFinder cof = new CollectionObjectFinder();
    
    public <T> Object search (Collection<T> collection, String search, Exception exception) throws Exception{
        boolean exists = collection.stream().anyMatch(element -> ((Object) element).toString().equals(search));
        if(!exists){
            throw exception;
        }
        Optional<T> string = collection.stream().filter(element -> element.toString().equals(search)).findFirst();
        return string.get();
    }
    
    public <T> boolean exists (Collection<T> collection, String search){
        return collection.stream().anyMatch(element -> ((Object) element).toString().equals(search));
    }
    
    public static CollectionObjectFinder getInstance(){
        return cof;
    }
}