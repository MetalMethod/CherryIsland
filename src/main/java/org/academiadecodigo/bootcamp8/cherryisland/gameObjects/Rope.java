package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;

/**
 * Created by joelalmeida on 10/07/17.
 */

public class Rope extends GameObject {

    public Rope(GridPosition gridPosition) {
        super(gridPosition, ObjectType.ROPE);
    }
}
