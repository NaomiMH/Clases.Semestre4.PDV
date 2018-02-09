/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author NamiDW
 */
public class Player extends Item{

    private int direction;
    private int width;
    private int height;
    private Game game;
    private Animation animationUp;  //to store the animation for going up
    private Animation animationLeft;    //to store the animation for going left
    private Animation animationDown;    //to store the animation for going down
    private Animation animationRight;   //to store the animation for going righ
    private boolean choque;
    private int contChoque;
    
    /**
     * To build a Player object
     * @param x an <code>int</code> value to get the x coordinate
     * @param y an <code>int</code> value to get the y coordinate
     * @param direction an <code>int</code> value to get the direction (0 = Down, 1 = Left, 2 = Up, 3 = Right)
     * @param width an <code>int</code> value to get the width
     * @param height an <code>int</code> value to get the height
     * @param game an <code>int</code> value to get outside elements
     */
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        
        this.animationUp = new Animation(Assets.playerUp,100);
        this.animationLeft = new Animation(Assets.playerLeft,100);
        this.animationDown = new Animation(Assets.playerDown,100);
        this.animationRight = new Animation(Assets.playerRight,100);
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
        //moving player depending on flags
        if(game.getMouseManager().isIzquierdo() && !getBounds().contains(game.getMouseManager().getX(),game.getMouseManager().getY()) && !choque && !game.isChoque()){
            setX(game.getMouseManager().getX()-40);
            setY(game.getMouseManager().getY()-40);
        }
        if(game.getKeyManager().up && !choque){
            setY(getY()-1);
            //updating animation
            this.animationUp.tick();
            setDirection(2);
        }
        if(game.getKeyManager().down && !choque){
            setY(getY()+1);
            //updating animation
            this.animationDown.tick();
            setDirection(0);
        }
        if(game.getKeyManager().left && !choque){
            setX(getX()-1);
            //updating animation
            this.animationLeft.tick();
            setDirection(1);
        }
        if(game.getKeyManager().right && !choque){
            setX(getX()+1);
            //updating animation
            this.animationRight.tick();
            setDirection(3);
        }
        //reset x position and y position if colision
        if(getX() + 70 > game.getWidth()){
            choque = true;
        }
        else if(getX() < -30){
            choque = true;
        }
        if(getY() + 70 > game.getHeight()){
            choque = true;
        }
        else if(getY() < -10){
            choque = true;
        }
    }
    
    public void reubicar(){
        if(getX() + 70 > game.getWidth()){
            setX(game.getWidth() - 80);
        }
        else if(getX() < -30){
            setX(-10);
        }
        if(getY() + 70 > game.getHeight()){
            setY(game.getHeight() - 80);
        }
        else if(getY() < -10){
            setY(0);
        }
    }
    
    public Rectangle getBounds(){
        return new Rectangle(getX()+30,getY()+30,getWidth()-60,getHeight()-60);
    }

    @Override
    public void render(Graphics g) {
        if(choque){
            if(contChoque==0)
                game.getChoqueLimites().play();
            g.drawImage(Assets.playerChoque3, getX(), getY(), getWidth(), getHeight(), null);
            contChoque++;
            if(contChoque == 40){
                contChoque = 0;
                reubicar();
                choque = false;
            }
        }
        else{
            if(game.getKeyManager().up)
                g.drawImage(animationUp.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
            else if(game.getKeyManager().down)
                g.drawImage(animationDown.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
            else if(game.getKeyManager().right)
                g.drawImage(animationRight.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
            else if(game.getKeyManager().left)
                g.drawImage(animationLeft.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
            else if(getDirection()==0)
                g.drawImage(animationDown.getFrames(0), getX(), getY(), getWidth(), getHeight(), null);
            else if(getDirection()==1)
                g.drawImage(animationLeft.getFrames(0), getX(), getY(), getWidth(), getHeight(), null);
            else if(getDirection()==2)
                g.drawImage(animationUp.getFrames(0), getX(), getY(), getWidth(), getHeight(), null);
            else if(getDirection()==3)
                g.drawImage(animationRight.getFrames(0), getX(), getY(), getWidth(), getHeight(), null);
        }
        
        
    }
}
