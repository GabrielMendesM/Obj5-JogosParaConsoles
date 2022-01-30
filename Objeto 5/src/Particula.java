import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class Particula extends Rectangle {
    public Particula(Rectangle rect) {
        super(rect);
    }

    public Particula(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g, Color cor) {        
        g.setColor(cor);
        g.fillOval(x, y, width, height);
        g.drawOval(x, y, width, height);
    }
}
