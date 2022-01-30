import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

public class QuadTree {    
    private static final int CAP = 4;

    private Quad quad;
    private ArrayList<Particula> particulas = new ArrayList<>();

    private QuadTree noroeste, nordeste, sudoeste, sudeste;
    private boolean dividido;

    public QuadTree(Rectangle rect) {
        this.quad = new Quad(rect);
        
        System.out.println(quad);

        dividido = false;
    }

    public boolean inserir(Particula p) {
        if (!quad.contem(p)) {
            return false;
        }
        if (particulas.size() < CAP) {
            particulas.add(p);
            System.out.println(this + ": " + particulas.size());
        } else {
            if (!dividido) {
                subdividir(p);
            }
            if (noroeste.inserir(p)) return true;
            if (nordeste.inserir(p)) return true;
            if (sudoeste.inserir(p)) return true;
            if (sudeste.inserir(p)) return true;
        }

        return false;
    }

    private void subdividir(Particula p) {
        dividido = true;

        int x = quad.x;
        int y = quad.y;
        int w = quad.width / 2;
        int h = quad.height / 2;

        Rectangle no = new Rectangle(x, y, w, h);
        noroeste = new QuadTree(no);
        
        Rectangle ne = new Rectangle(x + w, y, w, h);
        nordeste = new QuadTree(ne);
        
        Rectangle so = new Rectangle(x, y + h, w, h);
        sudoeste = new QuadTree(so);
        
        Rectangle se = new Rectangle(x + w, y + h, w, h);
        sudeste = new QuadTree(se);       

        
        for (int i = 0; i < particulas.size(); i++) {
            if (noroeste.inserir(particulas.get(i))) continue;
            if (nordeste.inserir(particulas.get(i))) continue;
            if (sudoeste.inserir(particulas.get(i))) continue;
            if (sudeste.inserir(particulas.get(i))) continue;
        }
    }

    public void paint(Graphics g, Color corFundo, Color corQuad) {
        quad.paint(g, corFundo, corQuad);

        if (dividido) {
            noroeste.paint(g, corFundo, corQuad);
            nordeste.paint(g, corFundo, corQuad);
            sudoeste.paint(g, corFundo, corQuad);
            sudeste.paint(g, corFundo, corQuad);
        }
    }
}
