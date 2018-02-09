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
public class Vida extends Item{

    private Game game;
    private boolean mostrar;
    
    public Vida(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        mostrar = true;
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
            g.drawImage(Assets.vida, getX(), getY(), getWidth(), getHeight(), null);
    }
}
