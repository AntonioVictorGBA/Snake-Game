package panel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import javax.swing.JPanel;

import main.MyKeyHandler;
import main.Window;

/**
 * Superclasse de todos os Panels utilizados no programa.
 * 
 * @author Antonio Victor
 * @version 1.0
 */
public class MyPanel extends JPanel{
    protected MyKeyHandler keyAdapter;
    protected Window frame;
    static protected Color snakeColor = Color.green,
                           backgroundColor = Color.black;
    static protected int snakeColorSelected = 0, backgroundColorSelected = 0,
                         bestScore = 0;
    static public boolean running = false,    //game is playing
                          sound = true,
                          hardMode = false;

    protected static final int UNIT_SIZE = 16,
                               SCREEN_WIDTH = UNIT_SIZE*25,
                               SCREEN_HEIGHT = UNIT_SIZE*25;

    /**
     * Método construtor da classe MyPanel.
     * @param frame JFrame modificado que contém o JPanel.
     */
    MyPanel(Window frame){
        this.frame = frame;
        this.keyAdapter = frame.keyHandler;
    }

    /**
     * Método que é sobrescrito nas subclasses para atualizar o Panel a cada iteração do loop.
     */
    public void update(){}

    /**
     * Dados um texto e o estilo da fonte, retorna a coordenada x em que fará o texto estar centralizado.
     * 
     * @param font Estilo de fonte do texto.
     * @param text String referente ao texto.
     * @return Coordenada x para o texto estar centralizado.
     */
    public int getXforCenteredText (Font font, String text){
        FontMetrics metrics = getFontMetrics(font);
        return (SCREEN_WIDTH-metrics.stringWidth(text))/2;
    }
}
