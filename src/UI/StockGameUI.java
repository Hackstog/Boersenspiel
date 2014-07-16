/**
 * StockGameUI.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package UI;
import AccountManager.AccountManagerImpl;
import Assets.Share;
import Assets.ShareItem;
import StockPrice.StockPriceProvider;
import StockPrice.HistoricalStockPriceProvider;
import StockPrice.RandomStockPriceProvider;
import Player.Player;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class StockGameUI extends Application{
    private static StockPriceProvider spp;
    static AccountManagerImpl am;
    static Timer timer = new java.util.Timer();
    private List<ShareItem> sharesOfPlayer = new ArrayList<>();
    static ResourceBundle rb = ResourceBundle.getBundle("de", Locale.getDefault());
    static DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault()));	

    public StockGameUI(){
        this.spp = new HistoricalStockPriceProvider();
        this.am = new AccountManagerImpl(spp);
        spp.startUpdate();
        
        am.createPlayer("Tim");
        am.createPlayer("Daniel");
        try{
            am.buyShare("Daniel", "Audi", 10);
            am.buyShare("Daniel", "Apple", 30);
            am.buyShare("Daniel", "Google", 5);
            am.buyShare("Daniel", "Siemens", 15);
            am.buyShare("Daniel", "Microsoft", 10);
            am.buyShare("Daniel", "VW", 40);
            am.buyShare("Daniel", "BMW", 50);
            am.buyShare("Tim", "Microsoft", 24);
            am.buyShare("Tim", "BMW", 50);
        }catch(Exception e){
        }
        
    }
    
    /**
     * Definition der Elemente
     */
    private final Scene scene = new Scene(new VBox(), 800, 600);
    private final Scene popup = new Scene(new VBox(), 300, 200);
    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final GridPane buttonPane = new GridPane();
    private static final GridPane graphPane = new GridPane();
    private static final GridPane leftPane = new GridPane();
    private final GridPane rightPane = new GridPane();
    private final Stage stage = new Stage();
    private final Stage secondaryStage = new Stage();
    private final MenuBar menuBar = new MenuBar();
    private final List<RadioButton> radioButtons = new ArrayList<>();
    private final ComboBox playerDropDown = new ComboBox();
    private static ObservableList<Player> playerDropDownData = FXCollections.observableArrayList();
    private final Menu menuStockGame = new Menu(rb.getString("WindowTitle"));
    private final MenuItem exit = new MenuItem(rb.getString("MenuItem_exit"));
    private final Menu menuPlayer = new Menu(rb.getString("MenuPlayer"));
    private final MenuItem createPlayer = new MenuItem(rb.getString("MenuItem_createPlayer"));
    private final MenuItem deletePlayer = new MenuItem(rb.getString("MenuItem_deletePlayer"));  
    private final Menu menuSettings = new Menu(rb.getString("MenuSettings"));
    private final Menu language = new Menu(rb.getString("MenuItem_language"));
    private final ToggleGroup languages = new ToggleGroup();
    RadioMenuItem lang_de = new RadioMenuItem("Deutsch");
    RadioMenuItem lang_en = new RadioMenuItem("Englisch");
    RadioMenuItem lang_it = new RadioMenuItem("Italiano");
    RadioMenuItem lang_fr = new RadioMenuItem("Français");
    private final Menu provider = new Menu(rb.getString("MenuItem_provider"));
    private final ToggleGroup providers = new ToggleGroup();
    private static ToggleGroup shares = new ToggleGroup();
    RadioMenuItem provider_random = new RadioMenuItem(rb.getString("Provider_random"));
    RadioMenuItem provider_historical = new RadioMenuItem(rb.getString("Provider_historical"));
    private final MenuItem parameter = new MenuItem(rb.getString("MenuItem_parameter"));
    private final Menu menuHelp = new Menu("?");
    private final MenuItem help = new MenuItem(rb.getString("MenuItem_help"));
    private final MenuItem about = new MenuItem(rb.getString("MenuItem_about"));
    Button buy = new Button();
    Button sell = new Button();
    Button getTrans = new Button();
    Button agent = new Button();
    final Label playerNameLabel = new Label();
    final Label playerCashValue = new Label();
    final Label shares_head = new Label (rb.getString("Shares"));
    final static Label choosenShare = new Label();
    final static NumberAxis xAxis = new NumberAxis("", 0d, 20d, 1);
    final static NumberAxis yAxis = new NumberAxis("", 0d, 1000, 10);
    final static LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(rb.getString("WindowTitle"));
        //Erzeugt das Menu
        menuStockGame.getItems().add(exit);
        menuPlayer.getItems().add(createPlayer);
        menuPlayer.getItems().add(deletePlayer);
        language.getItems().add(lang_de);
        language.getItems().add(lang_en);
        language.getItems().add(lang_it);
        language.getItems().add(lang_fr);
        lang_de.setToggleGroup(languages);
        lang_en.setToggleGroup(languages);
        lang_it.setToggleGroup(languages);
        lang_fr.setToggleGroup(languages);
        languages.selectToggle(lang_de);
        menuSettings.getItems().add(language);
        provider.getItems().add(provider_random);
        provider.getItems().add(provider_historical);
        provider_random.setToggleGroup(providers);
        provider_historical.setToggleGroup(providers);
        providers.selectToggle(provider_historical);
        menuSettings.getItems().add(provider);
        menuSettings.getItems().add(parameter);
        menuHelp.getItems().add(help);
        menuHelp.getItems().add(about);
        menuBar.getMenus().addAll(menuStockGame, menuPlayer, menuSettings, menuHelp);
        
        //Legt ein GridPane an
        gridPane.setVgap(10);
        gridPane.setHgap(20);
        leftPane.setVgap(10);
        leftPane.setHgap(5);
        rightPane.setVgap(10);
        rightPane.setHgap(10);
        rightPane.setPadding(new Insets(40, 10, 10, 10));
        buttonPane.setVgap(10);
        buttonPane.setHgap(10);
        buttonPane.setPadding(new Insets(0, 0, 0, 0));
        
        //Liste der Aktien
        listShares();
        updateShares();

        graphPane.add(chart, 1, 1);
        
        //Aktienkurs als Graph
        xAxis.minHeight(250);
        chart.minWidth(100);
        chart.maxWidth(100);
        chart.minHeight(100);
        chart.maxHeight(100);
        chart.setLegendVisible(false);

        
        
        //Liste der Spieler als DropDown-Menü
        listPlayer();
        playerDropDown.setItems(playerDropDownData);
        playerDropDown.setMaxWidth(Double.MAX_VALUE);
        rightPane.add(playerDropDown, 1, 1);
        
        
        //Aktien des Spielers als Scrollbare Liste
        ScrollPane shareList = new ScrollPane();
        shareList.setMinHeight(200);
        shareList.setMinWidth(400);
        playerNameLabel.setFont(new Font(16));
        rightPane.add(playerNameLabel, 1, 2);
        rightPane.add(playerCashValue, 1, 3);
        rightPane.add(shareList, 1, 4);
 


        //Buttons für Aktionen
        buy.setText(rb.getString("Button_buy"));
        buy.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(buy, 1, 1);
        getTrans.setText(rb.getString("Button_trans"));
        getTrans.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(getTrans, 2, 1);
        sell.setText(rb.getString("Button_sell"));
        sell.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(sell, 1, 2);
        agent.setText(rb.getString("Button_agent"));
        agent.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(agent, 2, 2);
        rightPane.add(buttonPane, 1, 5);

        //Statusleiste
        HBox statusbar = new HBox();
        Label status = new Label();
        statusbar.getChildren().add(status);
        statusbar.setMargin(status, new Insets(5, 5, 5, 25));

        
        gridPane.add(leftPane, 1, 1);
        gridPane.add(graphPane, 1, 2);
        borderPane.setTop(menuBar);
        borderPane.setCenter(rightPane);
        borderPane.setLeft(gridPane);
        borderPane.setBottom(statusbar);
        ((VBox) scene.getRoot()).getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        secondaryStage.setScene(popup);
        
        
        
        //Event Handler
        /**
         * Ausgewälten Spieler ermitteln und seine Aktien anzeigen
         */
        playerDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> ov, Player old, Player neu) {
                playerNameLabel.setText(neu.toString());
                String value = decimalFormat.format(((double) (neu.getCashAccount().getWert()/100))*Double.valueOf(rb.getString("CurrencyExchangeValue")));
                playerCashValue.setText(rb.getString("CashAccountValue")+" "+value+" "+rb.getString("Currency"));
                sharesOfPlayer = neu.getDepositAccount().getPakete();
                GridPane list = new GridPane();
                list.setHgap(5);
                list.setVgap(5);
                int j = 1;
                for(ShareItem paket : sharesOfPlayer){
                    Text name = new Text(paket.getName());
                    Text anzahl = new Text(String.valueOf(paket.getAnzahl()));
                    list.add(name, 1, j);
                    list.add(anzahl, 20, j);
                    j++;
                }
                status.setText("");
                shareList.setContent(list);
            }
        });
        
        
        /**
         * Ausgewählte Aktie ermittelnt und Graph erzeugen
         */
        shares.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old, Toggle neu) {
                RadioButton r = (RadioButton) neu.getToggleGroup().getSelectedToggle();
                choosenShare.setText(r.getText());
                try {
                    Share s = spp.getShare(r.getText());
                    List<Long> graphEntries = s.getLastRates();
                    
                    XYChart.Series series = new XYChart.Series();
                    int j = 0;
                    for(long l : graphEntries){
                        series.getData().add(new XYChart.Data(j, l/100));
                        j++;
                    }
                    chart.getData().clear();
                    chart.getData().add(series);
                } catch (Exception ex) {
                }
            }
        });
        
        
        //Spiel beenden
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        
        //Spracheinstellung
        lang_de.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("de", Locale.getDefault());
            setLanguage();
        });
        lang_en.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("en", Locale.getDefault());
            setLanguage();
        });
        lang_it.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("it", Locale.getDefault());
            setLanguage();
        });
        lang_fr.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("fr", Locale.getDefault());
            setLanguage();
        });
        
        //Providereinstellung
        provider_historical.setOnAction((ActionEvent t) -> {
            StockGameUI.spp = new HistoricalStockPriceProvider();
        });
        provider_random.setOnAction((ActionEvent t) -> {
           StockGameUI.spp = new RandomStockPriceProvider();
        });
        
        //About-Popup
        about.setOnAction((ActionEvent t) -> {
            AboutPopUp about = new AboutPopUp();
            about.get().show();
        });
        
        //Help-Popup
        help.setOnAction((ActionEvent t) -> {
            HelpPopUp help = new HelpPopUp();
            help.get().show();
        });
        
        //Player-Popup
        createPlayer.setOnAction((ActionEvent t) -> {
            CreatePlayerPopUp player = new CreatePlayerPopUp();
            player.start(secondaryStage);
            secondaryStage.show();
        });
        deletePlayer.setOnAction((ActionEvent t) -> {
            DeletePlayerPopUp player = new DeletePlayerPopUp();
            player.start(secondaryStage);
            secondaryStage.show();
        });
        
        //Parameter-Popup
        parameter.setOnAction((ActionEvent t) -> {
            ParameterPopUp params = new ParameterPopUp();
            params.start(secondaryStage);
            secondaryStage.show();
        });
        
        //Buttons
        //Transaktion-Button
        getTrans.setOnAction((ActionEvent ) ->{
            if(playerNameLabel.getText() != null){
                try {
                    am.getTransactions(playerNameLabel.getText(), "t", "html");
                    status.setTextFill(Color.web("green"));
                    status.setText(rb.getString("Status_success"));
                } catch (Exception ex) {
                    status.setTextFill(Color.web("red"));
                    status.setText(rb.getString("Status_error"));
                }
            }
        });
        
        //StartAgent-Button
        agent.setOnAction((ActionEvent ) ->{
            if(playerNameLabel.getText() != null){
                try {
                    am.startAgent(playerNameLabel.getText());
                } catch (Exception ex) {
                    Logger.getLogger(StockGameUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //Buy-Button
        
        //Sell-Button
    }
    
    
    private void setLanguage(){
        menuStockGame.setText(rb.getString("WindowTitle"));
        exit.setText(rb.getString("MenuItem_exit"));
        menuPlayer.setText(rb.getString("MenuPlayer"));
        createPlayer.setText(rb.getString("MenuItem_createPlayer"));
        deletePlayer.setText(rb.getString("MenuItem_deletePlayer"));  
        menuSettings.setText(rb.getString("MenuSettings"));
        language.setText(rb.getString("MenuItem_language"));
        provider.setText(rb.getString("MenuItem_provider"));
        provider_random.setText(rb.getString("Provider_random"));
        provider_historical.setText(rb.getString("Provider_historical"));
        parameter.setText(rb.getString("MenuItem_parameter"));
        help.setText(rb.getString("MenuItem_help"));
        about.setText(rb.getString("MenuItem_about"));
        buy.setText(rb.getString("Button_buy"));
        sell.setText(rb.getString("Button_sell"));
        getTrans.setText(rb.getString("Button_trans"));
        agent.setText(rb.getString("Button_agent"));
        shares_head.setText(rb.getString("Shares"));
        if(!(playerCashValue.getText().equals(""))){
            playerCashValue.setText(rb.getString("CashAccountValue")+" "+playerCashValue.getText().split(": ")[1].split(" ")[0]+" "+rb.getString("Currency"));
        }
        listShares();
    }
    
    public static void listPlayer(){
        playerDropDownData.clear();
        am.getPlayer().stream().forEach((p) -> {
            playerDropDownData.add(p);
        });
    }
    
    public static void listShares(){
        leftPane.getChildren().clear();
        Text head = new Text(rb.getString("Shares"));
        head.setFont(new Font(16));
        leftPane.add(head, 1, 1);
        int i = 2;
        for(Share s : spp.getAllSharesAsSnapshot()){
            RadioButton r = new RadioButton(s.getName());
            if(s.getName().equals(choosenShare.getText())){
                r.setSelected(true);
            }
            r.setToggleGroup(shares);
            leftPane.add(r, 1, i);
            String value = decimalFormat.format(((double) (s.getWert()/100))*Double.valueOf(rb.getString("CurrencyExchangeValue")));
            Label kurs = new Label(value+" "+rb.getString("Currency"));
            leftPane.add(kurs, 15, i);
            i++;
        }
    }
    
    public static void updateShares(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
//                    spp.startUpdate();
                    listShares();
                });
            }
        }, 1000, 1000);
    }
}
