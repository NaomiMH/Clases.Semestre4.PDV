/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author NamiDW
 */
public class Files {
    public static void saveFile(Game game){
        //define objects
        PrintWriter printWriter;
        try{
            //creatubg file object
            printWriter = new PrintWriter(new FileWriter("data.txt"));
            //writing the game
            printWriter.println("" + game.getPlayer().getX() + "," + 
                    game.getPlayer().getY());
            printWriter.println("" + game.getPuntuacion() + "," + 
                    game.getContChoque());
            printWriter.println("" + game.getEnemies().size());
            for(Enemy enemy : game.getEnemies()){
                printWriter.println("" + enemy.getX() + "," + enemy.getY());
            }
            printWriter.close();
        }catch(IOException ioe){
            System.out.println("Disco duro lleno " + ioe.toString());
        }
    }
    
    public static void loadFile(Game game){
        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader( new FileReader("data.txt"));
            // get the frist line
            String line = bufferedReader.readLine();
            String[] tokens = line.split(",");
            game.getPlayer().setX(Integer.parseInt(tokens[0]));
            game.getPlayer().setY(Integer.parseInt(tokens[1]));
            
            line = bufferedReader.readLine();
            tokens = line.split(",");
            game.setPuntuacion(Integer.parseInt(tokens[0]));
            game.setContChoque(Integer.parseInt(tokens[1]));
            
            game.getEnemies().clear();
            int enemies = Integer.parseInt(bufferedReader.readLine());
            for(int i=0; i<enemies; i++){
                line = bufferedReader.readLine();
                tokens = line.split(",");
                int x = Integer.parseInt(tokens[0]);
                int y = Integer.parseInt(tokens[1]);
                Enemy enemy = new Enemy(x,y,100,100,game);
                game.getEnemies().add(enemy);
            }
        }catch (IOException ioe){
            System.out.println(" Juego no ha sido guardado " + ioe.toString());
        }
    }
}
