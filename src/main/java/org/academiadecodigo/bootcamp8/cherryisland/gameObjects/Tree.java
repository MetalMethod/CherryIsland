package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;

/**
 * Created by joelalmeida on 04/07/17.
 */
public class Tree extends GameObject{


    public Tree(GridPosition gridPosition) {
        super(gridPosition, ObjectType.TREE);
    }
}
