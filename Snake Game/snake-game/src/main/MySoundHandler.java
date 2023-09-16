package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.net.URL;



public class MySoundHandler {
    //File beep = new File(".../res/songs/beep.wav");
    URL appleBeep,
        goldBeep,
        gameOver,
        startGame;
    Clip clip;
    MySoundHandler(){
        appleBeep = getClass().getResource("../res/beep-apple.wav");
        goldBeep = getClass().getResource("../res/beep-gold.wav");
        gameOver = getClass().getResource("../res/game-over.wav");
        startGame = getClass().getResource("../res/start-game.wav");
    }

    private void setFile(URL file){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){}
    }

    public void playAppleBeep(){
        setFile(appleBeep);
        clip.start(); 
    }
    public void playGoldBeep(){
        setFile(goldBeep);
        clip.start(); 
    }
    public void playGameOver(){
        setFile(gameOver);
        clip.start(); 
    }
    public void playStartGame(){
        setFile(startGame);
        clip.start(); 
    }


}

