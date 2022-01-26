import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

public class Cena extends JPanel implements Runnable {
    private volatile boolean isRodando = false;

    private static final int MAX_VELOCIDADE = 3;
    private static final int INTERVALO_THREAD = 16;

    private final Color corFundo = Color.GRAY;
    private final Color[] coresCirculo = new Color[9];

    private int largura, altura;
    private final Circulo[] circulos;

    public Cena(int larguraTela, int alturaTela, int nCirculos) {
        largura = larguraTela;
        altura = alturaTela;
        circulos = new Circulo[nCirculos];

        setCoresCirculo();

        for (int i = 0; i < circulos.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(0, largura - Circulo.getDimensao());
            int posY = ThreadLocalRandom.current().nextInt(0, altura - Circulo.getDimensao());
            float velX = ThreadLocalRandom.current().nextFloat(1, MAX_VELOCIDADE);
            float velY = ThreadLocalRandom.current().nextFloat(1, MAX_VELOCIDADE);
            Color cor = coresCirculo[ThreadLocalRandom.current().nextInt(0, coresCirculo.length)];

            circulos[i] = new Circulo(posX, posY, largura - Circulo.getDimensao(), altura - Circulo.getDimensao(), velX, velY, cor);
        }

        isRodando = true;
        new Thread(this).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(corFundo);
        g.fillRect(0, 0, largura, altura);

        for (Circulo c : circulos) {
            c.draw(g);            
        }
    }

    @Override
    public void run() {
        while (isRodando) {
            for (Circulo c : circulos) {
                c.mover();
                repaint();
            }

            try {
                Thread.sleep(INTERVALO_THREAD);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }        
    }

    private void setCoresCirculo() {
        coresCirculo[0] = Color.BLUE;
        coresCirculo[1] = Color.CYAN;
        coresCirculo[2] = Color.GREEN;
        coresCirculo[3] = Color.MAGENTA;
        coresCirculo[4] = Color.ORANGE;
        coresCirculo[5] = Color.PINK;
        coresCirculo[6] = Color.RED;
        coresCirculo[7] = Color.YELLOW;
        coresCirculo[8] = Color.WHITE;
    }
}
