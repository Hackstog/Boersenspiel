/**
* HelpPopUp.java
* Autor: Daniel Heigl <daniel.heigl@hs-augsburg.de>
* Datum: 14.07.2014
* Version: 2.0
*/


package UI;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.application.Application;

public class HelpPopUp extends Application{
    private final Scene scene = new Scene(new VBox(), 800, 600);
    private final GridPane gridPane = new GridPane();
    private final ScrollPane scrollPane = new ScrollPane();
    private final Stage stage = new Stage();
    
    @Override
    public void start(Stage secondaryStage){
        secondaryStage.setTitle(StockGameUI.rb.getString("MenuItem_help"));
        gridPane.setVgap(10);
        gridPane.setHgap(50);
        
        Text title = new Text(StockGameUI.rb.getString("MenuItem_help"));
        title.setFont(new Font(24));
        Font h2 = new Font(16);
        Font h3 = new Font(14);
        Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
        ok.setDefaultButton(true);

        if((StockGameUI.rb.getString("Help_notDefinedYet").equals("No"))){
            Text stockGame = new Text(StockGameUI.rb.getString("WindowTitle"));
            stockGame.setFont(h2);
            Text stockGame_exit = new Text(StockGameUI.rb.getString("MenuItem_exit"));
            stockGame_exit.setFont(h3);
            Text stockGame_exitText = new Text(StockGameUI.rb.getString("Help_exit"));
            Text player = new Text(StockGameUI.rb.getString("MenuPlayer"));
            player.setFont(h2);
            Text player_create = new Text(StockGameUI.rb.getString("MenuItem_createPlayer"));
            player_create.setFont(h3);
            Text player_createText = new Text(StockGameUI.rb.getString("Help_create"));
            Text player_delete = new Text(StockGameUI.rb.getString("MenuItem_deletePlayer"));
            player_delete.setFont(h3);
            Text player_deleteText = new Text(StockGameUI.rb.getString("Help_delete"));
            Text settings = new Text(StockGameUI.rb.getString("MenuSettings"));
            settings.setFont(h2);
            Text settings_lang = new Text(StockGameUI.rb.getString("MenuItem_language"));
            settings_lang.setFont(h3);
            Text settings_langText = new Text(StockGameUI.rb.getString("Help_lang"));
            Text settings_provider = new Text(StockGameUI.rb.getString("MenuItem_provider"));
            settings_provider.setFont(h3);
            Text settings_providerText = new Text(StockGameUI.rb.getString("Help_provider"));
            Text settings_parameter = new Text(StockGameUI.rb.getString("MenuItem_parameter"));
            settings_parameter.setFont(h3);
            Text settings_parameterText = new Text(StockGameUI.rb.getString("Help_parameter"));
            Text question = new Text("?");
            question.setFont(h2);
            Text question_help = new Text(StockGameUI.rb.getString("MenuItem_help"));
            question_help.setFont(h3);
            Text question_helpText = new Text(StockGameUI.rb.getString("Help_help"));
            Text question_about = new Text(StockGameUI.rb.getString("MenuItem_about"));
            question_about.setFont(h3);
            Text question_aboutText = new Text(StockGameUI.rb.getString("Help_about"));
            Text button_buy = new Text(StockGameUI.rb.getString("Button_buy"));
            button_buy.setFont(h2);
            Text button_buyText = new Text(StockGameUI.rb.getString("Help_buttonBuy"));
            Text button_sell = new Text(StockGameUI.rb.getString("Button_sell"));
            button_sell.setFont(h2);
            Text button_sellText = new Text(StockGameUI.rb.getString("Help_buttonSell"));
            Text button_trans = new Text(StockGameUI.rb.getString("Button_trans"));
            button_trans.setFont(h2);
            Text button_transText = new Text(StockGameUI.rb.getString("Help_buttonTrans"));
            Text button_agent = new Text(StockGameUI.rb.getString("Button_agent"));
            button_agent.setFont(h2);
            Text button_agentText = new Text(StockGameUI.rb.getString("Help_buttonAgent"));
            
            
            gridPane.add(title, 1, 1);
            gridPane.add(stockGame, 1, 3);
            gridPane.add(stockGame_exit, 1, 4);
            gridPane.add(stockGame_exitText, 1, 5);
            gridPane.add(player, 1, 7);
            gridPane.add(player_create, 1, 8);
            gridPane.add(player_createText, 1, 9);
            gridPane.add(player_delete, 1, 10);
            gridPane.add(player_deleteText, 1, 11);
            gridPane.add(settings, 1, 13);
            gridPane.add(settings_lang, 1, 14);
            gridPane.add(settings_langText, 1, 15);
            gridPane.add(settings_provider, 1, 16);
            gridPane.add(settings_providerText, 1, 17);
            gridPane.add(settings_parameter, 1, 18);
            gridPane.add(settings_parameterText, 1, 19);
            gridPane.add(question, 1, 21);
            gridPane.add(question_help, 1, 22);
            gridPane.add(question_helpText, 1, 23);
            gridPane.add(question_about, 1, 24);
            gridPane.add(question_aboutText, 1, 25);
            gridPane.add(button_buy, 1, 27);
            gridPane.add(button_buyText, 1, 28);
            gridPane.add(button_sell, 1, 30);
            gridPane.add(button_sellText, 1, 31);
            gridPane.add(button_trans, 1, 33);
            gridPane.add(button_transText, 1, 34);
            gridPane.add(button_agent, 1, 36);
            gridPane.add(button_agentText, 1, 37);
            scrollPane.setContent(gridPane);
            gridPane.add(ok, 1, 39);
            ((VBox) scene.getRoot()).getChildren().add(scrollPane);
        }else{
            Text noHelp = new Text(StockGameUI.rb.getString("Help_notDefinedYet"));
            gridPane.add(noHelp, 1, 1);
            gridPane.add(ok, 1, 2);
            ((VBox) scene.getRoot()).getChildren().add(gridPane);
        }
        
        ok.setOnAction((ActionEvent t) -> {
            secondaryStage.close();
        });
        
        secondaryStage.setScene(scene);
    }
}