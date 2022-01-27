import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

public class Simulacao extends JPanel implements Runnable {
    private volatile boolean isRodando = false;

    private static final int MAX_VELOCIDADE = 3;
    private static final int INTERVALO_THREAD = 16;

    private static final Color COR_CONTORNO = Color.RED;
    private static final Color COR_FUNDO = Color.decode("#705E78");
    private static final Color COR_CIRCULO = Color.BLACK;

    private int largura, altura;
    private final Circulo[] circulos;

    private long tempoProcessamento = 0;
    private long totalTempoProcessamento = 0;
    private int qtdProcessos = 0;

    public Simulacao(int larguraTela, int alturaTela, int nCirculos) {
        largura = larguraTela;
        altura = alturaTela;
        circulos = new Circulo[nCirculos];

        for (int i = 0; i < circulos.length; i++) {
            int posX = ThreadLocalRandom.current().nextInt(0, largura - Circulo.getDimensao());
            int posY = ThreadLocalRandom.current().nextInt(0, altura - Circulo.getDimensao());
            float velX = ThreadLocalRandom.current().nextInt(1, MAX_VELOCIDADE);
            float velY = ThreadLocalRandom.current().nextInt(1, MAX_VELOCIDADE);
            
            circulos[i] = new Circulo(posX, posY, largura - Circulo.getDimensao(), altura - Circulo.getDimensao(), velX, velY, COR_CIRCULO);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(COR_FUNDO);
        g.fillRect(0, 0, largura, altura);
        g.setColor(COR_CONTORNO);
        g.drawRect(0, 0, largura, altura);
        
        for (Circulo c : circulos) {
            c.draw(g);            
        }
    }

    @Override
    public void run() {
        while (isRodando) {
            tempoProcessamento = System.nanoTime();

            for (int i = 0; i < circulos.length; i++) {
                for (int j = 0; j < circulos.length; j++) {
                    if (i == j) continue;
                    if (circulos[i].checarColisao(circulos[j])) {
                        circulos[i].onColisao();
                    }
                }
            }

            tempoProcessamento = System.nanoTime() - tempoProcessamento;
            totalTempoProcessamento += tempoProcessamento;
            qtdProcessos++;

            for (Circulo c : circulos) {
                c.mover();
                repaint();
            }

            try {
                Thread.sleep(INTERVALO_THREAD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }

    public void comecar() {
        isRodando = true;
        new Thread(this).start();
    }

    public void parar() {
        isRodando = false;
        imprimirMediaProcessamento();
    }

    public void imprimirMediaProcessamento() {
        System.out.println("Média: " + (totalTempoProcessamento / qtdProcessos) + "ns");
    }
}
