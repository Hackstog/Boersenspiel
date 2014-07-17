/**
 * AboutPopUp.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package UI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class AboutPopUp extends Application{
    private final Scene scene = new Scene(new VBox(), 800, 600);
    private final GridPane gridPane = new GridPane();
    private final Stage stage = new Stage();
    
    @Override
    public void start(Stage secondaryStage){
        secondaryStage.setTitle(StockGameUI.rb.getString("MenuItem_about"));
        gridPane.setVgap(10);
        gridPane.setHgap(50);
        
        Text title = new Text(StockGameUI.rb.getString("WindowTitle"));
        title.setFont(new Font(24));
        Text version = new Text("Version:   10.0");
        Text hsa = new Text("Hochschule Augsburg");
        Text praktikum = new Text("Praktikum Programmieren 2 - Studiengang Bachelor Informatik");
        Text ss = new Text("Sommersemester 2014");
        Text autor = new Text("Autor:     Daniel Heigl");
        Text email = new Text("               daniel.heigl@hs-augsburg.de");
        Button ok = new Button(StockGameUI.rb.getString("Button_ok"));
        
        gridPane.add(title, 1, 1);
        gridPane.add(version, 1, 2);
        gridPane.add(hsa, 1, 4);
        gridPane.add(praktikum, 1, 5);
        gridPane.add(ss, 1, 6);
        gridPane.add(autor, 1, 9);
        gridPane.add(email, 1, 10);
        gridPane.add(ok, 1, 12);

        
        ((VBox) scene.getRoot()).getChildren().add(gridPane);
        
        ok.setOnAction((ActionEvent t) -> {
            secondaryStage.close();
        });
        
        secondaryStage.setScene(scene);
    }
}