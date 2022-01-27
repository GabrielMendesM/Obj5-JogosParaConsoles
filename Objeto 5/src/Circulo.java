import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;
import java.awt.Rectangle;

public class Circulo implements Colidivel {
    private static final int DIMENSAO = 3;
    private int posX, posY;
    private int maxPosX, maxPosY;
    private float velX, velY;
    private Color cor;
    private Color corOriginal;
    private int timerCor = 0;
    private static final int TIMER_COR_MAX = 10;

    private boolean colidiu;
    
    public Circulo(int posX, int posY, int maxPosX, int maxPosY, float velX, float velY, Color cor) {
        this.posX = posX;
        this.posY = posY;
        this.maxPosX = maxPosX;
        this.maxPosY = maxPosY;
        this.velX = velX;
        this.velY = velY;
        this.cor = cor;
        this.corOriginal = cor;

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

        if (posX < 0) {
            posX = 0;
        }
        if (posY < 0) {
            posY = 0;
        }
        if (posX > maxPosX) {
            posX = maxPosX;
        }
        if (posY > maxPosY) {
            posY = maxPosY;
        }

        if (colidiu) {
            timerCor++;
        }
        if (timerCor > TIMER_COR_MAX) {
            timerCor = 0;
            colidiu = false;
            cor = corOriginal;
        }
    }

    public static int getDimensao() {
        return DIMENSAO;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public boolean checarColisao(Colidivel outro) {
        if ((posX < outro.getPosX() && posX + DIMENSAO > outro.getPosX() && posY == outro.getPosY()) || 
            (posX < outro.getPosX() && posX + DIMENSAO > outro.getPosX() && posY < outro.getPosY() && posY + DIMENSAO > outro.getPosY()) ||
            (posX < outro.getPosX() && posX + DIMENSAO > outro.getPosX() && posY > outro.getPosY() && posY < outro.getPosY() + DIMENSAO) ||
            (posX > outro.getPosX() && posX < outro.getPosX() + DIMENSAO && posY == outro.getPosY()) ||
            (posX > outro.getPosX() && posX < outro.getPosX() + DIMENSAO && posY < outro.getPosY() && posY + DIMENSAO > outro.getPosY()) ||
            (posX > outro.getPosX() && posX < outro.getPosX() + DIMENSAO && posY > outro.getPosY() && posY < outro.getPosY() + DIMENSAO) || 
            (posX == outro.getPosX() && posY > outro.getPosY() && posY < outro.getPosY() + DIMENSAO) ||
            (posX == outro.getPosX() && posY < outro.getPosY() && posY + DIMENSAO > outro.getPosY())) {
            
            velX *= -1;
            velY *= -1;
            colidiu = true;
        }
        return colidiu;
    }

    @Override
    public Rectangle getColisaoRect() {
        return new Rectangle(posX, posY, DIMENSAO, DIMENSAO);
    }

    @Override
    public void onColisao(Colidivel outro) {
        cor = Color.WHITE;
    }
}
