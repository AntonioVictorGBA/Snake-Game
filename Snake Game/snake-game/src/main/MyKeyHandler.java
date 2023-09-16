package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe que implementa KeyListener.
 * 
 * @author Antonio Victor
 * @version 1.0
 */
public class MyKeyHandler implements KeyListener{
    private boolean upPressed = false,
                    downPressed = false,
                    leftPressed = false,
                    rightPressed = false,
                    enterPressed = false;

    /**
     * Método não utilizado neste programa, porém de implementação obrigatória da interface KeyListener.
     * 
     * @param e Evento de tecla.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Atribui verdadeiro para uma variável Pressed quando uma tecla é pressionada.
     * Variáveis 'Pressed' são booleanas que determinam se uma tecla está pressionada.
     * 
     * @param e Evento de tecla.
     */
    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();   //valor inteiro referente à tecla. Ex: A = 65

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
            upPressed = true;
        else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
            downPressed = true;
        else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
            leftPressed = true;
        else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
            rightPressed = true;
        else if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE)
            enterPressed = true;
        
    }
    
    /**
     * Atribui falso para uma variável Pressed quando uma tecla é solta.
     * Variáveis 'Pressed' são booleanas que determinam se uma tecla está pressionada.
     * 
     * @param e Evento de tecla.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();   //valor inteiro referente à tecla. Ex: A = 65

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
            upPressed = false;
        else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
            downPressed = false;
        else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
            leftPressed = false;
        else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
            rightPressed = false;
        else if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE)
            enterPressed = false;
    }

    public boolean getUpPressed(){return upPressed;}
    public boolean getDownPressed(){return downPressed;}
    public boolean getLeftPressed(){return leftPressed;}
    public boolean getRightPressed(){return rightPressed;}
    public boolean getEnterPressed(){return enterPressed;}
    
}

