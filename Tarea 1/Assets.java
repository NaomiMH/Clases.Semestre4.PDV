/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author NamiDW
 */
public class Assets {
    public static BufferedImage background; //to store background image
    public static BufferedImage player; //to store the player image
    public static BufferedImage player_choque; //choque del juegador
    
    /**
     * initializing the images of the game
     */
    public static void init(){
        background = ImageLoader.loadImage("/images/Background.jpg");
        player = ImageLoader.loadImage("/images/d5.png");
        player_choque = ImageLoader.loadImage("/images/m1.png");
    }
    
}
