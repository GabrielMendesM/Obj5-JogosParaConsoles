import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Principal extends JFrame {
    private static final int DIMENSAO = 700;
    private static final int N_CIRCULOS = 3000;

    public Principal() {
        criarJanela();
    }

    public void criarJanela() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIMENSAO, DIMENSAO));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new Cena(DIMENSAO - 16, DIMENSAO - 39, N_CIRCULOS));

        setVisible(true);

        pack();
    }
}
