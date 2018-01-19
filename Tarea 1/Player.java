/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author NamiDW
 */
public class Player extends Item{

    private int direction;
    private int width;
    private int height;
    private Game game;
    private boolean choque;
    private int contChoque;
    
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        choque = false;
        contChoque = 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void tick() {
        setY(getY()+game.getKeyManager().getVely());
        
        setX(getX()+game.getKeyManager().getVelx());
        
        //reset x position and y position if colision
        if(getX() + 60 > game.getWidth()){
            setX(game.getWidth() - 60);
            game.getKeyManager().setVelx(0);
            if(!choque)
                choque = true;
        }
        else if(getX() < -30){
            setX(-30);
            game.getKeyManager().setVelx(0);
            if(!choque)
                choque = true;
        }
        if(getY() + 80 > game.getHeight()){
            setY(game.getHeight() - 80);
            game.getKeyManager().setVely(0);
            if(!choque)
                choque = true;
        }
        else if(getY() < -20){
            setY(-20);
            game.getKeyManager().setVely(0);
            if(!choque)
                choque = true;
        }
    }

    @Override
    public void render(Graphics g) {
        if(choque){
            g.drawImage(Assets.player_choque, getX(), getY(), getWidth(), getHeight(), null);
            contChoque++;
            if(contChoque == 25){
                choque = false;
                contChoque = 0;
            }
        }
        else{
            g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
