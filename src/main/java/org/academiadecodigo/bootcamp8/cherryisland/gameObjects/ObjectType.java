package org.academiadecodigo.bootcamp8.cherryisland.gameObjects;

import java.util.Arrays;
import java.util.List;

/**
 * Created by joelalmeida on 04/07/17.
 */

public enum ObjectType {
    BOAT(Arrays.asList(new String[]{"/game_objects/boat.png"}), "boat"),
    TREE(Arrays.asList(new String[]{"/game_objects/tree1.png", "/game_objects/tree2.png", "/game_objects/tree3.png"}), "tree"),
    LAKE(Arrays.asList(new String[]{"/game_objects/lake1.png", "/game_objects/lake2.png", "/game_objects/lake3.png"}), "lake"),
    CHERRIES(Arrays.asList(new String[]{"/game_objects/cherrytree1.png", "/game_objects/cherrytree2.png", "/game_objects/cherrytree3.png"}), "cherries"),
    ROCK(Arrays.asList(new String[]{"/game_objects/rock.png"}), "rock"),
    ROPE(Arrays.asList(new String[]{"/game_objects/rope.png"}), "rope"),
    ENEMY(Arrays.asList(new String[]{"/characters/castaway/player_front0.png"}), "enemy");

    private List<String> path;
    private String name;

    ObjectType(List<String> path, String name) {
        this.path = path;
        this.name = name;
    }

    public List<String> getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
