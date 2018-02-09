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
    public static BufferedImage sprites;    //to store the sprites
    public static BufferedImage sprites2;   //to store the sprites
    public static BufferedImage playerUp[]; //pictures to go up
    public static BufferedImage playerDown[];   //pictures to go down
    public static BufferedImage playerLeft[];   //pictures to go left
    public static BufferedImage playerRight[];  //pictures to go right
    
    /**
     * initializing the images of the game
     */
    public static void init(){
        background = ImageLoader.loadImage("/images/Background.jpg");
        //getting the sprites from the picture
        sprites = ImageLoader.loadImage("/images/personaje.png");
        sprites2 = ImageLoader.loadImage("/images/personaje2.png");
        //creating array of images before animations
        SpreadSheet spritesheet = new SpreadSheet(sprites);
        SpreadSheet spritesheet2 = new SpreadSheet(sprites2);
        playerLeft = new BufferedImage[9];
        playerUp= new BufferedImage[6];
        playerRight = new BufferedImage[9];
        playerDown = new BufferedImage[6];
        //croping the pictures from the sheet into the array
        for(int i=0; i<6; i++){
            playerUp[i] = spritesheet.crop(i*80,5*80,80,80);
        }
        for(int i=1; i<=9; i++){
            playerLeft[i-1] = spritesheet2.crop(1024-i*80,0,80,80);
        }
        for(int i=0; i<6; i++){
            playerDown[i] = spritesheet.crop(i*80+6*80,5*80,80,80);
        }
        for(int i=0; i<9; i++){
            playerRight[i] = spritesheet.crop(i*80,0,80,80);
        }
    }
    
}
