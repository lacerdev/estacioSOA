package br.com.lacerda.estacio.jms;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AppUIProdutor extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static String operacaoSelecionada;

	public AppUIProdutor() {
		super(new GridLayout(3, 1));
		// Create the radio buttons.
		JRadioButton somaButton = new JRadioButton(Operacao.SOMA.name());
		somaButton.setActionCommand(Operacao.SOMA.name());
		somaButton.setSelected(true);
		operacaoSelecionada = Operacao.SOMA.name();

		JRadioButton subtracaoButton = new JRadioButton(Operacao.SUBTRACAO.name());
		subtracaoButton.setActionCommand(Operacao.SUBTRACAO.name());

		JRadioButton multiplicacaoButton = new JRadioButton(Operacao.MULTIPLICACAO.name());
		multiplicacaoButton.setActionCommand(Operacao.MULTIPLICACAO.name());

		JRadioButton divisaoButton = new JRadioButton(Operacao.DIVISAO.name());
		divisaoButton.setActionCommand(Operacao.DIVISAO.name());

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(somaButton);
		group.add(subtracaoButton);
		group.add(multiplicacaoButton);
		group.add(divisaoButton);

		// Register a listener for the radio buttons.
		somaButton.addActionListener(this);
		subtracaoButton.addActionListener(this);
		multiplicacaoButton.addActionListener(this);
		divisaoButton.addActionListener(this);

		// Put the radio buttons in a column in a panel.
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(somaButton);
		radioPanel.add(subtracaoButton);
		radioPanel.add(multiplicacaoButton);
		radioPanel.add(divisaoButton);

		JPanel inputsPanel = new JPanel(new GridLayout(2, 2));
		JLabel labelA = new JLabel("Número A");
		JLabel labelB = new JLabel("Número B");
		JTextField numA = new JTextField();
		JTextField numB = new JTextField();
		inputsPanel.add(labelA);
		inputsPanel.add(numA);
		inputsPanel.add(labelB);
		inputsPanel.add(numB);

		JButton botaoEnviar = new JButton("Efetuar operacao");
		botaoEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String mensagem = numA.getText() + "||" + operacaoSelecionada + "||" + numB.getText();
				try {
					Produtor.enviaMensagem(mensagem);
				} catch (NamingException | JMSException e) {
					JOptionPane.showMessageDialog(AppUIProdutor.this, "Ocorreu um erro ao enviar o calculo", "Ops!",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});

		add(radioPanel);
		add(inputsPanel);
		add(botaoEnviar);
		setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
	}

	/** Listens to the radio buttons. */
	public void actionPerformed(ActionEvent e) {
		operacaoSelecionada = e.getActionCommand();
		System.out.println("Clicou na " + operacaoSelecionada);
	}

	public static String getOperacaoSelecionada() {
		return operacaoSelecionada;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JPanel newContentPane = new AppUIProdutor();
		newContentPane.setPreferredSize(new Dimension(300, 400));
		frame.setContentPane(newContentPane);
		frame.setTitle("Operação com números");
		// Display the window.
		frame.pack();
//		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
