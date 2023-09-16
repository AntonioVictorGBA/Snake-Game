package main;

import javax.swing.JFrame;

import panel.GamePanel;
import panel.MainMenuPanel;
import panel.MyPanel;
import panel.OptionsMenuPanel;

/**
 * Objeto JFrame modificado para manipular os MyPanels e implementar o loop do jogo.
 * 
 * @author Antonio Victor
 * @version 1.0
 */
public class Window extends JFrame implements Runnable{
    int fps = 15;
    Thread gameThread = new Thread(this);
    public MyKeyHandler keyHandler = new MyKeyHandler();
    public MySoundHandler soundHandler = new MySoundHandler();
    private MyPanel panel;
    private String panelString = "MainMenu";

    /**
     * Método construtor da classe Window.
     */
    Window(){
        setPanel("MainMenu");

        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        gameThread.start();

    }

    /**
     * Troca o MyPanel.
     * @param panelString String do MyPanel a ser implementado.
     */
    public void setPanel(String panelString){
        if (panelString == "Game")
            panel = new GamePanel(this);
        else if (panelString == "MainMenu")
            panel = new MainMenuPanel(this);
        else if (panelString == "OptionsMenu")
            panel = new OptionsMenuPanel(this);

        add(panel);
        pack();
        this.panelString = panelString;
    }

    /**
     * Loop do jogo.
     */
    public void run(){
        long lastTime = System.nanoTime(); //última vez que um frame foi criado

        while(gameThread != null){
            if (panelString=="Game" && MyPanel.running && MyPanel.hardMode){fps = 20;}
            else if (panelString=="Game" && MyPanel.running && !MyPanel.hardMode){fps = 15;}
            else{fps = 12;}

            long delta = System.nanoTime()-lastTime;
            if(delta >= 1000000000/fps){
                lastTime = System.nanoTime();
                
                panel.update();
                panel.repaint();
            }
            
        }
    }
}
