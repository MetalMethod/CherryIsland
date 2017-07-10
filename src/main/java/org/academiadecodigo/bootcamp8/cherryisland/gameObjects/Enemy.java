package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;

/**
 * Created by codecadet on 08/07/17.
 */

public class Enemy extends GameObject {

    public Enemy(GridPosition gridPosition) {
        super(gridPosition, ObjectType.ENEMY);
    }
}
