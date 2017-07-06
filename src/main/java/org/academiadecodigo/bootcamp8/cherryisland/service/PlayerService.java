package org.academiadecodigo.bootcamp8.cherryisland.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgcst on 06/07/17.
 */

public class PlayerService {

    private List<String> players;

    public PlayerService() {
        players = new ArrayList<>();
    }

    public void addPlayer(String nickname) {
        players.add(nickname);
    }

    public void removePlayer(String nickname) {
        players.remove(nickname);
    }

    public boolean playerExists(String nickname) {
        return players.contains(nickname);
    }

    public String getName() {
        return getClass().getSimpleName();
    }

}
