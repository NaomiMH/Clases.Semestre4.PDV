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
public class Vidas extends Item{

    private int width;
    private int height;
    private Game game;
    private boolean mostrar;
    
    public Vidas(int x, int y, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        mostrar = true;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        if(isMostrar())
            g.drawImage(Assets.vidas, getX(), getY(), getWidth(), getHeight(), null);
    }
}
