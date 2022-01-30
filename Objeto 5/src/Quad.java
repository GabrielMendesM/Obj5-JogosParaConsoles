import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Quad extends Rectangle {
    public Quad(Rectangle rect) {
        super(rect);
    }

    public boolean contem(Particula p) {
        return (p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height);
    }

    public void paint(Graphics g, Color corFundo, Color corContorno) {
        g.setColor(corFundo);
        g.fillRect(x, y, width, height);
        g.setColor(corContorno);
        g.drawRect(x, y, width, height);
    }
}
