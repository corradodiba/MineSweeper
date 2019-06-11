package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mineSweeper;
    private Scene field;
    int stageWidth = 600, stageHeight = 600;

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("field.fxml"));
        mineSweeper = stage;
        field = new Scene(root);
        mineSweeper.setTitle("MineSweeper by D.Corrado, Z,Gabriele, T.Davide");
        field.getStylesheets().add("/minesweeper/field.css");
        mineSweeper.setScene(field);
        mineSweeper.setMinWidth(stageHeight);
        mineSweeper.setMinHeight(stageWidth);
        mineSweeper.setResizable(false);
        mineSweeper.show();

        Field f = new Field();
        System.out.println(f);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
