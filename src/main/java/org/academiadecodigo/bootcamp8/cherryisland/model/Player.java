package org.academiadecodigo.bootcamp8.cherryisland.model;

import org.academiadecodigo.bootcamp8.cherryisland.util.U;

/**
 * Created by dgcst on 30/06/17.
 */
public class Player {

    private GridPosition position;
    private String username;
    private int woodCount;
    private Direction direction;
    private int woodInBoat;

    private int health;

    public Player(int col, int row) {
        //this.username=username;
        position = new GridPosition(col, row);
        setPosition(col, row);
        health = U.PLAYER_INIT_HEALTH;
        woodCount = 0;
        woodInBoat=0;

    } // Instanciation -> Player player = new Player(new GridPosition(col, row));

    public int getHealth() {
        return health;
    }

    public void raiseHealth(int amount) {
        if (health < U.PLAYER_INIT_HEALTH) {
            health = health + amount;
            if (health > U.PLAYER_INIT_HEALTH) {
                health = U.PLAYER_INIT_HEALTH;
            }
        }
        System.out.println("Player health: "+health);
    }

    public void loseHealth() {
        health--;
        System.out.println("Player health: "+health);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public GridPosition getPosition() {
        return position;
    }

    public void setPosition(int col, int row) {
        position.setCol(col);
        position.setRow(row);
    }

    public int getWoodCounter() {
        return woodCount;
    }

    public void pickWood() {
        if (!carryMoreWood()) {
            return;
        }
        woodCount++;
        System.out.println("Wood count: " + woodCount);
    }

    public boolean carryMoreWood() {
        return woodCount < U.MAX_WOOD_CARRY;
    }

    public int getWoodCount(){
        return woodCount;
    }

    public boolean buildBoat() {
        return woodInBoat >= U.WOOD_FOR_BOAT;
    }

    public void depositWood(){
        woodInBoat=woodInBoat+woodCount;
        woodCount=0;
    }
}
