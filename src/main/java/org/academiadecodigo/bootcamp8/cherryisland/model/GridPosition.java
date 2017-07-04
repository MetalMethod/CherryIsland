package org.academiadecodigo.bootcamp8.cherryisland.model;

/**
 * Created by dgcst on 30/06/17.
 */

public class GridPosition {

    private int col;
    private int row;

    public GridPosition(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
