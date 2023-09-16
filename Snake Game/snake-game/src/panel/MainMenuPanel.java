package panel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import main.Window;

/**
 * Panel do Menú Principal.
 * Subclasse de MyPanel.
 * 
 * @author Antonio Victor
 * @version 1.0
 */
public class MainMenuPanel extends MyPanel{
    private int commandNum = 0;

    /**
     * Método construtor da classe MainMenuPanel.
     * @param frame JFrame modificado que contém o JPanel.
     */
    public MainMenuPanel(Window frame){
        super(frame);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setOpaque(true);
        setFocusable(true);
        this.requestFocus();

        addKeyListener(keyAdapter);

        repaint();
    }
    
    /**
     * Atualiza o Panel a cada iteração do loop.
     * Sobrescreve método 'update' da superclasse.
     */
    public void update(){
        if(keyAdapter.getUpPressed()){
            commandNum--;
            if (commandNum<0)
                commandNum = 2;
        }
        else if(keyAdapter.getDownPressed()){
            commandNum++;
            if (commandNum>2)
                commandNum = 0;
        }
        else if(keyAdapter.getEnterPressed()){
            if (commandNum==0)
                frame.setPanel("Game");
            else if (commandNum==1)
                frame.setPanel("OptionsMenu");
            else if(commandNum==2)
                System.exit(ABORT);
        }
    }

    /**
     * Desenha os componentes na janela.
     * 
     * @param g Gráficos.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        drawTitle(g);
        drawMenu(g);
    }

    /**
     * Extensão do método paintComponent.
     * Desenha o título.
     * 
     * @param g Gráficos.
     */
    public void drawTitle(Graphics g){
        String text;
        int x, y;

        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.BOLD, 50));

        text = "SNAKE GAME";
        x = getXforCenteredText(g.getFont(),text);
        y = 100;
        g.drawString(text, x, y);
    }

    /**
     * Extensão do método paintComponent.
     * Desenha o menu.
     * 
     * @param g Gráficos.
     */
    public void drawMenu(Graphics g){
        String text;
        int x, y;
        int espacamento = 30;
        g.setFont(new Font("Verdana", Font.BOLD, 15));

        text = "Start Game";
        x = getXforCenteredText(g.getFont(),text);
        y = 200;
        g.drawString(text, x, y);
        if (commandNum == 0)
            g.drawString(">", x-25, y);

        text = "Options";
        x = getXforCenteredText(g.getFont(),text);
        y += espacamento;
        g.drawString(text, x, y);
        if (commandNum == 1)
            g.drawString(">", x-25, y);

        text = "Exit";
        x = getXforCenteredText(g.getFont(),text);
        y += espacamento;
        g.drawString(text, x, y);
        if (commandNum == 2)
            g.drawString(">", x-25, y);

        text = String.format("BEST SCORE: %d", bestScore);
        x = 200;
        y = 380;
        g.drawString(text, x, y);
    }
}
