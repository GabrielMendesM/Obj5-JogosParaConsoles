import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class SimulacaoQuadTree extends Cena {
    private QuadTree quadTree;

    public SimulacaoQuadTree(Rectangle rect, Janela janela) {
        super(rect, janela);
        
        quadTree = new QuadTree(rect);
    }

    public void escolherPadrao(String padrao) {
        super.escolherPadrao(padrao);

        for (Particula p : particulas) {
            quadTree.inserir(p);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(COR_FUNDO);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        quadTree.paint(g, COR_FUNDO, COR_CONTORNO);

        for (Particula p : particulas) {
            p.paint(g, CORES_CIRCULO[0]);            
        }
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
        super.mousePressed(e);
        quadTree.inserir(particulas.get(particulas.size() - 1));
        nParticulas++;
        repaint();
    }
}
