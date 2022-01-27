import java.awt.Rectangle;

public interface Colidivel {
    boolean checarColisao(Colidivel outro);
    Rectangle getColisaoRect();
    void onColisao(Colidivel outro);
    int getPosX();
    int getPosY();
}
