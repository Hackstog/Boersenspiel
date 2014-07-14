/**
 * CSVReader.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package HelpClasses;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    private final String fileName;
    private final String shareName;
    private final List<Long> values = new ArrayList<>();
    private int l = 0;
    
    /**
     * Erzeugt einen neuen CSVReader
     * @param fileName
     * @param shareName 
     */
    public CSVReader(String fileName, String shareName){
        this.fileName = fileName;
        this.shareName = shareName;
    }
    
    public void run(){
        BufferedReader reader;
        String line;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            while((line = reader.readLine()) != null){
                String[] daten = line.split("\\,");
                if(!(daten[1].equals("Open"))){
                    String daten1 = daten[1];
                    String[] number = daten1.split("\\.");
                    if(number.length > 1){
                        String wert = number[0].concat(number[1]);
                        this.values.add(Long.parseLong(wert));
                    }
                }
            }
            reader.close();
        }catch (IOException | NumberFormatException e){
            e.printStackTrace();
        }
    }
    
    public long read(){
        if (l++ > (values.size() - 3)){
            l = 1;
	}
	return this.values.get(++l);
    }
    
    @Override
    public String toString(){
        return this.shareName;
    }
}
