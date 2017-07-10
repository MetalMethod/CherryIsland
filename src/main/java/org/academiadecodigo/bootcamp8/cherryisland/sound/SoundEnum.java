package org.academiadecodigo.bootcamp8.cherryisland.sound;

/**
 * Created by codecadet on 09/07/2017.
 */
public enum SoundEnum {

    MENU("/src/main/resources/sound/menu.wav"),
    WAITING("/src/main/resources/sound/waiting.wav"),
    SOUNDTRACK("/src/main/resources/sound/ImGonnaBe(500Miles).wav"),
    PUNCH("/src/main/resources/sound/punch.wav"),
    WOOD("/src/main/resources/sound/wood.wav"),
    PICKING("/src/main/resources/sound/bush_rustling.wav"),
    DRINK("/src/main/resources/sound/drink.wav"),
    WIN("/src/main/resources/sound/win.wav"),
    LOSE("/src/main/resources/sound/lose.wav");

    private String path;

    SoundEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

