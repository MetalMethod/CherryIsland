package org.academiadecodigo.bootcamp8.cherryisland.model;

import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.awt.image.DirectColorModel;

/**
 * Created by dgcst on 30/06/17.
 */
public class Player {

    private GridPosition position;
    private String username;
    private int woodcount;
    private int health;

    public int getHealth() {
        return health;
    }

    public void raiseHealth(int amount) {
        health=health+amount;
    }

    public void loseHealth(){
        health--;
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private Direction direction;

    public Player(int col, int row) {
        //this.username=username;
        position =  new GridPosition(col , row);
        setPosition(col,row);
        health= U.PLAYER_INITHEALTH;
        woodcount=0;

    } // Instanciation -> Player player = new Player(new GridPosition(col, row));

    public GridPosition getPosition() {
        return position;
    }

    public void setPosition(int col, int row) {
        position.setCol(col);
        position.setRow(row);
    }

    public int getWood2(){//TODO rename
        return woodcount;
    }

    public void getWood(){//TODO rename
        woodcount++;
    }

    public int getWoodcount(){
        return woodcount;
    }

    public boolean buildBoat(){
        if(woodcount >= U.WOODFORBOAT){
            return true;
        }
        return false;
    }
}
