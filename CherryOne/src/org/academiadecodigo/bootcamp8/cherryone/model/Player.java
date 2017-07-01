package org.academiadecodigo.bootcamp8.cherryone.model;

/**
 * Created by dgcst on 30/06/17.
 */
public class Player {

    private GridPosition position;

    public Player(GridPosition position) {
        this.position = position;
    } // Instanciation -> Player player = new Player(new GridPosition(col, row));

    public GridPosition getPosition() {
        return position;
    }

    public void setPosition(int col, int row) {
        position.setCol(col);
        position.setRow(row);
    }
}
