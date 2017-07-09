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
import org.academiadecodigo.bootcamp8.cherryisland.util.U;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    public Pane getPane() {
        return pane;
    }

    @FXML
    private Pane pane;

    @FXML
    private ImageView playerAvatar;

    @FXML
    private Label logCounter;

    @FXML
    private Label cherryCounter;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private ImageView howToPlay;

    private Player player1;

    private Game game;

    @FXML
    private ImageView playerImage;

    private Image up0;
    private Image up1;
    private Image down0;
    private Image down1;
    private Image left0;
    private Image left1;
    private Image right0;
    private Image right1;

    //private Rectangle enemy;

    @FXML
    void scrollPaneKeyPressed(KeyEvent event) {
        System.out.println("-------------");

        switch (event.getCode()){

            case UP:
                player1.setDirection(Direction.UP);
                System.out.println("UP");

                if (player1.getPosition().getRow() == 14 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow()-1)
                                +player1.getPosition().getCol()].equals("empty")){
                    updateSprite(player1.getDirection());
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() - 1);
                scrollPane.setVvalue(scrollPane.getVvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                updateSprite(player1.getDirection());
                break;

            case DOWN:
                player1.setDirection(Direction.DOWN);
                System.out.println("DOWN");

                if (player1.getPosition().getRow() == 85 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow()+1)
                                +player1.getPosition().getCol()].equals("empty")){
                    updateSprite(player1.getDirection());
                    return;
                }

                player1.setPosition(player1.getPosition().getCol(), player1.getPosition().getRow() +1);
                scrollPane.setVvalue(scrollPane.getVvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                updateSprite(player1.getDirection());
                break;

            case LEFT:
                player1.setDirection(Direction.LEFT);
                System.out.println("LEFT");

                if (player1.getPosition().getCol() == 14 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow())
                                +player1.getPosition().getCol()-1].equals("empty")){
                    updateSprite(player1.getDirection());
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() - 1, player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() - U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                updateSprite(player1.getDirection());
                break;

            case RIGHT:
                player1.setDirection(Direction.RIGHT);
                System.out.println("RIGHT");

                if (player1.getPosition().getCol() == 85 ||
                        !game.getPositionContents()[U.GRID_COLS*(player1.getPosition().getRow())
                                +player1.getPosition().getCol()+1].equals("empty")){
                    updateSprite(player1.getDirection());
                    return;
                }

                player1.setPosition(player1.getPosition().getCol() + 1 , player1.getPosition().getRow());
                scrollPane.setHvalue(scrollPane.getHvalue() + U.COL_ROW_SIZE);

                System.out.println("COL: " + player1.getPosition().getCol());
                System.out.println("ROW: " + player1.getPosition().getRow());

                game.gameSend(game.getPlayerNumber()+" move "+
                        player1.getPosition().getCol()+" "+player1.getPosition().getRow());
                player1.loseHealth();
                updateSprite(player1.getDirection());
                break;

            case Z:
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
        hpBar.setProgress(player1.getHealth() * 0.01);
        System.out.println("Player healt: -------- " + player1.getHealth());
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

    public void setPlayer1(Player player1){
        this.player1 = player1;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Label getLogCounter() {
        return logCounter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setVmax(2500-725);
        scrollPane.setHmax(2500-725);
        scrollPane.setPannable(false);

        up0=new Image("/characters/rambo/player_up0.png");
        up1=new Image("/characters/rambo/player_up1.png");
        down0=new Image("/characters/rambo/player_front0.png");
        down1=new Image("/characters/rambo/player_front1.png");
        left0=new Image("/characters/rambo/player_left0.png");
        left1=new Image("/characters/rambo/player_left1.png");
        right0=new Image("/characters/rambo/player_right0.png");
        right1=new Image("/characters/rambo/player_right1.png");
    }

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
}

