/**
 * HistoriclaStockPriceProvider.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package StockPrice;
import Assets.Share;
import HelpClasses.CSVReader;
import HelpClasses.CollectionObjectFinder;
import Exceptions.ShareNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class HistoricalStockPriceProvider extends StockPriceProvider{
    List<CSVReader> csvList = new ArrayList<>();
    CollectionObjectFinder collectionObjectFinder = CollectionObjectFinder.getInstance();
    
    public HistoricalStockPriceProvider(){
        csvList.add(new CSVReader("HistoricalValues/Audi.csv", "Audi"));
        csvList.get(0).run();
        csvList.add(new CSVReader("HistoricalValues/Apple.csv", "Apple"));
        csvList.get(1).run();
        csvList.add(new CSVReader("HistoricalValues/BMW.csv", "BMW"));
        csvList.get(2).run();
        csvList.add(new CSVReader("HistoricalValues/Siemens.csv", "Siemens"));
        csvList.get(3).run();
        csvList.add(new CSVReader("HistoricalValues/Google.csv", "Google"));
        csvList.get(4).run();
        csvList.add(new CSVReader("HistoricalValues/Microsoft.csv", "Microsoft"));
        csvList.get(5).run();
        csvList.add(new CSVReader("HistoricalValues/VW.csv", "VW"));
        csvList.get(6).run();
        csvList.add(new CSVReader("HistoricalValues/Intel.csv", "Intel"));
        csvList.get(7).run();
    }
    
    /**
     * Ändert den Kurs einer Aktie basierend auf eienr CSV Datei
     * @param share 
     */
    @Override
    public void updateShareRate(Share share){
        try{
            CSVReader reader = (CSVReader) collectionObjectFinder.search(csvList, share.getName(), null);
            share.setWert(reader.read());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
