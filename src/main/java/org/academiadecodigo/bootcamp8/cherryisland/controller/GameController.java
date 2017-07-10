package org.academiadecodigo.bootcamp8.cherryisland.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.academiadecodigo.bootcamp8.cherryisland.model.Direction;
import org.academiadecodigo.bootcamp8.cherryisland.model.Player;
import org.academiadecodigo.bootcamp8.cherryisland.service.Game;
import org.academiadecodigo.bootcamp8.cherryisland.util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Game game;
    private Player player;
    private Image up0;
    private Image up1;
    private Image down0;
    private Image down1;
    private Image left0;
    private Image left1;
    private Image right0;
    private Image right1;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Pane pane;

    @FXML
    private ImageView playerAvatar;

    @FXML
    private Label boatRopeCounterMax;

    @FXML
    private Label boatRopeCounter;

    @FXML
    private Label boatWoodCounterMax;

    @FXML
    private Label boatWoodCounter;

    @FXML
    private Label woodCounter;

    @FXML
    private Label woodCounterMax;

    @FXML
    private Label ropeCounter;

    @FXML
    private Label ropeCounterMax;

    @FXML
    private Label cherryCounter;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private ImageView howToPlay;

    @FXML
    private ImageView playerImage;

    //private Rectangle enemy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        woodCounterMax.setText("/ " + Utils.MAX_WOOD_CARRY);
        ropeCounterMax.setText("/ "+ Utils.MAX_ROPE_CARRY);
        boatRopeCounterMax.setText("/ "+Utils.ROPE_FOR_BOAT);
        boatWoodCounterMax.setText("/ "+Utils.WOOD_FOR_BOAT);

        scrollPane.setVmax(2500-725);
        scrollPane.setHmax(2500-725);
        scrollPane.setPannable(false);

        up0 = new Image("/characters/rambo/player_up0.png");
        up1 = new Image("/characters/rambo/player_up1.png");
        down0 = new Image("/characters/rambo/player_front0.png");
        down1 = new Image("/characters/rambo/player_front1.png");
        left0 = new Image("/characters/rambo/player_left0.png");
        left1 = new Image("/characters/rambo/player_left1.png");
        right0 = new Image("/characters/rambo/player_right0.png");
        right1 = new Image("/characters/rambo/player_right1.png");
    }

    @FXML
    private void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("-------------");

        switch (event.getCode()){

            case UP:
                player.setDirection(Direction.UP);
                System.out.println("UP");

                if (player.getPosition().getRow() == 14 ||
                        (!game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow()-1)
                                + player.getPosition().getCol()].equals("empty")&&
                                !game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow()-1)
                                        + player.getPosition().getCol()].equals("boat"))){
                    updateSprite(player.getDirection());
                    return;
                }

                player.setPosition(player.getPosition().getCol(), player.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - Utils.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player.getPosition().getCol()+" "+ player.getPosition().getRow());
                player.loseHealth();
                updateSprite(player.getDirection());
                break;

            case DOWN:
                player.setDirection(Direction.DOWN);
                System.out.println("DOWN");

                if (player.getPosition().getRow() == 85 ||
                        (!game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow()+1)
                                + player.getPosition().getCol()].equals("empty")&&
                                !game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow()+1)
                                        + player.getPosition().getCol()].equals("boat"))){
                    updateSprite(player.getDirection());
                    return;
                }

                player.setPosition(player.getPosition().getCol(), player.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + Utils.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player.getPosition().getCol()+" "+ player.getPosition().getRow());
                player.loseHealth();
                updateSprite(player.getDirection());
                break;

            case LEFT:
                player.setDirection(Direction.LEFT);
                System.out.println("LEFT");

                if (player.getPosition().getCol() == 14 ||
                        (!game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow())
                                + player.getPosition().getCol()-1].equals("empty")&&
                                !game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow())
                                        + player.getPosition().getCol()-1].equals("boat"))){
                    updateSprite(player.getDirection());
                    return;
                }

                player.setPosition(player.getPosition().getCol() - 1, player.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - Utils.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player.getPosition().getCol()+" "+ player.getPosition().getRow());
                player.loseHealth();
                updateSprite(player.getDirection());
                break;

            case RIGHT:
                player.setDirection(Direction.RIGHT);
                System.out.println("RIGHT");

                if (player.getPosition().getCol() == 85 ||
                        (!game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow())
                                + player.getPosition().getCol()+1].equals("empty")&&
                                !game.getPositionContents()[Utils.GRID_COLS*(player.getPosition().getRow())
                                        + player.getPosition().getCol()+1].equals("boat"))){
                    updateSprite(player.getDirection());
                    return;
                }

                player.setPosition(player.getPosition().getCol() + 1 , player.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + Utils.COL_ROW_SIZE);

                System.out.println("COL: " + player.getPosition().getCol());
                System.out.println("ROW: " + player.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player.getPosition().getCol()+" "+ player.getPosition().getRow());
                player.loseHealth();
                updateSprite(player.getDirection());
                break;

            case E:
                System.out.println("Trying to take action");

                game.takeAction();

                break;
            case I:
                if (howToPlay.isVisible()) {
                    howToPlay.setVisible(false);
                    return;
                }
                howToPlay.setVisible(true);
                break;
        }
        game.checkPlayerHealth();
        hpBar.setProgress((double) player.getHealth()/Utils.PLAYER_INIT_HEALTH);
        System.out.println("Player health: -------- " + player.getHealth());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void scrollPaneRequest(){
        scrollPane.requestFocus();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Label getWoodCounter() {
        return woodCounter;
    }

    public Label getRopeCounter(){
        return ropeCounter;
    }

    public Label getBoatRopeCounter(){return boatRopeCounter;}

    public Label getBoatWoodCounter(){return boatWoodCounter;}

    /* Minimap
    public void setEnemyPos(int row, int col) {
        enemy.setX( col + 8);//magic number is horizontal padding
        enemy.setY( row + 8);//magic number is vertical padding
    }
    */

    private void updateSprite(Direction dir){
        switch (dir){
            case DOWN:
                if(playerImage.getImage().equals(down0)){
                    playerImage.setImage(down1);
                } else {
                    playerImage.setImage(down0);
                }
                break;
            case UP:
                if(playerImage.getImage().equals(up0)){
                    playerImage.setImage(up1);
                } else {
                    playerImage.setImage(up0);
                }
                break;
            case LEFT:
                if(playerImage.getImage().equals(left0)){
                    playerImage.setImage(left1);
                } else {
                    playerImage.setImage(left0);
                }
                break;
            case RIGHT:
                if(playerImage.getImage().equals(right0)){
                    playerImage.setImage(right1);
                } else {
                    playerImage.setImage(right0);
                }
                break;
        }
    }

    public Pane getPane() {
        return pane;
    }
}

