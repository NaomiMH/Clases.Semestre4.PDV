/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author NamiDW
 */
public class KeyManager implements KeyListener{
    public boolean up;      //flag to move up the player
    public boolean down;    //flag to move down the player
    public boolean left;    //flag to move left the player
    public boolean right;   //flag to move right the player
    private int velx;       //velocidad en el eje x (negativo izquierda, positivo derecha)
    private int vely;       //velocidad en el eje y (negativo arriba, positivo abajo)
    
    private boolean keys[]; //to store all the flags for every key
    
    public KeyManager(){
        keys = new boolean[256];
        velx = 0;
        vely = 0;
    }

    public void setVely(int vely) {
        if(vely == 0)
            this.vely = 0;
        this.vely += vely;
    }

    public void setVelx(int velx) {
        if(velx == 0)
            this.velx = 0;
        this.velx += velx;
    }

    public int getVely() {
        return vely;
    }

    public int getVelx() {
        return velx;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //set true to every key pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //set false to every key released
        if(keys[KeyEvent.VK_UP]){
            setVely(-1);
        }
        else if(keys[KeyEvent.VK_DOWN]){
            setVely(1);
        }
        else if(keys[KeyEvent.VK_LEFT]){
            setVelx(-1);
        }
        else if(keys[KeyEvent.VK_RIGHT]){
            setVelx(1);
        }
        keys[e.getKeyCode()] = false;
    }
    
    public void tick(){
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
    }
}
