package org.academiadecodigo.bootcamp8.cherryisland.sound;

/**
 * Created by codecadet on 09/07/2017.
 */
public enum SoundEnum {

    WAITING("/src/main/resources/sound/waiting.wav"),
    PUNCH("/src/main/resources/sound/punch.wav"),
    WOOD("/src/main/resources/sound/wood.wav"),
    PICKING("/src/main/resources/sound/bust rustling.wav"),
    SOUNDTRACK("/src/main/resources/sound/Im Gonna Be (500 Miles).wav");

    private String path;

    SoundEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

