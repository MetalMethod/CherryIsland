package org.academiadecodigo.bootcamp8.cherryisland.model;

/**
 * Created by dgcst on 30/06/17.
 */
public class Player {

    private GridPosition position;
    private String username;

    public Player(int col, int row, String username) {
        this.username=username;
        position =  new GridPosition(col , row);
        setPosition(col,row);

    } // Instanciation -> Player player = new Player(new GridPosition(col, row));

    public GridPosition getPosition() {
        return position;
    }

    public void setPosition(int col, int row) {
        position.setCol(col);
        position.setRow(row);
    }
}
