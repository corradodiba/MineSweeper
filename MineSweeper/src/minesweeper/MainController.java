package minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Field field = new Field();
    ArrayList<Tile> tiles = new ArrayList<>();
    Color[] colors = {null, Color.BLUE, Color.GREEN, Color.RED, Color.AZURE, Color.DARKGOLDENROD};
    Image bombImage = new Image("/minesweeper/bomb.png");
    Image flagImage = new Image("/minesweeper/flag.png");

    private int foundBombs = 0;

    @FXML
    VBox container;


    public class Tile extends StackPane {

        private int x;
        private int y;
        private Button button = new Button();
        private Color color;
        private Boolean active = true;
        private Boolean flagged = false;
        private Boolean isBomb = false;
        private int bombsAround = 0;
        private ArrayList<Tile> tilesAround;
        private ArrayList<int[]> coordsTilesAround;

        public Tile() {

            this.tilesAround = new ArrayList<>();
            this.button.setOnMouseClicked(e -> {
                onClick(e);
            });
            this.getChildren().add(button);
        }

        private void onClick(MouseEvent e) {

            //LEFT
            if(e.getButton() == MouseButton.PRIMARY){
                if(!this.flagged){
                    this.button.setBackground(null);
                    this.button.setDisable(true);
                    this.active = false;
                }

                if(this.isBomb){
                    this.button.setGraphic(new ImageView(bombImage));
                    gameOver();
                }
                else if(this.bombsAround == 0){
                    this.emptyTileClick(this);
                }
                else {
                    this.button.setGraphic(null);
                    this.button.setText(Integer.toString(this.bombsAround));
                    this.button.setTextFill(this.color);
                }
            }

            //RIGHT
            else if (!this.flagged) {

                    this.flagged = true;
                    this.button.setGraphic(new ImageView(flagImage));

                    if (this.isBomb) {
                        foundBombs++;
                        if (foundBombs == field.getBombs().size()) {  win(); }
                    }
                }
                else if (!this.isBomb) {
                    foundBombs--;
                    this.flagged = false;
                }
            }

        private void emptyTileClick(Tile tile){

            for(int i = 0; i < tile.tilesAround.size(); i++){
                if(tile.tilesAround.get(i).active){
                    tile.tilesAround.get(i).button.setDisable(true);
                    tile.tilesAround.get(i).button.setGraphic(null);
                    tile.tilesAround.get(i).button.setText(Integer.toString(tile.tilesAround.get(i).bombsAround));
                    tile.tilesAround.get(i).button.setTextFill(tile.tilesAround.get(i).color);
                    tile.tilesAround.get(i).active = false;
                    if(tile.tilesAround.get(i).bombsAround == 0){
                        this.emptyTileClick(tile.tilesAround.get(i));
                    }
                }
            }
        }

        public boolean contains(Tile t) {

            for(int i = 0; i < this.coordsTilesAround.size(); i++){
                int[] coord = this.coordsTilesAround.get(i);
                if(coord[0] == t.x && coord[1] == t.y){ return true; }
            }

            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.createContent();
    }

    public void createContent() {

        TilePane root = new TilePane();

        for(int x = 0; x < this.field.getGridSize(); x++){
            for(int y = 0; y < this.field.getGridSize(); y++){
                Tile tile = new Tile();
                tile.x = x;
                tile.y = y;
                if(this.field.findBombsByCoordinates(x, y)){ tile.isBomb = true; }
                tile.coordsTilesAround = this.field.getTilesNear(x, y);
                root.getChildren().add(tile);
                this.tiles.add(tile);
            }
        }

        for(Tile tile : this.tiles){
            for(Tile t : this.tiles){
                if(tile.contains(t)) { tile.tilesAround.add(t);}
                if(tile.contains(t) && t.isBomb) { tile.bombsAround++;}
            }
            if(tile.bombsAround > colors.length - 1){ tile.color = colors[colors.length - 1]; }
            tile.color = colors[tile.bombsAround];
        }

        this.container.getChildren().add(root);
    }

    public void gameOver() {

        for(Tile tile : this.tiles){
            if(tile.isBomb){
                tile.button.setGraphic(new ImageView(bombImage));
                tile.button.setDisable(true);
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle("Game Over!");
        alert.setGraphic(new ImageView(bombImage));
        alert.setHeaderText("You're a sucker");
        alert.setContentText("Oh no! Try again, you'll be luckier.");
        alert.showAndWait();
        this.tiles = new ArrayList<>();
        this.field = new Field();
        this.container.getChildren().remove(0);
        this.createContent();

    }

    public void win() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle("You are win!");
        alert.setGraphic(new ImageView(flagImage));
        alert.setHeaderText("Congratulations");
        alert.setContentText("You found all the bombs, thx for playing!");
        alert.showAndWait();
        this.tiles = new ArrayList<>();
        this.field = new Field();
        this.container.getChildren().remove(0);
        this.createContent();
    }


}
