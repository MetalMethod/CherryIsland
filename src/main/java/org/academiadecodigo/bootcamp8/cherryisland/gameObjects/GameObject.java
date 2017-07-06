package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;

/**
 * Created by joelalmeida on 04/07/17.
 */
abstract public class GameObject {

    private GridPosition gridPosition;
    private ObjectType objectType;

    public GameObject(GridPosition gridPosition, ObjectType objectType){
        this.gridPosition=gridPosition;
        this.objectType=objectType;
    }

    public GridPosition getGridPosition(){
        return gridPosition;
    }
}
