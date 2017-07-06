package org.academiadecodigo.bootcamp8.cherryisland.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dgcst on 06/07/17.
 */

public class ServiceRegistry {

    private static ServiceRegistry instance = null;
    private Map<String, PlayerService> registry = new HashMap<>();

    private ServiceRegistry() {}

    public static synchronized ServiceRegistry getInstance() {
        if(instance == null) {
            instance = new ServiceRegistry();
        }
        return instance;
    }

    public void addService(String name, PlayerService service) {
        registry.put(name, service);
    }

    public PlayerService getService(String name) {
        return registry.get(name);
    }

    public void removeService(String name) {
        registry.remove(name);
    }
}