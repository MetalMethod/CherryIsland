package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import javafx.scene.image.ImageView;
import org.academiadecodigo.bootcamp8.cherryisland.model.GridPosition;



/**
 * Created by joelalmeida on 04/07/17.
 */
public class GameObjectFactory {

    public static GameObject getObject(ObjectType objectType, GridPosition gridPosition) {

        GameObject gameObject = null;
        ImageView imageView;

        switch (objectType) {

            case LAKE:
                gameObject = new Lake(gridPosition);
                //imageView =  new ImageView("path to lake IMG");
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
