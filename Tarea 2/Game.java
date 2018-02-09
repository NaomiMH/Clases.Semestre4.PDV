/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author NamiDW
 */
public class Game implements Runnable {
    
    private BufferStrategy bs;      //to have several buffers when displaying
    private Graphics g;             //to print objects
    private Display display;        //to display int the game
    String title;                   //title of the window
    private int width;              //width of the window
    private int height;             //height of the window
    private Thread thread;          //thread to creat the game
    private boolean running;        //to set the game
    private Player player;          //to use a player
    private Enemy enemy;
    private Vidas vida1;
    private Vidas vida2;
    private Vidas vida3;
    private int vidas;
    private KeyManager keyManager;  //to manage the keyboard
    private MouseManager mouseManager;   //to manage the mouse
    private SoundClip choqueEnemigo;
    private SoundClip choqueLimites;
    private int cont;
    private boolean choque;
    
    /**
     * to create title width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        vidas = 3;
        choqueEnemigo = new SoundClip("/sonido/sonido_Choque.wav");
        choqueLimites = new SoundClip("/sonido/sonido_Choque2.wav");
        cont=0;
        choque = false;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    public SoundClip getChoqueLimites() {
        return choqueLimites;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init(){
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        player = new Player(0, getHeight() - 100, 0, 100, 100, this);
        enemy = new Enemy(getWidth()-100,0,0,100,100,this); 
        vida1 = new Vidas(0,0,40,40,this);
        vida2 = new Vidas(45,0,40,40,this);
        vida3 = new Vidas(90,0,40,40,this);
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    @Override
    public void run() {
        init();
        //frames per second
        int fps = 50;
        //time for each tick in nano segs
        double timeTick = 1000000000/fps;
        //initializing delta
        double delta = 0;
        //define now to use inside the loop
        long now;
        //initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while(running){
            //setting the time now to the actual time
            now = System.nanoTime();
            //acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            //updating the lastTime
            lastTime = now;
            
            //if delta is positive we tick the game
            if(delta >= 1){
                tick();
                render();
                delta--;
            }
        }
        stop();
    }
    
    public KeyManager getKeyManager(){
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
        switch(vidas)
        {
            case 3:
                vida3.setMostrar(true);
                vida2.setMostrar(true);
                vida1.setMostrar(true);
                break;
            case 2:
                vida2.setMostrar(true);
                vida3.setMostrar(false);
                vida1.setMostrar(true);
                break;
            case 1:
                vida2.setMostrar(false);
                vida3.setMostrar(false);
                vida1.setMostrar(true);
                break;
            case 0:
                vida1.setMostrar(false);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isChoque() {
        return choque;
    }
    
    private void tick(){
        keyManager.tick();
        //avancing player with colision
        player.tick();
        if(enemy.isJugar()){
            enemy.setJugar(false);
        }
        else if(cont==0)
            enemy.setJugar(true);
        enemy.tick();
        
        if(player.getBounds().intersects(enemy.getBounds())){
            enemy.setJugar(false);
            choque = true;
            if(cont==0)
                choqueEnemigo.play();
            cont++;
            if(cont==220){
                enemy.setX(getWidth()-100);
                enemy.setY(0);
                player.setX(0);
                player.setY(getHeight() - 100);
                setVidas(getVidas()-1);
                if(getVidas()==0)
                    setRunning(false);
                cont=0;
                choque = false;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }
    
    private void render(){
        //get buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the
        buffer strategy element.
        show the graphic and dispose it to the trash system
        */
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            enemy.render(g);
            vida1.render(g);
            vida2.render(g);
            vida3.render(g);
            bs.show();
            g.dispose();
        }
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start(){
        if(!running){
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop(){
        if(running){
            running = false;
            try{
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
