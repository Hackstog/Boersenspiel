/**
 * ParameterPopUp.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package UI;
import HelpClasses.ConstantValues;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.scene.control.ScrollPane;
import javafx.application.Application;
import javafx.scene.paint.Color;

public class ParameterPopUp extends Application{
    private final Scene scene = new Scene(new VBox(), 300, 200);
    private final GridPane gridPane = new GridPane();
    private final GridPane buttonPane = new GridPane();
    private final ScrollPane scrollPane = new ScrollPane();
    private final Stage stage = new Stage();
    static Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
    static Button abbort = new Button(StockGameUI.rb.getString("Button_abbort"));
    
        
    @Override
    public void start(Stage secondaryStage){
        secondaryStage.setTitle(StockGameUI.rb.getString("MenuItem_parameter"));
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        buttonPane.setHgap(20);
        
        Text header = new Text (StockGameUI.rb.getString("MenuItem_parameter"));
        header.setFont(new Font(24));
        gridPane.add(header, 1, 1);
        
        Text general = new Text(StockGameUI.rb.getString("General"));
        general.setFont(new Font(16));
        gridPane.add(general, 1, 3);
        Text startKapital = new Text(StockGameUI.rb.getString("Param_startKapital"));
        gridPane.add(startKapital, 1, 4);
        Slider sliderKapital = new Slider();
        sliderKapital.setMin(1000);
        sliderKapital.setMax(50000);
        sliderKapital.setValue(ConstantValues.getStartKapital()/100);
        sliderKapital.setShowTickLabels(true);
        sliderKapital.setShowTickMarks(true);
        sliderKapital.setMajorTickUnit(10000);
        sliderKapital.setMinorTickCount(0);
        sliderKapital.setBlockIncrement(1000);
        gridPane.add(sliderKapital, 1, 5);
        
        Text transactions = new Text(StockGameUI.rb.getString("Param_maxTransactions"));
        gridPane.add(transactions, 1, 6);        
        Slider sliderTrans = new Slider();
        sliderTrans.setMin(10);
        sliderTrans.setMax(500);
        sliderTrans.setValue(ConstantValues.getMaxTransactions());
        sliderTrans.setShowTickLabels(true);
        sliderTrans.setShowTickMarks(true);
        sliderTrans.setMajorTickUnit(100);
        sliderTrans.setMinorTickCount(0);
        sliderTrans.setBlockIncrement(10);
        gridPane.add(sliderTrans, 1, 7);
        
        Text stockchange = new Text(StockGameUI.rb.getString("Param_maxStockChange"));
        gridPane.add(stockchange, 1, 8);        
        Slider sliderStockChange = new Slider();
        sliderStockChange.setMin(10);
        sliderStockChange.setMax(500);
        sliderStockChange.setValue(ConstantValues.getMaxStockChange());
        sliderStockChange.setShowTickLabels(true);
        sliderStockChange.setShowTickMarks(true);
        sliderStockChange.setMajorTickUnit(100);
        sliderStockChange.setMinorTickCount(0);
        sliderStockChange.setBlockIncrement(10);
        gridPane.add(sliderStockChange, 1, 9);

        Text agent = new Text(StockGameUI.rb.getString("TradeAgent"));
        agent.setFont(new Font(16));
        gridPane.add(agent, 1, 11);
        Text buyWhen = new Text(StockGameUI.rb.getString("Param_buyWhen"));
        gridPane.add(buyWhen, 1, 12);
        Slider sliderBuyWhen = new Slider();
        sliderBuyWhen.setMin(1);
        sliderBuyWhen.setMax(20);
        sliderBuyWhen.setValue(ConstantValues.getBuyWhen());
        sliderBuyWhen.setShowTickLabels(true);
        sliderBuyWhen.setShowTickMarks(true);
        sliderBuyWhen.setMajorTickUnit(4);
        sliderBuyWhen.setMinorTickCount(0);
        sliderBuyWhen.setBlockIncrement(10);
        gridPane.add(sliderBuyWhen, 1, 13);
        
        Text buyMax = new Text(StockGameUI.rb.getString("Param_buyMax"));
        gridPane.add(buyMax, 1, 14);   
        Slider sliderBuyMax = new Slider();
        sliderBuyMax.setMin(10);
        sliderBuyMax.setMax(100);
        sliderBuyMax.setValue(ConstantValues.getBuyMax());
        sliderBuyMax.setShowTickLabels(true);
        sliderBuyMax.setShowTickMarks(true);
        sliderBuyMax.setMajorTickUnit(10);
        sliderBuyMax.setMinorTickCount(0);
        sliderBuyMax.setBlockIncrement(10);
        gridPane.add(sliderBuyMax, 1, 15);
        
        Text sellDiff = new Text(StockGameUI.rb.getString("Param_sellDiff"));
        gridPane.add(sellDiff, 1, 16);
        Slider sliderSellDiff = new Slider();
        sliderSellDiff.setMin(10);
        sliderSellDiff.setMax(50);
        sliderSellDiff.setValue(ConstantValues.getSellDiff());
        sliderSellDiff.setShowTickLabels(true);
        sliderSellDiff.setShowTickMarks(true);
        sliderSellDiff.setMajorTickUnit(10);
        sliderSellDiff.setMinorTickCount(0);
        sliderSellDiff.setBlockIncrement(5);
        gridPane.add(sliderSellDiff, 1, 17);
        
        Text sellPerc = new Text(StockGameUI.rb.getString("Param_sellPerc"));
        gridPane.add(sellPerc, 1, 18);           
        Slider sliderSellPerc = new Slider();
        sliderSellPerc.setMin(0);
        sliderSellPerc.setMax(100);
        sliderSellPerc.setValue(ConstantValues.getSellPerc());
        sliderSellPerc.setShowTickLabels(true);
        sliderSellPerc.setShowTickMarks(true);
        sliderSellPerc.setMajorTickUnit(10);
        sliderSellPerc.setMinorTickCount(0);
        sliderSellPerc.setBlockIncrement(5);
        gridPane.add(sliderSellPerc, 1, 19);
        
        Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
        Button abbort = new Button(StockGameUI.rb.getString("Button_abbort"));
        buttonPane.add(ok, 1, 1);
        buttonPane.add(abbort, 2, 1);
        gridPane.add(buttonPane, 1, 22);
        
        scrollPane.setContent(gridPane);
        ((VBox) scene.getRoot()).getChildren().add(scrollPane);
        secondaryStage.setScene(scene);
        
        ok.setOnAction((ActionEvent t) -> {
            ConstantValues.setStartKapital((int) (sliderKapital.getValue()*100));
            ConstantValues.setMaxTransactions((int) sliderTrans.getValue());
            ConstantValues.setMaxStockChange((int) sliderStockChange.getValue());
            ConstantValues.setBuyMax((int) sliderBuyMax.getValue());
            ConstantValues.setBuyWhen((int) sliderBuyWhen.getValue());
            ConstantValues.setSellDiff((int) sliderSellDiff.getValue());
            ConstantValues.setSellPerc((int) sliderSellPerc.getValue());
            StockGameUI.status.setTextFill(Color.web("green"));
            StockGameUI.status.setText(StockGameUI.rb.getString("Status_changeSuccess"));
            secondaryStage.close();
        });
        
        abbort.setOnAction((ActionEvent t) -> {
            secondaryStage.close();
        });
    }
}
