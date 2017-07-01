package org.academiadecodigo.bootcamp8.cherryone.service;

import org.academiadecodigo.bootcamp8.cherryone.model.GridPosition;
import org.academiadecodigo.bootcamp8.cherryone.model.Player;

/**
 * Created by dgcst on 30/06/17.
 */

public class Game {

    private Player player;

    public Game(int startingCol, int startingRow) {
        player = new Player(new GridPosition(startingCol, startingRow));
    }

    public void movePlayer(int col, int row) {
        player.setPosition(col, row);
    }
}
