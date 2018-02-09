/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {
    public static BufferedImage background; // to store background image
    public static BufferedImage player;     // to store the player image
    public static BufferedImage playerDown[];
    public static BufferedImage playerRight[];
    public static BufferedImage playerUp[];
    public static BufferedImage playerLeft[];
    public static BufferedImage sprites_Player;
    public static BufferedImage enemy;
    public static BufferedImage sprites_Enemy;
    public static BufferedImage vida;
    public static BufferedImage sprites_Vida;

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/Background.jpg");
        sprites_Player = ImageLoader.loadImage("/images/Greedy_Mouse.png");
        sprites_Enemy = ImageLoader.loadImage("/images/tuna_sprite.png");
        sprites_Vida = ImageLoader.loadImage("/images/personaje.png");
        SpreadSheet spritesheet_Player = new SpreadSheet(sprites_Player);
        player = spritesheet_Player.crop(0,0,48,48);
        playerLeft = new BufferedImage[4];
        playerUp= new BufferedImage[4];
        playerRight = new BufferedImage[4];
        playerDown = new BufferedImage[4];
        for(int i=0;i<4;i++){
            playerUp[i]=spritesheet_Player.crop(i*48,3*48,48,48);
            playerLeft[i]=spritesheet_Player.crop(i*48,1*48,48,48);
            playerRight[i]=spritesheet_Player.crop(i*48,2*48,48,48);
            playerDown[i]=spritesheet_Player.crop(i*48,0*48,48,48);
        }
        SpreadSheet spritesheet_Enemy = new SpreadSheet(sprites_Enemy);
        enemy = spritesheet_Enemy.crop(1*400, 5*199, 400, 199);
        SpreadSheet spritesheet_Vida = new SpreadSheet(sprites_Vida);
        vida = spritesheet_Vida.crop(10*80, 10*80, 2*80, 2*80);
    }
    
}
