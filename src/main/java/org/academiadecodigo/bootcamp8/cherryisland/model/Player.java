package org.academiadecodigo.bootcamp8.cherryisland.model;

import org.academiadecodigo.bootcamp8.cherryisland.util.Utils;

/**
 * Created by dgcst on 30/06/17.
 */
public class Player {

    private GridPosition position;
    private String username;
    private int woodCount;
    private Direction direction;
    private int woodInBoat;
    private int ropeCount;
    private int ropeInBoat;

    private int health;

    public Player(int col, int row) {
        //this.username=username;
        position = new GridPosition(col, row);
        setPosition(col, row);
        health = Utils.PLAYER_INIT_HEALTH;
        woodCount = 0;
        woodInBoat=0;
        ropeCount = 0;
        ropeInBoat = 0;

    } // Instanciation -> Player player = new Player(new GridPosition(col, row));

    public int getHealth() {
        return health;
    }

    public void raiseHealth(int amount) {
        if (health < Utils.PLAYER_INIT_HEALTH) {
            health = health + amount;
            if (health > Utils.PLAYER_INIT_HEALTH) {
                health = Utils.PLAYER_INIT_HEALTH;
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

    public int getRopeCount() {
        return ropeCount;
    }

    public void pickWood() {
        if (!carryMoreWood()) {
            return;
        }
        woodCount++;
        System.out.println("Wood count: " + woodCount);
    }

    public boolean carryMoreWood() {
        return woodCount < Utils.MAX_WOOD_CARRY;
    }

    public boolean carryMoreRope(){
        return ropeCount < Utils.MAX_ROPE_CARRY;
    }

    public void pickRope(){
        if (!carryMoreRope()){
            return;
        }
        ropeCount++;
        System.out.println("Rope count: " + ropeCount);
    }

    public boolean buildBoat() {
        return ((woodInBoat >= Utils.WOOD_FOR_BOAT) && (ropeInBoat >= Utils.ROPE_FOR_BOAT));
    }

    public void depositWood(){
        if (woodInBoat >= Utils.WOOD_FOR_BOAT){
            return;
        }
        woodInBoat=woodInBoat+woodCount;
        woodCount=0;
    }

    public void depositRope(){
        if (ropeInBoat >= Utils.ROPE_FOR_BOAT){
            return;
        }
        ropeInBoat+=ropeCount;
        ropeCount=0;
    }
}
