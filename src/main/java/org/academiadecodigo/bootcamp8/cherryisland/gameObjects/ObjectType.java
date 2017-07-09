package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

/**
 * Created by joelalmeida on 04/07/17.
 */
public enum ObjectType {
    TREE("/gameobjects/tree1.png","tree"),
    LAKE("/gameobjects/lake1.png","lake"),
    CHERRIES("/gameobjects/cherrytree1.png","cherries"),
    ENEMY("/characters/castaway/player_front0.png","enemy"),
    BOAT("/gameobjects/boat.png","boat");

    private String path;
    private String name;

    ObjectType(String path, String name){
        this.path=path;
        this.name=name;
    }

    public String getPath(){
        return path;
    }

    public String getName() {return name;}

    public static ObjectType getObjectType(String path){
        for (ObjectType type: ObjectType.values()){
            if (path.equals(type.getPath())){
                return type;
            }
        } return null;
    }

}
