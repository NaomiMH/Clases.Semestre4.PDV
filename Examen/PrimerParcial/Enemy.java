/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Enemy extends Item{

    private Game game;
    private Animation animationLeft;    //to store the animation for going left
    
    public Enemy(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.animationLeft = new Animation(Assets.enemy1Left,100);
    }

    @Override
    public void tick() {
        setX(getX()-1);
        animationLeft.tick();
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animationLeft.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
}
