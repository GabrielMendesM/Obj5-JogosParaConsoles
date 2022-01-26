import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;

public class Circulo {
    private static final int DIMENSAO = 4;
    private int posX, posY;
    private int maxPosX, maxPosY;
    private float velX, velY;
    private Color cor;
    
    public Circulo(int posX, int posY, int maxPosX, int maxPosY, float velX, float velY, Color cor) {
        this.posX = posX;
        this.posY = posY;
        this.maxPosX = maxPosX;
        this.maxPosY = maxPosY;
        this.velX = velX;
        this.velY = velY;
        this.cor = cor;

        int rand = ThreadLocalRandom.current().nextInt(0, 4);
        
        if (rand == 0) {
            this.velX *= -1;
        } else if (rand == 1) {
            this.velX *= -1;
            this.velY *= -1;
        } else if (rand == 2) {
            this.velY *= -1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(cor);
        g.drawOval(posX, posY, DIMENSAO, DIMENSAO);
        g.fillOval(posX, posY, DIMENSAO, DIMENSAO);
    }

    public void mover() {
        posX += velX;
        posY += velY;

        if (posX <= 0 ||
            posX + DIMENSAO >= maxPosX) {
                velX *= -1;
            }
        if (posY <= 0 ||
            posY + DIMENSAO >= maxPosY) {
            velY *= -1;
        }
    }

    public static int getDimensao() {
        return DIMENSAO;
    }
}
