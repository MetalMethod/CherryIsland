package org.academiadecodigo.bootcamp8.cherryisland.sound;

/**
 * Created by codecadet on 09/07/2017.
 */
public enum SoundEnum {

    WAITING("resources/sound/waiting.wav"),
    PUNCH("resources/sound/punch.wav"),
    WOOD("resources/sound/wood.wav"),
    PICKING("resources/sound/bust rustling.wav"),
    SOUNDTRACK("resources/sound/Im Gonna Be (500 Miles).wav");

    private String path;

    SoundEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

