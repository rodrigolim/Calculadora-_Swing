import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculadoraComHistorico extends JFrame implements ActionListener {
    private JTextField display;
    private JButton[] botoesNumericos;
    private JButton[] botoesOperacoes;
    private JButton botaoIgual;
    private JButton botaoLimpar;

    private JTextArea historicoTextArea;

    private double numeroAtual;
    private String operacaoAtual;

    private ArrayList<String> historico;

    public CalculadoraComHistorico() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 600);

        historicoTextArea = new JTextArea();
        historicoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historicoTextArea);
        // Ajuste para ocupar 50% do frame
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() / 3));
        add(scrollPane, BorderLayout.NORTH);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setEditable(false);
        add(display, BorderLayout.CENTER);

        botoesNumericos = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botoesNumericos[i] = new JButton(Integer.toString(i));
            configurarBotao(botoesNumericos[i]);
            botoesNumericos[i].addActionListener(this);
        }

        botoesOperacoes = new JButton[4];
        botoesOperacoes[0] = new JButton("+");
        botoesOperacoes[1] = new JButton("-");
        botoesOperacoes[2] = new JButton("*");
        botoesOperacoes[3] = new JButton("/");
        for (JButton botao : botoesOperacoes) {
            configurarBotao(botao);
            botao.addActionListener(this);
        }

        botaoIgual = new JButton("=");
        configurarBotao(botaoIgual);
        botaoIgual.addActionListener(this);

        botaoLimpar = new JButton("C");
        configurarBotao(botaoLimpar);
        botaoLimpar.addActionListener(this);

        historico = new ArrayList<>();

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4));

        painelBotoes.add(botoesNumericos[7]);
        painelBotoes.add(botoesNumericos[8]);
        painelBotoes.add(botoesNumericos[9]);
        painelBotoes.add(botoesOperacoes[0]);
        painelBotoes.add(botoesNumericos[4]);
        painelBotoes.add(botoesNumericos[5]);
        painelBotoes.add(botoesNumericos[6]);
        painelBotoes.add(botoesOperacoes[1]);
        painelBotoes.add(botoesNumericos[1]);
        painelBotoes.add(botoesNumericos[2]);
        painelBotoes.add(botoesNumericos[3]);
        painelBotoes.add(botoesOperacoes[2]);
        painelBotoes.add(botoesNumericos[0]);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoIgual);
        painelBotoes.add(botoesOperacoes[3]);

        // Ajuste para ocupar 50% do frame
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void configurarBotao(JButton botao) {
        // Configuração da fonte para Arial 22, negrito
        botao.setFont(new Font("Arial", Font.BOLD, 22));
        // Ajusta o tamanho do botão para ocupar 50% do frame
        botao.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / 8));
    }

@Override
public void actionPerformed(ActionEvent e) {
    JButton botaoClicado = (JButton) e.getSource();

    if (isBotaoNumerico(botaoClicado)) {
        String numeroClicado = botaoClicado.getText();
        display.setText(display.getText() + numeroClicado);
    } else if (isBotaoOperacao(botaoClicado)) {
        numeroAtual = Double.parseDouble(display.getText());
        operacaoAtual = botaoClicado.getText();
        display.setText("");
    } else if (botaoClicado == botaoIgual) {
        double segundoNumero = Double.parseDouble(display.getText());
        double resultado = realizarOperacao(numeroAtual, segundoNumero, operacaoAtual);
        display.setText(String.valueOf(resultado));

        // Adiciona a operação ao histórico
        String operacao = numeroAtual + " " + operacaoAtual + " " + segundoNumero + " = " + resultado;
        historico.add(operacao);

        // Atualiza o JTextArea do histórico
        atualizarHistorico();
    } else if (botaoClicado == botaoLimpar) {
        display.setText("");
    }
}

private boolean isBotaoNumerico(JButton botao) {
    for (JButton b : botoesNumericos) {
        if (botao == b) {
            return true;
        }
    }
    return false;
}

private boolean isBotaoOperacao(JButton botao) {
    for (JButton b : botoesOperacoes) {
        if (botao == b) {
            return true;
        }
    }
    return false;
}

private double realizarOperacao(double num1, double num2, String operacao) {
    switch (operacao) {
        case "+":
            return num1 + num2;
        case "-":
            return num1 - num2;
        case "*":
            return num1 * num2;
        case "/":
            if (num2 != 0) {
                return num1 / num2;
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Divisão por zero!");
                return 0;
            }
        default:
            return 0;
    }
}

private void atualizarHistorico() {
    // Atualiza o JTextArea com o histórico
    historicoTextArea.setText("");
    for (String operacao : historico) {
        historicoTextArea.append(operacao + "\n");
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraComHistorico calculadora = new CalculadoraComHistorico();
            calculadora.setVisible(true);
        });
    }
}

