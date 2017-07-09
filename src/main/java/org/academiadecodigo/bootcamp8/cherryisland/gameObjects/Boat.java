package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;

/**
 * Created by codecadet on 09/07/17.
 */
public class Boat extends GameObject{

    public Boat(GridPosition gridPosition) {
        super(gridPosition, ObjectType.CHERRIES);

    }
}
