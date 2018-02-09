/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player;          // to use a player
    private KeyManager keyManager;  // to manage the keyboard
    private boolean choque;
    private ArrayList<Enemy> enemies; //to store enemies collection
    private boolean gameover;
    private int puntuacion;
    String titulo;
    private int contVidas;
    private int vidas;
    private int vel;
    private Vida vidass[];
    private SoundClip musica;
    private SoundClip enemigoPerdido;
    private SoundClip enemigoAtrapado;
    private SoundClip risaGameover;
    
    
    /**
     * to create title, width and height and set the game is still not running
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        choque = false;
        gameover = false;
        puntuacion = 0;
        title = "Puntos: ";
        titulo = title + puntuacion;
        contVidas = 0;
        vidas = 5;
        vel = 1;
        vidass = new Vida[vidas];
        musica = new SoundClip("/sonido/ringtones-be-happy.wav");
        enemigoPerdido = new SoundClip("/sonido/laugh3.wav");
        enemigoAtrapado = new SoundClip("/sonido/los-animales_2.wav");
        risaGameover = new SoundClip("/sonido/gracioso_2.wav");
        musica.setLooping(true);
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public int getVel() {
        return vel;
    }

    public boolean isChoque() {
        return choque;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
         display = new Display(titulo, getWidth(), getHeight());  
         Assets.init();
         player = new Player(0, getHeight() - 70, 70, 70, this);
         //create Array of enemies
         enemies = new ArrayList<>();
         //adding enemies to the colection
         for(int cont=0; cont<10; cont++){
             enemies.add(new Enemy((int)(Math.random()*(getWidth()-80)), - (int)(Math.random()* 2 * getHeight()), 100, 80, this));
         }
         //agregando las vidas
         for(int cont=0; cont<vidas; cont++){
             vidass[cont] = new Vida(cont*40+(cont*5),0,40,40,this);
         }
         display.getJframe().addKeyListener(keyManager);
    }
    
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running && !gameover) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta --;
            }
        }
        risaGameover.play();
        musica.stop();
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void tick() {
        keyManager.tick();
        // avancing player with colision
        player.tick();
        //moving the enemies
        Iterator itr = enemies.iterator();
        while(itr.hasNext()){
            Enemy enemy = (Enemy)itr.next();
            enemy.tick();
            //check if colision with player
            if(player.intersects(enemy)){
                enemy.setX((int) (Math.random()*(getWidth()-80)));
                enemy.setY(- (int) (Math.random()* 2 *(getHeight() -80)));
                puntuacion+=100;
                enemigoAtrapado.play();
            }
            
            //reset position if it get out of the screen at the button
            if(enemy.getY() >= getHeight()){
                enemy.setX((int) (Math.random()*(getWidth()-80)));
                enemy.setY(- (int) (Math.random()* 2 *(getHeight() -80)));
                contVidas++;
                puntuacion-=20;
                enemigoPerdido.play();
            }
        }
        
        if(puntuacion<0)
            puntuacion=0;
        titulo = title+puntuacion;
        display.changJframe(titulo);
        
        if(contVidas>=10){
            vidas--;
            if(vidas>=0)
                vidass[vidas].setMostrar(false);
            contVidas=0;
            setVel(getVel()+1);
        }
        if(vidas<=0)
            gameover = true;
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            Iterator itr = enemies.iterator();
            while(itr.hasNext()){
                ((Enemy)itr.next()).render(g);
            }
            for(int cont=0; cont<vidas; cont++){
                vidass[cont].render(g);
            }
            bs.show();
            g.dispose();
        }
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            musica.play();
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }           
        }
    }
}
