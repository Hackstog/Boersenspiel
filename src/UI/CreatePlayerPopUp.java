/**
 * PlayerPopUp.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package UI;
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
import javafx.application.Application;

public class CreatePlayerPopUp extends Application{
    private final Scene scene = new Scene(new VBox(), 300, 200);
    private final GridPane gridPane = new GridPane();
    private final GridPane buttonPane = new GridPane();
    private final Stage stage = new Stage();
    static Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
    static Button abbort = new Button(StockGameUI.rb.getString("Button_abbort"));
        
    @Override
    public void start(Stage secondaryStage){
        secondaryStage.setTitle(StockGameUI.rb.getString("MenuItem_createPlayer"));
        gridPane.setVgap(10);
        gridPane.setHgap(50);
        buttonPane.setHgap(20);
        
        Text inputName = new Text(StockGameUI.rb.getString("Text_inputName"));
        gridPane.add(inputName, 1, 1);
        
        TextField input = new TextField();
        input.setPrefWidth(150);
        gridPane.add(input, 1, 2);
        
        Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
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
                String s = input.getText();
                StockGameUI.am.createPlayer(s);
            } catch (Exception ex) {
                Logger.getLogger(CreatePlayerPopUp.class.getName()).log(Level.SEVERE, null, ex);
            }
            StockGameUI.listPlayer();
            secondaryStage.close();
        });
    }
}