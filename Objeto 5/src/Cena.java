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

    protected static final ArrayList<Particula> particulas = new ArrayList<>();

    protected Rectangle rect;
    private static Janela janela;
    protected int nParticulas;

    public Cena(Rectangle rect, Janela janela) {
        this.rect = rect;
        
        CORES_CIRCULO[0] = Color.BLUE;
        CORES_CIRCULO[1] = Color.CYAN;
        CORES_CIRCULO[2] = Color.GREEN;
        CORES_CIRCULO[3] = Color.MAGENTA;
        CORES_CIRCULO[4] = Color.ORANGE;
        CORES_CIRCULO[5] = Color.PINK;
        CORES_CIRCULO[6] = Color.RED;
        CORES_CIRCULO[7] = Color.YELLOW;

        Cena.janela = janela;
    }

    public void escolherPadrao(String padrao) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < nParticulas; i++) {
            switch(padrao) {
                case "ALEATORIO":
                    x = ThreadLocalRandom.current().nextInt(0, janela.getRect().width - PARTICULA_DIMENSAO);
                    y = ThreadLocalRandom.current().nextInt(0, janela.getRect().height - PARTICULA_DIMENSAO);
                    break;
                case "EM_ORDEM":
                    x = i * PARTICULA_DIMENSAO * 2 + PARTICULA_DIMENSAO;
                    y = i % 2 == 0 ? PARTICULA_DIMENSAO * 2 : PARTICULA_DIMENSAO * 4;
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
            Rectangle particulaRect = new Rectangle(x, y, PARTICULA_DIMENSAO, PARTICULA_DIMENSAO);
            particulas.add(new Particula(particulaRect));
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
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        particulas.add(new Particula(e.getX(), e.getY(), PARTICULA_DIMENSAO, PARTICULA_DIMENSAO));
        nParticulas++;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public int getNParticulas() {
        return nParticulas;
    }
    
    public void setNParticulas(int nParticulas) {
        this.nParticulas = nParticulas;
    }
}
