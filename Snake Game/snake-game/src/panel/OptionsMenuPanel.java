package panel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

import main.Window;

/**
 * Panel do Menú de Opções.
 * Subclasse de MyPanel.
 * 
 * @author Antonio Victor
 * @version 1.0
 */
public class OptionsMenuPanel extends MyPanel{
    private int commandNum = 0;
    private Color[] snakeColors = {Color.green, Color.blue, Color.magenta, Color.orange};
    private Color[] backgroundColors = {Color.black, Color.cyan, Color.pink, Color.lightGray};

    /**
     * Método construtor da classe OptionsMenuPanel.
     * @param frame JFrame modificado que contém o JPanel.
     */
    public OptionsMenuPanel(Window frame){
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
        if (keyAdapter.getUpPressed()){
            commandNum--;
            if (commandNum<0)
                commandNum = 4;
        }
        else if (keyAdapter.getDownPressed()){
            commandNum++;
            if (commandNum>4)
                commandNum = 0;
        }
        else if (keyAdapter.getRightPressed()){
            if (commandNum == 0){
                snakeColorSelected++;
                if (snakeColorSelected >= snakeColors.length){
                    snakeColorSelected = 0;
                }
                snakeColor = snakeColors[snakeColorSelected];
            }
            else if (commandNum == 1){
                backgroundColorSelected++;
                if (backgroundColorSelected >= backgroundColors.length){
                    backgroundColorSelected = 0;
                }
                backgroundColor = backgroundColors[backgroundColorSelected];
            }
            else if (commandNum == 2){sound = !sound;}
            else if (commandNum == 3){hardMode = !hardMode;}
        }
        else if (keyAdapter.getLeftPressed()){
            if (commandNum == 0){
                snakeColorSelected--;
                if (snakeColorSelected < 0){
                    snakeColorSelected = snakeColors.length-1;
                }
                snakeColor = snakeColors[snakeColorSelected];
            }
            else if (commandNum == 1){
                backgroundColorSelected--;
                if (backgroundColorSelected < 0){
                    backgroundColorSelected = backgroundColors.length-1;
                }
                backgroundColor = backgroundColors[backgroundColorSelected];
            }
            else if (commandNum == 2){sound = !sound;}
            else if (commandNum == 3){hardMode = !hardMode;}
        }
        else if(keyAdapter.getEnterPressed()){
            if (commandNum==4)
                frame.setPanel("MainMenu");
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
    private void drawTitle(Graphics g){
        String text;
        int x, y;

        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.BOLD, 50));

        text = "OPTIONS";
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
    private void drawMenu(Graphics g){
        int x = 45, y = 200;
        int espacamento = 30;
        g.setFont(new Font("Verdana", Font.BOLD, 15));

        drawSnakeColorsOption(g,x,y);
        y += espacamento;
        drawBackgroundColorsOption(g,x,y);
        y += espacamento;
        drawSoundOption(g,x,y);
        y += espacamento;
        drawDifficultyOption(g,x,y);
        y += espacamento;
        drawBackOption(g,x,y);
    }

    /**
     * Extensão do método drawMenu.
     * Desenha as opções de cores da cobra.
     * 
     * @param g Gráficos.
     * @param x Coordenada x das opções.
     * @param y Coordenada y das opções.
     */
    private void drawSnakeColorsOption(Graphics g, int x, int y){
        String text = "Snake Color";

        g.setColor(Color.white);
        g.drawString(text, x, y);
        if (commandNum == 0)
            g.drawString(">", x-25, y);

        int borda = 2, size = 20, blockX = 200, espacamento = 25;
        g.fillRect(blockX-borda+(snakeColorSelected*(espacamento)), y-15-borda,size+borda+borda,size+borda+borda);

        for (int i=0; i<snakeColors.length; i++){
            drawBlock(blockX, y-15, size, snakeColors[i], g);
            blockX+=espacamento;
        }
    }

    /**
     * Extensão do método drawMenu.
     * Desenha as opções de cores do campo.
     * 
     * @param g Gráficos.
     * @param x Coordenada x das opções.
     * @param y Coordenada y das opções.
     */
    private void drawBackgroundColorsOption(Graphics g, int x, int y){
        String text = "Background Color";

        g.setColor(Color.white);
        g.drawString(text, x, y);
        if (commandNum == 1)
            g.drawString(">", x-25, y);

        int borda = 2, size = 20, blockX = 200, espacamento = 25;
        g.fillRect(blockX-borda+(backgroundColorSelected*(espacamento)), y-15-borda,size+borda+borda,size+borda+borda);

        for (int i=0; i<backgroundColors.length; i++){
            drawBlock(blockX, y-15, 20, backgroundColors[i], g);
            blockX+=espacamento;
        }
    }

    /**
     * Extensão do método drawMenu.
     * Desenha as opções de som.
     * 
     * @param g Gráficos.
     * @param x Coordenada x das opções.
     * @param y Coordenada y das opções.
     */
    private void drawSoundOption(Graphics g, int x, int y){
        String text = "Sound";

        g.setColor(Color.white);
        g.drawString(text, x, y);
        if (commandNum == 2)
            g.drawString(">", x-25, y);
        
        if (sound){g.drawString("YES", x+190, y);}
        else{g.drawString("NO", x+193, y);}
    }

    /**
     * Extensão do método drawMenu.
     * Desenha as opções de dificuldade.
     * 
     * @param g Gráficos.
     * @param x Coordenada x das opções.
     * @param y Coordenada y das opções.
     */
    private void drawDifficultyOption(Graphics g, int x, int y){
        String text = "Difficulty";

        g.setColor(Color.white);
        g.drawString(text, x, y);
        if (commandNum == 3)
            g.drawString(">", x-25, y);

        if (hardMode){g.drawString("HARD", x+182, y);}
        else{g.drawString("NORMAL", x+172, y);}
    }

    /**
     * Extensão do método drawMenu.
     * Desenha a opção de voltar ao menu principal.
     * 
     * @param g Gráficos.
     * @param x Coordenada x das opções.
     * @param y Coordenada y das opções.
     */
    private void drawBackOption(Graphics g, int x, int y){
        String text = "Back";

        g.setColor(Color.white);
        g.drawString(text, x, y);
        if (commandNum == 4)
            g.drawString(">", x-25, y);
    }

    /**
     * Desenha um quadrado.
     * 
     * @param x Coordenada x do quadrado.
     * @param y Coordenada y do quadrado.
     * @param size Tamanho do quadrado.
     * @param color Cor do quadrado.
     * @param g Gráficos.
     */
    private void drawBlock(int x, int y, int size, Color color, Graphics g){
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

}
