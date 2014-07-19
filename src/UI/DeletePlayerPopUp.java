/**
* DeletePlayerPopUp.java
* Autor: Daniel Heigl <daniel.heigl@hs-augsburg.de>
* Datum: 14.07.2014
* Version: 1.0
*/


package UI;
import AccountManager.AccountManager;
import Player.Player;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

public class DeletePlayerPopUp extends Application{
    private AccountManager am;
    private final Scene scene = new Scene(new VBox(), 300, 200);
    private final GridPane gridPane = new GridPane();
    private final GridPane buttonPane = new GridPane();
    private final Stage stage = new Stage();
    static Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
    static Button abbort = new Button(StockGameUI.rb.getString("Button_abbort"));
    private final ComboBox playerDropDown = new ComboBox();
    private static ObservableList<Player> playerDropDownData = FXCollections.observableArrayList();
    String playerName;
        
    @Override
    public void start(Stage secondaryStage){
        secondaryStage.setTitle(StockGameUI.rb.getString("MenuItem_createPlayer"));
        gridPane.setVgap(10);
        gridPane.setHgap(50);
        buttonPane.setHgap(20);
        
        Text inputName = new Text(StockGameUI.rb.getString("Text_inputName"));
        gridPane.add(inputName, 1, 1);
        
        playerDropDownData.clear();
        StockGameUI.am.getPlayer().stream().forEach((p) -> {
            playerDropDownData.add(p);
        });
        playerDropDown.setItems(playerDropDownData);
        playerDropDown.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(playerDropDown, 1, 2);
        
        Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
        ok.setDefaultButton(true);
        Button abbort = new Button(StockGameUI.rb.getString("Button_abbort"));
        buttonPane.add(ok, 1, 1);
        buttonPane.add(abbort, 2, 1);
        gridPane.add(buttonPane, 1, 3);
        
        ((VBox) scene.getRoot()).getChildren().add(gridPane);
        secondaryStage.setScene(scene);
        
        abbort.setOnAction((ActionEvent t) -> {
            secondaryStage.close();
        });
        
        ok.setOnAction((ActionEvent t) -> {
            try {
                StockGameUI.am.removePlayer(playerName);
                StockGameUI.listPlayer();
                StockGameUI.status.setTextFill(Color.web("green"));
                StockGameUI.status.setText(StockGameUI.rb.getString("Status_deletePlayerSuccess"));
                secondaryStage.close();
            } catch (Exception ex) {
                StockGameUI.status.setTextFill(Color.web("red"));
                StockGameUI.status.setText(StockGameUI.rb.getString("Status_deletePlayerError"));
            }
            StockGameUI.listPlayer();
        });
                

        playerDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> ov, Player old, Player neu) {
                playerName = neu.getName();
            }
        });
    }
}