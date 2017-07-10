package org.academiadecodigo.bootcamp8.cherryisland.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgcst on 06/07/17.
 */

public class PlayerService {

    private static PlayerService instance = null;
    private List<String> players = new ArrayList<>();

    public PlayerService() {}

    public static synchronized PlayerService getInstance() {
        if(instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public void addPlayer(String nickname) {
        players.add(nickname);
    }

    public boolean playerExists(String nickname) {
        return players.contains(nickname);
    }
}
