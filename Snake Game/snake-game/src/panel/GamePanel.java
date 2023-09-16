package panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.util.Random;

import main.Window;

/**
 * Panel do Jogo.
 * Subclasse de MyPanel.
 * 
 * @author Antonio Victor
 * @version 1.0
 */
public class GamePanel extends MyPanel{
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE,
                     POINTS_PER_APPLE = 45,
                     POINTS_PER_GOLD = 200,
                     HARD_MODE_BONUS = 30,
                     FIELD_WIDTH = SCREEN_WIDTH-2*UNIT_SIZE,
                     FIELD_HEIGHT = SCREEN_HEIGHT-5*UNIT_SIZE;
    final int x[] = new int[GAME_UNITS],
              y[] = new int[GAME_UNITS];
    
    int snakeSize,
        applesEaten,
        score,
        appleX,appleY,
        goldX,goldY;

    char direction;
    boolean goldTime = false,
            gameOver = true,
            playAgain = true,
            newRecord = false;
    Random random;

    /**
     * Método construtor da classe GamePanel.
     * 
     * @param frame JFrame modificado que contém o JPanel.
     */
    public GamePanel(Window frame){
        super(frame);

        random = new Random();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setOpaque(true);
        addKeyListener(keyAdapter);
        setFocusable(true);
        this.requestFocus();

        startGame();
    }
    
    /**
     * Inicia o jogo.
     */
    public void startGame(){
        snakeSize = 3;
        applesEaten = 0;
        score = 0;
        appleX = 0;
        appleY = 0;
        direction = 'R';
        x[0] = UNIT_SIZE*2;
        y[0] = UNIT_SIZE*2;

        newApple();

        if (sound)
            frame.soundHandler.playStartGame();

        gameOver = false;
        goldTime = false;
        running = true;
        playAgain = true;
        newRecord = false;
    }

    /**
     * Atualiza as coordenadas da posição da maçã.
     */
    public void newApple(){
        appleX = UNIT_SIZE + random.nextInt((int)(FIELD_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = UNIT_SIZE + random.nextInt((int)(FIELD_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    /**
     * Atualiza as coordenadas da posição do ouro.
     */
    public void newGold(){
        goldX = UNIT_SIZE + random.nextInt((int)(FIELD_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        goldY = UNIT_SIZE + random.nextInt((int)(FIELD_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        goldTime = true;
    }

    /**
     * Atualiza a posição de todas as partes da cobra.
     */
    public void move(){
        //ATUALIZA CORPO----------------------
        for (int i=snakeSize; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //------------------------------------
        //ATUALIZA CABEÇA---------------------
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
        //------------------------------------
    }

    /**
     * Verifica se a primeira posição (cabeça) da cobra colidiu com a maçã.
     */
    public void checkApple(){
        if (x[0]==appleX && y[0]==appleY){
            if (sound)
                frame.soundHandler.playAppleBeep();
            snakeSize++;
            applesEaten++;
            score+=POINTS_PER_APPLE;
            if (hardMode){score+=HARD_MODE_BONUS;}
            newApple();
        }
    }

    /**
     * Verifica se a primeira posição ("cabeça") da cobra colidiu com o ouro.
     */
    public void checkGold(){
        if (x[0]==goldX && y[0]==goldY){
            if (sound)
                frame.soundHandler.playGoldBeep();
            score+=POINTS_PER_GOLD;
            if (hardMode){score+=HARD_MODE_BONUS;}
            goldTime = false;
        }
    }

    /**
     * Verifica se a primeira posição ("cabeça") da cobra colidiu com as paredes ou com o própio corpo.
     */
    public void checkCollisions(){
        //verifica se a cabeça tocou o corpo.
        for (int i=snakeSize; i>0; i--)
            if (x[0]==x[i] && y[0]==y[i])
                gameOver();
        //verifica se a cabeça tocou a parede esquerda.
        if(x[0]<UNIT_SIZE)
            gameOver();
        //verifica se a cabeça tocou a parede direita.
        if(x[0]>=FIELD_WIDTH+UNIT_SIZE)
            gameOver();
        //verifica se a cabeça tocou a parede de cima.
        if(y[0]<UNIT_SIZE)
            gameOver();
        //verifica se a cabeça tocou a parede de baixo.
        if(y[0]>=FIELD_HEIGHT+UNIT_SIZE)
            gameOver();
    }

    /**
     * Atualiza as variáveis para estado de Game Over.
     */
    public void gameOver(){
        if (sound)
            frame.soundHandler.playGameOver();
        running = false;
        gameOver = true;
        if (score > bestScore){
            bestScore = score;
            newRecord = true;
        }
    }

    /**
     * Atualiza o Panel a cada iteração do loop.
     * Sobrescreve método 'update' da superclasse.
     */
    public void update(){
        if(keyAdapter.getUpPressed()){
            if (direction!='D')
                direction = 'U';
        }
        else if(keyAdapter.getDownPressed()){
            if (direction!='U')
                direction = 'D';
        }
        else if(keyAdapter.getLeftPressed()){
            if (direction!='R')
                direction = 'L';
        }
        else if(keyAdapter.getRightPressed()){
            if (direction!='L')
                direction = 'R';
        }
        updateGame();
    }

    /**
     * Extensão do método update.
     * Atualiza o jogo.
     */
    public void updateGame() {
        if (running){
            if(random.nextInt(250)==0 && !goldTime){
                goldTime = true;
                newGold();
            }
            move();
            checkApple();
            if (goldTime)
                checkGold();
            checkCollisions();
        }
        else if (gameOver){
            if (keyAdapter.getLeftPressed() || keyAdapter.getRightPressed())
                playAgain = !playAgain;
            else if (keyAdapter.getEnterPressed()){
                if (playAgain){startGame();}
                else{frame.setPanel("MainMenu");}
            }
        }
    }

    /**
     * Desenha os componentes na janela.
     * 
     * @param g Gráficos.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawField(g);
        drawApple(g);
        if (goldTime){drawGold(g);}
        drawSnake(g);
        drawScore(g);
        if (gameOver){drawGameOverScreen(g);}
    }

    /**
     * Extensão do método paintComponent.
     * Desenha o campo.
     * 
     * @param g Gráficos.
     */
    public void drawField(Graphics g){
        g.setColor(backgroundColor);
        g.fillRect(UNIT_SIZE,UNIT_SIZE,FIELD_WIDTH,FIELD_HEIGHT);

        g.setColor(Color.gray);
        for (int i=2; i<SCREEN_HEIGHT/UNIT_SIZE-1; i++){
            g.drawLine(i*UNIT_SIZE, UNIT_SIZE, i*UNIT_SIZE, FIELD_HEIGHT+UNIT_SIZE);     //VERTICAL LINES
            if (i<SCREEN_HEIGHT/UNIT_SIZE-4)
                g.drawLine(UNIT_SIZE, i*UNIT_SIZE, FIELD_WIDTH+UNIT_SIZE, i*UNIT_SIZE);  //HORIZONTAL LINES
        }
        g.setColor(Color.white);
        g.fillRect(0, 0, UNIT_SIZE, FIELD_HEIGHT+2*UNIT_SIZE);                       //LEFT BORDER
        g.fillRect(FIELD_WIDTH+UNIT_SIZE, 0, UNIT_SIZE, FIELD_HEIGHT+2*UNIT_SIZE);     //RIGHT BORDER
        g.fillRect(0, 0, FIELD_WIDTH+2*UNIT_SIZE, UNIT_SIZE);                        //TOP BORDER
        g.fillRect(0, SCREEN_HEIGHT-4*UNIT_SIZE, FIELD_WIDTH+2*UNIT_SIZE, UNIT_SIZE);  //BOTTOM BORDER
    }

    /**
     * Extensão do método paintComponent.
     * Desenha a maçã.
     * 
     * @param g Gráficos.
     */
    public void drawApple(Graphics g){
        g.setColor(Color.red);
        g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    }

    /**
     * Extensão do método paintComponent.
     * Desenha o ouro.
     * 
     * @param g Gráficos.
     */
    public void drawGold(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(goldX, goldY, UNIT_SIZE, UNIT_SIZE);
    }

    /**
     * Extensão do método paintComponent.
     * Desenha a cobra.
     * 
     * @param g Gráficos.
     */
    public void drawSnake(Graphics g){
        g.setColor(snakeColor);
        for (int i=0; i<snakeSize; i++){
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**
     * Extensão do método paintComponent.
     * Desenha o placar.
     * 
     * @param g Gráficos.
     */
    public void drawScore(Graphics g){
        g.setColor(Color.white);
        String text;
        g.setFont(new Font("Verdana", Font.BOLD, 15));

        text = String.format("APPLES EATEN = %d", applesEaten);
        g.drawString(text, 20, FIELD_HEIGHT+3*UNIT_SIZE);

        text = String.format("SCORE = %d", score);
        g.drawString(text, SCREEN_WIDTH/2 + 20, FIELD_HEIGHT+3*UNIT_SIZE);
    }

    /**
     * Extensão do método paintComponent.
     * Desenha a tela de Game Over.
     * 
     * @param g Gráficos.
     */
    public void drawGameOverScreen(Graphics g){
        int x = 60, y = 120, //x & y da tela de game over.
            border = 6;      //borda da tela de game over em pixels.
        g.setColor(Color.white);
        g.fillRect(x, y, SCREEN_WIDTH-2*x, SCREEN_HEIGHT-2*y);

        g.setColor(Color.black);
        g.fillRect(x+border, y+border, SCREEN_WIDTH-2*(x+border), SCREEN_HEIGHT-2*(y+border));

        g.setColor(Color.red);
        String text = "GAME OVER";
        g.drawString(text, getXforCenteredText(g.getFont(),text), y+30);

        g.setColor(Color.white);
        if (newRecord)
            text = String.format("* NEW BEST SCORE: %d *", bestScore);
        else
            text = String.format("BEST SCORE: %d", bestScore);
        g.drawString(text, getXforCenteredText(g.getFont(),text), y+55);
        text = "PLAY AGAIN?";
        g.drawString(text, getXforCenteredText(g.getFont(),text), y+105);
        text = "YES";
        g.drawString(text, getXforCenteredText(g.getFont(),text)-30, y+130);
        text = "NO";
        g.drawString(text, getXforCenteredText(g.getFont(),text)+30, y+130);

        if(playAgain){g.drawString(">", getXforCenteredText(g.getFont(),text)-50, y+130);}
        else{g.drawString(">", getXforCenteredText(g.getFont(),text)+12, y+130);}
    }
}
