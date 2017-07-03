package org.academiadecodigo.bootcamp8.cherryone.service;

import org.academiadecodigo.bootcamp8.cherryone.model.GridPosition;
import org.academiadecodigo.bootcamp8.cherryone.model.Player;

/**
 * Created by dgcst on 30/06/17.
 */

public class Game {

    private Player player;

    public Game() {


    }

    public void movePlayer(int col, int row) {
        player.setPosition(col, row);
    }

    public boolean comparePosition(GridPosition g1, GridPosition g2){

        if (g1.getCol() == g2.getCol() || g1.getRow() == g2.getRow()){
            return true;
        }

        return false;

    }
}
