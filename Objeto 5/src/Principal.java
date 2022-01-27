import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Principal extends JFrame {
    private static final int LARGURA = 1365;
    private static final int ALTURA = 730;
    private static final int N_CIRCULOS = 1500;
    private Cena cena;

    public Principal() {
        cena = new Cena(LARGURA - 16, ALTURA - 70, N_CIRCULOS);

        criarJanela();

        JPanel pnlGUI = new JPanel();
        pnlGUI.setLayout(new FlowLayout(FlowLayout.LEFT));
        Button btnNormal = new Button("Normal");
        Button btnQuadTree = new Button("QuadTree");
        Button btnParar = new Button("Parar");

        pnlGUI.add(btnNormal);
        pnlGUI.add(btnQuadTree);
        pnlGUI.add(btnParar);

        add(pnlGUI, BorderLayout.SOUTH);

        btnNormal.addActionListener(e -> {
            pnlGUI.remove(btnNormal);
            cena.comecar();
        });

        btnQuadTree.addActionListener(e -> {
            System.out.println("Não implementado");
        });

        btnParar.addActionListener(e -> cena.parar());
    }

    public void criarJanela() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(cena);

        setVisible(true);

        pack();
    }
}
