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
 * @author NamiDW
 * 
 * Juego del pajaro pasando por obstaculos.
 * Se pulsa espacio para hacerlo elevarse
 * En caso contrario va descendiendo lentamente
 * Tiene que esquivar a los enemigos que vienen en contra.
 * 10 vidas.
 * Pausa.
 * Opcion de guardar partida y cargar.
 * 
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
    private ArrayList<Enemy> enemies; //to store enemies collection
    private boolean gameover;
    private int puntuacion;
    String titulo;
    private SoundClip musica;
    private SoundClip enemigoPerdido;
    private SoundClip enemigoAtrapado;
    private SoundClip risaGameover;
    private int espacio;
    private int contEnemigos;
    private boolean choque;
    private int contChoque;
    private boolean pausa;
    private boolean ganar;
    private boolean enemigoPasado;
    
    
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
        gameover = false;
        puntuacion = 0;
        title = "Puntos: ";
        titulo = title + puntuacion;
        musica = new SoundClip("/sonido/ringtones-be-happy.wav");
        enemigoPerdido = new SoundClip("/sonido/laugh3.wav");
        enemigoAtrapado = new SoundClip("/sonido/los-animales_2.wav");
        risaGameover = new SoundClip("/sonido/gracioso_2.wav");
        musica.setLooping(true);
        contEnemigos =0;
        choque = false;
        pausa=false;
        contChoque=0;
        ganar=false;
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

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setContChoque(int contChoque) {
        this.contChoque = contChoque;
    }

    public int getContChoque() {
        return contChoque;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Player getPlayer() {
        return player;
    }

    private void agregarEnemigo(){
        espacio = (int) (Math.random()*4);
        System.out.println(espacio);
        if(espacio==0){
            enemies.add(new Enemy( getWidth(), 200, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 300, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 400, 100, 100, this));
        }
        else if(espacio==1){
            enemies.add(new Enemy( getWidth(), 0, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 300, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 400, 100, 100, this));
        }
        else if(espacio==2){
            enemies.add(new Enemy( getWidth(), 0, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 100, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 400, 100, 100, this));
        }
        else{
            enemies.add(new Enemy( getWidth(), 0, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 100, 100, 100, this));
            enemies.add(new Enemy( getWidth(), 200, 100, 100, this));
        }
    }
    
    private void reordenar(){
        //esta funcion tiene que eliminar a todos los enemigos existentes, 
        //      mandar a crear nuevos y reiniciar al jugador.
        
        //reiniciar al jugador
        player.setX(0);
        player.setY(0);
        
        //eliminar a los enemigos
        Iterator itr = enemies.iterator();
        while(itr.hasNext()){
            Enemy enemy = (Enemy)itr.next();
            enemies.remove(enemy);
            itr = enemies.iterator();
        }
        agregarEnemigo();
        contEnemigos=0;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
         display = new Display(titulo, getWidth(), getHeight());  
         Assets.init();
         player = new Player(0, 0, 100, 100, this);
         //create Array of enemies
         enemies = new ArrayList<>();
         //adding enemies to the colection
         agregarEnemigo();
         //agregando las vidas
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
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick
            //      units
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
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void tick() {
        keyManager.tick();
        
        if(!gameover && !choque && !pausa && !ganar){
        
        contEnemigos++;
        if(contEnemigos>=200){
            agregarEnemigo();
            contEnemigos=0;
        }
        // avancing player with colision
        player.tick();
        //moving the enemies
        Iterator itr = enemies.iterator();
        while(itr.hasNext()){
            Enemy enemy = (Enemy)itr.next();
            enemy.tick();
            //check if colision with player
            if(player.intersects(enemy)){
                enemies.remove(enemy);
                itr = enemies.iterator();
                enemigoPerdido.play();
                contChoque++;
                //condiciones para perder
                if(contChoque>=10){
                    gameover=true;
                }
                else{
                    //sino perdio solo pasa la pausa choque
                    choque=true;
                }
            }
            
            //reset position if it get out of the screen
            if(enemy.getX() <= 80){
                enemies.remove(enemy);
                itr = enemies.iterator();
                enemigoAtrapado.play();
                enemigoPasado = true;
            }
        }
        //para que no se agrege varias veces la puntuacion.
        if(enemigoPasado){
            enemigoPasado=false;
            puntuacion+=100;
        }
        //ganaste el juego
        if(puntuacion>=1000)
            ganar=true;
        
        //pausar el juego
        if(keyManager.P){
            pausa=true;
        }
        //las condicines de lo que pasa cuando se detiene
        }
        else if(gameover){
            risaGameover.play();
            musica.stop();
            running = false;
        }
        else if(choque){
            if(keyManager.R){
                reordenar();
                choque = false;
            }
        }
        else if(pausa){
            if(keyManager.S)
                Files.saveFile(this);
            if(keyManager.L)
                Files.loadFile(this);
            if(keyManager.P)
                pausa=false;
        }
        
        
        //checa puntuacion;
        if(puntuacion<0)
            puntuacion=0;
        titulo = title+puntuacion;
        display.changJframe(titulo);
        
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
            if(pausa)
                g.drawImage(Assets.backgroundPausa, 0, 0, width, height, null);
            if(gameover)
                g.drawImage(Assets.gameover, 0, 0, width, height, null);
            if(ganar)
                g.drawImage(Assets.ganaste, 0, 0, width, height, null);
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
