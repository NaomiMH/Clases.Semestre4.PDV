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
    public static BufferedImage playerDown[];
    public static BufferedImage playerRight[];
    public static BufferedImage playerUp[];
    public static BufferedImage playerLeft[];
    public static BufferedImage sprites_Player;
    public static BufferedImage sprites_Enemy;
    public static BufferedImage vida;
    public static BufferedImage sprites_Vida;
    public static BufferedImage enemy1Left[];
    public static BufferedImage backgroundPausa;
    public static BufferedImage gameover;
    public static BufferedImage ganaste;

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/Background.jpg");
        sprites_Player = ImageLoader.loadImage("/images/aves.png");
        sprites_Enemy = ImageLoader.loadImage("/images/gatos.png");
        sprites_Vida = ImageLoader.loadImage("/images/personaje.png");
        backgroundPausa = ImageLoader.loadImage("/images/cementerio.jpg");
        gameover = ImageLoader.loadImage("/images/gameover.jpg");
        ganaste = ImageLoader.loadImage("/images/muerte.jpg");
        SpreadSheet spritesheet_Player = new SpreadSheet(sprites_Player);
        playerLeft = new BufferedImage[3];
        playerUp= new BufferedImage[3];
        playerRight = new BufferedImage[3];
        playerDown = new BufferedImage[3];
        SpreadSheet spritesheet_Enemy = new SpreadSheet(sprites_Enemy);
        enemy1Left = new BufferedImage[3];
        for(int i=0;i<3;i++){
            playerUp[i]=spritesheet_Player.crop(i*48,3*48,48,48);
            playerLeft[i]=spritesheet_Player.crop(i*48,1*48,48,48);
            playerRight[i]=spritesheet_Player.crop(i*48,6*48,48,48);
            playerDown[i]=spritesheet_Player.crop(i*48,0*48,48,48);
            enemy1Left[i]=spritesheet_Enemy.crop(i*32 + 0*32,1*32,32,32);
        }
        SpreadSheet spritesheet_Vida = new SpreadSheet(sprites_Vida);
        vida = spritesheet_Vida.crop(10*80, 10*80, 2*80, 2*80);
    }
    
}
