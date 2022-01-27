import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Principal extends JFrame {
    private String tipoSimulacao;

    private static final int LARGURA = 1366;
    private static final int ALTURA = 730;
    private static int nParticulas;
    private Simulacao simulacaoNormal;
    private SimulacaoQuadTree simulacaoQuadTree;

    public Principal() {
        super("Objeto 5");
        
        criarJanela();

        JPanel pnlGUI = new JPanel();
        pnlGUI.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextArea inputNCirculos = new JTextArea("N Partículas: ");
        Button btnNormal = new Button("Normal");
        Button btnQuadTree = new Button("QuadTree");
        Button btnFinalizar = new Button("Finalizar");

        pnlGUI.add(inputNCirculos);
        pnlGUI.add(btnNormal);
        pnlGUI.add(btnQuadTree);

        add(pnlGUI, BorderLayout.SOUTH);

        setVisible(true);
        pack();
        
        btnNormal.addActionListener(e -> {
            nParticulas = Integer.parseInt(inputNCirculos.getText());
            pnlGUI.remove(btnNormal);
            pnlGUI.remove(btnQuadTree);
            pnlGUI.remove(inputNCirculos);
            pnlGUI.add(btnFinalizar);
            simulacaoNormalComecar();
        });

        btnQuadTree.addActionListener(e -> {
            nParticulas = Integer.parseInt(inputNCirculos.getText());
            pnlGUI.remove(btnNormal);
            pnlGUI.remove(btnQuadTree);
            pnlGUI.remove(inputNCirculos);
            pnlGUI.add(btnFinalizar);
            simulacaoQuadTreeComecar();
        });

        btnFinalizar.addActionListener(e -> {
            if (tipoSimulacao != null) {
                if (tipoSimulacao == "normal") {
                    simulacaoNormal.parar();
                } else {
                    simulacaoQuadTree.parar();
                }
            }
            System.exit(0);
        });
    }

    public void criarJanela() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void simulacaoNormalComecar() {
        simulacaoNormal = new Simulacao(LARGURA - 20, ALTURA - 76, nParticulas);
        tipoSimulacao = "normal";
        add(simulacaoNormal);
        pack();
        simulacaoNormal.comecar();
    }

    private void simulacaoQuadTreeComecar() {
        simulacaoQuadTree = new SimulacaoQuadTree(LARGURA - 20, ALTURA - 76, nParticulas);
        tipoSimulacao = "quadTree";
        add(simulacaoQuadTree);
        pack();
        simulacaoQuadTree.comecar();
    }
}
