package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Field {

    private int[][] grid;
    private ArrayList<int []> bombs; //the quantity is equal to gridSize

    int gridSize = 10;

    public Field() {
        this.grid = new int[gridSize][gridSize];
        this.bombs = new ArrayList<>();

        this.setBombsCoordinates();


    }

    public void setBombsCoordinates(){

        Random r = new Random();


        for(int i = 0; i < this.gridSize; i++){
            int[] coordinates = new int[2];
            coordinates[0] = r.nextInt(gridSize);
            coordinates[1] = r.nextInt(gridSize);
            this.bombs.add(coordinates);
            this.grid[coordinates[0]][coordinates[1]] = 9;
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    public ArrayList<int[]> getBombs() {
        return bombs;
    }

    public boolean findBombsByCoordinates(int x, int y) {

        for(int[] bomb : this.bombs){
            if((bomb[0] == x) && (bomb[1] == y)){ return true; }
        }
        return false;
    }

    public ArrayList<int[]> getTilesNear(int x, int y){

        ArrayList<int[]> tilesAround = new ArrayList<>();

        for (int j = x-1; j <= x+1; j++) {
            if (j < 0 || j > this.gridSize - 1) continue;
            for (int z = y-1 ; z <= y+1; z++) {
                if (z < 0 || z > this.gridSize || (j==x && z == y)) continue;
                tilesAround.add(new int[]{j,z});
            }
        }
        return tilesAround;
    }

    @Override
    public String toString() {

        String s = "";

        for(int y = 0; y < this.grid.length; y++){
            for(int x = 0; x < this.grid.length; x++){
                s += this.grid[x][y] + " | ";
            }
            s += "\n";
        }

        return s;
    }
}
