/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author NamiDW
 */
public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean izquierdo;  //to check if left has been pushed
    private boolean derecho;    //to check if right has been pushed
    private int x;              //to get x position of the mouse
    private int y;              //to get y position of the mouse
    
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON1){
            izquierdo = false;
            x = me.getX();
            y = me.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        izquierdo = true;
        x = me.getX();
        y = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

    public void setDerecho(boolean derecho) {
        this.derecho = derecho;
    }

    public void setIzquierdo(boolean izquierdo) {
        this.izquierdo = izquierdo;
    }

    public boolean isDerecho() {
        return derecho;
    }

    public boolean isIzquierdo() {
        return izquierdo;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    
}
