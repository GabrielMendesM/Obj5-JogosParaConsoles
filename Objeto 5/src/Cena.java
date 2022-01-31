import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Cena extends JPanel implements Runnable, MouseInputListener {
    protected volatile boolean rodando = false;
    protected static final int INTERVALO_THREAD = 16;

    protected static final Color COR_CONTORNO = Color.decode("#84728C");
    protected static final Color COR_FUNDO = Color.decode("#66546E");
    protected static final Color[] CORES_CIRCULO = new Color[8];
    private static final int PARTICULA_DIMENSAO = 3;
    private static final int MAX_VELOCIDADE = 3;

    protected static final ArrayList<Particula> particulas = new ArrayList<>();

    protected Rectangle rect;
    protected int nParticulas;

    protected long tempoProcessamento = 0;
    protected long totalTempoProcessamento = 0;
    protected long qtdProcessos = 0;

    public Cena(Rectangle rect) {
        this.rect = rect;
        
        CORES_CIRCULO[0] = Color.BLUE;
        CORES_CIRCULO[1] = Color.CYAN;
        CORES_CIRCULO[2] = Color.GREEN;
        CORES_CIRCULO[3] = Color.ORANGE;
        CORES_CIRCULO[4] = Color.PINK;
        CORES_CIRCULO[5] = Color.RED;
        CORES_CIRCULO[6] = Color.YELLOW;
    }

    public void escolherPadrao(String padrao) {
        int x = 0;
        int y = 0;
        int velX = 1;
        int velY = 1;

        Rectangle particulaRect;

        int l = rect.width;
        int a = rect.height;
        int d = PARTICULA_DIMENSAO;

        for (int i = 0; i < nParticulas; i++) {
            Color cor = CORES_CIRCULO[ThreadLocalRandom.current().nextInt(0, CORES_CIRCULO.length)];
            switch(padrao) {
                case "ALEATORIO":
                    x = ThreadLocalRandom.current().nextInt(0, rect.width - d);
                    y = ThreadLocalRandom.current().nextInt(0, rect.height - d);
                    velX = ThreadLocalRandom.current().nextInt(1, MAX_VELOCIDADE);
                    velY = ThreadLocalRandom.current().nextInt(1, MAX_VELOCIDADE);

                    particulaRect = new Rectangle(x, y, d, d);
                    particulas.add(new Particula(particulaRect, rect.width, rect.height, velX, velY, cor, true));
                    break;
                case "EM_ORDEM":
                    int maxPorLinha = l / (d * 4) + 1;
                    int maxPorColuna = a / (d * 2);
                    int max = maxPorLinha * maxPorColuna + 1;

                    if (i == max) {
                        nParticulas = max;
                        return;
                    }

                    if (i < maxPorLinha) {
                        x = i * d * 4 + d;
                        y = 0;
                    } else {
                        x = (i - (maxPorLinha * (i / maxPorLinha))) * d * 4 + d;
                        y = i / maxPorLinha * d * 2 + d;
                    }

                    particulaRect = new Rectangle(x, y, d, d);
                    particulas.add(new Particula(particulaRect, rect.width, rect.height, velX, velY, cor));
                    System.out.println(i);
                    break;
                case "X":
                    //NÃO IMPLEMENTADO
                    break;
                case "MOLDURA":
                    //NÃO IMPLEMENTADO
                    break;
                default:
                    System.out.print("NÃO IMPLEMENTADO!");
                    return;
            }
        }
    }

    public void liberarMouse() {
        addMouseListener(this);
    }

    public void comecar() {
        rodando = true;
        new Thread(this).start();
    }
    
    public void finalizar() {
        rodando = false;
        System.out.println("Média: " + (totalTempoProcessamento / qtdProcessos) + "ns");
        System.exit(0);
    }

    @Override
    public void run() {
        while (rodando) {
            repaint();
            try {
                Thread.sleep(INTERVALO_THREAD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        Rectangle pRect = new Rectangle(e.getX() - PARTICULA_DIMENSAO / 2, e.getY() - PARTICULA_DIMENSAO / 2, PARTICULA_DIMENSAO, PARTICULA_DIMENSAO);
        int velX = ThreadLocalRandom.current().nextInt(1, MAX_VELOCIDADE);
        int velY = ThreadLocalRandom.current().nextInt(1, MAX_VELOCIDADE);
        particulas.add(new Particula(pRect, rect.width, rect.height, velX, velY, CORES_CIRCULO[ThreadLocalRandom.current().nextInt(0, CORES_CIRCULO.length)], true));
        nParticulas++;
        repaint();
    }

    //#region MouseInputListener não usados
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    //#endregion
    
    public void setNParticulas(int nParticulas) {
        this.nParticulas = nParticulas;
    }
}
