package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;

/**
 * Created by joelalmeida on 04/07/17.
 */
public class GameObjectFactory {

    public static GameObject getObject(ObjectType objectType, GridPosition gridPosition) {

        GameObject gameObject = null;

        switch (objectType) {

            case LAKE:
                gameObject = new Lake(gridPosition);
                break;

            case TREE:
                gameObject = new Tree(gridPosition);
                break;

            case CHERRIES:
                gameObject = new Cherries(gridPosition);
                break;
        }

        return gameObject;
    }
}
